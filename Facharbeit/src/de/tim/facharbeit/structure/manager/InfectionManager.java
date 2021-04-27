package de.tim.facharbeit.structure.manager;

import java.util.Iterator;
import java.util.List;
import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;

import de.tim.facharbeit.Main;
import de.tim.facharbeit.Variables;
import de.tim.facharbeit.dijkstra.DijkstraManager;
import de.tim.facharbeit.frames.Frame;
import de.tim.facharbeit.structure.Health;
import de.tim.facharbeit.structure.Human;

public class InfectionManager {
	
	private static boolean isHumanNearInfected(Human human) {
		Random random = new Random();
		List<Human> infected = HumanManager.getInfectedHumans();
		for (Human sick : infected) {
			if (human.distanceTo(sick.getPoint()) <= Variables.infectionDistance && !(human.health.equals(Health.IMUNE))) {
				return true;
			}
		}
		return false;
	}
	

	public static void start() {
		System.out.println("--------------------- Infection Manager started ----------------------------------");

		Timer timer = new Timer();
		Random random = new Random();

		timer.scheduleAtFixedRate(new TimerTask() {

			@Override
			public void run() {
				for (Human human : HumanManager.getHealthyHumans()) {
					if (isHumanNearInfected(human) && random.nextInt(50000) == 1) {
						human.setHealth(Health.INFECTED);
					}
				}
			}
		}, 100, 1);
	}
}
