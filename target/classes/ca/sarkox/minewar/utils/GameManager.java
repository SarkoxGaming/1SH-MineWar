package ca.sarkox.minewar.utils;

import ca.sarkox.minewar.runnable.LoadIsland;
import org.bukkit.Bukkit;
import org.bukkit.Chunk;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import ca.sarkox.minewar.Main;
import ca.sarkox.minewar.events.BlockListener;
import ca.sarkox.minewar.models.CPlayer;
import ca.sarkox.minewar.runnable.EndRunnable;
import ca.sarkox.minewar.runnable.PvPRunnable;
import org.bukkit.util.Vector;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

public class GameManager {




	public static void start() {

		GameState.setState(GameState.STUFFING);
		PlayersManager.getInstance().clear();

		ArrayList<Location> islands = IslandsManager.getInstance().getIslands();
		Collections.shuffle(islands, new Random());
		for (Player pls : Bukkit.getOnlinePlayers()) {
			CPlayer player = new CPlayer(pls);

			PlayersManager.getInstance().addPlayer(player.getPlayer());
			
			player.reset();

			//teleport
			Location loc = islands.get(0);
			islands.remove(loc);
			player.getPlayer().teleport(loc);

		}
		
		new PvPRunnable().run();
		Main.getWorld().setPVP(false);
		new LoadIsland().run();
	}

	@SuppressWarnings("deprecation")
	public static void end() {
		
		GameState.setState(GameState.LOBBY);

		Player player = PlayersManager.getInstance().getPlayers().get(0);
		TextUtil.getInstance().broadcast(String.format("§c%s §6a gagné la partie !", player.getName()));
		
			
		for (Player pls : Bukkit.getOnlinePlayers()) {
			CPlayer cpls = new CPlayer(pls);
			cpls.getPlayer().teleport(player.getLocation());
			cpls.reset();
		}

		BlockListener.reset();

		Main.getWorld().setPVP(false);
		new EndRunnable().run();

	}

	public static void pvp() {

		GameState.setState(GameState.FIGHTING);

		List<Location> fightSpawn = IslandsManager.getInstance().getFightSpawn();
		for (Player player : PlayersManager.getInstance().getPlayers()) {

			player.teleport(fightSpawn.get(0));
			fightSpawn.remove(fightSpawn.get(0));
		}

		
		Main.getWorld().setPVP(true);

	}

	public static void lobbyconnect() {

		

	}

}
