package ca.sarkox.minewar.utils;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import com.mojang.authlib.GameProfile;
import com.mojang.authlib.properties.Property;
import org.bukkit.Bukkit;
import org.bukkit.Color;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.EnchantmentStorageMeta;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.LeatherArmorMeta;
import org.bukkit.inventory.meta.PotionMeta;
import org.bukkit.inventory.meta.SkullMeta;
import org.bukkit.potion.Potion;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionType;

@SuppressWarnings("deprecation")
public class ItemBuilder {

	ItemStack item;

	public ItemBuilder(Material mat) {
		this(mat, 1);
	}

	public ItemBuilder(Material mat, int i) {
		this(mat, i, (short) 0);
	}

	public ItemBuilder(Material mat, int i, short damage) {
		item = new ItemStack(mat, i, damage);
	}

	public ItemBuilder(PotionType pt, int l, boolean s, boolean e) {
		Potion potion = new Potion(pt, l);
		potion.setSplash(s);
		potion.setHasExtendedDuration(e);
		this.item = potion.toItemStack(1);
	}

	public ItemBuilder addEnchant(Enchantment e, int l) {
		if (this.item.getType() == Material.ENCHANTED_BOOK) {
			EnchantmentStorageMeta a = (EnchantmentStorageMeta) this.item.getItemMeta();
			a.addStoredEnchant(e, l, true);
			this.item.setItemMeta(a);
		} else
			this.item.addUnsafeEnchantment(e, l);

		return this;
	}

	public ItemBuilder setLeatherArmor(Color color) {
		if (this.item.getType() == Material.LEATHER_BOOTS || this.item.getType() == Material.LEATHER_CHESTPLATE
				|| this.item.getType() == Material.LEATHER_HELMET || this.item.getType() == Material.LEATHER_LEGGINGS) {
			LeatherArmorMeta meta = (LeatherArmorMeta) this.item.getItemMeta();
			meta.setColor(color);
			this.item.setItemMeta(meta);
		}

		return this;
	}

	public ItemBuilder setSkull(String s) {
		if (this.item.getType() == Material.LEGACY_SKULL_ITEM) {
			SkullMeta meta = (SkullMeta) Bukkit.getItemFactory().getItemMeta(Material.LEGACY_SKULL_ITEM);
			meta.setOwner(s);
			this.item.setItemMeta(meta);
		}

		return this;
	}

	public ItemBuilder setAttackSpeed(int s) {
		if (this.item.getType() == Material.DIAMOND_SWORD || this.item.getType() == Material.GOLDEN_SWORD
				|| this.item.getType() == Material.STONE_SWORD || this.item.getType() == Material.STONE_SWORD
				|| this.item.getType() == Material.WOODEN_SWORD) {
			ItemMeta meta = this.item.getItemMeta();
			this.item.setItemMeta(meta);
		}

		return this;
	}

	
	public ItemBuilder setUnbreakable() {
		ItemMeta meta = this.item.getItemMeta();
		meta.setUnbreakable(true);
		this.item.setItemMeta(meta);

		return this;
	}

	public ItemBuilder hideEnchant() {
		ItemMeta meta = this.item.getItemMeta();
		meta.addItemFlags(new ItemFlag[] { ItemFlag.HIDE_ENCHANTS });
		this.item.setItemMeta(meta);

		return this;
	}

	public ItemBuilder hideAttributes() {
		ItemMeta meta = this.item.getItemMeta();
		meta.addItemFlags(new ItemFlag[] { ItemFlag.HIDE_ATTRIBUTES });
		this.item.setItemMeta(meta);

		return this;
	}

	public ItemBuilder hideUnbreakble() {
		ItemMeta meta = this.item.getItemMeta();
		meta.addItemFlags(new ItemFlag[] { ItemFlag.HIDE_UNBREAKABLE });
		this.item.setItemMeta(meta);

		return this;
	}

	public ItemBuilder hidePotionEffects() {
		ItemMeta meta = this.item.getItemMeta();
		meta.addItemFlags(new ItemFlag[] { ItemFlag.HIDE_POTION_EFFECTS });
		this.item.setItemMeta(meta);

		return this;
	}

	public ItemBuilder setLore(String lore) {
		ItemMeta meta = this.item.getItemMeta();
		meta.setLore(lore(new String[] { lore }));
		this.item.setItemMeta(meta);

		return this;
	}

	public ItemBuilder setLore(String... lores) {
		ItemMeta meta = this.item.getItemMeta();
		meta.setLore(lore(lores));
		this.item.setItemMeta(meta);

		return this;
	}
	public ItemBuilder setLore(List<String> lores) {
		ItemMeta meta = this.item.getItemMeta();
		meta.setLore(lore(lores));
		this.item.setItemMeta(meta);

		return this;
	}

	public ItemBuilder setName(String name) {
		ItemMeta meta = this.item.getItemMeta();
		meta.setDisplayName(name);
		this.item.setItemMeta(meta);

		return this;
	}

	public ItemBuilder addPotionEffect(PotionEffect e) {
		if (this.item.getType() == Material.POTION) {
			PotionMeta meta = (PotionMeta) this.item.getItemMeta();
			meta.addCustomEffect(e, true);
		}

		return this;
	}

	public ItemBuilder setSkullFromBase64(String base64) {
		if (base64 == null || base64.isEmpty())
			return this;
		SkullMeta skullMeta = (SkullMeta) this.item.getItemMeta();
		GameProfile profile = new GameProfile(UUID.randomUUID(), null);
		profile.getProperties().put("textures", new Property("textures", base64));
		Field profileField = null;
		try {
			profileField = skullMeta.getClass().getDeclaredField("profile");
		} catch (NoSuchFieldException | SecurityException e) {
			e.printStackTrace();
		}
		profileField.setAccessible(true);
		try {
			profileField.set(skullMeta, profile);
		} catch (IllegalArgumentException | IllegalAccessException e) {
			e.printStackTrace();
		}
		this.item.setItemMeta(skullMeta);
		return this;
	}


	private ArrayList<String> lore(String[] lore) {
		ArrayList<String> finalLore = new ArrayList<String>();

		String[] arrayOfString = lore;
		int j = lore.length;

		for (int i = 0; i < j; i++) {
			String s = arrayOfString[i];

			if (s != null)
				finalLore.add("§f" + s);
		}

		return finalLore;
	}

	private ArrayList<String> lore(List<String> lore) {
		ArrayList<String> finalLore = new ArrayList<String>();

		List<String> arrayOfString = lore;
		int j = lore.size();

		for (int i = 0; i < j; i++) {
			String s = arrayOfString.get(i);

			if (s != null)
				finalLore.add("§f" + s);
		}

		return finalLore;
	}

	public ItemStack toItemStack() {
		return this.item;
	}

}
