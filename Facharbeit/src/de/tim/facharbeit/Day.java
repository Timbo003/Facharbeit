package de.tim.facharbeit;


public class Day {
	int date;
	
	int infected;
	int imune;
	int healthy;
	int dead;
	
	public Day(int date) {
		this.date = date;
		Variables.days.add(this);
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
	
	@Override
	public String toString() {
		return "date: " + date + "  infected: " + infected + "  healthy: " + healthy + "  imune: " + imune + "  dead: " + dead + "\n";
	}
}
