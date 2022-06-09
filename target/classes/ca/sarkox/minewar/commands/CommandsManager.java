package ca.sarkox.minewar.commands;

import ca.sarkox.minewar.Main;

public class CommandsManager {

    Main pl;

    public CommandsManager(Main pl) {
        this.pl = pl;
    }

    public void registerCommands() {

        this.pl.getCommand("forcestart").setExecutor(new StartCommand());
        this.pl.getCommand("forcestart").setTabCompleter(new StartCommandCompletor());

        this.pl.getCommand("config").setExecutor(new ConfigCommand());


    }
}
