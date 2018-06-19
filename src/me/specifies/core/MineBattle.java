package me.specifies.core;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import org.bukkit.Bukkit;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitRunnable;

import me.specifies.core.Commands.Battle;
import me.specifies.core.Commands.ViewStats;
import me.specifies.core.Events.BlockCommands;
import me.specifies.core.Events.FirstJoin;
import me.specifies.core.Events.Kills;
import me.specifies.core.Events.LeadBoards;
import me.specifies.core.Events.PreventState;
import me.specifies.core.Events.Stats;
import me.specifies.core.Events.TeleportIfDisc;
import me.specifies.core.Management.Leaderboard;
import net.md_5.bungee.api.ChatColor;

public class MineBattle extends JavaPlugin{
	//Instance
	public Battle ba = new Battle(this);
	public Leaderboard lb = new Leaderboard(this);
	
	//prefix
	public String prefix = "&7[&3Mine&8Battles&7]";
	
	//config
	public int time = this.getConfig().getInt("countdown") * 60;
	public int last = this.getConfig().getInt("battle-time") * 60;
	public int broadcaststart = this.getConfig().getInt("broadcast-interval");
	public boolean broadcastsenable = this.getConfig().getBoolean("broadcast-enabled");
	
	//non edited vars
	public int lastf = this.getConfig().getInt("battle-time") * 60;
	public int lastt = this.getConfig().getInt("countdown") * 60;
	
	//management
	public boolean started = false;
	public ArrayList<String> players = new ArrayList<String>();
	public ArrayList<String> disconinarena = new ArrayList<String>();
	File folder = new File(this.getDataFolder() + File.separator + "data");
	File yml = new File(this.getDataFolder() + File.separator + "data" + File.separator + "data.yml");
	FileConfiguration ymlconf = YamlConfiguration.loadConfiguration(yml);
	public boolean startedman = false;
	
	public void onEnable() {
		createDefFile();
		this.saveDefaultConfig();
		startCountDown();
		registerCommands();
		registerEvents();
	}
	
	
	public void registerCommands() {
		getCommand("minebattle").setExecutor(new Battle(this));
		getCommand("viewstats").setExecutor(new ViewStats(this));
	}
	
	public void registerEvents() {
		PluginManager pm = Bukkit.getPluginManager();
		pm.registerEvents(new FirstJoin(this), this);
		pm.registerEvents(new BlockCommands(this), this);
		pm.registerEvents(new Kills(this), this);
		pm.registerEvents(new PreventState(this), this);
		pm.registerEvents(new TeleportIfDisc(this), this);
		pm.registerEvents(new Stats(this), this);
		pm.registerEvents(new LeadBoards(this), this);
	}
	
	public void createDefFile() {
		if(!folder.exists()) {
			folder.mkdirs();
			ymlconf.set("x", 1);
			ymlconf.set("y", 1);
			ymlconf.set("z", 1);
			ymlconf.set("x1", 1);
			ymlconf.set("y1", 1);
			ymlconf.set("z1", 1);
			try {
				ymlconf.save(yml);
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
	
	
	
	public String color(String m) {
		return ChatColor.translateAlternateColorCodes('&', m);
	}
	public String convert(int b) {
		return Integer.toString(b);
	}
	public void startCountDown() {
		new BukkitRunnable() {
			public void run() {
				time = time - 1;
				if(broadcastsenable == true) {
					if(time < broadcaststart) {
						String timestring = Integer.toString(time);
						Bukkit.getServer().broadcastMessage(color(prefix + " &7A MineBattle will start in &6" + timestring + " &7seconds."));
					}
					
				}
				if(startedman == true) {
					startedman = false;
					time = lastt;
					this.cancel();
				}
				if(time == 0) {
					Bukkit.getServer().broadcastMessage(color(prefix + " &7A MineBattle has been started. Type /minebattle enter to join!"));
					time = lastt;
					started = true;
					startEnd();
					this.cancel();
				}
			}
		}.runTaskTimer(this, 0, 20);
	}
	public void startEnd() {
		new BukkitRunnable() {
			public void run() {
				last = last - 1;
				if(broadcastsenable == true) {
					if(last < broadcaststart) {
						String laststring = Integer.toString(last);
						Bukkit.getServer().broadcastMessage(color(prefix + " &7The current Minebattle will end in &6" + laststring + " &7seconds."));
					}
				}
				if(last == 0) {
					this.cancel();
					Bukkit.getServer().broadcastMessage(color(prefix + " &7The MineBattle has ended."));
					last = lastf;
					started = false;
					startCountDown();
					ba.endGame();
				}
			}
		}.runTaskTimer(this, 0, 20);
	}

}
