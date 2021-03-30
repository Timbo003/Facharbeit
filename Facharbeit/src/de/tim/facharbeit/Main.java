package de.tim.facharbeit;
import java.util.List;
import java.util.Random;
import java.util.ArrayList;

import de.tim.facharbeit.structure.Blob;
import de.tim.facharbeit.structure.House;
import de.tim.facharbeit.structure.Structure;

public class Main {

	public static List<Structure> structures = new ArrayList<>();		//alle sachen, welche auf der map gezeigt werden

	private static Frame frame;

	public static void main(String[] args) {
		System.out.println("start");
		frame = new Frame();	
		createHouses(10);

//		Structure myStructure = structures.get(0);						//Irgeneine Struktur. Aber ist es ein Blob oder Haus?
//		if (myStructure instanceof House) {								//wenn die Struktur ein Haus is dann wird gecastet
//			House myHouse = (House) myStructure;						//Die Struktur zum Haus casten
//			myHouse.getBlobAmount();									//Dann kann man mit dem Haus arbeiten
//		}

		frame.update();
		System.out.println("end");
	}

	private static boolean isLocFree(int x, int y) {
		final int distance = 60;
		for (Structure structure : structures) {
			int xDifference = structure.getX() - x;
			int yDifference = structure.getY() - y;
			if (!(xDifference > distance || yDifference > distance || xDifference < -distance || yDifference < -distance)) {
				return false;
			}
		}
		return true;
	}
	
	private static void createHouses(int house) {
		Random random = new Random();
		for (int i = 0; i < house; i++) { //Soviele Häuser sollen hinzugefügt werden
			for (int j = 0; j < 1000; j++) { //So oft wird maximal getestet ob die Position frei ist
				int x = random.nextInt(500);
				int y = random.nextInt(300);
				if (isLocFree(x, y)) {
					structures.add(new House(x, y));
					break;
				}
			}
		}
	}
}
