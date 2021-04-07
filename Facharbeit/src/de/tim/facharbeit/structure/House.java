package de.tim.facharbeit.structure;

import java.awt.Color;
import java.awt.Graphics;
import java.util.ArrayList;
import java.util.List;

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
		humans.add(new Human(new Point(point.getX() + 10, point.getY() + 10), this, Health.HEALTHY));
		humans.add(new Human(new Point(point.getX() + 20, point.getY() + 30), this, Health.INFECTED));
		humans.add(new Human(new Point(point.getX() + 30, point.getY() + 10), this, Health.IMUNE));
		Main.structures.addAll(humans);
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
