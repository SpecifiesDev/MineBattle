package me.specifies.core.Commands;

import java.io.File;
import java.io.IOException;
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

public class ClearStats implements CommandExecutor{
	MineBattle plugin;
	public ClearStats(MineBattle instance) {
		this.plugin = instance;
	}
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		if(cmd.getName().equalsIgnoreCase("mbclearstats")) {
			if(args.length == 1) {
				if(!(sender instanceof Player)) {
					OfflinePlayer target = Bukkit.getOfflinePlayer(args[0]);
					if(!target.hasPlayedBefore()) {
						sender.sendMessage(plugin.color(plugin.prefix + " &cIt appears this player has never played before."));
					} else {
						UUID u = target.getUniqueId();
						File pf = new File(plugin.getDataFolder() + File.separator + "players" + File.separator + u + ".yml");
						FileConfiguration conf = YamlConfiguration.loadConfiguration(pf);
						
						conf.set("Kills", 0);
						conf.set("Deaths", 0);
						try { 
							conf.save(pf);
						} 
						catch(IOException e) {
							e.printStackTrace();
						}
						sender.sendMessage(plugin.color(plugin.prefix + " &cYou sucessfully cleared the stats of &6" + target.getName()));
					}
				} else {
					Player p = (Player) sender;
					if(p.hasPermission("minebattle.stats.clear")) {
						OfflinePlayer target = Bukkit.getOfflinePlayer(args[0]);
						if(!target.hasPlayedBefore()) {
							p.sendMessage(plugin.color(plugin.prefix + " &cIt appears this player has never played before."));
						} else {
							UUID u = target.getUniqueId();
							File pf = new File(plugin.getDataFolder() + File.separator + "players" + File.separator + u + ".yml");
							FileConfiguration conf = YamlConfiguration.loadConfiguration(pf);
							conf.set("Kills", 0);
							conf.set("Deaths", 0);
							try { 
								conf.save(pf);
							} 
							catch(IOException e) {
								e.printStackTrace();
							}
							p.sendMessage(plugin.color(plugin.prefix + " &cYou sucessfully cleared the stats of &6" + target.getName()));
						}
					} else {
						p.sendMessage(plugin.color(plugin.prefix + " &cIt appears you are not permitted to use this command."));
					}
				}
			} else {
				sender.sendMessage(plugin.color(plugin.prefix + " &cUsage&7: &c/mbclearstats <player>"));
			}
		}
		return true;
	}
}
