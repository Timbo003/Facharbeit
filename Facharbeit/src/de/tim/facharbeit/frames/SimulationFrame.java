package de.tim.facharbeit.frames;

import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Toolkit;
import java.awt.Window;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Iterator;

import javax.imageio.ImageIO;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSlider;

import de.tim.facharbeit.Day;
import de.tim.facharbeit.Main;
import de.tim.facharbeit.Variables;
import de.tim.facharbeit.graph.GraphManager;
import de.tim.facharbeit.structure.Block;
import de.tim.facharbeit.structure.Entrance;
import de.tim.facharbeit.structure.Garden;
import de.tim.facharbeit.structure.House;
import de.tim.facharbeit.structure.Human;
import de.tim.facharbeit.structure.Structure;
import de.tim.facharbeit.structure.manager.HumanManager;
import de.tim.facharbeit.structure.streets.Street;

public class SimulationFrame extends JPanel {

	private JFrame frame;
	private static JPanel controllPanel;
	private static JPanel varPanel;
	private static JPanel sliderPanel;
	private static JPanel buttonPanel;
	private static JPanel animationSpeedPanel;
	private static JPanel maskPanel;
	private static JPanel p1;
	private static JPanel p2;
	private static JPanel p3;
	private static JPanel p4;
	private static JPanel savePanel;

	private static Font varFont = new Font("Arial", Font.PLAIN, (int) (Variables.screenSize.getWidth() / 96));

	private static JLabel infectedLable = new JLabel();
	private static JLabel imuneLable = new JLabel();
	private static JLabel healthyLable = new JLabel();
	private static JLabel deadLable = new JLabel();
	private static JLabel dateLable = new JLabel();
	private static JLabel notDead = new JLabel();

	private static JButton maskButton = new JButton();
	public static JButton stopButton = new JButton();

	private static JSlider animationSpeedSlider = new JSlider(1, 10, Variables.animationSpeed);
	private static JLabel animationSpeedSliderText = new JLabel();

	private static JSlider maskSlider = new JSlider(0, 100, Variables.wearingMask);
	private static JLabel maskSliderText = new JLabel();
	
	private static JSlider maxPpInHouseSlider = new JSlider(1, 25, Variables.maxHumansInHouse);
	private static JLabel maxPpInHouseSliderText = new JLabel();

	private static JSlider allowedDistanceSlider = new JSlider(50, 1000, Variables.allowedDistance);
	private static JLabel allowedDistanceSliderText = new JLabel();

	public static SimulationFrame instance;
	public static Color buttonColor = new Color(230, 239, 244);
	
	public static JButton saveGraphButton;
	public static JButton saveSimButton;

	public SimulationFrame() {
		instance = this;
		frame = new JFrame("Simulation");//Name des Fensters	
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);//wird beendet wenn es geschlossen wird
		frame.setResizable(false);//man kann es nicht größer oder kleiner ziehen
		frame.getContentPane().setPreferredSize(Variables.screenSize);//die größe width & height vom screen
		super.setBounds(0, 0, Variables.screenSize.width, Variables.screenSize.height - 230);
		super.setBackground(Color.WHITE);//Hintergrund Farbe
		super.setLayout(null);//eigenes Layout später
		frame.setLayout(null);//eigenes Layout später
		frame.setExtendedState(JFrame.MAXIMIZED_BOTH);//soll maximiert werden wenn gestartet
		frame.add(this);

		setupSimulationFrame();

