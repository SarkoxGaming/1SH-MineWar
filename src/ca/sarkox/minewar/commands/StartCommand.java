package ca.sarkox.minewar.commands;

import ca.sarkox.minewar.runnable.StartRunnable;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

public class StartCommand implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String msg, String[] args) {

        if (args.length == 0) {
            StartRunnable.timer = 6;
            new StartRunnable().run();
            return true;
        }

        if (args.length == 1) {
            StartRunnable.timer = Integer.parseInt(args[0]);
            new StartRunnable().run();
            return true;
        }


        return false;
    }
}
