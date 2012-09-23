package me.stuppsman.finanzen;

import java.util.ArrayList;

import org.bukkit.Bukkit;
import org.bukkit.Location;

public class Stadt {
	boolean lager, fischer, muehle, taverne, markt,  
	kapelle, mine, rathaus, bank, bahnhof, //10
	shop, schiff1, schiff2, hafen, kirche, 
	gericht, mauer, polizei, burg, bibliothek, //20
	hotel, knast, feuerwehr, krankenhaus, palast, 
	denkmal, turnierplatz, magierturm, kloster, museum, //30 
	casino, schlachthaus, tankstelle, supermarkt, mall, 
	tierheim, schuldturm, pranger, baecker, garnison, //40
	alchemist, werkzeugschmied, waffenschmied, ruestungsschmied, fabrik1, 
	fabrik2, fabrik3, weltwunder1, weltwunder2, weltwunder3, //50 
	weltwunder4, weltwunder5, weltwunder6, weltwunder7, schiff3, 
	leuchtturm, reiseburo = false; 
	
	public Stadt(String name, String besitzer, boolean allggsrecht, boolean mobspawn, boolean firespread, boolean allgbaurecht, boolean pvp, double stadtkasse, double steuersatz, ArrayList<String> bewohner, ArrayList<String> ratsmitglieder, ArrayList<Location> lagerkisten, String welt, int xa, int za, int xd, int zd, String status, String gebaeude) {
		String[] booleans = gebaeude.split("#");
		if (booleans.length == 57) {
			lager = Boolean.parseBoolean(booleans[0]);
			fischer = Boolean.parseBoolean(booleans[1]);
			muehle = Boolean.parseBoolean(booleans[2]);
			taverne = Boolean.parseBoolean(booleans[3]);
			markt = Boolean.parseBoolean(booleans[4]);
			
			kapelle = Boolean.parseBoolean(booleans[5]);
			mine = Boolean.parseBoolean(booleans[6]);
			rathaus = Boolean.parseBoolean(booleans[7]);
			bank = Boolean.parseBoolean(booleans[8]);
			bahnhof = Boolean.parseBoolean(booleans[9]);
			//10
			shop = Boolean.parseBoolean(booleans[10]);
			schiff1 = Boolean.parseBoolean(booleans[11]);
			schiff2 = Boolean.parseBoolean(booleans[12]);
			hafen = Boolean.parseBoolean(booleans[13]);
			kirche = Boolean.parseBoolean(booleans[14]);
			
			gericht = Boolean.parseBoolean(booleans[15]);
			mauer = Boolean.parseBoolean(booleans[16]);
			polizei = Boolean.parseBoolean(booleans[17]);
			burg = Boolean.parseBoolean(booleans[18]);
			bibliothek = Boolean.parseBoolean(booleans[19]);
			//20
			hotel = Boolean.parseBoolean(booleans[20]);
			knast = Boolean.parseBoolean(booleans[21]);
			feuerwehr = Boolean.parseBoolean(booleans[22]);
			krankenhaus = Boolean.parseBoolean(booleans[23]);
			palast = Boolean.parseBoolean(booleans[24]);
			
			denkmal = Boolean.parseBoolean(booleans[25]);
			turnierplatz = Boolean.parseBoolean(booleans[26]);
			magierturm = Boolean.parseBoolean(booleans[27]);
			kloster = Boolean.parseBoolean(booleans[28]);
			museum = Boolean.parseBoolean(booleans[29]);
			//30 
			casino = Boolean.parseBoolean(booleans[30]);
			schlachthaus = Boolean.parseBoolean(booleans[31]);
			tankstelle = Boolean.parseBoolean(booleans[32]);
			supermarkt = Boolean.parseBoolean(booleans[33]);
			mall = Boolean.parseBoolean(booleans[34]);
			
			tierheim = Boolean.parseBoolean(booleans[35]);
			schuldturm = Boolean.parseBoolean(booleans[36]);
			pranger = Boolean.parseBoolean(booleans[37]);
			baecker = Boolean.parseBoolean(booleans[38]);
			garnison = Boolean.parseBoolean(booleans[39]);
			//40
			alchemist = Boolean.parseBoolean(booleans[40]);
			werkzeugschmied = Boolean.parseBoolean(booleans[41]);
			waffenschmied = Boolean.parseBoolean(booleans[42]);
			ruestungsschmied = Boolean.parseBoolean(booleans[43]);
			fabrik1 = Boolean.parseBoolean(booleans[44]);
			
			fabrik2 = Boolean.parseBoolean(booleans[45]);
			fabrik3 = Boolean.parseBoolean(booleans[46]);
			weltwunder1 = Boolean.parseBoolean(booleans[47]);
			weltwunder2 = Boolean.parseBoolean(booleans[48]);
			weltwunder3 = Boolean.parseBoolean(booleans[49]);
			//50 
			weltwunder4 = Boolean.parseBoolean(booleans[50]);
			weltwunder5 = Boolean.parseBoolean(booleans[51]);
			weltwunder6 = Boolean.parseBoolean(booleans[52]);
			weltwunder7 = Boolean.parseBoolean(booleans[53]);
			schiff3 = Boolean.parseBoolean(booleans[54]);
			
			leuchtturm = Boolean.parseBoolean(booleans[55]);
			reiseburo = Boolean.parseBoolean(booleans[56]);
		}
		this.name = name;
		this.besitzer = besitzer;
		this.allggsrecht = allggsrecht;
		this.mobspawn = mobspawn;
		if (mauer) {
			this.mobspawn = false;
		}
		this.firespread = firespread;
		if (feuerwehr) {
			this.firespread = false;
		}
		this.allgbaurecht = allgbaurecht;
		this.pvp = pvp;
		if (polizei) {
			this.pvp = false;
		}
		this.stadtkasse = stadtkasse;
		this.steuersatz = steuersatz;
		this.bewohner = bewohner;
		this.ratsmitglieder = ratsmitglieder;
		this.lagerkisten = lagerkisten;
		this.welt = welt;
		int X1 = xa;
		int Z1 = za;
		int X2 = xd;
		int Z2 = zd;
		this.zd = (Z1 > Z2) ? Z1 : Z2;
		this.xd = (X1 > X2) ? X1 : X2;
		this.za = (Z1 > Z2) ? Z2 : Z1;
		this.xa = (X1 > X2) ? X2 : X1;
		this.status = status;
	}
	public Stadt(String name, String besitzer, double preis, Location A, Location D) {
		this.name = name;
		this.besitzer = besitzer;
		this.mobspawn = true;
		this.firespread = true;
		this.allgbaurecht = false;
		this.pvp = true;
		this.stadtkasse = 0;
		this.steuersatz = 0;
		this.bewohner = new ArrayList<String>();
		bewohner.add(besitzer);
		this.ratsmitglieder = new ArrayList<String>();
		this.lagerkisten = new ArrayList<Location>();
		this.welt = A.getWorld().getName();
		int X1 = A.getBlockX();
		int Z1 = A.getBlockZ();
		int X2 = D.getBlockX();
		int Z2 = D.getBlockZ();
		zd = (Z1 > Z2) ? Z1 : Z2;
		xd = (X1 > X2) ? X1 : X2;
		za = (Z1 > Z2) ? Z2 : Z1;
		xa = (X1 > X2) ? X2 : X1;
		this.status = "Gehöft";
	
	}
	
