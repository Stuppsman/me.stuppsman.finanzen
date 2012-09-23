package me.stuppsman.finanzen;



import java.io.File;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashMap;

import me.stuppsman.finanzen.commandExecutor.FinanzenCE_GS;
import me.stuppsman.finanzen.commandExecutor.FinanzenCE_baurecht;
import me.stuppsman.finanzen.commandExecutor.FinanzenCE_ca;
import me.stuppsman.finanzen.commandExecutor.FinanzenCE_chilli;
import me.stuppsman.finanzen.commandExecutor.FinanzenCE_chillis;
import me.stuppsman.finanzen.commandExecutor.FinanzenCE_delgs;
import me.stuppsman.finanzen.commandExecutor.FinanzenCE_delstadt;
import me.stuppsman.finanzen.commandExecutor.FinanzenCE_fertig;
import me.stuppsman.finanzen.commandExecutor.FinanzenCE_gib;
import me.stuppsman.finanzen.commandExecutor.FinanzenCE_grenzen;
import me.stuppsman.finanzen.commandExecutor.FinanzenCE_id;
import me.stuppsman.finanzen.commandExecutor.FinanzenCE_idsuche;
import me.stuppsman.finanzen.commandExecutor.FinanzenCE_kaufe;
import me.stuppsman.finanzen.commandExecutor.FinanzenCE_konto;
import me.stuppsman.finanzen.commandExecutor.FinanzenCE_neuestadt;
import me.stuppsman.finanzen.commandExecutor.FinanzenCE_rsrecht;
import me.stuppsman.finanzen.commandExecutor.FinanzenCE_schluessel;
import me.stuppsman.finanzen.commandExecutor.FinanzenCE_shop;
import me.stuppsman.finanzen.commandExecutor.FinanzenCE_spende;
import me.stuppsman.finanzen.commandExecutor.FinanzenCE_stadt;
import me.stuppsman.finanzen.commandExecutor.FinanzenCE_verkaufe;
import me.stuppsman.finanzen.commandExecutor.FinanzenCE_wert;
import me.stuppsman.finanzen.listener.FinanzenL_Creeperschaden;
import me.stuppsman.finanzen.listener.FinanzenL_Firespread;
import me.stuppsman.finanzen.listener.FinanzenL_GS_Anwahl;
import me.stuppsman.finanzen.listener.FinanzenL_GS_BauAbbau;
import me.stuppsman.finanzen.listener.FinanzenL_GS_Kisten;
import me.stuppsman.finanzen.listener.FinanzenL_GS_Markierung;
import me.stuppsman.finanzen.listener.FinanzenL_GS_NEU;
import me.stuppsman.finanzen.listener.FinanzenL_GS_RedStone;
import me.stuppsman.finanzen.listener.FinanzenL_Lagerkisten;
import me.stuppsman.finanzen.listener.FinanzenL_Login;
import me.stuppsman.finanzen.listener.FinanzenL_MobSpawn;
import me.stuppsman.finanzen.listener.FinanzenL_Move;
import me.stuppsman.finanzen.listener.FinanzenL_PVP;
import me.stuppsman.finanzen.listener.FinanzenL_Shopabriss;
import me.stuppsman.finanzen.listener.FinanzenL_Shopanwahl;
import me.stuppsman.finanzen.listener.FinanzenL_Shopbau;

import org.bukkit.Location;
import org.bukkit.block.BlockState;
import org.bukkit.block.Chest;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.inventory.Inventory;
import org.bukkit.block.Sign;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;
import org.dynmap.DynmapAPI;
import org.dynmap.markers.AreaMarker;
import org.dynmap.markers.MarkerAPI;
import org.dynmap.markers.MarkerIcon;
import org.dynmap.markers.MarkerSet;
import com.xemsdoom.mexdb.MexDB;
import com.xemsdoom.mexdb.exception.EmptyIndexException;
import com.xemsdoom.mexdb.exception.EmptyListException;
import com.xemsdoom.mexdb.system.Entry;



public class Finanzen extends JavaPlugin {
	public static void main (String args[]) {}
	public static Finanzen plugin;
	public static DecimalFormat f = new DecimalFormat("###,###,###,##0.00"); 
	
	

	public Finanzen() {
		plugin = this;
	}
	public static FileConfiguration config;
	
	public static int shopanzahl, gsanzahl, stadtanzahl, freiegs = 0;
	public static int abstand;
	public static int marker, tiefe;
	public static double GSKosten, Stadtkosten;
	public static String markername;
	public static String waehrung, kuerzel, GSGrenzen;
	public static double shopkosten, shoperstattung, shopumstellkosten;
	public static ArrayList<String> shopbau = new ArrayList<String>();
	public static ArrayList<String> adminshopbau = new ArrayList<String>();
	public static ArrayList<String> shopumstellen = new ArrayList<String>();
	public static ArrayList<String> lager = new ArrayList<String>();//spieler, die lagerkisten anwählen
	public static ArrayList<String> muehle = new ArrayList<String>();//spieler, die den muehlenshop setzen
	public static ArrayList<String> fischer = new ArrayList<String>();
	public static ArrayList<String> baecker = new ArrayList<String>();
	public static ArrayList<String> mine = new ArrayList<String>();
	public static ArrayList<String> fabrik1 = new ArrayList<String>();
	public static ArrayList<String> fabrik2 = new ArrayList<String>();
	public static ArrayList<String> fabrik3 = new ArrayList<String>();
	public static ArrayList<String> tierheim = new ArrayList<String>();
	
	public static ArrayList<String> taverne = new ArrayList<String>();
	public static ArrayList<String> markt = new ArrayList<String>();
	public static ArrayList<String> kapelle = new ArrayList<String>();
	public static ArrayList<String> bank = new ArrayList<String>();
	public static ArrayList<String> bibliothek = new ArrayList<String>();
	public static ArrayList<String> knast = new ArrayList<String>();
	public static ArrayList<String> shop = new ArrayList<String>();
	public static ArrayList<String> schiff1 = new ArrayList<String>();
	public static ArrayList<String> schiff2 = new ArrayList<String>();
	public static ArrayList<String> schiff3 = new ArrayList<String>();
	public static ArrayList<String> hafen = new ArrayList<String>();
	public static ArrayList<String> kirche = new ArrayList<String>();
	public static ArrayList<String> feuerwehr = new ArrayList<String>();
	public static ArrayList<String> krankenhaus = new ArrayList<String>();
	public static ArrayList<String> palast = new ArrayList<String>(); //info-schilder zur stadtstatistik
	public static ArrayList<String> turnierplatz = new ArrayList<String>();//auch neue spawnpunkte
	public static ArrayList<String> kloster = new ArrayList<String>();
	public static ArrayList<String> museum = new ArrayList<String>();
	public static ArrayList<String> schlachthaus = new ArrayList<String>();
	public static ArrayList<String> tankstelle = new ArrayList<String>();
	public static ArrayList<String> supermarkt = new ArrayList<String>();
	public static ArrayList<String> mall = new ArrayList<String>();
	public static ArrayList<String> schuldturm = new ArrayList<String>();
	public static ArrayList<String> pranger = new ArrayList<String>();
	public static ArrayList<String> garnison = new ArrayList<String>();
	public static ArrayList<String> alchemist = new ArrayList<String>();
	public static ArrayList<String> werkzeugschmied = new ArrayList<String>();
	public static ArrayList<String> waffenschmied = new ArrayList<String>();
	public static ArrayList<String> ruestungsschmied = new ArrayList<String>();
	
	public static HashMap<Location, String> befSchild = new HashMap<Location, String>(); //ort, befehl, der ausgeführt wird
	
	/*lager, fischer, muehle, taverne, markt,  
	kapelle, mine, rathaus, bank, bahnhof, //10
	shop, schiff1, schiff2, hafen, kirche, 
	gericht, mauer, polizei, burg, bibliothek, //20
	hotel, knast, feuerwehr, krankenhaus, palast, 
	denkmal, turnierplatz, magierturm, kloster, museum, //30 
	casino, schlachthaus, tankstelle, supermarkt, mall, 
	tierheim, schuldturm, pranger, baecker, garnison, //40
	alchemist, werkzeugschmied, waffenschmied, ruestungsschmied, fabrik1, 
	fabrik2, fabrik3, weltwunder1, weltwunder2, weltwunder3, //50 
	weltwunder4, weltwunder5, weltwunder6, weltwunder7, schiff3
	leuchtturm, reiseburo*/
	public static ArrayList<String> reichste = new ArrayList<String>();
	public static HashMap<String, GS> grundstuecke = new HashMap<String, GS>(); //GS-ID, GS
	public static HashMap<String, Stadt> staedte = new HashMap<String, Stadt>(); //Stadtname, Stadt
	
	
	public static HashMap<Location, String> GSSchilder = new HashMap<Location, String>();//schildloc, GS-ID
	public static HashMap<String, String> GSFokus = new HashMap<String, String>();//spielername, GS-ID
	public FileManager fm = new FileManager();
	public DynmapAPI api;
	public static MarkerAPI markerapi;
	public static MarkerSet sets, setgs, setgebaeude;
	
	
	public static MexDB Finanzendb = new MexDB("plugins/Finanzen","Finanzen"); 
	public static MexDB Shops = new MexDB("plugins/Finanzen", "Shops");
	public static MexDB GSDB = new MexDB("plugins/Finanzen", "Grundstuecke");
	public static MexDB StaedteDB = new MexDB("plugins/Finanzen", "Staedte");
	public static HashMap<String, Integer> ShopHM = new HashMap<String, Integer>(); // welt:xx:yy:zz, ShopID
	public static HashMap<String, Integer> ShopFokus = new HashMap<String, Integer>(); //spielername, ShopID
	public static HashMap<String, Location> locA = new HashMap<String, Location>();
	public static HashMap<String, Location> locB = new HashMap<String, Location>();
	
	public static ArrayList<Location> SchildHalterBlocks = new ArrayList<Location>();
	public static ArrayList<String> chillis = new ArrayList<String>();
	
