package de.tim.facharbeit;

import java.util.List;
import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;

import javax.swing.plaf.basic.BasicSplitPaneUI.BasicVerticalLayoutManager;

import java.sql.Time;
import java.util.ArrayList;

import de.tim.facharbeit.structure.Point;
import de.tim.facharbeit.structure.Structure;
import de.tim.facharbeit.structure.streets.Street;
import de.tim.facharbeit.structure.streets.StreetOrientation;

public class Main {

	public static List<Structure> structures = new ArrayList<>(); // alle sachen, welche auf der map gezeigt werden

	private static Frame Frame;

	public static void main(String[] args) {
		System.out.println("start");
		Frame = new Frame();

		createStreets();
		Frame.instance.update();
		System.out.println("end");

		new Timer().scheduleAtFixedRate(new TimerTask() {

			@Override
			public void run() {
				addStreet();

				Frame.instance.update();
			}
		}, 0, 1000);
	}

	private static void createStreets() {
		new Street(new Point(20, 20), StreetOrientation.HORIZONTAL, 300); // 0 -
		new Street(new Point(20, 20), StreetOrientation.VERTICAL, 300); // 1 |
		new Street(new Point(20, 320), StreetOrientation.HORIZONTAL, 300); // 2 _
		new Street(new Point(320, 20), StreetOrientation.VERTICAL, 300); // 3 |

	}

	private static void addStreet() {
		Random random = new Random();
		int i = random.nextInt(Street.streets.size());
		if (i == 2 || i == 3) {
			addStreet();
			return;
		}
		System.out.println("i: " + i);
		Street old = Street.streets.get(i);
		int b = old.getLength() - 10;
		if (b <= 0) {
			addStreet();
			return;
		}
		int a = random.nextInt(b) + 10;
		System.out.println("a: " + a);
		StreetOrientation orientation = old.getOrientation() == StreetOrientation.VERTICAL
				? StreetOrientation.HORIZONTAL
				: StreetOrientation.VERTICAL;
		int x = old.getX() + (orientation == StreetOrientation.VERTICAL ? a : 0);
		int y = old.getY() + (orientation == StreetOrientation.VERTICAL ? 0 : a);
		Point point = new Point(x, y);
		System.out.println(point);
		System.out.println(orientation);
		try {
			new Street(point, orientation);
		} catch (Exception e) {
			addStreet();
		}
	}
}