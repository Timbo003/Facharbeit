package de.tim.facharbeit.structure.manager;

import java.awt.Color;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;

import de.tim.facharbeit.Main;
import de.tim.facharbeit.Variables;
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

	public static void prepairAnimation(Human human) {
		Random random = new Random();

		House house = Main.totalHouses().get(random.nextInt(Main.totalHouses().size() - 1));

		while (house.nearestDijkstra.equals(human.currentHouse.nearestDijkstra)) {
			house = Main.totalHouses().get(random.nextInt(Main.totalHouses().size() - 1));
		}
//		while (house.entrance.getPoint().distanceToPoint(human.getHome().entrance.getPoint()) >= Variables.allowedDistance) {
//			house = Main.totalHouses().get(random.nextInt(Main.totalHouses().size() - 1));
//		}

		prepairAnimation(human, house);
	}

	public static void prepairAnimation(Human human, House house) {
		human.reset();

		human.targetHouse = house;

		DijkstraPoint start = human.currentHouse.nearestDijkstra;
		DijkstraPoint end = human.targetHouse.nearestDijkstra;

		if (start.equals(end)) {
			human.path = new ArrayList<>();
			human.path.add(human.currentHouse.nearestDijkstra);
			DijkstraManager.resetPoints();
		} else {
			human.path = DijkstraManager.startDijkstra(start, end);
			DijkstraManager.resetPoints();
		}

		Point point = human.nextPointToEntrance();
		DijkstraPoint midPoint = new DijkstraPoint(point);
		DijkstraPoint entrancePoint = new DijkstraPoint(human.currentHouse.pointOnStreet);
		human.path.add(0, midPoint);
		human.path.add(1, entrancePoint);

		DijkstraPoint targetEntrance = new DijkstraPoint(human.targetHouse.pointOnStreet);
		human.path.add(targetEntrance);

		Point insidePoint = human.targetHouse.entrance.getPoint();

		int x = insidePoint.getX() - targetEntrance.getPoint().getX();
		int y = insidePoint.getY() - targetEntrance.getPoint().getY();

		int newX = insidePoint.getX() + 3 * x;
		int newY = insidePoint.getY() + 3 * y;

		Point p = new Point(newX, newY);

		DijkstraPoint targetInside = new DijkstraPoint(p);
		human.path.add(targetInside);
		human.currentHouse = null;
	}

	public static Human getRandomHuman() {
		Random random = new Random();
		Human human = WalkManager.getHumansWhoMovedEnought()
				.get(random.nextInt(WalkManager.getHumansWhoMovedEnought().size()));
		if (human.isHumanAllowdToWalk() && human.currentHouse != null && human.timeInHouse > human.minMovesInHouse) {
			human.timeInHouse = 0;
			return human;

		}
		return getRandomHuman();
	}

	private static int counter = 0;
	private static int animationCounter = 0;

	public static void walkAnimation() {
		System.out.println("--------------------- walkAnimation ----------------------------------");
		DijkstraManager.resetPoints();

		Timer timer = new Timer();
		Random random = new Random();

		Variables.activeTimers.add(timer);

		List<Human> humans = Main.getAllHumans();

		timer.scheduleAtFixedRate(new TimerTask() {

			@Override
			public void run() {
				if (!(Variables.stop)) {
					animationCounter++;
					if ((animationCounter % Math.abs(Variables.animationSpeed - 11) * 100) == 0) { // animationSpeed
						animationCounter = 0;
						counter++;
						for (Human human : humans) {
							if (!(counter % human.speed == 0))
								continue;
							if (human.currentHouse == null) {
								if (human.walkStep()) {
									human.timeInHouse = 0;
									human.currentHouse = human.targetHouse;
									human.targetHouse = null;
									human.visited++;
									continue;
								}
							} else if (counter % (human.speed * 10) == 0) {
								human.moveInHouse();
							}
						}
						if (HumanManager.areAllHumansFinished()) {
							timer.cancel();
							timer.purge();
							cancel();
							System.out.println("finished");
							System.out.println(Variables.days);
							for (Human human : Main.getAllHumans()) {
								human.setHealth(Health.DEAD);
								break;
							}
							DayManager.nextDay();
						}
						Frame.instance.update();
					}
				}
			}

		}, 100, 1);
	}

}
