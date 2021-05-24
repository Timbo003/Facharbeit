package de.tim.facharbeit.structure.manager;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;

import javax.swing.JPopupMenu;

import de.tim.facharbeit.Main;
import de.tim.facharbeit.Variables;
import de.tim.facharbeit.frames.SimulationFrame;
import de.tim.facharbeit.structure.Block;
import de.tim.facharbeit.structure.Health;
import de.tim.facharbeit.structure.House;
import de.tim.facharbeit.structure.Human;
import de.tim.facharbeit.structure.Personality;
import de.tim.facharbeit.structure.Structure;
import de.tim.facharbeit.structure.streets.Street;

public class HumanManager {
	private static List<Health> healthArr = new ArrayList<>();
	private static List<Personality> personalityArr = new ArrayList<>();
	private static List<House> houseArr = new ArrayList<>();

	public static int totalHumans() {
		return Main.getAllHumans().size();
	}

	// SpawnTheRightAmountOfHumans//
	public static void humanStartup() {										//erzeugt die Menschen und setzt sie in ein random Haus
		int tmpHumanCount = Variables.totalHumanCounter;
		houseArr = Main.totalHouses();
		int i = 0;
		while (tmpHumanCount > 0 && i++ < 100) {
			for (House house : houseArr) {
				shuffleHouseList();
				if (tmpHumanCount > 0) {
					if (house.humans.size() < Variables.maxHumansInHome) {
						house.spawnBlob();
						tmpHumanCount -= 1;
						continue;
					}
					continue;
				} else {
					break;
				}
			}
		}
		if (i > 100) {
			System.err.println("Houses can't contain that many Humans, because single house cap is reached!");
		}
	}

	public static void refrechHumanHealthVar() {				//updated die Health Variables
		Variables.infected = 0;
		Variables.imune = 0;
		Variables.dead = 0;
		Variables.healthy = 0;
		Variables.alive = Main.getAllLifingHumans().size();
		Variables.aliveAndWilling = HumanManager.getMaskHumans();
		

		for (Human human : Main.getAllHumans()) {
			switch (human.health) {
			case HEALTHY:
				Variables.healthy++;
				break;
			case DEAD:
				Variables.dead++;
				break;
			case INFECTED:
				Variables.infected++;
				break;
			case IMUNE:
				Variables.imune++;
				break;
			default:
				break;
			}
		}
	}

	private static void shuffleHouseList() {				//shuffled die Liste aller Häuser
		House tmp;
		int rand;
		Random r = new Random();
		for (int i = 0; i < houseArr.size(); i++) {
			rand = r.nextInt(houseArr.size());
			tmp = houseArr.get(i);
			houseArr.set(i, houseArr.get(rand));
			houseArr.set(rand, tmp);
		}
	}

	// MakeTheHealthRight//
	public static void healthStartup() {																	//macht, dass die Menschen zum Start die richtige Health bekommen
		giveRightHealthToHumans(fillHealthArr(totalHumans() - Variables.imuneCount - Variables.infectedCount,
				Variables.infectedCount, Variables.imuneCount));

	}

	public static void characterStartup() {	//gibt den Menschen ihre personality
		giveRightPersonalityToHumans(
				fillpersonalityArr(totalHumans() - Variables.verweigererCount - Variables.bedachtCount,
						Variables.bedachtCount, Variables.verweigererCount));
	}

	private static List<Personality> fillpersonalityArr(int normal, int bedacht, int verweigerer) {	//macht eine Liste mit den persönlichkeiten von denen später ausgewählt werden kann
		for (int i = 0; i < bedacht; i++) {
			personalityArr.add(Personality.BEDACHT);
		}
		for (int i = 0; i < normal; i++) {
			personalityArr.add(Personality.NORMAL);
		}
		for (int i = 0; i < verweigerer; i++) {
			personalityArr.add(Personality.VERWEIGERER);
		}
		shufflePersonalityList(personalityArr);
		return personalityArr;

	}

	private static List<Personality> shufflePersonalityList(List<Personality> personalityArr) {		//shuffelt die personality list
		Personality tmp;
		int rand;
		Random r = new Random();
		for (int i = 0; i < personalityArr.size(); i++) {
			rand = r.nextInt(personalityArr.size());
			tmp = personalityArr.get(i);
			personalityArr.set(i, personalityArr.get(rand));
			personalityArr.set(rand, tmp);
		}
		return personalityArr;
	}

	private static void giveRightPersonalityToHumans(List<Personality> personalityArr) {			//sucht einen passende Personality für jeden Menschen
		for (Human human : Main.getAllHumans()) {
			human.setPersonality(personalityArr.get(0));
			personalityArr.remove(0);
		}
	}

	public static boolean areAllHumansFinished() {													//haben alle Menschen genug Häuser besucht und haben sich bewegt und sind wieder zuhause, dann sind sie alle fertig
		for (Human human : Main.getAllLifingHumans()) {
			if (human.currentHouse == null || human.currentHouse != human.getHome() || human.isHumanAllowdToWalk()
					|| human.minMovesInHouse > human.timeInHouse) {
				return false;
			}
		}
		return true;
	}

	private static List<Health> fillHealthArr(int healthy, int infected, int imune) {			//macht eine Liste mit den Gesundheiten von denen später ausgewählt werden kann
		for (int i = 0; i < healthy; i++) {
			healthArr.add(Health.HEALTHY);
		}
		for (int i = 0; i < infected; i++) {
			healthArr.add(Health.INFECTED);
		}
		for (int i = 0; i < imune; i++) {
			healthArr.add(Health.IMUNE);
		}
		shuffleHealthList(healthArr);
		return healthArr;
	}

	private static List<Health> shuffleHealthList(List<Health> healthArr) {						//shuffelt die Health list
		Health tmp;
		int rand;
		Random r = new Random();
		for (int i = 0; i < healthArr.size(); i++) {
			rand = r.nextInt(healthArr.size());
			tmp = healthArr.get(i);
			healthArr.set(i, healthArr.get(rand));
			healthArr.set(rand, tmp);
		}
		return healthArr;
	}

	public static List<Human> getInfectedHumans() {				
		List<Human> infected = new ArrayList<>();
		for (Human human : Main.getAllHumans()) {
			if (human.health.equals(Health.INFECTED)) {
				infected.add(human);
			}
		}
		return infected;
	}

	public static List<Human> getImuneHumans() {
		List<Human> imune = new ArrayList<>();
		for (Human human : Main.getAllHumans()) {
			if (human.health.equals(Health.IMUNE)) {
				imune.add(human);
			}
		}
		return imune;
	}

	public static List<Human> getHealthyHumans() {
		List<Human> healthy = new ArrayList<>();
		for (Human human : Main.getAllLifingHumans()) {
			if (human.health.equals(Health.HEALTHY)) {
				healthy.add(human);
			}
		}
		return healthy;
	}

	private static void giveRightHealthToHumans(List<Health> healthArr) {			//gibt den Menschen einen passenden Gesundheitszustand
		for (Human human : Main.getAllHumans()) {
			human.setHealth(healthArr.get(0));
			healthArr.remove(0);
		}
	}
	
	public static List<Human> getMaskHumans(){
		List<Human> maskHumans = new ArrayList<>();
		for (Human human : Main.getAllLifingHumans()) {
			if (!(human.personality == Personality.VERWEIGERER)) {
				maskHumans.add(human);
//				System.out.println(human);
			}
		}
		return maskHumans;
	}

}
