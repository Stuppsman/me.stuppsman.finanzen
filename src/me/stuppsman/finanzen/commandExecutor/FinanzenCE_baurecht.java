package me.stuppsman.finanzen.commandExecutor;

import me.stuppsman.finanzen.Finanzen;
import me.stuppsman.finanzen.GS;

import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class FinanzenCE_baurecht implements CommandExecutor {

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		if (cmd.getName().equalsIgnoreCase("baurecht")) {
			if (!(sender instanceof Player)) {
				System.out.println("Dieser Befehl ist nur für Spieler!");
				return true;
			}
			
			Player player = (Player) sender;
			String ziel = args[0];
			if (args.length != 1) {
				player.sendMessage(ChatColor.RED + "/baurecht <name> oder /baurecht -<name>");
				return true;
			}
			Location loc = player.getLocation();
			GS gs = Finanzen.getGSAtLoc(loc);
			if (gs == null) {
				player.sendMessage(ChatColor.RED + "Du musst dich auf deinem Grundstück befinden!");
				return true;
			}
			
			if (!(player.hasPermission("finanzen.admin") || gs.isGSBesitzer(player.getName()))) {
				player.sendMessage(ChatColor.RED + "Du darfst für dieses Grundstück keine Rechte einrichten!");
				return true;
			}
			if (!Finanzen.Finanzendb.hasIndex(ziel)) {
				if (ziel.startsWith("-")) {
					ziel = ziel.substring(1);
					if (Finanzen.Finanzendb.hasIndex(ziel)) {
						if (!gs.getBauer().contains(ziel)) {
							player.sendMessage(ChatColor.RED + "Der Spieler hat gar kein Baurecht auf diesem Grundstück!");
							return true;
						}
						gs.remBauer(ziel);
						player.sendMessage(ChatColor.GREEN + "Du hast dem Spieler " + ChatColor.YELLOW + ziel + ChatColor.GREEN + " die Baurechte für das Grundstück " + ChatColor.YELLOW + args[0] + ChatColor.GREEN + " entzogen!");
						return true;
					}
					
				}
				player.sendMessage(ChatColor.RED + "Der Spieler existiert nicht!");
				return false;
			}
			if (!gs.getBauer().isEmpty()) {
				if (gs.getBauer().contains(ziel)) {
					player.sendMessage(ChatColor.RED + "Der Spieler hat schon Baurechte auf diesem Grundstück!");
					return true;
				}
			}
			gs.addBauer(ziel);
			player.sendMessage(ChatColor.GREEN + "Du hast dem Spieler " + ChatColor.YELLOW + args[1] + ChatColor.GREEN + " Baurechte für das Grundstück " + ChatColor.YELLOW + args[0] + ChatColor.GREEN + " gegeben!");
			return true;
		
		}
		
		return false;
	}

}
