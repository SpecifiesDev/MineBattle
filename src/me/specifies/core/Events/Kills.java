package me.specifies.core.Events;

import java.io.File;
import java.io.IOException;
import java.util.UUID;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;

import me.specifies.core.MineBattle;

public class Kills implements Listener{
	MineBattle plugin;
	public Kills(MineBattle instance) {
		this.plugin = instance;
	}
	@EventHandler
	public void death(PlayerDeathEvent e) {
		Player kill = e.getEntity().getKiller();
		Player dead = e.getEntity();
		if(plugin.players.contains(kill.getName()) && plugin.players.contains(dead.getName())) {
			UUID ku = kill.getUniqueId();
			UUID du = dead.getUniqueId();
			plugin.players.remove(dead.getName());
			dead.sendMessage(plugin.color(plugin.prefix + " &cYou have died in a battle, rejoin with &b/minebattle enter&7."));
			// Kill File
			File kf = new File(plugin.getDataFolder() + File.separator + "players" + File.separator + ku + ".yml");
			FileConfiguration conf = YamlConfiguration.loadConfiguration(kf);
			int kills = conf.getInt("Kills");
			int killfinal = kills + 1;
			conf.set("Kills", killfinal);
			try {
				conf.save(kf);
			}
			catch(IOException e1) {
				e1.printStackTrace();
			}
			// Dead File
			File df = new File(plugin.getDataFolder() + File.separator + "players" + File.separator + du + ".yml");
			FileConfiguration dconf = YamlConfiguration.loadConfiguration(df);
			int deaths = dconf.getInt("Deaths");
			int deathfinal = deaths + 1;
			dconf.set("Deaths", deathfinal);
			try {
				dconf.save(df);
			}
			catch(IOException e1) {
				e1.printStackTrace();
			}
		}
	}
}
