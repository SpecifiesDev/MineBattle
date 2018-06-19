package me.specifies.core.Events;

import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;

import me.specifies.core.MineBattle;

public class LeadBoards implements Listener{
	MineBattle plugin;
	public LeadBoards(MineBattle instance) {
		this.plugin = instance;
	}
	@EventHandler
	public void onClick(InventoryClickEvent e) {
		Player p = (Player) e.getWhoClicked();
		String prefix = "&7Leaderboard";
		if(e.getInventory().getTitle().equals(plugin.color(prefix))) {
			ItemStack item = e.getCurrentItem();
			if(item == null || item.getType() == Material.AIR) {
				return;
			} 
			if(item.getType() == Material.SKULL_ITEM) {
				e.setCancelled(true);
				p.playSound(p.getLocation(), Sound.ITEM_BREAK, 10, 1);
			}
			if(item.getType() == Material.STAINED_GLASS_PANE) {
				e.setCancelled(true);
				p.playSound(p.getLocation(), Sound.ITEM_BREAK, 10, 1);
			}
		}
	}
}