	public void onEnable() {
		
		//tor einbauen in gs-umrandung, wenn player im Weg steht
		//biete und
		//versteiger-gs-befehl
		//kredit-befehle
		//shop-anwahl schöner machen - shop-inventar-anzahl herausfinden
		plugin.getCommand("stadt").setExecutor(new FinanzenCE_stadt());
		plugin.getCommand("neuestadt").setExecutor(new FinanzenCE_neuestadt(plugin));
		plugin.getCommand("delstadt").setExecutor(new FinanzenCE_delstadt());
		plugin.getCommand("konto").setExecutor(new FinanzenCE_konto(plugin));
		//plugin.getCommand("kredit").setExecutor(new FinanzenCE_kredit(plugin));
		plugin.getCommand("wert").setExecutor(new FinanzenCE_wert(plugin));
		plugin.getCommand("gib").setExecutor(new FinanzenCE_gib(plugin));
		plugin.getCommand("kaufe").setExecutor(new FinanzenCE_kaufe(plugin));
		plugin.getCommand("verkaufe").setExecutor(new FinanzenCE_verkaufe(plugin));
		plugin.getCommand("id").setExecutor(new FinanzenCE_id(plugin));
		plugin.getCommand("shop").setExecutor(new FinanzenCE_shop(plugin));
		plugin.getCommand("idsuche").setExecutor(new FinanzenCE_idsuche());
		plugin.getCommand("chilli").setExecutor(new FinanzenCE_chilli());
		plugin.getCommand("chillis").setExecutor(new FinanzenCE_chillis());
		plugin.getCommand("ca").setExecutor(new FinanzenCE_ca());
		plugin.getCommand("gs").setExecutor(new FinanzenCE_GS());
		plugin.getCommand("delgs").setExecutor(new FinanzenCE_delgs());
		plugin.getCommand("baurecht").setExecutor(new FinanzenCE_baurecht());
		plugin.getCommand("rsrecht").setExecutor(new FinanzenCE_rsrecht());
		plugin.getCommand("schluessel").setExecutor(new FinanzenCE_schluessel());
		plugin.getCommand("fertig").setExecutor(new FinanzenCE_fertig());
		plugin.getCommand("grenzen").setExecutor(new FinanzenCE_grenzen());
		plugin.getCommand("spende").setExecutor(new FinanzenCE_spende());
		getServer().getPluginManager().registerEvents(new FinanzenL_Login(plugin), plugin);
		getServer().getPluginManager().registerEvents(new FinanzenL_Shopbau(plugin), plugin);
		getServer().getPluginManager().registerEvents(new FinanzenL_Shopabriss(), plugin);
		getServer().getPluginManager().registerEvents(new FinanzenL_Shopanwahl(), plugin);
		getServer().getPluginManager().registerEvents(new FinanzenL_GS_NEU(plugin), plugin);
		getServer().getPluginManager().registerEvents(new FinanzenL_GS_Markierung(), plugin);
		getServer().getPluginManager().registerEvents(new FinanzenL_GS_BauAbbau(), plugin);
		getServer().getPluginManager().registerEvents(new FinanzenL_GS_Kisten(), plugin);
		getServer().getPluginManager().registerEvents(new FinanzenL_GS_RedStone(), plugin);
		getServer().getPluginManager().registerEvents(new FinanzenL_GS_Anwahl(), plugin);
		getServer().getPluginManager().registerEvents(new FinanzenL_Creeperschaden(plugin), plugin);
		getServer().getPluginManager().registerEvents(new FinanzenL_MobSpawn(plugin), plugin);
		getServer().getPluginManager().registerEvents(new FinanzenL_Firespread(), plugin);
		getServer().getPluginManager().registerEvents(new FinanzenL_Move(), plugin);
		getServer().getPluginManager().registerEvents(new FinanzenL_Lagerkisten(), plugin);
		getServer().getPluginManager().registerEvents(new FinanzenL_PVP(), plugin);
		System.out.println("["+this.getDescription().getName() + "] v" + this.getDescription().getVersion() + " erfolgreich geladen!");
		loadConfig();
		loadShops();
		loadShopHalter();
		loadGS();
		loadStaedte();
		Plugin dynmap = getServer().getPluginManager().getPlugin("dynmap");
		if((dynmap == null) || (!dynmap.isEnabled())) {
			System.out.println("["+this.getDescription().getName() + "] v" + this.getDescription().getVersion() + " DynMap nicht gefunden!");
			return;
		}

		api = (DynmapAPI) dynmap;
		markerapi = api.getMarkerAPI();
		loadGebiete();
		
	}


	


	





	public void onDisable() {
		System.out.println("["+this.getDescription().getName() + "] v" + this.getDescription().getVersion() + " erfolgreich deaktiviert!");
		Finanzendb.push();
		Shops.push();
		if (!(grundstuecke.isEmpty()))
			saveGS();
		if (!staedte.isEmpty())
			saveStaedte();
	
		
	}

	public static void incKonto(String name, double amount) {
		if (staedte.containsKey(name)) {
			getStadt(name).incStadtkasse(amount);
			return;
		}
		Finanzen.Finanzendb.setValue(name, "konto", new Double(Finanzen.Finanzendb.getDouble(name, "konto")+amount).toString());
		
	}
	
	public static String reichster(ArrayList<String> r) {
		double max = 0d;
		String reichster = "noch niemand";
		for (String index : Finanzendb.getIndices()) {
			if (r.contains(index)) {
				continue;
			}
			double konto = Finanzendb.getDouble(index, "konto"); 
			if (konto > max) {
				max = konto;
				reichster = index;
			}
		}
		reichste.add(reichster);
		return reichster;
	}
	
