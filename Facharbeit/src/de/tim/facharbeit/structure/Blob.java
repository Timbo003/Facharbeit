package de.tim.facharbeit.structure;

import java.awt.Color;
import java.awt.Graphics;

public class Blob extends Structure{

	public enum Health
	{
		HEALTHY, INFECTED, IMUNE, DEAD
	}
	
	public enum Characteristics 
	{
	 Stubenhocker, Partymacher, Einkäufer
	}
	
	private House home;
	private Color blobColor;
	private Health blobHealth;
	private Characteristics character;
	

	public Blob(int x, int y, House home, Health blobHealth) {
		super(x, y, 10, 10);
		this.home = home;
		this.blobHealth = blobHealth;

//		switch (this.blobHealth) {
//		case Health.HEALTHY: {this.blobColor = Color.green;}
//		default:
//			throw new IllegalArgumentException("Unexpected value: " + this.blobHealth);
//		}
		
		if (this.blobHealth == Health.HEALTHY) {
		this.blobColor = Color.green;
		}
		else if(this.blobHealth == Health.INFECTED){
			this.blobColor = Color.red;
		}
		else if(this.blobHealth == Health.IMUNE){
			this.blobColor = Color.blue;
		}else {
			this.blobColor = Color.gray;
		}
	}

	
	
	@Override
	public void draw(Graphics graphics) {
		graphics.setColor(blobColor);
		graphics.fillOval(x, y, width, height);
		
	}

	public House getHome() {
		return home;
	}

	public void setHome(House home) {
		this.home = home;
	}
}
