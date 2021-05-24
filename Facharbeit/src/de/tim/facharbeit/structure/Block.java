package de.tim.facharbeit.structure;

import java.awt.Color;
import java.awt.Graphics;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import de.tim.facharbeit.Main;
import de.tim.facharbeit.Variables;
import de.tim.facharbeit.frames.SimulationFrame;
import de.tim.facharbeit.structure.streets.Street;
																						//TODO noch nicht fertig
public class Block extends Structure { 													// House stammt von Structure ab
	
	public List<House> houses = new ArrayList<>();										//H�user die in diesem Block liegen
	private List<Garden> gardens = new ArrayList<>();									//G�rten die in diesem Block liegen

	public List<Street> surroundingStreets = new ArrayList<>();							//von welchen Stra�en ist der Block umschlossen

	int standardDimensions = Variables.minimumDistance - 10;

	public Block(Point point, int width, int height, List<Street> surroundingStreets) {	//constructor
		super(point, width, height);
		this.surroundingStreets = surroundingStreets;
		spawnHouses();																	//erzeugt die H�user und G�rten
	}

	public void spawnHouses() {															//erzeugt die H�user und G�rten
		int nW = this.width / standardDimensions;										//wieviele H�user passen in die Breite
		int nH = this.height / standardDimensions;										//wieviele H�user passen in die H�he

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

		for (int i = 0; i < nW; i++) {																			//erzeugung der punkte der verschiedenen H�user
			for (int j = 0; j < nH; j++) {
				Point point = new Point(this.getX() + i * houseWidth, this.getY() + j * houseHeight);

				if (i == 0) {
					House newHouse = new House(point, houseWidth, houseHeight, this, HouseOrientation.LEFT);
					houses.add(newHouse);
					Main.structures.add(newHouse);
				} else if (j == 0) {
					House newHouse = new House(point, houseWidth, houseHeight, this, HouseOrientation.UP);
					houses.add(newHouse);
					Main.structures.add(newHouse);
				} else if (nW - i == 1) {
					House newHouse = new House(point, houseWidth, houseHeight, this, HouseOrientation.RIGHT);
					houses.add(newHouse);
					Main.structures.add(newHouse);
				} else if (nH - j == 1) {
					House newHouse = new House(point, houseWidth, houseHeight, this, HouseOrientation.DOWN);
					houses.add(newHouse);
					Main.structures.add(newHouse);
				} else {
					Garden garden = new Garden(point, houseWidth, houseHeight, this);								//wenn nicht mach einen Garten aus diesem Feld
					gardens.add(garden);
					Main.structures.add(garden);
				}
			}
		}
	}

	@Override
	public void draw(Graphics graphics) {
		graphics.setColor(Color.lightGray);
		graphics.drawRect(point.getX(), point.getY(), width, height);
	}
}