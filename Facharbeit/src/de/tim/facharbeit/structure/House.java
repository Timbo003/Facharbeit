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
	public List<Human> humans = new ArrayList<>();
	public Block block;
	public Entrance entrance;
	public HouseOrientation orientation;

	// constructor//
	public House(Point point, int width, int height, Block block, HouseOrientation orientation) {
		super(point, width, height);
		this.block = block;
		this.orientation = orientation;
		createEntrance();
	}

	// others//
	public void spawnBlob() {
		Human human = new Human(new Point(point.getX() + (width/ 2)+20, point.getY() + (height/ 2)), this, Health.HEALTHY);
		humans.add(human);
		Main.structures.add(human);		
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
