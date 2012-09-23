package me.stuppsman.finanzen.commandExecutor;

import me.stuppsman.finanzen.Finanzen;
import me.stuppsman.finanzen.Stadt;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class FinanzenCE_grenzen implements CommandExecutor {

	
	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		if (cmd.getName().equalsIgnoreCase("grenzen")) {
			if (!(sender instanceof Player)) {
				System.out.println("Dieser Befehl ist nur für Spieler!");
				return true;
			}
			Player player = (Player) sender;
			String name = player.getName();
			
			if (!Finanzen.locA.containsKey(name) || !Finanzen.locB.containsKey(name)) {
				player.sendMessage(ChatColor.RED + "Du musst vorher einen Bereich festlegen!");
				return true;
			}
			Location a = Finanzen.locA.get(name);
			Location b = Finanzen.locB.get(name);
			if (Finanzen.getStadtAtLoc(player.getLocation()) == null) {
				player.sendMessage(ChatColor.RED + "Du musst auf dem alten Stadtgrundstück stehen, um die Grenzen zu erweitern!");
				return true;
			}
			Stadt stadt = Finanzen.getStadtAtLoc(player.getLocation());
			if (!stadt.getBesitzer().equalsIgnoreCase(name)) {
				player.sendMessage(ChatColor.RED + "Stadtgrenzen darf nur der Bürgermeister neu setzen!");
				return true;
			}
			double kosten = Finanzen.getStadtWert(a, b) - Finanzen.getStadtWert(stadt);
			if (stadt.getKasse()<kosten) {
				player.sendMessage(ChatColor.RED + "Die Stadt kann es sich nicht leisten, das neue Grundstück zu reservieren!");
				return true;
			}
			stadt.incStadtkasse((-1)*kosten);
			int X1 = a.getBlockX();
			int Z1 = a.getBlockZ();
			int X2 = b.getBlockX();
			int Z2 = b.getBlockZ();
			int zd = (Z1 > Z2) ? Z1 : Z2;
			int xd = (X1 > X2) ? X1 : X2;
			int za = (Z1 > Z2) ? Z2 : Z1;
			int xa = (X1 > X2) ? X2 : X1;
			stadt.setGrenzen(xa, za, xd, zd, player.getLocation().getWorld().getName());
			Bukkit.broadcastMessage(ChatColor.GOLD + "Die Stadt " + stadt.getName() + " hat seine Grenzen erweitert!");
			player.sendMessage(ChatColor.GOLD + "Kosten für die Stadt: " + Finanzen.f.format(kosten)+Finanzen.kuerzel);
			Finanzen.loadGebiete();
			return true;
			
		}
		return false;
	}

}
