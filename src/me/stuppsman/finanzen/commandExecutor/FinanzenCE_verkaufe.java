package me.stuppsman.finanzen.commandExecutor;

import java.util.HashMap;

import me.stuppsman.finanzen.Finanzen;
import me.stuppsman.finanzen.GS;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

public class FinanzenCE_verkaufe implements CommandExecutor {

	private Finanzen plugin;

	public FinanzenCE_verkaufe(Finanzen plugin) {
		this.plugin=plugin;
	}

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		if (!(sender instanceof Player)) {
			System.out.println("Dieser Befehl ist nur für Spieler!");
		}
		Player player = (Player) sender;
		String name = player.getName();
		if (cmd.getName().equalsIgnoreCase("verkaufe")) {
			if (args.length == 0) {
				player.sendMessage(ChatColor.DARK_GRAY + "/verkaufe <itemid> <anzahl> - Verkauf an Server");
				player.sendMessage(ChatColor.DARK_GRAY + "Data-Werte werden mit Doppelpunkten abgetrennt!");
				player.sendMessage(ChatColor.DARK_GRAY + "/verkaufe shop <anzahl> - Verkauf an den angewählten Shop");
				player.sendMessage(ChatColor.DARK_GRAY + "/verkaufe gs <preis> - Verkauft das angewählte Grundstück");
				return true;
			}
			if (args.length != 2) {
				player.sendMessage(ChatColor.DARK_GRAY + "/verkaufe <itemid> <anzahl> - Verkauf an Server");
				player.sendMessage(ChatColor.DARK_GRAY + "Data-Werte werden mit Doppelpunkten abgetrennt!");
				player.sendMessage(ChatColor.DARK_GRAY + "/verkaufe shop <anzahl> - Verkauf an den angewählten Shop");
				player.sendMessage(ChatColor.DARK_GRAY + "/verkaufe gs <preis> - Verkauft das angewählte Grundstück");
				return true;
			}
			if (args[0].equalsIgnoreCase("gs")) {
				if (Finanzen.GSFokus.isEmpty()) {
					player.sendMessage(ChatColor.RED + "Du musst erst ein Grundstück anwählen!");
					return true;
				}
				if (!Finanzen.GSFokus.containsKey(name)) {
					player.sendMessage(ChatColor.RED + "Du musst erst ein Grundstück anwählen!");
					return true;
				}
				GS gs = Finanzen.getGS(Finanzen.GSFokus.get(name));
				if (!gs.getBesitzer().equalsIgnoreCase(name)) {
					player.sendMessage(ChatColor.RED + "Fremde Grundstücke verkaufen.. Dass da noch niemand drauf gekommen is.. ;)");
					return true;
				}
				try {
					double preis = new Double(args[1]);
					gs.setPreis(preis);
					gs.setStatus("zum Verkauf freigegeben");
					player.sendMessage(ChatColor.GOLD + "Du hast das Grundstück zum Verkauf angeboten!");
					return true;
				}
				catch (NumberFormatException e) {
					player.sendMessage(ChatColor.RED + "Preis falsch angegeben!");
					return true;
				}
				
				
			}
			if (args[0].equalsIgnoreCase("shop")) {
				if (Finanzen.ShopFokus.isEmpty()) {
					player.sendMessage(ChatColor.RED + "Du musst einen Shop anwählen, damit du dort einkaufen kannst!");
					return true;
				}
				if (!Finanzen.ShopFokus.containsKey(name)) {
					player.sendMessage(ChatColor.RED + "Du musst einen Shop anwählen, damit du dort einkaufen kannst!");
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
				if (!Finanzen.isEingestellt(ShopID)) {
					player.sendMessage(ChatColor.RED + "Der Shop ist leider noch nicht konfiguriert!");
					return true;
				}
				String shopbesitzer = Finanzen.getBesitzer(ShopID);
				if (shopbesitzer.equalsIgnoreCase(name)) {
					player.sendMessage(ChatColor.RED + "Du kannst an deinen eigenen Shop nichts verkaufen!");
					return true;
				}
				try {
					String gegenstand = Finanzen.getWare(ShopID);
					int id, anzahl;
					byte data;
					double einzelkosten = Finanzen.Shops.getDouble(String.valueOf(ShopID), "ep");
					if (einzelkosten == 0) {
						player.sendMessage(ChatColor.RED + "Der Shop kauft keine Ware!");
						return true;
					}
					if (args[1].equalsIgnoreCase("max")) {
						if (!Finanzen.isAdminShop(ShopID)){
							anzahl = (int)(Finanzen.Finanzendb.getDouble(shopbesitzer.toLowerCase(), "konto")/einzelkosten);
						}else {
							anzahl = 2304;
						}
					}else {
						anzahl = Integer.parseInt(args[1]);
					}
					String[] idA = Finanzen.getWareID(ShopID).split("#");
					ItemStack toDel;
					id = Integer.parseInt(idA[0]);
					data = (byte) Integer.parseInt(idA[1]);
					toDel = new ItemStack(id, anzahl, (short)0, data);
					double gesamtkosten = einzelkosten*anzahl;
					if (!Finanzen.isAdminShop(ShopID)) {
						if (gesamtkosten>Finanzen.Finanzendb.getDouble(shopbesitzer.toLowerCase(), "konto")) {
							player.sendMessage(ChatColor.RED + "Das kann sich "+ ChatColor.YELLOW + shopbesitzer + ChatColor.RED +" nicht leisten!");
							return true;
						}
					}
					HashMap<Integer, ItemStack> restHM = player.getInventory().removeItem(toDel);
					int rest = 0;
					if (!restHM.isEmpty()) {
						rest = restHM.get(0).getAmount();
					}
					if (rest == anzahl && rest != 0) {
						player.sendMessage(ChatColor.RED + "Du hast nichts davon im Inventar!");
						return true;
					}
					anzahl = anzahl-rest;
					toDel.setAmount(anzahl);
					if (!Finanzen.isAdminShop(ShopID)) {	
						player.getInventory().addItem(toDel);
						HashMap<Integer, ItemStack> restKisteHM = Finanzen.getShopInv(ShopID).addItem(toDel);
						int restKiste = 0;
						if (!restKisteHM.isEmpty()) {
							restKiste = restKisteHM.get(0).getAmount();
						}
						if (restKiste == anzahl && restKiste != 0) {
							player.sendMessage(ChatColor.RED + "Der Shop hat leider keinen Platz mehr!");
							return true;
						}
						anzahl = anzahl-restKiste;
						toDel.setAmount(anzahl);
						player.getInventory().removeItem(toDel);
						double kosten = anzahl*einzelkosten;
						Finanzen.incKonto(name, kosten);
						Finanzen.incKonto(shopbesitzer, (-1)*kosten);
						plugin.addViS(name, gegenstand, shopbesitzer, anzahl, kosten);
						String helper = "s";
						if (shopbesitzer.endsWith("s") || shopbesitzer.endsWith("z")) {
							helper = "'";
						}
						player.sendMessage(ChatColor.GREEN + "Du hast an "+ChatColor.YELLOW+shopbesitzer+helper +ChatColor.DARK_GREEN+" Shop "+anzahl+ " "+gegenstand+ChatColor.GREEN + " für insg "+ChatColor.DARK_GREEN+Finanzen.f.format(kosten)+Finanzen.kuerzel+ChatColor.GREEN +" verkauft!");
						if (plugin.getServer().getPlayer(shopbesitzer) != null) {
							plugin.getServer().getPlayer(shopbesitzer).sendMessage(ChatColor.YELLOW+ name + ChatColor.GREEN +" hat in deinem Shop "+ChatColor.DARK_GREEN+anzahl+ " "+gegenstand+ChatColor.GREEN +" für insg "+ChatColor.DARK_GREEN +Finanzen.f.format(kosten)+Finanzen.kuerzel+ChatColor.GREEN +" verkauft!");
						}
						if (rest > 0) {
							player.sendMessage(ChatColor.GRAY + "(Mehr hattest nicht im Inventar!)");
						}
						return true;
					}
					double kosten = anzahl*einzelkosten;
					Finanzen.incKonto(name, kosten);
					plugin.addViAS(name, gegenstand, anzahl, kosten);
					player.sendMessage(ChatColor.GREEN + "Du hast im "+ChatColor.DARK_GREEN + "AdminShop "+anzahl+ " "+gegenstand+ChatColor.GREEN+" für insg "+ChatColor.DARK_GREEN+Finanzen.f.format(kosten)+Finanzen.kuerzel+ChatColor.GREEN+" verkauft!");
					return true;
					
				}catch (NumberFormatException e) {
					player.sendMessage(ChatColor.RED + "Die Anzahl wurde falsch angegeben!");
					player.sendMessage(ChatColor.RED + "/verkaufe shop <anzahl>");
					return true;
				}
				
			}
			try {
				int id, anzahl;
				if (args[1].equalsIgnoreCase("max")) {
					anzahl = 2304;
					
				}else {
					anzahl = Integer.parseInt(args[1]);
				}
				if (!args[0].contains(":")) {
					args[0] = args[0]+":0";
				}
				
				ItemStack toDel;
				byte data;
				boolean succeed;
				
				double einzelpreis = plugin.getConfig().getDouble(args[0].replace(":", ".")+ ".EK");
				if (einzelpreis == 0) {
					player.sendMessage(ChatColor.RED + "Dieses Item kann man nicht verkaufen!");
					return true;
				}
				
				String[] idA = args[0].split(":");
				try {
					id = Integer.parseInt(idA[0]);
					data = (byte) Integer.parseInt(idA[1]);
					toDel = new ItemStack(id, anzahl, (short)0, data);
					succeed = true;
				}catch (NumberFormatException e) {
					player.sendMessage(ChatColor.RED + "Die ID wurde falsch eingegeben!");
					return false;
				}
				
				
				if (succeed) {
					HashMap<Integer, ItemStack> restHM = player.getInventory().removeItem(toDel);
					int rest = 0;
					if (!restHM.isEmpty()) {
						rest = restHM.get(0).getAmount();
					}
					if (rest == anzahl) {
						player.sendMessage(ChatColor.RED + "Du hast nichts davon im Inventar!");
						return true;
					}
					anzahl = anzahl-rest;
					double gesamtpreis = einzelpreis*anzahl;
					Finanzen.incKonto(player.getName().toLowerCase(), gesamtpreis);
					player.sendMessage(ChatColor.GREEN + "Du hast "+ChatColor.DARK_GREEN + anzahl + ChatColor.GREEN + "x den Gegenstand "+ChatColor.DARK_GREEN + plugin.getConfig().getString(args[0].replace(":", ".")+".name") + ChatColor.GREEN + " verkauft!");
					player.sendMessage(ChatColor.DARK_AQUA + "Gesamtpreis: " + Finanzen.f.format(gesamtpreis) + " " + Finanzen.waehrung);
					if (rest>0) {
						player.sendMessage(ChatColor.DARK_GRAY + "(Mehr hattest du nicht im Inventar!)");
					}
					plugin.addV(player.getName().toLowerCase(), plugin.getConfig().getString(args[0].replace(":", ".")+".name"), anzahl, gesamtpreis);
					return true;
				}
			}catch (NumberFormatException e) {
				player.sendMessage(ChatColor.RED + "Die Anzahl wurde falsch angegeben!");
				return false;
			}
		}
		
		return false;
	}

}
