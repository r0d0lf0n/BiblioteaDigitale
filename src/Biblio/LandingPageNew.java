package Biblio;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import InsertBook.InsertBookView;
import ShowCatalog.ShowCatalogView;

import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.SpringLayout;
import javax.swing.JLabel;

public class LandingPageNew extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					LandingPageNew frame = new LandingPageNew();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public LandingPageNew() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

		setContentPane(contentPane);
		
		JButton btnInsertBook = new JButton("Open Insert Book Window");
		btnInsertBook.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				InsertBookView insertBook = new InsertBookView();
				insertBook.setVisible(true);
			}
		});
		SpringLayout sl_contentPane = new SpringLayout();
		sl_contentPane.putConstraint(SpringLayout.NORTH, btnInsertBook, 10, SpringLayout.NORTH, contentPane);
		sl_contentPane.putConstraint(SpringLayout.WEST, btnInsertBook, 17, SpringLayout.WEST, contentPane);
		contentPane.setLayout(sl_contentPane);
		contentPane.add(btnInsertBook);
		
		JButton btnShowCatalog = new JButton("Open Show Catalog Window");
		sl_contentPane.putConstraint(SpringLayout.NORTH, btnShowCatalog, 19, SpringLayout.SOUTH, btnInsertBook);
		sl_contentPane.putConstraint(SpringLayout.WEST, btnShowCatalog, 0, SpringLayout.WEST, btnInsertBook);
		btnShowCatalog.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				ShowCatalogView showCatalog = new ShowCatalogView();
				showCatalog.setVisible(true);
			}
		});
		contentPane.add(btnShowCatalog);
		
		JLabel lblNewLabel = new JLabel("LANDING PAGE (To Be Designed)");
		sl_contentPane.putConstraint(SpringLayout.NORTH, lblNewLabel, 68, SpringLayout.SOUTH, btnShowCatalog);
		sl_contentPane.putConstraint(SpringLayout.EAST, lblNewLabel, -101, SpringLayout.EAST, contentPane);
		contentPane.add(lblNewLabel);
	}
}
