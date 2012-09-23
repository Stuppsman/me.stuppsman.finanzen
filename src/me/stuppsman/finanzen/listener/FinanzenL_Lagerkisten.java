package me.stuppsman.finanzen.listener;

import me.stuppsman.finanzen.Finanzen;
import me.stuppsman.finanzen.GS;
import me.stuppsman.finanzen.Stadt;

import org.bukkit.ChatColor;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;

public class FinanzenL_Lagerkisten implements Listener {

	@EventHandler
	public void onLagerKistenAnwahl(PlayerInteractEvent event) {
		
		if (event.getAction() == Action.LEFT_CLICK_BLOCK) {
			if (Finanzen.lager.contains(event.getPlayer().getName())) {
				if (event.getClickedBlock().getTypeId() == 54) {
					event.setCancelled(true);
					Stadt stadt = Finanzen.getStadtAtLoc(event.getPlayer().getLocation());
					if (stadt == null) {
						event.getPlayer().sendMessage(ChatColor.RED + "Du bist grade in keiner Stadt!");
						event.getPlayer().sendMessage(ChatColor.GRAY + "Wenn du die Lagerkistenanwahl beenden möchtest, gib wieder /stadt lager ein.");
						return;
					}
					if (!stadt.hatLager()) {
						event.getPlayer().sendMessage(ChatColor.RED + "Die Stadt hat noch kein Lager!");
						return;
					}
					boolean isAtLager = false;
					for (GS gs : Finanzen.grundstuecke.values()) {
						if (gs.getStatus().equalsIgnoreCase("Lager")) {
							if (Finanzen.isGSinStadt(gs, stadt)) {
								isAtLager = true;
							}
						}
					}
					if (!isAtLager) {
						event.getPlayer().sendMessage(ChatColor.RED + "Die Truhe muss sich im Stadtlager befinden!");
						return;
					}
					stadt.addLagerkiste(event.getClickedBlock().getLocation());
					event.getPlayer().sendMessage(ChatColor.GREEN + "Diese Truhe gilt ab jetzt als Lagertruhe der Stadt "+stadt.getName());
					int restkisten = stadt.getMaxKisten() - stadt.getLagerkisten().size();
					if (restkisten == 0) {
						event.getPlayer().sendMessage(ChatColor.GRAY + "Du hast die maximale Anzahl an Stadtkisten erreicht!");
						event.getPlayer().sendMessage(ChatColor.GRAY + "(Lagerkistenanwahl beendet)");
						Finanzen.lager.remove(event.getPlayer().getName());
					}else {
						event.getPlayer().sendMessage(ChatColor.GRAY + "Du kannst noch " + restkisten + " Lagerkisten bestimmen!");
					}
					
				}
				
				
			}
		}
		
	}
}
