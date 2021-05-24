package de.tim.facharbeit.structure.manager;

import java.util.List;
import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;

import de.tim.facharbeit.Variables;
import de.tim.facharbeit.structure.Health;
import de.tim.facharbeit.structure.Human;

public class InfectionManager {
	
	public static Timer timer = new Timer();

	private static boolean isHumanNearInfected(Human human) {				//ist ein Mensch in der Nähe eiens infizierten
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

	private static Human nearWhichInfected(Human human) {					//neben welchen Infizierten ist der Mensch
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

	private static int animationCounter = 0;		//passt sich an den animations speed an
	public static void start() {
		System.out.println("--------------------- Infection Manager started ----------------------------------");

		timer = new Timer();
		Random random = new Random();

		timer.scheduleAtFixedRate(new TimerTask() {
			@Override
			public void run() {
				if (!(Variables.stop)) {			//wenn es den nicht gerade angehalten ist
					animationCounter++;
					if ((animationCounter % Math.abs(Variables.animationSpeed - 11) * 100) == 0 || Variables.animationSpeed == 10) {// animationSpeed
						animationCounter = 0;
						for (Human human : HumanManager.getHealthyHumans()) {
							if (isHumanNearInfected(human)) {
								Human sick = nearWhichInfected(human);
								if (!(human.currentHouse == null)) { // in einem Huas Trägt man keine Masken
									if (random.nextInt(100000) < (Variables.infectionRisk * 1000)) {
										if (!(human.infectionChecked.contains(sick))) {
											human.infectionChecked.add(sick);
											human.setHealth(Health.INFECTED);
										}
									}
								} else { // auf der Straße trägt man vielleicht eine Masken
									sick = nearWhichInfected(human);									
									if (random.nextInt(100000) <= (Variables.infectionRisk * 1000)) {
										if (sick.isWearingMask == false && human.isWearingMask == false) {	//beide tragen keine Maske
											if (random.nextInt(10000) < (Variables.infectionRisk * 100)) {
												if (!(human.infectionChecked.contains(sick))) {
													human.infectionChecked.add(sick);
													human.setHealth(Health.INFECTED);
												}
											}
										} else if ((sick.isWearingMask == true && human.isWearingMask == false)
												|| (sick.isWearingMask == false && human.isWearingMask == true)) {//einer der beiden trägt eine Maske
											if (random.nextInt(100000) < (Variables.infectionRisk * 1000)) {
												if (random.nextInt(100) > Variables.maskEffectivity) {
													if (!(human.infectionChecked.contains(sick))) {
														human.infectionChecked.add(sick);
														human.setHealth(Health.INFECTED);
													}
												}

											}
										} else if (sick.isWearingMask == true && human.isWearingMask == true) {	//beide tragen eine Maske
											if (random.nextInt(100000) < (Variables.infectionRisk * 1000)) {
												if (random.nextInt(100) > Variables.maskEffectivity) {
													if (random.nextInt(100) > Variables.maskEffectivity) {
														if (!(human.infectionChecked.contains(sick))) {
															human.infectionChecked.add(sick);
															human.setHealth(Health.INFECTED);
														}
													}
												}
											}
										}
									}
								}
							}
						}
					}

				}
			}
		}, 100, 1);
	}
}
