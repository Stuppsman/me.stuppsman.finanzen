package me.stuppsman.finanzen.listener; 

import me.stuppsman.finanzen.Finanzen;
import me.stuppsman.finanzen.GS;
import me.stuppsman.finanzen.Stadt;

import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.SignChangeEvent;
import org.dynmap.markers.AreaMarker;
import org.dynmap.markers.MarkerIcon;

public class FinanzenL_GS_NEU implements Listener {
	
	
	private Finanzen plugin;

	public FinanzenL_GS_NEU(Finanzen plugin) {
		this.plugin = plugin;
	}

	@EventHandler
	public void onSchildSetzen(SignChangeEvent event) {
		Player player = event.getPlayer();
		String name = player.getName();
		Location Schild = event.getBlock().getLocation();
		if (event.getLine(0).equalsIgnoreCase("schild")) {
			if (event.getLine(1).equalsIgnoreCase("ersetzen")) {
				for (GS gs : Finanzen.grundstuecke.values()) {
					if (gs.isOnGS(Schild)) {
						Finanzen.GSSchilder.remove(gs.getSchildLoc());
						gs.setSchildLoc(Schild);
						Finanzen.GSSchilder.put(Schild, gs.getID());
						return;
					}
				}
			}
		}
		if (event.getLine(0).equalsIgnoreCase("Grundstück")) {
			if (event.getLine(1).equalsIgnoreCase("kaufen")) {
				if (!Finanzen.locA.containsKey(name) || !Finanzen.locB.containsKey(name)) {
					player.sendMessage(ChatColor.RED + "Du musst erst ein Grundstück markieren!");
					player.sendMessage(ChatColor.DARK_GRAY + "Markierungsitem: " + Finanzen.markername);
					event.getBlock().breakNaturally();
					return;
				}
				String ID = getFreeID();
				Location locA = Finanzen.locA.get(name);
				Location locB = Finanzen.locB.get(name);
				double kosten = Finanzen.getGSWert(locA, locB);
				String helper = "";
				if (kosten>Finanzen.Finanzendb.getDouble(player.getName().toLowerCase(), "konto")) {
					player.sendMessage(ChatColor.RED + "Das kannst du dir nicht leisten!"+ ChatColor.DARK_GRAY + Finanzen.f.format(kosten)+Finanzen.kuerzel);
					event.getBlock().breakNaturally();
					
					return;
				}
				GS gs = new GS(ID, locA, locB, name, Schild);
				if (!Finanzen.grundstuecke.values().isEmpty()){
					for (GS gst : Finanzen.grundstuecke.values()) {
						if (gs.schneidet(gst)) {
							player.sendMessage(ChatColor.RED + "Das markierte Grundstück überschneidet sich mit mindestens einem anderen Grundstück!");
							event.getBlock().breakNaturally();
							Finanzen.GSSchilder.remove(gs.getSchildLoc());
							return;
						}
					}
				}
				if (Finanzen.staedte.isEmpty()) {
					Finanzen.incKonto(name, (-1)*kosten);
					gs.setBesitzer(name);
					gs.setStatus("Baustelle");
					Finanzen.grundstuecke.put(ID, gs);
					player.sendMessage(ChatColor.BLUE + "Glückwunsch! Du hast ein Grundstück" + helper + " gekauft!");
					GSMarkieren(gs);
					plugin.addGSKauf(name, gs.getID(), kosten);
					Finanzen.GSSchildBeschreiben(gs.getSchildLoc());
					gsToDM(gs);
					return;
				}
				for (Stadt stadt : Finanzen.staedte.values())	{
					if (Finanzen.isGSinStadt(gs, stadt)) {
						if (!stadt.getBesitzer().equalsIgnoreCase(name)) {
							if (stadt.isAllgGSRecht()) {
								player.sendMessage(ChatColor.RED + "In " + stadt.getName() + " darfst du dir keine Grundstücke selbst abstecken!");
								player.sendMessage(ChatColor.RED + "Gehe in die Wildnis oder kaufe ein schon markiertes Grundstück in dieser Stadt.");
								event.getBlock().breakNaturally();
								Finanzen.GSSchilder.remove(gs.getSchildLoc());
								return;
							}
						}
						
						stadt.incStadtkasse(kosten);
						Finanzen.incKonto(name, (-1)*kosten);
						gs.setBesitzer(name);
						gs.setStatus("Baustelle");
						Finanzen.grundstuecke.put(ID, gs);
						helper += " in " + Finanzen.capFirst(stadt.getName());
						GSMarkieren(gs);
						plugin.addGSKauf(name, gs.getID(), kosten);
						plugin.addGSVerkauf(stadt.getName(), kosten, name, gs.getID());
						Finanzen.GSSchildBeschreiben(gs.getSchildLoc());
						player.sendMessage(ChatColor.BLUE + "Glückwunsch! Du hast ein Grundstück" + helper + " gekauft!");
						gsToDM(gs);
						return;
						
					}
				}
				gs.setBesitzer(name);
				gs.setStatus("Baustelle");
				Finanzen.grundstuecke.put(ID, gs);
				Finanzen.incKonto(name, (-1)*kosten);
				GSMarkieren(gs);
				player.sendMessage(ChatColor.BLUE + "Glückwunsch! Du hast ein Grundstück" + helper + " gekauft!");
				plugin.addGSKauf(name, ID, kosten);
				Finanzen.GSSchildBeschreiben(gs.getSchildLoc());
				gsToDM(gs);
				return;
				
			}
		}
		
		if (event.getLine(0).equalsIgnoreCase("[Grundstück]")) {
			if (!player.hasPermission("finanzen.admin")) {
				event.getBlock().breakNaturally();
				player.sendMessage(ChatColor.RED + "Das Schild mit eckigen Klammern ist Admins vorbehalten!");
				return;
			}
			if (event.getBlock().getTypeId() == 68) {
				player.sendMessage(ChatColor.RED + "Das Schild muss auf dem Boden stehen!");
				event.getBlock().breakNaturally();
				return;
			}
			if (!(Finanzen.locA.containsKey(name) && Finanzen.locB.containsKey(name))) {
				player.sendMessage(ChatColor.RED + "Du musst erst ein Grundstück markieren!");
				player.sendMessage(ChatColor.DARK_GRAY + "Markierungsitem: " + Finanzen.markername);
				event.getBlock().breakNaturally();
				return;
			}	
			String ID = getFreeID();
			Location locA = Finanzen.locA.get(name);
			Location locB = Finanzen.locB.get(name);
			if (event.getLine(1).isEmpty()) {
				double preis = Finanzen.getGSWert(locA, locB);
				GS gs = new GS(ID, locA, locB, preis, Schild);
				for (GS gst : Finanzen.grundstuecke.values()) {
					if (gs.schneidet(gst)) {
						player.sendMessage(ChatColor.RED + "Das markierte Grundstück überschneidet sich mit mindestens einem anderen Grundstück!");
						event.getBlock().breakNaturally();
						Finanzen.GSSchilder.remove(gs.getSchildLoc());
						return;
					}
				}
				player.sendMessage(ChatColor.BLUE + "Das Grundstück mit ID: \"" + ChatColor.YELLOW + ID + ChatColor.BLUE + "\" wird jetzt für " + ChatColor.YELLOW+ Finanzen.f.format(preis) + Finanzen.kuerzel + ChatColor.BLUE + " zum Kauf angeboten!");
				Finanzen.grundstuecke.put(ID, gs);
				Finanzen.GSSchildBeschreiben(Schild);
				GSMarkieren(gs);
				gsToDM(gs);
				return;
			}
			try {
				try {
					double preis = new Double(event.getLine(1));
					GS gs = new GS(ID, locA, locB, preis, Schild);
					for (GS gst : Finanzen.grundstuecke.values()) {
						if (gs.schneidet(gst)) {
							player.sendMessage(ChatColor.RED + "Das markierte Grundstück überschneidet sich mit mindestens einem anderen Grundstück!");
							event.getBlock().breakNaturally();
							Finanzen.GSSchilder.remove(gs.getSchildLoc());
							return;
						}
					}
					player.sendMessage(ChatColor.BLUE + "Das Grundstück mit ID: \"" + ChatColor.YELLOW + ID + ChatColor.BLUE + "\" wird jetzt für " + ChatColor.YELLOW+ Finanzen.f.format(preis) + Finanzen.kuerzel + ChatColor.BLUE + " zum Kauf angeboten!");
					Finanzen.grundstuecke.put(ID, gs);
					Finanzen.GSSchildBeschreiben(Schild);
					GSMarkieren(gs);
					gsToDM(gs);
					return;
					
					
				}catch (NumberFormatException e) {
					String besitzer = event.getLine(1);
					if (!Finanzen.Finanzendb.hasIndex(besitzer)) {
						player.sendMessage(ChatColor.RED + "Der Preis/Besitzer wurde falsch angegeben!");
						event.getBlock().breakNaturally();
						return;
					}
					GS gs = new GS(ID, locA, locB, besitzer, Schild);
					for (GS gst : Finanzen.grundstuecke.values()) {
						if (gst.schneidet(gs)) {
							player.sendMessage(ChatColor.RED + "Das markierte Grundstück überschneidet sich mit mindestens einem anderen Grundstück!");
							event.getBlock().breakNaturally();
							return;
						}
					}
					
						
					player.sendMessage(ChatColor.BLUE + "Das Grundstück mit ID: \"" + ChatColor.YELLOW + ID + ChatColor.BLUE + "\" wurde für den Spieler " + ChatColor.YELLOW + besitzer + ChatColor.BLUE + " reserviert!");
					Finanzen.grundstuecke.put(ID, gs);
					Finanzen.GSSchildBeschreiben(Schild);
					GSMarkieren(gs);
					gsToDM(gs);
					return;
				}
				
			} catch (IndexOutOfBoundsException e) {
				player.sendMessage(ChatColor.RED + "Du musst folgendes angegeben: ");
				player.sendMessage(ChatColor.DARK_GRAY + "Zeile 1: "+ChatColor.GRAY + "[Grundstück]");
				player.sendMessage(ChatColor.DARK_GRAY + "Zeile 2: "+ChatColor.GRAY + "Preis oder Besitzer");
				event.getBlock().breakNaturally();
				
			}
			
		}
	}

