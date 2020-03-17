package me.theremixpvp.ckitpvp;

import java.util.ArrayList;

import org.bukkit.inventory.Inventory;

public class PData {
	
	private final String name;
	private int credits;
	private String kit;
	private int kills;
	private int deaths;
	private final ArrayList<String> unlockedkits = new ArrayList<>();
	private Inventory bank;
	
	public PData(String name) {
		this.name = name;
	}
	
	public String name() {
		return this.name;
	}
	
	public int credits() {
		return this.credits;
	}
	
	public void setCredits(int credits) {
		this.credits = credits;
	}
	
	public int kills() {
		return this.kills;
	}
	
	public void setKills(int kills) {
		this.kills = kills;
	}
	
	public int deaths() {
		return this.deaths;
	}
	
	public void setDeaths(int deaths) {
		this.deaths = deaths;
	}
	
	public ArrayList<String> unlockedkits() {
		return this.unlockedkits;
	}
	
	public void addkit(String kit) {
		this.unlockedkits.add(kit);
	}
	
	public String getKit() {
		return this.kit;
	}
	
	public void setKit(String kit) {
		this.kit = kit;
	}
	
	public Inventory getBank() {
		return this.bank;
	}
	
	public void setBank(Inventory bank) {
		this.bank = bank;
	}

}
