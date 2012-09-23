package me.stuppsman.finanzen.listener;

import me.stuppsman.finanzen.Finanzen;
import me.stuppsman.finanzen.GS;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.block.BlockRedstoneEvent;

public class FinanzenL_GS_RedStone implements Listener {

	@EventHandler
	public void onRSAccess(PlayerInteractEvent event) {
		if (event.getAction() == Action.RIGHT_CLICK_BLOCK) {
			Material mat = event.getClickedBlock().getType(); 
			Location loc = event.getClickedBlock().getLocation();
			String name = event.getPlayer().getName();
			if (mat == Material.LEVER || mat == Material.STONE_BUTTON) {
				for (GS gs : Finanzen.grundstuecke.values()) {
					if (!gs.isOnGS(loc)) {
						continue;
					}
					if (!gs.hatRedstoneRecht(name)) {
						event.setCancelled(true);
					}
				}
			}
		
		
		}
	}
	@EventHandler
	public void onPlates(BlockRedstoneEvent event) {
		
	}
}
