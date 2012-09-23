package me.stuppsman.finanzen.listener;

import me.stuppsman.finanzen.Finanzen;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

import com.xemsdoom.mexdb.exception.EmptyIndexException;
import com.xemsdoom.mexdb.system.Entry;

public class FinanzenL_Login implements Listener {
	private Finanzen plugin;

	public FinanzenL_Login(Finanzen plugin) {
		this.plugin =  plugin;
	}
	
	@EventHandler
	public void onJoin(PlayerJoinEvent event) {
		Player player = event.getPlayer();
		String name = player.getName();
		if (!Finanzen.Finanzendb.hasIndex(name.toLowerCase())) {
			try {
				newPlayer(name.toLowerCase());
			} catch (EmptyIndexException e) {
				e.printStackTrace();
			
			}
		}
		if (!Finanzen.Finanzendb.hasIndex(plugin.getConfig().getString("Servername"))) {
			try {
				newPlayer(plugin.getConfig().getString("Servername"));
			} catch (EmptyIndexException e) {
				e.printStackTrace();
			}
		}
		if (!Finanzen.Finanzendb.hasKey(name.toLowerCase(), "chillis")) {
			Finanzen.Finanzendb.setValue(name, "chillis", "0");
		}
		
	}
	
	//Farben: &0:schwarz,&1:dunkelblau,&2dunkelgrün,&3:dunkelcyan,&4:dunkelrot,&5:dunkellila,&6:orange,
	//&7:grau,&8:dunkelgrau,&9:blau,&a:grün,&b:cyan,&c:rot,&d:rosa,&e:gelb,&f:weiss

	private void newPlayer(String name) throws EmptyIndexException {
		Entry neuerSpieler = new Entry(name);
		neuerSpieler.addValue("konto", new Double(plugin.getConfig().getDouble("Startgeld")).toString());
		neuerSpieler.addValue("trans1", ("&3| &6Startgeld bekommen: &2+" + plugin.getConfig().getDouble("Startgeld")).toString() + Finanzen.kuerzel);
		neuerSpieler.addValue("chillis", "0");
		Finanzen.Finanzendb.addEntry(neuerSpieler);
		Finanzen.Finanzendb.push();
	}

}
