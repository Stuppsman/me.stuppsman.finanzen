package me.stuppsman.finanzen.commandExecutor;

import java.util.HashMap;
import me.stuppsman.finanzen.Finanzen;
import me.stuppsman.finanzen.GS;
import me.stuppsman.finanzen.Stadt;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

public class FinanzenCE_kaufe implements CommandExecutor {

	private Finanzen plugin;

	public FinanzenCE_kaufe(Finanzen plugin) {
		this.plugin=plugin;
	}

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		boolean succeed = false;
		if (cmd.getName().equalsIgnoreCase("kaufe")) {
			if (!(sender instanceof Player)) {
				System.out.println("Kannste mir auch erklären, wo ich das hintun soll?");
				return true;
			}
			Player player = (Player) sender;
			String name = player.getName();
			if (args.length==0) {
				player.sendMessage(ChatColor.DARK_GRAY + "/kaufe <itemid> <anzahl> - Kauf beim Server");
				player.sendMessage(ChatColor.DARK_GRAY + "Data-Werte werden durch Doppelpunkte abgetrennt: zB /kaufe 5:1 10");
				player.sendMessage(ChatColor.DARK_GRAY + "/kaufe shop <anzahl> - Kauf beim angewählten Shop");
				return true;
			}
			if (args.length == 1) {
				if (args[0].equalsIgnoreCase("gs")) {
					if (Finanzen.grundstuecke.get(Finanzen.GSFokus.get(name)) == null) {
						player.sendMessage(ChatColor.RED + "Du musst vorher ein Grundstücksschild anklicken!");
						return true;
					}
					GS gs = Finanzen.grundstuecke.get(Finanzen.GSFokus.get(name));
					double kosten = gs.getPreis();
					String helper = "";
					if (kosten>Finanzen.Finanzendb.getDouble(player.getName().toLowerCase(), "konto")) {
						player.sendMessage(ChatColor.RED + "Das kannst du dir nicht leisten!"+ ChatColor.DARK_GRAY + Finanzen.f.format(kosten)+Finanzen.kuerzel);
						return true;
					}
					if (!gs.getStatus().equalsIgnoreCase("zum Verkauf freigegeben")) {
						player.sendMessage(ChatColor.RED+ "Das Grundstück steht nicht zum Verkauf!");
						return true;
						
					}
					Finanzen.incKonto(name, (-1)*kosten);
					if (Finanzen.staedte.isEmpty()) {
						if (!gs.getBesitzer().equalsIgnoreCase("tba")) {
							Finanzen.incKonto(gs.getBesitzer(), kosten);
							plugin.addGSVerkauf(gs.getBesitzer(), kosten, name, gs.getID());
							helper += " von " + gs.getBesitzer();
						}
						gs.setBesitzer(name);
						gs.setStatus("Baustelle");
						player.sendMessage(ChatColor.BLUE + "Glückwunsch! Du hast ein Grundstück" + helper + " gekauft!");
						plugin.addGSKauf(name, Finanzen.GSFokus.get(name), kosten);
						Finanzen.GSSchildBeschreiben(gs.getSchildLoc());
						return true;
					}
					for (Stadt stadt : Finanzen.staedte.values())	{
						if (Finanzen.isGSinStadt(gs, stadt)) {
							if (!gs.getBesitzer().equalsIgnoreCase("tba")) {
								double steuern = kosten*stadt.getSteuersatz()/100;
								stadt.incStadtkasse(steuern);
								Finanzen.incKonto(gs.getBesitzer(), kosten-steuern);
								helper += " von " + gs.getBesitzer();
								helper += " in " + Finanzen.capFirst(stadt.getName());
								String vorbesitzer =gs.getBesitzer();
								plugin.addGSKauf(name, Finanzen.GSFokus.get(name), kosten);
								plugin.addGSVerkauf(gs.getBesitzer(), kosten, stadt.getName(), steuern, name, gs.getID());
								gs.setBesitzer(name);
								gs.setStatus("Baustelle");
								Finanzen.GSSchildBeschreiben(gs.getSchildLoc());
								player.sendMessage(ChatColor.BLUE + "Glückwunsch! Du hast ein Grundstück" + helper + " gekauft!");
								if (!hatAnderesGS(stadt, vorbesitzer)) {
									stadt.remBewohner(vorbesitzer);
								}
								return true;
							} else {
								stadt.incStadtkasse(kosten);
								plugin.addGSKauf(name, Finanzen.GSFokus.get(name), kosten);
								plugin.addGSVerkauf(stadt.getName(), kosten, name, gs.getID());
								gs.setBesitzer(name);
								gs.setStatus("Baustelle");
								helper += "in " + Finanzen.capFirst(stadt.getName());
								Finanzen.GSSchildBeschreiben(gs.getSchildLoc());
								player.sendMessage(ChatColor.BLUE + "Glückwunsch! Du hast ein Grundstück " + helper + " gekauft!");
								return true;
							}
							
							
						}
					}
					if (!gs.getBesitzer().equalsIgnoreCase("tba")) {
						Finanzen.incKonto(gs.getBesitzer(), kosten);
						plugin.addGSVerkauf(gs.getBesitzer(), kosten, name, gs.getID());
						helper += " von " + gs.getBesitzer();
					}
					plugin.addGSKauf(name, Finanzen.GSFokus.get(name), kosten);
					gs.setBesitzer(name);
					gs.setStatus("Baustelle");
					player.sendMessage(ChatColor.BLUE + "Glückwunsch! Du hast ein Grundstück" + helper + " gekauft!");
					Finanzen.GSSchildBeschreiben(gs.getSchildLoc());
					return true;
					
				}
			}
			if (args.length != 2) {
				player.sendMessage(ChatColor.DARK_GRAY + "/kaufe <itemid> <anzahl> - Kauf beim Server");
				player.sendMessage(ChatColor.DARK_GRAY + "Data-Werte werden durch Doppelpunkte abgetrennt: zB /kaufe 5:1 10");
				player.sendMessage(ChatColor.DARK_GRAY + "/kaufe shop <anzahl> - Kauf beim angewählten Shop");
				return true;
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
					player.sendMessage(ChatColor.RED + "Du kannst in deinem eigenen Shop nicht einkaufen!");
					return true;
				}
				try {
					String gegenstand = Finanzen.getWare(ShopID);
					int id, anzahl;
					byte data;
					double einzelkosten = Finanzen.Shops.getDouble(String.valueOf(ShopID), "vp");
					if (einzelkosten == 0) {
						player.sendMessage(ChatColor.RED + "Der Shop verkauft seine Ware nicht!");
						return true;
					}
					if (args[1].equalsIgnoreCase("max")) {
						anzahl = (int) (Finanzen.Finanzendb.getDouble(player.getName().toLowerCase(), "konto")/einzelkosten);
					}else {
						anzahl = Integer.parseInt(args[1]);
					}
					String[] idA = Finanzen.getWareID(ShopID).split("#");
					ItemStack toAdd;
					id = Integer.parseInt(idA[0]);
					data = (byte) Integer.parseInt(idA[1]);
					toAdd = new ItemStack(id, anzahl, (short)0, data);
					double gesamtkosten = einzelkosten*anzahl;
					if (gesamtkosten>Finanzen.Finanzendb.getDouble(player.getName().toLowerCase(), "konto")) {
						player.sendMessage(ChatColor.RED + "Das kannst du dir nicht leisten!");
						player.sendMessage(ChatColor.DARK_GRAY + "" + anzahl + " " + plugin.getConfig().getString(id+"."+data+".name") + " - Gesamtkosten: " + Finanzen.f.format(gesamtkosten)+Finanzen.kuerzel);
						return true;
					}
					HashMap<Integer, ItemStack> restHM = player.getInventory().addItem(toAdd);
					int rest = 0;
					if (!restHM.isEmpty()) {
						rest = restHM.get(0).getAmount();
					}
					if (rest == anzahl && rest != 0) {
						player.sendMessage(ChatColor.RED + "Du hast keinen Platz im Inventar!");
						return true;
					}
					anzahl = anzahl-rest;
					toAdd.setAmount(anzahl);
					if (!(Finanzen.isAdminShop(ShopID) || Finanzen.Shops.hasKey(String.valueOf(ShopID), "special"))) {	
						player.getInventory().removeItem(toAdd);
						HashMap<Integer, ItemStack> restKisteHM = Finanzen.getShopInv(ShopID).removeItem(toAdd);
						int restKiste = 0;
						if (!restKisteHM.isEmpty()) {
							restKiste = restKisteHM.get(0).getAmount();
						}
						if (restKiste == anzahl && restKiste != 0) {
							player.sendMessage(ChatColor.RED + "Der Shop ist leider ausverkauft!");
							return true;
						}
						anzahl = anzahl-restKiste;
						toAdd.setAmount(anzahl);
						player.getInventory().addItem(toAdd);
						
						double kosten = anzahl*einzelkosten;
						Finanzen.incKonto(name, (-1)*kosten);
						Finanzen.incKonto(Finanzen.getBesitzer(ShopID), kosten);
						plugin.addKiS(name, gegenstand, shopbesitzer, anzahl, kosten);
						String helper = "s";
						if (shopbesitzer.endsWith("s") || shopbesitzer.endsWith("z")) {
							helper = "'";
						}
						player.sendMessage(ChatColor.GREEN + "Du hast in "+ChatColor.YELLOW+shopbesitzer+helper +ChatColor.DARK_GREEN+" "+gegenstand+"-Shop "+anzahl+ " Stk "+ChatColor.GREEN+"für insg "+ChatColor.DARK_GREEN+Finanzen.f.format(kosten)+Finanzen.kuerzel+ChatColor.GREEN+" gekauft!");
						if (plugin.getServer().getPlayer(shopbesitzer) != null) {
							plugin.getServer().getPlayer(shopbesitzer).sendMessage(ChatColor.YELLOW + name + ChatColor.GREEN + " hat in deinem "+ChatColor.DARK_GREEN+gegenstand+"-Shop "+anzahl+ " "+gegenstand +ChatColor.GREEN + "für insg "+ChatColor.DARK_GREEN+Finanzen.f.format(kosten)+Finanzen.kuerzel+ChatColor.GREEN +" gekauft!");
						}
						
						return true;
					}
					double kosten = anzahl*einzelkosten;
					Finanzen.incKonto(name, (-1)*kosten);
					plugin.addKiAS(name, gegenstand, anzahl, kosten);
					player.sendMessage(ChatColor.GREEN + "Du hast im "+ChatColor.DARK_GREEN + "AdminShop "+anzahl+ " " + gegenstand+ChatColor.GREEN+" für insg "+ChatColor.DARK_GREEN+Finanzen.f.format(kosten)+Finanzen.kuerzel+ChatColor.GREEN+" gekauft!");
					return true;
					
				}catch (NumberFormatException e) {
					player.sendMessage(ChatColor.RED + "Die Anzahl wurde falsch angegeben!");
					player.sendMessage(ChatColor.RED + "/kaufe shop <anzahl>");
					return true;
				}
				
			}
			if (!player.hasPermission("finanzen.servershop")) {
				player.sendMessage(ChatColor.RED + "Beim Servershop können leider nur VIPs einkaufen!");
				return true;
			}
			try {
				if (!args[0].contains(":")) {
					args[0] = args[0]+":0";
				}
				
				ItemStack toAdd;
				int id, anzahl;
				byte data;
				
				double einzelkosten = plugin.getConfig().getDouble(args[0].replace(":", ".")+ ".VK");
				if (einzelkosten == 0) {
					player.sendMessage(ChatColor.RED + "Dieses Item wird leider nicht verkauft!");
					return true;
				}
				if (args[1].equalsIgnoreCase("max")) {
					anzahl = (int) (Finanzen.Finanzendb.getDouble(player.getName().toLowerCase(), "konto")/einzelkosten);
					
				}else {
					anzahl = Integer.parseInt(args[1]);
				}
				String[] idA = args[0].split(":");
				try {
					id = Integer.parseInt(idA[0]);
					data = (byte) Integer.parseInt(idA[1]);
					toAdd = new ItemStack(id, anzahl, (short)0, data);
					succeed = true;
				}catch (NumberFormatException e) {
					player.sendMessage(ChatColor.RED + "Die ID wurde falsch eingegeben!");
					return false;
				}
				double gesamtkosten = einzelkosten*anzahl;
				if (gesamtkosten>Finanzen.Finanzendb.getDouble(player.getName().toLowerCase(), "konto") &&(!args[1].equalsIgnoreCase("max"))) {
					player.sendMessage(ChatColor.RED + "Das kannst du dir nicht leisten!");
					player.sendMessage(ChatColor.DARK_GRAY + "" + anzahl + " " + plugin.getConfig().getString(args[0].replace(":", ".")+ ".name") + " - Gesamtkosten: " + Finanzen.f.format(gesamtkosten)+Finanzen.kuerzel);
					return true;
				}
				
				if (succeed) {
					HashMap<Integer, ItemStack> restHM = player.getInventory().addItem(toAdd);
					int rest = 0;
					if (!restHM.isEmpty()) {
						rest = restHM.get(0).getAmount();
					}
					if (rest == anzahl) {
						player.sendMessage(ChatColor.RED + "Du hast keinen Platz im Inventar!");
						return true;
					}
					anzahl = anzahl-rest;
					gesamtkosten = einzelkosten*anzahl;
					Finanzen.incKonto(player.getName().toLowerCase(), (-1)*gesamtkosten);
					player.sendMessage(ChatColor.GREEN + "Du hast "+ChatColor.DARK_GREEN + anzahl + ChatColor.GREEN + "x den Gegenstand "+ChatColor.DARK_GREEN + plugin.getConfig().getString(args[0].replace(":", ".")+".name") + ChatColor.GREEN + " gekauft!");
					player.sendMessage(ChatColor.DARK_AQUA + "Gesamtkosten: " + Finanzen.f.format(gesamtkosten)  + " " + Finanzen.waehrung);
					if (rest>0) {
						player.sendMessage(ChatColor.DARK_GRAY + "(mehr passte nicht ins Inventar!)");
					}
					plugin.addK(player.getName().toLowerCase(), plugin.getConfig().getString(args[0].replace(":", ".")+".name"), anzahl, gesamtkosten);
					return true;
				}
			}catch (NumberFormatException e) {
				player.sendMessage(ChatColor.RED + "Die Anzahl wurde falsch angegeben!");
				return false;
			}
		}
		return false;
	}

	private boolean hatAnderesGS(Stadt stadt, String vorbesitzer) {
		for (GS gs : Finanzen.grundstuecke.values()) {
			if (gs.isGSBesitzer(vorbesitzer))
				if (Finanzen.isGSinStadt(gs, stadt)) {
					return true;
				}
				
		}
		return false;
	}

	

}
