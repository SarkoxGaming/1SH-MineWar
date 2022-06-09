package ca.sarkox.minewar.events;

import org.bukkit.Bukkit;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.PluginManager;

public class EventsManager {

	Plugin pl;

	public EventsManager(Plugin pl) {
		this.pl = pl;
	}

	public void registerEvents() {
		PluginManager pm = Bukkit.getPluginManager();

		pm.registerEvents(new PlayerJoin(), pl);
		pm.registerEvents(new PlayerMove(), pl);
		pm.registerEvents(new BlockListener(), pl);
		pm.registerEvents(new PlayerCraft(), pl);
		pm.registerEvents(new PlayerDie(), pl);
		pm.registerEvents(new PlayerDamage(), pl);
		pm.registerEvents(new EntityDamageByEntity(), pl);
		pm.registerEvents(new PlayerUse(), pl);


	}

}
