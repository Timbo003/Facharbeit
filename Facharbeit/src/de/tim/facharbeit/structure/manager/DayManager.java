package de.tim.facharbeit.structure.manager;

import de.tim.facharbeit.Day;
import de.tim.facharbeit.Main;
import de.tim.facharbeit.Variables;
import de.tim.facharbeit.dijkstra.DijkstraManager;
import de.tim.facharbeit.frames.ScoreFrame;
import de.tim.facharbeit.structure.Human;

public class DayManager {
	
	
	public static void nextDay() {
		Day day = new Day(Variables.days.size());
		Variables.days.add(day);
		
		ScoreFrame.updateDate();
		
		for (Human human : Main.getAllHumans()) {
			human.visited = 0;
		}
		DijkstraManager.resetPoints();
		
		
		if (Variables.days.size() > 0) {
			Day formerDay =  Variables.days.get(Variables.days.size() - 1);
			formerDay.setDead(Variables.dead);
			formerDay.setInfected(Variables.infected);
			formerDay.setHealthy(Variables.healthy);
			formerDay.setImune(Variables.imune);
		}
		
		WalkManager.startHuman();
		AnimationManager.walkAnimation();
	}
}
