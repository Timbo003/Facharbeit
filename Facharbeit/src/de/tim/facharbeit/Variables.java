package de.tim.facharbeit;

import java.awt.Dimension;
import java.awt.Font;
import java.awt.Toolkit;
import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Timer;

import de.tim.facharbeit.structure.Human;

public class Variables {
	
	public static String pathGraph;														//Pfade zum speichern 
	public static String pathSim;
	public static String pathText;
	public static File DataFile;
		
	public static List<Day> days = new ArrayList<>();									//alle bisherigen Tage 
	public static List<Timer> activeTimers = new ArrayList<>();							//alle aktiven Timer

	public static Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();	//screen size für SimFrame
	
	public static Font defaultFont  = new Font("Arial", Font.PLAIN, 17);				
	
	public static int minimumDistance = 100;											//min ditanz zwischen den Straßen
	
	public static int streetCount = 15;													//wie viele Straßen sollen zu den 4 Randstraßen erzeugt werden  
	
	public static int infectedCount = 10;												//wie viele Infizierte
	public static int imuneCount = 5;													//wie viele Imune
	
	public static int totalHumanCounter = 200;											//wie viele Menschen insgesamt
	public static int maxHumansInHome = 4;												//wie groß ist eine Familie
	public static int maxHumansInHouse = 1;												//wieviele Menschen sind in einem Haus zugelassen
	
	public static int bedachtCount = 1;													//wie viele Bedachte													
	public static int verweigererCount = 1;												//wie viele Verweigerer
	
	public static boolean infectionRiskInputOk = true;									//passt die eingabe des infRisks
	public static boolean mortalityInputOk = true;										//passt die eingabe der mortality
	public static boolean maskEffectivityInputOk = true;								//passt die eingabe der maskEff
		
	public static int infected = 0;														//wieviele Kranke gibt es momentan
	public static int imune = 0;														//wieviele Imune gibt es momentan
	public static int dead = 0;															//wieviele Tote gibt es momentan
	public static int healthy = 0;														//wieviele Gesunde gibt es momentan
	public static int alive = 0;														//wieviele Lebenden gibt es momentan
	
	public static int animationSpeed = 7;												//Animationgeschwindigkeit
	public static boolean stop = false;													//soll die sim anhalten
	public static boolean stopLock = false;												//ist die sim zuende
	public static int wearingMask = 0; 													//wie viel % tragen masken
	public static int howManyAreWearingMasks = 0;										//prozent angewendet auf alle menschen die Masken tragen können
	public static List<Human> aliveAndWilling;											//Menschen die Masken tragen können
	public static boolean maskButtonPressed = false;									//tragen die Menschen schon masken oder nicht	
	
	public static int maxTimeSick = 4;													//wie lange bis man imun wird
	public static int infectionDistance = 10;											//abstand in dem man krank werden kann
	public static int allowedDistance = 300;											//wei eit darf ein mensch von seinem Home weg
	
	public static double infectionRisk = 0.005;											//Infektionsrisiko in %	
	public static double mortality = 20;												//Mortalität in %
	public static double maskEffectivity = 20;											//Effektivität einer Maske in %
	
	public static boolean useFixedDayLength = false;										//sollen die tage alle gleich lang sein
	public static int dayLength = 110000;												//Länge eines Tags
}
