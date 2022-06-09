package ca.sarkox.minewar.gui;

import ca.sarkox.minewar.utils.*;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.configuration.MemorySection;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.SkullMeta;
import org.checkerframework.checker.units.qual.A;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.HashMap;

public class IslandGUI extends GUI {

    public IslandGUI(Player player) {

        super(player, 54, TextUtil.prefix, "Îles");
    }

    @Override
    public void onOpen(Player player) {

        //Fill Border Glass
        borderFill(new ItemBuilder(Material.WHITE_STAINED_GLASS_PANE).setName(" ").toItemStack());

        //Add item

        set(10,new ItemBuilder(Material.PLAYER_HEAD).setSkullFromBase64(SkullTexture.GREEN_ADD.hexa).setName("§aAjouter").toItemStack(), event -> {
            IslandsManager.getInstance().addIsland(player.getLocation());
            TextUtil.getInstance().toPlayer(player,"Ile ajoutée");
        });

        //Fill island
        ArrayList<Location> island = IslandsManager.getInstance().getIslands();
        int row = 1;
        int col = 2;
        for (Location loc : island) {
            if (col % 8 == 0) {
                row++;
                col -= 7;
            }
            set(col,row,new ItemBuilder(Material.OAK_SAPLING).setName("§aIsland " + (island.indexOf(loc)+1))
                    .setLore("§eAppuyez pour te teleporter à l'île!")
                    .toItemStack()
                    , event -> {
                player.teleport(loc);
            });
            col ++;
        }

        //back
        set(43,new ItemBuilder(Material.PLAYER_HEAD).setSkullFromBase64(SkullTexture.RED_ARROW_LEFT.hexa).setName("§cRetour").toItemStack(), event ->   new ConfigGUI(player));
    }

    @Override
    public void onClose(Player player) {

    }
}
