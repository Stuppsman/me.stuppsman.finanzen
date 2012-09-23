package me.stuppsman.finanzen.commandExecutor;

import me.stuppsman.finanzen.Finanzen;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class FinanzenCE_rsrecht implements CommandExecutor {

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		if (cmd.getName().equalsIgnoreCase("rsrecht")) {
			if (!(sender instanceof Player)) {
				System.out.println("Dieser Befehl ist nur für Spieler!");
				return true;
			}
			
			Player player = (Player) sender;
			String ziel = args[1];
			if (args.length != 2) {
				player.sendMessage(ChatColor.RED + "/rsrecht <GS-ID> <name>");
				return true;
			}
			if (!Finanzen.GSDB.hasIndex(ziel)) {
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
						if (!Finanzen.grundstuecke.get(args[0]).getRedstonerecht().contains(ziel)) {
							player.sendMessage(ChatColor.RED + "Der Spieler hat gar kein Redstonerecht auf diesem Grundstück!");
							return true;
						}
						Finanzen.grundstuecke.get(args[0]).remRedstoneRecht(ziel);
						player.sendMessage(ChatColor.GREEN + "Du hast dem Spieler " + ChatColor.YELLOW + ziel + ChatColor.GREEN + " die Redstonerechte für das Grundstück " + ChatColor.YELLOW + args[0] + ChatColor.GREEN + " entzogen!");
						return true;
					}
					
				}
				player.sendMessage(ChatColor.RED + "Der Spieler existiert nicht!");
				return false;
			}
			if (!Finanzen.grundstuecke.get(args[0]).getRedstonerecht().isEmpty()) {
				if (Finanzen.grundstuecke.get(args[0]).getRedstonerecht().contains(ziel)) {
					player.sendMessage(ChatColor.RED + "Der Spieler hat schon Redstonerechte auf diesem Grundstück!");
					return true;
				}
			}
			Finanzen.grundstuecke.get(args[0]).addRedstoneRecht(ziel);
			player.sendMessage(ChatColor.GREEN + "Du hast dem Spieler " + ChatColor.YELLOW + args[1] + ChatColor.GREEN + " Redstonerechte für das Grundstück " + ChatColor.YELLOW + args[0] + ChatColor.GREEN + " gegeben!");
			return true;
		
			
		}
		
		return false;
		
	}

}
