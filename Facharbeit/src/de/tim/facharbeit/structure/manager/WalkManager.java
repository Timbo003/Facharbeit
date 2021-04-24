package de.tim.facharbeit.structure.manager;

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
					AnimationManager.prepairAnimation(AnimationManager.getRandomHuman());  
				}
				
			}
		}, 100, 10);
	}
}
