package me.specifies.core.Commands;

import java.io.File;
import java.io.IOException;
import java.util.Iterator;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;

import me.specifies.core.MineBattle;

public class Battle implements CommandExecutor{
	MineBattle plugin;
	public Battle(MineBattle instance) {
		this.plugin = instance;
	}
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		if(cmd.getName().equalsIgnoreCase("minebattle")) {
			if(!(sender instanceof Player)) {
				sender.sendMessage(plugin.color(plugin.prefix + " &cYou must be a player to use this command."));
			} else {
				Player p = (Player) sender;
				if(args.length >= 1) {
					if(args[0].equalsIgnoreCase("enter")) {
						if(p.hasPermission("minebattles.enter")) {
						if(plugin.players.contains(p.getName())) {
							p.sendMessage(plugin.color(plugin.prefix + " &cIt appears that you're already in a battle."));
							
						}
						else if(plugin.started == true) {
							
							File yml = new File(plugin.getDataFolder() + File.separator + "data" + File.separator + "data.yml");
							FileConfiguration ymlconf = YamlConfiguration.loadConfiguration(yml);
							
							int x = ymlconf.getInt("x");
							int y = ymlconf.getInt("y");
							int z = ymlconf.getInt("z");
							
							plugin.players.add(p.getName());
							p.sendMessage(plugin.color(plugin.prefix + " &7You have entered a MineBattle. Type &b/minebattle leave &7in order to leave the arena."));
							Location loc = new Location(p.getWorld(), x, y, z);
							p.teleport(loc);
						} else {
							int time = plugin.time;
							int days = time / 86400;
							int hours = (time % 86400) / 3600;
							int mins = ((time % 86400) % 3600) / 60;
							int secs = ((time % 86400) %3600) % 60;
							String day = plugin.convert(days);
							String hour = plugin.convert(hours);
							String min = plugin.convert(mins);
							String sec = plugin.convert(secs);
							String dayplural = pluralornot("days", "day", days);
							String hourplural = pluralornot("hours", "hour", hours);
							String minplural = pluralornot("minutes", "minute", mins);
							String secplural = pluralornot("seconds", "second", secs);
 							p.sendMessage(plugin.color(plugin.prefix + " &3A MineBattle will start in &6" + day + " &3" + dayplural + " &6" + hour + " &3" + hourplural + " &6" + min + " &3" + minplural + " and &6" + sec + " &3" + secplural + "&7."));
						}
					} else {
						p.sendMessage(plugin.color(plugin.prefix + " &cIt appears that you are not permitted to use this commmand."));
					}
					}//end of enter
					if(args[0].equalsIgnoreCase("time")) {
						if(p.hasPermission("minebattles.time")) {
							// We're going to go on the presumption that a battle won't last over an hour.
							if(plugin.started == true) {
							int timeleft = plugin.last;
							int mins = ((timeleft % 86400) %3600) /60;
							int seconds = ((timeleft % 86400) %3600) % 60;
							String min = plugin.convert(mins);
							String sec = plugin.convert(seconds);
							String minplural = pluralornot("minutes", "minute", mins);
							String secplural = pluralornot("seconds", "second", seconds);
							p.sendMessage(plugin.color(plugin.prefix + " &3The current battle has &6" + min + " &3" + minplural + " &3and &6" + sec + " &3" + secplural + " left&7."));
							} else {
								p.sendMessage(plugin.color(plugin.prefix + " &cIt appears there isn't an active battle occuring, try again later."));
							}
						} else {
							p.sendMessage(plugin.color(plugin.prefix + " &cIt appears that you are not permitted to use this commmand."));
						}
					}//time
					if(args[0].equalsIgnoreCase("setendpoint")) {
						if(p.hasPermission("minebattles.setendpoint")) {
							File yml = new File(plugin.getDataFolder() + File.separator + "data" + File.separator + "data.yml");
							FileConfiguration ymlconf = YamlConfiguration.loadConfiguration(yml);
							int x = p.getLocation().getBlockX();
							int y = p.getLocation().getBlockY();
							int z = p.getLocation().getBlockZ();
							ymlconf.set("x1", x);
							ymlconf.set("y1", y);
							ymlconf.set("z1", z);
							try {
								ymlconf.save(yml);
							}
							catch(IOException e) {
								e.printStackTrace();
							}
							p.sendMessage(plugin.color(plugin.prefix + " &3You have set the MineBattle exit at X&8: &6" + plugin.convert(x) + " &3Y&8: &6" + plugin.convert(y) + " &3Z&8: &6" + plugin.convert(z)));
						} else {
							p.sendMessage(plugin.color(plugin.prefix + " &cIt appears that you are not permitted to use this commmand."));
						}
					}//setend
					if(args[0].equalsIgnoreCase("leave")) {
						if(p.hasPermission("minebattles.leave")) {
							if(plugin.players.contains(p.getName())) {
								File yml = new File(plugin.getDataFolder() + File.separator + "data" + File.separator + "data.yml");
								FileConfiguration ymlconf = YamlConfiguration.loadConfiguration(yml);
								int x = ymlconf.getInt("x1");
								int y = ymlconf.getInt("y1");
								int z = ymlconf.getInt("z1");
								Location loc = new Location(p.getWorld(), x, y, z);
								plugin.players.remove(p.getName());
								p.teleport(loc);
								p.sendMessage(plugin.color(plugin.prefix + " &7You have left the battle. You may re-enter as long as it is still open."));
							} else {
								p.sendMessage(plugin.color(plugin.prefix + " &cIt appears that you are not currently in a battle."));
							}
						} else {
							p.sendMessage(plugin.color(plugin.prefix + " &cIt appears that you are not permitted to use this commmand."));
						}
					}//leave
					if(args[0].equalsIgnoreCase("start")) {
						if(p.hasPermission("minebattles.start")) {
							if(plugin.started == true) {
								p.sendMessage(plugin.color(plugin.prefix + " &cIt appears that there is a battle already in progress."));
							} 
							if(plugin.started == false) {
								p.sendMessage(plugin.color(plugin.prefix + " &aYou have started a battle."));
								plugin.started = true;
								plugin.startedman = true;
								Bukkit.getServer().broadcastMessage(plugin.color(plugin.prefix + " &3A battle has been manually started by &6" + p.getName() + " &b/minebattle enter &3 to join."));
								plugin.startEnd();
							}
						}
					}//start
					if(args[0].equalsIgnoreCase("leavesilent")) {
						File yml = new File(plugin.getDataFolder() + File.separator + "data" + File.separator + "data.yml");
						FileConfiguration ymlconf = YamlConfiguration.loadConfiguration(yml);
						int x = ymlconf.getInt("x1");
						int y = ymlconf.getInt("y1");
						int z = ymlconf.getInt("z1");
						Location loc = new Location(p.getWorld(), x, y, z);
						p.teleport(loc);
					}
					if(args[0].equalsIgnoreCase("setspawn")) {
						if(p.hasPermission("minebattles.setspawnpoint")) {
							File yml = new File(plugin.getDataFolder() + File.separator + "data" + File.separator + "data.yml");
							FileConfiguration ymlconf = YamlConfiguration.loadConfiguration(yml);
							int x = p.getLocation().getBlockX();
							int y = p.getLocation().getBlockY();
							int z = p.getLocation().getBlockZ();
							ymlconf.set("x", x);
							ymlconf.set("y", y);
							ymlconf.set("z", z);;
							try {
								ymlconf.save(yml);
							} 
							catch(IOException e) {
								e.printStackTrace();
							}
							p.sendMessage(plugin.color(plugin.prefix + " &3You have set the MineBattle spawn at X&8: &6" + plugin.convert(x) + " &3Y&8: &6" + plugin.convert(y) + " &3Z&8: &6" + plugin.convert(z)));
						} else {
							p.sendMessage(plugin.color(plugin.prefix + " &cIt appears that you are not permitted to use this commmand."));
						}
					}//setspawn
				} else {
					p.sendMessage(plugin.color(plugin.prefix + " &cInvalid args."));
				}
			}
		}
		return true;
	}
	public String pluralornot(String m, String m1, int amount) {
		if(amount > 1 || amount == 0) {
			return m;
		} else {
			return m1;
		}
	}
	public void endGame() {
		if(plugin.players.isEmpty()) {
			
		} else {
			File yml = new File(plugin.getDataFolder() + File.separator + "data" + File.separator + "data.yml");
			FileConfiguration ymlconf = YamlConfiguration.loadConfiguration(yml);
			Iterator<String> iterator = plugin.players.iterator();
			while(iterator.hasNext()) {
				String pl = iterator.next();
				Player p = Bukkit.getPlayer(pl);
				int x = ymlconf.getInt("x1");
				int y = ymlconf.getInt("y1");
				int z = ymlconf.getInt("z1");
				Location loc = new Location(p.getWorld(), x, y, z);
				p.teleport(loc);
				p.sendMessage(plugin.color(plugin.prefix + " &7The current battle has ended, thank you for playing!"));
				p.chat("/minebattle leavesilent");
			}//for
			plugin.players.clear();
		}//eld
	}//end
	public void createStats() {
		
	}
}
