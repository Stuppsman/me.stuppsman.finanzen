package me.stuppsman.finanzen.listener;

import me.stuppsman.finanzen.Finanzen;
import me.stuppsman.finanzen.GS;
import me.stuppsman.finanzen.Stadt;

import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;

public class FinanzenL_GS_Anwahl implements Listener {

	@EventHandler
	public void onGSWahl(PlayerInteractEvent event) {
		
		if (event.getAction() == Action.RIGHT_CLICK_BLOCK) {
			if (event.getClickedBlock().getTypeId() == 63){
				Location loc = event.getClickedBlock().getLocation();
				if (Finanzen.isGSSchild(loc)) {
					Player player = event.getPlayer();
					String GSID = Finanzen.GSSchilder.get(loc);
					GS gs = Finanzen.grundstuecke.get(GSID);
					String besitzer = gs.getBesitzer();
					Finanzen.GSFokus.put(player.getName(), GSID);
					player.sendMessage(ChatColor.DARK_BLUE + "| " + ChatColor.DARK_BLUE + "------------------------------------------");
					player.sendMessage(ChatColor.DARK_BLUE + "| " + ChatColor.BLUE + "Du hast folgendes Grundstück angewählt:");
					player.sendMessage(ChatColor.DARK_BLUE + "| " + ChatColor.DARK_BLUE + "------------------------------------------");
					player.sendMessage(ChatColor.DARK_BLUE + "| " + ChatColor.BLUE + "ID: " + ChatColor.YELLOW + gs.getID());
					if (gs.getStatus().equalsIgnoreCase("zum Verkauf freigegeben")) {
						player.sendMessage(ChatColor.DARK_BLUE + "| " + ChatColor.BLUE + "Preis: " + ChatColor.YELLOW + Finanzen.f.format(gs.getPreis())+Finanzen.kuerzel);
						if (!besitzer.equalsIgnoreCase("tba")) player.sendMessage(ChatColor.DARK_BLUE + "| " + ChatColor.BLUE + "Verkäufer: " + ChatColor.YELLOW + besitzer);
						
					}else {
						if (Finanzen.staedte.containsKey(besitzer)) {
							player.sendMessage(ChatColor.DARK_BLUE + "| " + ChatColor.BLUE + "Stadt: " + ChatColor.YELLOW + Finanzen.capFirst(besitzer));
						}else{
							player.sendMessage(ChatColor.DARK_BLUE + "| " + ChatColor.BLUE + "Besitzer: " + ChatColor.YELLOW + besitzer);
						}
					}
					int x = gs.getD().getBlockX() - gs.getA().getBlockX();
					int z = gs.getD().getBlockZ() - gs.getA().getBlockZ();
					int mx = (gs.getA().getBlockX() + gs.getD().getBlockX())/2;
					int mz = (gs.getA().getBlockZ() + gs.getD().getBlockZ())/2;
					player.sendMessage(ChatColor.DARK_BLUE + "| " + ChatColor.BLUE + "Größe: " + ChatColor.YELLOW + (x+1) + "*" + (z+1));
					player.sendMessage(ChatColor.DARK_BLUE + "| " + ChatColor.BLUE + "Mittelpunkt: "+ChatColor.YELLOW + mx+ ";"+mz);
					player.sendMessage(ChatColor.DARK_BLUE + "| " + ChatColor.BLUE + "Schildlocation: " + ChatColor.YELLOW + gs.getSchildLoc().getBlockX() + ";" + gs.getSchildLoc().getBlockY() + ";" + gs.getSchildLoc().getBlockZ());
					player.sendMessage(ChatColor.DARK_BLUE + "| " + ChatColor.BLUE + "Status: " + ChatColor.YELLOW + gs.getStatus());
					if (gs.getStatus().equalsIgnoreCase("Lager")) {
						Stadt stadt = Finanzen.getStadtAtLoc(gs.getSchildLoc());
						int kisten = stadt.getLagerkisten().size();
						int maxkisten = stadt.getMaxKisten();
						player.sendMessage(ChatColor.DARK_BLUE + "| " + ChatColor.BLUE + "Kapazität: " + ChatColor.YELLOW + kisten+"/"+maxkisten +" Truhen markiert");
						
					}
					player.sendMessage(ChatColor.DARK_BLUE + "| " + ChatColor.DARK_BLUE + "------------------------------------------");
					if (gs.getStatus().equalsIgnoreCase("zum Verkauf freigegeben")) {
						player.sendMessage(ChatColor.DARK_GRAY + "Um das Grundstück zu kaufen, gib '/kaufe gs' ein!");
					}
					Finanzen.GSSchildBeschreiben(loc);
					event.setCancelled(true);
					return;
				}
			}
		}
	}
}
