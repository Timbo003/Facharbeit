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

	public List<House> houses = new ArrayList<>();
	private List<Garden> gardens = new ArrayList<>();

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

		for (int i = 0; i < nW; i++) {
			for (int j = 0; j < nH; j++) {
				Point point = new Point(this.getX() + i * houseWidth, this.getY() + j * houseHeight);

				if (i == 0) {
					houses.add(new House(point, houseWidth, houseHeight, this, HouseOrientation.LEFT));
				} else if (j == 0) {
					houses.add(new House(point, houseWidth, houseHeight, this, HouseOrientation.UP));
				} else if (nW - i == 1) {
					houses.add(new House(point, houseWidth, houseHeight, this, HouseOrientation.RIGHT));
				} else if (nH - j == 1) {
					houses.add(new House(point, houseWidth, houseHeight, this, HouseOrientation.DOWN));
				} else {
					gardens.add(new Garden(point, houseWidth, houseHeight, this));
				}
				Main.structures.addAll(houses);
				Main.structures.addAll(gardens);
			}
		}
	}

	@Override
	public void draw(Graphics graphics) {
		graphics.setColor(Color.lightGray);
		graphics.drawRect(point.getX(), point.getY(), width, height);
	}
}