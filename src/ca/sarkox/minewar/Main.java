package ca.sarkox.minewar;

import ca.sarkox.minewar.commands.CommandsManager;
import ca.sarkox.minewar.events.BlockListener;
import ca.sarkox.minewar.events.EventsManager;
import ca.sarkox.minewar.utils.FileManager;
import ca.sarkox.minewar.utils.GameState;
import org.bukkit.Bukkit;
import org.bukkit.World;
import org.bukkit.event.Listener;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;

public class Main extends JavaPlugin implements Listener {


	@Override
	public void onEnable() {

		new EventsManager(this).registerEvents();
        new CommandsManager(this).registerCommands();

		FileManager.configTime();

		// GameState
		GameState.setState(GameState.LOBBY);

	}

	@Override
	public void onDisable() {
		BlockListener.reset();
	}
	
	
	public static Plugin getPlugin() {
		return Bukkit.getPluginManager().getPlugin("MineWar");
	}
	
	public static World getWorld() {
		return Bukkit.getWorlds().iterator().next();
	}
	
	
}
