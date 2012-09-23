package me.stuppsman.finanzen.listener;


import me.stuppsman.finanzen.Finanzen;
import me.stuppsman.finanzen.Stadt;

import org.bukkit.Location;
import org.bukkit.craftbukkit.entity.CraftArrow;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent.DamageCause;

public class FinanzenL_PVP implements Listener {

	@EventHandler
	public void onNahkampf(EntityDamageByEntityEvent event) {
		
		Location loc = event.getEntity().getLocation();
		if (Finanzen.getStadtAtLoc(loc) == null) {
			return;
		}
		Stadt stadt = Finanzen.getStadtAtLoc(loc);
		Player verteidiger = null, angreifer = null;
		if (event.getEntity() instanceof Player) {
			verteidiger = (Player) event.getEntity();
			
		} else {
			return;
		}
		
		if (event.getDamager() instanceof Player) {
			angreifer = (Player) event.getDamager();
		} else {
			if (event.getCause() == DamageCause.PROJECTILE) {
				if (event.getDamager() instanceof CraftArrow) {
					if (((CraftArrow) event.getDamager()).getShooter() instanceof Player) {
						angreifer = (Player) ((CraftArrow) event.getDamager()).getShooter();
					}
				}
			}
		}
		if (angreifer != null && verteidiger != null) {
			if (!Finanzen.config.getBoolean("PVP")) {
				event.setCancelled(true);
				return;
			}
			if (stadt != null) {
				if (!stadt.isPVPAllowed()) {
					event.setCancelled(true);
				}
			}
		}
		
	}
}
