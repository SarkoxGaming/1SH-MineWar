package ca.sarkox.minewar.models;

import org.bukkit.GameMode;
import org.bukkit.attribute.Attribute;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

public class CPlayer {

	private Player p;

	public CPlayer(Player p) {
		this.p = p;
	}

	public Player getPlayer() {
		return  this.p;
	}

	public void reset() {
		this.p.getAttribute(Attribute.GENERIC_MAX_HEALTH).setBaseValue(20);
		this.p.setSaturation(20f);
		this.p.setHealth(20);
		this.p.setFoodLevel(20);
		this.p.getInventory().clear();
		this.p.getInventory().setArmorContents(new ItemStack[4]);
		this.p.setCanPickupItems(true);
		this.p.dropItem(true);
		this.p.setAllowFlight(false);
		this.p.setCanPickupItems(true);
		this.p.setCollidable(true);
		this.p.setGameMode(GameMode.SURVIVAL);
		this.p.setTotalExperience(1);
	}
	
	public void setDeath() {
		
	}
	
	
	public void setSpectator() {
		this.reset();
		this.p.setAllowFlight(true);
		this.p.setCanPickupItems(false);
		this.p.setCollidable(false);
		this.p.setInvisible(true);
		this.p.dropItem(false);
	}
	
	
	
}
