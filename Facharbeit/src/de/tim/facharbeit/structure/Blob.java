package de.tim.facharbeit.structure;

import java.awt.Color;
import java.awt.Graphics;

import de.tim.facharbeit.Frame;

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
		setBlobHealth(blobHealth);
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
	
	public void getBlobHealth() {
		
	}
	
	public void setBlobHealth(Health health) {
		this.blobHealth = health;
		if (this.blobHealth == Health.HEALTHY) {
		this.blobColor = Color.green;
		}
		else if(this.blobHealth == Health.INFECTED){
			this.blobColor = Color.red;
		}
		else if(this.blobHealth == Health.IMUNE){
			this.blobColor = Color.blue;
		}else if(this.blobHealth == Health.DEAD){
			this.blobColor = Color.gray;
		}else {
			System.out.println(this.blobHealth + " is not a HealthState");
		}
		
	}
	
	
}
