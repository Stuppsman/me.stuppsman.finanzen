package me.stuppsman.finanzen.listener;

import me.stuppsman.finanzen.Finanzen;
import me.stuppsman.finanzen.Stadt;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBurnEvent;
import org.bukkit.event.block.BlockIgniteEvent;
import org.bukkit.event.block.BlockIgniteEvent.IgniteCause;

public class FinanzenL_Firespread implements Listener {

	@EventHandler
    public void onBlockBurn(BlockBurnEvent event) {
		for (Stadt stadt : Finanzen.staedte.values()) {
			if (stadt.isInStadt(event.getBlock().getLocation())) {
				if (stadt.hatFeuerwehr()) {
					event.setCancelled(true);
					return;
				}
			}
		}
    }

    @EventHandler
    public void onBlockIgnite(BlockIgniteEvent event) {
    	if (event.getCause() == IgniteCause.SPREAD)
    		for (Stadt stadt : Finanzen.staedte.values()) {
    			if (stadt.isInStadt(event.getBlock().getLocation())) {
    				if (stadt.hatFeuerwehr()) {
    					event.setCancelled(true);
    					return;
    				}
    			}
    		}
    		
    	}
}
