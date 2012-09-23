package me.stuppsman.finanzen.commandExecutor;

import me.stuppsman.finanzen.Finanzen;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class FinanzenCE_chillis implements CommandExecutor {

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		if (cmd.getName().equalsIgnoreCase("chillis")) {
			if (!(sender instanceof Player)) {
				System.out.println("Dieser Befehl ist nur für Spieler!");
				return true;
			}
			Player player = (Player) sender;
			if (args.length > 1) {
				return false;
			}
			if (args.length == 0) {
				player.sendMessage(ChatColor.RED + "Momentan hast du " + ChatColor.DARK_RED + Finanzen.Finanzendb.getInt(player.getName(), "chillis") + ChatColor.RED + " Chillis!");
				if (!Finanzen.chillis.contains(player.getName())) {
					player.sendMessage(ChatColor.DARK_RED + "Du hast dir dein Chilli heut noch nicht abgeholt!");
				}
			}
			if (args.length == 1) {
				if (!player.hasPermission("finanzen.admin")) {
					player.sendMessage(ChatColor.RED + "Dazu fehlt dir leider die Berechtigung!");
					return false;
				}
				if (!Finanzen.Finanzendb.hasIndex(args[0])) {
					player.sendMessage(ChatColor.RED + "Spieler nicht in der Datenbank!");
					return true;
				}
				if (!Finanzen.Finanzendb.hasKey(args[0], "chillis")) {
					player.sendMessage(ChatColor.RED + "Der Spieler hat noch keine Chillis gesammelt!");
					return true;
				}
				
				player.sendMessage(ChatColor.DARK_RED + "Momentan hat "+ChatColor.YELLOW + args[0] + ChatColor.RED + " " + Finanzen.Finanzendb.getInt(args[0], "chillis") + ChatColor.DARK_RED + " Chillis!");
				return true;
			}
			return true;
		}
		return false;
	}

}