	public void addV(String name, String gegenstand, int anzahl, double preis) {
		int max = plugin.getConfig().getInt("maxTransaktionen");
		String gesamtpreis = Finanzen.f.format(preis);
		if (max == 0) {
			int i = 1;
			while (Finanzendb.hasKey(name, ("trans"+i).toString())){
				i++;
			}
			Finanzendb.setValue(name, ("trans"+(i)), ("&3| &cVerkauf von &2"+anzahl + " " + gegenstand + "&c: &a+" + gesamtpreis + kuerzel));
		}
		if (Finanzendb.hasKey(name, ("trans" + max))) {
			transDurchrutschen(name);
			Finanzendb.setValue(name, ("trans"+max), ("&3| &cVerkauf von &2"+anzahl + " " + gegenstand + "&c: &a+" + gesamtpreis + kuerzel));
		}else {
			int i = 1;
			while (Finanzendb.hasKey(name, ("trans"+i).toString())){
				i++;
			}
			Finanzendb.setValue(name, ("trans"+(i)), ("&3| &cVerkauf von &2"+anzahl + " " + gegenstand + "&c: &a+" + gesamtpreis + kuerzel));
		}
		fm.addToLog("[Verkauf]",name + " verkauft " + anzahl + "x "+ gegenstand +" an den Server. (+" + gesamtpreis + kuerzel + ")");
		
	}
	public void addK(String name, String gegenstand, int anzahl, double geskosten) {
		int max = plugin.getConfig().getInt("maxTransaktionen");
		String kosten = Finanzen.f.format(geskosten);
		if (max == 0) {
			int i = 1;
			while (Finanzendb.hasKey(name, ("trans"+i).toString())){
				i++;
			}
			Finanzendb.setValue(name, ("trans"+(i)), ("&3| &aKauf von &2"+anzahl + " " + gegenstand + "&a: &c-" + kosten + kuerzel));
		}
		if (Finanzendb.hasKey(name, ("trans" + max))) {
			transDurchrutschen(name);
			Finanzendb.setValue(name, ("trans"+max), ("&3| &aKauf von &2"+anzahl + " " + gegenstand + "&a: &c-" + kosten + kuerzel));
		}else {
			int i = 1;
			while (Finanzendb.hasKey(name, ("trans"+i).toString())){
				i++;
			}
			Finanzendb.setValue(name, ("trans"+(i)), ("&3| &aKauf von &2"+anzahl + " " + gegenstand + "&a: &c-" + kosten+ kuerzel));
		}
		fm.addToLog("[Kauf]",name + " kauft " + anzahl + "x "+ gegenstand +" vom Server.  (-" + kosten + kuerzel + ")");
	}
	public void addU(String empf, String geber, double amount1) {
		int max = plugin.getConfig().getInt("maxTransaktionen");
		String amount = Finanzen.f.format(amount1);
		fm.addToLog("[Ueberweisung]", geber + " ueberweist " + amount1 + kuerzel +" an " + empf);
		if (max == 0) {
			int i = 1;
			while (Finanzendb.hasKey(empf, ("trans"+i).toString())){
				i++;
			}
			Finanzendb.setValue(empf, ("trans"+(i)), ("&3| &2Überweisung von &e" + getServer().getOfflinePlayer(geber).getName() + "&2: +" + amount + kuerzel));
			i = 1;
			while (Finanzendb.hasKey(geber, ("trans"+i).toString())){
				i++;
			}
			Finanzendb.setValue(geber, ("trans"+(i)), ("&3| &4Überweisung an &e" + getServer().getOfflinePlayer(empf).getName() + "&4: -" + amount + kuerzel));
		}
		if (Finanzendb.hasKey(empf, ("trans" + max))) {
			transDurchrutschen(empf);
			Finanzendb.setValue(empf, ("trans" + max), ("&3| &2Überweisung von &e" + getServer().getOfflinePlayer(geber).getName() + "&2: +" + amount + kuerzel));
		}else {
			int i = 1;
			while (Finanzendb.hasKey(empf, ("trans"+i).toString())){
				i++;
			}
			Finanzendb.setValue(empf, ("trans"+(i)), ("&3| &2Überweisung von &e" + getServer().getOfflinePlayer(geber).getName() + "&2: +" + amount + kuerzel));
		}
		if (Finanzendb.hasKey(geber, ("trans" + max))) {
			transDurchrutschen(geber);
			Finanzendb.setValue(geber, ("trans" + max), ("&3| &4Überweisung an &e" + getServer().getOfflinePlayer(empf).getName() + "&4: -" + amount + kuerzel));
		}else {
			int i = 1;
			while (Finanzendb.hasKey(geber, ("trans"+i).toString())){
				i++;
			}
			Finanzendb.setValue(geber, ("trans"+(i)), ("&3| &4Überweisung an &e" + getServer().getOfflinePlayer(empf).getName() + "&4: -" + amount + kuerzel));
		}
	}
	public void addSK(String name) {
		int max = plugin.getConfig().getInt("maxTransaktionen");
		String shopkosten = Finanzen.f.format(Finanzen.shopkosten);
		fm.addToLog(name, "Kauf von Kistenshop (-" + shopkosten + kuerzel + ")");
		if (max == 0) {
			int i = 1;
			while (Finanzendb.hasKey(name, ("trans"+i).toString())){
				i++;
			}
			Finanzendb.setValue(name, ("trans"+(i)), ("&3| &aKauf von &2Kistenshop&a: &c-" + shopkosten + kuerzel));
		}
		if (Finanzendb.hasKey(name, ("trans" + max))) {
			transDurchrutschen(name);
			Finanzendb.setValue(name, ("trans"+max), ("&3| &aKauf von &2Kistenshop&a: &c-" + shopkosten + kuerzel));
		}else {
			int i = 1;
			while (Finanzendb.hasKey(name, ("trans"+i).toString())){
				i++;
			}
			Finanzendb.setValue(name, ("trans"+(i)), ("&3| &aKauf von &2Kistenshop&a: &c-" + shopkosten+ kuerzel));
		}
	}
	public void addSVK(String name) {
		int max = plugin.getConfig().getInt("maxTransaktionen");
		String erstattung = Finanzen.f.format(shoperstattung);
		fm.addToLog(name, "Verkauf von Kistenshop (+" + erstattung + kuerzel + ")");
		if (max == 0) {
			int i = 1;
			while (Finanzendb.hasKey(name, ("trans"+i).toString())){
				i++;
			}
			Finanzendb.setValue(name, ("trans"+(i)), ("&3| &cVerkauf von &2Kistenshop&a: &a+" + erstattung + kuerzel));
		}
		if (Finanzendb.hasKey(name, ("trans" + max))) {
			transDurchrutschen(name);
			Finanzendb.setValue(name, ("trans"+max), ("&3| &cVerkauf von &2Kistenshop&a: &a+" + erstattung + kuerzel));
		}else {
			int i = 1;
			while (Finanzendb.hasKey(name, ("trans"+i).toString())){
				i++;
			}
			Finanzendb.setValue(name, ("trans"+(i)), ("&3| &cVerkauf von &2Kistenshop&a: &a+" + erstattung+ kuerzel));
		}
	}
	public void addKiS(String kaeufer, String gegenstand, String shopbesitzer, int anzahl, double kosten1) {
		int max = plugin.getConfig().getInt("maxTransaktionen");
		String kosten = Finanzen.f.format(kosten1);
		fm.addToLog("[Kauf]", kaeufer + " kauft " + anzahl + "x "+ gegenstand +" in " + shopbesitzer+getHelper(shopbesitzer) + " Shop (-" + kosten + kuerzel + ")");
		if (max == 0) {
			int i = 1;
			while (Finanzendb.hasKey(kaeufer, ("trans"+i).toString())){
				i++;
			}
			Finanzendb.setValue(kaeufer, ("trans"+(i)), ("&3| &aKauf von &2"+anzahl + " " + gegenstand + "&a in &e" + shopbesitzer+getHelper(shopbesitzer)+ " &aShop: &c-" + kosten + kuerzel));
		}
		if (Finanzendb.hasKey(kaeufer, ("trans" + max))) {
			transDurchrutschen(kaeufer);
			Finanzendb.setValue(kaeufer, ("trans"+max), ("&3| &aKauf von &2"+anzahl + " " + gegenstand + "&a in &e" + shopbesitzer+getHelper(shopbesitzer)+ " &aShop: &c-" + kosten + kuerzel));
		}else {
			int i = 1;
			while (Finanzendb.hasKey(kaeufer, ("trans"+i).toString())){
				i++;
			}
			Finanzendb.setValue(kaeufer, ("trans"+(i)), ("&3| &aKauf von &2"+anzahl + " " + gegenstand + "&a in &e" + shopbesitzer+getHelper(shopbesitzer)+ " &aShop: &c-" + kosten+ kuerzel));
		}
		//ShopBesitzer
		if (max == 0) {
			int i = 1;
			while (Finanzendb.hasKey(shopbesitzer, ("trans"+i).toString())){
				i++;
			}
			Finanzendb.setValue(shopbesitzer, ("trans"+(i)), ("&3| &e"+kaeufer+"&c kauft &2"+anzahl + " " + gegenstand + "&c in deinem Shop: &a+" + kosten + kuerzel));
		}
		if (Finanzendb.hasKey(shopbesitzer, ("trans" + max))) {
			transDurchrutschen(shopbesitzer);
			Finanzendb.setValue(shopbesitzer, ("trans"+max), ("&3| &e"+kaeufer+"&c kauft &2"+anzahl + " " + gegenstand + "&c in deinem Shop: &a+" + kosten + kuerzel));
		}else {
			int i = 1;
			while (Finanzendb.hasKey(shopbesitzer, ("trans"+i).toString())){
				i++;
			}
			Finanzendb.setValue(shopbesitzer, ("trans"+(i)), ("&3| &e"+kaeufer+"&c kauft &2"+anzahl + " " + gegenstand + "&c in deinem Shop: &a+" + kosten + kuerzel));
		}
	}
	public void addViS(String verkaeufer, String gegenstand, String shopbesitzer, int anzahl, double gespreis) {
		int max = plugin.getConfig().getInt("maxTransaktionen");
		String gesamtpreis = Finanzen.f.format(gespreis);
		fm.addToLog("[Verkauf]", verkaeufer + " verkauft " + anzahl + "x "+ gegenstand +" an " + shopbesitzer+getHelper(shopbesitzer) + " Shop (+" + gesamtpreis + kuerzel + ")");
		if (max == 0) {
			int i = 1;
			while (Finanzendb.hasKey(verkaeufer, ("trans"+i).toString())){
				i++;
			}
			Finanzendb.setValue(verkaeufer, ("trans"+(i)), ("&3| &cVerkauf von &2"+anzahl + " " + gegenstand + "&c an &e" + shopbesitzer+getHelper(shopbesitzer)+ " &cShop: &2+" + gesamtpreis + kuerzel));
		}
		if (Finanzendb.hasKey(verkaeufer, ("trans" + max))) {
			transDurchrutschen(verkaeufer);
			Finanzendb.setValue(verkaeufer, ("trans"+max), ("&3| &cVerkauf von &2"+anzahl + " " + gegenstand + "&c an &e" + shopbesitzer+getHelper(shopbesitzer)+ " &cShop: &2+" + gesamtpreis + kuerzel));
		}else {
			int i = 1;
			while (Finanzendb.hasKey(verkaeufer, ("trans"+i).toString())){
				i++;
			}
			Finanzendb.setValue(verkaeufer, ("trans"+(i)), ("&3| &cVerkauf von &2"+anzahl + " " + gegenstand + "&c an &e" + shopbesitzer+getHelper(shopbesitzer)+ " &cShop: &2+" + gesamtpreis+ kuerzel));
		}
		//ShopBesitzer
		if (max == 0) {
			int i = 1;
			while (Finanzendb.hasKey(shopbesitzer, ("trans"+i).toString())){
				i++;
			}
			Finanzendb.setValue(shopbesitzer, ("trans"+(i)), ("&3| &e"+verkaeufer+"&a verkauft &2"+anzahl + " " + gegenstand + "&a an deinen Shop: &c-" + gesamtpreis + kuerzel));
		}
		if (Finanzendb.hasKey(shopbesitzer, ("trans" + max))) {
			transDurchrutschen(shopbesitzer);
			Finanzendb.setValue(shopbesitzer, ("trans"+max), ("&3| &e"+verkaeufer+"&a verkauft &2"+anzahl + " " + gegenstand + "&a an deinen Shop: &c-" + gesamtpreis + kuerzel));
		}else {
			int i = 1;
			while (Finanzendb.hasKey(shopbesitzer, ("trans"+i).toString())){
				i++;
			}
			Finanzendb.setValue(shopbesitzer, ("trans"+(i)), ("&3| &e"+verkaeufer+"&a verkauft &2"+anzahl + " " + gegenstand + "&a an deinen Shop: &c-" + gesamtpreis + kuerzel));
		}
	}
	public void addKiAS(String kaeufer, String gegenstand, int anzahl, double kosten1) {
		int max = plugin.getConfig().getInt("maxTransaktionen");
		String kosten = Finanzen.f.format(kosten1);
		fm.addToLog("[Kauf] ", kaeufer + " kauft " + anzahl + "x "+ gegenstand +" in einem AdminShop (-" + kosten + kuerzel + ")");
		if (max == 0) {
			int i = 1;
			while (Finanzendb.hasKey(kaeufer, ("trans"+i).toString())){
				i++;
			}
			Finanzendb.setValue(kaeufer, ("trans"+(i)), ("&3| &aKauf von &2"+anzahl + " " + gegenstand + "&a in ServerShop: &c-" + kosten + kuerzel));
		}
		if (Finanzendb.hasKey(kaeufer, ("trans" + max))) {
			transDurchrutschen(kaeufer);
			Finanzendb.setValue(kaeufer, ("trans"+max), ("&3| &aKauf von &2"+anzahl + " " + gegenstand + "&a in ServerShop: &c-" + kosten + kuerzel));
		}else {
			int i = 1;
			while (Finanzendb.hasKey(kaeufer, ("trans"+i).toString())){
				i++;
			}
			Finanzendb.setValue(kaeufer, ("trans"+(i)), ("&3| &aKauf von &2"+anzahl + " " + gegenstand + "&a in ServerShop: &c-" + kosten+ kuerzel));
		}
	}
	public void addViAS(String verkaeufer, String gegenstand, int anzahl, double gespreis) {
		int max = plugin.getConfig().getInt("maxTransaktionen");
		String gesamtpreis = Finanzen.f.format(gespreis);
		fm.addToLog("[Verkauf]", verkaeufer + " verkauft " + anzahl + "x "+ gegenstand +" an einen AdminShop (+" + gesamtpreis + kuerzel + ")");
		if (max == 0) {
			int i = 1;
			while (Finanzendb.hasKey(verkaeufer, ("trans"+i).toString())){
				i++;
			}
			Finanzendb.setValue(verkaeufer, ("trans"+(i)), ("&3| &cVerkauf von &2"+anzahl + " " + gegenstand + "&c an ServerShop: &2+" + gesamtpreis + kuerzel));
		}
		if (Finanzendb.hasKey(verkaeufer, ("trans" + max))) {
			transDurchrutschen(verkaeufer);
			Finanzendb.setValue(verkaeufer, ("trans"+max), ("&3| &cVerkauf von &2"+anzahl + " " + gegenstand + "&c an ServerShop: &2+" + gesamtpreis + kuerzel));
		}else {
			int i = 1;
			while (Finanzendb.hasKey(verkaeufer, ("trans"+i).toString())){
				i++;
			}
			Finanzendb.setValue(verkaeufer, ("trans"+(i)), ("&3| &cVerkauf von &2"+anzahl + " " + gegenstand + "&c an ServerShop: &2+" + gesamtpreis+ kuerzel));
		}
	}
	public void addShopUmstellen(String name) {
		int max = plugin.getConfig().getInt("maxTransaktionen");
		String umstellkosten = Finanzen.f.format(shopumstellkosten);
		if (max == 0) {
			int i = 1;
			while (Finanzendb.hasKey(name, ("trans"+i).toString())){
				i++;
			}
			Finanzendb.setValue(name, ("trans"+(i)), ("&3| &Umstellen von &2Kistenshop&a: &c-" + umstellkosten + kuerzel));
		}
		if (Finanzendb.hasKey(name, ("trans" + max))) {
			transDurchrutschen(name);
			Finanzendb.setValue(name, ("trans"+max), ("&3| &aUmstellen von &2Kistenshop&a: &c-" + umstellkosten + kuerzel));
		}else {
			int i = 1;
			while (Finanzendb.hasKey(name, ("trans"+i).toString())){
				i++;
			}
			Finanzendb.setValue(name, ("trans"+(i)), ("&3| &aUmstellen von &2Kistenshop&a: &c-" + umstellkosten+ kuerzel));
		}
		fm.addToLog(name, "Shoprepositionierung  (-" + umstellkosten + kuerzel + ")");
	}

