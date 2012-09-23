package me.stuppsman.finanzen.commandExecutor;

import me.stuppsman.finanzen.Finanzen;
import me.stuppsman.finanzen.GS;

import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class FinanzenCE_GS implements CommandExecutor {

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {

		if (cmd.getName().equalsIgnoreCase("gs")) {
			if (args.length == 0) {
				if (!(sender instanceof Player)) {
					System.out.println("Du musst im Spiel sein, um Informationen über das Grundstück zu erhalten, auf dem du dich befindest!");
					return true;
				}
				Player player = (Player) sender;
				Location loc = player.getLocation();
				for (GS gs : Finanzen.grundstuecke.values()) {
					if (gs.isOnGS(loc)) {
						sender.sendMessage(ChatColor.DARK_BLUE + "-----------------------------");
						sender.sendMessage(ChatColor.DARK_BLUE + "| " + ChatColor.BLUE +"Du befindest dich auf folgendem Grundstück: ");
						sender.sendMessage(ChatColor.DARK_BLUE + "-----------------------------");
						sender.sendMessage(ChatColor.DARK_BLUE + "| " + ChatColor.BLUE + "ID: " + ChatColor.YELLOW + gs.getID());
						if (gs.getStatus().equalsIgnoreCase("zum Verkauf freigegeben")) {
							sender.sendMessage(ChatColor.DARK_BLUE + "| " + ChatColor.BLUE + "Preis: " + ChatColor.YELLOW + Finanzen.f.format(gs.getPreis())+Finanzen.kuerzel);
							if (!gs.getBesitzer().equalsIgnoreCase("tba")) sender.sendMessage(ChatColor.DARK_BLUE + "| " + ChatColor.BLUE + "Verkäufer: " + ChatColor.YELLOW + gs.getBesitzer());
							
						}else {
							sender.sendMessage(ChatColor.DARK_BLUE + "| " + ChatColor.BLUE + "Besitzer: " + ChatColor.YELLOW + gs.getBesitzer());
						}
						int x = gs.getD().getBlockX() - gs.getA().getBlockX();
						int z = gs.getD().getBlockZ() - gs.getA().getBlockZ();
						int mx = (gs.getA().getBlockX() + gs.getD().getBlockX())/2;
						int mz = (gs.getA().getBlockZ() + gs.getD().getBlockZ())/2;
						sender.sendMessage(ChatColor.DARK_BLUE + "| " + ChatColor.BLUE + "Größe: " + ChatColor.YELLOW + (x+1) + "*" + (z+1));
						sender.sendMessage(ChatColor.DARK_BLUE + "| " + ChatColor.BLUE + "Mittelpunkt: "+ChatColor.YELLOW + "(" + mx+ ";"+mz + ")");
						sender.sendMessage(ChatColor.DARK_BLUE + "| " + ChatColor.BLUE + "Schildlocation: " + ChatColor.YELLOW + "("+ gs.getSchildLoc().getBlockX() + ";" + gs.getSchildLoc().getBlockY() + ";" + gs.getSchildLoc().getBlockZ() + ")");
						sender.sendMessage(ChatColor.DARK_BLUE + "| " + ChatColor.BLUE + "Status: " + ChatColor.YELLOW + gs.getStatus());
						sender.sendMessage(ChatColor.DARK_BLUE + "| " + ChatColor.DARK_BLUE + "----------------------------");
						return true;
					}
				}
				sender.sendMessage(ChatColor.DARK_BLUE + "| " + ChatColor.BLUE +"Du befindest dich momentan nicht auf einem markierten Grundstück!");
				return true;
			}
			if (args.length == 1) {
				if (args[0].equalsIgnoreCase("frei")) {
					sender.sendMessage(ChatColor.DARK_BLUE + "-----------------------------");
					sender.sendMessage(ChatColor.DARK_BLUE + "| " + ChatColor.BLUE +"Freie Grundstücke: ");
					sender.sendMessage(ChatColor.DARK_BLUE + "-----------------------------");
					for (GS gs : Finanzen.grundstuecke.values()) {
						if (!gs.getStatus().equalsIgnoreCase("zum Verkauf freigegeben")) {
							continue;
						}
						sender.sendMessage(ChatColor.DARK_BLUE + "| " + ChatColor.BLUE + "ID: " + ChatColor.YELLOW + gs.getID());
						sender.sendMessage(ChatColor.DARK_BLUE + "| " + ChatColor.BLUE + "Preis: " + ChatColor.YELLOW + Finanzen.f.format(gs.getPreis())+Finanzen.kuerzel);
						
						if (!gs.getBesitzer().equalsIgnoreCase("tba")) sender.sendMessage(ChatColor.DARK_BLUE + "| " + ChatColor.BLUE + "Verkäufer: " + ChatColor.YELLOW + gs.getBesitzer());
						
						int x = gs.getD().getBlockX() - gs.getA().getBlockX();
						int z = gs.getD().getBlockZ() - gs.getA().getBlockZ();
						int mx = (gs.getA().getBlockX() + gs.getD().getBlockX())/2;
						int mz = (gs.getA().getBlockZ() + gs.getD().getBlockZ())/2;
						sender.sendMessage(ChatColor.DARK_BLUE + "| " + ChatColor.BLUE + "Größe: " + ChatColor.YELLOW + (x+1) + "*" + (z+1));
						sender.sendMessage(ChatColor.DARK_BLUE + "| " + ChatColor.BLUE + "Mittelpunkt: "+ChatColor.YELLOW + "(" + mx+ ";"+mz +")");
						sender.sendMessage(ChatColor.DARK_BLUE + "| " + ChatColor.BLUE + "Schildlocation: " + ChatColor.YELLOW + "(" + gs.getSchildLoc().getBlockX() + ";" + gs.getSchildLoc().getBlockY() + ";" + gs.getSchildLoc().getBlockZ() + ")");
						sender.sendMessage(ChatColor.DARK_BLUE + "| " + ChatColor.BLUE + "Status: " + ChatColor.YELLOW + gs.getStatus());
						sender.sendMessage(ChatColor.DARK_BLUE + "| " + ChatColor.DARK_BLUE + "----------------------------");
						
					}
					return true;
				}
				if (args[0].equalsIgnoreCase("liste")) {
					sender.sendMessage(ChatColor.DARK_BLUE + "-----------------------------");
					sender.sendMessage(ChatColor.DARK_BLUE + "| " + ChatColor.BLUE +"Vergebene Grundstücke: ");
					sender.sendMessage(ChatColor.DARK_BLUE + "-----------------------------");
					for (GS gs : Finanzen.grundstuecke.values()) {
						if (gs.getStatus().equalsIgnoreCase("zum Verkauf freigegeben")) {
							continue;
						}
						sender.sendMessage(ChatColor.DARK_BLUE + "| " + ChatColor.BLUE + "ID: " + ChatColor.YELLOW + gs.getID());
						sender.sendMessage(ChatColor.DARK_BLUE + "| " + ChatColor.BLUE + "Besitzer: " + ChatColor.YELLOW + gs.getBesitzer());
						int x = gs.getD().getBlockX() - gs.getA().getBlockX();
						int z = gs.getD().getBlockZ() - gs.getA().getBlockZ();
						int mx = (gs.getA().getBlockX() + gs.getD().getBlockX())/2;
						int mz = (gs.getA().getBlockZ() + gs.getD().getBlockZ())/2;
						sender.sendMessage(ChatColor.DARK_BLUE + "| " + ChatColor.BLUE + "Größe: " + ChatColor.YELLOW + (x+1) + "*" + (z+1));
						sender.sendMessage(ChatColor.DARK_BLUE + "| " + ChatColor.BLUE + "Mittelpunkt: "+ChatColor.YELLOW + "(" + mx+ ";"+mz + ")");
						sender.sendMessage(ChatColor.DARK_BLUE + "| " + ChatColor.BLUE + "Schildlocation: " + ChatColor.YELLOW + "(" + gs.getSchildLoc().getBlockX() + ";" + gs.getSchildLoc().getBlockY() + ";" + gs.getSchildLoc().getBlockZ() + ")");
						sender.sendMessage(ChatColor.DARK_BLUE + "| " + ChatColor.BLUE + "Status: " + ChatColor.YELLOW + gs.getStatus());
						sender.sendMessage(ChatColor.DARK_BLUE + "| " + ChatColor.DARK_BLUE + "----------------------------");
						
					}
					return true;
				}
				if (args[0].equalsIgnoreCase("alle")) {
					sender.sendMessage(ChatColor.DARK_BLUE + "| " + ChatColor.DARK_BLUE + "----------------------------");
					sender.sendMessage(ChatColor.DARK_BLUE + "| " + ChatColor.BLUE + "Markierte Grundstücke:");
					sender.sendMessage(ChatColor.DARK_BLUE + "| " + ChatColor.DARK_BLUE + "----------------------------");
					for (GS gs : Finanzen.grundstuecke.values()) {
						sender.sendMessage(ChatColor.DARK_BLUE + "| " + ChatColor.BLUE + "ID: " + ChatColor.YELLOW + gs.getID());
						if (gs.getStatus().equalsIgnoreCase("zum Verkauf freigegeben")) {
							sender.sendMessage(ChatColor.DARK_BLUE + "| " + ChatColor.BLUE + "Preis: " + ChatColor.YELLOW + Finanzen.f.format(gs.getPreis())+Finanzen.kuerzel);
							if (!gs.getBesitzer().equalsIgnoreCase("tba")) sender.sendMessage(ChatColor.DARK_BLUE + "| " + ChatColor.BLUE + "Verkäufer: " + ChatColor.YELLOW + gs.getBesitzer());
							
						}else {
							sender.sendMessage(ChatColor.DARK_BLUE + "| " + ChatColor.BLUE + "Besitzer: " + ChatColor.YELLOW + gs.getBesitzer());
						}
						int x = gs.getD().getBlockX() - gs.getA().getBlockX();
						int z = gs.getD().getBlockZ() - gs.getA().getBlockZ();
						int mx = (gs.getA().getBlockX() + gs.getD().getBlockX())/2;
						int mz = (gs.getA().getBlockZ() + gs.getD().getBlockZ())/2;
						sender.sendMessage(ChatColor.DARK_BLUE + "| " + ChatColor.BLUE + "Größe: " + ChatColor.YELLOW + (x+1) + "*" + (z+1));
						sender.sendMessage(ChatColor.DARK_BLUE + "| " + ChatColor.BLUE + "Mittelpunkt: "+ChatColor.YELLOW + "(" + mx+ ";"+mz + ")");
						sender.sendMessage(ChatColor.DARK_BLUE + "| " + ChatColor.BLUE + "Schildlocation: " + ChatColor.YELLOW + "("+ gs.getSchildLoc().getBlockX() + ";" + gs.getSchildLoc().getBlockY() + ";" + gs.getSchildLoc().getBlockZ() + ")");
						sender.sendMessage(ChatColor.DARK_BLUE + "| " + ChatColor.BLUE + "Status: " + ChatColor.YELLOW + gs.getStatus());
						sender.sendMessage(ChatColor.DARK_BLUE + "| " + ChatColor.DARK_BLUE + "----------------------------");
						
					}
					
					return true;
				}
			}
		}
		return false;
	}

}
