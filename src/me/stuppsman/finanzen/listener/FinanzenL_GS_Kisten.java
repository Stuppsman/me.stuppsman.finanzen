package me.stuppsman.finanzen.listener;

import me.stuppsman.finanzen.Finanzen;
import me.stuppsman.finanzen.GS;
import me.stuppsman.finanzen.Stadt;

import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;

public class FinanzenL_GS_Kisten implements Listener{

	@EventHandler
	public void onChestAccess(PlayerInteractEvent event) {
		if (event.getAction() == Action.RIGHT_CLICK_BLOCK) {
			if (event.getClickedBlock().getTypeId() == 54 || event.getClickedBlock().getTypeId() == 64) {
				Location loc = event.getClickedBlock().getLocation();
				String name = event.getPlayer().getName();
				for (GS gs : Finanzen.grundstuecke.values()) {
					if (!gs.isOnGS(loc)) {
						continue;
					}
					if (gs.getStatus().equalsIgnoreCase("Lager")) {
						Stadt stadt = Finanzen.getStadtAtLoc(loc);
						if (stadt.getLagerkisten().contains(loc)) {
							if (!stadt.hatLagerrecht(name)) {
								event.getPlayer().sendMessage(ChatColor.RED + "Du hast kein Recht, auf das Lager der Stadt zuzugreifen!");
								event.setCancelled(true);
								return;
							}
						}
						return;
					}
					if (!gs.hatSchluessel(name)) {
						event.getPlayer().sendMessage(ChatColor.RED + "Abgeschlossen!");
						event.setCancelled(true);
					}
				}	
			}
	
		}
	}
}
