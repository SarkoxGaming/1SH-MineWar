package ca.sarkox.minewar.runnable;

import org.bukkit.scheduler.BukkitRunnable;

import ca.sarkox.minewar.Main;
import ca.sarkox.minewar.utils.GameManager;
import ca.sarkox.minewar.utils.TextUtil;



public class StartRunnable extends BukkitRunnable {
	
	public static int timer = 11;

	public void run() {
		new BukkitRunnable() {

			@Override
			public void run() {
				timer--;

				if (timer != 1)
					TextUtil.getInstance().broadcastAction(String.format("§aLa partie va commencer dans §f%d§a secondes!", timer));

				if (timer == 1)
					TextUtil.getInstance().broadcastAction(String.format("§aLa partie va commencer dans §f%d§a seconde!", timer));

				if (timer == 0) {
					cancel();

					GameManager.start();

				}
			}
		}.runTaskTimer(Main.getPlugin(), 20L, 20L);
	}
}