	public void addStadt(String name, String stadtname, double preis) {
		int max = plugin.getConfig().getInt("maxTransaktionen");
		if (max == 0) {
			int i = 1;
			while (Finanzendb.hasKey(name, ("trans"+i).toString())){
				i++;
			}
			Finanzendb.setValue(name, ("trans"+(i)), ("&3| &6Gründung von &e"+stadtname+"&a: &c-" + preis + kuerzel));
		}
		if (Finanzendb.hasKey(name, ("trans" + max))) {
			transDurchrutschen(name);
			Finanzendb.setValue(name, ("trans"+max), ("&3| &6Gründung von &e"+stadtname+"&a: &c-" + preis + kuerzel));
		}else {
			int i = 1;
			while (Finanzendb.hasKey(name, ("trans"+i).toString())){
				i++;
			}
			Finanzendb.setValue(name, ("trans"+(i)), ("&3| &6Gründung von &e"+stadtname+"&a: &c-" + preis + kuerzel));
		}
		fm.addToLog(name, "Stadtgründung \""+stadtname+"\" (-" + preis + kuerzel + ")");
	}
	public void addGSKauf(String name, String GSID, double preis) {
		int max = plugin.getConfig().getInt("maxTransaktionen");
		String helper = "";
		if (!getGS(GSID).getBesitzer().equalsIgnoreCase("tba")) {
			helper = " von " + getGS(GSID).getBesitzer();
		}
		if (max == 0) {
			int i = 1;
			while (Finanzendb.hasKey(name, ("trans"+i).toString())){
				i++;
			}
			Finanzendb.setValue(name, ("trans"+(i)), ("&3| &9Kauf von Grundstück &e"+getMasse(GSID)+helper+"&9: &c-" + preis + kuerzel));
		}
		if (Finanzendb.hasKey(name, ("trans" + max))) {
			transDurchrutschen(name);
			Finanzendb.setValue(name, ("trans"+max), ("&3| &9Kauf von Grundstück &e"+getMasse(GSID)+helper+"&9: &c-" + preis + kuerzel));
		}else {
			int i = 1;
			while (Finanzendb.hasKey(name, ("trans"+i).toString())){
				i++;
			}
			Finanzendb.setValue(name, ("trans"+(i)), ("&3| &9Kauf von Grundstück &e"+getMasse(GSID)+helper+"&9: &c-" + preis + kuerzel));
		}
		fm.addToLog(name, "Grundstückskauf \""+GSID+"\" (-" + preis + kuerzel + ")");
	}
	public void addGSVerkauf(String Besitzer, double preis, String kaeufer, String GSID) {
		int max = plugin.getConfig().getInt("maxTransaktionen");
		if (max == 0) {
			int i = 1;
			while (Finanzendb.hasKey(Besitzer, ("trans"+i).toString())){
				i++;
			}
			Finanzendb.setValue(Besitzer, ("trans"+(i)), ("&3| &4Verkauf von Grundstück &e"+GSID+"&4 an &e"+kaeufer+"&4: &a+" + preis + kuerzel));
		}
		if (Finanzendb.hasKey(Besitzer, ("trans" + max))) {
			transDurchrutschen(Besitzer);
			Finanzendb.setValue(Besitzer, ("trans"+max), ("&3| &4Verkauf von Grundstück &e"+GSID+"&4 an &e"+kaeufer+"&4: &a+" + preis + kuerzel));
		}else {
			int i = 1;
			while (Finanzendb.hasKey(Besitzer, ("trans"+i).toString())){
				i++;
			}
			Finanzendb.setValue(Besitzer, ("trans"+(i)), ("&3| &4Verkauf von Grundstück &e"+GSID+"&4 an &e"+kaeufer+"&4: &a+" + preis + kuerzel));
		}
		fm.addToLog(Besitzer, "Grundstücksverkauf \""+GSID+"\" (+" + preis + kuerzel + ")");
	}
	public void addGSVerkauf(String Besitzer, double kosten, String stadtname, double steuern, String kaeufer, String GSID) {
		int max = plugin.getConfig().getInt("maxTransaktionen");
		if (max == 0) {
			int i = 1;
			while (Finanzendb.hasKey(Besitzer, ("trans"+i).toString())){
				i++;
			}
			Finanzendb.setValue(Besitzer, ("trans"+(i)), ("&3| &4Verkauf von Grundstück &e"+GSID+"&4 an &e"+kaeufer+"&4 in &e "+stadtname+"&4: &a+" + kosten + kuerzel + "&c(-"+steuern + kuerzel+" Steuern)"));
		}
		if (Finanzendb.hasKey(Besitzer, ("trans" + max))) {
			transDurchrutschen(Besitzer);
			Finanzendb.setValue(Besitzer, ("trans"+max), ("&3| &4Verkauf von Grundstück &e"+GSID+"&4 an &e"+kaeufer+"&4 in &e "+stadtname+"&4: &a+" + kosten + kuerzel + "&c(-"+steuern + kuerzel+" Steuern)"));
		}else {
			int i = 1;
			while (Finanzendb.hasKey(Besitzer, ("trans"+i).toString())){
				i++;
			}
			Finanzendb.setValue(Besitzer, ("trans"+(i)), ("&3| &4Verkauf von Grundstück &e"+GSID+"&4 an &e"+kaeufer+"&4 in &e "+stadtname+"&4: &a+" + kosten + kuerzel + "&c(-"+steuern + kuerzel+" Steuern)"));
		}
		fm.addToLog(Besitzer, "Grundstücksverkauf \""+GSID+"\" in "+stadtname + ": +" + kosten + kuerzel + "(-"+steuern + kuerzel+" Steuern)");
	
	}
	public void addSpende(String name, double spende, String stadtname) {
		int max = plugin.getConfig().getInt("maxTransaktionen");
		if (max == 0) {
			int i = 1;
			while (Finanzendb.hasKey(name, ("trans"+i).toString())){
				i++;
			}
			Finanzendb.setValue(name, ("trans"+(i)), ("&3| &6Spende an &e"+stadtname+"&a: &c-" + spende + kuerzel));
		}
		if (Finanzendb.hasKey(name, ("trans" + max))) {
			transDurchrutschen(name);
			Finanzendb.setValue(name, ("trans"+max), ("&3| &6Spende an &e"+stadtname+"&a: &c-" + spende + kuerzel));
		}else {
			int i = 1;
			while (Finanzendb.hasKey(name, ("trans"+i).toString())){
				i++;
			}
			Finanzendb.setValue(name, ("trans"+(i)), ("&3| &6Spende an&e"+stadtname+"&a: &c-" + spende + kuerzel));
		}
		fm.addToLog(name, "Spende an \""+stadtname+"\" (-" + spende + kuerzel + ")");
		if (max == 0) {
			int i = 1;
			while (Finanzendb.hasKey(stadtname, ("trans"+i).toString())){
				i++;
			}
			Finanzendb.setValue(stadtname, ("trans"+(i)), ("&3| &6Spende von &e"+name+"&a: &c+" + spende + kuerzel));
		}
		if (Finanzendb.hasKey(stadtname, ("trans" + max))) {
			transDurchrutschen(stadtname);
			Finanzendb.setValue(stadtname, ("trans"+max), ("&3| &6Spende von &e"+name+"&a: &c+" + spende + kuerzel));
		}else {
			int i = 1;
			while (Finanzendb.hasKey(stadtname, ("trans"+i).toString())){
				i++;
			}
			Finanzendb.setValue(stadtname, ("trans"+(i)), ("&3| &6Spende von &e"+name+"&a: &c+" + spende + kuerzel));
		}
		
		
		
	}
	

	private void transDurchrutschen(String name) {
		for (int i = 1; Finanzendb.hasKey(name, ("trans"+(i+1)).toString()); i++) {
			Finanzendb.setValue(name, ("trans"+i).toString(), Finanzendb.getString(name, ("trans"+(i+1)).toString()));
		}
		
	}
	public void transReset(String name) {
		int i = 1;
		while (Finanzendb.hasKey(name, ("trans"+(i)).toString())) {
			Finanzendb.removeValue(name, ("trans"+(i)).toString()); 
			i++;
		}
		Finanzendb.setValue(name, "trans1", "&3| Alter Kontostand: &b" + Finanzendb.getDouble(name, "konto") + " " + waehrung);
		
	}


