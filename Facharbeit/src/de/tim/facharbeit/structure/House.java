package de.tim.facharbeit.structure;

import java.awt.Color;
import java.awt.Graphics;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;

import de.tim.facharbeit.Main;

public class House extends Structure {

	// variables//
	private List<Human> humans = new ArrayList<>();
	public Block block;
	public Entrance entrance;
	public HouseOrientation orientation;

	// constructor//
	public House(Point point, int width, int height, Block block, HouseOrientation orientation) {
		super(point, width, height);
		this.block = block;
		this.orientation = orientation;
		spawnBlobs();
		createEntrance();
	}

	// others//
	private void spawnBlobs() {
		Random random = new Random();
		int randomNum = ThreadLocalRandom.current().nextInt(1, 4 + 1);
		for (int i = 0; i < randomNum; i++) {
			
			humans.add(new Human(new Point(point.getX() + (width/ 2) + i *10, point.getY() + (height/ 2) + i *10), this, Health.HEALTHY));
			
		}
		Main.structures.addAll(humans);		
//		
//		humans.add(new Human(new Point(point.getX() + (width/ 2)+20, point.getY() + (height/ 2)), this, Health.INFECTED));
//		humans.add(new Human(new Point(point.getX() + (width/ 2)-20, point.getY() + (height/ 2)), this, Health.IMUNE));
		
	}

	private void createEntrance() {
		Entrance entrance = new Entrance(new Point(this.getX() + orientation.getX(this.getWidth()),
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
