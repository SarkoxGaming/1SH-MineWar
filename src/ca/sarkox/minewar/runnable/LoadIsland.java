package ca.sarkox.minewar.runnable;

import ca.sarkox.minewar.Main;
import ca.sarkox.minewar.utils.IslandsManager;
import org.bukkit.scheduler.BukkitRunnable;

public class LoadIsland extends BukkitRunnable {



    public void run() {
        new BukkitRunnable() {

            @Override
            public void run() {

                boolean end = IslandsManager.getInstance().setFightSpawn();
                if (end){
                    cancel();
                }

            }
        }.runTask(Main.getPlugin());
    }
}