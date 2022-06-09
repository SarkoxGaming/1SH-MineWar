package ca.sarkox.minewar.utils;

import ca.sarkox.minewar.Main;
import org.bukkit.Chunk;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.configuration.MemorySection;
import org.bukkit.entity.Player;
import org.bukkit.util.Vector;
import org.checkerframework.checker.units.qual.A;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

public class IslandsManager {

	private static IslandsManager instance = null;
	
	public static IslandsManager getInstance()
    {
        if (instance == null)
        	instance = new IslandsManager();

        return instance;
    }
	private IslandsManager() {
		this.getIslands();
	}
	
	private ArrayList<Location> islands = new ArrayList<>();
	private ArrayList<Location> fightSpawn = new ArrayList<>();
	
	public void addIsland(Location loc) {
		new FileManager().addLocation(loc, String.format("Islands.%d", this.islandSize())).save();
		this.islands.add(loc);
	}
	public void removeIsland(Integer number) {
		this.islands.remove(number);
	}
	
	public ArrayList<Location> getIslands() {
		this.islands.clear();
		MemorySection obj = (MemorySection) new FileManager().getFileConfig().get("Islands");

		int count = 0;
		Location loc;
		do {
			loc = obj.getLocation("" + count);
			if (loc != null) this.islands.add(loc);
			count++;
		} while (loc != null);

		return this.islands;
	}
	
	public int islandSize() {
		return this.islands.size();
	}

	public boolean setFightSpawn() {
		for (Chunk chunk : Main.getWorld().getLoadedChunks()) {
			int cx = chunk.getX() << 4;
			int cz = chunk.getZ() << 4;
			for (int x = cx; x < cx + 16; x++) {
				for (int z = cz; z < cz + 16; z++) {
					for (int y = 0; y < 128; y++) {
						Block block = Main.getWorld().getBlockAt(x, y, z);
						if (block.getType() == Material.WAXED_OXIDIZED_COPPER) {
							this.fightSpawn.add(block.getLocation().add(new Vector(0.5,2,0.5)));
						}
					}
				}
			}

		}

		Collections.shuffle(this.fightSpawn, new Random());
		return true;
	}

	public List<Location> getFightSpawn() {
		return this.fightSpawn;
	}

	
	
}