	private String getFreeID() {
		int i = 0;
		while (Finanzen.grundstuecke.containsKey(String.valueOf(i))) {
			i++;
		}
		return String.valueOf(i);
	}

	private void GSMarkieren(GS gs) {
		World world = gs.getA().getWorld();
		Location a = hoechsterFreier(gs.getA());
		Location e = hoechsterFreier(gs.getD());
		Location c = hoechsterFreier(new Location(world, a.getBlockX(), 1, e.getBlockZ()));
		Location g = hoechsterFreier(new Location(world, e.getBlockX(), 1, a.getBlockZ()));
		if (Finanzen.GSGrenzen.equalsIgnoreCase("Zaun")) {
			//a-c
			zieheZaun(a, c);
			//c-e
			zieheZaun(e, c);
			//e-g
			zieheZaun(e, g);
			//g-a
			zieheZaun(a, g);
			return;
		}
		if (Finanzen.GSGrenzen.equalsIgnoreCase("Mauer")) {
			//a-c
			zieheMauer(a, c);
			//c-e
			zieheMauer(e, c);
			//e-g
			zieheMauer(e, g);
			//g-a
			zieheMauer(a, g);
			//a-c
			zieheMauer(a, c);
			//c-e
			zieheMauer(e, c);
			//e-g
			zieheMauer(e, g);
			//g-a
			zieheMauer(a, g);
			return;
		}
		
		
		a.getBlock().setTypeId(49);
		e.getBlock().setTypeId(49);
		c.getBlock().setTypeId(49);
		g.getBlock().setTypeId(49);
		
		/*Location b = hoechsterFreier(new Location(world, e.getBlockX(), 1, a.getBlockX()));
		Location d = hoechsterFreier(new Location(world, e.getBlockX(), 1, a.getBlockX()));
		Location f = hoechsterFreier(new Location(world, e.getBlockX(), 1, a.getBlockX()));
		Location h = hoechsterFreier(new Location(world, e.getBlockX(), 1, a.getBlockX()));
		
		//a,c,e,g = Ecken
		*/
	}

