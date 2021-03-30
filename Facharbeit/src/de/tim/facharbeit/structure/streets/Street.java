package de.tim.facharbeit.structure.streets;

import java.awt.Color;
import java.awt.Graphics;
import java.util.ArrayList;
import java.util.List;

import de.tim.facharbeit.structure.Point;
import de.tim.facharbeit.structure.Structure;

public abstract class Street extends Structure {

	protected static final int size = 40;
	public static List<Street> allStreets = new ArrayList<>();					//Liste aller Straﬂen
	
	private List<Street> neighbours = new ArrayList<>();						//Liste der anliegenden Straﬂen
	
	protected Street(int x, int y, int width, int height) {
		super(x, y, width, height);
		allStreets.add(this);
//		if (street instanceof VerticalStreet) {}								//testet ob straﬂe vertical (oder horizontal ist)
	}

	@Override
	public void draw(Graphics graphics) {
		graphics.setColor(Color.black);
		graphics.fillRect(x, y, width, height);
	}
	
	
	public abstract boolean checkPoint(Point point);			//funk gibt es in vererbten klassen nicht im parent

	public abstract Point getDistance(int distance);
}
