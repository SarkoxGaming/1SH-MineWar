package ca.sarkox.minewar.utils;

import org.bukkit.Bukkit;
import org.bukkit.Sound;
import org.bukkit.SoundCategory;
import org.bukkit.entity.Player;

import net.md_5.bungee.api.ChatMessageType;
import net.md_5.bungee.api.chat.TextComponent;


public class TextUtil {

private static TextUtil instance = null;
	
	public static TextUtil getInstance()
    {
        if (instance == null)
        	instance = new TextUtil();

        return instance;
    }
	
	public static String prefix = "§f[§bMine§6War§f]";
	
	private String subMessage(String message) {
		return String.format("%s ? %s", this.prefix, message);
	}
	
	public TextUtil broadcast(String message) {
		Bukkit.broadcastMessage(this.subMessage(message));
		return this;
	}
	
	public TextUtil broadcastAction(String message) {
		for (Player player : Bukkit.getOnlinePlayers()) {
			player.spigot().sendMessage(ChatMessageType.ACTION_BAR, new TextComponent(message));
		}
		return this;
	}
	
	public void toPlayer(Player player, String message) {
		player.sendRawMessage(this.subMessage(message));
	}
	
	public String getJoinMessage(Player player) {
		return this.subMessage(String.format("§c%s§6 à rejoint le MineWar!", player.getName()));
	}
	
	public void alertAll() {
		for (Player player : Bukkit.getOnlinePlayers()) {
			player.playSound(player.getLocation(), Sound.BLOCK_NOTE_BLOCK_BELL, SoundCategory.MUSIC, 10, 10);
		}
	}
	
}
