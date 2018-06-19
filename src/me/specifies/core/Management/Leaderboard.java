package me.specifies.core.Management;

import java.io.File;
import java.util.ArrayList;
import java.util.Collections;
import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.OfflinePlayer;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.SkullMeta;

import me.specifies.core.MineBattle;

public class Leaderboard {
	MineBattle plugin;
	public Leaderboard(MineBattle instance) {
		this.plugin = instance;
	}
	int num1;
	int num2;
	int num3;
	int num4;
	int num5;

	
	public void leaderBoard(File file, Player p) {
		File[] dirl = file.listFiles();
		if(dirl != null) {
			ArrayList<String> data = new ArrayList<String>();
			ArrayList<String> secondary = new ArrayList<String>();
			OfflinePlayer player = null;
			for (File child : dirl) {
				// we can use this to get the player's head, etc
				String us = child.getName();
				String[] uuids = us.split(".yml");
				String usf = uuids[0];
				System.out.println(usf);
				UUID u = UUID.fromString(usf);
				
				player = Bukkit.getOfflinePlayer(u);
				
				// get data
				File pf = new File(plugin.getDataFolder() + File.separator + "players" + File.separator + u + ".yml");
				FileConfiguration conf = YamlConfiguration.loadConfiguration(pf);
				
				String kills = plugin.convert(conf.getInt("Kills"));
				data.add(kills + player.getName());
				secondary.add(kills);
			}//for
			Collections.sort(data, Collections.reverseOrder());
			Collections.sort(secondary, Collections.reverseOrder());
			/*
			 * Here we created two data sets that are aligned in order, so we can pair the data. 
			 * I chose to do it this way so I wouldn't have to reset the project management.
			 */
			Inventory inv = Bukkit.createInventory(null, 27, plugin.color("&7Leaderboard"));
			
			ItemStack glass = new ItemStack(Material.STAINED_GLASS_PANE, 1, (byte) 15);
			ItemMeta gm = glass.getItemMeta();
			gm.setDisplayName(plugin.color("&9"));
			glass.setItemMeta(gm);
			
			ItemStack blank = new ItemStack(Material.AIR);
			
			int i;
			for(i = 0; i < 9; i++) {
				inv.setItem(i, glass);
			}
			inv.setItem(9, glass);
			inv.setItem(10, glass);
			//Number one on leaderboard
			String playerf1 = data.get(0);
			String playerf = playerf1.substring(1);
			OfflinePlayer player1 = Bukkit.getOfflinePlayer(playerf);
			if(player1 == null) {
				inv.setItem(11, blank);
			} else {
				ItemStack skull = new ItemStack(Material.SKULL_ITEM, 1, (byte) 3);
				SkullMeta skm = (SkullMeta) skull.getItemMeta();
				skm.setDisplayName(plugin.color("&8&l#&71. &b&l" + player1.getName()));
				ArrayList<String> lore = new ArrayList<String>();
				lore.add(plugin.color("&c&lKills&8: &7" + secondary.get(0)));
				skm.setLore(lore);
				skm.setOwner(player1.getName());
				skull.setItemMeta(skm);
				inv.setItem(11, skull);
			}
			String playerf2 = data.get(1);
			String playerfin = playerf2.substring(1);
			OfflinePlayer player2 = Bukkit.getOfflinePlayer(playerfin);
			if(player2 == null) {
				inv.setItem(12, blank);
			} else {
				ItemStack skull = new ItemStack(Material.SKULL_ITEM, 1, (byte) 3);
				SkullMeta skm = (SkullMeta) skull.getItemMeta();
				skm.setDisplayName(plugin.color("&8&l#&72. &b&l" + player2.getName()));
				ArrayList<String> lore = new ArrayList<String>();
				lore.add(plugin.color("&c&lKills&8: &7" + secondary.get(1)));
				skm.setLore(lore);
				skm.setOwner(player2.getName());
				skull.setItemMeta(skm);
				inv.setItem(12, skull);
			}
			String playerf3 = data.get(2);
			String playerfin1 = playerf3.substring(1);
			OfflinePlayer player3 = Bukkit.getOfflinePlayer(playerfin1);
			if(player2 == null) {
				inv.setItem(13, blank);
			} else {
				ItemStack skull = new ItemStack(Material.SKULL_ITEM, 1, (byte) 3);
				SkullMeta skm = (SkullMeta) skull.getItemMeta();
				skm.setDisplayName(plugin.color("&8&l#&73. &b&l" + player3.getName()));
				ArrayList<String> lore = new ArrayList<String>();
				lore.add(plugin.color("&c&lKills&8: &7" + secondary.get(3)));
				skm.setLore(lore);
				skm.setOwner(player3.getName());
				skull.setItemMeta(skm);
				inv.setItem(13, skull);
			}
			String playerf4 = data.get(3);
			String playerfin3 = playerf4.substring(1);
			OfflinePlayer player4 = Bukkit.getOfflinePlayer(playerfin3);
			if(player2 == null) {
				inv.setItem(14, blank);
			} else {
				ItemStack skull = new ItemStack(Material.SKULL_ITEM, 1, (byte) 3);
				SkullMeta skm = (SkullMeta) skull.getItemMeta();
				skm.setDisplayName(plugin.color("&8&l#&74. &b&l" + player4.getName()));
				ArrayList<String> lore = new ArrayList<String>();
				lore.add(plugin.color("&c&lKills&8: &7" + secondary.get(3)));
				skm.setLore(lore);
				skm.setOwner(player4.getName());
				skull.setItemMeta(skm);
				inv.setItem(14, skull);
			}
			String playerf5 = data.get(4);
			String playerfin4 = playerf5.substring(1);
			OfflinePlayer player5 = Bukkit.getOfflinePlayer(playerfin4);
			if(player2 == null) {
				inv.setItem(15, blank);
			} else {
				ItemStack skull = new ItemStack(Material.SKULL_ITEM, 1, (byte) 3);
				SkullMeta skm = (SkullMeta) skull.getItemMeta();
				skm.setDisplayName(plugin.color("&8&l#&75. &b&l" + player5.getName()));
				ArrayList<String> lore = new ArrayList<String>();
				lore.add(plugin.color("&c&lKills&8: &7" + secondary.get(4)));
				skm.setLore(lore);
				skm.setOwner(player5.getName());
				skull.setItemMeta(skm);
				inv.setItem(15, skull);
			}
			int ii;
			for(ii = 16; ii < 27; ii++) {
				inv.setItem(ii, glass);
			}
			p.openInventory(inv);
		}//if
	}
}
