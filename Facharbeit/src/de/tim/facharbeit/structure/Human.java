package de.tim.facharbeit.structure;

import java.awt.Color;
import java.awt.Graphics;
import java.util.Random;

import javax.swing.JLabel;

import de.tim.facharbeit.Frame;

public class Human extends Structure {

	// variables//
	private House home;
	private Color blobColor;
	private Health health;
	private Character character;

	// constructor//
	public Human(Point point, House home, Health health) {
		super(point, 10, 10);
		this.home = home;
		setHealth(health);
	}

	// tmp

	// get & set//
	public House getHome() {
		return home;
	}

	public void setHome(House home) {
		this.home = home;
	}

	public void getBlobHealth() {

	}

	// others//
	public void setHealth(Health health) {
		this.health = health;
		switch (this.health) {
		case HEALTHY: {
			this.blobColor = Color.green;
			break;
		}
		case INFECTED: {
			this.blobColor = Color.red;
			break;
		}
		case IMUNE: {
			this.blobColor = Color.blue;
			break;
		}
		case DEAD: {
			this.blobColor = Color.gray;
			break;
		}
		default:
			throw new IllegalArgumentException("Unexpected health value: " + this.health);
		}
		Frame.instance.update();
	}
	


	public void setCharacter(Character character) {
		this.character = character;
		switch (this.character) {
		case BEDACHT: {
			;
		}
		case NORMAL: {
			;
		}
		case VERWEIGERER: {
			;
		}
		default:
			throw new IllegalArgumentException("Unexpected character value: " + this.character);
		}
		// Frame.instance.update();
	}

	public void moveToX(int deltaX) {
		
	}

	public void moveToY(int deltaY) {

	}
	
	public void nextStep() {
		moveInHouse();
	}
	
	public void moveToEntrance() {

	}
	
	public void moveInHouse() {
		Random random = new Random();
		int x = random.nextInt(5) - 2;
		int y = random.nextInt(5) - 2;
		int newX = getX() + x;
		int newY = getY() + y;
		
		if (newX < home.getX() || newX > home.getX() + home.width) {
			newX = getX() - x *2;
		}
		if (newY < home.getY() || newY > home.getY() + home.height) {
			newY = getY() - y *2;
		}
		setPoint(new Point(newX, newY));
		
	}
	
	public int distanceTo(Point point) {
		return (int) Math.sqrt((this.getX() - point.getX())^2 + (this.getY() - point.getY())^2);
	}
	

	// draw & toString//
	@Override
	public void draw(Graphics graphics) {
		graphics.setColor(blobColor);
		graphics.fillOval(point.getX(), point.getY(), width, height);

	}



}
