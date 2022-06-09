package ca.sarkox.minewar.events;

import ca.sarkox.minewar.Main;
import ca.sarkox.minewar.utils.GameManager;
import ca.sarkox.minewar.utils.GameState;
import ca.sarkox.minewar.utils.PlayersManager;
import ca.sarkox.minewar.utils.TextUtil;
import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerMoveEvent;

public class PlayerMove implements Listener {

	@EventHandler
	public void onMove(PlayerMoveEvent e) {

		Player p = e.getPlayer();
		Location loc = p.getLocation();

		if (GameState.isState(GameState.FIGHTING)) {

			if (p.getLocation().getBlockY() <= 0) {
				if (PlayersManager.getInstance().hasPlayer(p)) {
					p.setMaxHealth(20);
					p.setHealth(20.0);
					p.setFoodLevel(20);
					p.setSaturation(20f);

					PlayersManager.getInstance().removePlayer(p);

					TextUtil.getInstance().broadcast(String.format("§c%s §6est Mort!",p.getName())).alertAll();

					p.setGameMode(GameMode.SPECTATOR);
					p.setHealth(20);
					p.setFoodLevel(20);
					p.getInventory().clear();
					for (Player pls : PlayersManager.getInstance().getPlayers())
						p.teleport(pls.getLocation());

					for (Player pls : PlayersManager.getInstance().getPlayers()) {
						p.teleport(pls);
					}

					if (PlayersManager.getInstance().playerSize() <= 1) {

						GameManager.end();
					}
				}
			}
		}

	}
}
