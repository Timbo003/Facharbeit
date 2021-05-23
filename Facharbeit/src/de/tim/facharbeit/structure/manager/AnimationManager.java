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

		int counter = 0;
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
			try {
				prepairAnimation(human, house);
			} catch (Exception e) {
				System.err.println("this should not happen or is not suppoerted yet. AM");
			}
		}
	}

	public static boolean isHouseOk(House house, Human human) {
		if (house.equals(human.getHome())) {
			return false;
		}
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
				if (h.targetHouse.equals(house) && !(h.targetHouse.equals(h.getHome()))) {
					humansInThisHouse++;
				}
			}
		}
		if (humansInThisHouse >= Variables.maxHumansInHouse) {
			return false;
		}
		return true;
	}

	public static void prepairAnimation(Human human, House house) throws Exception {
		human.reset();

		human.targetHouse = house;

		House start = human.currentHouse;
		House end = human.targetHouse;

		
		if (start.equals(end)) {
			human.path = new ArrayList<>();
			human.path.add(human.currentHouse.nearestDijkstra);
		} else {
			human.path = DijkstraManager.startDijkstra(start, end);
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
		
		
		if (human.path.contains(null)) {
			//Variables.stop = true;
//			System.out.println("midPoint: " +midPoint);
//			System.out.println("entrancePoint: " +entrancePoint);
//			System.out.println("targetEntrance: " +targetEntrance);
//			System.out.println("targetInside: " +targetInside);

			
			
			human.blobColor = Color.orange;
			System.out.println(human.path);
			
			human.path.remove(null);
			System.out.println(human.path);
		}
		
		human.currentHouse = null;

	}

	public static Human getRandomHuman() {
		Random random = new Random();
		ArrayList<Human> movedEnought = WalkManager.getHumansWhoMovedEnought();
		Human human = movedEnought.get(random.nextInt(movedEnought.size()));
		
//		Human human = WalkManager.getHumansWhoMovedEnought()
//				.get(random.nextInt(WalkManager.getHumansWhoMovedEnought().size()));
		if (human.isHumanAllowdToWalk() && human.currentHouse != null && human.timeInHouse > human.minMovesInHouse) {
			human.timeInHouse = 0;
			return human;

		}
		return getRandomHuman();
	}

	public static int counter = 0;
	private static int animationCounter = 0;

	public static void walkAnimation() {
		System.out.println("--------------------- walkAnimation ----------------------------------");
		System.out.println("crossings: " + DijkstraManager.crossings);

		Timer timer = new Timer();
		Random random = new Random();

		Variables.activeTimers.add(timer);

		List<Human> humans = Main.getAllLifingHumans();

		timer.scheduleAtFixedRate(new TimerTask() {

			@Override
			public void run() {
				if (!(Variables.stop)) {
					animationCounter++;
					if ((animationCounter % Math.abs(Variables.animationSpeed - 11) * 100) == 0 || Variables.animationSpeed == 10) { // animationSpeed
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
							
							if (!Variables.useFixedDayLength || (Variables.dayLength < counter)) {
								timer.cancel();
								timer.purge();
								cancel();
								
								System.out.println("finished at time " + counter);
								counter = 0;
								System.out.println(Variables.days);
								if (StopSim() != true) {
									DayManager.nextDay();
	
								} else {
									Variables.stop = true;
									Variables.stopLock = true;
									System.out.println("simulation stopped");
								}
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
