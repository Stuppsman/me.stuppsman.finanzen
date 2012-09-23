package me.stuppsman.finanzen.commandExecutor;

import me.stuppsman.finanzen.Finanzen;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class FinanzenCE_id implements CommandExecutor {

	private Finanzen plugin;

	public FinanzenCE_id(Finanzen plugin) {
		this.plugin = plugin;
	}

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		
		if (cmd.getName().equalsIgnoreCase("ID")) {
			if (!(sender instanceof Player)) {
				System.out.println("Dieser Befehl ist nur für Spieler!");
				return true;
			}
			if (args.length > 0) {
				return false;
			}
			Player player = (Player) sender;
			if (!player.hasPermission("finanzen.member")) {
				player.sendMessage(ChatColor.RED + "Dazu fehlt dir die Berechtigung!");
				return true;
			}
			int ID = player.getItemInHand().getTypeId();
			int Data = player.getItemInHand().getData().getData();
			player.sendMessage(ChatColor.DARK_GRAY + plugin.getConfig().getString(ID + "."+ Data + ".name")+":   " + ID + ":" + Data);
			return true;
		}
		
		
		
		return false;
	}

}
