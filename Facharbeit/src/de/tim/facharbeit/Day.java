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
}
