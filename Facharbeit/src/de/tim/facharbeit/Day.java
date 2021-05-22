package de.tim.facharbeit;


public class Day {
	int date;
	
	int infected;							//wie viele Infizierte gab es
	int imune;								//wie viele Imune gab es
	int healthy;							//wie viele Gesunde gab es
	int dead;								//wie viele Tote gab es
	
	int maxAllowedDistance;					//wie weit durfte man sich vom Haus entfernen
	int wearingMasks;						//wie viele haben Masken getragen
	int visitors;							//wie viele Besucher waren zugelassen
	
	public Day(int date) {					//constructor
		this.date = date;
		Variables.days.add(this);
    }
	
	public int getvisitors() {
		return visitors;
	}

	public void setvisitors(int visitors) {
		this.visitors = visitors;
	}

	public int getWearingMasks() {
		return wearingMasks;
	}

	public void setWearingMasks(int wearingMasks) {
		this.wearingMasks = wearingMasks;
	}

	public int getMaxAllowedDistance() {
		return maxAllowedDistance;
	}

	public void setMaxAllowedDistance(int maxAllowedDistance) {
		this.maxAllowedDistance = maxAllowedDistance;
	}

	public int getDate() {
		return date;
	}

	public void setDate(int date) {
		this.date = date;
	}

	public int getInfected() {
		return infected;
	}

	public void setInfected(int infected) {
		this.infected = infected;
	}

	public int getImune() {
		return imune;
	}

	public void setImune(int imune) {
		this.imune = imune;
	}

	public int getHealthy() {
		return healthy;
	}

	public void setHealthy(int healthy) {
		this.healthy = healthy;
	}

	public int getDead() {
		return dead;
	}

	public void setDead(int dead) {
		this.dead = dead;
	}
	
	@Override							//damit man es einfach syso kann 
	public String toString() {
		return "date: " + date + "  infected: " + infected + "  healthy: " + healthy + "  imune: " + imune + "  dead: " + dead + " maxAllowedDistance: " + maxAllowedDistance+ " wearingMasks: " + wearingMasks + " visitors: " + visitors+ "\n";
	}
}
