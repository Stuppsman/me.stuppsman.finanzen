package me.stuppsman.finanzen;

import java.util.ArrayList;

import org.bukkit.Location;
import org.bukkit.World;

public class GS {
	
	Location A, D, Schild;
	String besitzer, ID, status;
	ArrayList<String> baurecht, redstonerecht, generalschluessel;
	double preis;
	
	public GS(String ID, Location A, Location D, String besitzer, ArrayList<String> baurecht, ArrayList<String> redstonerecht, ArrayList<String> generalschluessel, double preis, Location Schild, String status) {
		this.status = status;
		this.ID = ID;
		this.Schild = Schild;
		World world = A.getWorld();
		int X1 = A.getBlockX();
		int Z1 = A.getBlockZ();
		int X2 = D.getBlockX();
		int Z2 = D.getBlockZ();
		int maxZ = (Z1 > Z2) ? Z1 : Z2;
		int maxX = (X1 > X2) ? X1 : X2;
		int minZ = (Z1 > Z2) ? Z2 : Z1;
		int minX = (X1 > X2) ? X2 : X1;
		this.A = new Location(world, minX, 64, minZ);
		this.D = new Location(world, maxX, 64, maxZ);
		this.besitzer = besitzer;
		this.baurecht = baurecht;
		this.redstonerecht = redstonerecht;
		this.generalschluessel = generalschluessel;
		this.preis = preis;
		Finanzen.GSSchilder.put(Schild, ID);
		Finanzen.grundstuecke.put(ID, this);
	}
	
	public GS(String ID, Location loc1, Location loc2, double preis, Location Schild) {
		this.ID = ID;
		this.preis = preis;
		this.Schild = Schild;
		this.besitzer = "tba";
		World world = loc1.getWorld();
		int X1 = loc1.getBlockX();
		int Z1 = loc1.getBlockZ();
		int X2 = loc2.getBlockX();
		int Z2 = loc2.getBlockZ();
		int maxZ = (Z1 > Z2) ? Z1 : Z2;
		int maxX = (X1 > X2) ? X1 : X2;
		int minZ = (Z1 > Z2) ? Z2 : Z1;
		int minX = (X1 > X2) ? X2 : X1;
		this.A = new Location(world, minX, 64, minZ);
		this.D = new Location(world, maxX, 64, maxZ);
		this.baurecht = new ArrayList<String>();
		this.redstonerecht = new ArrayList<String>();
		this.generalschluessel = new ArrayList<String>();
		this.status = "zum Verkauf freigegeben";
		Finanzen.GSSchilder.put(Schild, ID);
		
	}
	public GS(String ID, Location loc1, Location loc2, String besitzer, Location Schild) {
		this.ID = ID;
		this.besitzer = besitzer;
		this.Schild = Schild;
		this.preis = new Double(0);
		World world = loc1.getWorld();
		int X1 = loc1.getBlockX();
		int Z1 = loc1.getBlockZ();
		int X2 = loc2.getBlockX();
		int Z2 = loc2.getBlockZ();
		int maxZ = (Z1 > Z2) ? Z1 : Z2;
		int maxX = (X1 > X2) ? X1 : X2;
		int minZ = (Z1 > Z2) ? Z2 : Z1;
		int minX = (X1 > X2) ? X2 : X1;
		this.A = new Location(world, minX, 64, minZ);
		this.D = new Location(world, maxX, 64, maxZ);
		this.baurecht = new ArrayList<String>();
		this.redstonerecht = new ArrayList<String>();
		this.generalschluessel = new ArrayList<String>();
		this.status = "Baustelle";
		Finanzen.GSSchilder.put(Schild, ID);
		
	}
	public boolean hatBaurecht(String name) {
		if (status.equalsIgnoreCase("Baustelle") || status.equalsIgnoreCase("Wohnhaus") || status.equalsIgnoreCase("zum Verkauf freigegeben")) {
			return (isGSBesitzer(name) || this.baurecht.contains(name));
		}
		Stadt stadt = Finanzen.getStadtAtLoc(Schild);
		
		return (stadt.getBesitzer().equalsIgnoreCase(name) || stadt.getRat().contains(name));
		}
	public boolean hatRedstoneRecht(String name) {
		return (isGSBesitzer(name) || redstonerecht.contains(name));
	}
	public boolean hatSchluessel(String name) {
		return (isGSBesitzer(name) || generalschluessel.contains(name));
	}
	public boolean isGSBesitzer(String name) {
		return this.besitzer.equalsIgnoreCase(name);
	}
	public void setSchildLoc(Location loc) {
		this.Schild = loc;
	}
	public void setBesitzer(String name) {
		this.besitzer = name;
	}
	public void setPreis(double preis) {
		this.preis = preis;
	}
	public boolean isOnGS(Location loc) {
		int hx = loc.getBlockX();
		int hz = loc.getBlockZ();
		if (hx < A.getBlockX()) return false;
		if (hx > D.getBlockX()) return false;
		if (hz < A.getBlockZ()) return false;
		if (hz > D.getBlockZ()) return false;
		return true;
	}
	
	public String getID() {
		return ID;
	}
	public Location getA() {
		return A;
	}
	public Location getD() {
		return D;
	}
	public Location getSchildLoc() {
		return Schild;
	}
	public String getBesitzer() {
		return besitzer;
	}
	public ArrayList<String> getBauer() {
		return baurecht;
	}
	public ArrayList<String> getRedstonerecht() {
		return redstonerecht;
	}
	public ArrayList<String> getSchluesselkinder() {
		return generalschluessel;
	}
	public double getPreis() {
		return preis;
	}
	public void addBauer(String name) {
		baurecht.add(name);
	}
	public void addRedstoneRecht(String name) {
		redstonerecht.add(name);
	}
	public void addSchluesselkind(String name) {
		generalschluessel.add(name);
	}
	public void remBauer(String name) {
		baurecht.remove(name);
	}
	public void remRedstoneRecht(String name) {
		redstonerecht.remove(name);
	}
	public void remSchluesselkind(String name) {
		generalschluessel.remove(name);
	}
	public void clearBauer() {
		baurecht.clear();
	}
	public void clearRedstonerecht() {
		redstonerecht.clear();
	}
	public void clearSchluesselkinder() {
		generalschluessel.clear();
	}

	public String getStatus() {
		return status;
	}
	public void setStatus(String s) {
		this.status = s;
	}
	public boolean schneidet(Location A, Location D) {
		Location B = new Location(A.getWorld(), A.getBlockX(), 64, D.getZ());
		Location C = new Location(A.getWorld(), D.getBlockX(), 64, A.getZ());
		if (isOnGS(A) || isOnGS(B) || isOnGS(C) || isOnGS(D)) {
			return true;
		}
		return false;
	}
	public boolean schneidet(GS gs) {
		Location A = gs.getA();
		Location D = gs.getD();
		Location B = new Location(A.getWorld(), A.getBlockX(), 64, D.getZ());
		Location C = new Location(A.getWorld(), D.getBlockX(), 64, A.getZ());
		if (isOnGS(A) || isOnGS(B) || isOnGS(C) || isOnGS(D)) {
			return true;
		}
		return false;
	}
	
	
}
