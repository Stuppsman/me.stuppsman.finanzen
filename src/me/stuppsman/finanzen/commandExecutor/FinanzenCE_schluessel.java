package me.stuppsman.finanzen.commandExecutor;

import me.stuppsman.finanzen.Finanzen;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class FinanzenCE_schluessel implements CommandExecutor {

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {

		if (cmd.getName().equalsIgnoreCase("schluessel")) {
			if (!(sender instanceof Player)) {
				System.out.println("Dieser Befehl ist nur für Spieler!");
				return true;
			}
			
			Player player = (Player) sender;
			String ziel = args[1];
			if (args.length != 2) {
				player.sendMessage(ChatColor.RED + "/schluessel <GS-ID> <name> vergibt das Recht. Um es wieder zu entziehen, den Namen mit einem Minus davor eingeben!");
				return true;
			}
			if (!Finanzen.GSDB.hasIndex(args[0])) {
				player.sendMessage(ChatColor.RED + "Grundstück nicht gefunden!");
				return true;
			}
			
			if (!(player.hasPermission("finanzen.admin") || Finanzen.grundstuecke.get(args[0]).isGSBesitzer(player.getName()))) {
				player.sendMessage(ChatColor.RED + "Du darfst für dieses Grundstück keine Rechte einrichten!");
				return true;
			}
			if (!Finanzen.Finanzendb.hasIndex(ziel)) {
				if (ziel.startsWith("-")) {
					ziel = ziel.substring(1);
					if (Finanzen.Finanzendb.hasIndex(ziel)) {
						if (!Finanzen.grundstuecke.get(args[0]).getSchluesselkinder().contains(ziel)) {
							player.sendMessage(ChatColor.RED + "Der Spieler hat gar keinen Schlüssel von dem Grundstück!");
							return true;
						}
						Finanzen.grundstuecke.get(args[0]).remSchluesselkind(ziel);
						player.sendMessage(ChatColor.GREEN + "Du hast dem Spieler " + ChatColor.YELLOW + ziel + ChatColor.GREEN + " die Redstonerechte für das Grundstück " + ChatColor.YELLOW + args[0] + ChatColor.GREEN + " entzogen!");
						return true;
					}
					
				}
				player.sendMessage(ChatColor.RED + "Der Spieler existiert nicht!");
				return false;
			}
			if (!Finanzen.grundstuecke.get(args[0]).getSchluesselkinder().isEmpty()) {
				if (Finanzen.grundstuecke.get(args[0]).getSchluesselkinder().contains(ziel)) {
					player.sendMessage(ChatColor.RED + "Der Spieler hat bereits Schlüssel von diesem Grundstück!");
					return true;
				}
			}
			Finanzen.grundstuecke.get(args[0]).addSchluesselkind(ziel);
			player.sendMessage(ChatColor.GREEN + "Du hast dem Spieler " + ChatColor.YELLOW + args[1] + ChatColor.GREEN + " Redstonerechte für das Grundstück " + ChatColor.YELLOW + args[0] + ChatColor.GREEN + " gegeben!");
			return true;
		
		}
		
		return false;
		
	}

}
