package de.tim.facharbeit.structure.manager;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;

import javax.swing.JPopupMenu;

import de.tim.facharbeit.Main;
import de.tim.facharbeit.Variables;
import de.tim.facharbeit.frames.Frame;
import de.tim.facharbeit.structure.Block;
import de.tim.facharbeit.structure.Health;
import de.tim.facharbeit.structure.House;
import de.tim.facharbeit.structure.Human;
import de.tim.facharbeit.structure.Structure;
import de.tim.facharbeit.structure.streets.Street;

public class HumanManager {
	private static List<Health> healthArr = new ArrayList<>();
	private static List<House> houseArr = new ArrayList<>();

	public static int totalHumans() {
		int totalHumans = 0;
		for (Block block : Street.blocks) {
			for (House house : block.houses) {
				totalHumans += house.humans.size();
			}
		}
		return totalHumans;
	}

	// SpawnTheRightAmountOfHumans//
	public static void humanStartup() {
		int tmpHumanCount = Variables.totalHumanCounter;
		houseArr = Main.totalHouses();
		//fillHouseList();
		//shuffleHouseList();
		System.out.println(Main.totalHouses().size());
		System.out.println("a: " + houseArr.size());
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
				}else {
					break;
				}
			}
		}
		if (i > 100) {
			System.err.println("Houses can't contain that many Humans, because single house cap is reached!");
		}
	}

	private static void shuffleHouseList() {
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
	public static void healthStartup() {
		giveRightHealthToHumans(fillHealthArr(totalHumans() - Variables.imuneCount - Variables.infectedCount,
				Variables.infectedCount, Variables.imuneCount));

	}

	private static List<Health> fillHealthArr(int healthy, int infected, int imune) {
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

	private static List<Health> shuffleHealthList(List<Health> healthArr) {
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

	private static void giveRightHealthToHumans(List<Health> healthArr) {
		for (Block block : Street.blocks) {
			for (House house : block.houses) {
				for (Human human : house.humans) {
					human.setHealth(healthArr.get(0));
					healthArr.remove(0);
				}
			}
		}
	}

}
