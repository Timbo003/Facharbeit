package de.tim.facharbeit.structure;

import java.awt.Color;
import java.awt.Graphics;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Random;

import javax.swing.JLabel;

import de.tim.facharbeit.dijkstra.DijkstraPoint;
import de.tim.facharbeit.frames.Frame;

public class Human extends Structure {

	// variables//
	private House home;
	private House target;
	private Color blobColor;
	private Health health;
	private Character character;
	public List<DijkstraPoint> path = new ArrayList<>();
	private int pathIndex = 1;
	
	// constructor//
	public Human(Point point, House home, Health health) {
		super(point, 10, 10);
		this.home = home;
		setHealth(health);
	}

	// get & set//
	public House getHome() {
		return home;
	}

	public void setHome(House home) {
		this.home = home;
	}
	

	// others//
	
	public boolean walkStep() {
		Point target = path.get(pathIndex).getPoint();
		Point point = nextPointOnTheWay(target);
		super.setPoint(point);
		if (point.equals(target)) {
			if (++pathIndex >= path.size()) {
				return true;
			}
		}
		return false;
	}
	
	public Point nextPointOnTheWay(Point target) {
		int stride = 1;
		System.out.println(point);
		if (target.getY() == point.getY()) { // Y gleich muss sich nach links oder rechts bewegen
			System.out.println("same y");
			if (target.getX() > point.getX()) { 
				return new Point(point.getX() + stride, point.getY());
			} else { 
				return new Point(point.getX() - stride, point.getY());
			}
		} else if (target.getX() == point.getX()   ) { // X gleich muss sich nach oben oder unten bewegen
			System.out.println("same x");
			if (target.getY() > point.getY()) { 
				return new Point(point.getX() , point.getY() + stride);
			} else { 
				return new Point(point.getX() , point.getY() - stride);
			}
		} else {
			System.err.println("not a fitting point");
		}
		return null;
	}

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

	public void moveToEntrance() {

	}

	public void nextStep() {
		moveInHouse();
	}

	public void moveInHouse() {
		Random random = new Random();
		int x = random.nextInt(5) - 2;
		int y = random.nextInt(5) - 2;
		int newX = getX() + x;
		int newY = getY() + y;

		if (newX < home.getX() + 15 || newX > home.getX() + home.width - 15) {
			newX = getX() - x * 2;
		}
		if (newY < home.getY() + 15 || newY > home.getY() + home.height - 15) {
			newY = getY() - y * 2;
		}
		setPoint(new Point(newX, newY));

	}

	public int distanceTo(Point point) {
		return (int) Math.sqrt((this.getX() - point.getX()) ^ 2 + (this.getY() - point.getY()) ^ 2);
	}

	// draw & toString//
	@Override
	public void draw(Graphics graphics) {
		graphics.setColor(blobColor);
		graphics.fillOval(point.getX(), point.getY(), width, height);

	}

}
