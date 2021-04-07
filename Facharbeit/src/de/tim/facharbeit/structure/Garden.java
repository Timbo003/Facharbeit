package de.tim.facharbeit.structure;

import java.awt.Color;
import java.awt.Graphics;

public class Garden extends Structure{
	
	
	
	private Block block;

	public Garden(Point point, int width, int height, Block block) {
		super(point, width, height);
		this.block = block;
	}

	@Override
	public void draw(Graphics graphics) {
		graphics.setColor(new Color(34, 177, 76));
		graphics.fillRect(point.getX(),point.getY() , width, height);
		
	}

}
