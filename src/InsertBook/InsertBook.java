package InsertBook;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JFrame;
import javax.swing.JLabel;

public class InsertBook {
	
	JFrame frame = new JFrame();
	JLabel label = new JLabel("Test");
	
	public InsertBook(){
		label.setBounds(100, 160, 200, 40);
		frame.add(label);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setLocationRelativeTo(null);
		frame.setSize(600, 600);
		frame.setLayout(null);
		frame.setResizable(false);
		frame.setVisible(true);
	}
}
