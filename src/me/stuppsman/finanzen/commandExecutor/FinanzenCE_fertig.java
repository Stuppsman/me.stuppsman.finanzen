package me.stuppsman.finanzen.commandExecutor;

import me.stuppsman.finanzen.Finanzen;
import me.stuppsman.finanzen.GS;
import me.stuppsman.finanzen.Stadt;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class FinanzenCE_fertig implements CommandExecutor {

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		if (cmd.getName().equalsIgnoreCase("fertig")) {
		
			if (!(sender instanceof Player)) {
				System.out.println("Dieser Befehl ist nur für Spieler!");
				return true;
			}
			Player player = (Player) sender;
			String name = player.getName();
			if (args.length == 1) {
				if (args[0].equalsIgnoreCase("mauer")) {
					Stadt stadt = Finanzen.getStadtAtLoc(player.getLocation());
					if (stadt == null) {
						player.sendMessage(ChatColor.RED + "Du musst in einer Stadt stehen, um die Mauer registrieren zu können!");
						return true;
					}
					stadt.setMauer();
				}
			}
			String GSID = Finanzen.GSFokus.get(player.getName());
			if (GSID == null) {
				player.sendMessage(ChatColor.RED + "Du musst erst ein Grundstück anwählen!");
				return true;
			}
			GS gs = Finanzen.getGS(GSID);
			if (args.length == 0) {
				if (gs.getBesitzer().equalsIgnoreCase("tba")) {
					player.sendMessage(ChatColor.RED + "Dieses Grundstück gehört noch niemandem, daher kannst du es nicht als fertig markieren!");
					return true;
				}
				gs.setStatus("Wohnhaus");
				player.sendMessage(ChatColor.BLUE + "Status erfolgreich auf \"Wohnhaus\" geändert!");
				for (Stadt stadt : Finanzen.staedte.values())	{
					if (Finanzen.isGSinStadt(gs, stadt)) {
						if (!stadt.getBewohner().isEmpty()) {
							if (!stadt.getBewohner().contains(name)) {
								stadt.addBewohner(name);
							}
						}else {
							stadt.addBewohner(name);
						}
					}
				}return true;

			}
			
			if (args.length == 1) {
				String neubau = args[0].toLowerCase();
				if (!Finanzen.config.isConfigurationSection("Gebaeude."+neubau)) {
					player.sendMessage(ChatColor.RED + "Gebäudetyp unbekannt!");
					return true;
				}
				Stadt stadt = Finanzen.getStadtAtLoc(player.getLocation());
				if (stadt == null) {
					player.sendMessage(ChatColor.RED + "Du bist grade in keiner Stadt!");
					return true;
				}
				int bewohneranzahl = stadt.getBewohner().size();
				int minAnzahl = Finanzen.config.getInt("Gebaeude."+neubau+".minBewohner");
				if (bewohneranzahl < minAnzahl) {
					player.sendMessage(ChatColor.RED + "Dafür hat die Stadt zu wenig Einwohner!");
					player.sendMessage(ChatColor.GRAY + "(Benötigte Bewohner für das Gebäude " + Finanzen.capFirst(neubau) + ": " + minAnzahl + ")");
					return true;
				}
				double kosten = Finanzen.config.getDouble("Gebaeude."+neubau+".kosten");
				if (kosten>stadt.getKasse()) {
					player.sendMessage(ChatColor.RED + "Das kann sich die Stadt nicht leisten!");
					return true;
				}
				
				if (stadt.setNeubau(neubau, gs)) {
					stadt.incStadtkasse((-1)*kosten);
					Bukkit.broadcastMessage(ChatColor.GOLD + Finanzen.capFirst(stadt.getName()) + " hat ein neues Gebäude: " + Finanzen.capFirst(neubau));
					player.sendMessage(ChatColor.GRAY + "Der Stadt wurden " + Finanzen.f.format(kosten)+Finanzen.kuerzel + " abgezogen!");
					if (neubau.equalsIgnoreCase("muehle") || neubau.equalsIgnoreCase("mühle")) {
						player.sendMessage(ChatColor.GRAY + "Setze jetzt ein Schild an die Stelle, wo die Mühle Weizensamen kaufen soll.");
						Finanzen.muehle.add(name);
					}
					if (neubau.equalsIgnoreCase("fischer")) {
						player.sendMessage(ChatColor.GRAY + "Setze jetzt ein Schild an die Stelle, wo der Fischer Fische verkaufen soll.");
						Finanzen.fischer.add(name);
					}
					if (neubau.equalsIgnoreCase("baecker") || neubau.equalsIgnoreCase("baeckerei")) {
						player.sendMessage(ChatColor.GRAY + "Setze jetzt ein Schild an die Stelle, wo der Bäcker Brot verkaufen soll.");
						Finanzen.baecker.add(name);
					}
					if (neubau.equalsIgnoreCase("mine")) {
						player.sendMessage(ChatColor.GRAY + "Setze jetzt ein Schild an die Stelle, wo die Mine Kohle verkaufen soll.");
						Finanzen.mine.add(name);
					}
					if (neubau.equalsIgnoreCase("fabrik1")) {
						player.sendMessage(ChatColor.GRAY + "Setze jetzt ein Schild an die Stelle, wo die Fabrik Redstone verkaufen soll.");
						Finanzen.fabrik1.add(name);
					}
					if (neubau.equalsIgnoreCase("fabrik2")) {
						player.sendMessage(ChatColor.GRAY + "Setze jetzt ein Schild an die Stelle, wo die Fabrik Kolben verkaufen soll.");
						Finanzen.fabrik2.add(name);
					}
					if (neubau.equalsIgnoreCase("fabrik3")) {
						player.sendMessage(ChatColor.GRAY + "Setze jetzt ein Schild an die Stelle, wo die Fabrik Klebekolben verkaufen soll.");
						Finanzen.fabrik3.add(name);
					}
					if (neubau.equalsIgnoreCase("tierheim")) {
						player.sendMessage(ChatColor.GRAY + "Setze jetzt ein Schild an die Stelle, wo das Tierheim Knochen kaufen soll.");
						Finanzen.tierheim.add(name);
					}
					return true;
				}else {
					player.sendMessage(ChatColor.RED + "Die Stadt erfüllt die Voraussetzungen nicht!");
					player.sendMessage(ChatColor.RED + "Nähere Infos unter /stadt");
					return true;
				}
				
				
			}
		}
		return false;
	}

}
