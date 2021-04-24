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
	
	private static List<Human> allowedList;
	
	public static void prepairAnimation(Human human) {
		human.reset();
		
		Random random = new Random();
		human.targetHouse = Main.totalHouses().get(random.nextInt(Main.totalHouses().size()-1));
		
		while (human.targetHouse.nearestDijkstra.equals(human.currentHouse.nearestDijkstra)) {
			human.targetHouse = Main.totalHouses().get(random.nextInt(Main.totalHouses().size()-1));
		}
		
		DijkstraPoint start = human.currentHouse.nearestDijkstra;
		DijkstraPoint end = human.targetHouse.nearestDijkstra;
		
		human.path = DijkstraManager.startDijkstra(start, end);
		DijkstraManager.resetPoints();
		
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

		int newX = insidePoint.getX() + 5*x;
		int newY = insidePoint.getY() + 5*y;
		
		Point p = new Point(newX, newY);
		
		DijkstraPoint targetInside = new DijkstraPoint(p);
		human.path.add(targetInside);
		human.currentHouse = null;
	}
	
	public static void refreshAllowedList() {
		
	}
	
	public static Human getRandomHuman() { //TODO don't use humans that are already walking
		Random random = new Random();
		Human human = Main.getAllHumans().get(random.nextInt(Main.getAllHumans().size()));
		if (human.isHumanAllowdToWalk() && human.currentHouse !=null) {
			return human;
		}
		return getRandomHuman();
	}
	
	
	private static int counter = 0; 

	public static void walkAnimation() {
		System.out.println("--------------------- walkAnimation ----------------------------------");

		Timer timer = new Timer();

		List<Human> humans = Main.getAllHumans();

		timer.scheduleAtFixedRate(new TimerTask() {

			@Override
			public void run() {
				counter++;
				System.out.println(humans.size() + " humans walking.");
				List<Integer> removeIndexes = new ArrayList<>();
				for (Human human : humans) {
					if (!(counter % human.speed == 0))
						continue;
					if (human.currentHouse == null) {
						System.out.println("steps: " + human.getPointAmountToWalk());
						System.out.println("path" + human.path);

						if (human.walkStep()) {
							human.currentHouse = human.targetHouse;
							human.targetHouse = null;
							human.setHealth(Health.DEAD);
							System.out.println(human.currentHouse.getPoint());
							System.out.println("1 human done");
							continue;
						}
					} else if (counter % (human.speed * 10) == 0) {
						human.moveInHouse();
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
		}, 100, 1);
	}
}
