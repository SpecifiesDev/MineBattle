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

public class EditStats implements CommandExecutor{
	MineBattle plugin;
	public EditStats(MineBattle instance) {
		this.plugin = instance;
	}
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		if(cmd.getName().equalsIgnoreCase("mbeditstats")) {
			if(args.length == 4) {
				if(!(sender instanceof Player)) {
					String type = args[0];
					String data = args[1];
					OfflinePlayer target = Bukkit.getOfflinePlayer(args[2]);
					int amount = Integer.parseInt(args[3]);
					if(!target.hasPlayedBefore()) {
						sender.sendMessage(plugin.color(plugin.prefix + " &cIt appears that this player has never played before."));
					} else {
					// pull target files
					UUID u = target.getUniqueId();
					File pf = new File(plugin.getDataFolder() + File.separator + "players" + File.separator + u + ".yml");
					FileConfiguration conf = YamlConfiguration.loadConfiguration(pf);
					
					//different params
					int kills = conf.getInt("Kills");
					int deaths = conf.getInt("Deaths");
					
					if(type.equalsIgnoreCase("add")) {
						if(data.equalsIgnoreCase("kills")) {
							int finalkills = kills + amount;
							conf.set("Kills", finalkills);
							try {
								conf.save(pf);
							}
							catch(IOException e) {
								e.printStackTrace();
							}
							sender.sendMessage(plugin.color(plugin.prefix + " &cUpdated the stats of &6" + target.getName()));
						}// kills
						else if(data.equalsIgnoreCase("deaths")) {
							int finaldeaths = deaths + amount;
							conf.set("Deaths", finaldeaths);
							try {
								conf.save(pf);
							}
							catch(IOException e) {
								e.printStackTrace();
							}
							sender.sendMessage(plugin.color(plugin.prefix + " &cUpdated the stats of &6" + target.getName()));
						}//deaths
						else {
							sender.sendMessage(plugin.color(plugin.prefix + " &cInvalid data type."));
						}
					}
					if(type.equalsIgnoreCase("set")) {
						if(data.equalsIgnoreCase("kills")) {
							conf.set("Kills", amount);
							try {
								conf.save(pf);
							} 
							catch(IOException e) {
								e.printStackTrace();
							}
							sender.sendMessage(plugin.color(plugin.prefix + " &cUpdated the stats of &6" + target.getName()));
						}//kills
						else if(data.equalsIgnoreCase("deaths")) {
							conf.set("Deaths", amount);
							try {
								conf.save(pf);
							} catch (IOException e) {
								e.printStackTrace();
							}
						}//deaths
						else {
							sender.sendMessage(plugin.color(plugin.prefix + " &cInvalid data type."));
						}
					}
					if(type.equalsIgnoreCase("subtract")) {
						if(data.equalsIgnoreCase("kills")) {
							int finalkills = kills - amount;
							conf.set("Kills", finalkills);
							try {
								conf.save(pf);
							}
							catch(IOException e) {
								e.printStackTrace();
							}
							sender.sendMessage(plugin.color(plugin.prefix + " &cUpdated the stats of &6" + target.getName()));
						}// kills
						else if(data.equalsIgnoreCase("deaths")) {
							int finaldeaths = deaths - amount;
							conf.set("Deaths", finaldeaths);
							try {
								conf.save(pf);
							}
							catch(IOException e) {
								e.printStackTrace();
							}
							sender.sendMessage(plugin.color(plugin.prefix + " &cUpdated the stats of &6" + target.getName()));
						}//deaths 
						else {
							sender.sendMessage(plugin.color(plugin.prefix + " &cInvalid data type."));
						}
					}
					
					}
				}// console
				else {
					Player p = (Player) sender;
					String type = args[0];
					String data = args[1];
					OfflinePlayer target = Bukkit.getOfflinePlayer(args[2]);
					int amount = Integer.parseInt(args[3]);
					if(!target.hasPlayedBefore()) {
						sender.sendMessage(plugin.color(plugin.prefix + " &cIt appears that this player has never played before."));
					} else {
						// pull target files
						UUID u = target.getUniqueId();
						File pf = new File(plugin.getDataFolder() + File.separator + "players" + File.separator + u + ".yml");
						FileConfiguration conf = YamlConfiguration.loadConfiguration(pf);
						
						//different params
						int kills = conf.getInt("Kills");
						int deaths = conf.getInt("Deaths");
						
						if(type.equalsIgnoreCase("add")) {
							if(p.hasPermission("minebattles.editstats.add")) {
								if(data.equalsIgnoreCase("kills")) {
									int finalkills = kills + amount;
									conf.set("Kills", finalkills);
									try {
										conf.save(pf);
									} catch (IOException e) {
										e.printStackTrace();
									}
									p.sendMessage(plugin.color(plugin.prefix + " &cUpdated the stats of &6" + p.getName()));
								} 
								else if(data.equalsIgnoreCase("deaths")) {
									int finaldeaths = deaths + amount;
									conf.set("Deaths", finaldeaths);
									try {
										conf.save(pf);
									} catch (IOException e) {
										e.printStackTrace();
									}
									p.sendMessage(plugin.color(plugin.prefix + " &cUpdated the stats of &6" + p.getName()));
								}else {
									p.sendMessage(plugin.color(plugin.prefix + " &cInvalid data type."));
								}
							} else {
								p.sendMessage(plugin.color(plugin.prefix + " &cYou do not have permission to add stats to a player."));
							}
						}//add
						if(type.equalsIgnoreCase("subtract")) {
							if(p.hasPermission("minebattles.editstats.subtract")) {
								if(data.equalsIgnoreCase("kills")) {
									int finalkills = kills - amount;
									conf.set("Kills", finalkills);
									try {
										conf.save(pf);
									} catch (IOException e) {
										e.printStackTrace();
									}
									p.sendMessage(plugin.color(plugin.prefix + " &cUpdated the stats of &6" + p.getName()));
								} 
								else if(data.equalsIgnoreCase("deaths")) {
									int finaldeaths = deaths - amount;
									conf.set("Deaths", finaldeaths);
									try {
										conf.save(pf);
									} catch (IOException e) {
										e.printStackTrace();
									}
									p.sendMessage(plugin.color(plugin.prefix + " &cUpdated the stats of &6" + p.getName()));
								}else {
									p.sendMessage(plugin.color(plugin.prefix + " &cInvalid data type."));
								}
							} else {
								p.sendMessage(plugin.color(plugin.prefix + " &cYou do not have permission to add stats to a player."));
							}
						}//subtract
						if(type.equalsIgnoreCase("set")) {
							if(p.hasPermission("minebattles.editstats.set")) {
								if(data.equalsIgnoreCase("kills")) {
									conf.set("Kills", amount);
									try {
										conf.save(pf);
									} catch (IOException e) {
										e.printStackTrace();
									}
									p.sendMessage(plugin.color(plugin.prefix + " &cUpdated the stats of &6" + p.getName()));
								} 
								else if(data.equalsIgnoreCase("deaths")) {
									conf.set("Deaths", amount);
									try {
										conf.save(pf);
									} catch (IOException e) {
										e.printStackTrace();
									}
									p.sendMessage(plugin.color(plugin.prefix + " &cUpdated the stats of &6" + p.getName()));
								} else {
									p.sendMessage(plugin.color(plugin.prefix + " &cInvalid data type."));
								}
							} else {
								p.sendMessage(plugin.color(plugin.prefix + " &cYou do not have permission to add stats to a player."));
							}
						}//set
					}
				}//player
			}// args 
			else {
				sender.sendMessage(plugin.color(plugin.prefix + " &cUsage&7: &c/mbeditstats <add/set/subtract> <kills/deaths> <player> <amount>"));
			}//args esle
		}
		return true;
	}
}
