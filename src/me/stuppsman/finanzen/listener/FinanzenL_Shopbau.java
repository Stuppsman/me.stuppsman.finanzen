package me.stuppsman.finanzen.listener;

import me.stuppsman.finanzen.Finanzen;
import me.stuppsman.finanzen.Stadt;

import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.SignChangeEvent;

import com.xemsdoom.mexdb.exception.EmptyIndexException;
import com.xemsdoom.mexdb.system.Entry;

public class FinanzenL_Shopbau implements Listener {

	private Finanzen plugin;

	public FinanzenL_Shopbau(Finanzen plugin) {
		this.plugin = plugin;
	}
	
	@EventHandler
	public void Shopbau(SignChangeEvent event) {
		Player player = event.getPlayer();
		String name = player.getName();
		Location uS = event.getBlock().getLocation();
		uS.setY(uS.getY()-1);
		if (Finanzen.muehle.contains(name)) {
			if (!(uS.getBlock().getType() == Material.AIR)) {
				player.sendMessage(ChatColor.RED + "Unter dem Schild muss Platz für eine Kiste sein!");
				event.getBlock().breakNaturally();
				event.setCancelled(true);
				return;
			}
			if (Finanzen.isNaheKiste(uS)) {
				player.sendMessage(ChatColor.RED + "Es darf sich keine Kiste neben einer Shopkiste befinden!");
				event.getBlock().breakNaturally();
				event.setCancelled(true);
				return;
			}
			name="Mühle";
			try {
				int index = 0;
				while (Finanzen.Shops.hasIndex(String.valueOf(index))) {
					index++;
				}
				Stadt stadt = Finanzen.getStadtAtLoc(uS);
				double ep = Finanzen.config.getDouble("295.0.EK");
				double rabatt = Finanzen.config.getDouble("Gebaeude.muehle.shoprabatt");
				ep = ep*(1+rabatt/100);
				Location SL = event.getBlock().getLocation();
				Entry neuerShop = new Entry(String.valueOf(index));
				neuerShop.addValue("besitzer", stadt.getName());
				neuerShop.addValue("special", "muehle");
				neuerShop.addValue("id", "295#0");
				neuerShop.addValue("ep", ep);
				neuerShop.addValue("vp", "0");
				neuerShop.addValue("x", String.valueOf(SL.getBlockX()));
				neuerShop.addValue("y", String.valueOf(SL.getBlockY()));
				neuerShop.addValue("z", String.valueOf(SL.getBlockZ()));
				neuerShop.addValue("world", SL.getWorld().getName());
				Finanzen.Shops.addEntry(neuerShop);
				Finanzen.Shops.push();
				
				
				String key = SL.getWorld().getName() + ":" + SL.getBlockX() + ":" + SL.getBlockY() + ":" + SL.getBlockZ(); 
				Finanzen.ShopHM.put(key, index);
				
				Location helper = new Location (SL.getWorld(), SL.getBlockX(), SL.getBlockY(), SL.getBlockZ());
				byte data = SL.getBlock().getData();
				if (data == 2) {
					helper.setZ(helper.getZ()+1);
				}
				if (data == 3) {
					helper.setZ(helper.getZ()-1);
				}
				if (data == 4) {
					helper.setX(helper.getX()+1);
				}
				if (data == 5) {
					helper.setX(helper.getX()-1);
				}
				Finanzen.SchildHalterBlocks.add(helper);
				
				uS.getBlock().setTypeId(54);
				uS.getBlock().setData(event.getBlock().getData());
				player.sendMessage(ChatColor.GREEN + "Die Mühle kauft jetzt Weizensamen für "+Finanzen.f.format(ep)+Finanzen.kuerzel+".");
				Finanzen.SchildBeschreiben(index);
				event.setLine(0, "--Mühle--");
				event.setLine(1, "kauft");
				event.setLine(2, "Weizensamen");
				event.setLine(3, "für "+Finanzen.f.format(ep)+Finanzen.kuerzel);
				Finanzen.muehle.remove(name);
				
			} catch (EmptyIndexException e) {
				e.printStackTrace();
				return;
			}
			
			
			
		}
		if (Finanzen.fischer.contains(name)) {
			if (!(uS.getBlock().getType() == Material.AIR)) {
				player.sendMessage(ChatColor.RED + "Unter dem Schild muss Platz für eine Kiste sein!");
				event.getBlock().breakNaturally();
				event.setCancelled(true);
				return;
			}
			if (Finanzen.isNaheKiste(uS)) {
				player.sendMessage(ChatColor.RED + "Es darf sich keine Kiste neben einer Shopkiste befinden!");
				event.getBlock().breakNaturally();
				event.setCancelled(true);
				return;
			}
			name="Fischer";
			try {
				int index = 0;
				while (Finanzen.Shops.hasIndex(String.valueOf(index))) {
					index++;
				}
				Stadt stadt = Finanzen.getStadtAtLoc(uS);
				double vp = Finanzen.config.getDouble("349.0.VK");
				double rabatt = Finanzen.config.getDouble("Gebaeude.fischer.shoprabatt");
				vp = vp*(1-rabatt/100);
				Location SL = event.getBlock().getLocation();
				Entry neuerShop = new Entry(String.valueOf(index));
				neuerShop.addValue("besitzer", stadt.getName());
				neuerShop.addValue("special", "fischer");
				neuerShop.addValue("id", "349#0");
				neuerShop.addValue("ep", "0");
				neuerShop.addValue("vp", vp);
				neuerShop.addValue("x", String.valueOf(SL.getBlockX()));
				neuerShop.addValue("y", String.valueOf(SL.getBlockY()));
				neuerShop.addValue("z", String.valueOf(SL.getBlockZ()));
				neuerShop.addValue("world", SL.getWorld().getName());
				Finanzen.Shops.addEntry(neuerShop);
				Finanzen.Shops.push();
				
				
				String key = SL.getWorld().getName() + ":" + SL.getBlockX() + ":" + SL.getBlockY() + ":" + SL.getBlockZ(); 
				Finanzen.ShopHM.put(key, index);
				
				Location helper = new Location (SL.getWorld(), SL.getBlockX(), SL.getBlockY(), SL.getBlockZ());
				byte data = SL.getBlock().getData();
				if (data == 2) {
					helper.setZ(helper.getZ()+1);
				}
				if (data == 3) {
					helper.setZ(helper.getZ()-1);
				}
				if (data == 4) {
					helper.setX(helper.getX()+1);
				}
				if (data == 5) {
					helper.setX(helper.getX()-1);
				}
				Finanzen.SchildHalterBlocks.add(helper);
				
				uS.getBlock().setTypeId(54);
				uS.getBlock().setData(event.getBlock().getData());
				player.sendMessage(ChatColor.GREEN + "Der Fischer verkauft jetzt Fische für "+Finanzen.f.format(vp)+Finanzen.kuerzel+".");
				Finanzen.SchildBeschreiben(index);
				event.setLine(0, "--Fischer--");
				event.setLine(1, "verkauft");
				event.setLine(2, "frischen Fisch");
				event.setLine(3, "für "+Finanzen.f.format(vp)+Finanzen.kuerzel);
				Finanzen.fischer.remove(name);
				
			} catch (EmptyIndexException e) {
				e.printStackTrace();
				return;
			}
			
			
			
		}
		if (Finanzen.baecker.contains(name)) {
			if (!(uS.getBlock().getType() == Material.AIR)) {
				player.sendMessage(ChatColor.RED + "Unter dem Schild muss Platz für eine Kiste sein!");
				event.getBlock().breakNaturally();
				event.setCancelled(true);
				return;
			}
			if (Finanzen.isNaheKiste(uS)) {
				player.sendMessage(ChatColor.RED + "Es darf sich keine Kiste neben einer Shopkiste befinden!");
				event.getBlock().breakNaturally();
				event.setCancelled(true);
				return;
			}
			name="Bäcker";
			try {
				int index = 0;
				while (Finanzen.Shops.hasIndex(String.valueOf(index))) {
					index++;
				}
				Stadt stadt = Finanzen.getStadtAtLoc(uS);
				double vp = Finanzen.config.getDouble("297.0.VK");
				double rabatt = Finanzen.config.getDouble("Gebaeude.fischer.shoprabatt");
				vp = vp*(1-rabatt/100);
				Location SL = event.getBlock().getLocation();
				Entry neuerShop = new Entry(String.valueOf(index));
				neuerShop.addValue("besitzer", stadt.getName());
				neuerShop.addValue("special", "baecker");
				neuerShop.addValue("id", "297#0");
				neuerShop.addValue("ep", "0");
				neuerShop.addValue("vp", vp);
				neuerShop.addValue("x", String.valueOf(SL.getBlockX()));
				neuerShop.addValue("y", String.valueOf(SL.getBlockY()));
				neuerShop.addValue("z", String.valueOf(SL.getBlockZ()));
				neuerShop.addValue("world", SL.getWorld().getName());
				Finanzen.Shops.addEntry(neuerShop);
				Finanzen.Shops.push();
				
				
				String key = SL.getWorld().getName() + ":" + SL.getBlockX() + ":" + SL.getBlockY() + ":" + SL.getBlockZ(); 
				Finanzen.ShopHM.put(key, index);
				
				Location helper = new Location (SL.getWorld(), SL.getBlockX(), SL.getBlockY(), SL.getBlockZ());
				byte data = SL.getBlock().getData();
				if (data == 2) {
					helper.setZ(helper.getZ()+1);
				}
				if (data == 3) {
					helper.setZ(helper.getZ()-1);
				}
				if (data == 4) {
					helper.setX(helper.getX()+1);
				}
				if (data == 5) {
					helper.setX(helper.getX()-1);
				}
				Finanzen.SchildHalterBlocks.add(helper);
				
				uS.getBlock().setTypeId(54);
				uS.getBlock().setData(event.getBlock().getData());
				player.sendMessage(ChatColor.GREEN + "Der Bäcker verkauft jetzt Brot für "+Finanzen.f.format(vp)+Finanzen.kuerzel+".");
				Finanzen.SchildBeschreiben(index);
				event.setLine(0, "--Bäcker--");
				event.setLine(1, "verkauft");
				event.setLine(2, "frisches Brot");
				event.setLine(3, "für "+Finanzen.f.format(vp)+Finanzen.kuerzel);
				Finanzen.baecker.remove(name);
				
			} catch (EmptyIndexException e) {
				e.printStackTrace();
				return;
			}
			
			
			
		}
		if (Finanzen.mine.contains(name)) {
			if (!(uS.getBlock().getType() == Material.AIR)) {
				player.sendMessage(ChatColor.RED + "Unter dem Schild muss Platz für eine Kiste sein!");
				event.getBlock().breakNaturally();
				event.setCancelled(true);
				return;
			}
			if (Finanzen.isNaheKiste(uS)) {
				player.sendMessage(ChatColor.RED + "Es darf sich keine Kiste neben einer Shopkiste befinden!");
				event.getBlock().breakNaturally();
				event.setCancelled(true);
				return;
			}
			name="Mine";
			try {
				int index = 0;
				while (Finanzen.Shops.hasIndex(String.valueOf(index))) {
					index++;
				}
				Stadt stadt = Finanzen.getStadtAtLoc(uS);
				double vp = Finanzen.config.getDouble("263.0.VK");
				double rabatt = Finanzen.config.getDouble("Gebaeude.mine.shoprabatt");
				vp = vp*(1-rabatt/100);
				Location SL = event.getBlock().getLocation();
				Entry neuerShop = new Entry(String.valueOf(index));
				neuerShop.addValue("besitzer", stadt.getName());
				neuerShop.addValue("special", "mine");
				neuerShop.addValue("id", "263#0");
				neuerShop.addValue("ep", "0");
				neuerShop.addValue("vp", vp);
				neuerShop.addValue("x", String.valueOf(SL.getBlockX()));
				neuerShop.addValue("y", String.valueOf(SL.getBlockY()));
				neuerShop.addValue("z", String.valueOf(SL.getBlockZ()));
				neuerShop.addValue("world", SL.getWorld().getName());
				Finanzen.Shops.addEntry(neuerShop);
				Finanzen.Shops.push();
				
				
				String key = SL.getWorld().getName() + ":" + SL.getBlockX() + ":" + SL.getBlockY() + ":" + SL.getBlockZ(); 
				Finanzen.ShopHM.put(key, index);
				
				Location helper = new Location (SL.getWorld(), SL.getBlockX(), SL.getBlockY(), SL.getBlockZ());
				byte data = SL.getBlock().getData();
				if (data == 2) {
					helper.setZ(helper.getZ()+1);
				}
				if (data == 3) {
					helper.setZ(helper.getZ()-1);
				}
				if (data == 4) {
					helper.setX(helper.getX()+1);
				}
				if (data == 5) {
					helper.setX(helper.getX()-1);
				}
				Finanzen.SchildHalterBlocks.add(helper);
				
				uS.getBlock().setTypeId(54);
				uS.getBlock().setData(event.getBlock().getData());
				player.sendMessage(ChatColor.GREEN + "Die Mine verkauft jetzt Kohle für "+Finanzen.f.format(vp)+Finanzen.kuerzel+".");
				Finanzen.SchildBeschreiben(index);
				event.setLine(0, "--Mine--");
				event.setLine(1, "verkauft");
				event.setLine(2, "Kohle");
				event.setLine(3, "für "+Finanzen.f.format(vp)+Finanzen.kuerzel);
				Finanzen.mine.remove(name);
				
			} catch (EmptyIndexException e) {
				e.printStackTrace();
				return;
			}
			
			
			
		}
		if (Finanzen.fabrik1.contains(name)) {
			if (!(uS.getBlock().getType() == Material.AIR)) {
				player.sendMessage(ChatColor.RED + "Unter dem Schild muss Platz für eine Kiste sein!");
				event.getBlock().breakNaturally();
				event.setCancelled(true);
				return;
			}
			if (Finanzen.isNaheKiste(uS)) {
				player.sendMessage(ChatColor.RED + "Es darf sich keine Kiste neben einer Shopkiste befinden!");
				event.getBlock().breakNaturally();
				event.setCancelled(true);
				return;
			}
			name="Fabrik";
			try {
				int index = 0;
				while (Finanzen.Shops.hasIndex(String.valueOf(index))) {
					index++;
				}
				Stadt stadt = Finanzen.getStadtAtLoc(uS);
				double vp = Finanzen.config.getDouble("331.0.VK");
				double rabatt = Finanzen.config.getDouble("Gebaeude.fabrik1.shoprabatt");
				vp = vp*(1-rabatt/100);
				Location SL = event.getBlock().getLocation();
				Entry neuerShop = new Entry(String.valueOf(index));
				neuerShop.addValue("besitzer", stadt.getName());
				neuerShop.addValue("special", "fabrik1");
				neuerShop.addValue("id", "331#0");
				neuerShop.addValue("ep", "0");
				neuerShop.addValue("vp", vp);
				neuerShop.addValue("x", String.valueOf(SL.getBlockX()));
				neuerShop.addValue("y", String.valueOf(SL.getBlockY()));
				neuerShop.addValue("z", String.valueOf(SL.getBlockZ()));
				neuerShop.addValue("world", SL.getWorld().getName());
				Finanzen.Shops.addEntry(neuerShop);
				Finanzen.Shops.push();
				
				
				String key = SL.getWorld().getName() + ":" + SL.getBlockX() + ":" + SL.getBlockY() + ":" + SL.getBlockZ(); 
				Finanzen.ShopHM.put(key, index);
				
				Location helper = new Location (SL.getWorld(), SL.getBlockX(), SL.getBlockY(), SL.getBlockZ());
				byte data = SL.getBlock().getData();
				if (data == 2) {
					helper.setZ(helper.getZ()+1);
				}
				if (data == 3) {
					helper.setZ(helper.getZ()-1);
				}
				if (data == 4) {
					helper.setX(helper.getX()+1);
				}
				if (data == 5) {
					helper.setX(helper.getX()-1);
				}
				Finanzen.SchildHalterBlocks.add(helper);
				
				uS.getBlock().setTypeId(54);
				uS.getBlock().setData(event.getBlock().getData());
				player.sendMessage(ChatColor.GREEN + "Die Fabrik verkauft jetzt Redstone für "+Finanzen.f.format(vp)+Finanzen.kuerzel+".");
				Finanzen.SchildBeschreiben(index);
				event.setLine(0, "--Fabrik--");
				event.setLine(1, "verkauft");
				event.setLine(2, "Redstone");
				event.setLine(3, "für "+Finanzen.f.format(vp)+Finanzen.kuerzel);
				Finanzen.fabrik1.remove(name);
				
			} catch (EmptyIndexException e) {
				e.printStackTrace();
				return;
			}
		}
		if (Finanzen.fabrik2.contains(name)) {
			if (!(uS.getBlock().getType() == Material.AIR)) {
				player.sendMessage(ChatColor.RED + "Unter dem Schild muss Platz für eine Kiste sein!");
				event.getBlock().breakNaturally();
				event.setCancelled(true);
				return;
			}
			if (Finanzen.isNaheKiste(uS)) {
				player.sendMessage(ChatColor.RED + "Es darf sich keine Kiste neben einer Shopkiste befinden!");
				event.getBlock().breakNaturally();
				event.setCancelled(true);
				return;
			}
			name="Fabrik";
			try {
				int index = 0;
				while (Finanzen.Shops.hasIndex(String.valueOf(index))) {
					index++;
				}
				Stadt stadt = Finanzen.getStadtAtLoc(uS);
				double vp = Finanzen.config.getDouble("33.7.VK");
				double rabatt = Finanzen.config.getDouble("Gebaeude.fabrik2.shoprabatt");
				vp = vp*(1-rabatt/100);
				Location SL = event.getBlock().getLocation();
				Entry neuerShop = new Entry(String.valueOf(index));
				neuerShop.addValue("besitzer", stadt.getName());
				neuerShop.addValue("special", "fabrik2");
				neuerShop.addValue("id", "33#7");
				neuerShop.addValue("ep", "0");
				neuerShop.addValue("vp", vp);
				neuerShop.addValue("x", String.valueOf(SL.getBlockX()));
				neuerShop.addValue("y", String.valueOf(SL.getBlockY()));
				neuerShop.addValue("z", String.valueOf(SL.getBlockZ()));
				neuerShop.addValue("world", SL.getWorld().getName());
				Finanzen.Shops.addEntry(neuerShop);
				Finanzen.Shops.push();
				
				
				String key = SL.getWorld().getName() + ":" + SL.getBlockX() + ":" + SL.getBlockY() + ":" + SL.getBlockZ(); 
				Finanzen.ShopHM.put(key, index);
				
				Location helper = new Location (SL.getWorld(), SL.getBlockX(), SL.getBlockY(), SL.getBlockZ());
				byte data = SL.getBlock().getData();
				if (data == 2) {
					helper.setZ(helper.getZ()+1);
				}
				if (data == 3) {
					helper.setZ(helper.getZ()-1);
				}
				if (data == 4) {
					helper.setX(helper.getX()+1);
				}
				if (data == 5) {
					helper.setX(helper.getX()-1);
				}
				Finanzen.SchildHalterBlocks.add(helper);
				
				uS.getBlock().setTypeId(54);
				uS.getBlock().setData(event.getBlock().getData());
				player.sendMessage(ChatColor.GREEN + "Die Fabrik verkauft jetzt Kolben für "+Finanzen.f.format(vp)+Finanzen.kuerzel+".");
				Finanzen.SchildBeschreiben(index);
				event.setLine(0, "--Fabrik--");
				event.setLine(1, "verkauft");
				event.setLine(2, "Kolben");
				event.setLine(3, "für "+Finanzen.f.format(vp)+Finanzen.kuerzel);
				Finanzen.fabrik2.remove(name);
				
			} catch (EmptyIndexException e) {
				e.printStackTrace();
				return;
			}
		}
		if (Finanzen.fabrik3.contains(name)) {
			if (!(uS.getBlock().getType() == Material.AIR)) {
				player.sendMessage(ChatColor.RED + "Unter dem Schild muss Platz für eine Kiste sein!");
				event.getBlock().breakNaturally();
				event.setCancelled(true);
				return;
			}
			if (Finanzen.isNaheKiste(uS)) {
				player.sendMessage(ChatColor.RED + "Es darf sich keine Kiste neben einer Shopkiste befinden!");
				event.getBlock().breakNaturally();
				event.setCancelled(true);
				return;
			}
			name="Fabrik";
			try {
				int index = 0;
				while (Finanzen.Shops.hasIndex(String.valueOf(index))) {
					index++;
				}
				Stadt stadt = Finanzen.getStadtAtLoc(uS);
				double vp = Finanzen.config.getDouble("29.7.VK");
				double rabatt = Finanzen.config.getDouble("Gebaeude.fabrik3.shoprabatt");
				vp = vp*(1-rabatt/100);
				Location SL = event.getBlock().getLocation();
				Entry neuerShop = new Entry(String.valueOf(index));
				neuerShop.addValue("besitzer", stadt.getName());
				neuerShop.addValue("special", "fabrik3");
				neuerShop.addValue("id", "29#7");
				neuerShop.addValue("ep", "0");
				neuerShop.addValue("vp", vp);
				neuerShop.addValue("x", String.valueOf(SL.getBlockX()));
				neuerShop.addValue("y", String.valueOf(SL.getBlockY()));
				neuerShop.addValue("z", String.valueOf(SL.getBlockZ()));
				neuerShop.addValue("world", SL.getWorld().getName());
				Finanzen.Shops.addEntry(neuerShop);
				Finanzen.Shops.push();
				
				
				String key = SL.getWorld().getName() + ":" + SL.getBlockX() + ":" + SL.getBlockY() + ":" + SL.getBlockZ(); 
				Finanzen.ShopHM.put(key, index);
				
				Location helper = new Location (SL.getWorld(), SL.getBlockX(), SL.getBlockY(), SL.getBlockZ());
				byte data = SL.getBlock().getData();
				if (data == 2) {
					helper.setZ(helper.getZ()+1);
				}
				if (data == 3) {
					helper.setZ(helper.getZ()-1);
				}
				if (data == 4) {
					helper.setX(helper.getX()+1);
				}
				if (data == 5) {
					helper.setX(helper.getX()-1);
				}
				Finanzen.SchildHalterBlocks.add(helper);
				
				uS.getBlock().setTypeId(54);
				uS.getBlock().setData(event.getBlock().getData());
				player.sendMessage(ChatColor.GREEN + "Die Fabrik verkauft jetzt Klebekolben für "+Finanzen.f.format(vp)+Finanzen.kuerzel+".");
				Finanzen.SchildBeschreiben(index);
				event.setLine(0, "--Fabrik--");
				event.setLine(1, "verkauft");
				event.setLine(2, "Klebekolben");
				event.setLine(3, "für "+Finanzen.f.format(vp)+Finanzen.kuerzel);
				Finanzen.fabrik3.remove(name);
				
			} catch (EmptyIndexException e) {
				e.printStackTrace();
				return;
			}
		}
		if (Finanzen.tierheim.contains(name)) {
			if (!(uS.getBlock().getType() == Material.AIR)) {
				player.sendMessage(ChatColor.RED + "Unter dem Schild muss Platz für eine Kiste sein!");
				event.getBlock().breakNaturally();
				event.setCancelled(true);
				return;
			}
			if (Finanzen.isNaheKiste(uS)) {
				player.sendMessage(ChatColor.RED + "Es darf sich keine Kiste neben einer Shopkiste befinden!");
				event.getBlock().breakNaturally();
				event.setCancelled(true);
				return;
			}
			name="Tierheim";
			try {
				int index = 0;
				while (Finanzen.Shops.hasIndex(String.valueOf(index))) {
					index++;
				}
				Stadt stadt = Finanzen.getStadtAtLoc(uS);
				double ep = Finanzen.config.getDouble("352.0.EK");
				double rabatt = Finanzen.config.getDouble("Gebaeude.tierheim.shoprabatt");
				ep = ep*(1+rabatt/100);
				Location SL = event.getBlock().getLocation();
				Entry neuerShop = new Entry(String.valueOf(index));
				neuerShop.addValue("besitzer", stadt.getName());
				neuerShop.addValue("special", "tierheim");
				neuerShop.addValue("id", "352#0");
				neuerShop.addValue("ep", ep);
				neuerShop.addValue("vp", 0);
				neuerShop.addValue("x", String.valueOf(SL.getBlockX()));
				neuerShop.addValue("y", String.valueOf(SL.getBlockY()));
				neuerShop.addValue("z", String.valueOf(SL.getBlockZ()));
				neuerShop.addValue("world", SL.getWorld().getName());
				Finanzen.Shops.addEntry(neuerShop);
				Finanzen.Shops.push();
				
				
				String key = SL.getWorld().getName() + ":" + SL.getBlockX() + ":" + SL.getBlockY() + ":" + SL.getBlockZ(); 
				Finanzen.ShopHM.put(key, index);
				
				Location helper = new Location (SL.getWorld(), SL.getBlockX(), SL.getBlockY(), SL.getBlockZ());
				byte data = SL.getBlock().getData();
				if (data == 2) {
					helper.setZ(helper.getZ()+1);
				}
				if (data == 3) {
					helper.setZ(helper.getZ()-1);
				}
				if (data == 4) {
					helper.setX(helper.getX()+1);
				}
				if (data == 5) {
					helper.setX(helper.getX()-1);
				}
				Finanzen.SchildHalterBlocks.add(helper);
				
				uS.getBlock().setTypeId(54);
				uS.getBlock().setData(event.getBlock().getData());
				player.sendMessage(ChatColor.GREEN + "Das Tierheim kauft jetzt Knochen für "+Finanzen.f.format(ep)+Finanzen.kuerzel+".");
				Finanzen.SchildBeschreiben(index);
				event.setLine(0, "--Tierheim--");
				event.setLine(1, "kauft");
				event.setLine(2, "Knochen");
				event.setLine(3, "für "+Finanzen.f.format(ep)+Finanzen.kuerzel);
				Finanzen.tierheim.remove(name);
				
			} catch (EmptyIndexException e) {
				e.printStackTrace();
				return;
			}
		}
		
		if (Finanzen.adminshopbau.contains(name)) {
			if (!(uS.getBlock().getType() == Material.AIR)) {
				player.sendMessage(ChatColor.RED + "Unter dem Schild muss Platz für die Kiste sein!");
				event.getBlock().breakNaturally();
				event.setCancelled(true);
				return;
			}
			if (Finanzen.isNaheKiste(uS)) {
				player.sendMessage(ChatColor.RED + "Es darf sich keine Kiste neben einer Shopkiste befinden!");
				event.getBlock().breakNaturally();
				event.setCancelled(true);
				return;
			}
			name="AdminShop";
			try {
				setNewShop(name, event.getBlock().getLocation());
			} catch (EmptyIndexException e) {
				e.printStackTrace();
				return;
			}
			event.setLine(0, name);
			event.setLine(1, "Noch");
			event.setLine(2, "nicht");
			event.setLine(3, "eingerichtet!");
			uS.getBlock().setTypeId(54);
			uS.getBlock().setData(event.getBlock().getData());
			player.sendMessage(ChatColor.GREEN + "AdminShop erfolgreich erstellt!");
			player.sendMessage(ChatColor.GREEN + "Mit '/shop ware <id>' stellt man ein, mit welcher Ware der Shop handelt.");
			player.sendMessage(ChatColor.GREEN + "Mit '/shop ep <preis>' stellst du ein, für wieviel der Shop für dich einkauft.");
			player.sendMessage(ChatColor.GREEN + "Mit '/shop vp <preis>', für wieviel der Shop für dich verkauft.");
			player.sendMessage(ChatColor.GREEN + "Wenn du '0' als Preis angibst, wird das Item nicht ge-/verkauft.");
			player.sendMessage(ChatColor.GREEN + "ServerShops funktionieren unabhängig vom Kisteninhalt!");
			Finanzen.adminshopbau.remove(name);
			
			
			
		} else
		if (Finanzen.shopbau.contains(name)) {
			if (!(uS.getBlock().getType() == Material.AIR)) {
				player.sendMessage(ChatColor.RED + "Unter dem Schild muss Platz für die Kiste sein!");
				event.getBlock().breakNaturally();
				event.setCancelled(true);
				return;
			}
			if (Finanzen.isNaheKiste(uS)) {
				player.sendMessage(ChatColor.RED + "Es darf sich keine Kiste neben einer Shopkiste befinden!");
				event.getBlock().breakNaturally();
				event.setCancelled(true);
				return;
			}
			
			try {
				setNewShop(name, event.getBlock().getLocation());
			} catch (EmptyIndexException e) {
				e.printStackTrace();
				return;
			}
			event.setLine(0, name+Finanzen.getHelper(name));
			event.setLine(1, "Kistenshop");
			event.setLine(2, "Noch nicht");
			event.setLine(3, "eingerichtet!");
			uS.getBlock().setTypeId(54);
			uS.getBlock().setData(event.getBlock().getData());
			player.sendMessage(ChatColor.GREEN + "Shop erfolgreich erstellt!");
			player.sendMessage(ChatColor.GREEN + "Mit '/shop ware <id>' stellt man ein, mit welcher Ware der Shop handelt.");
			player.sendMessage(ChatColor.GREEN + "Mit '/shop ep <preis>' stellst du ein, für wieviel der Shop für dich einkauft.");
			player.sendMessage(ChatColor.GREEN + "Mit '/shop vp <preis>', für wieviel der Shop für dich verkauft.");
			player.sendMessage(ChatColor.GREEN + "Wenn du '0' als Preis angibst, wird das Item nicht ge-/verkauft.");
			player.sendMessage(ChatColor.GREEN + "Der Shop verkauft nur, was in der Kiste ist und kauft nur, wenn der Besitzer genug Geld hat!");
			Finanzen.shopbau.remove(name);
		} 
		
	}
	
