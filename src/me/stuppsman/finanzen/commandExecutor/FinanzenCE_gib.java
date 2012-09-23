package me.stuppsman.finanzen.commandExecutor;

import me.stuppsman.finanzen.Finanzen;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class FinanzenCE_gib implements CommandExecutor {

	private Finanzen plugin;
	public FinanzenCE_gib(Finanzen plugin) {
		this.plugin = plugin;
	}
	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		if (cmd.getName().equalsIgnoreCase("gib")) {
			if (!(sender instanceof Player)) {
				System.out.println("Dieser Befehl ist nur für Spieler!");
				return true;
			}
			Player player = (Player) sender;
			String name = player.getName().toLowerCase();
			if (args.length == 0) {
				player.sendMessage(ChatColor.GRAY + "/gib <Spieler> <Geld>");
				return true;
			}
			
			if (args.length == 1) {
				if (!player.hasPermission("finanzen.admin")) {
					player.sendMessage(ChatColor.RED + "Dazu fehlt dir die Berechtigung!");
					return false;
				}
				//#Farben: &0:schwarz,&1:dunkelblau,&2dunkelgrün,&3:dunkelcyan,&4:dunkelrot,&5:dunkellila,&6:orange,
				//#&7:grau,&8:dunkelgrau,&9:blau,&a:grün,&b:cyan,&c:rot,&d:rosa,&e:gelb,&f:weiss

				try {
					double amount = new Double(args[0]);
					Finanzen.incKonto(name, amount);
					plugin.addU(name, plugin.getConfig().getString("Servername"), amount);
					player.sendMessage(ChatColor.GREEN + "Deinem Konto wurden "+ChatColor.DARK_GREEN + Finanzen.f.format(amount) + Finanzen.kuerzel + ChatColor.GREEN + " gutgeschrieben!");
					return true;
					
				}catch (NumberFormatException e) {
					player.sendMessage(ChatColor.RED + "Das war keine Zahl!");
					return true;
				}
			}
			if (args.length == 2) {
				if (!player.hasPermission("finanzen.member")) {
					player.sendMessage(ChatColor.RED + "Dafür fehlt dir die Berechtigung!");
					return true;
				}
				String empf = args[0].toLowerCase();
				if (!Finanzen.Finanzendb.hasIndex(empf)) {
					player.sendMessage(ChatColor.RED + "Spieler nicht gefunden!");
					return false;
				}
				if (empf.equalsIgnoreCase(name)) {
					player.sendMessage(ChatColor.RED + "Du kannst dir selbst nichts überweisen!");
					return true;
				}
				try {
					double amount = new Double(args[1]);
					if (amount>Finanzen.Finanzendb.getDouble(name, "konto")) {
						player.sendMessage(ChatColor.RED + "Dein Geld reicht leider nicht aus!");
						return true;
					}
					if (amount <= 0) {
						player.sendMessage(ChatColor.RED + "Du kannst keine negativen Beträge überweisen!");
						return true;
					}
					Finanzen.incKonto(empf, amount);
					Finanzen.incKonto(name, amount*(-1));
					plugin.addU(empf, name, amount);
					if (plugin.getServer().getPlayer(empf) != null) {
						plugin.getServer().getPlayer(empf).sendMessage(ChatColor.GREEN + "Der Spieler " + ChatColor.YELLOW + player.getName() + ChatColor.GREEN + " hat dir " + ChatColor.DARK_GREEN +  Finanzen.f.format(amount) + Finanzen.kuerzel + ChatColor.GREEN + " überwiesen.");
						plugin.getServer().getPlayer(empf).sendMessage(ChatColor.DARK_AQUA + "Dein neuer Kontostand: " + Finanzen.f.format(Finanzen.Finanzendb.getDouble(empf, "konto")) + Finanzen.kuerzel);
					}
					player.sendMessage(ChatColor.GREEN + "Du hast dem Spieler " + ChatColor.YELLOW + plugin.getServer().getOfflinePlayer(empf).getName()+ " " + ChatColor.DARK_GREEN +  Finanzen.f.format(amount)  + Finanzen.kuerzel + ChatColor.GREEN + " überwiesen.");
					player.sendMessage(ChatColor.DARK_AQUA + "Dein neuer Kontostand: " + Finanzen.f.format(Finanzen.Finanzendb.getDouble(name, "konto")) + Finanzen.kuerzel);
					return true;
				}catch (NumberFormatException e) {
					player.sendMessage(ChatColor.RED + "Erst den Spielernamen, dann den Betrag!");
					return false;
				}
				
			}
			if (args.length == 3 && args[2].equalsIgnoreCase("admin")) {
				if (!player.hasPermission("finanzen.admin")) {
					player.sendMessage(ChatColor.RED + "Dafür fehlt dir die Berechtigung!");
					return false;
				}
				String empf = args[0].toLowerCase();
				if (!Finanzen.Finanzendb.hasIndex(empf)) {
					player.sendMessage(ChatColor.RED + "Spieler nicht gefunden!");
					return false;
				}
				try {
					double amount = new Double(args[1]);
					Finanzen.incKonto(empf, amount);
					plugin.addU(empf, plugin.getConfig().getString("Servername"), amount);
					player.sendMessage(ChatColor.GREEN + "Du hast dem Spieler "+ ChatColor.YELLOW + plugin.getServer().getOfflinePlayer(empf).getName() + " " + ChatColor.DARK_GREEN + Finanzen.f.format(amount)  + Finanzen.kuerzel + ChatColor.GREEN + " ercheatet!");
					Player ziel = plugin.getServer().getPlayer(empf);
					if (ziel != null) {
						ziel.sendMessage(ChatColor.GREEN + "Deinem Konto wurden "+ChatColor.DARK_GREEN + Finanzen.f.format(amount)  + Finanzen.kuerzel + ChatColor.GREEN + " gutgeschrieben!");
					}
					return true;
					
				} catch (NumberFormatException e) {
					player.sendMessage(ChatColor.RED + "Das war keine Zahl!");
					return true;
				}
				
			}
		}
		return false;
	}

	

}
