package me.stuppsman.finanzen.listener;

import me.stuppsman.finanzen.Finanzen;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.ExplosionPrimeEvent;

public class FinanzenL_Creeperschaden implements Listener {

	private Finanzen plugin;
	public FinanzenL_Creeperschaden(Finanzen plugin)  {
		this.plugin = plugin;
	}
	@EventHandler
	public void onCreeperExplosion(ExplosionPrimeEvent event) {
		if (plugin.getConfig().getString("Explosions").equalsIgnoreCase("false")) {
			event.setCancelled(true);
			return;
		}
		
	}
}
