package me.stuppsman.finanzen.commandExecutor;

import me.stuppsman.finanzen.Finanzen;
import me.stuppsman.finanzen.Stadt;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.dynmap.markers.AreaMarker;

import com.xemsdoom.mexdb.exception.EmptyIndexException;
import com.xemsdoom.mexdb.system.Entry;

public class FinanzenCE_neuestadt implements CommandExecutor {

	
	private Finanzen plugin;

	public FinanzenCE_neuestadt(Finanzen plugin) {
		this.plugin = plugin;
	}

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {

		if (cmd.getName().equalsIgnoreCase("neuestadt")) {
			if (args.length != 1) {
				return false;
			}
			if (args.length == 1) {
				if (!(sender instanceof Player)) {
					System.out.println("Dieser Befehl ist nur für Spieler!");
					return true;
				}
				Player player = (Player) sender;
				String name = player.getName();
				String stadtname = args[0];
				if (!player.hasPermission("finanzen.neuestadt")) {
					player.sendMessage(ChatColor.RED + "Du hast nicht das Recht, eine neue Stadt zu gründen!");
					return true;
				}
				if (!Finanzen.locA.containsKey(name) || !Finanzen.locB.containsKey(name)) {
					player.sendMessage(ChatColor.RED + "Du musst vorher einen Bereich festlegen!");
					return true;
				}
				if (Finanzen.staedte.containsKey(stadtname)) {
					player.sendMessage(ChatColor.RED + "Stadtname schon vergeben!");
					return true;
				}
				if (Finanzen.Finanzendb.hasIndex(stadtname)) {
					player.sendMessage(ChatColor.RED + "Städte dürfen nicht wie Spieler heissen!");
					return true;
				}
				
				String besitzer = name;
				Location A = Finanzen.locA.get(name);
				Location D = Finanzen.locB.get(name);
				double preis = Finanzen.getStadtWert(A, D);
				if (preis < 2500*Finanzen.config.getDouble("Stadtkosten")) {
					player.sendMessage(ChatColor.RED + "Ein neues Stadtgrundstück muss mindestens 50x50 groß sein!");
					return true;
				}
				if (!player.hasPermission("finanzen.admin")) {
					if (Finanzen.Finanzendb.getDouble(name, "konto") < preis) {
						player.sendMessage(ChatColor.RED + "Das kannst du dir nicht leisten! (Kosten: " + preis + Finanzen.kuerzel + ")");
						return true;
					}
					Finanzen.incKonto(name, (-1)* preis);
					plugin.addStadt(besitzer, stadtname, preis);
				}
				Stadt stadt = new Stadt(stadtname, besitzer, preis, A, D);
				Finanzen.staedte.put(stadtname, stadt);
				addStadtKonto(stadt);
				markStadt(stadt);
				Bukkit.getServer().broadcastMessage(ChatColor.GOLD + "Eine neue Stadt namens " + ChatColor.YELLOW + stadtname + ChatColor.GOLD + " wurde von " + ChatColor.YELLOW + besitzer + ChatColor.GOLD + " gegründet!");
				stadtToDM(stadt);
				if (!player.hasPermission("finanzen.admin")) {
					player.sendMessage(ChatColor.AQUA + "Das hat dich " + Finanzen.f.format(preis) + " " + Finanzen.waehrung + " gekostet!");
				}else {
					player.sendMessage(ChatColor.AQUA + "Wert: " + Finanzen.f.format(preis) +" "+ Finanzen.waehrung);
				}
				return true;
			}
		}
		return false;
	}

	private void stadtToDM(Stadt stadt) {
		double[] x = new double[4];
        double[] z = new double[4];
        x[0] = stadt.getA().getX(); z[0] = stadt.getA().getZ();
        x[1] = stadt.getA().getX(); z[1] = stadt.getD().getZ()+1;
        x[2] = stadt.getD().getX()+1; z[2] = stadt.getD().getZ()+1;
        x[3] = stadt.getD().getX()+1; z[3] = stadt.getA().getZ();
        AreaMarker m = Finanzen.sets.createAreaMarker(stadt.getName(), Finanzen.capFirst(stadt.getName()), false, stadt.getA().getWorld().getName(), x, z, false);
		String stadtname = stadt.getName();
		String status = stadt.getStatus();
		String kasse = Finanzen.f.format(stadt.getKasse())+Finanzen.kuerzel;
		String bm = stadt.getBesitzer();
		String anzahl = String.valueOf(stadt.getBewohner().size());
		String groesse = Finanzen.getMasse(stadt.getA(), stadt.getD());
        String rat = stadt.getRat().toString();
		String anzeige = "<div class=\"infowindow\">" +
				"<span style=\"font-size:140%;\"><span style =\"text-decoration:underline;\">"+Finanzen.capFirst(stadtname)+"</span></span>  ("+status+")<br /><br />" +
				"<span style=\"font-weight:bold;\">Bürgermeister: </span>"+bm+"<br />" +
				"<span style=\"font-weight:bold;\">Rat: </span>"+rat+"<br />" +
				"<span style=\"font-weight:bold;\">Bewohner: </span>"+anzahl+"<br />" +
				"<span style=\"font-weight:bold;\">Stadtkasse: </span>"+kasse+"<br />" +
				"<span style=\"font-weight:bold;\">Größe: </span>"+groesse+"<br />" +
				"</div>";
		m.setDescription(anzeige);
		m.setFillStyle(0.4, 0x00008B);
		m.setLineStyle(2, 0.1, 0);
	}
	private void addStadtKonto(Stadt stadt) {
		try {
			Entry entry = new Entry(stadt.getName());
			entry.addValue("trans1", "&4Gründung: +" + stadt.getKasse() + Finanzen.kuerzel);
			entry.addValue("konto", stadt.getKasse());
			Finanzen.Finanzendb.addEntry(entry);
			
		} catch (EmptyIndexException e) {
			e.printStackTrace();
		}
		
	}
	private void markStadt(Stadt stadt) {
		Location a = stadt.getA(); 
		Location b = new Location(stadt.getA().getWorld(), stadt.getD().getBlockX(), 1, stadt.getA().getZ());
		Location c = stadt.getD();
		Location d = new Location(stadt.getA().getWorld(), stadt.getA().getBlockX(), 1, stadt.getD().getZ()); 
		zieheZaun(a,b);
		zieheZaun(b,c);
		zieheZaun(c,d);
		zieheZaun(d,a);
						
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
	private Location hoechsterFreier(Location a) {
		int max = a.getWorld().getMaxHeight();
		Location hochster = new Location(a.getWorld(), a.getBlockX(), max-1, a.getBlockZ());
		while (hochster.getBlock().getTypeId() == 0 || hochster.getBlock().getTypeId() == 18) {
			hochster.setY(hochster.getY()-1);
		}
		hochster.setY(hochster.getY()+1);
		return hochster;
	}
	
}
