package ca.sarkox.minewar.events;

import ca.sarkox.minewar.utils.FileManager;
import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.attribute.Attribute;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

import ca.sarkox.minewar.models.CPlayer;
import ca.sarkox.minewar.runnable.StartRunnable;
import ca.sarkox.minewar.utils.GameState;
import ca.sarkox.minewar.utils.TextUtil;


public class PlayerJoin implements Listener {
	
	@EventHandler
	public void onJoin(PlayerJoinEvent e) {
	
		
		
		CPlayer player = new CPlayer(e.getPlayer());

		
		player.getPlayer().getAttribute(Attribute.GENERIC_ATTACK_SPEED).setBaseValue(16);

		if (GameState.isState(GameState.LOBBY)) {

			e.setJoinMessage(TextUtil.getInstance().getJoinMessage(player.getPlayer()));

			player.getPlayer().teleport((Location) new FileManager().getFileConfig().get("Spawn"));

			player.reset();
			
			if (Bukkit.getOnlinePlayers().size() >= 4 && StartRunnable.timer == 11)
				new StartRunnable().run();
		} else {

			player.setSpectator();

		}
		
	}
}
