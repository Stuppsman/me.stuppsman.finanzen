package me.stuppsman.finanzen.commandExecutor;

import me.stuppsman.finanzen.Finanzen;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

public class FinanzenCE_delstadt implements CommandExecutor {

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		if (cmd.getName().equalsIgnoreCase("delstadt")) {
			if (args.length != 1) {
				sender.sendMessage(ChatColor.RED + "/delstadt <stadtname>");
				return false;
			}
			if (!Finanzen.staedte.containsKey(args[0])) {
				sender.sendMessage(ChatColor.RED + "Die Stadt existiert nicht!");
				return true;
			}
			Finanzen.staedte.remove(args[0]);
			Finanzen.StaedteDB.removeEntry(args[0]);
			Finanzen.Finanzendb.removeEntry(args[0]);
			sender.sendMessage(ChatColor.GREEN + "Die Stadt " + args[0] + " wurde gelöscht!");
			return true;
		}
		return false;
	}

}
