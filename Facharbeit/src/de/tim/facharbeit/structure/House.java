package de.tim.facharbeit.structure;

import java.awt.Color;
import java.awt.Graphics;
import java.util.ArrayList;
import java.util.List;
import de.tim.facharbeit.Main;
import de.tim.facharbeit.structure.Blob.Health;

public class House extends Structure {						//House stammt von Structure ab

	private List<Blob> blobs = new ArrayList<>();

	public House(int x, int y) {
		super(x, y, 50, 50);
		spawnBlobs();
	}
	
	private void spawnBlobs() {
		blobs.add(new Blob(x + 10, y + 10, this, Health.HEALTHY));
		blobs.add(new Blob(x + 20, y + 30, this, Health.INFECTED));
		blobs.add(new Blob(x + 30, y + 10, this, Health.DEAD));
		Main.structures.addAll(blobs);
	}

	@Override
	public void draw(Graphics graphics) {
		graphics.setColor(Color.black);
		graphics.drawRect(x, y, width, height);
	}
}
