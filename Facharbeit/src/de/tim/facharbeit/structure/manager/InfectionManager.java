package de.tim.facharbeit.structure.manager;

import java.util.List;
import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;

import de.tim.facharbeit.Variables;
import de.tim.facharbeit.structure.Health;
import de.tim.facharbeit.structure.Human;

public class InfectionManager {

	private static boolean isHumanNearInfected(Human human) {
		Random random = new Random();
		List<Human> infected = HumanManager.getInfectedHumans();
		for (Human sick : infected) {
			if (human.distanceTo(sick.getPoint()) <= Variables.infectionDistance
					&& !(human.health.equals(Health.IMUNE))) {
				// System.err.println("inf detected");
				return true;
			}
		}
		return false;
	}

	private static Human nearWhichInfected(Human human) {
		Random random = new Random();
		List<Human> infected = HumanManager.getInfectedHumans();
		for (Human sick : infected) {
			if (human.distanceTo(sick.getPoint()) <= Variables.infectionDistance
					&& !(human.health.equals(Health.IMUNE))) {
				// System.err.println("inf detected");
				return sick;
			}
		}
		return null;
	}

	private static int animationCounter = 0;

	public static void start() {
		System.out.println("--------------------- Infection Manager started ----------------------------------");

		Timer timer = new Timer();
		Random random = new Random();

		timer.scheduleAtFixedRate(new TimerTask() {

			@Override
			public void run() {
				if (!(Variables.stop)) {
					animationCounter++;
					if ((animationCounter % Math.abs(Variables.animationSpeed - 11) * 100) == 0) {// animationSpeed
						animationCounter = 0;
						for (Human human : HumanManager.getHealthyHumans()) {
							if (isHumanNearInfected(human) && random.nextInt(Variables.infectionRisk) == 1) {
								Human sick = nearWhichInfected(human);
								if (!(human.infectionChecked.contains(sick))) {
									human.infectionChecked.add(sick);
									human.setHealth(Health.INFECTED);
								}

							}
						}
					}
				}

			}
		}, 100, 1);
	}
}
