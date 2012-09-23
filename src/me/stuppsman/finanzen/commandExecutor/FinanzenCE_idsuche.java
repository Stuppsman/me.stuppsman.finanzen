package me.stuppsman.finanzen.commandExecutor;

import java.util.Set;

import me.stuppsman.finanzen.Finanzen;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

public class FinanzenCE_idsuche implements CommandExecutor {

	
	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		
		if (cmd.getName().equalsIgnoreCase("idsuche")) {
			if (args.length == 1) {
				Set<String> bla = Finanzen.config.getKeys(true);
				for (String key : bla) {
					if (key.endsWith("name")) {
						if (Finanzen.config.getString(key).toLowerCase().contains(args[0].toLowerCase())) {
							sender.sendMessage(ChatColor.DARK_GRAY+Finanzen.config.getString(key) + "(" + key.substring(0, key.length()-5) + ")");
						}
					}
				}
				return true;
			}
			sender.sendMessage(ChatColor.RED + "Nur ein Argument angeben!");
			
			
		}
		return false;
	}

}