	String name, besitzer, welt, status;
	boolean allggsrecht, allgbaurecht = false;
	boolean mobspawn, firespread, pvp = true;
	double stadtkasse, steuersatz;
	ArrayList<String> bewohner, ratsmitglieder;
	ArrayList<Location> lagerkisten;
	int xa, xd, za, zd;
	

	
	public boolean setNeubau(String neubau, GS gs) {
		if (neubau.equalsIgnoreCase("lager")) {
			gs.setStatus("Lager");
			lager = true;
			gs.setBesitzer(name);
			return true;
		}
		if (neubau.equalsIgnoreCase("fischer")) {
			gs.setStatus("Fischer");
			fischer = true;
			gs.setBesitzer(name);
			return true;
		}
		if (neubau.equalsIgnoreCase("muehle")) {
			gs.setStatus("Mühle");
			muehle= true;
			gs.setBesitzer(name);
			return true;
		}
		if (neubau.equalsIgnoreCase("taverne")) {
			gs.setStatus("Taverne");
			taverne = true;
			gs.setBesitzer(name);
			return true;
		}
		if (neubau.equalsIgnoreCase("markt")) {
			gs.setStatus("Markt");
			markt = true;
			gs.setBesitzer(name);
			return true;
		}
		if (neubau.equalsIgnoreCase("kapelle")) {
			gs.setStatus("Kapelle");
			kapelle = true;
			gs.setBesitzer(name);
			return true;
		}
		if (neubau.equalsIgnoreCase("mine")) {
			gs.setStatus("Mine");
			mine = true;
			gs.setBesitzer(name);
			return true;
		}
		if (neubau.equalsIgnoreCase("rathaus")) {
			gs.setStatus("Rathaus");
			rathaus = true;
			gs.setBesitzer(name);
			return true;
		}
		if (neubau.equalsIgnoreCase("bank")) {
			gs.setStatus("Bank");
			bank = true;
			gs.setBesitzer(name);
			return true;
		}
		if (neubau.equalsIgnoreCase("bahnhof")) {
			gs.setStatus("Bahnhof");
			bahnhof = true;
			gs.setBesitzer(name);
			return true;
		}
		if (neubau.equalsIgnoreCase("shop")) {
			gs.setStatus("Shop");
			shop = true;
			gs.setBesitzer(name);
			return true;
		}
		if (neubau.equalsIgnoreCase("schiff1")) {
			gs.setStatus("Schiff");
			schiff1 = true;
			gs.setBesitzer(name);
			return true;
		}
		if (neubau.equalsIgnoreCase("schiff2")) {
			gs.setStatus("Schiff");
			schiff2= true;
			gs.setBesitzer(name);
			return true;
		}
		if (neubau.equalsIgnoreCase("schiff3")) {
			gs.setStatus("Schiff");
			schiff3= true;
			gs.setBesitzer(name);
			return true;
		}
		if (neubau.equalsIgnoreCase("hafen")) {
			gs.setStatus("Hafen");
			hafen = true;
			gs.setBesitzer(name);
			return true;
		}
		if (neubau.equalsIgnoreCase("kirche")) {
			gs.setStatus("Kirche");
			kirche = true;
			gs.setBesitzer(name);
			return true;
		}
		if (neubau.equalsIgnoreCase("gericht")) {
			gs.setStatus("Gericht");
			gericht = true;
			gs.setBesitzer(name);
			return true;
		}
		if (neubau.equalsIgnoreCase("polizei")) {
			gs.setStatus("Polizei");
			polizei = true;
			gs.setBesitzer(name);
			return true;
		}
		if (neubau.equalsIgnoreCase("burg")) {
			gs.setStatus("Burg");
			burg = true;
			gs.setBesitzer(name);
			return true;
		}
		if (neubau.equalsIgnoreCase("bibliothek")) {
			gs.setStatus("Bibliothek");
			bibliothek = true;
			gs.setBesitzer(name);
			return true;
		}
		if (neubau.equalsIgnoreCase("hotel")) {
			gs.setStatus("Hotel");
			hotel = true;
			gs.setBesitzer(name);
			return true;
		}
		if (neubau.equalsIgnoreCase("knast")||neubau.equalsIgnoreCase("gefängnis") || neubau.equalsIgnoreCase("gefaengnis")) {
			gs.setStatus("Gefängnis");
			knast = true;
			gs.setBesitzer(name);
			return true;
		}
		if (neubau.equalsIgnoreCase("feuerwehr")) {
			gs.setStatus("Feuerwehr");
			firespread = false;
			gs.setBesitzer(name);
			feuerwehr = true;
			return true;
		}
		if (neubau.equalsIgnoreCase("krankenhaus")) {
			gs.setStatus("Krankenhaus");
			krankenhaus = true;
			gs.setBesitzer(name);
			return true;
		}
		if (neubau.equalsIgnoreCase("palast")) {
			gs.setStatus("Palast");
			palast = true;
			gs.setBesitzer(name);
			return true;
		}
		if (neubau.equalsIgnoreCase("denkmal")) {
			gs.setBesitzer(name);
			gs.setStatus("Denkmal");
			denkmal = true;
			return true;
		}
		if (neubau.equalsIgnoreCase("turnierplatz")) {
			gs.setBesitzer(name);
			gs.setStatus("Turnierplatz");
			turnierplatz = true;
			return true;
		}
		if (neubau.equalsIgnoreCase("magierturm")) {
			gs.setBesitzer(name);
			gs.setStatus("Magierturm");
			magierturm = true;
			return true;
		}
		if (neubau.equalsIgnoreCase("kloster")) {
			gs.setBesitzer(name);
			gs.setStatus("Kloster");
			kloster = true;
			return true;
		}
		if (neubau.equalsIgnoreCase("museum")) {
			gs.setBesitzer(name);
			gs.setStatus("Museum");
			museum = true;
			return true;
		}
		if (neubau.equalsIgnoreCase("casino")) {
			gs.setBesitzer(name);
			gs.setStatus("Casino");
			casino = true;
			return true;
		}
		if (neubau.equalsIgnoreCase("schlachthaus")) {
			gs.setBesitzer(name);
			gs.setStatus("Schlachthaus");
			schlachthaus = true;
			return true;
		}
		if (neubau.equalsIgnoreCase("tankstelle")) {
			gs.setBesitzer(name);
			gs.setStatus("Tankstelle");
			tankstelle = true;
			return true;
		}
		if (neubau.equalsIgnoreCase("supermarkt")) {
			gs.setBesitzer(name);
			gs.setStatus("Supermarkt");
			supermarkt = true;
			return true;
		}
		if (neubau.equalsIgnoreCase("mall")) {
			gs.setBesitzer(name);
			gs.setStatus("Mall");
			mall = true;
			return true;
		}
		if (neubau.equalsIgnoreCase("tierheim")) {
			gs.setBesitzer(name);
			gs.setStatus("Tierheim");
			tierheim = true;
			return true;
		}
		if (neubau.equalsIgnoreCase("schuldturm")) {
			gs.setBesitzer(name);
			gs.setStatus("Schuldturm");
			schuldturm = true;
			return true;
		}
		if (neubau.equalsIgnoreCase("pranger")) {
			gs.setBesitzer(name);
			gs.setStatus("Pranger");
			pranger = true;
			return true;
		}
		if (neubau.equalsIgnoreCase("baecker") || neubau.equalsIgnoreCase("baeckerei")) {
			gs.setBesitzer(name);
			gs.setStatus("Bäckerei");
			baecker = true;
			return true;
		}
		if (neubau.equalsIgnoreCase("garnison")) {
			gs.setBesitzer(name);
			gs.setStatus("Garnison");
			garnison = true;
			return true;
		}
		if (neubau.equalsIgnoreCase("alchemist")) {
			gs.setBesitzer(name);
			gs.setStatus("Alchemist");
			alchemist = true;
			return true;
		}
		if (neubau.equalsIgnoreCase("werkzeugschmied")) {
			gs.setBesitzer(name);
			gs.setStatus("Werkzeugschmied");
			werkzeugschmied = true;
			return true;
		}
		if (neubau.equalsIgnoreCase("waffenschmied")) {
			gs.setBesitzer(name);
			gs.setStatus("Waffenschmied");
			waffenschmied = true;
			return true;
		}
		if (neubau.equalsIgnoreCase("ruestungsschmied")) {
			gs.setBesitzer(name);
			gs.setStatus("Rüstungsschmied");
			ruestungsschmied = true;
			return true;
		}
		if (neubau.equalsIgnoreCase("fabrik1")) {
			gs.setBesitzer(name);
			gs.setStatus("Fabrik");
			fabrik1 = true;
			return true;
		}
		if (neubau.equalsIgnoreCase("fabrik2")) {
			gs.setBesitzer(name);
			gs.setStatus("Fabrik");
			fabrik2 = true;
			return true;
		}
		if (neubau.equalsIgnoreCase("fabrik3")) {
			gs.setBesitzer(name);
			gs.setStatus("Fabrik");
			fabrik3 = true;
			return true;
		}
		if (neubau.equalsIgnoreCase("weltwunder1")) {
			gs.setBesitzer(name);
			gs.setStatus("Weltwunder");
			weltwunder1 = true;
			return true;
		}
		if (neubau.equalsIgnoreCase("weltwunder2")) {
			gs.setBesitzer(name);
			gs.setStatus("Weltwunder");
			weltwunder2 = true;
			return true;
		}
		if (neubau.equalsIgnoreCase("weltwunder3")) {
			gs.setBesitzer(name);
			gs.setStatus("Weltwunder");
			weltwunder3 = true;
			return true;
		}
		if (neubau.equalsIgnoreCase("weltwunder4")) {
			gs.setBesitzer(name);
			gs.setStatus("Weltwunder");
			weltwunder4 = true;
			return true;
		}
		if (neubau.equalsIgnoreCase("weltwunder5")) {
			gs.setBesitzer(name);
			gs.setStatus("Weltwunder");
			weltwunder5 = true;
			return true;
		}
		if (neubau.equalsIgnoreCase("weltwunder6")) {
			gs.setBesitzer(name);
			gs.setStatus("Weltwunder");
			weltwunder6 = true;
			return true;
		}
		if (neubau.equalsIgnoreCase("weltwunder7")) {
			gs.setBesitzer(name);
			gs.setStatus("Weltwunder");
			weltwunder7 = true;
			return true;
		}
		if (neubau.equalsIgnoreCase("leuchtturm")) {
			gs.setStatus("Leuchtturm");
			leuchtturm = true;
			gs.setBesitzer(name);
			return true;
		}
		return false;
	}
	public String getStatus() {
		int bewohnerzahl = getBewohner().size();
		status = "Gehöft";
		
		if (bewohnerzahl >= Finanzen.config.getInt("Stadtstufen.Siedlung.minBewohner") && lager && fischer) {
			status = "Siedlung";
		}
		if (bewohnerzahl >= Finanzen.config.getInt("Stadtstufen.Weiler.minBewohner") && muehle && baecker) {
			status = "Weiler";
		}
		if (bewohnerzahl >= Finanzen.config.getInt("Stadtstufen.Ortschaft.minBewohner") && taverne && kapelle && mine) {
			status = "Ortschaft";
		}
		if (bewohnerzahl >= Finanzen.config.getInt("Stadtstufen.Dorf.minBewohner") && markt && bank && schlachthaus) {
			status = "Dorf";
		}
		if (bewohnerzahl >= Finanzen.config.getInt("Stadtstufen.Kleinstadt.minBewohner") && rathaus && feuerwehr && shop && werkzeugschmied && waffenschmied && ruestungsschmied) {
			status = "Kleinstadt";
		}
		if (bewohnerzahl >= Finanzen.config.getInt("Stadtstufen.Stadt.minBewohner") && bank && kirche && mauer && supermarkt && alchemist) {
			status = "Stadt";
		}
		if (bewohnerzahl >= Finanzen.config.getInt("Stadtstufen.Hafenstadt.minBewohner") && status.equalsIgnoreCase("Stadt") && hafen) {
			status = "Hafenstadt";
		}
		if (bewohnerzahl >= Finanzen.config.getInt("Stadtstufen.Grossstadt.minBewohner") && bahnhof && gericht && polizei && knast && bibliothek && fabrik1) {
			status = "Großstadt";
		}
		if (bewohnerzahl >= Finanzen.config.getInt("Stadtstufen.Metropole.minBewohner") && burg  && hotel && krankenhaus && kloster && mall && fabrik2 && tierheim) {
			status = "Metropole";
		}
		if (bewohnerzahl >= Finanzen.config.getInt("Stadtstufen.Koenigreich.minBewohner") && magierturm && garnison && palast && schuldturm  && casino && turnierplatz) {
			status = "Königreich";
		}
		if (bewohnerzahl >= Finanzen.config.getInt("Stadtstufen.Weltstadt.minBewohner") && weltwunder1 && fabrik3 && denkmal && pranger && tankstelle) {
			status = "Weltstadt";
		}
		return status;
	}
	
