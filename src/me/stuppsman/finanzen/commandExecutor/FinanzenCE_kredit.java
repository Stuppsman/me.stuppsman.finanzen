package me.stuppsman.finanzen.commandExecutor;

import me.stuppsman.finanzen.Finanzen;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class FinanzenCE_kredit implements CommandExecutor {

	private Finanzen plugin;

	public FinanzenCE_kredit(Finanzen plugin) {
		this.plugin=plugin;
	}

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		if (cmd.getName().equalsIgnoreCase("kredit")) {
			if (!(sender instanceof Player)) {
				System.out.println("Der Befehl ist nur für Spieler!");
				return true;
			}
			Player player = (Player) sender;
			String index = player.getName().toLowerCase(); 
			/*  
		 
  kredit:
    description: Zeigt dir alle Infos zu Krediten an.
    permission: finanzen.kredit
    usage: /<command>
*/
			if (args.length == 0) {
				player.sendMessage(ChatColor.DARK_AQUA + "---------------------------");
				player.sendMessage(ChatColor.DARK_AQUA + "= "+ChatColor.AQUA+"Offene Kreditanfragen: "+ChatColor.DARK_AQUA+"=");
				player.sendMessage(ChatColor.DARK_AQUA + "---------------------------");
				for(String name : Finanzen.Finanzendb.getIndices()) {
					if (Finanzen.Finanzendb.hasKey(name, "kredit")) {
						if (!Finanzen.Finanzendb.getString(name, "kredit").contains(":")) {
							player.sendMessage(ChatColor.DARK_AQUA + "| " + ChatColor.YELLOW + name + ChatColor.AQUA+": " + Finanzen.Finanzendb.getString(name, "kredit"));
						}
					}
				}
				player.sendMessage(ChatColor.DARK_AQUA + "---------------------------");
				player.sendMessage(ChatColor.DARK_AQUA + "= "+ChatColor.AQUA+"Dein Kreditstatus:"+ChatColor.DARK_AQUA+" =");
				player.sendMessage(ChatColor.DARK_AQUA + "---------------------------");
				if (Finanzen.Finanzendb.hasKey(index, "kredit")) {
					if (Finanzen.Finanzendb.getString(index, "kredit").contains(":")) {
						String[] kredi = Finanzen.Finanzendb.getString(index, "konto").split(":");
						try {
							String schulden = new Double(kredi[0]).toString();
							String glaeubiger = kredi[1];
							player.sendMessage(ChatColor.DARK_AQUA + "| Du hast noch "+ChatColor.AQUA+ schulden + ChatColor.DARK_AQUA+" Schulden bei "+ChatColor.YELLOW+ glaeubiger + ChatColor.DARK_AQUA+".");
							
							
						}catch (NumberFormatException e) {
							player.sendMessage(ChatColor.RED + "Bitte kontaktiere Stuppsman, hier liegt ein Fehler beim Speichern der Kredite vor!");
							return true;
						}
					}else {
						player.sendMessage(ChatColor.DARK_AQUA + "| Du hast eine Kreditanfrage über "+ ChatColor.AQUA + Finanzen.Finanzendb.getString(index, "kredit") + ChatColor.DARK_AQUA + " eingetragen.");
						int i = 1;
						if (Finanzen.Finanzendb.hasKey(index, "kreditangebot1")) {
							player.sendMessage(ChatColor.DARK_AQUA + "| Darauf haben sich folgende Spieler gemeldet: ");
						}
						while (Finanzen.Finanzendb.hasKey(index, "kreditangebot"+i)) {
							String[] kreditangebot = Finanzen.Finanzendb.getString(index, "kreditangebot"+i).split(":");
							String bieter = kreditangebot[0];
							String zinssatz = kreditangebot[1];
							player.sendMessage(ChatColor.DARK_AQUA + "| "+ ChatColor.YELLOW + bieter + ChatColor.DARK_AQUA + " bietet dir einen Zinssatz von "+ChatColor.AQUA + zinssatz+ChatColor.DARK_AQUA+"% an.");
							i++;
						}
						
					}
				}else {
					player.sendMessage(ChatColor.DARK_AQUA + "Du hast momentan keine Kredite!");
				}
			
				player.sendMessage(ChatColor.DARK_AQUA + "---------------------------");
				player.sendMessage(ChatColor.DARK_AQUA + "= "+ChatColor.AQUA+"Dein Guthaben:"+ChatColor.DARK_AQUA+" =");
				player.sendMessage(ChatColor.DARK_AQUA + "---------------------------");
				for(String name : Finanzen.Finanzendb.getIndices()) {
					if (Finanzen.Finanzendb.hasKey(name, "kredit")) {
						if (Finanzen.Finanzendb.getString(name, "kredit").contains(":"+player.getName())) {
							String[] kredi = Finanzen.Finanzendb.getString(index, "kredit").split(":");
							try {
								String guthaben = new Double(kredi[0]).toString();
								player.sendMessage(ChatColor.DARK_AQUA + "| Du hast noch "+ChatColor.GREEN + guthaben+ ChatColor.DARK_AQUA+" Guthaben bei " + name + ".");
								return true;
							}catch (NumberFormatException e) {
								player.sendMessage(ChatColor.RED + "Bitte kontaktiere Stuppsman, hier liegt ein Fehler beim Speichern der Kredite vor!");
								return true;
							}
							
						}
					}
				}
				player.sendMessage(ChatColor.DARK_AQUA + "---------------------------");
				return true;
			}
/*  kredit anfrage <geld>:
    description: Schreibt eine Kreditanfrage in die Datenbank.
    permission: finanzen.kredit
    usage: /<command>*/
			if (args.length == 2) {
				if (args[0].equalsIgnoreCase("anfrage")) {
					if (Finanzen.Finanzendb.hasKey(index, "Kredit")) {
						if (Finanzen.Finanzendb.getString(index, "kredit").contains(":")) {
							player.sendMessage(ChatColor.RED + "Du musst erst deine Schulden bezahlen, bevor du einen neuen Kredit aufnehmen darfst.");
							return true;
						}
					}
					try {
						String value = new Double(args[1]).toString();
						Finanzen.Finanzendb.setValue(index, "Kredit", value);
						player.sendMessage(ChatColor.DARK_AQUA + "Du hast eine Kreditanfrage über " + ChatColor.AQUA + value + ChatColor.DARK_AQUA + " in die Datenbank geschrieben!");
						return true;
					}catch (NumberFormatException e) {
						player.sendMessage(ChatColor.RED + "Das war keine Zahl!");
						return false;
					}	
				}
/*
  kredit <spieler> <zinssatz>:
    description: Macht dem anfragenden Spieler ein Angebot, sich das Geld zu einmaligen Zinsen zu leihen.
    permission: finanzen.kredit
    usage: /<command>*/			
				else {
					String ziel = args[0].toLowerCase();
					if (!Finanzen.Finanzendb.hasIndex(ziel)) {
						player.sendMessage(ChatColor.RED + "Diesen Spieler gibt es nicht in der Datenbank!");
						return true;
					}
					if (Finanzen.Finanzendb.hasKey(ziel, "kredit")) {
						if (Finanzen.Finanzendb.getString(ziel, "kredit").contains(":")) {
							player.sendMessage(ChatColor.RED + "Dieser Kredit wurde schon vergeben!");
							return true;
						}else {
							try {
								double betrag = new Double(Finanzen.Finanzendb.getString(ziel, "kredit"));
								double kontostand = new Double(Finanzen.Finanzendb.getString(index, "konto"));
								
								if (betrag > kontostand) {
									player.sendMessage(ChatColor.RED + "Wirklich nett von dir, aber das kannst du dir nicht leisten!");
									return true;									
								}
								kontostand -= betrag;
								Finanzen.Finanzendb.setValue(index, "konto", String.valueOf(kontostand));
								int i = 1;
								while (Finanzen.Finanzendb.hasKey(ziel, ("kreditangebot"+i).toString())) {
									i++;
								}
								try {
									double zinssatz = new Double(args[1]);
									if (zinssatz > plugin.getConfig().getInt("maxZinssatz")) {
										player.sendMessage(ChatColor.RED + "Der Zinssatz darf nicht über " +plugin.getConfig().getInt("maxZinssatz") + " liegen!");
										return true;
									}
									String value = index + ":" +zinssatz;
									Finanzen.Finanzendb.setValue(ziel, "kreditangebot"+i, value);
									player.sendMessage(ChatColor.DARK_AQUA + "Du hast dem Spieler " + ChatColor.YELLOW + plugin.getServer().getOfflinePlayer(ziel).getName() + " ein Kreditangebot gemacht.");
									if (plugin.getServer().getPlayer(ziel) != null) {
										plugin.getServer().getPlayer(ziel).sendMessage(ChatColor.DARK_AQUA + "Dir wurde ein Kreditangebot gemacht, schaue unter '/kredit'.");
									}
									return true;
								}catch (NumberFormatException e) {
									player.sendMessage(ChatColor.RED + "Zinssätze werden in Prozent angegeben und dürfen nicht über "+plugin.getConfig().getInt("maxZinssatz")+"% liegen.");
									return true;
								}
								
							}catch (NumberFormatException e) {
								player.sendMessage(ChatColor.RED + "Bitte kontaktiere Stuppsman, hier liegt ein Fehler beim Speichern der Kredite vor!");
								return true;
							}
						}
					}
				}
				
				/*
				  kredit von <spieler> annehmen:
				    description: Nimmt das Leihangebot eines Spielers an.
				    permission: finanzen.kredit
				    usage: /<command>
				  kredit bezahlen:
				    description: Zahlt dem Spieler das geliehene Geld zurueck.
				    permission: finanzen.kredit
				    usage: /<command>*/								
				
			}//length == 2
			if (args.length == 3 && args[0].equalsIgnoreCase("von") && args[2].equalsIgnoreCase("annehmen")) {
				String glaeubiger = args[1];
				if (!Finanzen.Finanzendb.hasIndex(glaeubiger)) {
					player.sendMessage(ChatColor.RED + "Spieler nicht gefunden!");
					return true;
				}
				
			}
		}
		return false;
	}

}
