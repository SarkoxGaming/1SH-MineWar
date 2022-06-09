package ca.sarkox.minewar.runnable;

import ca.sarkox.minewar.utils.FileManager;
import org.bukkit.scheduler.BukkitRunnable;

import ca.sarkox.minewar.Main;
import ca.sarkox.minewar.utils.GameManager;
import ca.sarkox.minewar.utils.TextUtil;



public class PvPRunnable extends BukkitRunnable {

	
	public static int timer = 20;

	public void run() {
		timer = (Integer) new FileManager("config.yml").getFileConfig().get("time.stuffing")+1;
		new BukkitRunnable() {

			@Override
			public void run() {
				timer--;

				if (timer % 60 == 0 && timer != 60)
					TextUtil.getInstance().broadcast(String.format("§aLe PVP va commencer dans §f%d§a minutes!", (timer / 60))).alertAll();
				
				
				if (timer == 60)
					TextUtil.getInstance().broadcast("§aLe PVP va commencer dans §f1§a minute!").alertAll();
				
				if (timer == 10 || timer == 3 || timer == 2)
					TextUtil.getInstance().broadcast(String.format("§aLe PVP va commencer dans §f%d§a secondes!", timer)).alertAll();

				if (timer == 1)
					TextUtil.getInstance().broadcast(String.format("§aLe PVP va commencer dans §f%d§a seconde!", timer)).alertAll();

				if (timer == 0) {
					cancel();

					GameManager.pvp();
				}
			}
		}.runTaskTimer(Main.getPlugin(), 20L, 20L);
	}
}
