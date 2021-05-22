package de.tim.facharbeit.structure;

import java.awt.Color;
import java.awt.Graphics;

public class Garden extends Structure{
	private Block block;												//in welchem Block liegt er

	public Garden(Point point, int width, int height, Block block) {	//constructor
		super(point, width, height);
		this.block = block;
	}

	@Override
	public void draw(Graphics graphics) {								//wie wird ein Garten gemalt	
		graphics.setColor(new Color(34, 177, 76));						//grüne farbe
		graphics.fillRect(point.getX(),point.getY() , width, height);	//gefülltes Rechteck
		
	}

}
