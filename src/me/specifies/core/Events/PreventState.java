package me.specifies.core.Events;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerQuitEvent;

import me.specifies.core.MineBattle;

public class PreventState implements Listener{
	MineBattle plugin;
	public PreventState(MineBattle instance) {
		this.plugin = instance;
	}
	// Here we make sure that they are removed from the state, then we will add them to another list that force tps them to the exit location, to prevent a bug.
	@EventHandler
	public void onQuit(PlayerQuitEvent e) {
		Player p = e.getPlayer();
		if(plugin.players.contains(p.getName())) {
			plugin.players.remove(p.getName());
			plugin.disconinarena.add(p.getName());
		}
	}
}