	public static String replaceColors(String string) {
	    return string.replaceAll("(?i)&([a-f0-9])", "\u00A7$1");
	}
	public void loadConfig(){
		
		if(new File("plugins/Finanzen/config.yml").exists()){			
			config = getConfig();
			config.options().copyDefaults(true);
			System.out.println("[Finanzen] Config geladen!");	
		}else{
			saveDefaultConfig();
			config = getConfig();
			config.options().copyDefaults(true);
			System.out.println("[Finanzen] Config geladen und erstellt!");
		}
		waehrung = config.getString("Waehrung");
		kuerzel = config.getString("Kuerzel");
		shopkosten = config.getDouble("Shopkosten");
		shoperstattung = config.getDouble("Shoperstattung");
		shopumstellkosten = config.getDouble("ShopUmstellKosten");
		abstand = config.getInt("Abstand");
		marker = config.getInt("marker");
		markername = config.getString(marker+".0.name");
		tiefe = config.getInt("Tiefe");
		GSGrenzen = config.getString("GSGrenzen");
		GSKosten = config.getDouble("GSKosten");
		Stadtkosten = config.getDouble("Stadtkosten");
		
		
	}
	private void loadShops() {
		if (Shops.getIndices().isEmpty()) 
			return;
		for (String index : Shops.getIndices()) {
			ShopHM.put(Shops.getString(index, "world")+":"+ Shops.getInt(index, "x")+":"+ Shops.getInt(index, "y")+":"+ Shops.getInt(index, "z"), Integer.parseInt(index));
			shopanzahl++;
		}
		System.out.println("[Finanzen] " + shopanzahl + " Shops geladen.");
	}
	private void loadShopHalter() {
		if (Shops.getIndices().isEmpty()) {
			return;
		}
		for (String index : Shops.getIndices()) {
			Location SL = new Location(plugin.getServer().getWorld(Shops.getString(index, "world")), Shops.getInt(index, "x"), Shops.getInt(index, "y"), Shops.getInt(index, "z"));
			Location helper = new Location (SL.getWorld(), SL.getBlockX(), SL.getBlockY(), SL.getBlockZ());
			if (SL.getBlock().getData() == 2) {
				helper.setZ(helper.getZ()+1);
			}
			if (SL.getBlock().getData() == 3) {
				helper.setZ(helper.getZ()-1);
			}
			if (SL.getBlock().getData() == 4) {
				helper.setX(helper.getX()+1);
			}
			if (SL.getBlock().getData() == 5) {
				helper.setX(helper.getX()-1);
			}
			SchildHalterBlocks.add(helper);
		}
	}
	
	public static boolean isShopSchild(Location loc) {
		String key = loc.getWorld().getName()+":"+loc.getBlockX()+":"+loc.getBlockY()+":"+loc.getBlockZ();
		return ShopHM.containsKey(key);
	}
	public static boolean isShopChest(Location loc) {
		loc.setY(loc.getY()+1);
		return isShopSchild(loc);
	}
	public static boolean isAdminShop(int ShopID) {
		return getBesitzer(ShopID).equalsIgnoreCase("AdminShop");
	}
	
	public static boolean holdsShopSchild(Location loc) {
		return SchildHalterBlocks.contains(loc);
	}
	
	public static boolean isNaheKiste(Location loc) {
		Location loc1 = new Location(loc.getWorld(), loc.getX()+1, loc.getY(), loc.getZ());
		Location loc2 = new Location(loc.getWorld(), loc.getX()-1, loc.getY(), loc.getZ());
		Location loc3 = new Location(loc.getWorld(), loc.getX(), loc.getY(), loc.getZ()+1);
		Location loc4 = new Location(loc.getWorld(), loc.getX(), loc.getY(), loc.getZ()-1);
		
		if (loc1.getBlock().getTypeId() == 54 || loc2.getBlock().getTypeId() == 54 || loc3.getBlock().getTypeId() == 54 || loc4.getBlock().getTypeId() == 54)
			return true;
		return false;
	}
	public static boolean isNahGenug(Location loc, int ShopID) {
		int i = Finanzen.abstand;
		int x = loc.getBlockX();
		int y = loc.getBlockY();
		int z = loc.getBlockZ();
		Location shop = getShopLocation(ShopID);
		int shopx = shop.getBlockX();
		int shopy = shop.getBlockY();
		int shopz = shop.getBlockZ();
		if (x < shopx-i || x > shopx+i) {
			return false;
		}
		if (y < shopy-i || y > shopy+i) {
			return false;
		}
		if (z < shopz-i || z > shopz+i) {
			return false;
		}
		
		return true;
	}
	
	public static boolean isEingestellt(int ShopID) {
		String index = String.valueOf(ShopID);
		if (Shops.getString(index, "id").equalsIgnoreCase("tba"))
			return false;
		return true;
	}
	public static String getBesitzer(int ShopID) {
		return Shops.getString(String.valueOf(ShopID), "besitzer");
	}
	public static String getWare(int ShopID) {
		String bla = Shops.getString(String.valueOf(ShopID), "id");
		if (bla.equalsIgnoreCase("tba")) {
			return "noch nicht eingestellt";
		}
		return config.getString(bla.replace("#", ".")+".name");
		
	}
	public static double getEP(int ShopID) {
		return Shops.getDouble(String.valueOf(ShopID), "ep");
	}
	public static double getVP(int ShopID) {
		return Shops.getDouble(String.valueOf(ShopID), "vp");
	}
	public static String getWareID(int ShopID) {
		return Shops.getString(String.valueOf(ShopID), "id");
	}
	public static Inventory getShopInv(int ShopID) {
		Location loc = getShopLocation(ShopID);
		loc.setY(loc.getY()-1);
		Chest shop = (Chest) loc.getBlock().getState();
		return shop.getInventory();
	}
	public static Location getShopLocation(int ShopID) {
		String index = String.valueOf(ShopID);
		Location loc = new Location(plugin.getServer().getWorld(Shops.getString(index, "world")), Shops.getInt(index, "x"), Shops.getInt(index, "y"), Shops.getInt(index, "z"), 0f, 0f);
		return loc;
	}
	public static int getShopAt(Location loc) {
		String key = loc.getWorld().getName()+":"+loc.getBlockX()+":"+loc.getBlockY()+":"+loc.getBlockZ();
		int i = ShopHM.get(key);
		
		return i;
	}
	public static String getHMKey(int ShopID) {
		Location loc = getShopLocation(ShopID);
		return loc.getWorld().getName() + ":"+loc.getBlockX()+":"+loc.getBlockY()+":"+loc.getBlockZ();
	}
	public static void SchildBeschreiben(int ShopID) {
		Location loc = getShopLocation(ShopID);
		BlockState bs = loc.getBlock().getState();
		Sign schild = (Sign) bs;
		String index = String.valueOf(ShopID);
		String besitzer = getBesitzer(ShopID);
		double ep = Shops.getDouble(index, "ep");
		double vp = Shops.getDouble(index, "vp");
		String line1 = "";
		String preis = "";
		String ware = getWare(ShopID);
		if (ep != 0) {
			line1 = "kauft hier";
			preis = "für " +f.format(ep) + kuerzel;
		}
		if (vp != 0) {
			line1 = "verkauft hier";
			preis = "für " +f.format(vp) + kuerzel;
		}
		if (ep != 0 && vp != 0) {
			line1 = "kauft/verkauft";
			ware = getWare(ShopID) + " für";
			preis = ep+kuerzel+"/"+vp+kuerzel;
		}
		
		if (ep == 0 && vp == 0) {
			line1 = "inaktiver";
			preis = "Shop";
			besitzer = besitzer+getHelper(besitzer);
			
		}
		schild.setLine(0, besitzer);
		schild.setLine(1, line1);
		schild.setLine(2, ware);
		schild.setLine(3, preis);
		schild.update();
	}
	public static void SchildBeschreiben(Location loc) {
		BlockState bs = loc.getBlock().getState();
		Sign schild = (Sign) bs;
		int ShopID = getShopAt(loc);
		String index = String.valueOf(ShopID);
		String besitzer = getBesitzer(ShopID);
		double ep = Shops.getDouble(index, "ep");
		double vp = Shops.getDouble(index, "vp");
		String line1 = "";
		String preis = "";
		String ware = getWare(ShopID);
		if (ep != 0) {
			line1 = "kauft hier";
			preis = "für " + f.format(ep) + kuerzel;
		}
		if (vp != 0) {
			line1 = "verkauft hier";
			preis = "für " + f.format(vp) + kuerzel;
		}
		if (ep != 0 && vp != 0) {
			line1 = "kauft/verkauft";
			ware = getWare(ShopID) + " für";
			preis = ep+kuerzel+"/"+vp+kuerzel;
		}
		
		if (ep == 0 && vp == 0) {
			line1 = "inaktiver";
			preis = "Shop";
			besitzer = besitzer+getHelper(besitzer);
			
		}
		schild.setLine(0, besitzer);
		schild.setLine(1, line1);
		schild.setLine(2, ware);
		schild.setLine(3, preis);
		schild.update();
	}
	public static String getHelper(String name) {
		String helper = "s";
		if (name.endsWith("s") || name.endsWith("z")) {
			helper = "'";
		}
		return helper;
	}
	public static void removeShop(int shopID) {
		removeShopHalter(shopID);
		Location loc = getShopLocation(shopID);
		loc.getBlock().breakNaturally();
		loc.setY(loc.getBlockY() -1);
		loc.getBlock().breakNaturally();
		
		ShopHM.remove(getHMKey(shopID));
		Shops.removeEntry(String.valueOf(shopID));
		
	}
	public static void removeShopHalter(int ShopID) {
		Location SL = getShopLocation(ShopID);
		Location helper = new Location (SL.getWorld(), SL.getBlockX(), SL.getBlockY(), SL.getBlockZ());
		if (SL.getBlock().getData() == 2) {
			helper.setZ(helper.getZ()+1);
		}
		if (SL.getBlock().getData() == 3) {
			helper.setZ(helper.getZ()-1);
		}
		if (SL.getBlock().getData() == 4) {
			helper.setX(helper.getX()+1);
		}
		if (SL.getBlock().getData() == 5) {
			helper.setX(helper.getX()-1);
		}
		SchildHalterBlocks.remove(helper);
	}
	
