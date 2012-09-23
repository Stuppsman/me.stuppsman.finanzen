package me.stuppsman.finanzen.listener;

import me.stuppsman.finanzen.Finanzen;
import me.stuppsman.finanzen.Stadt;

import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerMoveEvent;

public class FinanzenL_Move implements Listener {

	@EventHandler
	public void onPlayerMove(PlayerMoveEvent event) {
		for (Stadt stadt : Finanzen.staedte.values()) {
			Location loc = event.getPlayer().getLocation();
			Location to = event.getTo();
			if (stadt.isInStadt(to) && !stadt.isInStadt(loc)) {
				event.getPlayer().sendMessage(ChatColor.GOLD + "Willkommen in " + Finanzen.capFirst(stadt.getName()) + "!");
				return;
			}
			if (!stadt.isInStadt(to) && stadt.isInStadt(loc)) {
				event.getPlayer().sendMessage(ChatColor.DARK_GREEN + "Du betrittst die Wildnis!");
				return;
			}
			
		}
		
	}
}
