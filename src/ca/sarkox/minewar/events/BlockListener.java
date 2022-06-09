package ca.sarkox.minewar.events;

import java.util.*;

import ca.sarkox.minewar.utils.ItemBuilder;
import ca.sarkox.minewar.utils.TextUtil;
import net.minecraft.server.level.EntityPlayer;
import net.minecraft.world.level.block.state.IBlockData;
import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.craftbukkit.v1_18_R2.entity.CraftPlayer;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockFormEvent;
import org.bukkit.event.block.BlockFromToEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.inventory.ItemStack;

import ca.sarkox.minewar.utils.GameState;


public class BlockListener implements Listener {

	private static ArrayList<Block> blocks = new ArrayList<Block>();
	private static HashMap<Location, Material> blocksBroke = new HashMap<Location, Material>();
	private static ArrayList<Location> fluid = new ArrayList<Location>();

	@SuppressWarnings("unused")
	@EventHandler
	public void onBreakBlocks(BlockBreakEvent e) {
		Block b = e.getBlock();
		Player p = e.getPlayer();

		if (p.getGameMode().equals(GameMode.CREATIVE)) { return; }
		if (!GameState.isState(GameState.FIGHTING)) {
			e.setCancelled(true);
		}

		if (GameState.isState(GameState.STUFFING)){

			List<Material> needItem = Arrays.asList(Material.STONE,Material.IRON_ORE,Material.GOLD_ORE,Material.DIAMOND_ORE,Material.REDSTONE_ORE,Material.LAPIS_ORE,Material.OBSIDIAN,Material.COBWEB);
			List<ItemStack> items = new ArrayList<>();
			e.getBlock().getDrops().forEach(itemStack -> {
				if (itemStack.getType() == Material.RAW_IRON) {
					items.add(new ItemStack(Material.IRON_INGOT));
				} else if (itemStack.getType() == Material.RAW_GOLD) {
					items.add(new ItemStack(Material.GOLD_INGOT));
				} else if (itemStack.getType() == Material.GRAVEL) {
					TextUtil.getInstance().broadcastAction("§cRien reçu..");
					return;
				} else if (itemStack.getType() == Material.FLINT) {
					TextUtil.getInstance().broadcastAction("§aVous avez trouvé §f10§a flèches!");
					items.add(new ItemStack(Material.ARROW, 10));
				} else if (itemStack.getType() == Material.OAK_SAPLING) {
					items.add(new ItemStack(Material.APPLE, 5));
				} else
					items.add(itemStack);
			});

			ItemStack tool = p.getInventory().getItemInMainHand();

			net.minecraft.world.level.block.Block nmsBlock = org.bukkit.craftbukkit.v1_18_R2.util.CraftMagicNumbers.getBlock(e.getBlock().getType());
			if (nmsBlock == null) { return; }
			IBlockData data = nmsBlock.n();

			if(tool != null && needItem.indexOf(b.getType()) != -1 ){
				if (org.bukkit.craftbukkit.v1_18_R2.util.CraftMagicNumbers.getItem(tool.getType()).a_(data))
					items.forEach(itemStack -> p.getInventory().addItem(itemStack));
			} else {
				items.forEach(itemStack -> p.getInventory().addItem(itemStack));
			}

		} else if (GameState.isState(GameState.FIGHTING)) {
			blocksBroke.put(b.getLocation(), b.getType());
		}

	}

	@EventHandler
	public void onPlaceBlocks(BlockPlaceEvent e) {

		Block b = e.getBlock();
		Player p = e.getPlayer();

		if (p.getGameMode().equals(GameMode.CREATIVE)) { return; }

		if (GameState.isState(GameState.LOBBY)) {
			e.setCancelled(true);
			return;
		}

		List<Material> acceptedBlock = Arrays.asList(Material.CRAFTING_TABLE,Material.ANVIL, Material.FURNACE, Material.ENCHANTING_TABLE);

		if ((GameState.isState(GameState.STUFFING) && acceptedBlock.indexOf(b.getType()) != -1) || GameState.isState(GameState.FIGHTING) ) {
			blocks.add(b);
		} else {
			e.setCancelled(true);
		}
	}
	
	@EventHandler
	public void onBlockFromTo(BlockFromToEvent e) {
	  Material mat = e.getBlock().getType();
	  List<Material> detect = Arrays.asList(Material.WATER, Material.LAVA, Material.COBBLESTONE, Material.OBSIDIAN);
	  if(detect.indexOf(mat) != -1) {
		  fluid.add(e.getBlock().getLocation());
	  }
	}
	@EventHandler
	public void onMerge (BlockFormEvent e) {
		blocks.add(e.getBlock());
	}


	public static void reset() {
		for (Location loc : fluid) {
			Block b = loc.getBlock();
			b.setType(Material.AIR);
		}

		for (Location loc : blocksBroke.keySet()) {
			Material mat = blocksBroke.get(loc);
			loc.getBlock().setType(mat);
		}

		for (Block bl : blocks) {
			bl.setType(Material.AIR);
		}

		fluid.clear();
		blocks.clear();
		blocksBroke.clear();
	}
}
