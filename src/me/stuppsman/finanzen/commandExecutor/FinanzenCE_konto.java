package me.stuppsman.finanzen.commandExecutor;

import me.stuppsman.finanzen.Finanzen;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class FinanzenCE_konto implements CommandExecutor {

	private Finanzen plugin;
	
	public FinanzenCE_konto(Finanzen plugin) {
		this.plugin= plugin;
	}

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		if (!(sender instanceof Player)) {
			System.out.println("Dieser Befehl ist nur für Spieler!");
			return true;
		}
		Player player = (Player) sender;
		String name = player.getName().toLowerCase();
		if (cmd.getName().equalsIgnoreCase("konto")) {
			if (args.length == 0) {
				if (player.hasPermission("finanzen.kontoauszug")) {
					player.sendMessage(ChatColor.DARK_AQUA + "---------------------------");
					player.sendMessage(ChatColor.DARK_AQUA + "= Deine letzten Transaktionen: =");
					player.sendMessage(ChatColor.DARK_AQUA + "---------------------------");
					
					int i = 1;
					while (Finanzen.Finanzendb.hasKey(name, ("trans"+i).toString())){
						player.sendMessage(Finanzen.replaceColors(Finanzen.Finanzendb.getString(name, ("trans"+i)).toString()));
						i++;
					}
				}
				player.sendMessage(ChatColor.DARK_AQUA + "---------------------------");
				player.sendMessage(ChatColor.DARK_AQUA + "| Kontostand: " + ChatColor.AQUA + Finanzen.f.format(Finanzen.Finanzendb.getDouble(player.getName(), "konto")) + " " + Finanzen.waehrung);
				player.sendMessage(ChatColor.DARK_AQUA + "---------------------------");
				return true;
			}
			if (args.length == 1) {
				if (args[0].equalsIgnoreCase("reset")) {
					plugin.transReset(name.toLowerCase());
					return true;
				}
				if (args[0].equalsIgnoreCase("top")) {
					
					player.sendMessage(ChatColor.DARK_AQUA + "---------------------------");
					player.sendMessage(ChatColor.DARK_AQUA + "| "+ChatColor.AQUA+"Die "+Finanzen.config.getInt("maxReichste")+" reichsten Spieler auf " + Finanzen.replaceColors(Finanzen.config.getString("Servername")));
					player.sendMessage(ChatColor.DARK_AQUA + "---------------------------");
					Finanzen.reichste.clear();
					for (int i = 0; i < Finanzen.config.getInt("maxReichste"); i++) {
						String reich = Finanzen.reichster(Finanzen.reichste);
						if (reich.equalsIgnoreCase("noch niemand")) {
							break;
						}
						player.sendMessage(ChatColor.DARK_AQUA + "| "+ChatColor.YELLOW +reich+ " " + ChatColor.AQUA+Finanzen.f.format(Finanzen.Finanzendb.getDouble(reich, "konto"))+ Finanzen.kuerzel);
					}
					player.sendMessage(ChatColor.DARK_AQUA + "---------------------------");
					return true;
					
				}
				if (player.hasPermission("finanzen.admin")) {
					if (Finanzen.Finanzendb.hasIndex(args[0].toLowerCase())) {
						name = args[0].toLowerCase();
						player.sendMessage(ChatColor.DARK_AQUA + "---------------------------");
						player.sendMessage(ChatColor.DARK_AQUA + "= " + plugin.getServer().getOfflinePlayer(name).getName() + Finanzen.getHelper(name) + " letzte Transaktionen: =");
						player.sendMessage(ChatColor.DARK_AQUA + "---------------------------");

						int i = 1;
						while (Finanzen.Finanzendb.hasKey(name, ("trans" + i).toString())) {
							player.sendMessage(Finanzen.replaceColors(Finanzen.Finanzendb.getString(name, ("trans" + i)).toString()));
							i++;
						}
						player.sendMessage(ChatColor.DARK_AQUA + "---------------------------");
						player.sendMessage(ChatColor.DARK_AQUA + "| Kontostand: " + ChatColor.AQUA + Finanzen.f.format(Finanzen.Finanzendb.getDouble(name, "konto")) + " " + Finanzen.waehrung);
						player.sendMessage(ChatColor.DARK_AQUA + "---------------------------");
						return true;
					}
				}
			}
			
		}
		return false;
	}

}
