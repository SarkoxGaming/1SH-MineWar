package ca.sarkox.minewar.commands;

import ca.sarkox.minewar.gui.ConfigGUI;
import ca.sarkox.minewar.runnable.StartRunnable;
import ca.sarkox.minewar.utils.ItemBuilder;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class ConfigCommand implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String msg, String[] args) {

        if (commandSender instanceof Player) {
            Player player = (Player) commandSender;
            new ConfigGUI(player);
        }


        return false;
    }
}
