package de.tim.facharbeit;

import java.awt.Dimension;
import java.awt.Font;
import java.awt.Toolkit;
import java.util.ArrayList;
import java.util.List;
import java.util.Timer;

public class Variables {
	public static String pathGraph = "C:\\Users\\Tim\\Desktop\\GraphExport.png";
	public static String pathSim = "C:\\Users\\Tim\\Desktop\\SimExport.png";
	
	public static List<Day> days = new ArrayList<>();
	public static List<Timer> activeTimers = new ArrayList<>();

	public static Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
	
	public static Font defaultFont  = new Font("Arial", Font.PLAIN, 19);
	
	public static int streetCount = 10;
	
	public static int infectedCount = 10;
	public static int imuneCount = 5;
	
	public static int totalHumanCounter = 200;
	public static int maxHumansInHome = 4;
	public static int maxHumansInHouse = 1;
	
	public static int bedachtCount = 1;
	public static int verweigererCount = 1;
	
	public static boolean infectionRiskInputOk = true;
	public static boolean mortalityInputOk = true;
	public static boolean maskEffectivityInputOk = true;
	
	public static int infected = 0;
	public static int imune = 0;
	public static int dead = 0;
	public static int healthy = 0;
	public static int alive = 0;
	
	public static int animationSpeed = 7;
	public static boolean stop = false;
	public static boolean stopLock = false;
	public static int wearingMask = 0; 			//in %
	public static int howManyAreWearingMasks = 0;
	public static boolean maskButtonPressed = false;		
	
	public static int maxTimeSick = 4;
	public static int infectionDistance = 10;
	public static int allowedDistance = 300;
	
//	public static double infectionRisk = 100;
	public static double infectionRisk = 0.005;		//in %
	public static double mortality = 20;			//in %
	public static double maskEffectivity = 20;		//in %
}