	public void loadGS() {
		if (GSDB.getIndices().isEmpty()) {
			System.out.println("Keine Grundstücke gefunden!");
			return;
		}
		for (String index : GSDB.getIndices()) {
			Location A = new Location(plugin.getServer().getWorld(GSDB.getString(index, "Welt")), GSDB.getInt(index, "x1"), 64, GSDB.getInt(index, "z1"));
			Location D = new Location(plugin.getServer().getWorld(GSDB.getString(index, "Welt")), GSDB.getInt(index, "x2"), 64, GSDB.getInt(index, "z2"));
			Location Schild = new Location(plugin.getServer().getWorld(GSDB.getString(index, "Welt")), GSDB.getInt(index, "SX"), GSDB.getInt(index, "SY"), GSDB.getInt(index, "SZ"));
			String besitzer = GSDB.getString(index, "besitzer");
			ArrayList<String> baurecht = GSDB.hasKey(index, "baurecht") ? GSDB.getArrayList(index, "baurecht") : new ArrayList<String>();
			ArrayList<String> redstonerecht = GSDB.hasKey(index, "redstonerecht") ? GSDB.getArrayList(index, "redstonerecht") : new ArrayList<String>();
			ArrayList<String> generalschluessel = GSDB.hasKey(index, "generalschluessel") ? GSDB.getArrayList(index, "generalschluessel") : new ArrayList<String>();
			double preis = GSDB.getDouble(index, "preis");
			String status = GSDB.getString(index, "status");
			Finanzen.grundstuecke.put(index, new GS(index, A, D, besitzer, baurecht, redstonerecht, generalschluessel, preis, Schild, status));
			gsanzahl++;
			if (status.equalsIgnoreCase("zum Verkauf freigegeben")) {
				freiegs++;
			}
			
			
		}
		System.out.println("[Grundstücke] " + gsanzahl + " Grundstücke geladen.");
		System.out.println("[Grundstücke] " + freiegs + " davon zum Verkauf freigegeben.");
		
		
	}
	public void saveGS() {
		for (GS gs : Finanzen.grundstuecke.values()) {
			try {
				neuesGS(gs);
				
			} catch (EmptyIndexException e) {
				System.out.println("[ERROR] EmptyIndexException");
			} catch (EmptyListException e) {
				System.out.println("[ERROR] EmptyListException");
			}
		}
		GSDB.push();
	}
	public void neuesGS(GS gs) throws EmptyIndexException, EmptyListException {
		String index = gs.getID();
		Entry neuesGS = new Entry(index);
		neuesGS.addValue("besitzer", gs.getBesitzer());
		neuesGS.addValue("x1", gs.getA().getBlockX());
		neuesGS.addValue("z1", gs.getA().getBlockZ());
		neuesGS.addValue("x2", gs.getD().getBlockX());
		neuesGS.addValue("z2", gs.getD().getBlockZ());
		neuesGS.addValue("Welt", gs.getA().getWorld().getName());
		if (!gs.getBauer().isEmpty()) {
			neuesGS.addList("baurecht", gs.getBauer());
		}
		if (!gs.getRedstonerecht().isEmpty()) {
			neuesGS.addList("redstonerecht", gs.getRedstonerecht());
		}
		if (!gs.getSchluesselkinder().isEmpty()) {
			neuesGS.addList("generalschluessel", gs.getSchluesselkinder());
		}
		neuesGS.addValue("preis", gs.getPreis());
		neuesGS.addValue("SX", gs.getSchildLoc().getBlockX());
		neuesGS.addValue("SY", gs.getSchildLoc().getBlockY());
		neuesGS.addValue("SZ", gs.getSchildLoc().getBlockZ());
		neuesGS.addValue("status", gs.getStatus());
		Finanzen.GSDB.addEntry(neuesGS);
		
	}





	public void loadStaedte() {
		if (StaedteDB.getIndices().isEmpty()) {
			System.out.println("Keine Städte gefunden!");
			return;
		}
		for (String index : StaedteDB.getIndices()) {
			Location A = new Location(plugin.getServer().getWorld(StaedteDB.getString(index, "Welt")), StaedteDB.getInt(index, "xa"), 64, StaedteDB.getInt(index, "za"));
			Location D = new Location(plugin.getServer().getWorld(StaedteDB.getString(index, "Welt")), StaedteDB.getInt(index, "xd"), 64, StaedteDB.getInt(index, "zd"));
			String name = index;
			String besitzer = StaedteDB.getString(index, "besitzer");
			boolean allggsrecht = StaedteDB.getBoolean(index, "allggsrecht");
			boolean mobspawn = StaedteDB.getBoolean(index, "mobspawn");
			boolean firespread = StaedteDB.getBoolean(index, "firespread");
			boolean allgbaurecht = StaedteDB.getBoolean(index, "allgbaurecht");
			boolean pvp = StaedteDB.getBoolean(index, "pvp");
			double stadtkasse = StaedteDB.getDouble(index, "stadtkasse");
			double steuersatz = StaedteDB.getDouble(index, "steuersatz");
			ArrayList<String> bewohner = StaedteDB.hasKey(index, "bewohner") ? StaedteDB.getArrayList(index, "bewohner") : new ArrayList<String>();
			ArrayList<String> ratsmitglieder = StaedteDB.hasKey(index, "ratsmitglieder") ? StaedteDB.getArrayList(index, "ratsmitglieder") : new ArrayList<String>();
			ArrayList<String> lagerkistenStrings = StaedteDB.hasKey(index, "lagerkisten") ? StaedteDB.getArrayList(index, "lagerkisten") : new ArrayList<String>();
			ArrayList<Location> lagKist = new ArrayList<Location>();
			for (String lk : lagerkistenStrings) {
				String[] lksplit = lk.split("#");
				String welt = lksplit[0];
				int x = Integer.parseInt(lksplit[1]);
				int y = Integer.parseInt(lksplit[2]);
				int z = Integer.parseInt(lksplit[3]);
				lagKist.add(new Location(getServer().getWorld(welt), x, y, z));
				
			}
			String status = StaedteDB.getString(index, "status");
			String gebaeude = StaedteDB.getString(index, "gebaeude");
			Finanzen.staedte.put(index, new Stadt(name, besitzer, allggsrecht, mobspawn, firespread, allgbaurecht, pvp, stadtkasse, steuersatz, bewohner, ratsmitglieder, lagKist, A.getWorld().getName(), A.getBlockX(), A.getBlockZ(), D.getBlockX(), D.getBlockZ(), status, gebaeude));
			stadtanzahl++;
		}
		System.out.println("[Städte] " + stadtanzahl + " Städte geladen.");
		
	}
	public void saveStaedte() {
		
		for (Stadt stadt : Finanzen.staedte.values()) {
			try {
				String stadtname = stadt.getName();
				Entry entry = new Entry(stadtname);
				entry.addValue("besitzer", stadt.getBesitzer());
				entry.addValue("xa", stadt.getA().getBlockX());
				entry.addValue("za", stadt.getA().getBlockZ());
				entry.addValue("xd", stadt.getD().getBlockX());
				entry.addValue("zd", stadt.getD().getBlockZ());
				entry.addValue("Welt", stadt.getA().getWorld().getName());
				entry.addValue("stadtkasse", stadt.getKasse());
				entry.addValue("steuersatz", stadt.getSteuersatz());
				entry.addValue("mobspawn", stadt.isMobspawnAllowed());
				entry.addValue("firespread", stadt.isFirespreadAllowed());
				entry.addValue("allgbaurecht", stadt.isAllgBaurecht());
				entry.addValue("pvp", stadt.isPVPAllowed());
				if (!stadt.getBewohner().isEmpty()) {
					entry.addList("bewohner", stadt.getBewohner());
				}
				if (!stadt.getRat().isEmpty()) {
					entry.addList("ratsmitglieder", stadt.getRat());
				}
				entry.addValue("status", stadt.getStatus());
				if (!stadt.getLagerkisten().isEmpty()) {
					ArrayList<String> bla = new ArrayList<String>();
					for (Location loc : stadt.getLagerkisten()) {
						String key = loc.getWorld().getName()+"#"+loc.getBlockX()+"#"+loc.getBlockY()+"#"+loc.getBlockZ();
						bla.add(key);
					}
					entry.addList("lagerkisten", bla);
				}
				
			
				String gebaeude = String.valueOf(
						String.valueOf(stadt.lager)+"#"+ 
						String.valueOf(stadt.fischer)+"#"+ 
						String.valueOf(stadt.muehle)+"#"+ 
						String.valueOf(stadt.taverne)+"#"+ 
						String.valueOf(stadt.markt)+"#"+ //5
						String.valueOf(stadt.kapelle)+"#"+ 
						String.valueOf(stadt.mine)+"#"+ 
						String.valueOf(stadt.rathaus)+"#"+ 
						String.valueOf(stadt.bank)+"#"+ 
						String.valueOf(stadt.bahnhof)+"#"+ //10
						String.valueOf(stadt.shop)+"#"+ 
						String.valueOf(stadt.schiff1)+"#"+ 
						String.valueOf(stadt.schiff2)+"#"+ 
						String.valueOf(stadt.hafen)+"#"+ 
						String.valueOf(stadt.kirche)+"#"+ //15
						String.valueOf(stadt.gericht)+"#"+ 
						String.valueOf(stadt.mauer)+"#"+ 
						String.valueOf(stadt.polizei)+"#"+ 
						String.valueOf(stadt.burg)+"#"+ 
						String.valueOf(stadt.bibliothek)+"#"+ //20
						String.valueOf(stadt.hotel)+"#"+ 
						String.valueOf(stadt.knast)+"#"+ 
						String.valueOf(stadt.feuerwehr)+"#"+ 
						String.valueOf(stadt.krankenhaus)+"#"+ 
						String.valueOf(stadt.palast)+"#"+ //25
						String.valueOf(stadt.denkmal)+"#"+ 
						String.valueOf(stadt.turnierplatz)+"#"+ 
						String.valueOf(stadt.magierturm)+"#"+ 
						String.valueOf(stadt.kloster)+"#"+ 
						String.valueOf(stadt.museum)+"#"+ //30
						String.valueOf(stadt.casino)+"#"+ 
						String.valueOf(stadt.schlachthaus)+"#"+ 
						String.valueOf(stadt.tankstelle)+"#"+ 
						String.valueOf(stadt.supermarkt)+"#"+ 
						String.valueOf(stadt.mall)+"#"+ //35
						String.valueOf(stadt.tierheim)+"#"+ 
						String.valueOf(stadt.schuldturm)+"#"+ 
						String.valueOf(stadt.pranger)+"#"+ 
						String.valueOf(stadt.baecker)+"#"+ 
						String.valueOf(stadt.garnison)+"#"+ //40
						String.valueOf(stadt.alchemist)+"#"+ 
						String.valueOf(stadt.werkzeugschmied)+"#"+ 
						String.valueOf(stadt.waffenschmied)+"#"+ 
						String.valueOf(stadt.ruestungsschmied)+"#"+ 
						String.valueOf(stadt.fabrik1)+"#"+ //45
						String.valueOf(stadt.fabrik2)+"#"+ 
						String.valueOf(stadt.fabrik3)+"#"+ 
						String.valueOf(stadt.weltwunder1)+"#"+ 
						String.valueOf(stadt.weltwunder2)+"#"+ 
						String.valueOf(stadt.weltwunder3)+"#"+//50 
						String.valueOf(stadt.weltwunder4)+"#"+ 
						String.valueOf(stadt.weltwunder5)+"#"+ 
						String.valueOf(stadt.weltwunder6)+"#"+ 
						String.valueOf(stadt.weltwunder7)+"#"+ 
						String.valueOf(stadt.schiff3)+"#"+//55
						String.valueOf(stadt.leuchtturm)+"#"+
						String.valueOf(stadt.reiseburo)
						);
				entry.addValue("gebaeude", gebaeude);
				Finanzen.StaedteDB.addEntry(entry);
				
			} catch (EmptyIndexException e) {
				e.printStackTrace();
			} catch (EmptyListException e) {
				e.printStackTrace();
			}
		}
		Finanzen.StaedteDB.push();
	}
	public static GS getGS(String GSID) {
		return Finanzen.grundstuecke.get(GSID);
	}
	public static boolean isGSSchild(Location loc) {
		return Finanzen.GSSchilder.containsKey(loc);
	}
	
