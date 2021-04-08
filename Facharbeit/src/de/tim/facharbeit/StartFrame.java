package de.tim.facharbeit;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.TextField;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSlider;
import javax.swing.event.AncestorEvent;
import javax.swing.event.AncestorListener;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;


public class StartFrame {
	private static JFrame frame;

	public StartFrame() {
        frame = new JFrame("StartScreen");									
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setResizable(false);
		frame.getContentPane().setPreferredSize(new Dimension(500,500));
		frame.setLayout(null);

        setupStartFrame();
		
		//ENDE nichts mehr danach
		frame.pack();						
		frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }
	
	public static void setupStartFrame() {
		JButton myButton = new JButton("Start Sim");
//		myButton.addActionListener(new ActionListener() {
//			
//			@Override
//			public void actionPerformed(ActionEvent e) {
//				Main.switchToSim();
//				frame.dispose();
//				System.out.println("press");
//				
//			}
//		});
		myButton.addActionListener((e)->{
			Main.switchToSim();
			frame.dispose();
			System.out.println("press");
		});
		myButton.setBounds(50, 50, 100, 100);
		myButton.setVisible(true);
        frame.add(myButton);
        
        
        JPanel SliderLable = new JPanel();
        SliderLable.setVisible(true);
        SliderLable.setBounds(200, 200, 200, 200);
        SliderLable.setBackground(Color.LIGHT_GRAY);
        
        
        //slider & text for Streets
        JLabel streetSliderText = new JLabel();
        streetSliderText.setBounds(0, 0, 100, 100);
        streetSliderText.setVisible(true);
        streetSliderText.setText("Streets: "+ Variables.streetCount);
        
        JSlider streetSlider = new JSlider(0, 50, Variables.streetCount);
        streetSlider.addChangeListener((e) -> {
        	Variables.streetCount = streetSlider.getValue();
        	streetSliderText.setText("Streets: " + streetSlider.getValue());
        });
        streetSlider.setBounds(0, 20, 100, 100);
        streetSlider.setVisible(true);
        SliderLable.add(streetSlider);
        SliderLable.add(streetSliderText);
        
        
        //slider & text for Infected
        JLabel infectedSliderText = new JLabel();
        infectedSliderText.setBounds(0, 0, 100, 100);
        infectedSliderText.setVisible(true);
        infectedSliderText.setText("Infected: "+ Variables.infectedCount);
        
        JSlider infectedSlider = new JSlider(0, 100, Variables.infectedCount);
        infectedSlider.addChangeListener((e) -> {
        	Variables.infectedCount = infectedSlider.getValue();
        	infectedSliderText.setText("Infected: " + infectedSlider.getValue());
        });
        infectedSlider.setBounds(0, 20, 100, 100);
        infectedSlider.setVisible(true);
        SliderLable.add(infectedSlider);
        SliderLable.add(infectedSliderText);
        
        
        //slider & text for Imune
        JLabel imuneSliderText = new JLabel();
        imuneSliderText.setBounds(0, 0, 100, 100);
        imuneSliderText.setVisible(true);
        imuneSliderText.setText("Imune: "+ Variables.imuneCount);
        
        JSlider imuneSlider = new JSlider(0, 100, Variables.imuneCount);
        imuneSlider.addChangeListener((e) -> {
        	Variables.imuneCount = imuneSlider.getValue();
        	imuneSliderText.setText("Imune: " + imuneSlider.getValue());
        });
        imuneSlider.setBounds(0, 20, 100, 100);
        imuneSlider.setVisible(true);
        SliderLable.add(imuneSlider);
        SliderLable.add(imuneSliderText);
        
        
       frame.add(SliderLable);
	}
}
