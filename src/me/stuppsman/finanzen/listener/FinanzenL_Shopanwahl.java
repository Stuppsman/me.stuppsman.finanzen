package me.stuppsman.finanzen.listener;

import me.stuppsman.finanzen.Finanzen;
import me.stuppsman.finanzen.Stadt;

import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.block.BlockState;
import org.bukkit.block.Sign;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

public class FinanzenL_Shopanwahl implements Listener {

	
	@EventHandler
	public void onShopWahl(PlayerInteractEvent event) {
		
		if (event.getAction() == Action.RIGHT_CLICK_BLOCK) {
			if (event.getClickedBlock().getTypeId() == 68){
				Location loc = event.getClickedBlock().getLocation();
				
				if (Finanzen.isShopSchild(loc)) {
					Player player = event.getPlayer();
					int ShopID = Finanzen.ShopHM.get(loc.getWorld().getName()+":"+loc.getBlockX()+":"+loc.getBlockY()+":"+loc.getBlockZ());
					Finanzen.ShopFokus.put(player.getName(), ShopID);
					String besitzer = Finanzen.getBesitzer(ShopID);
					String helper = "s";
					if (besitzer.endsWith("s") || besitzer.endsWith("z")) {
						helper = "'";
					}
					String ware = Finanzen.getWare(ShopID);
					event.setCancelled(true);
					if (Finanzen.Shops.hasKey(String.valueOf(ShopID), "special")) {
						String special = Finanzen.Shops.getString(String.valueOf(ShopID), "special");
						if (special.equalsIgnoreCase("muehle")) {
							double ep = Finanzen.getEP(ShopID);
							Stadt stadt = Finanzen.getStadtAtLoc(loc);
							player.sendMessage(ChatColor.DARK_GREEN + "| " + ChatColor.DARK_GREEN + "------------------------------------------");
							player.sendMessage(ChatColor.DARK_GREEN + "| " + ChatColor.GREEN + "Mühle von " + Finanzen.capFirst(stadt.getName())+":");
							player.sendMessage(ChatColor.DARK_GREEN + "| " + ChatColor.DARK_GREEN + "------------------------------------------");
							player.sendMessage(ChatColor.DARK_GREEN + "| " + ChatColor.GREEN + "Die Mühle kauft unbegrenzt "+ware+" für "+Finanzen.f.format(ep)+Finanzen.kuerzel+" ein.");
							player.sendMessage(ChatColor.DARK_GREEN + "| " + ChatColor.GRAY + "Mit dem Befehl /verkaufe shop <anzahl> verkaufst du deine Ware.");
							player.sendMessage(ChatColor.DARK_GREEN + "| " + ChatColor.GRAY + "Das Geld dafür kommt aus der Stadtkasse.");
							player.sendMessage(ChatColor.DARK_GREEN + "| " + ChatColor.DARK_GREEN + "------------------------------------------");
							BlockState bs = loc.getBlock().getState();
							Sign schild = (Sign) bs;
							schild.setLine(0, "--Mühle--");
							schild.setLine(1, "kauft");
							schild.setLine(2, "Weizensamen");
							schild.setLine(3, "für "+Finanzen.f.format(ep)+Finanzen.kuerzel);
							schild.update();
							return;
								
							
						}
						if (special.equalsIgnoreCase("fischer")) {
							double vp = Finanzen.getVP(ShopID);
							Stadt stadt = Finanzen.getStadtAtLoc(loc);
							player.sendMessage(ChatColor.DARK_GREEN + "| " + ChatColor.DARK_GREEN + "------------------------------------------");
							player.sendMessage(ChatColor.DARK_GREEN + "| " + ChatColor.GREEN + "Fischer von " + Finanzen.capFirst(stadt.getName())+":");
							player.sendMessage(ChatColor.DARK_GREEN + "| " + ChatColor.DARK_GREEN + "------------------------------------------");
							player.sendMessage(ChatColor.DARK_GREEN + "| " + ChatColor.GREEN + "Der Fischer verkauft unbegrenzt "+ware+" für "+Finanzen.f.format(vp)+Finanzen.kuerzel+".");
							player.sendMessage(ChatColor.DARK_GREEN + "| " + ChatColor.GRAY + "Mit dem Befehl /kaufe shop <anzahl> kaufst du hier ein.");
							player.sendMessage(ChatColor.DARK_GREEN + "| " + ChatColor.GRAY + "Das Geld geht direkt in die Stadtkasse.");
							player.sendMessage(ChatColor.DARK_GREEN + "| " + ChatColor.DARK_GREEN + "------------------------------------------");
							BlockState bs = loc.getBlock().getState();
							Sign schild = (Sign) bs;
							schild.setLine(0, "--Fischer--");
							schild.setLine(1, "verkauft");
							schild.setLine(2, "frischen Fisch");
							schild.setLine(3, "für "+Finanzen.f.format(vp)+Finanzen.kuerzel);
							schild.update();
							return;
								
							
						}
						if (special.equalsIgnoreCase("baecker")) {
							double vp = Finanzen.getVP(ShopID);
							Stadt stadt = Finanzen.getStadtAtLoc(loc);
							player.sendMessage(ChatColor.DARK_GREEN + "| " + ChatColor.DARK_GREEN + "------------------------------------------");
							player.sendMessage(ChatColor.DARK_GREEN + "| " + ChatColor.GREEN + "Bäcker von " + Finanzen.capFirst(stadt.getName())+":");
							player.sendMessage(ChatColor.DARK_GREEN + "| " + ChatColor.DARK_GREEN + "------------------------------------------");
							player.sendMessage(ChatColor.DARK_GREEN + "| " + ChatColor.GREEN + "Der Bäcker verkauft unbegrenzt "+ware+" für "+Finanzen.f.format(vp)+Finanzen.kuerzel+".");
							player.sendMessage(ChatColor.DARK_GREEN + "| " + ChatColor.GRAY + "Mit dem Befehl /kaufe shop <anzahl> kaufst du hier ein.");
							player.sendMessage(ChatColor.DARK_GREEN + "| " + ChatColor.GRAY + "Das Geld geht direkt in die Stadtkasse.");
							player.sendMessage(ChatColor.DARK_GREEN + "| " + ChatColor.DARK_GREEN + "------------------------------------------");
							BlockState bs = loc.getBlock().getState();
							Sign schild = (Sign) bs;
							schild.setLine(0, "--Bäcker--");
							schild.setLine(1, "verkauft");
							schild.setLine(2, "frisches Brot");
							schild.setLine(3, "für "+Finanzen.f.format(vp)+Finanzen.kuerzel);
							schild.update();
							return;
						}
						if (special.equalsIgnoreCase("mine")) {
							double vp = Finanzen.getVP(ShopID);
							Stadt stadt = Finanzen.getStadtAtLoc(loc);
							player.sendMessage(ChatColor.DARK_GREEN + "| " + ChatColor.DARK_GREEN + "------------------------------------------");
							player.sendMessage(ChatColor.DARK_GREEN + "| " + ChatColor.GREEN + "Mine von " + Finanzen.capFirst(stadt.getName())+":");
							player.sendMessage(ChatColor.DARK_GREEN + "| " + ChatColor.DARK_GREEN + "------------------------------------------");
							player.sendMessage(ChatColor.DARK_GREEN + "| " + ChatColor.GREEN + "Die Mine verkauft unbegrenzt "+ware+" für "+Finanzen.f.format(vp)+Finanzen.kuerzel+".");
							player.sendMessage(ChatColor.DARK_GREEN + "| " + ChatColor.GRAY + "Mit dem Befehl /kaufe shop <anzahl> kaufst du hier ein.");
							player.sendMessage(ChatColor.DARK_GREEN + "| " + ChatColor.GRAY + "Das Geld geht direkt in die Stadtkasse.");
							player.sendMessage(ChatColor.DARK_GREEN + "| " + ChatColor.DARK_GREEN + "------------------------------------------");
							BlockState bs = loc.getBlock().getState();
							Sign schild = (Sign) bs;
							schild.setLine(0, "--Mine--");
							schild.setLine(1, "verkauft");
							schild.setLine(2, "Kohle");
							schild.setLine(3, "für "+Finanzen.f.format(vp)+Finanzen.kuerzel);
							schild.update();
							return;
						}
						if (special.equalsIgnoreCase("fabrik1")) {
							double vp = Finanzen.getVP(ShopID);
							Stadt stadt = Finanzen.getStadtAtLoc(loc);
							player.sendMessage(ChatColor.DARK_GREEN + "| " + ChatColor.DARK_GREEN + "------------------------------------------");
							player.sendMessage(ChatColor.DARK_GREEN + "| " + ChatColor.GREEN + "Fabrik von " + Finanzen.capFirst(stadt.getName())+":");
							player.sendMessage(ChatColor.DARK_GREEN + "| " + ChatColor.DARK_GREEN + "------------------------------------------");
							player.sendMessage(ChatColor.DARK_GREEN + "| " + ChatColor.GREEN + "Diese Fabrik verkauft "+ware+" für "+Finanzen.f.format(vp)+Finanzen.kuerzel+".");
							player.sendMessage(ChatColor.DARK_GREEN + "| " + ChatColor.GRAY + "Mit dem Befehl /kaufe shop <anzahl> kaufst du hier ein.");
							player.sendMessage(ChatColor.DARK_GREEN + "| " + ChatColor.GRAY + "Das Geld geht direkt in die Stadtkasse.");
							player.sendMessage(ChatColor.DARK_GREEN + "| " + ChatColor.DARK_GREEN + "------------------------------------------");
							BlockState bs = loc.getBlock().getState();
							Sign schild = (Sign) bs;
							schild.setLine(0, "--Fabrik--");
							schild.setLine(1, "verkauft");
							schild.setLine(2, "Redstonestaub");
							schild.setLine(3, "für "+Finanzen.f.format(vp)+Finanzen.kuerzel);
							schild.update();
							return;
						}
						if (special.equalsIgnoreCase("fabrik2")) {
							double vp = Finanzen.getVP(ShopID);
							Stadt stadt = Finanzen.getStadtAtLoc(loc);
							player.sendMessage(ChatColor.DARK_GREEN + "| " + ChatColor.DARK_GREEN + "------------------------------------------");
							player.sendMessage(ChatColor.DARK_GREEN + "| " + ChatColor.GREEN + "Fabrik von " + Finanzen.capFirst(stadt.getName())+":");
							player.sendMessage(ChatColor.DARK_GREEN + "| " + ChatColor.DARK_GREEN + "------------------------------------------");
							player.sendMessage(ChatColor.DARK_GREEN + "| " + ChatColor.GREEN + "Diese Fabrik verkauft "+ware+" für "+Finanzen.f.format(vp)+Finanzen.kuerzel+".");
							player.sendMessage(ChatColor.DARK_GREEN + "| " + ChatColor.GRAY + "Mit dem Befehl /kaufe shop <anzahl> kaufst du hier ein.");
							player.sendMessage(ChatColor.DARK_GREEN + "| " + ChatColor.GRAY + "Das Geld geht direkt in die Stadtkasse.");
							player.sendMessage(ChatColor.DARK_GREEN + "| " + ChatColor.DARK_GREEN + "------------------------------------------");
							BlockState bs = loc.getBlock().getState();
							Sign schild = (Sign) bs;
							schild.setLine(0, "--Fabrik--");
							schild.setLine(1, "verkauft");
							schild.setLine(2, "Kolben");
							schild.setLine(3, "für "+Finanzen.f.format(vp)+Finanzen.kuerzel);
							schild.update();
							return;
						}
						if (special.equalsIgnoreCase("fabrik3")) {
							double vp = Finanzen.getVP(ShopID);
							Stadt stadt = Finanzen.getStadtAtLoc(loc);
							player.sendMessage(ChatColor.DARK_GREEN + "| " + ChatColor.DARK_GREEN + "------------------------------------------");
							player.sendMessage(ChatColor.DARK_GREEN + "| " + ChatColor.GREEN + "Fabrik von " + Finanzen.capFirst(stadt.getName())+":");
							player.sendMessage(ChatColor.DARK_GREEN + "| " + ChatColor.DARK_GREEN + "------------------------------------------");
							player.sendMessage(ChatColor.DARK_GREEN + "| " + ChatColor.GREEN + "Diese Fabrik verkauft "+ware+" für "+Finanzen.f.format(vp)+Finanzen.kuerzel+".");
							player.sendMessage(ChatColor.DARK_GREEN + "| " + ChatColor.GRAY + "Mit dem Befehl /kaufe shop <anzahl> kaufst du hier ein.");
							player.sendMessage(ChatColor.DARK_GREEN + "| " + ChatColor.GRAY + "Das Geld geht direkt in die Stadtkasse.");
							player.sendMessage(ChatColor.DARK_GREEN + "| " + ChatColor.DARK_GREEN + "------------------------------------------");
							BlockState bs = loc.getBlock().getState();
							Sign schild = (Sign) bs;
							schild.setLine(0, "--Fabrik--");
							schild.setLine(1, "verkauft");
							schild.setLine(2, "Klebekolben");
							schild.setLine(3, "für "+Finanzen.f.format(vp)+Finanzen.kuerzel);
							schild.update();
							return;
						}
						if (special.equalsIgnoreCase("tierheim")) {
							double ep = Finanzen.getEP(ShopID);
							Stadt stadt = Finanzen.getStadtAtLoc(loc);
							player.sendMessage(ChatColor.DARK_GREEN + "| " + ChatColor.DARK_GREEN + "------------------------------------------");
							player.sendMessage(ChatColor.DARK_GREEN + "| " + ChatColor.GREEN + "Tierheim von " + Finanzen.capFirst(stadt.getName())+":");
							player.sendMessage(ChatColor.DARK_GREEN + "| " + ChatColor.DARK_GREEN + "------------------------------------------");
							player.sendMessage(ChatColor.DARK_GREEN + "| " + ChatColor.GREEN + "Diese Fabrik verkauft "+ware+" für "+Finanzen.f.format(ep)+Finanzen.kuerzel+".");
							player.sendMessage(ChatColor.DARK_GREEN + "| " + ChatColor.GRAY + "Mit dem Befehl /kaufe shop <anzahl> kaufst du hier ein.");
							player.sendMessage(ChatColor.DARK_GREEN + "| " + ChatColor.GRAY + "Das Geld geht direkt in die Stadtkasse.");
							player.sendMessage(ChatColor.DARK_GREEN + "| " + ChatColor.DARK_GREEN + "------------------------------------------");
							BlockState bs = loc.getBlock().getState();
							Sign schild = (Sign) bs;
							schild.setLine(0, "--Tierheim--");
							schild.setLine(1, "kauft");
							schild.setLine(2, "Knochen");
							schild.setLine(3, "für "+Finanzen.f.format(ep)+Finanzen.kuerzel);
							schild.update();
							return;
						}
						
					}
					if (ware.equalsIgnoreCase("noch nicht eingestellt")) {
						if (player.getName().equalsIgnoreCase(besitzer)){
							player.sendMessage(ChatColor.DARK_GREEN + "| " + ChatColor.DARK_GREEN + "------------------------------------------");
							player.sendMessage(ChatColor.DARK_GREEN + "| " + ChatColor.GREEN + "Dein Kistenshop:");
							player.sendMessage(ChatColor.DARK_GREEN + "| " + ChatColor.DARK_GREEN + "------------------------------------------");
							player.sendMessage(ChatColor.DARK_GREEN + "| " + ChatColor.GREEN + "Dieser Shop wurde noch nicht konfiguriert.");
							player.sendMessage(ChatColor.DARK_GREEN + "| " + ChatColor.GREEN + "Such dir mit /idsuche oder /id die Ware raus, mit der du hier handeln möchtest.");
							player.sendMessage(ChatColor.DARK_GREEN + "| " + ChatColor.GREEN + "Der Befehl /shop ware <id> stellt den Shop auf die Handelsware ein.");
							player.sendMessage(ChatColor.DARK_GREEN + "| " + ChatColor.DARK_GREEN + "------------------------------------------");
							event.setCancelled(true);
							return;
						}else {
							if (besitzer.equalsIgnoreCase("AdminShop")) {
								player.sendMessage(ChatColor.DARK_GREEN + "| " + ChatColor.DARK_GREEN + "------------------------------------------");
								player.sendMessage(ChatColor.DARK_GREEN + "| " + ChatColor.GREEN +"Servershop:");
								player.sendMessage(ChatColor.DARK_GREEN + "| " + ChatColor.DARK_GREEN + "------------------------------------------");
								player.sendMessage(ChatColor.DARK_GREEN + "| " + ChatColor.GREEN + "Dieser Shop wurde noch nicht konfiguriert.");
								player.sendMessage(ChatColor.DARK_GREEN + "| " + ChatColor.DARK_GREEN + "------------------------------------------");
								event.setCancelled(true);
								return;
							}
							player.sendMessage(ChatColor.DARK_GREEN + "| " + ChatColor.DARK_GREEN + "------------------------------------------");
							player.sendMessage(ChatColor.DARK_GREEN + "| " + ChatColor.GREEN +besitzer+helper+ "Kistenshop:");
							player.sendMessage(ChatColor.DARK_GREEN + "| " + ChatColor.DARK_GREEN + "------------------------------------------");
							player.sendMessage(ChatColor.DARK_GREEN + "| " + ChatColor.GREEN + "Dieser Shop wurde noch nicht konfiguriert.");
							player.sendMessage(ChatColor.DARK_GREEN + "| " + ChatColor.DARK_GREEN + "------------------------------------------");
							event.setCancelled(true);
							return;
						}
					}
					double ep = Finanzen.getEP(ShopID);
					double vp = Finanzen.getVP(ShopID);
					String wareID = Finanzen.getWareID(ShopID);
					String[] s = wareID.split("#");
					int id = Integer.parseInt(s[0]);
					byte data = (byte) Integer.parseInt(s[1]);
					Inventory shopinv = Finanzen.getShopInv(ShopID);
					ItemStack is = new ItemStack(id, 1729, (short) 0, data);
					int restbestand = 1729-(shopinv.removeItem(is).get(0).getAmount() | 0);
					is.setAmount(restbestand);
					shopinv.addItem(is);
					ItemStack is2 = new ItemStack(id, 1729, (short) 0, data);
					int restplatz = 1729-(shopinv.addItem(is2).get(0).getAmount() | 0);
					is2.setAmount(restplatz);
					shopinv.removeItem(is2);
					if (player.getName().equalsIgnoreCase(besitzer)){
						if (Finanzen.isEingestellt(ShopID)) {
							Finanzen.SchildBeschreiben(ShopID);
							player.sendMessage(ChatColor.DARK_GREEN + "| " + ChatColor.DARK_GREEN + "------------------------------------------");
							player.sendMessage(ChatColor.DARK_GREEN + "| " + ChatColor.GREEN + "Dein Kistenshop:");
							player.sendMessage(ChatColor.DARK_GREEN + "| " + ChatColor.DARK_GREEN + "------------------------------------------");
							if (vp == 0 && ep == 0) {
								player.sendMessage(ChatColor.DARK_GREEN + "| " + ChatColor.GREEN + "Dieser Shop handelt normalerweise für dich mit "+ware+".");
								player.sendMessage(ChatColor.DARK_GREEN + "| " + ChatColor.GREEN + "Er ist aber momentan inaktiv.");
								player.sendMessage(ChatColor.DARK_GREEN + "| " + ChatColor.GRAY + "Gib /shop ware <id> ein, um ihn zu konfigurieren.");
								player.sendMessage(ChatColor.DARK_GREEN + "| " + ChatColor.GRAY + "Tipp: /idsuche <stichwort> und /id helfen bei der ID.");
							}
							if (vp != 0 && ep != 0){
								player.sendMessage(ChatColor.DARK_GREEN + "| " + ChatColor.GREEN + "Dieser Shop handelt für dich mit "+ware+".");
								player.sendMessage(ChatColor.DARK_GREEN + "| " + ChatColor.GREEN + "Er kauft die Ware für " + Finanzen.f.format(ep)+Finanzen.kuerzel+" ein");
								player.sendMessage(ChatColor.DARK_GREEN + "| " + ChatColor.GREEN + "und verkauft sie wieder für "+ Finanzen.f.format(vp)+Finanzen.kuerzel+".");
								player.sendMessage(ChatColor.DARK_GREEN + "| " + ChatColor.GREEN + "Momentaner Bestand: "+restbestand+"/"+(restplatz+restbestand));
							}
							
							if (vp == 0) {
								player.sendMessage(ChatColor.DARK_GREEN + "| " + ChatColor.GREEN + "Der Shop kauft noch "+restplatz+" "+ware+" für dich ein.");
								player.sendMessage(ChatColor.DARK_GREEN + "| " + ChatColor.GREEN + "Insgesamt wären das Kosten von "+Finanzen.f.format(restplatz*ep)+Finanzen.kuerzel+".");
								player.sendMessage(ChatColor.DARK_GREEN + "| " + ChatColor.GRAY + "Tipp: Wenn du weniger kaufen möchtest, verstopfe die Kiste mit shopfremder Ware!");
								
							}
							if (ep == 0) {
								player.sendMessage(ChatColor.DARK_GREEN + "| " + ChatColor.GREEN + "Der Shop verkauft "+restbestand+" "+ware+" für dich.");
								player.sendMessage(ChatColor.DARK_GREEN + "| " + ChatColor.GREEN + "Insgesamt sind Einnahmen von "+Finanzen.f.format(restbestand*vp)+Finanzen.kuerzel+" zu erwarten.");

							}
							player.sendMessage(ChatColor.DARK_GREEN + "| " + ChatColor.DARK_GREEN + "------------------------------------------");
							
						}
					}else {
						if (besitzer.equalsIgnoreCase("AdminShop")) {
							Finanzen.SchildBeschreiben(ShopID);
							player.sendMessage(ChatColor.DARK_GREEN + "| " + ChatColor.DARK_GREEN + "------------------------------------------");
							player.sendMessage(ChatColor.DARK_GREEN + "| " + ChatColor.GREEN + "Servershop:");
							player.sendMessage(ChatColor.DARK_GREEN + "| " + ChatColor.DARK_GREEN + "------------------------------------------");
							if (vp == 0 && ep == 0) {
								player.sendMessage(ChatColor.DARK_GREEN + "| " + ChatColor.GREEN + "Dieser Shop handelt normalerweise mit "+ware+".");
								player.sendMessage(ChatColor.DARK_GREEN + "| " + ChatColor.GREEN + "Er ist aber momentan inaktiv.");
								if (player.hasPermission("finanzen.admin")) {
									player.sendMessage(ChatColor.DARK_GREEN + "| " + ChatColor.GRAY + "Gib /shop ware <id> ein, um ihn zu konfigurieren.");
									player.sendMessage(ChatColor.DARK_GREEN + "| " + ChatColor.GRAY + "Tipp: /idsuche <stichwort> und /id helfen bei der ID.");
								}
								
							}
							if (vp != 0 && ep != 0){
								player.sendMessage(ChatColor.DARK_GREEN + "| " + ChatColor.GREEN + "Dieser Shop handelt unbegrenzt mit "+ware+".");
								player.sendMessage(ChatColor.DARK_GREEN + "| " + ChatColor.GREEN + "Er kauft die Ware für " + Finanzen.f.format(ep)+Finanzen.kuerzel+" ein");
								player.sendMessage(ChatColor.DARK_GREEN + "| " + ChatColor.GREEN + "und verkauft sie wieder für "+ Finanzen.f.format(vp)+Finanzen.kuerzel+".");
								
							}
							
							if (vp == 0) {
								player.sendMessage(ChatColor.DARK_GREEN + "| " + ChatColor.GREEN + "Dieser Shop kauft unbegrenzt "+ware+" für "+Finanzen.f.format(ep)+Finanzen.kuerzel+" ein.");
								player.sendMessage(ChatColor.DARK_GREEN + "| " + ChatColor.GRAY + "Mit dem Befehl /verkaufe shop <anzahl> verkaufst du deine Ware.");
								
							}
							if (ep == 0) {
								player.sendMessage(ChatColor.DARK_GREEN + "| " + ChatColor.GREEN + "Der Shop verkauft unbegrenzt "+ware+" für "+Finanzen.f.format(ep)+Finanzen.kuerzel+".");
								player.sendMessage(ChatColor.DARK_GREEN + "| " + ChatColor.GRAY + "Mit dem Befehl /kaufe shop <anzahl> kannst du hier einkaufen.");

							}
							player.sendMessage(ChatColor.DARK_GREEN + "| " + ChatColor.DARK_GREEN + "------------------------------------------");
							
						}else {
							Finanzen.SchildBeschreiben(ShopID);
							player.sendMessage(ChatColor.DARK_GREEN + "| " + ChatColor.DARK_GREEN + "------------------------------------------");
							player.sendMessage(ChatColor.DARK_GREEN + "| " + ChatColor.GREEN + besitzer+helper+" Kistenshop:");
							player.sendMessage(ChatColor.DARK_GREEN + "| " + ChatColor.DARK_GREEN + "------------------------------------------");
							if (vp == 0 && ep == 0) {
								player.sendMessage(ChatColor.DARK_GREEN + "| " + ChatColor.GREEN + "Dieser Shop handelt normalerweise für dich mit "+ware+".");
								player.sendMessage(ChatColor.DARK_GREEN + "| " + ChatColor.GREEN + "Er ist aber momentan inaktiv.");

							}
							if (vp != 0 && ep != 0){
								player.sendMessage(ChatColor.DARK_GREEN + "| " + ChatColor.GREEN + "Dieser Shop handelt mit "+ware+".");
								player.sendMessage(ChatColor.DARK_GREEN + "| " + ChatColor.GREEN + "Er kauft die Ware für " + Finanzen.f.format(ep)+Finanzen.kuerzel+" ein");
								player.sendMessage(ChatColor.DARK_GREEN + "| " + ChatColor.GREEN + "und verkauft sie wieder für "+ Finanzen.f.format(vp)+Finanzen.kuerzel+".");
								player.sendMessage(ChatColor.DARK_GREEN + "| " + ChatColor.GREEN + "Momentaner Bestand: "+restbestand+"/"+(restplatz+restbestand));
							}
							
							if (vp == 0) {
								player.sendMessage(ChatColor.DARK_GREEN + "| " + ChatColor.GREEN + "Dieser Shop kauft noch "+restplatz+" "+ware+".");
								player.sendMessage(ChatColor.DARK_GREEN + "| " + ChatColor.GREEN + "Insgesamt wären das Einnahmen von "+Finanzen.f.format(restplatz*ep)+Finanzen.kuerzel+".");
							}
							if (ep == 0) {
								player.sendMessage(ChatColor.DARK_GREEN + "| " + ChatColor.GREEN + "Dieser Shop verkauft "+restbestand+" "+ware+".");
								player.sendMessage(ChatColor.DARK_GREEN + "| " + ChatColor.GREEN + "Insgesamt wären das Kosten von "+Finanzen.f.format(restbestand*vp)+Finanzen.kuerzel+".");

							}
							player.sendMessage(ChatColor.DARK_GREEN + "| " + ChatColor.DARK_GREEN + "------------------------------------------");
							
						}
						
					}

					event.setCancelled(true);
					
				}
			}
			if (event.getClickedBlock().getTypeId() == 54){
				Location loc = event.getClickedBlock().getLocation();
				if (Finanzen.isShopChest(loc)) {
					Player player = event.getPlayer();
					String key = loc.getWorld().getName()+":"+loc.getBlockX()+":"+loc.getBlockY()+":"+loc.getBlockZ();
					int ShopID = Finanzen.ShopHM.get(key);
					Finanzen.ShopFokus.put(player.getName(), ShopID);
					String besitzer = Finanzen.getBesitzer(ShopID);
					String helper = "s";
					if (besitzer.endsWith("s") || besitzer.endsWith("z")) {
						helper = "'";
					}
					String ware = Finanzen.getWare(ShopID);
					if (Finanzen.Shops.hasKey(String.valueOf(ShopID), "special")) {
						String special = Finanzen.Shops.getString(String.valueOf(ShopID), "special");
						event.setCancelled(true);
						if (special.equalsIgnoreCase("muehle")) {
							double ep = Finanzen.getEP(ShopID);
							Stadt stadt = Finanzen.getStadtAtLoc(loc);
							player.sendMessage(ChatColor.DARK_GREEN + "| " + ChatColor.DARK_GREEN + "------------------------------------------");
							player.sendMessage(ChatColor.DARK_GREEN + "| " + ChatColor.GREEN + "Mühle von " + Finanzen.capFirst(stadt.getName())+":");
							player.sendMessage(ChatColor.DARK_GREEN + "| " + ChatColor.DARK_GREEN + "------------------------------------------");
							player.sendMessage(ChatColor.DARK_GREEN + "| " + ChatColor.GREEN + "Die Mühle kauft unbegrenzt "+ware+" für "+Finanzen.f.format(ep)+Finanzen.kuerzel+" ein.");
							player.sendMessage(ChatColor.DARK_GREEN + "| " + ChatColor.GRAY + "Mit dem Befehl /verkaufe shop <anzahl> verkaufst du deine Ware.");
							player.sendMessage(ChatColor.DARK_GREEN + "| " + ChatColor.GRAY + "Das Geld dafür kommt aus der Stadtkasse.");
							player.sendMessage(ChatColor.DARK_GREEN + "| " + ChatColor.DARK_GREEN + "------------------------------------------");
							return;
								
							
						}
						if (special.equalsIgnoreCase("fischer")) {
							double vp = Finanzen.getVP(ShopID);
							Stadt stadt = Finanzen.getStadtAtLoc(loc);
							player.sendMessage(ChatColor.DARK_GREEN + "| " + ChatColor.DARK_GREEN + "------------------------------------------");
							player.sendMessage(ChatColor.DARK_GREEN + "| " + ChatColor.GREEN + "Fischer von " + Finanzen.capFirst(stadt.getName())+":");
							player.sendMessage(ChatColor.DARK_GREEN + "| " + ChatColor.DARK_GREEN + "------------------------------------------");
							player.sendMessage(ChatColor.DARK_GREEN + "| " + ChatColor.GREEN + "Der Fischer verkauft unbegrenzt "+ware+" für "+Finanzen.f.format(vp)+Finanzen.kuerzel+".");
							player.sendMessage(ChatColor.DARK_GREEN + "| " + ChatColor.GRAY + "Mit dem Befehl /kaufe shop <anzahl> kaufst du hier ein.");
							player.sendMessage(ChatColor.DARK_GREEN + "| " + ChatColor.GRAY + "Das Geld geht direkt in die Stadtkasse.");
							player.sendMessage(ChatColor.DARK_GREEN + "| " + ChatColor.DARK_GREEN + "------------------------------------------");
							return;
								
							
						}
						if (special.equalsIgnoreCase("baecker")) {
							double vp = Finanzen.getVP(ShopID);
							Stadt stadt = Finanzen.getStadtAtLoc(loc);
							player.sendMessage(ChatColor.DARK_GREEN + "| " + ChatColor.DARK_GREEN + "------------------------------------------");
							player.sendMessage(ChatColor.DARK_GREEN + "| " + ChatColor.GREEN + "Bäcker von " + Finanzen.capFirst(stadt.getName())+":");
							player.sendMessage(ChatColor.DARK_GREEN + "| " + ChatColor.DARK_GREEN + "------------------------------------------");
							player.sendMessage(ChatColor.DARK_GREEN + "| " + ChatColor.GREEN + "Der Bäcker verkauft unbegrenzt "+ware+" für "+Finanzen.f.format(vp)+Finanzen.kuerzel+".");
							player.sendMessage(ChatColor.DARK_GREEN + "| " + ChatColor.GRAY + "Mit dem Befehl /kaufe shop <anzahl> kaufst du hier ein.");
							player.sendMessage(ChatColor.DARK_GREEN + "| " + ChatColor.GRAY + "Das Geld geht direkt in die Stadtkasse.");
							player.sendMessage(ChatColor.DARK_GREEN + "| " + ChatColor.DARK_GREEN + "------------------------------------------");
							return;
						}
						if (special.equalsIgnoreCase("mine")) {
							double vp = Finanzen.getVP(ShopID);
							Stadt stadt = Finanzen.getStadtAtLoc(loc);
							player.sendMessage(ChatColor.DARK_GREEN + "| " + ChatColor.DARK_GREEN + "------------------------------------------");
							player.sendMessage(ChatColor.DARK_GREEN + "| " + ChatColor.GREEN + "Mine von " + Finanzen.capFirst(stadt.getName())+":");
							player.sendMessage(ChatColor.DARK_GREEN + "| " + ChatColor.DARK_GREEN + "------------------------------------------");
							player.sendMessage(ChatColor.DARK_GREEN + "| " + ChatColor.GREEN + "Die Mine verkauft unbegrenzt "+ware+" für "+Finanzen.f.format(vp)+Finanzen.kuerzel+".");
							player.sendMessage(ChatColor.DARK_GREEN + "| " + ChatColor.GRAY + "Mit dem Befehl /kaufe shop <anzahl> kaufst du hier ein.");
							player.sendMessage(ChatColor.DARK_GREEN + "| " + ChatColor.GRAY + "Das Geld geht direkt in die Stadtkasse.");
							player.sendMessage(ChatColor.DARK_GREEN + "| " + ChatColor.DARK_GREEN + "------------------------------------------");
							return;
						}
						if (special.equalsIgnoreCase("fabrik1")) {
							double vp = Finanzen.getVP(ShopID);
							Stadt stadt = Finanzen.getStadtAtLoc(loc);
							player.sendMessage(ChatColor.DARK_GREEN + "| " + ChatColor.DARK_GREEN + "------------------------------------------");
							player.sendMessage(ChatColor.DARK_GREEN + "| " + ChatColor.GREEN + "Fabrik von " + Finanzen.capFirst(stadt.getName())+":");
							player.sendMessage(ChatColor.DARK_GREEN + "| " + ChatColor.DARK_GREEN + "------------------------------------------");
							player.sendMessage(ChatColor.DARK_GREEN + "| " + ChatColor.GREEN + "Diese Fabrik verkauft "+ware+" für "+Finanzen.f.format(vp)+Finanzen.kuerzel+".");
							player.sendMessage(ChatColor.DARK_GREEN + "| " + ChatColor.GRAY + "Mit dem Befehl /kaufe shop <anzahl> kaufst du hier ein.");
							player.sendMessage(ChatColor.DARK_GREEN + "| " + ChatColor.GRAY + "Das Geld geht direkt in die Stadtkasse.");
							player.sendMessage(ChatColor.DARK_GREEN + "| " + ChatColor.DARK_GREEN + "------------------------------------------");
							return;
						}
						if (special.equalsIgnoreCase("fabrik2")) {
							double vp = Finanzen.getVP(ShopID);
							Stadt stadt = Finanzen.getStadtAtLoc(loc);
							player.sendMessage(ChatColor.DARK_GREEN + "| " + ChatColor.DARK_GREEN + "------------------------------------------");
							player.sendMessage(ChatColor.DARK_GREEN + "| " + ChatColor.GREEN + "Fabrik von " + Finanzen.capFirst(stadt.getName())+":");
							player.sendMessage(ChatColor.DARK_GREEN + "| " + ChatColor.DARK_GREEN + "------------------------------------------");
							player.sendMessage(ChatColor.DARK_GREEN + "| " + ChatColor.GREEN + "Diese Fabrik verkauft "+ware+" für "+Finanzen.f.format(vp)+Finanzen.kuerzel+".");
							player.sendMessage(ChatColor.DARK_GREEN + "| " + ChatColor.GRAY + "Mit dem Befehl /kaufe shop <anzahl> kaufst du hier ein.");
							player.sendMessage(ChatColor.DARK_GREEN + "| " + ChatColor.GRAY + "Das Geld geht direkt in die Stadtkasse.");
							player.sendMessage(ChatColor.DARK_GREEN + "| " + ChatColor.DARK_GREEN + "------------------------------------------");
							return;
						}
						if (special.equalsIgnoreCase("fabrik3")) {
							double vp = Finanzen.getVP(ShopID);
							Stadt stadt = Finanzen.getStadtAtLoc(loc);
							player.sendMessage(ChatColor.DARK_GREEN + "| " + ChatColor.DARK_GREEN + "------------------------------------------");
							player.sendMessage(ChatColor.DARK_GREEN + "| " + ChatColor.GREEN + "Fabrik von " + Finanzen.capFirst(stadt.getName())+":");
							player.sendMessage(ChatColor.DARK_GREEN + "| " + ChatColor.DARK_GREEN + "------------------------------------------");
							player.sendMessage(ChatColor.DARK_GREEN + "| " + ChatColor.GREEN + "Diese Fabrik verkauft "+ware+" für "+Finanzen.f.format(vp)+Finanzen.kuerzel+".");
							player.sendMessage(ChatColor.DARK_GREEN + "| " + ChatColor.GRAY + "Mit dem Befehl /kaufe shop <anzahl> kaufst du hier ein.");
							player.sendMessage(ChatColor.DARK_GREEN + "| " + ChatColor.GRAY + "Das Geld geht direkt in die Stadtkasse.");
							player.sendMessage(ChatColor.DARK_GREEN + "| " + ChatColor.DARK_GREEN + "------------------------------------------");
							return;
						}
						if (special.equalsIgnoreCase("tierheim")) {
							double ep = Finanzen.getEP(ShopID);
							Stadt stadt = Finanzen.getStadtAtLoc(loc);
							player.sendMessage(ChatColor.DARK_GREEN + "| " + ChatColor.DARK_GREEN + "------------------------------------------");
							player.sendMessage(ChatColor.DARK_GREEN + "| " + ChatColor.GREEN + "Tierheim von " + Finanzen.capFirst(stadt.getName())+":");
							player.sendMessage(ChatColor.DARK_GREEN + "| " + ChatColor.DARK_GREEN + "------------------------------------------");
							player.sendMessage(ChatColor.DARK_GREEN + "| " + ChatColor.GREEN + "Diese Fabrik verkauft "+ware+" für "+Finanzen.f.format(ep)+Finanzen.kuerzel+".");
							player.sendMessage(ChatColor.DARK_GREEN + "| " + ChatColor.GRAY + "Mit dem Befehl /kaufe shop <anzahl> kaufst du hier ein.");
							player.sendMessage(ChatColor.DARK_GREEN + "| " + ChatColor.GRAY + "Das Geld geht direkt in die Stadtkasse.");
							player.sendMessage(ChatColor.DARK_GREEN + "| " + ChatColor.DARK_GREEN + "------------------------------------------");
							return;
						}
						
					}
					if (ware.equalsIgnoreCase("noch nicht eingestellt")) {
						if (player.getName().equalsIgnoreCase(besitzer)){
							player.sendMessage(ChatColor.DARK_GREEN + "| " + ChatColor.DARK_GREEN + "------------------------------------------");
							player.sendMessage(ChatColor.DARK_GREEN + "| " + ChatColor.GREEN + "Dein Kistenshop:");
							player.sendMessage(ChatColor.DARK_GREEN + "| " + ChatColor.DARK_GREEN + "------------------------------------------");
							player.sendMessage(ChatColor.DARK_GREEN + "| " + ChatColor.GREEN + "Dieser Shop wurde noch nicht konfiguriert.");
							player.sendMessage(ChatColor.DARK_GREEN + "| " + ChatColor.GREEN + "Such dir mit /idsuche oder /id die Ware raus, mit der du hier handeln möchtest.");
							player.sendMessage(ChatColor.DARK_GREEN + "| " + ChatColor.GREEN + "Der Befehl /shop ware <id> stellt den Shop auf die Handelsware ein.");
							player.sendMessage(ChatColor.DARK_GREEN + "| " + ChatColor.DARK_GREEN + "------------------------------------------");
							return;
						}else {
							if (besitzer.equalsIgnoreCase("AdminShop")) {
								player.sendMessage(ChatColor.DARK_GREEN + "| " + ChatColor.DARK_GREEN + "------------------------------------------");
								player.sendMessage(ChatColor.DARK_GREEN + "| " + ChatColor.GREEN +"Servershop:");
								player.sendMessage(ChatColor.DARK_GREEN + "| " + ChatColor.DARK_GREEN + "------------------------------------------");
								player.sendMessage(ChatColor.DARK_GREEN + "| " + ChatColor.GREEN + "Dieser Shop wurde noch nicht konfiguriert.");
								player.sendMessage(ChatColor.DARK_GREEN + "| " + ChatColor.DARK_GREEN + "------------------------------------------");
								event.setCancelled(true);
								return;
							}
							player.sendMessage(ChatColor.DARK_GREEN + "| " + ChatColor.DARK_GREEN + "------------------------------------------");
							player.sendMessage(ChatColor.DARK_GREEN + "| " + ChatColor.GREEN +besitzer+helper+ "Kistenshop:");
							player.sendMessage(ChatColor.DARK_GREEN + "| " + ChatColor.DARK_GREEN + "------------------------------------------");
							player.sendMessage(ChatColor.DARK_GREEN + "| " + ChatColor.GREEN + "Dieser Shop wurde noch nicht konfiguriert.");
							player.sendMessage(ChatColor.DARK_GREEN + "| " + ChatColor.DARK_GREEN + "------------------------------------------");
							event.setCancelled(true);
							return;
						}
					}
					double ep = Finanzen.getEP(ShopID);
					double vp = Finanzen.getVP(ShopID);
					String wareID = Finanzen.getWareID(ShopID);
					String[] s = wareID.split("#");
					int id = Integer.parseInt(s[0]);
					byte data = (byte) Integer.parseInt(s[1]);
					Inventory shopinv = Finanzen.getShopInv(ShopID);
					ItemStack is = new ItemStack(id, 1729, (short) 0, data);
					int restbestand = 1729-(shopinv.removeItem(is).get(0).getAmount() | 0);
					is.setAmount(restbestand);
					shopinv.addItem(is);
					ItemStack is2 = new ItemStack(id, 1729, (short) 0, data);
					int restplatz = 1729-(shopinv.addItem(is2).get(0).getAmount() | 0);
					is2.setAmount(restplatz);
					shopinv.removeItem(is2);
					
					if (player.getName().equalsIgnoreCase(besitzer)){
						if (Finanzen.isEingestellt(ShopID)) {
							Finanzen.SchildBeschreiben(ShopID);
							player.sendMessage(ChatColor.DARK_GREEN + "| " + ChatColor.DARK_GREEN + "------------------------------------------");
							player.sendMessage(ChatColor.DARK_GREEN + "| " + ChatColor.GREEN + "Dein Kistenshop:");
							player.sendMessage(ChatColor.DARK_GREEN + "| " + ChatColor.DARK_GREEN + "------------------------------------------");
							if (vp == 0 && ep == 0) {
								player.sendMessage(ChatColor.DARK_GREEN + "| " + ChatColor.GREEN + "Dieser Shop handelt normalerweise für dich mit "+ware+".");
								player.sendMessage(ChatColor.DARK_GREEN + "| " + ChatColor.GREEN + "Er ist aber momentan inaktiv.");
								player.sendMessage(ChatColor.DARK_GREEN + "| " + ChatColor.GRAY + "Gib /shop ware <id> ein, um ihn zu konfigurieren.");
								player.sendMessage(ChatColor.DARK_GREEN + "| " + ChatColor.GRAY + "Tipp: /idsuche <stichwort> und /id helfen bei der ID.");
							}
							if (vp != 0 && ep != 0){
								player.sendMessage(ChatColor.DARK_GREEN + "| " + ChatColor.GREEN + "Dieser Shop handelt für dich mit "+ware+".");
								player.sendMessage(ChatColor.DARK_GREEN + "| " + ChatColor.GREEN + "Er kauft die Ware für " + Finanzen.f.format(ep)+Finanzen.kuerzel+" ein");
								player.sendMessage(ChatColor.DARK_GREEN + "| " + ChatColor.GREEN + "und verkauft sie wieder für "+ Finanzen.f.format(vp)+Finanzen.kuerzel+".");
								player.sendMessage(ChatColor.DARK_GREEN + "| " + ChatColor.GREEN + "Momentaner Bestand: "+restbestand+"/"+(restplatz+restbestand));
							}
							
							if (vp == 0) {
								player.sendMessage(ChatColor.DARK_GREEN + "| " + ChatColor.GREEN + "Der Shop kauft noch "+restplatz+" "+ware+" für dich ein.");
								player.sendMessage(ChatColor.DARK_GREEN + "| " + ChatColor.GREEN + "Insgesamt wären das Kosten von "+Finanzen.f.format(restplatz*ep)+Finanzen.kuerzel+".");
								player.sendMessage(ChatColor.DARK_GREEN + "| " + ChatColor.GRAY + "Tipp: Wenn du weniger kaufen möchtest, verstopfe die Kiste mit shopfremder Ware!");
								
							}
							if (ep == 0) {
								player.sendMessage(ChatColor.DARK_GREEN + "| " + ChatColor.GREEN + "Der Shop verkauft "+restbestand+" "+ware+" für dich.");
								player.sendMessage(ChatColor.DARK_GREEN + "| " + ChatColor.GREEN + "Insgesamt sind Einnahmen von "+Finanzen.f.format(restbestand*vp)+Finanzen.kuerzel+" zu erwarten.");

							}
							player.sendMessage(ChatColor.DARK_GREEN + "| " + ChatColor.DARK_GREEN + "------------------------------------------");
							
						}
					}else {
						event.setCancelled(true);
						if (besitzer.equalsIgnoreCase("AdminShop")) {
							Finanzen.SchildBeschreiben(ShopID);
							player.sendMessage(ChatColor.DARK_GREEN + "| " + ChatColor.DARK_GREEN + "------------------------------------------");
							player.sendMessage(ChatColor.DARK_GREEN + "| " + ChatColor.GREEN + "Servershop:");
							player.sendMessage(ChatColor.DARK_GREEN + "| " + ChatColor.DARK_GREEN + "------------------------------------------");
							if (vp == 0 && ep == 0) {
								player.sendMessage(ChatColor.DARK_GREEN + "| " + ChatColor.GREEN + "Dieser Shop handelt normalerweise mit "+ware+".");
								player.sendMessage(ChatColor.DARK_GREEN + "| " + ChatColor.GREEN + "Er ist aber momentan inaktiv.");
								if (player.hasPermission("finanzen.admin")) {
									player.sendMessage(ChatColor.DARK_GREEN + "| " + ChatColor.GRAY + "Gib /shop ware <id> ein, um ihn zu konfigurieren.");
									player.sendMessage(ChatColor.DARK_GREEN + "| " + ChatColor.GRAY + "Tipp: /idsuche <stichwort> und /id helfen bei der ID.");
								}
								
							}
							if (vp != 0 && ep != 0){
								player.sendMessage(ChatColor.DARK_GREEN + "| " + ChatColor.GREEN + "Dieser Shop handelt unbegrenzt mit "+ware+".");
								player.sendMessage(ChatColor.DARK_GREEN + "| " + ChatColor.GREEN + "Er kauft die Ware für " + Finanzen.f.format(ep)+Finanzen.kuerzel+" ein");
								player.sendMessage(ChatColor.DARK_GREEN + "| " + ChatColor.GREEN + "und verkauft sie wieder für "+ Finanzen.f.format(vp)+Finanzen.kuerzel+".");
								
							}
							
							if (vp == 0) {
								player.sendMessage(ChatColor.DARK_GREEN + "| " + ChatColor.GREEN + "Dieser Shop kauft unbegrenzt "+ware+" für "+Finanzen.f.format(ep)+Finanzen.kuerzel+" ein.");
								player.sendMessage(ChatColor.DARK_GREEN + "| " + ChatColor.GRAY + "Mit dem Befehl /verkaufe shop <anzahl> verkaufst du deine Ware.");
								
							}
							if (ep == 0) {
								player.sendMessage(ChatColor.DARK_GREEN + "| " + ChatColor.GREEN + "Der Shop verkauft unbegrenzt "+ware+" für "+Finanzen.f.format(ep)+Finanzen.kuerzel+".");
								player.sendMessage(ChatColor.DARK_GREEN + "| " + ChatColor.GRAY + "Mit dem Befehl /kaufe shop <anzahl> kannst du hier einkaufen.");

							}
							player.sendMessage(ChatColor.DARK_GREEN + "| " + ChatColor.DARK_GREEN + "------------------------------------------");
							
						}else {
							Finanzen.SchildBeschreiben(ShopID);
							player.sendMessage(ChatColor.DARK_GREEN + "| " + ChatColor.DARK_GREEN + "------------------------------------------");
							player.sendMessage(ChatColor.DARK_GREEN + "| " + ChatColor.GREEN + besitzer+helper+" Kistenshop:");
							player.sendMessage(ChatColor.DARK_GREEN + "| " + ChatColor.DARK_GREEN + "------------------------------------------");
							if (vp == 0 && ep == 0) {
								player.sendMessage(ChatColor.DARK_GREEN + "| " + ChatColor.GREEN + "Dieser Shop handelt normalerweise für dich mit "+ware+".");
								player.sendMessage(ChatColor.DARK_GREEN + "| " + ChatColor.GREEN + "Er ist aber momentan inaktiv.");

							}
							if (vp != 0 && ep != 0){
								player.sendMessage(ChatColor.DARK_GREEN + "| " + ChatColor.GREEN + "Dieser Shop handelt mit "+ware+".");
								player.sendMessage(ChatColor.DARK_GREEN + "| " + ChatColor.GREEN + "Er kauft die Ware für " + Finanzen.f.format(ep)+Finanzen.kuerzel+" ein");
								player.sendMessage(ChatColor.DARK_GREEN + "| " + ChatColor.GREEN + "und verkauft sie wieder für "+ Finanzen.f.format(vp)+Finanzen.kuerzel+".");
								player.sendMessage(ChatColor.DARK_GREEN + "| " + ChatColor.GREEN + "Momentaner Bestand: "+restbestand+"/"+(restplatz+restbestand));
							}
							
							if (vp == 0) {
								player.sendMessage(ChatColor.DARK_GREEN + "| " + ChatColor.GREEN + "Dieser Shop kauft noch "+restplatz+" "+ware+".");
								player.sendMessage(ChatColor.DARK_GREEN + "| " + ChatColor.GREEN + "Insgesamt wären das Einnahmen von "+Finanzen.f.format(restplatz*ep)+Finanzen.kuerzel+".");
							}
							if (ep == 0) {
								player.sendMessage(ChatColor.DARK_GREEN + "| " + ChatColor.GREEN + "Dieser Shop verkauft "+restbestand+" "+ware+".");
								player.sendMessage(ChatColor.DARK_GREEN + "| " + ChatColor.GREEN + "Insgesamt wären das Kosten von "+Finanzen.f.format(restbestand*vp)+Finanzen.kuerzel+".");

							}
							player.sendMessage(ChatColor.DARK_GREEN + "| " + ChatColor.DARK_GREEN + "------------------------------------------");
							
						}
						
					}

				}
			}
		}
		
		
	}
}
