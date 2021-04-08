package de.tim.facharbeit.structure;

import java.awt.Color;
import java.awt.Graphics;

import de.tim.facharbeit.Frame;

public class Human extends Structure{

	//variables//
	private House home;
	private Color blobColor;
	private Health health;
	private Character character;
	
	//constructor//
	public Human(Point point, House home, Health health) {
		super(point, 10, 10);
		this.home = home;
		setBlobHealth(health);
	}
	
	
	//get & set//
	public House getHome() {
		return home;
	}

	public void setHome(House home) {
		this.home = home;
	}
	
	public void getBlobHealth() {
		
	}
	
	public void setBlobHealth(Health health) {
		this.health = health;
		switch (this.health) {
		case HEALTHY: {this.blobColor = Color.green;break;}
		case INFECTED: {this.blobColor = Color.red;break;}
		case IMUNE: {this.blobColor = Color.blue;break;}
		case DEAD: {this.blobColor = Color.gray;break;}
		default:
			throw new IllegalArgumentException("Unexpected value: " + this.health);
		}
		Frame.instance.update();	
	}
	
	
	//draw & toString//
	@Override
	public void draw(Graphics graphics) {
		graphics.setColor(blobColor);
		graphics.fillOval(point.getX(), point.getY(), width, height);
		
	}
	
}
