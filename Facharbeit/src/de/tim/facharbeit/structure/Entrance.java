package de.tim.facharbeit.structure;

import java.awt.Color;
import java.awt.Graphics;

public class Entrance extends Structure{

	static int width = 10;
	static int height = 10;
	House house;
	
	public Entrance(Point point, House house) {
		super(point, width, height);
		this.house = house;
	}

	@Override
	public void draw(Graphics graphics) {
		graphics.setColor(Color.black);
		graphics.drawRect(point.getX(),point.getY() , width, height);
		
	}

}
