package ca.sarkox.minewar.utils;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.Sound;
import org.bukkit.entity.HumanEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.HandlerList;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import ca.sarkox.minewar.Main;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.util.Consumer;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.UUID;


public abstract class GUI implements Listener {



	private boolean cancel = true;

	private List<GuiItem> items = new ArrayList<GuiItem>();
	
	private String design = " §f? "; // Entre prefix & name. Exemple : »

	private Player player;
	private Inventory inv;

	public GUI(Player player, int size, String prefix, String name) {
		this.player = player;
		Inventory inventory = Bukkit.createInventory(null, size, "§9" + prefix + this.design + "§6" + name);
		this.inv = inventory;
		onOpen(player);
		player.openInventory(getInv());
		Bukkit.getPluginManager().registerEvents(this, Main.getPlugin());
	}

	public GUI(Player player, InventoryType type, String prefix, String name) {
		this.player = player;
		Inventory inventory = Bukkit.createInventory(null, type, "§9" + prefix + this.design + "§f" + name);
		this.inv = inventory;
		onOpen(player);
		player.openInventory(getInv());
		Bukkit.getPluginManager().registerEvents(this, Main.getPlugin());
	}

	public abstract void onOpen(Player player);

	public abstract void onClose(Player player);

	public void open() {
		player.openInventory(getInv());
	}
	
	public void setCancel(boolean b) {
		this.cancel = b;
	}

	// Events
	@EventHandler
	public void onInventoryClose(InventoryCloseEvent e) {
		HumanEntity player = e.getPlayer();

		if (e.getInventory().equals(this.inv) && player.equals(this.player)) {
			HandlerList.unregisterAll(this);

			onClose((Player) player);
		}
	}

	@EventHandler
	public void onInventoryClick(InventoryClickEvent e) {
		Player player = (Player) e.getWhoClicked();

		if (e.getCurrentItem() == null)
			return;

		if (e.getClickedInventory().equals(getInv()) && player.equals(this.player)) {
			if (this.cancel)
				e.setCancelled(true);

			GuiItem clickedItem = GUI.findMatchingItem(items, e.getCurrentItem());
			if (clickedItem != null)
				clickedItem.callAction(e);

			if (e.getCurrentItem() != null || e.getCurrentItem() != new ItemStack(Material.AIR))
				player.playSound(player.getLocation(), Sound.UI_BUTTON_CLICK, 1.0F, 1.0F);
		}
	}

	public void set(int slot, ItemStack item, @Nullable Consumer<InventoryClickEvent> action) {
		GuiItem gi = new GuiItem(item,action);
		getInv().setItem(slot, gi.getItem());
		items.add(gi);
	}

	public void set(int colunm, int row, ItemStack item, @Nullable Consumer<InventoryClickEvent> action) {
		set((9*row)+colunm, item, action);
	}

	public Player getPlayer() {
		return this.player;
	}

	public Inventory getInv() {
		return inv;
	}

	protected static <T extends GuiItem> T findMatchingItem(@NotNull Collection<T> items, @NotNull ItemStack item) {
		ItemMeta meta = item.getItemMeta();
		if (meta == null) {
			return null;
		}

		UUID uuid = meta.getPersistentDataContainer().get(GuiItem.KEY_UUID, UUIDTagType.INSTANCE);
		if (uuid == null) {
			return null;
		}

		return items.stream()
				.filter(guiItem -> guiItem.getUUID().equals(uuid))
				.findAny().orElse(null);
	}

	public void borderFill(ItemStack item) {
		int size = getInv().getSize();
		for (int i = 0; i < size; i++) {
			if ((i >= 0 && i <= 8) || (i >= (size-9) && i <= size)) set(i,item, null);
			else if (i % 9 == 0 || i % 9 == 8) set(i,item, null);
		}
	}

}