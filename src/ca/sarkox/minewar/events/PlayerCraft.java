package ca.sarkox.minewar.events;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.CraftItemEvent;
import org.bukkit.inventory.ItemStack;


public class PlayerCraft implements Listener {

	@EventHandler
	public void onCraft(CraftItemEvent e) {

		Player p = (Player) e.getWhoClicked();
		
		if (e.getRecipe().getResult().getType() == Material.BUCKET) {
			
			p.getInventory().addItem(new ItemStack(Material.LAVA_BUCKET));
			p.getInventory().addItem(new ItemStack(Material.WATER_BUCKET));
			
		} 
	}
}
