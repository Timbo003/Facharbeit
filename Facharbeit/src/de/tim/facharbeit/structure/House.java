package de.tim.facharbeit.structure;

import java.awt.Color;
import java.awt.Graphics;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;

import de.tim.facharbeit.Main;
import de.tim.facharbeit.dijkstra.DijkstraManager;
import de.tim.facharbeit.dijkstra.DijkstraPoint;
import de.tim.facharbeit.structure.streets.Street;
import de.tim.facharbeit.structure.streets.StreetOrientation;

public class House extends Structure {

	// variables//
	public List<Human> humans = new ArrayList<>();
	public Block block;
	public Entrance entrance;
	public HouseOrientation orientation;
	public Point pointOnStreet;
	public DijkstraPoint nearestDijkstra;
	

	// constructor//
	public House(Point point, int width, int height, Block block, HouseOrientation orientation) {
		super(point, width, height);
		this.block = block;
		this.orientation = orientation;
		createEntrance();
	}

	// others//
	public DijkstraPoint getDijkstraPoint() {
		boolean found = false;
		Point pointToCheck = null;
		int count = 0;
		Street onThis = null;
		while (!found) {
			System.out.println(count);
			if (orientation == HouseOrientation.UP) {
				pointToCheck = new Point(entrance.getX(), entrance.getY() - count);
			} else if (orientation == HouseOrientation.DOWN) {
				pointToCheck = new Point(entrance.getX(), entrance.getY() + count);
			} else if (orientation == HouseOrientation.LEFT) {
				pointToCheck = new Point(entrance.getX() - count, entrance.getY());
			} else if (orientation == HouseOrientation.RIGHT) {
				pointToCheck = new Point(entrance.getX() + count, entrance.getY());
			}
			for (Street street : Street.streets) {
				if (street.isPointOnStreet(pointToCheck)) {
					found = true;
					onThis = street;
				}
			}
			count++;
		}
		pointOnStreet = pointToCheck;
		DijkstraPoint nearest = null;
		int distance = Integer.MAX_VALUE;

		if (onThis.orientation.equals(StreetOrientation.HORIZONTAL)) {
			for (DijkstraPoint point : DijkstraManager.crossings) {
				if (point.getY() == pointOnStreet.getY()) {
					if (Math.abs(pointOnStreet.getX() - point.getX()) < distance) {
						distance = Math.abs(pointOnStreet.getX() - point.getX());
						nearest = point;
					}
				}
			}
		} else {
			for (DijkstraPoint point : DijkstraManager.crossings) {
				if (point.getX() == pointOnStreet.getX()) {
					if (Math.abs(pointOnStreet.getY() - point.getY()) < distance) {
						distance = Math.abs(pointOnStreet.getY() - point.getY());
						nearest = point;
					}
				}
			}
		}
		System.out.println("entrance point " + entrance.point);
		System.out.println("on street " + pointOnStreet);
		System.out.println("nearest dij " + nearest.point);
		nearestDijkstra = nearest;
		return nearestDijkstra;
	}

	public void spawnBlob() {
		Random random = new Random();

		int x = random.nextInt(width - 20);
		int y = random.nextInt(height - 20);
		Point point = new Point(x + 10 + this.point.getX(), y + 10 + this.point.getY());
//		Human human = new Human(new Point(point.getX() + (width/ 2)+20, point.getY() + (height/ 2)), this, Health.HEALTHY);
		Human human = new Human(point, this, Health.HEALTHY);
		humans.add(human);
		Main.structures.add(human);
	}

	private void createEntrance() {
		this.entrance = new Entrance(new Point(this.getX() + orientation.getX(this.getWidth()),
				this.getY() + orientation.getY(this.getHeight())), this);

		Main.structures.add(entrance);
	}

	// get & set//
	public Block getBlock() {
		return block;
	}

	public void setBlock(Block block) {
		this.block = block;
	}

	// draw & toString//
	@Override
	public void draw(Graphics graphics) {
		graphics.setColor(new Color(112, 146, 190));
		graphics.drawRect(point.getX(), point.getY(), width, height);
	}
}
