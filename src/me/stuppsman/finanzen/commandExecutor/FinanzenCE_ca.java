package me.stuppsman.finanzen.commandExecutor;

import me.stuppsman.finanzen.Finanzen;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class FinanzenCE_ca implements CommandExecutor {

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {

		if (cmd.getName().equalsIgnoreCase("ca")) {
			if (!(sender instanceof Player)) {
				System.out.println("Dieser Befehl ist nur für Spieler!");
				return true;
			}
			Player player = (Player) sender;
			
			if (args.length == 0) {
				player.sendMessage(ChatColor.GRAY + "/ca <spielername> <anzahl> zieht dem Spieler Chillis ab.");
				return true;
			}
			if (args.length != 2) {
				player.sendMessage(ChatColor.GRAY + "/ca <spielername> <anzahl> zieht dem Spieler Chillis ab.");
				return true;
			}
			if (!Finanzen.Finanzendb.hasIndex(args[0])) {
				player.sendMessage(ChatColor.RED + "Dieser Spieler existiert in der Datenbank nicht!");
				return true;
			}
			try {
				int anzahl = Integer.parseInt(args[1]);
				int a = Finanzen.Finanzendb.getInt(args[0], "chillis");
				Finanzen.Finanzendb.setValue(args[0], "chillis", a-anzahl);
				player.sendMessage(ChatColor.GREEN + "Du hast dem Spieler " + ChatColor.YELLOW + args[0] +" " +ChatColor.GREEN+ anzahl + " Chillis abgezogen!");
				if (Bukkit.getServer().getPlayer(args[0]) != null) {
					Bukkit.getServer().getPlayer(args[0]).sendMessage(ChatColor.YELLOW + player.getName() + ChatColor.RED + " hat dir " + anzahl + " Chillis abgezogen.");
				}
				return true;
			}catch (NumberFormatException e) {
				player.sendMessage(ChatColor.RED + "Die Anzahl wurde falsch angegeben!");
				return false;
			}
		}
		return false;
	}

}
