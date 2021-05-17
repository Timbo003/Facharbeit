package de.tim.facharbeit.structure.manager;

import java.util.Random;
import java.util.Timer;

import de.tim.facharbeit.Day;
import de.tim.facharbeit.Main;
import de.tim.facharbeit.Variables;
import de.tim.facharbeit.dijkstra.DijkstraManager;
import de.tim.facharbeit.frames.SimulationFrame;
import de.tim.facharbeit.structure.Health;
import de.tim.facharbeit.structure.Human;

public class DayManager {

	public static void nextDay() {
		Day day = new Day(Variables.days.size());

		Random random = new Random();
		
		if (Variables.days.size() > 0) {
			for (Human human : HumanManager.getInfectedHumans()) {
				if (!(human.deathCheck)) {
					if (random.nextInt(10000) <= (Variables.mortality * 100)) {
						human.die();
						human.deathCheck = true;
					} else {
						human.deathCheck = true;
					}
				}
			}
		}
		for (Human human : HumanManager.getInfectedHumans()) {
			if (human.daysInfected > Variables.maxTimeSick) {
				human.setHealth(Health.IMUNE);
			}
		}

		SimulationFrame.updateDate();

		for (Human human : Main.getAllHumans()) {
			human.visited = 0;
		}

		if (Variables.days.size() > 0) {
			Day formerDay = Variables.days.get(Variables.days.size() - 1);
			formerDay.setDead(Variables.dead);
			formerDay.setInfected(Variables.infected);
			formerDay.setHealthy(Variables.healthy);
			formerDay.setImune(Variables.imune);
			formerDay.setMaxAllowedDistance(Variables.allowedDistance);
			formerDay.setWearingMasks(Variables.howManyAreWearingMasks);
		}

		for (Timer timer : Variables.activeTimers) {
			timer.cancel();
			timer.purge();
			Variables.activeTimers.remove(timer);
		}
		Variables.activeTimers.clear();

		for (Human human : Main.getAllHumans()) {
			human.setHealth(human.health);
		}
		for (Human human : HumanManager.getInfectedHumans()) {
			human.daysInfected++;
		}
		
		WalkManager.startHuman();
		AnimationManager.walkAnimation();
	}
}
