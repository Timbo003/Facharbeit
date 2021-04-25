package de.tim.facharbeit.structure.manager;

import java.awt.Color;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;

import de.tim.facharbeit.Main;
import de.tim.facharbeit.structure.Human;

public class WalkManager {

	public static void startHuman() {
		
		Timer timer = new Timer();
		

		timer.scheduleAtFixedRate(new TimerTask() {

			@Override
			public void run() {
				Random random = new Random();
				if (random.nextInt(10) == 1) {
					if (getHumansInAHouse().size() > 0) {
						AnimationManager.prepairAnimation(AnimationManager.getRandomHuman());
					}  
				}
				for (Human human : Main.getAllHumans()) {
					if (!(human.isHumanAllowdToWalk()) && human.currentHouse != null) {
						if(!(human.currentHouse.equals(human.getHome()))) {
							AnimationManager.prepairAnimation(human, human.getHome());
							human.blobColor = Color.cyan;
						}	
					}
				}
				
			}
		}, 100, 10);
	}
	
	public static ArrayList<Human> getHumansInAHouse(){
		ArrayList<Human> humansInsideAHouse = new ArrayList<>();
		for (Human human : Main.getAllHumans()) {
			if (human.currentHouse != null && human.isHumanAllowdToWalk()) {
				humansInsideAHouse.add(human);
			}
		}
		return humansInsideAHouse;
		
	}
}