	private void zieheZaun(Location a, Location b) {
		int ax = a.getBlockX();
		int bx = b.getBlockX();
		int az = a.getBlockZ();
		int bz = b.getBlockZ();
		
		if (ax == bx) {
			int minZ = (az>bz) ? bz : az;
			int maxZ = (az>bz) ? az : bz;
			for ( ;minZ != maxZ+1; minZ++) {
				Location loc = new Location(a.getWorld(), ax, 1, minZ);
				hoechsterFreier(loc).getBlock().setType(Material.FENCE);
			}
		}
		if (az == bz) {
			int minX = (ax>bx) ? bx : ax;
			int maxX = (ax>bx) ? ax : bx;
			for ( ;minX != maxX+1; minX++) {
				Location loc = new Location(a.getWorld(), minX, 1, az);
				hoechsterFreier(loc).getBlock().setType(Material.FENCE);
			}
		}
		
	}
	private void zieheMauer(Location a, Location b) {
		int ax = a.getBlockX();
		int bx = b.getBlockX();
		int az = a.getBlockZ();
		int bz = b.getBlockZ();
		
		if (ax == bx) {
			int minZ = (az>bz) ? bz : az;
			int maxZ = (az>bz) ? az : bz;
			for ( ;minZ != maxZ+1; minZ++) {
				Location loc = new Location(a.getWorld(), ax, 1, minZ);
				hoechsterFreier(loc).getBlock().setType(Material.STONE);
			}
		}
		if (az == bz) {
			int minX = (ax>bx) ? bx : ax;
			int maxX = (ax>bx) ? ax : bx;
			for ( ;minX != maxX+1; minX++) {
				Location loc = new Location(a.getWorld(), minX, 1, az);
				hoechsterFreier(loc).getBlock().setType(Material.STONE);
			}
		}
		
	}

