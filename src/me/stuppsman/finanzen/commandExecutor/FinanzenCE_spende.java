package me.stuppsman.finanzen.commandExecutor;

import me.stuppsman.finanzen.Finanzen;
import me.stuppsman.finanzen.Stadt;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class FinanzenCE_spende implements CommandExecutor {

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {

		if (!(sender instanceof Player)) {
			System.out.println("Der Befehl ist nur für Spieler!");
			return true;
		}
		Player player = (Player) sender;
		if (cmd.getName().equalsIgnoreCase("spende")) {
			Stadt stadt = Finanzen.getStadtAtLoc(player.getLocation());
			if (stadt == null) {
				player.sendMessage(ChatColor.RED + "Du befindest dich grade in keiner Stadt, der du etwas spenden könntest!");
				return true;
			}
			if (args.length != 1) {
				player.sendMessage(ChatColor.RED + "/spende <geld>");
				return true;
			}
			try {
				double spende = new Double(args[0]);
				stadt.incStadtkasse(spende);
				Finanzen.incKonto(player.getName(), (-1)*spende);
				player.sendMessage(ChatColor.GREEN + "Du hast der Stadt " + Finanzen.capFirst(stadt.getName()) + " " + Finanzen.f.format(spende) + Finanzen.kuerzel + " gespendet!");
				
				return true;
			} catch (NumberFormatException e) {
				player.sendMessage(ChatColor.RED + "Spendenhöhe falsch angegeben!");
				return true;
			}
		}
		return false;
	}

}
