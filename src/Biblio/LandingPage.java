package Biblio;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import InsertBook.InsertBook;

public class LandingPage implements ActionListener {
	
	JFrame frame = new JFrame();
	JButton insertBookButton = new JButton("Open Insert Book Frame");
	
    LandingPage() {
		insertBookButton.setBounds(100, 160, 200, 40);
		insertBookButton.setFocusable(false);
		insertBookButton.addActionListener(this);
		
		frame.add(insertBookButton);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setLocationRelativeTo(null);
		frame.setSize(600, 600);
		frame.setLayout(null);
		frame.setResizable(false);
		frame.setVisible(true);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == insertBookButton) {
			frame.setVisible(false);
			InsertBook insertBook = new InsertBook();
		}
	}
	

//	super.setTitle("Welcome Screen");
//	super.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
//	super.setBounds(100, 100, 732, 679);
//	contentPane = new JPanel();
//	contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
//
//	super.setContentPane(contentPane);
//	
//	JButton insertBookButton = new JButton("Open Insert Book Frame");
//	insertBookButton.addActionListener(new ActionListener() {
//		public void actionPerformed(ActionEvent e) {
//			InsertBook insertBook = new InsertBook();
//		}
//	});
//	
//	contentPane.add(insertBookButton);
}
