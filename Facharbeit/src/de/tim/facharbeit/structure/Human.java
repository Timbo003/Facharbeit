package de.tim.facharbeit.structure;

import java.awt.Color;
import java.awt.Graphics;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Random;

import javax.swing.JLabel;

import de.tim.facharbeit.Main;
import de.tim.facharbeit.dijkstra.DijkstraManager;
import de.tim.facharbeit.dijkstra.DijkstraPoint;
import de.tim.facharbeit.frames.SimulationFrame;
import de.tim.facharbeit.structure.manager.HumanManager;

public class Human extends Structure {
	// variables//
	private House home;							//Zuhause
	public House targetHouse;					//Ziel Haus
	public House currentHouse;					//momentanes Haus
	public Color blobColor;						//Farbe

	public int timeInHouse;						//wie iel hat er sich schon in seinem Haus bewegt
	public int minMovesInHouse;					//wieviel muss er sich in einem Haus bewegen

	public int visited;							//wie viele Häuser hat er schon besucht		
	public int allowedVisits;					//wie viele Häuser darf er besuchen
	public int daysInfected = 0;				//wielange ist er schon infiziert
	public boolean deathCheck;					//wurde getestet ob er stirbt
	public boolean isWearingMask = false;		//trägt er eine Maske

	public Health health = null;				
	public Personality personality;

	public List<DijkstraPoint> path = new ArrayList<>();			//Pfad zum Zielhaus
	public List<Human> infectionChecked = new ArrayList<>();		//mit wem wurde er alle auf der straße schon getestet

	public int pathIndex = 0;
	public int speed = new Random().nextInt(7) + 2;	//seine Geschwindigkeit

	// constructor//
	public Human(Point point, House home, Health health) {
		super(point, 10, 10);
		this.home = home;
		this.currentHouse = home;
		setHealth(health);
		Random random = new Random();
		this.minMovesInHouse = random.nextInt(30) + 20;
		this.timeInHouse = 0;
	}

	// get & set//
	public House getHome() {
		return home;
	}

	public void setHome(House home) {
		this.home = home;
	}

	// others//
	public boolean isHumanAllowdToWalk() {
		return allowedVisits > visited;
	}

	public boolean walkStep() {								//nächster DijPoint zum Ziel
		if (path.contains(null)) {
			//System.out.println(path);
		}else {
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
		return false;
		
	}

	public void die() {							//stirb
		this.setHealth(Health.DEAD);
	}
	
	public Point nextPointToEntrance() {				//nächster Punkt auf dem Weg zum Hauseingang
		Point entrance = this.currentHouse.entrance.getPoint();
		if (point.equals(entrance)) {
			return null;
		}
		HouseOrientation houseOrientation = home.orientation;
		if (houseOrientation == HouseOrientation.LEFT || houseOrientation == HouseOrientation.RIGHT) {
			return new Point(this.getX(), entrance.getY());
		} else if (houseOrientation == HouseOrientation.UP || houseOrientation == HouseOrientation.DOWN) {
			return new Point(entrance.getX(), this.getY());
		} else {
			System.err.println("no valid HouseOrientation");
		}
		return point;
	}
	
	private Point nextPointOnTheWay(Point target) {//nächster Punkt zum nächsten DijPoint
		int stride = 1;
		if (target.getY() == point.getY()) { // Y gleich muss sich nach links oder rechts bewegen
			if (target.getX() > point.getX()) {
				return new Point(point.getX() + stride, point.getY());
			} else {
				return new Point(point.getX() - stride, point.getY());
			}
		} else if (target.getX() == point.getX()) { // X gleich muss sich nach oben oder unten bewegen
			if (target.getY() > point.getY()) {
				return new Point(point.getX(), point.getY() + stride);
			} else {
				return new Point(point.getX(), point.getY() - stride);
			}
		}
		return target;
	}

	public void setHealth(Health health) {		//passt Farbe der Gesundheit an
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
		for (Human human : Main.getAllHumans()) {
			if (human.health.equals(null)) {
				break;
			}
			SimulationFrame.updateText();
		}
		SimulationFrame.instance.update();
	}

	public void setPersonality(Personality personality) {			//setup der Variabel allowedVisits
		this.personality = personality;
		switch (this.personality) {
		case BEDACHT: {
			this.allowedVisits = 1;
			break;
		}
		case NORMAL: {
			this.allowedVisits = 2;
			break;
		}
		case VERWEIGERER: {
			this.allowedVisits = 4;
			break;
		}
		default:
			throw new IllegalArgumentException("Unexpected character value: " + this.personality);
		}
	}

	public void moveInHouse() {								//bewegt sich im Haus
		this.timeInHouse++;
		Random random = new Random();	
		int x = random.nextInt(3) - 1;						//der neue punkt darf 2 pixel weit entfernt sein
		int y = random.nextInt(3) - 1;

		int newX = getX() + x;
		int newY = getY() + y;

		if (newX < currentHouse.getX() + 10 || newX > currentHouse.getX() + currentHouse.width - 10) {	//wenn der neue Punkt nicht im haus liegt soll er sich wieder zurück bewegen
			newX = getX() - x * 2;																		//der Mensch soll scih ja nicht aus dem Haus buggen
		}
		if (newY < currentHouse.getY() + 10 || newY > currentHouse.getY() + currentHouse.height - 10) {
			newY = getY() - y * 2;
		}
		setPoint(new Point(newX, newY));
	}

	public int distanceTo(Point point) {			//distanz zu einem Punkt
		int distance = (int) Math.sqrt((point.getY() - this.getY()) * (point.getY() - this.getY())
				+ (point.getX() - this.getX()) * (point.getX() - this.getX()));
		return distance;
	}

	public int getPointAmountToWalk() {			//beim wievielten DijPoint auf seinem Weg ist er
		return path.size() - pathIndex;
	}

	public void reset() {						//resetet den Human
		pathIndex = 0;
		path.clear();
		infectionChecked.clear();
	}

	public void moveHome() {					
		this.reset();
	}

	// draw & toString//
	@Override
	public String toString() {
		return "personality: " + this.personality + "health: " + this.health;
	}
	
	@Override
	public void draw(Graphics graphics) {
		if (this.isWearingMask && this.currentHouse == null) {			//maskenträger bekommen einen Punkt in der mitte
			graphics.setColor(blobColor);
			graphics.fillOval(point.getX() - 5, point.getY() - 5, width, height);
			graphics.setColor(Color.gray);
			graphics.fillOval(point.getX()-3 , point.getY()-3 , 5, 5);
		}else {	//kein punkt in der Mitte
			graphics.setColor(blobColor);
			graphics.fillOval(point.getX() - 5, point.getY() - 5, width, height);
		}
	}

}
