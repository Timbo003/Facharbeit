package de.tim.facharbeit.structure;

import java.awt.Color;
import java.awt.Graphics;

public class Entrance extends Structure{

	static int width = 10;												//Breite
	static int height = 10;												//Höhe
	House house;														//zu welchem Haus gehört der Eingang
	
	public Entrance(Point point, House house) {							//constructor
		super(point, width, height);
		this.house = house;
	}

	@Override
	public void draw(Graphics graphics) {								//wie wird ein Eingang gemalt
		graphics.setColor(Color.black);									//Farbe schwarz
		graphics.drawRect(point.getX(),point.getY() , width, height);	//malt ein leeren Rechteck
	}

}
