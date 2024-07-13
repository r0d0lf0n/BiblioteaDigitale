package Biblio;

import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import InsertBook.InsertBook;
import ShowCatalog.ShowCatalog;
import ShowCatalog.ShowCatalog;

public class LandingPage implements ActionListener {
	
	JFrame frame = new JFrame();
	JButton insertBookButton = new JButton("Open Insert Book Frame");
	JButton showCatalogButton = new JButton("Open Book catalog");
	
    LandingPage() {
		insertBookButton.setBounds(100, 160, 200, 40);
		insertBookButton.setFocusable(false);
		insertBookButton.addActionListener(this);
		
		showCatalogButton.setBounds(100, 160, 200, 40);
		showCatalogButton.setFocusable(false);
		showCatalogButton.addActionListener(this);
		
		frame.add(insertBookButton);
		frame.add(showCatalogButton);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
//		frame.setLocationRelativeTo(null);
		frame.pack();
		frame.setSize(600, 600);
		frame.setLayout(new FlowLayout());
		frame.setResizable(false);
		frame.setVisible(true);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == insertBookButton) {
//			frame.setVisible(false);
			InsertBook insertBook = new InsertBook();
			insertBook.setVisible(true);
		} 
		
		if (e.getSource() == showCatalogButton) {
//			frame.setVisible(false);
			ShowCatalog showCatalog = new ShowCatalog();
			showCatalog.setVisible(true);
//			showCatalog.main(null);
	    }
    }
}
