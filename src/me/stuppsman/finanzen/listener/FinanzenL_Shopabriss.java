package me.stuppsman.finanzen.listener;

import me.stuppsman.finanzen.Finanzen;
import me.stuppsman.finanzen.Stadt;

import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;

public class FinanzenL_Shopabriss implements Listener {
	
	@EventHandler
	public void Shopabriss(BlockBreakEvent event) {
		if (event.getBlock().getTypeId() == 68) {
			if (Finanzen.ShopHM.isEmpty()) {
				return;
			}
			if (Finanzen.isShopSchild(event.getBlock().getLocation())) {
				event.getPlayer().sendMessage(ChatColor.RED + "Shops kannst du so nicht abreissen!");
				event.getPlayer().sendMessage(ChatColor.DARK_GRAY+ "'/shop abreissen' oder 'shop verstellen' eingeben!");
				event.setCancelled(true);
			}
		}
		if (event.getBlock().getTypeId() == 54) {
			if (!Finanzen.ShopHM.isEmpty()) {
				if (Finanzen.isShopChest(event.getBlock().getLocation())) {
					event.getPlayer().sendMessage(ChatColor.RED + "Shops kannst du so nicht abreissen!");
					event.getPlayer().sendMessage(ChatColor.DARK_GRAY+ "'/shop abreissen' oder 'shop verstellen' eingeben!");
					event.setCancelled(true);
				}
			}
			Location loc = event.getBlock().getLocation();
			for (Stadt stadt : Finanzen.staedte.values()) {
				if (stadt.getLagerkisten().contains(loc)) {
					if (!stadt.hatLagerrecht(event.getPlayer().getName())) {
						event.setCancelled(true);
						event.getPlayer().sendMessage(ChatColor.RED + "Du hast kein Recht dazu, Lagerkisten abzureissen!");
						
					}else {
						stadt.remLagerkiste(loc);
						event.getPlayer().sendMessage(ChatColor.GREEN + "Lagerkiste abgerissen!");
					}
				}
			}
		}
		if (!Finanzen.SchildHalterBlocks.isEmpty()) {
			if (Finanzen.holdsShopSchild(event.getBlock().getLocation())) {
				event.getPlayer().sendMessage(ChatColor.RED + "Shops kannst du so nicht abreissen!");
				event.getPlayer().sendMessage(ChatColor.DARK_GRAY+ "'/shop abreissen' oder 'shop verstellen' eingeben!");
				event.setCancelled(true);
			}
		}
	}
	
}
