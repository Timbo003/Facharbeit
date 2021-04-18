package de.tim.facharbeit.structure.manager;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;

import de.tim.facharbeit.Main;
import de.tim.facharbeit.dijkstra.DijkstraManager;
import de.tim.facharbeit.dijkstra.DijkstraPoint;
import de.tim.facharbeit.frames.Frame;
import de.tim.facharbeit.structure.Health;
import de.tim.facharbeit.structure.House;
import de.tim.facharbeit.structure.Human;
import de.tim.facharbeit.structure.Point;
import de.tim.facharbeit.structure.Structure;
import de.tim.facharbeit.structure.streets.Street;

public class AnimationManager {
	public static void start() {

//		for (Structure structure : Main.structures) {
//			if (structure instanceof Human) {
//				Random random = new Random();
//				int randIntStart = random.nextInt(DijkstraManager.crossings.size() - 1);
//				int randIntEnd = random.nextInt(DijkstraManager.crossings.size() - 1);
//				while (randIntEnd == randIntStart) {
//					randIntEnd = random.nextInt(DijkstraManager.crossings.size() - 1);
//				}
//
//				DijkstraPoint start = DijkstraManager.crossings.get(randIntStart);
//				DijkstraPoint end = DijkstraManager.crossings.get(randIntEnd);
//				System.out.println("start: " + start);
//				System.out.println("end: " + end);
//
//				Human human = (Human) structure;
//
//				human.setPoint(start.getPoint());
//				Frame.instance.update();
//				human.path = DijkstraManager.startDijkstra(start, end);
//				DijkstraManager.resetPoints();
//			}
//		}
//
//		walkAnimation();

		walkToEntranceAnimation();
		//walkToPoint();

		

	}

	private static void walkToPoint() {
		Timer timer = new Timer();
		Frame.instance.update();

		List<Human> humans = Main.getAllHumans();

		timer.scheduleAtFixedRate(new TimerTask() {
			@Override
			public void run() {
				System.out.println(humans.size() + " humans walking.");
				List<Integer> removeIndexes = new ArrayList<>();
				for (Human human : humans) {
					Point point = human.nextPointToPoint(human.currentHouse.getDijkstraPoint().point);
					if (point == null) {
						human.setHealth(Health.DEAD);
						removeIndexes.add(humans.indexOf(human));
						continue;
					}
					human.setPoint(point);
				}
				for (int i = removeIndexes.size() - 1; i >= 0; i--) {
					int b = removeIndexes.get(i);
					humans.remove(b);
				}
				if (humans.size() == 0) {
					timer.cancel();
					System.out.println("finished");

				}
				Frame.instance.update();
			}
		}, 100, 25);
	}

	private static void walkToEntranceAnimation() {
		Timer timer = new Timer();

		List<Human> humans = Main.getAllHumans();

		timer.scheduleAtFixedRate(new TimerTask() {
			@Override
			public void run() {
				System.out.println(humans.size() + " humans walking.");
				List<Integer> removeIndexes = new ArrayList<>();
				for (Human human : humans) {
					Point point = human.nextPointToEntrance(human.currentHouse.entrance);
					if (point == null) {
						//human.setPoint(human.currentHouse.);
						human.setHealth(Health.DEAD);
						removeIndexes.add(humans.indexOf(human));
						continue;
					}
					human.setPoint(point);
				}
				for (int i = removeIndexes.size() - 1; i >= 0; i--) {
					int b = removeIndexes.get(i);
					humans.remove(b);
				}
				if (humans.size() == 0) {
					timer.cancel();
					
					System.out.println("finished");
				}
				Frame.instance.update();
			}
		}, 100, 25);
	}

	private static void walkAnimation() {
		Timer timer = new Timer();

		List<Human> humans = Main.getAllHumans();

		timer.scheduleAtFixedRate(new TimerTask() {

			@Override
			public void run() {
				System.out.println(humans.size() + " humans walking.");
				List<Integer> removeIndexes = new ArrayList<>();
				for (Human human : humans) {
					Point point = human.nextPointOnTheWay(human.path.get(human.pathIndex).getPoint());

					if (point == null) {
						human.setHealth(Health.DEAD);
						removeIndexes.add(humans.indexOf(human));
						continue;
					}
					human.setPoint(point);
				}
				for (int i = removeIndexes.size() - 1; i >= 0; i--) {
					int b = removeIndexes.get(i);
					humans.remove(b);
				}
				if (humans.size() == 0) {
					timer.cancel();
					System.out.println("finished");
				}
				Frame.instance.update();
			}
		}, 100, 5);
	}
}
