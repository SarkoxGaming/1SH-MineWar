package ca.sarkox.minewar.gui;

import ca.sarkox.minewar.utils.*;
import org.bukkit.Material;
import org.bukkit.entity.Player;

public class TimeGUI extends GUI {
    public TimeGUI(Player player) {
        super(player, 45, TextUtil.prefix, "Temps");
    }

    int struffingTime;

    @Override
    public void onOpen(Player player) {
        this.struffingTime = (Integer) new FileManager("config.yml").getFileConfig().get("time.stuffing");
        borderFill(new ItemBuilder(Material.WHITE_STAINED_GLASS_PANE).setName(" ").toItemStack());

        set(2,2, new ItemBuilder(Material.PLAYER_HEAD).setSkullFromBase64(SkullTexture.BLUE_MINUS.hexa).setName("§bEnlever 10 secondes").toItemStack(), event -> {
            this.updateTime(-10);
        });

        set(1,2, new ItemBuilder(Material.PLAYER_HEAD).setSkullFromBase64(SkullTexture.RED_MINUS.hexa).setName("§cEnlever 1 minutes").toItemStack(), event -> {
            this.updateTime(-60);
        });
        set(6,2, new ItemBuilder(Material.PLAYER_HEAD).setSkullFromBase64(SkullTexture.BLUE_ADD.hexa).setName("§bAjouter 10 secondes").toItemStack(), event -> {
            this.updateTime(10);
        });
        set(7,2, new ItemBuilder(Material.PLAYER_HEAD).setSkullFromBase64(SkullTexture.RED_ADD.hexa).setName("§bAjouter 1 minutes").toItemStack(), event -> {
            this.updateTime(60);
        });

        set(4,2, new ItemBuilder(Material.DIAMOND).setName("§bTemps actuel: §6" + formatSeconds(this.struffingTime)).toItemStack(), null);

        //back
        set(4,3,new ItemBuilder(Material.PLAYER_HEAD).setSkullFromBase64(SkullTexture.RED_ARROW_LEFT.hexa).setName("§cRetour").toItemStack(), event ->  {
            new FileManager("config.yml").addData(this.struffingTime, "time.stuffing").save();
            new ConfigGUI(player);

        });
    }

    @Override
    public void onClose(Player player) {
        new FileManager("config.yml").addData(this.struffingTime, "time.stuffing").save();

    }

    private String formatSeconds(int timeInSeconds)
    {
        int secondsLeft = timeInSeconds % 3600 % 60;
        int minutes = (int) Math.floor(timeInSeconds % 3600 / 60);

        String MM =  "" +minutes;
        String SS = ((secondsLeft < 10) ? "0" : "") + secondsLeft;

        return MM + "m" + SS+"s";
    }

    private void updateTime(int seconde) {
        this.struffingTime += seconde;
        set(4,2, new ItemBuilder(Material.DIAMOND).setName("§bTemps actuel: §6" + formatSeconds(this.struffingTime)).toItemStack(), null);
    }
}
