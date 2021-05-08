package de.tim.facharbeit;

import java.awt.Dimension;
import java.awt.Toolkit;
import java.util.ArrayList;
import java.util.List;
import java.util.Timer;

public class Variables {
	public static List<Day> days = new ArrayList<>();
	public static List<Timer> activeTimers = new ArrayList<>();

	public static Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
	
	public static int streetCount = 10;
	
	public static int infectedCount = 5;
	public static int imuneCount = 5;
	
	public static int totalHumanCounter = 150;
	public static int maxHumansInHome = 3;
	
	public static int bedachtCount = 1;
	public static int verweigererCount = 1;
	
	
	public static int infected = 0;
	public static int imune = 0;
	public static int dead = 0;
	public static int healthy = 0;
	
	public static int animationSpeed = 7;
	
	public static int infectionDistance = 10;
	public static int allowedDistance = 200;
	public static int infectionRisk = 2000;			// 1/infectionRisk = %
	public static boolean stop = false;


	public static double mortality = 2.57;	
}
