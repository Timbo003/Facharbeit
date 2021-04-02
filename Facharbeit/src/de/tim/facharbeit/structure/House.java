package de.tim.facharbeit.structure;

import java.awt.Color;
import java.awt.Graphics;
import java.util.ArrayList;
import java.util.List;
import de.tim.facharbeit.Main;
import de.tim.facharbeit.structure.Blob.Health;

public class House extends Structure {						//House stammt von Structure ab

	private List<Blob> blobs = new ArrayList<>();

	public House(Point point, int width, int hight ) {
		super(point, width, hight);
		spawnBlobs();
	}
	
	private void spawnBlobs() {
		blobs.add(new Blob(new Point(point.getX() + 10, point.getY() + 10),  this, Health.HEALTHY));
		blobs.add(new Blob(new Point(point.getX() + 20, point.getY() + 30), this, Health.DEAD));
		blobs.add(new Blob(new Point(point.getX() + 30, point.getY() + 10), this, Health.IMUNE));
		Main.structures.addAll(blobs);
	}

	@Override
	public void draw(Graphics graphics) {
		graphics.setColor(Color.black);
		graphics.drawRect(point.getX(),point.getY() , width, height);
	}
}
