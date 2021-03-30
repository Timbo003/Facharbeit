package de.tim.facharbeit.structure;

import java.awt.Color;
import java.awt.Graphics;

public class Blob extends Structure{

	private House home;
	private Color blobColor;
	
	public enum Characteristics 
	{
	 Stubenhocker, Partymacher, Einkäufer
	}

	
	public Blob(int x, int y, House home, Color color) {
		super(x, y, 10, 10);
		this.home = home;
		blobColor = color;
		
	}

	@Override
	public void draw(Graphics graphics) {
		graphics.setColor(blobColor);
		graphics.fillOval(x, y, width, height);
		
	}

	public House getHome() {
		return home;
	}

	public void setHome(House home) {
		this.home = home;
	}
	
	
}
