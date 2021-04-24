package de.tim.facharbeit.structure.manager;

import de.tim.facharbeit.Day;
import de.tim.facharbeit.Variables;

public class DayManager {
	
	
	public static void nextDay() {
		Day day = new Day(Variables.days.size());
		
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