		frame.pack();
		frame.setLocationRelativeTo(null);
		frame.setVisible(true);
	}

	private void setupSimulationFrame() { // Panel for Buttens etc.
		controllPanel = new JPanel();
		controllPanel.setBounds(250, Variables.screenSize.height - 230, Variables.screenSize.width - 500, 190);
//		controllPanel.setBackground(new Color(114, 118, 125));
		controllPanel.setVisible(true);
		controllPanel.setLayout(null);

		setupButtonPanel();
		setupSliderPanel();
		setupVarPanel();
		setupSavePanel();
		frame.add(controllPanel);
	}
	
	private void setupButtonPanel() {
		// Stop Button
		buttonPanel = new JPanel();
		buttonPanel.setBounds(controllPanel.getWidth() / 2 - 150, 0, 300, controllPanel.getHeight());
//		buttonPanel.setBackground(Color.LIGHT_GRAY);
		buttonPanel.setVisible(true);
		buttonPanel.setLayout(null);

		stopButton = new JButton("Halt Stop");
		stopButton.setFont(varFont);
		stopButton.addActionListener((e) -> {
			if (Variables.stopLock == false) {
				if (Variables.stop) {
					stopButton.setText("Stop");
					Variables.stop = false;
				} else {
					stopButton.setText("Resume");
					Variables.stop = true;
				}
			}else {
				stopButton.setText("Simulation zu Ende");
			}

		});
		stopButton.setBounds(0, 0, buttonPanel.getWidth(), buttonPanel.getHeight() / 2 - 10);
		stopButton.setVisible(true);
		stopButton.setBackground(buttonColor);

		// Graph Button
		JButton showGraphButton = new JButton("Show Graph");
		showGraphButton.setFont(varFont);
		showGraphButton.addActionListener((e) -> {
			Main.switchToGraph();
			System.out.println("Graph");
		});
		showGraphButton.setBounds(0, buttonPanel.getHeight() / 2, buttonPanel.getWidth(),
				buttonPanel.getHeight() / 2 - 10);
		showGraphButton.setVisible(true);
		showGraphButton.setBackground(buttonColor);

		buttonPanel.add(showGraphButton);
		buttonPanel.add(stopButton);
		controllPanel.add(buttonPanel);
		HumanManager.refrechHumanHealthVar();
	}
	
	private void setupSavePanel() {
		savePanel = new JPanel();
		savePanel.setBounds((int) (Variables.screenSize.getWidth() - 200), Variables.screenSize.height - 230,150, controllPanel.getHeight());
//		savePanel.setBackground(Color.red);
		savePanel.setVisible(true);
		savePanel.setLayout(null);
		
		saveSimButton = new JButton();
		saveSimButton.setText("save Sim");
		saveSimButton.setBackground(buttonColor);
		saveSimButton.setFont(new Font("Arial", Font.PLAIN, 15));
		saveSimButton.setBounds(0, 0, savePanel.getWidth(), buttonPanel.getHeight() / 2 - 10);
		saveSimButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				
				 try {
					 getSaveSnapShot(frame, Variables.pathSim);
					 saveData(Variables.DataFile);
				} catch (Exception e2) {
					e2.printStackTrace();
				}
			}
		});
		savePanel.add(saveSimButton);
		
		saveGraphButton = new JButton();
		saveGraphButton.setText("save Graph");
		saveGraphButton.setFont(new Font("Arial", Font.PLAIN, 15));
		saveGraphButton.setBackground(buttonColor);
		saveGraphButton.setBounds(0, buttonPanel.getHeight() / 2, savePanel.getWidth(),
				buttonPanel.getHeight() / 2 - 10);
		saveGraphButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				Main.switchToGraph();
				 try {
					 getSaveSnapShot(GraphManager.graphFrame, Variables.pathGraph);
					 saveData(Variables.DataFile);
				} catch (Exception e2) {
					e2.printStackTrace();
				}
			}
		});
		savePanel.add(saveGraphButton);
		
		frame.add(savePanel);
	}
	
	public static void saveData(File file) {
		try {
			BufferedWriter writer = new BufferedWriter(new FileWriter(file));
		        for (Day day : Variables.days) {
		        	 writer.write(""+day);
				}
		         writer.close();
		}
		catch (IOException e) {
		e.printStackTrace();
		}
	}

	public static BufferedImage getScreenShot(Component component) {
        BufferedImage image = new BufferedImage(component.getWidth(), component.getHeight(), BufferedImage.TYPE_INT_RGB);
        // paints into image's Graphics
        component.paint(image.getGraphics());
        return image;
    }

    public static void getSaveSnapShot(Component component, String fileName) throws Exception {
        BufferedImage img = getScreenShot(component);
        // write the captured image as a PNG
        ImageIO.write(img, "png", new File(fileName));
    }

	private void setupSliderPanel() {
		sliderPanel = new JPanel();
		sliderPanel.setBounds(controllPanel.getWidth()/3*2, 0, controllPanel.getWidth()/3, controllPanel.getHeight() - 10);
//		sliderPanel.setBackground(Color.LIGHT_GRAY);
		sliderPanel.setVisible(true);
		sliderPanel.setLayout(null);

		// slider & text for animationSpeed
		animationSpeedPanel = new JPanel();
		animationSpeedPanel.setBounds(5, 10, sliderPanel.getWidth() - 10, 30);
//		animationSpeedPanel.setBackground(Color.red);
		animationSpeedPanel.setVisible(true);
		animationSpeedPanel.setLayout(null);

		animationSpeedSliderText.setBounds(animationSpeedPanel.getWidth()/2+5,
				animationSpeedPanel.getHeight() / 2 - 10, animationSpeedPanel.getWidth()/2-5, 20);
		animationSpeedSliderText.setVisible(true);
		animationSpeedSliderText.setText("Animation Speed: " + Variables.animationSpeed);
		animationSpeedSliderText.setFont(varFont);

		animationSpeedSlider.addChangeListener((e) -> {
			Variables.animationSpeed = animationSpeedSlider.getValue();
			animationSpeedSliderText.setText("Animation Speed: " + animationSpeedSlider.getValue());
		});
		animationSpeedSlider.setBounds(0, -5, animationSpeedPanel.getWidth() /2, 40);
		animationSpeedSlider.setVisible(true);

		animationSpeedPanel.add(animationSpeedSliderText);
		animationSpeedPanel.add(animationSpeedSlider);
		sliderPanel.add(animationSpeedPanel);
		controllPanel.add(sliderPanel);

		// Slider & Text & Button for Masks
		maskPanel = new JPanel();
		maskPanel.setBounds(5, 50, sliderPanel.getWidth() - 10, 130);
//		maskPanel.setBackground(Color.orange);
		maskPanel.setLayout(null);
		maskPanel.setVisible(true);

		maskSliderText.setBounds(maskPanel.getWidth()/2+5, 5, maskPanel.getWidth()/2-5, 20);
		maskSliderText.setVisible(true);
		maskSliderText.setText(maskSlider.getValue() + "% wearing masks");
		maskSliderText.setFont(varFont);

		maskSlider.addChangeListener((e) -> {
			if (Variables.maskButtonPressed == false) {
				Variables.wearingMask = maskSlider.getValue();
				maskSliderText.setText(maskSlider.getValue() + "% wearing a mask");
				
				Variables.howManyAreWearingMasks = (int) ((Variables.wearingMask * 0.01) * Variables.aliveAndWilling.size());
				maskButton.setText(Variables.howManyAreWearingMasks + " will wear a mask");
			}
		});
		maskSlider.setBounds(0, -5, maskPanel.getWidth()/2, 40);
		maskSlider.setVisible(true);

		maskPanel.add(maskSlider);
		maskPanel.add(maskSliderText);

		maskButton = new JButton(Variables.howManyAreWearingMasks + " will wear a mask");
		maskButton.setFont(varFont);
		maskButton.addActionListener((e) -> {
			if (Variables.maskButtonPressed) {
				Variables.maskButtonPressed = false;
				maskButton.setText(Variables.howManyAreWearingMasks + " will wear a mask");
				maskSliderText.setText(Variables.howManyAreWearingMasks + " will wear  a mask");
				for (Human human : Variables.aliveAndWilling) {
					human.isWearingMask = false;
				}

			} else {
				maskSliderText.setText("locked");
				maskButton.setText("off");
				Variables.maskButtonPressed = true;
				maskButton.setText(Variables.howManyAreWearingMasks + " are wearing a mask");

				for (int i = 0; i < Variables.howManyAreWearingMasks; i++) {
					Variables.aliveAndWilling.get(i).isWearingMask = true;
				}
			}
		});
		maskButton.setBounds(5, maskPanel.getHeight() - 85, maskPanel.getWidth() - 10, 85);
		maskButton.setVisible(true);
		maskButton.setBackground(buttonColor);

		maskPanel.add(maskButton);

		sliderPanel.add(maskPanel);
		controllPanel.add(sliderPanel);

	}

	private void setupVarPanel() {
		varPanel = new JPanel();
		varPanel.setBounds(20, 0, controllPanel.getWidth() / 3 , controllPanel.getHeight());
//		varPanel.setBackground(Color.GRAY);
		varPanel.setVisible(true);
		varPanel.setLayout(null);

		p1 = new JPanel();
		p1.setBounds(0, 0, varPanel.getWidth(), 50);
//		p1.setBackground(Color.blue);
		p1.setVisible(true);
		p1.setLayout(null);

		// infected
		infectedLable.setBounds(10 + p1.getWidth() / 3 * 0, -20, 200, 100);
		infectedLable.setVisible(true);
		infectedLable.setForeground(Color.red);
		infectedLable.setText("infected: " + Variables.infected);
		infectedLable.setFont(varFont);
		p1.add(infectedLable);

		// imune
		imuneLable.setBounds(10 + p1.getWidth() / 3 * 1, -20, 200, 100);
		imuneLable.setVisible(true);
		imuneLable.setForeground(Color.blue);
		imuneLable.setText("imune: " + Variables.imune);
		imuneLable.setFont(varFont);
		p1.add(imuneLable);

		// healthy
		healthyLable.setBounds(10 + p1.getWidth() / 3 * 2, -20, 200, 100);
		healthyLable.setForeground(new Color(0,205,0));
		healthyLable.setVisible(true);
		healthyLable.setText("healthy: " + Variables.healthy);
		healthyLable.setFont(varFont);
		p1.add(healthyLable);

		p2 = new JPanel();
		p2.setBounds(0, 50, varPanel.getWidth(), 50);
//		p2.setBackground(Color.cyan);
		p2.setVisible(true);
		p2.setLayout(null);

		// dead
		deadLable.setBounds(10 + p2.getWidth() / 3 * 0, -20, 200, 100);
		deadLable.setVisible(true);
		deadLable.setForeground(Color.gray);
		deadLable.setText("dead: " + Variables.dead);
		deadLable.setFont(varFont);
		p2.add(deadLable);

		// gesHumans
		notDead.setBounds(10 + p2.getWidth() / 3 * 1, -20, 200, 100);
		notDead.setVisible(true);
		notDead.setText("alive: " + Main.getAllLifingHumans().size());
		notDead.setFont(varFont);
		p2.add(notDead);

		// date
		dateLable.setBounds(10 + p2.getWidth() / 3 * 2, -20, 200, 100);
		dateLable.setVisible(true);
		dateLable.setText("date: " + (Variables.days.size()));
		dateLable.setFont(varFont);
		p2.add(dateLable);

		p3 = new JPanel();
		p3.setBounds(0, 100, varPanel.getWidth(), 40);
//		p3.setBackground(Color.orange);
		p3.setVisible(true);
		p3.setLayout(null);

		maxPpInHouseSliderText.setBounds(p3.getWidth()/2 + 5,
				15, p3.getWidth()/2 + 10, 20);
		maxPpInHouseSliderText.setVisible(true);
		maxPpInHouseSliderText.setText("max Besucher: " + Variables.maxHumansInHouse);
		maxPpInHouseSliderText.setFont(varFont);

		maxPpInHouseSlider.addChangeListener((e) -> {
			Variables.maxHumansInHouse = maxPpInHouseSlider.getValue();
			maxPpInHouseSliderText.setText("max Besucher: " + Variables.maxHumansInHouse);
		});
		maxPpInHouseSlider.setBounds(0, 5, p3.getWidth()/2, 40);
		maxPpInHouseSlider.setVisible(true);

		p3.add(maxPpInHouseSlider);
		p3.add(maxPpInHouseSliderText);
		
		p4 = new JPanel();
		p4.setBounds(0, 140, varPanel.getWidth(), 50);
//		p4.setBackground(Color.pink);
		p4.setVisible(true);
		p4.setLayout(null);

		allowedDistanceSliderText.setBounds(p4.getWidth()/2 + 5 ,
				15, p4.getWidth()/2-10, 20);
		allowedDistanceSliderText.setVisible(true);
		allowedDistanceSliderText.setText("allowed Distance: " + Variables.allowedDistance);
		allowedDistanceSliderText.setFont(varFont);

		allowedDistanceSlider.addChangeListener((e) -> {
			Variables.allowedDistance = allowedDistanceSlider.getValue();
			allowedDistanceSliderText.setText("allowed Distance: " + Variables.allowedDistance);
		});
		allowedDistanceSlider.setBounds(0, 5, p4.getWidth()/2, 40);
		allowedDistanceSlider.setVisible(true);

		p4.add(allowedDistanceSlider);
		p4.add(allowedDistanceSliderText);

		varPanel.add(p1);
		varPanel.add(p2);
		varPanel.add(p3);
		varPanel.add(p4);
		controllPanel.add(varPanel);
	}

	public static void updateDate() {
		dateLable.setText("date: " + (Variables.days.size()));
	}

	public static void updateText() {
		HumanManager.refrechHumanHealthVar();

		notDead.setText("alive: " + Variables.alive);
		infectedLable.setText("infected: " + Variables.infected);
		imuneLable.setText("imune: " + Variables.imune);
		healthyLable.setText("healthy: " + Variables.healthy);
		deadLable.setText("dead: " + Variables.dead);
	}

	public void update() {
		super.repaint();
	}

	@Override
	public void paint(Graphics graphics) {
		graphics.setColor(Color.WHITE);
		graphics.fillRect(0, 0, Variables.screenSize.width, Variables.screenSize.height - 230);

		final Class<?>[] structures = {Street.class, Block.class,Garden.class, House.class, Entrance.class, Human.class};
		
		for (Class<?> c  : structures) {
			for (int i = 0; i < Main.structures.size(); i++) {
				Structure structure = Main.structures.get(i);
				if (structure.getClass().equals(c)) {
					structure.draw(graphics);
				}
			}
		}
	}
}
