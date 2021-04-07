package de.tim.facharbeit.structure;

import java.awt.Color;
import java.awt.Graphics;

import de.tim.facharbeit.Frame;

public class Human extends Structure{

	public enum Health{
		HEALTHY, INFECTED, IMUNE, DEAD
	}
	
	public enum Characteristics {
	 Stubenhocker, Partymacher, Einkäufer
	}
 
	private House home;
	private Color blobColor;
	private Health blobHealth;
	private Characteristics character;
	

	public Human(Point point, House home, Health blobHealth) {
		super(point, 10, 10);
		this.home = home;
		setBlobHealth(blobHealth);
	}

	
	
	@Override
	public void draw(Graphics graphics) {
		graphics.setColor(blobColor);
		graphics.fillOval(point.getX(), point.getY(), width, height);
		
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
		switch (this.blobHealth) {
		case HEALTHY: {this.blobColor = Color.green;break;}
		case INFECTED: {this.blobColor = Color.red;break;}
		case IMUNE: {this.blobColor = Color.blue;break;}
		case DEAD: {this.blobColor = Color.gray;break;}
		default:
			throw new IllegalArgumentException("Unexpected value: " + this.blobHealth);
		}
		Frame.instance.update();	
	}
	
	
}
