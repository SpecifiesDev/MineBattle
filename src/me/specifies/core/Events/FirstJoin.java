package me.specifies.core.Events;

import java.io.File;
import java.io.IOException;
import java.util.UUID;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

import me.specifies.core.MineBattle;

public class FirstJoin implements Listener{
	MineBattle plugin;
	public FirstJoin(MineBattle instance) { 
		this.plugin = instance;
	}
	@EventHandler
	public void onJoin(PlayerJoinEvent e) {
		Player p = e.getPlayer();
		UUID u = p.getUniqueId();
		File folder = new File(plugin.getDataFolder()+File.separator+"players");
		if(!folder.exists()) folder.mkdirs();
		File pf = new File(plugin.getDataFolder() + File.separator + "players" + File.separator + u + ".yml");
		FileConfiguration conf = YamlConfiguration.loadConfiguration(pf);
		if(!pf.exists()) {
			conf.set("Kills", 0);
			conf.set("Deaths", 0);
			try {
				conf.save(pf);
			} catch (IOException e1) {
				e1.printStackTrace();
			}
		}
	}
}
