package me.stuppsman.finanzen.commandExecutor;

import me.stuppsman.finanzen.Finanzen;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

public class FinanzenCE_delgs implements CommandExecutor {

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		
		if (cmd.getName().equalsIgnoreCase("delgs")) {
			if (args.length != 1) {
				return false;
			}
			if (!Finanzen.GSDB.hasIndex(args[0])) {
				sender.sendMessage(ChatColor.RED + "Grundstück nicht gefunden!");
				return true;
			}
			
			Finanzen.GSSchilder.remove(Finanzen.getGS(args[0]).getSchildLoc());
			Finanzen.grundstuecke.remove(args[0]);
			Finanzen.GSDB.removeEntry(args[0]);
			
			sender.sendMessage(ChatColor.BLUE + "Grundstück " + args[0] + " wurde aus der Datenbank entfernt!");
			return true;
		}
		
		return false;
	}

}
