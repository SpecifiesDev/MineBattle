package me.specifies.core.Events;

import java.io.File;

import org.bukkit.Location;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

import me.specifies.core.MineBattle;

public class TeleportIfDisc implements Listener{
	MineBattle plugin;
	public TeleportIfDisc(MineBattle instance) {
		this.plugin = instance;
	}
	@EventHandler
	public void onJoin(PlayerJoinEvent e) {
		Player p = e.getPlayer();
		if(plugin.disconinarena.contains(p.getName())) {
			File yml = new File(plugin.getDataFolder() + File.separator + "data" + File.separator + "data.yml");
			FileConfiguration ymlconf = YamlConfiguration.loadConfiguration(yml);
			int x = ymlconf.getInt("x1");
			int y = ymlconf.getInt("y1");
			int z = ymlconf.getInt("z1");
			Location loc = new Location(p.getWorld(), x, y, z);
			p.sendMessage(plugin.color(plugin.prefix + " &3You disconnected in a battle, you have been teleported to the exit point."));
			p.teleport(loc);
			plugin.disconinarena.remove(p.getName());
		}
	}

}
