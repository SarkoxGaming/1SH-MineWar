package ca.sarkox.minewar.runnable;

import org.bukkit.scheduler.BukkitRunnable;

import ca.sarkox.minewar.Main;
import ca.sarkox.minewar.utils.GameManager;
import ca.sarkox.minewar.utils.TextUtil;

public class EndRunnable extends BukkitRunnable {

	
	public static int timer = 6;

	public void run() {
		new BukkitRunnable() {

			@Override
			public void run() {
				timer--;

				if (timer == 10 || timer == 3 || timer == 2)

					TextUtil.getInstance().broadcastAction(String.format("§aVous allez être téléporté au lobby dans §f%d§a secondes!", (timer / 60))).alertAll();
				
			
				if (timer == 1)
					TextUtil.getInstance().broadcastAction(String.format("§aVous allez être téléporté au lobby dans §f%d§a secondes!", (timer / 60))).alertAll();

				if (timer == 0) {
					cancel();

					GameManager.lobbyconnect();
				}
			}
		}.runTaskTimer(Main.getPlugin(), 20L, 20L);
	}

}
