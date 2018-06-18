package me.specifies.core.Events;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerCommandPreprocessEvent;

import me.specifies.core.MineBattle;

public class BlockCommands implements Listener{
	MineBattle plugin;
	public BlockCommands(MineBattle instance) {
		this.plugin = instance;
	}
	@EventHandler 
	public void onCommand(PlayerCommandPreprocessEvent e) {
		Player p = e.getPlayer();
		if(plugin.players.contains(p.getName())) {
			if(plugin.getConfig().getBoolean("disable-commands") == true) {
			if(e.getMessage().equalsIgnoreCase("/minebattle leave") || e.getMessage().equalsIgnoreCase("/minebattle leavesilent")) {
				
			} else {
				p.sendMessage(plugin.color(plugin.prefix + " &cYou can not use commands while in a battle."));
				e.setCancelled(true);
			}
			}
		}
	}
}