	public static void GSSchildBeschreiben(Location Schild) {
		Sign schild = (Sign) Schild.getBlock().getState();
		String GSID = Finanzen.GSSchilder.get(Schild);
		GS gs = Finanzen.grundstuecke.get(GSID);
		String besitzer = gs.getBesitzer();
		Stadt stadt = getStadtAtLoc(Schild);
		if (stadt == null) {
			schild.setLine(0, "-- Wildnis --");
			if (gs.getStatus().equalsIgnoreCase("Baustelle")) {
				schild.setLine(1, "Hier baut");
				schild.setLine(2, besitzer);
				schild.setLine(3, "");
				schild.update();
				return;
			}
			if (gs.getStatus().equalsIgnoreCase("Wohnhaus")) {
				schild.setLine(1, "Hier wohnt");
				schild.setLine(2, besitzer);
				schild.setLine(3, "");
				schild.update();
				return;
			}
			if (gs.getStatus().equalsIgnoreCase("zum Verkauf freigegeben")) {
				schild.setLine(1, "Baugrund");
				Location locA = gs.getA();
				Location locB = gs.getD();
				int x = locA.getBlockX() > locB.getBlockX() ? locA.getBlockX() - locB.getBlockX() : locB.getBlockX() - locA.getBlockX() ;
				int z = locA.getBlockZ() > locB.getBlockZ() ? locA.getBlockZ() - locB.getBlockZ() : locB.getBlockZ() - locA.getBlockZ() ;
				schild.setLine(2, "Grösse: " + (x+1) + "*"+(z+1));
				schild.setLine(3, "Nur " +Finanzen.f.format(gs.getPreis())+Finanzen.kuerzel+"!");
				schild.update();
				return;
			}
		}else {
			schild.setLine(0, "-- "+Finanzen.capFirst(stadt.getName())+" --");
			if (gs.getStatus().equalsIgnoreCase("Baustelle")) {
				schild.setLine(1, "Hier baut");
				schild.setLine(2, besitzer);
				schild.setLine(3, "");
				schild.update();
				return;
			}
			if (gs.getStatus().equalsIgnoreCase("Wohnhaus")) {
				schild.setLine(1, "Hier wohnt");
				schild.setLine(2, besitzer);
				schild.setLine(3, "");
				schild.update();
				return;
			}
			if (gs.getStatus().equalsIgnoreCase("zum Verkauf freigegeben")) {
				schild.setLine(1, "Baugrund");
				Location locA = gs.getA();
				Location locB = gs.getD();
				int x = locA.getBlockX() > locB.getBlockX() ? locA.getBlockX() - locB.getBlockX() : locB.getBlockX() - locA.getBlockX() ;
				int z = locA.getBlockZ() > locB.getBlockZ() ? locA.getBlockZ() - locB.getBlockZ() : locB.getBlockZ() - locA.getBlockZ() ;
				schild.setLine(2, "Grösse: " + (x+1) + "*"+(z+1));
				schild.setLine(3, "Nur " +Finanzen.f.format(gs.getPreis())+Finanzen.kuerzel+"!");
				schild.update();
				return;
			}
			schild.setLine(0, "--"+gs.getStatus()+"--");
			schild.setLine(1, "von");
			schild.setLine(2, Finanzen.capFirst(stadt.getName()));
			schild.setLine(3, "");
			schild.update();
		}
	}

	public static int getGroesse(Location A, Location D) {
		int x = A.getBlockX() > D.getBlockX() ? A.getBlockX() - D.getBlockX() : D.getBlockX() - A.getBlockX() ;
		int z = A.getBlockZ() > D.getBlockZ() ? A.getBlockZ() - D.getBlockZ() : D.getBlockZ() - A.getBlockZ() ;
		/*"Das markierte Grundstück hat die Maße: " + (x+1) + "*" + (z+1));*/
		return ((x+1)*(z+1));
	
	}
	public static String getMasse(Location A, Location D) {
		int x = A.getBlockX() > D.getBlockX() ? A.getBlockX() - D.getBlockX() : D.getBlockX() - A.getBlockX() ;
		int z = A.getBlockZ() > D.getBlockZ() ? A.getBlockZ() - D.getBlockZ() : D.getBlockZ() - A.getBlockZ() ;
		String bla = "(" + (x+1) + "*" + (z+1) + ")";
		return bla;
	}
	
	public static String getMasse(String GSID) {
		GS gs = getGS(GSID);
		Location A, D = null;
		if (gs != null) {
			A = gs.getA();
			D = gs.getD();
			int x = A.getBlockX() > D.getBlockX() ? A.getBlockX() - D.getBlockX() : D.getBlockX() - A.getBlockX() ;
			int z = A.getBlockZ() > D.getBlockZ() ? A.getBlockZ() - D.getBlockZ() : D.getBlockZ() - A.getBlockZ() ;
			String bla = "(" + (x+1) + "*" + (z+1) + ")";
			return bla;
		}
		return "FEHLER";
	}

	public void incStadtkasse(String stadtname, double amount) {
		getStadt(stadtname).stadtkasse += amount;
	}
	public static Stadt getStadt(String stadtname) {
		return staedte.get(stadtname);
	}
	public static boolean isGSinStadt(GS gs, Stadt stadt) {
		int stadtMinX = stadt.getA().getBlockX();
		int stadtMaxX = stadt.getD().getBlockX();
		int stadtMinZ = stadt.getA().getBlockZ();
		int stadtMaxZ = stadt.getD().getBlockZ();
		int gsMinX = gs.getA().getBlockX();
		int gsMinZ = gs.getA().getBlockZ();
		int gsMaxX = gs.getD().getBlockX();
		int gsMaxZ = gs.getD().getBlockZ();
		if (gsMinX <stadtMinX) {
			return false;
		}
		if (gsMaxX >stadtMaxX) {
			return false;
		}
		if (gsMinZ <stadtMinZ) {
			return false;
		}
		if (gsMaxZ >stadtMaxZ) {
			return false;
		}
		return true;
	}
 	
	public static GS getGSAtLoc(Location loc) {
		if (grundstuecke.isEmpty()) {
			return null;
		}
		for (GS gs : grundstuecke.values()) {
			if (gs.isOnGS(loc)) {
				return gs;
			}
		}
		return null;
	}
	public static Stadt getStadtAtLoc(Location loc) {
 		if (staedte.isEmpty()) {
 			return null;
 		}
 		for (Stadt stadt : staedte.values()) {
 			if (stadt.isInStadt(loc)) {
 				return stadt;
 			}
 		}
 		return null;
 	}

	public static boolean isUnterGSSchild(Location location) {
		
		for (Location loc : GSSchilder.keySet()) {
			Location temp = new Location(loc.getWorld(), loc.getX(), (loc.getY()-1), loc.getZ());
			if (location.equals(temp)) {
				return true;
			}
		}
		return false;
	}
	public static double getGSWert(GS gs) {
		double stkpreis = Finanzen.config.getDouble("GSKosten");
		int felder = (gs.getD().getBlockX()-gs.getA().getBlockX()+1)*(gs.getD().getBlockZ()-gs.getA().getBlockZ()+1);
		return stkpreis*felder;
	}
	public static double getGSWert(Location A, Location D) {
		int X1 = A.getBlockX();
		int Z1 = A.getBlockZ();
		int X2 = D.getBlockX();
		int Z2 = D.getBlockZ();
		int maxZ = (Z1 > Z2) ? Z1 : Z2;
		int maxX = (X1 > X2) ? X1 : X2;
		int minZ = (Z1 > Z2) ? Z2 : Z1;
		int minX = (X1 > X2) ? X2 : X1;
		A = new Location(A.getWorld(), minX, 64, minZ);
		D = new Location(A.getWorld(), maxX, 64, maxZ);
		
		double stkpreis = Finanzen.config.getDouble("GSKosten");
		int felder = (D.getBlockX()-A.getBlockX()+1)*(1+D.getBlockZ()-A.getBlockZ());
		return stkpreis*felder;
	}
	
	
	public static double getStadtWert(Stadt stadt) {
		
		double stkpreis = Finanzen.config.getDouble("Stadtkosten");
		int felder = (stadt.getD().getBlockX()-stadt.getA().getBlockX()+1)*(1+stadt.getD().getBlockZ()-stadt.getA().getBlockZ());
		return stkpreis*felder;
	}
	public static double getStadtWert(Location A, Location D) {
		int X1 = A.getBlockX();
		int Z1 = A.getBlockZ();
		int X2 = D.getBlockX();
		int Z2 = D.getBlockZ();
		int maxZ = (Z1 > Z2) ? Z1 : Z2;
		int maxX = (X1 > X2) ? X1 : X2;
		int minZ = (Z1 > Z2) ? Z2 : Z1;
		int minX = (X1 > X2) ? X2 : X1;
		A = new Location(A.getWorld(), minX, 64, minZ);
		D = new Location(A.getWorld(), maxX, 64, maxZ);
		
		double stkpreis = Finanzen.config.getDouble("Stadtkosten");
		int felder = (D.getBlockX()-A.getBlockX())*(D.getBlockZ()-A.getBlockZ());
		return stkpreis*felder;
	}

