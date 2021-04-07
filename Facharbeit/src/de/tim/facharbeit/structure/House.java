package de.tim.facharbeit.structure;

import java.awt.Color;
import java.awt.Graphics;
import java.util.ArrayList;
import java.util.List;

import de.tim.facharbeit.Main;
import de.tim.facharbeit.structure.Human.Health;

public class House extends Structure{
	
	private List<Human> humans = new ArrayList<>();
	
	private Block block;

	public House(Point point, int width, int height) {
		super(point, width, height);
		spawnBlobs();
	}
	
	
	private void spawnBlobs() {
		humans.add(new Human(new Point(point.getX() + 10, point.getY() + 10),  this, Health.HEALTHY));
		humans.add(new Human(new Point(point.getX() + 20, point.getY() + 30), this, Health.DEAD));
		humans.add(new Human(new Point(point.getX() + 30, point.getY() + 10), this, Health.IMUNE));
		Main.structures.addAll(humans);
	}
	

	@Override
	public void draw(Graphics graphics) {
		graphics.setColor(new Color(112, 146, 190));
		graphics.fillRect(point.getX(),point.getY() , width, height);
		
	}
	
	public Block getBlock() {
		return block;
	}


	public void setBlock(Block block) {
		this.block = block;
	}



}
