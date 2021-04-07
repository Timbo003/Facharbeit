package de.tim.facharbeit.structure;

import java.awt.Color;
import java.awt.Graphics;
import java.util.ArrayList;
import java.util.List;

import de.tim.facharbeit.Main;

public class House extends Structure{
	
	//variables//
	private List<Human> humans = new ArrayList<>();
	private Block block;

	
	//constructor//
	public House(Point point, int width, int height) {
		super(point, width, height);
		spawnBlobs();
	}
	
	
	//others//
	private void spawnBlobs() {
		humans.add(new Human(new Point(point.getX() + 10, point.getY() + 10),  this, Health.HEALTHY));
		humans.add(new Human(new Point(point.getX() + 20, point.getY() + 30), this, Health.DEAD));
		humans.add(new Human(new Point(point.getX() + 30, point.getY() + 10), this, Health.IMUNE));
		Main.structures.addAll(humans);
	}
	
	
	//get & set//
	public Block getBlock() {
		return block;
	}

	public void setBlock(Block block) {
		this.block = block;
	}
	
	
	//draw & toString//
	@Override
	public void draw(Graphics graphics) {
		graphics.setColor(new Color(112, 146, 190));
		graphics.fillRect(point.getX(),point.getY() , width, height);
	}
}
