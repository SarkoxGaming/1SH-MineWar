package ca.sarkox.minewar.gui;

import ca.sarkox.minewar.Main;
import ca.sarkox.minewar.utils.*;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryAction;

import java.util.Arrays;
import java.util.List;


public class ConfigGUI extends GUI {

    public ConfigGUI(Player player) {
        super(player, 27, TextUtil.prefix, "Configuration");
    }

    @Override
    public void onOpen(Player player) {

        Location spawn = (Location) new FileManager().getFileConfig().get("Spawn");

        List<String> spawnLore = (spawn == null)?  Arrays.asList("�eClick Gauche:�f Teleporter � la salle d'attente", "�eClick Droit:�f D�finir la position de la salle d'attente") : Arrays.asList("�eClick Gauche:�f Teleporter � la salle d'attente", "�eClick Droit:�f D�finir la position de la salle d'attente","�6Position Actuelle:", "x: " + spawn.getBlockX(), "y: " +spawn.getBlockY(), "z: " + spawn.getBlockZ());
        set(11, new ItemBuilder(Material.OAK_DOOR).setName("�aD�finir la position de la salle d'attente").setLore(spawnLore).toItemStack(), event -> {
            if (event.getAction() == InventoryAction.PICKUP_HALF) {
                new FileManager().addLocation(player.getLocation(), "Spawn").save();
                TextUtil.getInstance().toPlayer(player, "Spawn d�finit");
                Main.getWorld().setSpawnLocation(player.getLocation());
            }
            if (event.getAction() == InventoryAction.PICKUP_ALL) {
                if (spawn != null) {
                    player.teleport(spawn);
                }
            }
            player.closeInventory();
        });

        int stuffingTime = (Integer) new FileManager("config.yml").getFileConfig().get("time.stuffing");
        set(13,new ItemBuilder(Material.DIAMOND_CHESTPLATE).setName("�eTemps de pr�paration: �6" + formatSeconds(stuffingTime)).toItemStack(), event -> {
            new TimeGUI(player);
        });


        set(15,new ItemBuilder(Material.OAK_SAPLING).setName(String.format("�aConfiguration des �les (�f%d�a)",IslandsManager.getInstance().islandSize())).toItemStack(), event -> {
            new IslandGUI(player);
        });


        borderFill(new ItemBuilder(Material.WHITE_STAINED_GLASS_PANE).setName(" ").toItemStack());
    }

    @Override
    public void onClose(Player player) {

    }


    private String formatSeconds(int timeInSeconds)
    {
        int secondsLeft = timeInSeconds % 3600 % 60;
        int minutes = (int) Math.floor(timeInSeconds % 3600 / 60);

        String MM =  "" +minutes;
        String SS = ((secondsLeft < 10) ? "0" : "") + secondsLeft;

        return MM + "m" + SS+"s";
    }

}
