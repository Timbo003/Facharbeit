package de.tim.facharbeit.frames;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;

import javax.swing.JFrame;

import de.tim.facharbeit.Main;

public class ScoreFrame {

	private JFrame frame;
	public static ScoreFrame instance;

	public ScoreFrame() {
		frame = new JFrame("StartScreen");
		frame.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
		frame.setResizable(false);
		frame.getContentPane().setPreferredSize(new Dimension(500, 500));
		frame.setLayout(null);

		setupScoreFrame();

		// ENDE nichts mehr danach
		frame.pack();
		frame.setLocationRelativeTo(null);
		frame.setVisible(true);
    }

	private void setupScoreFrame() {
		
	}
}

