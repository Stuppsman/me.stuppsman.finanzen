package me.stuppsman.finanzen.listener;

import me.stuppsman.finanzen.Finanzen;
import me.stuppsman.finanzen.GS;
import me.stuppsman.finanzen.Stadt;

import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;

public class FinanzenL_GS_BauAbbau implements Listener {
	
	
	@EventHandler
	public void onBlockBreak(BlockBreakEvent event) {
		//grundstücksschilder
		
		if (event.getBlock().getTypeId() == 63) {
			if (Finanzen.isGSSchild(event.getBlock().getLocation()))  {
				event.setCancelled(true);
				event.getPlayer().sendMessage(ChatColor.RED + "Grundstücksschilder kann man so nicht abreissen!");
				return;
			}
		}
		if (Finanzen.isUnterGSSchild(event.getBlock().getLocation())) {
			event.setCancelled(true);
			event.getPlayer().sendMessage(ChatColor.RED + "Grundstücksschilder kann man so nicht abreissen!");
			return;
		}
		//fremde Grundstücke?
		
		Location loc = event.getBlock().getLocation();
		String name = event.getPlayer().getName();
		if (loc.getBlockY()<Finanzen.tiefe) {
			return;
		}
		for (GS gs : Finanzen.grundstuecke.values()) {
			if (!gs.isOnGS(loc)) {
				continue;
			}
			if (!gs.hatBaurecht(name)) {
				if (!gs.getBesitzer().equalsIgnoreCase("tba")) {
					event.getPlayer().sendMessage(ChatColor.RED + "Auf fremden Grundstücken darfst du nichts abbauen!");
					String helper = "";
					String besitzer = gs.getBesitzer();
					if (Finanzen.staedte.containsKey(besitzer)) {
						helper = "dem Bürgermeister von " + Finanzen.capFirst(besitzer);
					}else {
						helper = Finanzen.capFirst(besitzer);
					}
					event.getPlayer().sendMessage(ChatColor.RED + "Sprich mit " + helper + " wenn du hier unbedingt etwas abbauen möchtest!");
					event.setCancelled(true);
					return;
				}else {
					event.getPlayer().sendMessage(ChatColor.RED + "Wenn du hier bauen möchtest, musst du das Grundstück kaufen!");
					event.getPlayer().sendMessage(ChatColor.RED + "Klicke dazu mit der rechten Maustaste das GS-Schild an und gib danach '/kaufe gs' ein!");
					event.setCancelled(true);
					return;			
					
				}
			}
		}
		for (Stadt stadt : Finanzen.staedte.values()) {
			if (stadt.isInStadt(loc)) {
				if (!stadt.hatBaurecht(name)) {
					for (GS gs : Finanzen.grundstuecke.values()) {
						if (gs.isOnGS(loc)) {
							if (gs.hatBaurecht(name)) {
								return;
							}
						}
					}
					event.getPlayer().sendMessage(ChatColor.RED + "In " + Finanzen.capFirst(stadt.getName()) + " kannst du nicht abbauen!");
					event.getPlayer().sendMessage(ChatColor.RED + "Wende dich an Bürgermeister " + stadt.getBesitzer() + " um hier Baurechte zu bekommen.");
					event.setCancelled(true);
					return;
				}
			}
		}
	}
	@EventHandler
	public void onBlockPlace(BlockPlaceEvent event) {
		Location loc = event.getBlock().getLocation();
		String name = event.getPlayer().getName();
		if (loc.getBlockY()<Finanzen.tiefe) {
			return;
		}
		for (GS gs : Finanzen.grundstuecke.values()) {
			if (!gs.isOnGS(loc)) {
				continue;
			}
			if (!gs.hatBaurecht(name)) {
				if (!gs.getBesitzer().equalsIgnoreCase("tba")) {
					event.getPlayer().sendMessage(ChatColor.RED + "Auf fremden Grundstücken darfst du nichts bauen!");
					event.getPlayer().sendMessage(ChatColor.RED + "Sprich mit " + gs.getBesitzer() + ", wenn du hier unbedingt etwas bauen möchtest!");
					event.setCancelled(true);
					return;
				}else {
					event.getPlayer().sendMessage(ChatColor.RED + "Wenn du hier bauen möchtest, musst du das Grundstück kaufen!");
					event.getPlayer().sendMessage(ChatColor.RED + "Klicke dazu mit der rechten Maustaste das GS-Schild an und gib danach '/kaufe gs' ein!");
					event.setCancelled(true);
					return;			
					
				}
			}
		}
		for (Stadt stadt : Finanzen.staedte.values()) {
			if (stadt.isInStadt(loc)) {
				if (!stadt.hatBaurecht(name)) {
					for (GS gs : Finanzen.grundstuecke.values()) {
						if (gs.isOnGS(loc)) {
							if (gs.hatBaurecht(name)) {
								return;
							}
						}
					}
					event.getPlayer().sendMessage(ChatColor.RED + "In " + stadt.getName() + " kannst du nicht bauen!");
					event.getPlayer().sendMessage(ChatColor.RED + "Wende dich an Bürgermeister " + stadt.getBesitzer() + " um hier Baurechte zu bekommen.");
					event.setCancelled(true);
				}
			}
		}
	}
}
