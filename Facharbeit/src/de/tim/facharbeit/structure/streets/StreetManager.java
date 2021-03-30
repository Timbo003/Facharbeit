package de.tim.facharbeit.structure.streets;

import de.tim.facharbeit.Main;
import de.tim.facharbeit.structure.Point;

public class StreetManager {
	
	int frameWigth = 600;
	int frameHight = 400;
	
	public void streetStartUp() {
		HorizontalStreet h1 = new HorizontalStreet(new Point(20, 20), 600);
		VerticalStreet v1 = new VerticalStreet(new Point(20, 20), 400);
		
		HorizontalStreet h2 = new HorizontalStreet(new Point(frameWigth+20, frameHight-20), -600);
		VerticalStreet v2 = new VerticalStreet(new Point(frameWigth-20, frameHight+20), -400);

		Main.structures.addAll(Street.allStreets);	
		System.out.println(Street.allStreets);
		System.out.println("streetSetUp successfull");
	}
	
	public  void createStreet(Street street, int distance) {
		if (street instanceof VerticalStreet) {										//wenn sie vertikal ist muss die neue horizontal sein
			HorizontalStreet newStreet = new HorizontalStreet(street.getDistance(distance), 600);
		}else {																		//wenn nicht, dan ist sie horizontal und die neue muss vertikal sein
			VerticalStreet newStreet = new VerticalStreet(street.getDistance(distance), 400);
		}
		Main.structures.addAll(Street.allStreets);
	}
}
