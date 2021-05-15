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
import de.tim.facharbeit.frames.SimulationFrame;
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

		
		int counter = 0 ;
		boolean usable = true;
		
		while (!(isHouseOk(house, human))) {
			counter++;
			house = Main.totalHouses().get(random.nextInt(Main.totalHouses().size() - 1));
			if (counter >= Main.totalHouses().size()) {
				usable = false;
				break;
			}
		}
		if (usable) {
			prepairAnimation(human, house);
		}
		
	}

	public static boolean isHouseOk(House house, Human human) {
		if (house.entrance.getPoint()
				.distanceToPoint(human.getHome().entrance.getPoint()) >= Variables.allowedDistance) {
			return false;
		}
		int humansInThisHouse = 0;
		for (Human h : Main.getAllLifingHumans()) {
			if (h.currentHouse == null) {
			} else {
				if (h.currentHouse.equals(house) && !(h.currentHouse.equals(h.getHome()))) {
					humansInThisHouse++;
				}
			}
		}
		for (Human h : Main.getAllLifingHumans()) {
			if (h.targetHouse == null) {
			} else {
				if (h.targetHouse.equals(house)&& !(h.targetHouse.equals(h.getHome()))) {
					humansInThisHouse++;
				}
			}
		}
		if (humansInThisHouse >= Variables.maxHumansInHouse) {
			return false;
		}
		return true;
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

		for (int i = 0; i < human.path.size() - 1; i++) { // wenn die allowedDistance klein ist kann der nearest
															// dijkstra 2 mal hintereinander im path sein, hier nehmen
															// wir ihn wieder raus
			if (human.path.get(i).equals(human.path.get(i + 1))) {
				human.path.remove(i);
			}
		}

		Point point = human.nextPointToEntrance();
		DijkstraPoint midPoint = new DijkstraPoint(point);
		DijkstraPoint entrancePoint = new DijkstraPoint(human.currentHouse.pointOnStreet);
		human.path.add(0, midPoint);
		human.path.add(1, entrancePoint);

		Point a = human.currentHouse.pointOnStreet; // Eingang
		Point b = human.path.get(1).getPoint(); // Letzte Kreuzung
		Point c = human.path.get(2).getPoint(); // Vorletzte kreuzung
		if (a.pointDistance(c) < a.pointDistance(b)) {
			System.out.println("beginning");
			human.path.remove(1);
		}

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

		a = human.targetHouse.pointOnStreet; // Eingang
		b = human.path.get(human.path.size() - 3).getPoint(); // Letzte Kreuzung
		c = human.path.get(human.path.size() - 4).getPoint(); // Vorletzte kreuzung
		if (a.pointDistance(c) < a.pointDistance(b)) {
			human.path.remove(human.path.get(human.path.size() - 3));
		}

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

		List<Human> humans = Main.getAllLifingHumans();

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
								if (!(human.health.equals(Health.DEAD))) {
									human.moveInHouse();
								}

							}
						}
						if (HumanManager.areAllHumansFinished()) {
							timer.cancel();
							timer.purge();
							cancel();
							System.out.println("finished");
							System.out.println(Variables.days);
							if (StopSim() != true) {
								DayManager.nextDay();

							} else {
								Variables.stop = true;
								Variables.stopLock = true;
								System.out.println("simulation stopped");
							}

						}
						SimulationFrame.instance.update();
					}
				}
			}

		}, 100, 1);
	}

	private static boolean StopSim() {
		if (Variables.days.size() > 3) {
			if (Variables.days.get(Variables.days.size() - 1).getInfected() == 0) {
				SimulationFrame.stopButton.setText("Simulation zu Ende");
				SimulationFrame.stopButton.setBackground(new Color(97, 255, 181));
				return true;
			}
		}
		return false;
	}
}
