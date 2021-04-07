package de.tim.facharbeit.structure;

import java.awt.Color;
import java.awt.Graphics;
import java.util.ArrayList;
import java.util.List;
import de.tim.facharbeit.Main;
import de.tim.facharbeit.structure.Human.Health;

public class Block extends Structure {						//House stammt von Structure ab

	private List<House> houses = new ArrayList<>();

	public Block(Point point, int width, int height ) {
		super(point, width, height);
		spawnHouses();
	}
	
	private void spawnHouses() {
		houses.add(new House(new Point(point.getX() + 10, point.getY() + 10), width-20, height-20));
		Main.structures.addAll(houses);
	}

	@Override
	public void draw(Graphics graphics) { 
		graphics.setColor(Color.lightGray);
		graphics.fillRect(point.getX(),point.getY() , width, height);
	}
}