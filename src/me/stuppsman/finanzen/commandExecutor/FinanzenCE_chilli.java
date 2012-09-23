package me.stuppsman.finanzen.commandExecutor;

import me.stuppsman.finanzen.Finanzen;

import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class FinanzenCE_chilli implements CommandExecutor {

@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		
		if (cmd.getName().equalsIgnoreCase("chilli")) {
			
			if (!(sender instanceof Player)) {
				System.out.println("Dieser Befehl ist nur für Spieler!");
				return true;
			}
			if (args.length > 0 ) {
				return false;
			}
			
			Player player = (Player) sender;
			if (!Finanzen.chillis.isEmpty()) {
				if (Finanzen.chillis.contains(player.getName())) {
					player.sendMessage(ChatColor.RED + "Du hattest dein Chilli heute schon!");
					player.sendMessage(ChatColor.RED + "Das nächste gibt's erst nach dem nächsten Restart!");
					player.sendMessage(ChatColor.RED + "(Normalerweise um 5:00Uhr morgens!)");
					return true;
				}
			}
			player.sendMessage(ChatColor.RED + "Lecker.. aber scharf!"); 
			player.setFoodLevel(20);
			Location loc = player.getLocation();
			loc.setY(loc.getBlockY()+1);
			player.teleport(loc);
			player.setFireTicks(16);
			Finanzen.chillis.add(player.getName());
			int ch = Finanzen.Finanzendb.getInt(player.getName(), "chillis");
			Finanzen.Finanzendb.setValue(player.getName(), "chillis", ch+1);
			player.sendMessage(ChatColor.RED + "Das war deine " + (ch+1) + ". Chilli!");
			return true;
		
		}
		return false;
	}

}
