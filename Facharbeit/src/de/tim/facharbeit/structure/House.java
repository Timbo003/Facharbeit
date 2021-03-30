package de.tim.facharbeit.structure;

import java.awt.Color;
import java.awt.Graphics;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import de.tim.facharbeit.Main;

public class House extends Structure {						//House stammt von Structure ab

	private List<Blob> blobs = new ArrayList<>();

	public House(int x, int y) {
		super(x, y, 50, 50);
		spawnBlobs();
	}
	
	private void spawnBlobs() {
		blobs.add(new Blob(x + 10, y + 10, this, new Color(50,205,50)));
		blobs.add(new Blob(x + 20, y + 30, this, new Color(50,205,50)));
		blobs.add(new Blob(x + 30, y + 10, this, new Color(50,205,50)));
		Main.structures.addAll(blobs);
	}

	@Override
	public void draw(Graphics graphics) {
		graphics.setColor(Color.black);
		graphics.drawRect(x, y, width, height);
	}
}
