package de.tim.facharbeit.structure.manager;

import java.util.Timer;
import java.util.TimerTask;

import de.tim.facharbeit.Main;
import de.tim.facharbeit.frames.Frame;
import de.tim.facharbeit.structure.Human;
import de.tim.facharbeit.structure.Structure;

public class AnimationManager {

	
	public static void start() {
		Timer timer = new Timer();
		timer.scheduleAtFixedRate(new TimerTask() {
			
			@Override
			public void run() {
				for (Structure structure : Main.structures) {
					if (structure instanceof Human) {
						Human human = (Human) structure;
						human.nextStep();
					}
				}
				Frame.instance.update();
			}
		}, 100, 100);
	}
}
