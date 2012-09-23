package me.stuppsman.finanzen.listener;


import me.stuppsman.finanzen.Finanzen;
import me.stuppsman.finanzen.Stadt;

import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;

public class FinanzenL_GS_Markierung implements Listener {
	
	
	@EventHandler
	public void onPlayerInteract(PlayerInteractEvent event) {
		
		Player player = event.getPlayer();
		if (!player.hasPermission("finanzen.admin")) {
			return;
		}
		
		String name = player.getName();
		
		
		if (!(player.getItemInHand().getTypeId() == Finanzen.marker)) {
			return;
		}
		if (event.getAction() == Action.LEFT_CLICK_BLOCK) {
				event.setCancelled(true);
				Location locA = event.getClickedBlock().getLocation();
				Finanzen.locA.put(name, event.getClickedBlock().getLocation());
				player.sendMessage(ChatColor.DARK_GRAY + "Punkt 1 wurde erfolgreich gesetzt.");
				if (Finanzen.locB.containsKey(name)) {
					int x = Finanzen.locB.get(name).getBlockX() > locA.getBlockX() ? Finanzen.locB.get(name).getBlockX() - locA.getBlockX() : locA.getBlockX() - Finanzen.locB.get(name).getBlockX() ;
					int z = Finanzen.locB.get(name).getBlockZ() > locA.getBlockZ() ? Finanzen.locB.get(name).getBlockZ() - locA.getBlockZ() : locA.getBlockZ() - Finanzen.locB.get(name).getBlockZ() ;
					player.sendMessage(ChatColor.DARK_GRAY + "Das markierte Grundstück hat die Maße: " + (x+1) + "*" + (z+1) + " - Preis: " + Finanzen.f.format(Finanzen.getGSWert(locA, Finanzen.locB.get(name)))+Finanzen.kuerzel);
					player.sendMessage(ChatColor.DARK_GRAY + "Wenn du es kaufen möchtest, musst du ein Schild hinstellen.");
					player.sendMessage(ChatColor.DARK_GRAY + "1. Zeile: Grundstück");
					player.sendMessage(ChatColor.DARK_GRAY + "2. Zeile: kaufen");
					if ((x+1)>50 && (z+1)>50) {
						int midX = (locA.getBlockX()+Finanzen.locB.get(name).getBlockX()) / 2;
						int midZ = (locA.getBlockZ()+Finanzen.locB.get(name).getBlockZ()) / 2;
						Location locMid = new Location(locA.getWorld(), midX, 1, midZ);
						if (!(Finanzen.getStadtAtLoc(locMid) == null)) {
							Stadt stadt = Finanzen.getStadtAtLoc(locMid);
							if (stadt.getBesitzer().equalsIgnoreCase(name)) {
								double kosten = Finanzen.getStadtWert(locA, Finanzen.locB.get(name)) - Finanzen.getStadtWert(stadt);
								player.sendMessage(ChatColor.DARK_GRAY + "Kosten für Gebietserweiterung der Stadt " + stadt.getName() + ": " + Finanzen.f.format(kosten) + Finanzen.kuerzel);
								
							}
						}else
						if (player.hasPermission("finanzen.neuestadt")) {
							player.sendMessage(ChatColor.DARK_GRAY + "Wenn du eine neue Stadt gründen möchtes, gib /neuestadt Stadtname ein!");
							player.sendMessage(ChatColor.DARK_GRAY + "Kosten: " + Finanzen.f.format(Finanzen.getStadtWert(locA, Finanzen.locB.get(name))));
						}
						
					}
				}
				
			
		}
		if (event.getAction() == Action.RIGHT_CLICK_BLOCK){
				event.setCancelled(true);
				Location locB = event.getClickedBlock().getLocation();
				Finanzen.locB.put(name, locB);
				player.sendMessage(ChatColor.DARK_GRAY + "Punkt 2 wurde erfolgreich gesetzt.");
				if (Finanzen.locA.containsKey(name)) {
					int x = Finanzen.locA.get(name).getBlockX() > locB.getBlockX() ? Finanzen.locA.get(name).getBlockX() - locB.getBlockX() : locB.getBlockX() - Finanzen.locA.get(name).getBlockX() ;
					int z = Finanzen.locA.get(name).getBlockZ() > locB.getBlockZ() ? Finanzen.locA.get(name).getBlockZ() - locB.getBlockZ() : locB.getBlockZ() - Finanzen.locA.get(name).getBlockZ() ;
					player.sendMessage(ChatColor.DARK_GRAY + "Das markierte Grundstück hat die Maße: " + (x+1) + "*" + (z+1) + " - Preis: " + Finanzen.f.format(Finanzen.getGSWert(Finanzen.locA.get(name), locB))+Finanzen.kuerzel);
					player.sendMessage(ChatColor.DARK_GRAY + "Wenn du es kaufen möchtest, musst du ein Schild hinstellen.");
					player.sendMessage(ChatColor.DARK_GRAY + "1. Zeile: Grundstück");
					player.sendMessage(ChatColor.DARK_GRAY + "2. Zeile: kaufen");
					if ((x+1)>50 && (z+1)>50) {
						int midX = (Finanzen.locA.get(name).getBlockX()+locB.getBlockX()) / 2;
						int midZ = (Finanzen.locA.get(name).getBlockZ()+locB.getBlockZ()) / 2;
						Location locMid = new Location(locB.getWorld(), midX, 1, midZ);
						if (!(Finanzen.getStadtAtLoc(locMid) == null)) {
							Stadt stadt = Finanzen.getStadtAtLoc(locMid);
							if (stadt.getBesitzer().equalsIgnoreCase(name)) {
								double kosten = Finanzen.getStadtWert(Finanzen.locA.get(name), locB) - Finanzen.getStadtWert(stadt);
								player.sendMessage(ChatColor.DARK_GRAY + "Kosten für Gebietserweiterung der Stadt " + stadt.getName() + ": " + Finanzen.f.format(kosten) + Finanzen.kuerzel);
								
							}
						}else
						if (player.hasPermission("finanzen.neuestadt")) {
							player.sendMessage(ChatColor.DARK_GRAY + "Wenn du eine neue Stadt gründen möchtes, gib /neuestadt Stadtname ein!");
							player.sendMessage(ChatColor.DARK_GRAY + "Kosten: " + Finanzen.f.format(Finanzen.getStadtWert(Finanzen.locA.get(name), locB)));
						}
						
					}
				}
		}
	}
}
