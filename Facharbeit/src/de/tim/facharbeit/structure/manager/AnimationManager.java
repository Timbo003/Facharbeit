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

		for (Human human : Main.getAllHumans()) {
			Random random = new Random();
			human.reset();

			DijkstraPoint start = human.currentHouse.getDijkstraPoint();

			int randIntEnd = random.nextInt(DijkstraManager.crossings.size() - 1);
			DijkstraPoint end = DijkstraManager.crossings.get(randIntEnd);

			while (start.equals(end)) {
				end = DijkstraManager.crossings.get(random.nextInt(DijkstraManager.crossings.size() - 1));
				System.out.println("same");
			}

			human.path = DijkstraManager.startDijkstra(start, end);
			DijkstraManager.resetPoints();

			System.out.println(human.path);
		}
		
		walkAnimation();
	}

	public static void walkToEntranceAnimation() {
		System.out.println("--------------------- walkToEntranceAnimation ----------------------------------");
		for (Human human : Main.getAllHumans()) {
			Point point = human.nextPointToEntrance();
			DijkstraPoint midPoint = new DijkstraPoint(point);
			DijkstraPoint entrancePoint = new DijkstraPoint(human.currentHouse.pointOnStreet);
			human.path.add(0, midPoint);
			human.path.add(1, entrancePoint);
		}
	}

	public static void walkAnimation() {
		walkToEntranceAnimation();
		System.out.println("--------------------- walkAnimation ----------------------------------");
		
		Timer timer = new Timer();

		List<Human> humans = Main.getAllHumans();

		timer.scheduleAtFixedRate(new TimerTask() {

			@Override
			public void run() {
				System.out.println(humans.size() + " humans walking.");
				List<Integer> removeIndexes = new ArrayList<>();
				for (Human human : humans) {
					System.out.println("steps: " + human.getPointAmountToWalk());
					System.out.println("path"+human.path);
					
					if (human.walkStep()) {
						human.setHealth(Health.DEAD);
						removeIndexes.add(humans.indexOf(human));
						System.out.println("1 human done");
						continue;
					}
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
