package project;
import java.awt.Color;

import javax.swing.JFrame;
import javax.swing.JPanel;

public class Frame extends JFrame {
	Frame(JPanel panel){
		this.add(panel);
		this.setSize(495,1000);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setVisible(true);
		this.setLocationRelativeTo(null);
		
	}

}
