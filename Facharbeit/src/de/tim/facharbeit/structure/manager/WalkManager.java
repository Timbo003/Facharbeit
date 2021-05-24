package de.tim.facharbeit.structure.manager;

import java.awt.Color;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;

import de.tim.facharbeit.Main;
import de.tim.facharbeit.Variables;
import de.tim.facharbeit.structure.Health;
import de.tim.facharbeit.structure.Human;

public class WalkManager {
	private static int animationCounter = 0;

	public static void startHuman() {

		Timer timer = new Timer();
		Variables.activeTimers.add(timer);
		System.out.println("started one startHuman timer");

		timer.scheduleAtFixedRate(new TimerTask() {

			@Override
			public void run() {
				animationCounter++;
				if ((animationCounter % Math.abs(Variables.animationSpeed - 11) * 100) == 0
						|| Variables.animationSpeed == 10) { // animationSpeed
					Random random = new Random();
					if (random.nextInt(10) == 1) {
						if (getHumansWhoMovedEnought().size() > 0) {
							AnimationManager.prepairAnimation(AnimationManager.getRandomHuman());
							willSomeoneDie();
						}
					}
					for (Human human : Main.getAllLifingHumans()) { // soll heim gehen
						if (!(human.isHumanAllowdToWalk()) && human.currentHouse != null
								&& human.timeInHouse > human.minMovesInHouse&& isHomeFree(human)) {
							if (!(human.currentHouse.equals(human.getHome())) ) {
								try {
									AnimationManager.prepairAnimation(human, human.getHome());
								} catch (Exception e) {
									System.err.println("this should not happen or is not suppoerted yet. WM");
								}
								// human.blobColor = Color.cyan;
							}
						}
					}
					if (HumanManager.areAllHumansFinished()) {
						timer.cancel();
						timer.purge();
						cancel();
						System.out.println("canceled one startHuman timer");
					}
				}
			}
		}, 100, 10);
	}

	private static boolean isHomeFree(Human human) {
		int humansInThisHouse = 0;
		for (Human h : Main.getAllLifingHumans()) {
			if (h.currentHouse == null) {
			} else {
				if (h.currentHouse.equals(human.getHome())) {			//wenn ein Mensch in dem Haus ist +1
					humansInThisHouse++;
				}
			}
		}
		for (Human h : Main.getAllLifingHumans()) {
			if (h.targetHouse == null) {
			} else {
				if (h.targetHouse.equals(human.getHome())) {			//wenn ein Mensch auf dem weg dorthin ist +1
					humansInThisHouse++;
				}
			}
		}
		if (humansInThisHouse >= Variables.maxHumansInHouse + Variables.maxHumansInHome) {
			return false;
		}
		return true;
	}
	
	private static void willSomeoneGetImune() {
		Random random = new Random();
		Human randInfected = HumanManager.getInfectedHumans()
				.get(random.nextInt(HumanManager.getInfectedHumans().size()));
		if (randInfected.daysInfected > Variables.maxTimeSick) {
			randInfected.setHealth(Health.IMUNE);
		}
	}

	private static void willSomeoneDie() {
		Random random = new Random();
		if (HumanManager.getInfectedHumans().size() > 0) {
			Human randInfected = HumanManager.getInfectedHumans()
					.get(random.nextInt(HumanManager.getInfectedHumans().size()));
			if (randInfected.deathCheck) {

			} else {
				if (random.nextInt(10000) <= (Variables.mortality * 100)) {
					randInfected.die();
					randInfected.deathCheck = true;
				} else {
					randInfected.deathCheck = true;
				}
			}
		}
	}

	public static ArrayList<Human> getHumansInAHouse() {
		ArrayList<Human> humansInsideAHouse = new ArrayList<>();
		for (Human human : Main.getAllLifingHumans()) {
			if (human.currentHouse != null && human.isHumanAllowdToWalk()) {
				humansInsideAHouse.add(human);
			}
		}
		return humansInsideAHouse;
	}

	public static ArrayList<Human> getHumansWhoMovedEnought() {
		ArrayList<Human> humansWhoMovedEnought = new ArrayList<>();
		for (Human human : Main.getAllLifingHumans()) {
			if (human.timeInHouse > human.minMovesInHouse && human.currentHouse != null
					&& human.isHumanAllowdToWalk()) {
				humansWhoMovedEnought.add(human);
			}
		}
		return humansWhoMovedEnought;
	}
}