	private Location hoechsterFreier(Location a) {
		int max = a.getWorld().getMaxHeight();
		Location hochster = new Location(a.getWorld(), a.getBlockX(), max-1, a.getBlockZ());
		while (hochster.getBlock().getTypeId() == 0 || hochster.getBlock().getTypeId() == 18) {
			hochster.setY(hochster.getY()-1);
		}
		hochster.setY(hochster.getY()+1);
		return hochster;
	}
	private void gsToDM(GS gs) {
		double[] x = new double[4];
        double[] z = new double[4];
        x[0] = gs.getA().getX(); z[0] = gs.getA().getZ();
        x[1] = gs.getA().getX(); z[1] = gs.getD().getZ()+1;
        x[2] = gs.getD().getX()+1; z[2] = gs.getD().getZ()+1;
        x[3] = gs.getD().getX()+1; z[3] = gs.getA().getZ();
        AreaMarker m = Finanzen.setgs.createAreaMarker(gs.getID(), Finanzen.capFirst(gs.getBesitzer()), true, gs.getA().getWorld().getName(), x, z, true);
        int color = 0x00CED1;
        String status = gs.getStatus().equalsIgnoreCase("zum Verkauf freigegeben") || gs.getStatus().equalsIgnoreCase("Wohnhaus") ? "Wohngebiet" : gs.getStatus();
        String zeile2 = gs.getBesitzer().equalsIgnoreCase("tba") ? "<span style=\"font-weight:bold;\">Preis: </span>"+ Finanzen.f.format(gs.getPreis())+Finanzen.kuerzel+"<br />"  :"<span style=\"font-weight:bold;\">Besitzer: </span>"+gs.getBesitzer()+"<br />";
        if (!gs.getBesitzer().equalsIgnoreCase("tba") && gs.getStatus().equalsIgnoreCase("zum Verkauf freigegeben")) {
        	zeile2 += "<span style=\"font-weight:bold;\">Preis: </span>"+ Finanzen.f.format(gs.getPreis())+Finanzen.kuerzel+"<br />" ;
        }
        if (!gs.getStatus().equalsIgnoreCase("Baustelle") && !gs.getStatus().equalsIgnoreCase("zum Verkauf freigegeben") && !gs.getStatus().equalsIgnoreCase("Wohnhaus")) {
        	System.out.println(gs.getStatus());
        	zeile2 = "<span style=\"font-weight:bold;\">von </span>"+Finanzen.capFirst(Finanzen.getStadtAtLoc(gs.getA()).getName())+"<br />";
			color = 0xFFD700;
        }
        if (gs.getStatus().equalsIgnoreCase("zum Verkauf freigegeben")) {
        	color = 0x53868B;
        }
        String anzeige = "<div class=\"infowindow\">" +
				"<span style=\"font-size:120%;\"><span style =\"text-decoration:underline;\">"+status+"</span></span><br /><br />" +
				zeile2 +
				"<span style=\"font-weight:bold;\">ID: </span>"+gs.getID()+"<br />" +
				"<span style=\"font-weight:bold;\">Größe: </span>"+Finanzen.getMasse(gs.getID())+"<br />" +
				"</div>";
		m.setDescription(anzeige);
		m.setFillStyle(0.4, color);
		m.setLineStyle(2, 0.1, 0);
		String helper=Finanzen.getIconName(status);
		MarkerIcon icon = Finanzen.markerapi.getMarkerIcon(helper);
		Finanzen.setgebaeude.createMarker(gs.getID(), gs.getStatus(), gs.getSchildLoc().getWorld().getName(), gs.getSchildLoc().getX(), gs.getSchildLoc().getY(), gs.getSchildLoc().getZ(), icon, true);
		
	}

}
