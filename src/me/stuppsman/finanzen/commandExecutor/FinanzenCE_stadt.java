package me.stuppsman.finanzen.commandExecutor;

import me.stuppsman.finanzen.Finanzen;
import me.stuppsman.finanzen.Stadt;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.OfflinePlayer;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class FinanzenCE_stadt implements CommandExecutor {

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		if (cmd.getName().equalsIgnoreCase("stadt")) {
			if (args.length == 0) {
				if (!(sender instanceof Player)) {
					System.out.println("Um Informationen über die Stadt zu bekommen, in der du dich befindest, musst du dich schon ins Spiel einloggen.");
					return true;
				}
				Player player =(Player)sender;
				Stadt stadt = Finanzen.getStadtAtLoc(player.getLocation());
				if (stadt == null) {
					player.sendMessage(ChatColor.RED + "Du befindest dich momentan in der Wildnis.");
					return true;
				}
				sender.sendMessage(ChatColor.DARK_BLUE + "| " + ChatColor.DARK_BLUE + "----------------------------");
				sender.sendMessage(ChatColor.DARK_BLUE + "| " + ChatColor.BLUE + "Momentan befindest du dich in: " + ChatColor.YELLOW + Finanzen.capFirst(stadt.getName()));
				sender.sendMessage(ChatColor.DARK_BLUE + "| " + ChatColor.DARK_BLUE + "----------------------------");
				sender.sendMessage(ChatColor.DARK_BLUE + "| " + ChatColor.BLUE + "Bürgermeister: " + ChatColor.YELLOW + stadt.getBesitzer());
				sender.sendMessage(ChatColor.DARK_BLUE + "| " + ChatColor.BLUE + "Bewohner: " + ChatColor.YELLOW + stadt.getBewohner().size());
				sender.sendMessage(ChatColor.DARK_BLUE + "| " + ChatColor.BLUE + "Stadtkasse: " + ChatColor.YELLOW + Finanzen.f.format(stadt.getKasse()) + Finanzen.kuerzel);
				int mx = (stadt.getA().getBlockX() + stadt.getD().getBlockX())/2;
				int mz = (stadt.getA().getBlockZ() + stadt.getD().getBlockZ())/2;
				sender.sendMessage(ChatColor.DARK_BLUE + "| " + ChatColor.BLUE + "Größe: " + ChatColor.YELLOW + Finanzen.getMasse(stadt.getA(), stadt.getD()));
				sender.sendMessage(ChatColor.DARK_BLUE + "| " + ChatColor.BLUE + "Mittelpunkt: "+ChatColor.YELLOW + "(" + mx+ ";"+mz + ")");
				sender.sendMessage(ChatColor.DARK_BLUE + "| " + ChatColor.BLUE + "Status: " + ChatColor.YELLOW + stadt.getStatus());
				sender.sendMessage(ChatColor.DARK_BLUE + "| " + ChatColor.DARK_BLUE + "----------------------------");
				
				return true;
			}
			if (args.length == 2) {
				if (args[0].equalsIgnoreCase("rat")) {
					if (!(sender instanceof Player)) {
						System.out.println("Um Ratsmitglieder zu bestimmen, musst du dich im Spiel befinden.");
						return true;
					}
					Player player =(Player)sender;
					Stadt stadt = Finanzen.getStadtAtLoc(player.getLocation());
					if (stadt == null) {
						player.sendMessage(ChatColor.RED + "Du befindest dich momentan in der Wildnis.");
						return true;
					}
					if (!stadt.getBesitzer().equalsIgnoreCase(player.getName())) {
						player.sendMessage(ChatColor.RED + "Nur der Bürgermeister darf Ratsmitglieder bestimmen!");
						return true;
					}
					OfflinePlayer p = Bukkit.getServer().getOfflinePlayer(args[1]);
					if (p == null) {
						player.sendMessage(ChatColor.RED + "Spieler nicht gefunden!");
						return false;
					}
					stadt.addRatsmitglied(p.getName());
					player.sendMessage(ChatColor.YELLOW + p.getName() + ChatColor.GREEN + " gehört jetzt dem Stadtrat von " +ChatColor.GOLD +  stadt.getName() + ChatColor.GREEN + " an.");
					return true; 
					
				}
				
			}
			if (args.length != 1) {
				return false;
			}
			if (args[0].equalsIgnoreCase("liste")) {
				if (Finanzen.staedte.values().isEmpty()) {
					sender.sendMessage(ChatColor.DARK_BLUE + "Bisher gibt es keine Städte auf diesem Server!");
					return true;
				}
				sender.sendMessage(ChatColor.DARK_BLUE + "| " + ChatColor.DARK_BLUE + "----------------------------");
				sender.sendMessage(ChatColor.DARK_BLUE + "| " + ChatColor.BLUE + "Städte:");
				sender.sendMessage(ChatColor.DARK_BLUE + "| " + ChatColor.DARK_BLUE + "----------------------------");
				
				for (Stadt stadt : Finanzen.staedte.values()) {
					sender.sendMessage(ChatColor.DARK_BLUE + "| " + ChatColor.BLUE + "Name: " + ChatColor.YELLOW + Finanzen.capFirst(stadt.getName()));
					sender.sendMessage(ChatColor.DARK_BLUE + "| " + ChatColor.BLUE + "Bürgermeister: " + ChatColor.YELLOW + stadt.getBesitzer());
					sender.sendMessage(ChatColor.DARK_BLUE + "| " + ChatColor.BLUE + "Bewohner: " + ChatColor.YELLOW + stadt.getBewohner().size());
					sender.sendMessage(ChatColor.DARK_BLUE + "| " + ChatColor.BLUE + "Stadtkasse: " + ChatColor.YELLOW + Finanzen.f.format(stadt.getKasse()) + Finanzen.kuerzel);
					int mx = (stadt.getA().getBlockX() + stadt.getD().getBlockX())/2;
					int mz = (stadt.getA().getBlockZ() + stadt.getD().getBlockZ())/2;
					sender.sendMessage(ChatColor.DARK_BLUE + "| " + ChatColor.BLUE + "Größe: " + ChatColor.YELLOW + Finanzen.getMasse(stadt.getA(), stadt.getD()));
					sender.sendMessage(ChatColor.DARK_BLUE + "| " + ChatColor.BLUE + "Mittelpunkt: "+ChatColor.YELLOW + "(" + mx+ ";"+mz + ")");
					sender.sendMessage(ChatColor.DARK_BLUE + "| " + ChatColor.BLUE + "Status: " + ChatColor.YELLOW + stadt.getStatus());
					sender.sendMessage(ChatColor.DARK_BLUE + "| " + ChatColor.DARK_BLUE + "----------------------------");
					
				}
				
				return true;
			}
			if (args[0].equalsIgnoreCase("Lager")) {
				if (!(sender instanceof Player)) {
					System.out.println("Um Lagerkisten zu bestimmen, musst du dich im Spiel befinden.");
					return true;
				}
				Player player =(Player)sender;
				if (!Finanzen.lager.isEmpty()) {
					if (Finanzen.lager.contains(player.getName()))  {
						Finanzen.lager.remove(player.getName());
						player.sendMessage(ChatColor.GREEN + "Lagerkisten-Markierungsmodus beendet!");
						return true;
					}
				}
				Stadt stadt = Finanzen.getStadtAtLoc(player.getLocation());
				if (stadt == null) {
					player.sendMessage(ChatColor.RED + "Du befindest dich momentan in der Wildnis.");
					return true;
				}
				if (!stadt.getBesitzer().equalsIgnoreCase(player.getName())) {
					player.sendMessage(ChatColor.RED + "Nur der Bürgermeister darf das Stadtlager einrichten!");
					return true;
				}
				int maxNeue = stadt.getMaxKisten()-stadt.getLagerkisten().size(); 
				if (maxNeue <= 0) {
					player.sendMessage(ChatColor.RED + "Du kannst grade keine weiteren Lagerkisten bestimmen. Für jeden Bewohner der Stadt darfst du nur eine Kiste bestimmen. Einige Gebäude erhöhen die Kapazität zusätzlich.");
					return true;
				}
				Finanzen.lager.add(player.getName());
				player.sendMessage(ChatColor.GREEN + "Du kannst jetzt Lagerkisten bestimmen! (Noch: " + maxNeue + ")");
				player.sendMessage(ChatColor.GRAY + "(Dazu musst du sie links anklicken. Um den Modus zu verlassen, gib wieder /stadt lager ein!)");
				return true;
				
			}
			String stadtname = args[0];
			if (Finanzen.getStadt(stadtname)==null) {
				sender.sendMessage(ChatColor.RED + "Stadt nicht gefunden!");
				return true;
			}
			Stadt stadt = Finanzen.getStadt(stadtname);
			sender.sendMessage(ChatColor.DARK_BLUE + "| " + ChatColor.DARK_BLUE + "----------------------------");
			sender.sendMessage(ChatColor.DARK_BLUE + "| " + ChatColor.BLUE + "Informationen zu: " + ChatColor.YELLOW + Finanzen.capFirst(stadtname));
			sender.sendMessage(ChatColor.DARK_BLUE + "| " + ChatColor.DARK_BLUE + "----------------------------");
			sender.sendMessage(ChatColor.DARK_BLUE + "| " + ChatColor.BLUE + "Bürgermeister: " + ChatColor.YELLOW + stadt.getBesitzer());
			sender.sendMessage(ChatColor.DARK_BLUE + "| " + ChatColor.BLUE + "Bewohner: " + ChatColor.YELLOW + stadt.getBewohner().size());
			sender.sendMessage(ChatColor.DARK_BLUE + "| " + ChatColor.BLUE + "Stadtkasse: " + ChatColor.YELLOW + Finanzen.f.format(stadt.getKasse()) + Finanzen.kuerzel);
			int mx = (stadt.getA().getBlockX() + stadt.getD().getBlockX())/2;
			int mz = (stadt.getA().getBlockZ() + stadt.getD().getBlockZ())/2;
			sender.sendMessage(ChatColor.DARK_BLUE + "| " + ChatColor.BLUE + "Größe: " + ChatColor.YELLOW + Finanzen.getMasse(stadt.getA(), stadt.getD()));
			sender.sendMessage(ChatColor.DARK_BLUE + "| " + ChatColor.BLUE + "Mittelpunkt: "+ChatColor.YELLOW + "(" + mx+ ";"+mz + ")");
			sender.sendMessage(ChatColor.DARK_BLUE + "| " + ChatColor.BLUE + "Status: " + ChatColor.YELLOW + stadt.getStatus());
			sender.sendMessage(ChatColor.DARK_BLUE + "| " + ChatColor.DARK_BLUE + "----------------------------");
			return true;
		}
		
		return false;
	}

}
