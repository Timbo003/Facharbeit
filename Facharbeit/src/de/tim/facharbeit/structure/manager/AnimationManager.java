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

	private static DijkstraPoint getRandomCrossing() {
		Random random = new Random();
		List<DijkstraPoint> all = DijkstraManager.crossings;
		return all.get(random.nextInt(all.size()));
	}

	private static List<Human> getAllHumans() {
		List<Human> list = new ArrayList<>();
		for (House house : Main.totalHouses()) {
			for (Human human : house.humans) {
				list.add(human);
			}
		}
		return list;
	}

	public static void start() {
		for (Human human : getAllHumans()) {
			DijkstraPoint point = getRandomCrossing();
			human.setPoint(point.getPoint());
			human.path = DijkstraManager.startDijkstra(point, getRandomCrossing());
			DijkstraManager.resetPoints();
			walkAnimation();
		}

		Frame.instance.update();

//		Human testHuman = Street.blocks.get(0).houses.get(0).humans.get(0);
//		testHuman.setHealth(Health.DEAD);
//		
//		testHuman.setPoint(DijkstraManager.crossings.get(0).getPoint());
//		System.out.println("test" + testHuman.getPoint());
//		Frame.instance.update();
//
//		testHuman.path = DijkstraManager.startDijkstra(DijkstraManager.crossings.get(0), DijkstraManager.crossings.get(DijkstraManager.crossings.size() -1));
//
//		walkAnimation(testHuman);
	}

	private static void walkAnimation() {
		Timer timer = new Timer();
		timer.scheduleAtFixedRate(new TimerTask() {

			@Override
			public void run() {
				for (Human human : getAllHumans()) {
					if (human.reachedTarget())
						continue;
					Point point = human.getPoint();
					System.out.println(point);
					human.walkStep();
					Frame.instance.update();
					
				}
			}
		}, 100, 5);
	}
}