	public void setMauer() {
		mobspawn = false;
		mauer = true;
	}
	
	public void setStadtname(String name) {
		this.name = name;
	}
	public void setBesitzer(String name) {
		this.besitzer = name;
	}
	public void setLocationA(Location a) {
		xa = a.getBlockX();
		za = a.getBlockZ();
		welt = a.getWorld().getName();
	}
	public void setLocationD(Location d) {
		xd = d.getBlockX();
		zd = d.getBlockZ();
		welt = d.getWorld().getName();
	}
	public void setGrenzen(int xa, int za, int xd, int zd, String world)  {
		this.xa = xa;
		this.za = za;
		this.xd = xd;
		this.zd = zd;
		this.welt = world;
	}
	public Location getA() {
		return new Location (Bukkit.getServer().getWorld(welt), xa, 64, za);
	}
	public Location getD() {
		return new Location (Bukkit.getServer().getWorld(welt), xd, 64, zd);
	}
	
	public boolean isInStadt(Location loc) {
		int hx = loc.getBlockX();
		int hz = loc.getBlockZ();
		if (hx < xa) return false;
		if (hx > xd) return false;
		if (hz < za) return false;
		if (hz > zd) return false;
		return true;
	}
	public boolean isPVPAllowed() {
		return pvp;
	}
	public String getBesitzer() {
		return besitzer;
	}
	public boolean isAllgGSRecht() {
		return allggsrecht;
	}
	public boolean isMobspawnAllowed() {
		return mobspawn;
	}
	public boolean isFirespreadAllowed() {
		return firespread;
	}
	public boolean isAllgBaurecht() {
		return allgbaurecht;
	}
	public void incStadtkasse(double amount) {
		stadtkasse += amount;
		Finanzen.Finanzendb.setValue(name, "konto", stadtkasse);
	}
	public double getKasse() {
		return stadtkasse;
	}
	public double getSteuersatz() {
		return steuersatz;
	}
	public void setSteuersatz(double d) {
		this.steuersatz = d;
	}
	public boolean isBewohner(String name) {
		return bewohner.contains(name);
	}
	public void addBewohner(String name) {
		bewohner.add(name);
	}
	public void remBewohner(String name) {
		bewohner.remove(name);
	}
	public ArrayList<String> getBewohner() {
		return bewohner;
	}
	public ArrayList<String> getRat() {
		return ratsmitglieder;
	}
	public void addRatsmitglied(String name) {
		ratsmitglieder.add(name);
	}
	public void remRatsmitglied(String name) {
		ratsmitglieder.remove(name);
	}
	public String getName() {
		return name;
	}
	public ArrayList<Location> getLagerkisten() {
		return lagerkisten;
	}
	public void addLagerkiste(Location loc) {
		lagerkisten.add(loc);
	}
	public void remLagerkiste(Location loc) {
		lagerkisten.remove(loc);
	}
	public boolean hatBaurecht(String name) {
	
		if (allgbaurecht) {
			return true;
		}
		if (isBewohner(name)) {
			return true;
		}
		return false;
	}
	public boolean hatLagerrecht(String name) {
		if (besitzer.equalsIgnoreCase(name)) {
			return true;
		}
		if (ratsmitglieder.contains(name)) {
			return true;
		}
		return false;
	}
	public boolean hatFeuerwehr() {
		return feuerwehr;
	}
	public int getMaxKisten()  {
		int i = getBewohner().size();
		if (lager) i += Finanzen.config.getInt("Gebaeude.lager.lagerkisten");
		if (hafen) i += Finanzen.config.getInt("Gebaeude.hafen.lagerkisten");
		if (burg) i += Finanzen.config.getInt("Gebaeude.burg.lagerkisten");
		return i;
	}
	public boolean hatLager() {
		return lager;
	}
}
