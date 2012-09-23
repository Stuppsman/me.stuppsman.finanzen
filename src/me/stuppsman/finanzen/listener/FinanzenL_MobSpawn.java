package me.stuppsman.finanzen.listener;

import me.stuppsman.finanzen.Finanzen;
import me.stuppsman.finanzen.Stadt;

import org.bukkit.Location;
import org.bukkit.entity.EntityType;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.CreatureSpawnEvent;

public class FinanzenL_MobSpawn implements Listener {

	private Finanzen plugin;

	public FinanzenL_MobSpawn(Finanzen plugin) {
		this.plugin = plugin;
	}

	@EventHandler
	public void onMobSpawn(CreatureSpawnEvent event) {
		Location loc = event.getLocation();
		EntityType typ = event.getEntityType();
		if (typ == EntityType.ZOMBIE || typ == EntityType.SKELETON || typ == EntityType.CREEPER || typ == EntityType.SPIDER || typ == EntityType.CAVE_SPIDER) {	
			if (!plugin.getConfig().getBoolean("MobSpawn")) {
				event.setCancelled(true);
				return;
			}
			for (Stadt stadt : Finanzen.staedte.values()) {
				if (stadt.isInStadt(loc)) {
					if (!stadt.isMobspawnAllowed()) {
						event.setCancelled(true);
						return;
					}
				}
			}
		}
	}
}
