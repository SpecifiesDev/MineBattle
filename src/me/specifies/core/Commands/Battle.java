package me.specifies.core.Commands;

import java.io.File;
import java.io.IOException;
import java.util.Iterator;
import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.OfflinePlayer;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.SkullMeta;

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
					if(args[0].equalsIgnoreCase("help")) {
						if(p.hasPermission("minebattles.help")) {
							p.sendMessage(plugin.color("&8&m--------------&7" + plugin.prefix + "&7&8&m--------------"));
							p.sendMessage(plugin.color("&c&l/minebattle enter&7&l:"));
							p.sendMessage(plugin.color("&bEnter a battle if one is occuring."));
							p.sendMessage(plugin.color("&c&l/minebattle leave&7&l:"));
							p.sendMessage(plugin.color("&bLeave a battle if you are in one."));
							p.sendMessage(plugin.color("&c&l/minebattle setspawn&7&l:"));
							p.sendMessage(plugin.color("&bSet the start point of a battle."));
							p.sendMessage(plugin.color("&c&l/minebattle setendpoint&7&l:"));
							p.sendMessage(plugin.color("&bSet the end point of a battle."));
							p.sendMessage(plugin.color("&c&l/minebattle stats&7&l:"));
							p.sendMessage(plugin.color("&bView your stats."));
							p.sendMessage(plugin.color("&c&l/minebattle time&7&l:"));
							p.sendMessage(plugin.color("&bView the remaining time of an ongoing battle."));
							p.sendMessage(plugin.color("&c&l/minebattle leaderboard&7&l:"));
							p.sendMessage(plugin.color("&bView the leaderboard."));
							p.sendMessage(plugin.color("&c&l/viewstats <player>&7&l:"));
							p.sendMessage(plugin.color("&bView the stats of a target player."));
							
						}else {
							p.sendMessage(plugin.color(plugin.prefix + " &cIt appears that you are not permitted to use this commmand."));
						}
					}
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
					if(args[0].equalsIgnoreCase("stats")) {
						if(p.hasPermission("minebattle.stats")) {
							OfflinePlayer passparam = null;
							UUID u = p.getUniqueId();
							File pf = new File(plugin.getDataFolder() + File.separator + "players" + File.separator + u + ".yml");
							FileConfiguration conf = YamlConfiguration.loadConfiguration(pf);
							createStats("&7Stats", 27, p, conf, pf, p, false, passparam);
							
						}
					}//stats
					if(args[0].equalsIgnoreCase("leaderboard")) {
						File folder = new File(plugin.getDataFolder()+File.separator+"players");
						plugin.lb.leaderBoard(folder, p);
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
		}//else
	}//end
	public void createStats(String prefix, int slots, Player p, FileConfiguration conf, File f, Player head, boolean oPlayer, OfflinePlayer head2) {
		if(oPlayer == false) {
		//Basic inv
		Inventory inv = Bukkit.createInventory(null, slots, plugin.color(prefix));
		
		//Variables we will use
		int deaths = conf.getInt("Deaths");
		int kills = conf.getInt("Kills");
		String death = plugin.convert(deaths);
		String kill = plugin.convert(kills);
		
		//blank glass
		ItemStack glass = new ItemStack(Material.STAINED_GLASS_PANE, 1, (byte) 15);
		ItemMeta gm = glass.getItemMeta();
		gm.setDisplayName(plugin.color("&9"));
		glass.setItemMeta(gm);
		
		//sword
		ItemStack sword = new ItemStack(Material.DIAMOND_SWORD, 1);
		ItemMeta sm = sword.getItemMeta();
		sm.setDisplayName(plugin.color("&c&lKills&8: &7" + kill));
		sword.setItemMeta(sm);
		
		//skull
		ItemStack skull = new ItemStack(Material.SKULL_ITEM, 1, (byte) 3);
		SkullMeta skm = (SkullMeta) skull.getItemMeta();
		skm.setDisplayName(plugin.color("&c&lDeaths&8: &7" + death));
		skm.setOwner(head.getName());
		skull.setItemMeta(skm);
		
		// add items
		int i;
		for(i = 0; i < 9; i++) {
			inv.setItem(i, glass);
		}
		inv.setItem(9, glass);
		inv.setItem(10, glass);
		inv.setItem(11, glass);
		inv.setItem(12, sword);
		inv.setItem(13, glass);
		inv.setItem(14, skull);
		int ii;
		for(ii = 15; ii < 27; ii++) {
			inv.setItem(ii, glass);
		}
		p.openInventory(inv);
		}
		if(oPlayer == true) {
			//Basic inv
			Inventory inv = Bukkit.createInventory(null, slots, plugin.color(prefix));
			
			//Variables we will use
			int deaths = conf.getInt("Deaths");
			int kills = conf.getInt("Kills");
			String death = plugin.convert(deaths);
			String kill = plugin.convert(kills);
			
			//blank glass
			ItemStack glass = new ItemStack(Material.STAINED_GLASS_PANE, 1, (byte) 15);
			ItemMeta gm = glass.getItemMeta();
			gm.setDisplayName(plugin.color("&9"));
			glass.setItemMeta(gm);
			
			//sword
			ItemStack sword = new ItemStack(Material.DIAMOND_SWORD, 1);
			ItemMeta sm = sword.getItemMeta();
			sm.setDisplayName(plugin.color("&c&lKills&8: &7" + kill));
			sword.setItemMeta(sm);
			
			//skull
			ItemStack skull = new ItemStack(Material.SKULL_ITEM, 1, (byte) 3);
			SkullMeta skm = (SkullMeta) skull.getItemMeta();
			skm.setDisplayName(plugin.color("&c&lDeaths&8: &7" + death));
			skm.setOwner(head2.getName());
			skull.setItemMeta(skm);
			
			// add items
			int i;
			for(i = 0; i < 9; i++) {
				inv.setItem(i, glass);
			}
			inv.setItem(9, glass);
			inv.setItem(10, glass);
			inv.setItem(11, glass);
			inv.setItem(12, sword);
			inv.setItem(13, glass);
			inv.setItem(14, skull);
			int ii;
			for(ii = 15; ii < 27; ii++) {
				inv.setItem(ii, glass);
			}
			p.openInventory(inv);
		}
	}
}
