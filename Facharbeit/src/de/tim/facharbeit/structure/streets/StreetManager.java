package de.tim.facharbeit.structure.streets;

import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;

import de.tim.facharbeit.Frame;
import de.tim.facharbeit.Main;
import de.tim.facharbeit.structure.Point;

public class StreetManager {

	int frameWigth = 600;
	int frameHight = 400;

	public void streetStartUp() {
		System.out.println("Startout");
		this.frameWigth = Frame.instance.getWidth();
		try {
			HorizontalStreet h1 = new HorizontalStreet(new Point(20, 20), 600);
			VerticalStreet v1 = new VerticalStreet(new Point(20, 21), 400);

			HorizontalStreet h2 = new HorizontalStreet(new Point(20, frameHight - 20), 600);
			VerticalStreet v2 = new VerticalStreet(new Point(frameWigth - 21, 20), 400);
		} catch (Exception e) {
			e.printStackTrace();
		}
		Main.structures.addAll(Street.allStreets);
		System.out.println(Street.allStreets);
		System.out.println("streetSetUp successfull");
	}

	public void createStreets(int value, int distance) {
		for (int i = 0; i < value; i++) {
			createStreet(distance);
		}
	}

	public void createStreet(int distance) {
		int randInt = new Random().nextInt(Street.allStreets.size());
		System.out.println("randInt: " + randInt);

		Point p = Street.allStreets.get(randInt).getDistance(distance);
		if (Street.allStreets.get(randInt) instanceof VerticalStreet) { // wenn sie vertikal ist muss die neue horizontal sei
			try {
				HorizontalStreet newStreet = new HorizontalStreet(p);
				System.out.println("successsssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssss");
			} catch (Exception e) {
				Street.allStreets.remove(Street.allStreets.size()-1);
				System.out.println("h: Street could not be build");
			}													


		} 
		else { // wenn nicht, dan ist sie horizontal und die neue muss vertikal sein
			try {
				VerticalStreet newStreet = new VerticalStreet(p);
				System.out.println("successssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssss");
			} catch (Exception e) {
				Street.allStreets.remove(Street.allStreets.size()-1);
				System.out.println("v: Street could not be build");
			}
		}
		Main.structures.addAll(Street.allStreets);
	}
}
