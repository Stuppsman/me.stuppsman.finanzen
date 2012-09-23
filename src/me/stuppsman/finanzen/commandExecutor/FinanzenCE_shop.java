package me.stuppsman.finanzen.commandExecutor;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import me.stuppsman.finanzen.Finanzen;

public class FinanzenCE_shop implements CommandExecutor {

	private Finanzen plugin;
	
	

	public FinanzenCE_shop(Finanzen plugin) {
		this.plugin = plugin;
	}



	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		if (cmd.getName().equalsIgnoreCase("shop")) {
			if (!(sender instanceof Player)) {
				System.out.println("Dieser Befehl ist nur für Spieler!");
				return true;
			}
			Player player = (Player) sender;
			String name = player.getName();
			if (args.length == 0) {
				player.sendMessage(ChatColor.DARK_GREEN + "--------------");
				player.sendMessage(ChatColor.DARK_GREEN + " | "+ChatColor.GREEN+"Kistenshops "+ChatColor.DARK_GREEN+"|");
				player.sendMessage(ChatColor.DARK_GREEN + "--------------");
				player.sendMessage(ChatColor.DARK_GREEN + "Mit "+ChatColor.GREEN+"'/shop neu' "+ChatColor.DARK_GREEN+"erstellst du einen neuen Shop.");
				player.sendMessage(ChatColor.DARK_GREEN + "Wenn du ihn fertig konfiguriert hast, können andere Spieler dort die eingestellte Ware kaufen/verkaufen.");
				player.sendMessage(ChatColor.DARK_GREEN + "Die Befehle lauten "+ChatColor.GREEN+"'/kaufe shop <anzahl>' "+ChatColor.DARK_GREEN+"und "+ChatColor.GREEN+"'/verkaufe shop <anzahl>'"+ChatColor.DARK_GREEN+".");
				player.sendMessage(ChatColor.DARK_GREEN + "Wenn du nicht möchtest, dass der Shop für dich (ver)kauft, gibst du als Preis 0 an.");
				player.sendMessage(ChatColor.DARK_GREEN + "Das Erstellen eines Shops kostet "+ChatColor.GREEN+ Finanzen.f.format(Finanzen.shopkosten) +" "+ Finanzen.waehrung+ChatColor.DARK_GREEN+".");
				return true;
					
				
			}
			if (args.length == 1) {
				
				if (args[0].equalsIgnoreCase("neu")) {
					if (Finanzen.shopbau.contains(name)) {
						
						Finanzen.shopbau.remove(name);
						player.sendMessage(ChatColor.GREEN + "Shopbau abgebrochen!");
						return true;
					}
						
					if (Finanzen.Finanzendb.getDouble(name, "konto") < Finanzen.shopkosten) {
						player.sendMessage(ChatColor.RED + "Du kannst dir momentan leider keinen Shop leisten!" + ChatColor.GRAY + " (Kosten: " + Finanzen.f.format(Finanzen.shopkosten) +Finanzen.kuerzel+")");
						return true;
					}
					Finanzen.shopbau.add(name);
					player.sendMessage(ChatColor.GREEN + "Setze ein leeres Schild an die Wand, an der du den Shop eröffnen möchtest.");
					player.sendMessage(ChatColor.GRAY + "(Eine Kiste wird automatisch darunter platziert!)");
					player.sendMessage(ChatColor.GRAY + "(Zum Abbrechen wieder 'shop neu' eingeben!");
					return true;
				}	
				
				if (args[0].equalsIgnoreCase("abreissen")) {
					if (Finanzen.ShopFokus.isEmpty()) {
						player.sendMessage(ChatColor.RED + "Du musst erst einen Shop auswählen!" + ChatColor.GRAY + " (rechtsklick)");
						return true;
					}
					if (!(Finanzen.ShopFokus.containsKey(name))) {
						player.sendMessage(ChatColor.RED + "Du musst erst einen Shop auswählen!" + ChatColor.GRAY + " (rechtsklick)");
						return true;
					
					}
					
					int ShopID = Finanzen.ShopFokus.get(name);
					if (!Finanzen.ShopHM.containsValue(ShopID)) {
						player.sendMessage(ChatColor.RED + "Du hast den Shop schon abgerissen!");
						return true;
					}
					
					if (!Finanzen.getBesitzer(ShopID).equalsIgnoreCase(player.getName()) && !player.hasPermission("finanzen.admin")) {
						player.sendMessage(ChatColor.RED + "Du kannst nur eigene Shops abreissen!");
						return true;
					}
					
					if (!Finanzen.isAdminShop(ShopID)) {
						Finanzen.incKonto(name, Finanzen.shoperstattung);
						plugin.addSVK(name);
						player.sendMessage(ChatColor.GREEN + "Shop erfolgreich abgerissen!" + ChatColor.AQUA + " (+"+Finanzen.f.format(Finanzen.shoperstattung)+Finanzen.kuerzel+")");
						Finanzen.removeShop(ShopID);
						return true;
					}
					Finanzen.removeShop(ShopID);
					player.sendMessage(ChatColor.GREEN + "Shop erfolgreich abgerissen!");
					
					return true;
				}
				if (args[0].equalsIgnoreCase("verstellen") || args[0].equalsIgnoreCase("umstellen")) {
					if (Finanzen.ShopFokus.isEmpty()) {
						player.sendMessage(ChatColor.RED + "Du musst erst einen Shop auswählen!" + ChatColor.GRAY + " (rechtsklick)");
						return true;
					}
					if (!(Finanzen.ShopFokus.containsKey(name))) {
						player.sendMessage(ChatColor.RED + "Du musst erst einen Shop auswählen!" + ChatColor.GRAY + " (rechtsklick)");
						return true;
					
					}
					if (Finanzen.Finanzendb.getDouble(name, "konto") < Finanzen.shopumstellkosten) {
						player.sendMessage(ChatColor.RED + "Du kannst dir momentan leider nicht leisten, deinen Shop zu verstellen!" + ChatColor.GRAY + " (Kosten: " + Finanzen.f.format(Finanzen.shopumstellkosten) +Finanzen.kuerzel+")");
						return true;
					}
					int ShopID = Finanzen.ShopFokus.get(name);
					if (!Finanzen.ShopHM.containsValue(ShopID)) {
						player.sendMessage(ChatColor.RED + "Der Shop existiert nicht mehr!");
						return true;
					}
					if (!Finanzen.getBesitzer(ShopID).equalsIgnoreCase(player.getName())) {
						player.sendMessage(ChatColor.RED + "Du kannst nur eigene Shops verstellen!");
						return true;
					}
					Finanzen.removeShop(ShopID);
					Finanzen.shopbau.add(name);
					Finanzen.shopumstellen.add(name);
					Finanzen.incKonto(name, (-1)*Finanzen.shopumstellkosten);
					plugin.addShopUmstellen(name);
					player.sendMessage(ChatColor.GREEN + "Du kannst den Shop jetzt umstellen!");
					return true;
					
				}
			}
			if (args.length == 2) {
				if (args[0].equalsIgnoreCase("neu") && args[1].equalsIgnoreCase("admin")) {
					if (!player.hasPermission("finanzen.adminshop")) {
						player.sendMessage(ChatColor.RED + "Dazu fehlt dir die Berechtigung!");
						return true;
					}
					if (Finanzen.adminshopbau.contains(name)) {
						Finanzen.adminshopbau.remove(name);
						player.sendMessage(ChatColor.GREEN + "Adminshopbau abgebrochen!");
						return true;
					}
					
					Finanzen.adminshopbau.add(name);
					player.sendMessage(ChatColor.GREEN + "Setze ein leeres Schild an die Wand, an der du den Adminshop eröffnen möchtest.");
					player.sendMessage(ChatColor.GRAY + "(Eine Kiste wird automatisch darunter platziert!)");
					player.sendMessage(ChatColor.GRAY + "(Zum Abbrechen wieder 'shop neu admin' eingeben!");
					return true;	
					
				}
				if (args[0].equalsIgnoreCase("ware")) {
					if (Finanzen.ShopFokus.isEmpty()) {
						player.sendMessage(ChatColor.RED + "Du musst erst einen Shop auswählen!" + ChatColor.GRAY + " (rechtsklick)");
						return true;
					}
					if (!(Finanzen.ShopFokus.containsKey(name))) {
						player.sendMessage(ChatColor.RED + "Du musst erst einen Shop auswählen!" + ChatColor.GRAY + " (rechtsklick)");
						return true;
					
					}
					int ShopID = Finanzen.ShopFokus.get(name);
					if (!Finanzen.ShopHM.containsValue(ShopID)) {
						player.sendMessage(ChatColor.RED + "Der Shop wurde schon abgerissen!");
						return true;
					}
					if (!Finanzen.isNahGenug(player.getLocation(), ShopID)) {
						player.sendMessage(ChatColor.RED + "Du bist zu weit weg, um mit diesem Shop zu interagieren!");
						return true;
					}
					if (!Finanzen.getBesitzer(ShopID).equalsIgnoreCase(name) && !(Finanzen.isAdminShop(ShopID) && player.hasPermission("finanzen.adminshop"))) {
						player.sendMessage(ChatColor.RED + "Du kannst nur eigene Shops einrichten!");
						return true;
					}
					if (!args[1].contains(":")) {
						args[1] = args[1]+":0";
					}
					String[] idA = args[1].split(":");
					try {
						int id = Integer.parseInt(idA[0]);
						byte data = (byte) Integer.parseInt(idA[1]);
						if (Finanzen.config.getString(id+"."+data+".name") != null) {
							double ep = Finanzen.config.getDouble(id+"."+data+".EK");
							double vp = Finanzen.config.getDouble(id+"."+data+".VK");
							Finanzen.Shops.setValue(String.valueOf(Finanzen.ShopFokus.get(name)), "id", id+"#"+data);
							Finanzen.Shops.setValue(String.valueOf(Finanzen.ShopFokus.get(name)), "ep", String.valueOf(ep));
							Finanzen.Shops.setValue(String.valueOf(Finanzen.ShopFokus.get(name)), "vp", String.valueOf(vp));
							player.sendMessage(ChatColor.GREEN + "Der Shop handelt jetzt mit " + ChatColor.DARK_GREEN +Finanzen.config.getString(id+"."+data+".name") + ChatColor.GREEN +".");
							player.sendMessage(ChatColor.GREEN + "Die Preise wurden zurückgesetzt!");
							Finanzen.SchildBeschreiben(ShopID);
							return true;
						}else {
							player.sendMessage(ChatColor.RED + "Damit kannst du nicht handeln!");
							return true;
						}
						
					
					}catch(NumberFormatException e) {
						player.sendMessage(ChatColor.RED + "ID falsch eingegeben!");
						return false;
					}
				}
				if (args[0].equalsIgnoreCase("ep") || args[0].equalsIgnoreCase("kp") || args[0].equalsIgnoreCase("kaufpreis")||args[0].equalsIgnoreCase("einkaufspreis")) {
					if (Finanzen.ShopFokus.isEmpty()) {
						player.sendMessage(ChatColor.RED + "Du musst erst einen Shop auswählen!" + ChatColor.GRAY + " (rechtsklick)");
						return true;
					}
					if (!(Finanzen.ShopFokus.containsKey(name))) {
						player.sendMessage(ChatColor.RED + "Du musst erst einen Shop auswählen!" + ChatColor.GRAY + " (rechtsklick)");
						return true;
					
					}
					int ShopID = Finanzen.ShopFokus.get(name);
					if (!Finanzen.ShopHM.containsValue(ShopID)) {
						player.sendMessage(ChatColor.RED + "Der Shop wurde schon abgerissen!");
						return true;
					}
					if (!Finanzen.isNahGenug(player.getLocation(), ShopID)) {
						player.sendMessage(ChatColor.RED + "Du bist zu weit weg, um mit diesem Shop zu interagieren!");
						return true;
					}
					if (!Finanzen.getBesitzer(ShopID).equalsIgnoreCase(name) && !(Finanzen.isAdminShop(ShopID) && player.hasPermission("finanzen.adminshop"))) {
						player.sendMessage(ChatColor.RED + "Du kannst nur eigene Shops einrichten!");
						return true;
					}
					try {
						double ep = new Double(args[1]);
						if (ep < 0) {
							player.sendMessage(ChatColor.RED + "Der Preis muss positiv sein!");
							return true;
						}
						Finanzen.Shops.setValue(String.valueOf(ShopID), "ep", String.valueOf(ep));
						if (Finanzen.isEingestellt(ShopID)) {
							Finanzen.SchildBeschreiben(ShopID);
						}
						if (ep == 0) {
							player.sendMessage(ChatColor.GREEN + "Der Shop wird nur für dich verkaufen!");
							return true;
						}
						player.sendMessage(ChatColor.GREEN + "Der Shop kauft jetzt " +ChatColor.DARK_GREEN +Finanzen.getWare(ShopID)+ ChatColor.GREEN +" für " + ChatColor.DARK_GREEN +Finanzen.f.format(ep) + Finanzen.kuerzel +ChatColor.GREEN +" für dich ein.");
						return true;
					}catch (NumberFormatException e) {
						player.sendMessage(ChatColor.RED + "Preis falsch angegeben!");
						return false;
					}
				}
				if (args[0].equalsIgnoreCase("vp") || args[0].equalsIgnoreCase("vk") || args[0].equalsIgnoreCase("verkaufspreis")) {
					if (Finanzen.ShopFokus.isEmpty()) {
						player.sendMessage(ChatColor.RED + "Du musst erst einen Shop auswählen!" + ChatColor.GRAY + " (rechtsklick)");
						return true;
					}
					if (!(Finanzen.ShopFokus.containsKey(name))) {
						player.sendMessage(ChatColor.RED + "Du musst erst einen Shop auswählen!" + ChatColor.GRAY + " (rechtsklick)");
						return true;
					
					}
					int ShopID = Finanzen.ShopFokus.get(name);
					if (!Finanzen.ShopHM.containsValue(ShopID)) {
						player.sendMessage(ChatColor.RED + "Der Shop wurde schon abgerissen!");
						return true;
					}
					if (!Finanzen.isNahGenug(player.getLocation(), ShopID)) {
						player.sendMessage(ChatColor.RED + "Du bist zu weit weg, um mit diesem Shop zu interagieren!");
						return true;
					}
					if (!Finanzen.getBesitzer(ShopID).equalsIgnoreCase(name) && !(Finanzen.isAdminShop(ShopID) && player.hasPermission("finanzen.adminshop"))) {
						player.sendMessage(ChatColor.RED + "Du kannst nur eigene Shops einrichten!");
						return true;
					}
					try {
						double vp = new Double(args[1]);
						if (vp < 0) {
							player.sendMessage(ChatColor.RED + "Der Preis muss positiv sein!");
							return true;
						}
						Finanzen.Shops.setValue(String.valueOf(ShopID), "vp", String.valueOf(vp));
						if (Finanzen.isEingestellt(ShopID)) {
							Finanzen.SchildBeschreiben(ShopID);
						}
						if (vp == 0) {
							player.sendMessage(ChatColor.GREEN + "Der Shop wird nur für dich einkaufen!");
							return true;
						}
						player.sendMessage(ChatColor.GREEN + "Der Shop verkauft jetzt "+ ChatColor.DARK_GREEN + Finanzen.getWare(ShopID) +ChatColor.GREEN +" für " + ChatColor.DARK_GREEN + Finanzen.f.format(vp) + Finanzen.kuerzel + ChatColor.GREEN + " für dich.");
						return true;
					}catch (NumberFormatException e) {
						player.sendMessage(ChatColor.RED + "Preis falsch angegeben!");
						return false;
					}
					
				}
			}
			
			
		}
		return false;
	}

	
	
}
