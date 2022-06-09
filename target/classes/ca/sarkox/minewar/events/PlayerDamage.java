package ca.sarkox.minewar.events;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageEvent;

import ca.sarkox.minewar.utils.GameState;


public class PlayerDamage implements Listener {

	@EventHandler
	public void onDamage(EntityDamageEvent e) {

		if (!GameState.isState(GameState.FIGHTING)) {
			e.setCancelled(true);
			return;
		}
		
		if (e.getEntity() instanceof Player == false) {
			e.setCancelled(true);
			return;
		}
		

		
		
	}

}
