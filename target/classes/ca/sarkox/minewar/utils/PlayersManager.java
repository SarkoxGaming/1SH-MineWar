package ca.sarkox.minewar.utils;

import java.util.ArrayList;

import org.bukkit.entity.Player;

public class PlayersManager {

	private static PlayersManager instance = null;
	
	public static PlayersManager getInstance()
    {
        if (instance == null)
        	instance = new PlayersManager();

        return instance;
    }
	
	private ArrayList<Player> players = new ArrayList<>();
	
	
	public void addPlayer(Player player) {
		this.players.add(player);
	}
	public void removePlayer(Player player) {
		this.players.remove(player);
	}
	
	public ArrayList<Player> getPlayers() {
		return this.players;
	}
	
	public boolean hasPlayer(Player player) { 
		return this.players.contains(player);
	}
	
	public int playerSize() {
		return this.players.size();
	}

	public void clear() {
		this.players.clear();
	}

	
	
}
