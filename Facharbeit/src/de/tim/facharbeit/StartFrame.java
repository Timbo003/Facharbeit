package de.tim.facharbeit;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;


public class StartFrame extends JPanel{
	private static JFrame frame;
	public static StartFrame instance;

	public StartFrame() {
		instance = this;
        frame = new JFrame("StartScreen");									
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setResizable(false);
		frame.getContentPane().setPreferredSize(new Dimension(500,500));	
		super.setBackground(Color.WHITE);
		super.setLayout(null);
		frame.add(this);
		frame.pack();						
		frame.setLocationRelativeTo(null);
        frame.setVisible(true);
        setupStartFrame();
    }
	
	public static void setupStartFrame() {
		JButton myButton = new JButton("Start Sim");
		myButton.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				Main.switchToSim();
//				instance.setVisible(false); //you can't see me!
				frame.dispose();
				System.out.println("press");
				
			}
		});
		myButton.setBounds(20, 20, 100, 100);
		myButton.setVisible(true);
        instance.add(myButton);
        instance.update();
	}
	
	public void update() {
		super.repaint();
	}

	@Override
	public void paint(Graphics graphics) {
        for (int i = Main.structures.size() - 1; i >= 0 ; i--) {
            Main.structures.get(i).draw(graphics);
        }
	}
}
