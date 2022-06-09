package ca.sarkox.minewar.utils;

import java.io.File;

import ca.sarkox.minewar.Main;
import org.bukkit.Location;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;


public class FileManager {

	private File file;
	private FileConfiguration getFileConfiguration(){
		return YamlConfiguration.loadConfiguration(this.file);
	}
	private FileConfiguration cfg;
	
	public FileManager() {
		this.file = new File(Main.getPlugin().getDataFolder(), "Locations.yml");
		this.cfg = getFileConfiguration();
	}

	public FileManager(String filename) {
		this.file = new File(Main.getPlugin().getDataFolder(), filename);
		this.cfg = getFileConfiguration();
	}

	public FileConfiguration getFileConfig() {
		return this.cfg;
	}

	public void save() {
		try {
			this.cfg.save(this.file);
		} catch(Exception e){
			e.printStackTrace();
		}
	}

	public FileManager addLocation(Location loc, String type){

		this.cfg.set(type, loc);
		return this;
	}

	public FileManager addData(Object data, String type) {
		this.cfg.set(type, data);
		return this;
	}

	public static void configTime() {
		FileManager fm = new FileManager("config.yml");
		if (fm.getFileConfig().get("time") == null) {
			fm.addData(300, "time.stuffing").save();
		}
	}
	
	
}