	private int setNewShop(String name, Location SL) throws EmptyIndexException {
		int index = 0;
		while (Finanzen.Shops.hasIndex(String.valueOf(index))) {
			index++;
		}
		Entry neuerShop = new Entry(String.valueOf(index));
		neuerShop.addValue("besitzer", name);
		neuerShop.addValue("id", "tba");
		neuerShop.addValue("ep", "0");
		neuerShop.addValue("vp", "0");
		neuerShop.addValue("x", String.valueOf(SL.getBlockX()));
		neuerShop.addValue("y", String.valueOf(SL.getBlockY()));
		neuerShop.addValue("z", String.valueOf(SL.getBlockZ()));
		neuerShop.addValue("world", SL.getWorld().getName());
		Finanzen.Shops.addEntry(neuerShop);
		Finanzen.Shops.push();
		
		
		String key = SL.getWorld().getName() + ":" + SL.getBlockX() + ":" + SL.getBlockY() + ":" + SL.getBlockZ(); 
		Finanzen.ShopHM.put(key, index);
		
		Finanzen.ShopFokus.put(name, index);
		Location helper = new Location (SL.getWorld(), SL.getBlockX(), SL.getBlockY(), SL.getBlockZ());
		byte data = SL.getBlock().getData();
		if (data == 2) {
			helper.setZ(helper.getZ()+1);
		}
		if (data == 3) {
			helper.setZ(helper.getZ()-1);
		}
		if (data == 4) {
			helper.setX(helper.getX()+1);
		}
		if (data == 5) {
			helper.setX(helper.getX()-1);
		}
		Finanzen.SchildHalterBlocks.add(helper);
		if (Finanzen.shopumstellen.isEmpty() && !Finanzen.isAdminShop(index)) {
			plugin.addSK(name.toLowerCase());
			Finanzen.incKonto(name.toLowerCase(), (-1)*Finanzen.shopkosten);
		}else
		if (!Finanzen.shopumstellen.contains(name)) {
			if (!Finanzen.isAdminShop(index)) {
				plugin.addSK(name.toLowerCase());
				Finanzen.incKonto(name.toLowerCase(), (-1)*Finanzen.shopkosten);
			}
		}else {
			Finanzen.shopumstellen.remove(name);
		}
		return index;
	}
	
}
