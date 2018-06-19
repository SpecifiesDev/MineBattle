package me.specifies.core.Commands;


import java.io.File;
import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;

import me.specifies.core.MineBattle;

public class ViewStats implements CommandExecutor{
	MineBattle plugin;
	public ViewStats(MineBattle instance) {
		this.plugin = instance;
	}
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		if(cmd.getName().equalsIgnoreCase("viewstats")) {
			if(!(sender instanceof Player)) {
				sender.sendMessage(plugin.color(plugin.prefix + " &cYou must be a player to use this command"));
			} else {
				Player p = (Player) sender;
				if(p.hasPermission("minebattles.viewstats")) {
				if(args.length == 1) {
					String playername = args[0];
					OfflinePlayer player = Bukkit.getOfflinePlayer(playername);
					Player passparams = null;
					if(!player.hasPlayedBefore()) {
						p.sendMessage(plugin.color(plugin.prefix + " &cIt appears this player has never played before."));
					} else {
						UUID u = player.getUniqueId();
						File pf = new File(plugin.getDataFolder() + File.separator + "players" + File.separator + u + ".yml");
						FileConfiguration conf = YamlConfiguration.loadConfiguration(pf);
						plugin.ba.createStats("&7Stats", 27, p, conf, pf, passparams, true, player);
					}

				} else {
					p.sendMessage(plugin.color(plugin.prefix + " &cUsage&7: &c/viewstats <player>"));
				}//else
				} else {
					p.sendMessage(plugin.color(plugin.prefix + " &cIt appears you do not have access to this command."));
				}
			}
		}
		
		return true;
	}

}