	public static String capFirst(String s) {
		s = s.substring(0,1).toUpperCase()+s.substring(1,s.length()).toLowerCase();
		return s;
	}
	public static void loadGebiete() {
		sets = markerapi.getMarkerSet("staedte.markerset");
		setgs = markerapi.getMarkerSet("gs.markerset");
		setgebaeude = markerapi.getMarkerSet("gebaeude.markerset");
		if(sets == null)
            sets = markerapi.createMarkerSet("staedte.markerset", "Städte", null, false);
		if(setgs == null)
            setgs = markerapi.createMarkerSet("gs.markerset", "Grundstücke", null, false);
		if(setgebaeude == null) 
			setgebaeude = markerapi.createMarkerSet("gebaeude.markerset", "Gebäude", null, false);
        for (Stadt stadt : staedte.values()) {
        	double[] x = new double[4];
            double[] z = new double[4];
            x[0] = stadt.getA().getX(); z[0] = stadt.getA().getZ();
            x[1] = stadt.getA().getX(); z[1] = stadt.getD().getZ()+1;
            x[2] = stadt.getD().getX()+1; z[2] = stadt.getD().getZ()+1;
            x[3] = stadt.getD().getX()+1; z[3] = stadt.getA().getZ();
	        AreaMarker m = sets.createAreaMarker(stadt.getName(), capFirst(stadt.getName()), false, stadt.getA().getWorld().getName(), x, z, false);
			String stadtname = stadt.getName();
			String status = stadt.getStatus();
			String kasse = f.format(stadt.getKasse())+kuerzel;
			String bm = stadt.getBesitzer();
			String anzahl = String.valueOf(stadt.getBewohner().size());
			String groesse = getMasse(stadt.getA(), stadt.getD());
	        String rat = stadt.getRat().toString();
			String anzeige = "<div class=\"infowindow\">" +
					"<span style=\"font-size:140%;\"><span style =\"text-decoration:underline;\">"+capFirst(stadtname)+"</span></span>  ("+status+")<br /><br />" +
					"<span style=\"font-weight:bold;\">Bürgermeister: </span>"+bm+"<br />" +
					"<span style=\"font-weight:bold;\">Rat: </span>"+rat+"<br />" +
					"<span style=\"font-weight:bold;\">Bewohner: </span>"+anzahl+"<br />" +
					"<span style=\"font-weight:bold;\">Stadtkasse: </span>"+kasse+"<br />" +
					"<span style=\"font-weight:bold;\">Größe: </span>"+groesse+"<br />" +
					"</div>";
			m.setDescription(anzeige);
			m.setFillStyle(0.4, 0x00008B);
			m.setLineStyle(2, 0.1, 0);
			
		}
        for (GS gs : grundstuecke.values()) {
        	double[] x = new double[4];
            double[] z = new double[4];
            x[0] = gs.getA().getX(); z[0] = gs.getA().getZ();
            x[1] = gs.getA().getX(); z[1] = gs.getD().getZ()+1;
            x[2] = gs.getD().getX()+1; z[2] = gs.getD().getZ()+1;
            x[3] = gs.getD().getX()+1; z[3] = gs.getA().getZ();
	        AreaMarker m = setgs.createAreaMarker(gs.getID(), capFirst(gs.getBesitzer()), true, gs.getA().getWorld().getName(), x, z, true);
	        int color = 0x00CED1;
	        String status = gs.getStatus();
	        String zeile2 = gs.getBesitzer().equalsIgnoreCase("tba") ? "<span style=\"font-weight:bold;\">Preis: </span>"+ f.format(gs.getPreis())+kuerzel+"<br />"  :"<span style=\"font-weight:bold;\">Besitzer: </span>"+gs.getBesitzer()+"<br />";
	        if (!gs.getBesitzer().equalsIgnoreCase("tba") && gs.getStatus().equalsIgnoreCase("zum Verkauf freigegeben")) {
	        	zeile2 += "<span style=\"font-weight:bold;\">Preis: </span>"+ f.format(gs.getPreis())+kuerzel+"<br />" ;
	        }
	        if (!gs.getStatus().equalsIgnoreCase("Baustelle") && !gs.getStatus().equalsIgnoreCase("zum Verkauf freigegeben") && !gs.getStatus().equalsIgnoreCase("Wohnhaus")) {
	        	System.out.println(gs.getStatus());
	        	zeile2 = "<span style=\"font-weight:bold;\">von </span>"+capFirst(Finanzen.getStadtAtLoc(gs.getA()).getName())+"<br />";
				color = 0xFFD700;
	        }
	        if (gs.getStatus().equalsIgnoreCase("zum Verkauf freigegeben")) {
	        	color = 0x53868B;
	        }
	        String anzeige = "<div class=\"infowindow\">" +
					"<span style=\"font-size:120%;\"><span style =\"text-decoration:underline;\">"+status+"</span></span><br /><br />" +
					zeile2 +
					"<span style=\"font-weight:bold;\">ID: </span>"+gs.getID()+"<br />" +
					"<span style=\"font-weight:bold;\">Größe: </span>"+getMasse(gs.getID())+"<br />" +
					"</div>";
			m.setDescription(anzeige);
			m.setFillStyle(0.4, color);
			m.setLineStyle(2, 0.1, 0);
			String helper=getIconName(status);
			MarkerIcon icon = markerapi.getMarkerIcon(helper);
			setgebaeude.createMarker(gs.getID(), gs.getStatus(), gs.getSchildLoc().getWorld().getName(), gs.getSchildLoc().getX(), gs.getSchildLoc().getY(), gs.getSchildLoc().getZ(), icon, true);
			
		}
        
        
		
		
	}

	public static String getIconName(String neubau) {
		if (neubau.equalsIgnoreCase("zum Verkauf freigegeben")) {
			return "coins";
		}
		if (neubau.equalsIgnoreCase("Baustelle")) {
			return "construction";
		}
		if (neubau.equalsIgnoreCase("Wohnhaus")) {
			return "default";
		}
		if (neubau.equalsIgnoreCase("lager")) {
			return "chest";
		}
		if (neubau.equalsIgnoreCase("fischer")) {
			//ICON MACHEN
			return "fish";
		}
		if (neubau.equalsIgnoreCase("muehle")) {
			//ICON MACHEN
			return "muhle";
		}
		if (neubau.equalsIgnoreCase("taverne")) {
			return "beer";
		}
		if (neubau.equalsIgnoreCase("markt")) {
			return "scales";
		}
		if (neubau.equalsIgnoreCase("kapelle")) {
			return "church";
		}
		if (neubau.equalsIgnoreCase("mine")) {
			//ICON MACHEN
			return "mine";
		}
		if (neubau.equalsIgnoreCase("rathaus")) {
			return "house";
		}
		if (neubau.equalsIgnoreCase("bank")) {
			return "bank";
		}
		if (neubau.equalsIgnoreCase("bahnhof")) {
			return "minecart";
		}
		if (neubau.equalsIgnoreCase("shop")) {
			return "cart";
		}
		if (neubau.equalsIgnoreCase("schiff1")) {
			return "pirate flag";
		}
		if (neubau.equalsIgnoreCase("schiff2")) {
			return "pirate flag";
		}
		if (neubau.equalsIgnoreCase("schiff3")) {
			return "pirate flag";
		}
		if (neubau.equalsIgnoreCase("hafen")) {
			return "anchor";
		}
		if (neubau.equalsIgnoreCase("leuchtturm")) {
			return "light house";
		}
		if (neubau.equalsIgnoreCase("kirche")) {
			return "church";
		}
		if (neubau.equalsIgnoreCase("gericht")) {
			return "scales";
		}
		if (neubau.equalsIgnoreCase("polizei")) {
			//ICON MACHEN
			return "shield";
		}
		if (neubau.equalsIgnoreCase("burg")) {
			return "tower";
		}
		if (neubau.equalsIgnoreCase("bibliothek")) {
			return "bookshelf";
		}
		if (neubau.equalsIgnoreCase("hotel")) {
			return "building";
		}
		if (neubau.equalsIgnoreCase("knast")||neubau.equalsIgnoreCase("gefängnis") || neubau.equalsIgnoreCase("gefaengnis")) {
			return "tower";
		}
		if (neubau.equalsIgnoreCase("feuerwehr")) {
			return "fire";
		}
		if (neubau.equalsIgnoreCase("krankenhaus")) {
			//ICON MACHEN
			return "heart";
		}
		if (neubau.equalsIgnoreCase("palast")) {
			return "king";
		}
		if (neubau.equalsIgnoreCase("denkmal")) {
			return "pin";
		}
		if (neubau.equalsIgnoreCase("turnierplatz")) {
			return "gold medal";
		}
		if (neubau.equalsIgnoreCase("magierturm")) {
			//ICON MACHEN
			return "tower";
		}
		if (neubau.equalsIgnoreCase("kloster")) {
			return "church";
		}
		if (neubau.equalsIgnoreCase("museum")) {
			return "theater";
		}
		if (neubau.equalsIgnoreCase("casino")) {
			//ICON MACHEN
			return "coins";
		}
		if (neubau.equalsIgnoreCase("schlachthaus")) {
			//ICON MACHEN
			return "skull";
		}
		if (neubau.equalsIgnoreCase("tankstelle")) {
			//ICON MACHEN
			return "flower";
		}
		if (neubau.equalsIgnoreCase("supermarkt")) {
			return "cart";
		}
		if (neubau.equalsIgnoreCase("mall")) {
			return "cart";
		}
		if (neubau.equalsIgnoreCase("tierheim")) {
			return "dog";
		}
		if (neubau.equalsIgnoreCase("schuldturm")) {
			//ICON MACHEN
			return "tower";
		}
		if (neubau.equalsIgnoreCase("pranger")) {
			return "skull";
		}
		if (neubau.equalsIgnoreCase("baecker") || neubau.equalsIgnoreCase("baeckerei")) {
			//ICON MACHEN
			return "sun";
		}
		if (neubau.equalsIgnoreCase("garnison")) {
			return "shield";
		}
		if (neubau.equalsIgnoreCase("alchemist")) {
			return "bomb";
		}
		if (neubau.equalsIgnoreCase("werkzeugschmied")) {
			return "hammer";
		}
		if (neubau.equalsIgnoreCase("waffenschmied")) {
			//ICON MACHEN
			return "hammer";
		}
		if (neubau.equalsIgnoreCase("ruestungsschmied")) {
			//ICON MACHEN
			return "hammer";
		}
		if (neubau.equalsIgnoreCase("fabrik1")) {
			return "factory";
		}
		if (neubau.equalsIgnoreCase("fabrik2")) {
			return "factory";
		}
		if (neubau.equalsIgnoreCase("fabrik3")) {
			return "factory";
		}
		if (neubau.equalsIgnoreCase("weltwunder1")) {
			return "camera";
		}
		if (neubau.equalsIgnoreCase("weltwunder2")) {
			return "camera";
		}
		if (neubau.equalsIgnoreCase("weltwunder3")) {
			return "camera";
		}
		if (neubau.equalsIgnoreCase("weltwunder4")) {
			return "camera";
		}
		if (neubau.equalsIgnoreCase("weltwunder5")) {
			return "camera";
		}
		if (neubau.equalsIgnoreCase("weltwunder6")) {
			return "camera";
		}
		if (neubau.equalsIgnoreCase("weltwunder7")) {
			return "camera";
		}
		return "default";
	}
	










	
}
