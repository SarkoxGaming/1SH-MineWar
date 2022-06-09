package ca.sarkox.minewar.events;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerQuitEvent;

import ca.sarkox.minewar.utils.GameManager;
import ca.sarkox.minewar.utils.GameState;
import ca.sarkox.minewar.utils.PlayersManager;


public class PlayerQuit implements Listener {
	
	@EventHandler
	public void PlayerQuitEvent(PlayerQuitEvent e) {
		e.setQuitMessage(null);
		Player player = e.getPlayer();
		
		PlayersManager.getInstance().removePlayer(player);

		if (!GameState.isState(GameState.LOBBY)) {
			if (PlayersManager.getInstance().playerSize() == 1) {
				GameManager.end();
			}
		}
	}

}
