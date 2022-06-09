package ca.sarkox.minewar.events;

import org.bukkit.entity.Player;
import org.bukkit.entity.Projectile;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;

import ca.sarkox.minewar.models.CPlayer;
import ca.sarkox.minewar.utils.GameState;
import ca.sarkox.minewar.utils.TextUtil;


public class EntityDamageByEntity implements Listener {

	@EventHandler
	public void onEntityDamageByEntity(EntityDamageByEntityEvent e) {

		if (e.getDamager() instanceof Player == false || e.getDamager() instanceof Projectile == false)  {
			e.setCancelled(true);
			return;
		};
		

		CPlayer player = (CPlayer) e.getEntity();
		Player damager = (Player) e.getDamager();

		if (!player.getPlayer().canSee(damager) || !GameState.isState(GameState.FIGHTING)) {
			e.setCancelled(true);
		}
		
	
		
		double playerHealth = player.getPlayer().getHealth();
		
		if (e.getFinalDamage() > playerHealth) {
			e.setCancelled(true);
			player.setDeath();
			TextUtil.getInstance().broadcast(String.format("§a%s §fa été tué par §c$s", player.getPlayer().getName(), damager.getName()));
		}
			
		
		
		
		
		
	}
}
