package de.tim.facharbeit.structure;

import java.awt.Color;
import java.awt.Graphics;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import de.tim.facharbeit.Frame;
import de.tim.facharbeit.Main;
import de.tim.facharbeit.structure.streets.Street;

public class Block extends Structure { // House stammt von Structure ab

	private List<House> houses = new ArrayList<>();
	public List<Street> surroundingStreets = new ArrayList<>();
	
	int standardDimensions = Main.minimumDistance - 10;

	public Block(Point point, int width, int height, List<Street> surroundingStreets) {
		super(point, width, height);
		this.surroundingStreets = surroundingStreets;
		spawnHouses();
	}

	public void spawnHouses() {
		int nW = this.width / standardDimensions;
		int nH = this.height / standardDimensions;

		System.out.println("\n");
		System.out.println();
		System.out.println("nW: " + nW);
		System.out.println("nH: " + nH);

		int houseWidth = 10;
		int houseHeight = 10;

		if (nW < 1) {
			houseWidth = this.getWidth();
		} else {
			houseWidth = this.getWidth() / nW;
		}
		if (nH < 1) {
			houseHeight = this.getHeight();
		} else {
			houseHeight = this.getHeight() / nH;
		}

		System.out.println("houseWidth: " + houseWidth);
		System.out.println("houseHeight: " + houseHeight);
		System.out.println("blockWidth: " + this.width);
		System.out.println("blockHeight: " + this.height);

		for (int i = 0; i < nW; i++) {
			for (int j = 0; j < nH; j++) {
				Point point = new Point(this.getX() + i * houseWidth, this.getY() + j * houseHeight);
				houses.add(new House(point, houseWidth, houseHeight, this));
				Main.structures.addAll(houses);
			}
		}

		if (!(nW == 0 && nH == 0)) {
			if (nW == 0) {
				for (int i = 0; i < nH; i++) {
					Point point = new Point(this.getX(), this.getY() + i * houseHeight);
					houses.add(new House(point, this.width, houseHeight, this));
					Main.structures.addAll(houses);
				}
			} else if (nH == 0) {
				for (int i = 0; i < nW; i++) {
					Point point = new Point(this.getX()+ i * houseWidth, this.getY());
					houses.add(new House(point, houseWidth, this.height, this));
					Main.structures.addAll(houses);
				}
			}
		} else {
			Point point = new Point(this.getX(), this.getY());
			houses.add(new House(point, this.width, this.height, this));
			Main.structures.addAll(houses);
		}
		
		for (House house : houses) {
			System.out.println(house.isHouseNearStreet());
		}
	}

	@Override
	public void draw(Graphics graphics) {
		graphics.setColor(Color.lightGray);
		graphics.fillRect(point.getX(), point.getY(), width, height);
	}
}