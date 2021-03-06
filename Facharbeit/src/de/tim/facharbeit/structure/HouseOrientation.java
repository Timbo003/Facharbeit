package de.tim.facharbeit.structure;

public enum HouseOrientation {

	UP(10, 0, false),			//der Eingang kann untern, rechts, links oder oben sein
	LEFT(0, 10, false),			//das sind die Werte die man dazu rechnen muss
	DOWN(-30, -10, true),		
	RIGHT(-10, -30, true);		
	
	private int x;
	private int y;
	private boolean useLength;
	
	private HouseOrientation(int x, int y, boolean b) {
		this.x = x;
		this.y = y;
		this.useLength = b;
	}

	public int getX(int x) {
		return useLength ? x + this.x : this.x;
	}

	public int getY(int y) {
		return useLength ? y + this.y : this.y;
	}
}
