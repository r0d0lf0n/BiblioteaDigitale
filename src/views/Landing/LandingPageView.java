package views.Landing;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.SpringLayout;
import javax.swing.border.EmptyBorder;

import java.awt.EventQueue;

import javax.swing.BorderFactory;
import javax.swing.JButton;

public class LandingPageView extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	JButton btnOpenLoans;
	JButton btnOpenCatalog;
	JButton btnOpenAdvancedSearch;
	
//	/**
//	 * Launch the application.
//	 */
//	public static void main(String[] args) {
//		EventQueue.invokeLater(new Runnable() {
//			public void run() {
//				try {
//					Test frame = new Test();
//					frame.setVisible(true);
//				} catch (Exception e) {
//					e.printStackTrace();
//				}
//			}
//		});
//	}

	/**
	 * Create the frame.
	 */
	public LandingPageView() {
		setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
		setBounds(100, 100, 190, 537);
		contentPane = new JPanel();
//		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
//		contentPane.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
		contentPane.setBorder(BorderFactory.createTitledBorder("Dati Utente"));
		SpringLayout springLayout = new SpringLayout();
		getContentPane().setLayout(springLayout);
	
		
		btnOpenLoans = new JButton("Open Loans");
		springLayout.putConstraint(SpringLayout.NORTH, btnOpenLoans, 21, SpringLayout.NORTH, getContentPane());
		springLayout.putConstraint(SpringLayout.WEST, btnOpenLoans, 19, SpringLayout.WEST, getContentPane());
		springLayout.putConstraint(SpringLayout.EAST, btnOpenLoans, 169, SpringLayout.WEST, getContentPane());
		getContentPane().add(btnOpenLoans);
		
	    btnOpenCatalog = new JButton("Open Catalog");
	    springLayout.putConstraint(SpringLayout.WEST, btnOpenCatalog, 0, SpringLayout.WEST, btnOpenLoans);
	    springLayout.putConstraint(SpringLayout.SOUTH, btnOpenCatalog, -414, SpringLayout.SOUTH, getContentPane());
	    springLayout.putConstraint(SpringLayout.EAST, btnOpenCatalog, -21, SpringLayout.EAST, getContentPane());
		getContentPane().add(btnOpenCatalog);
		
		btnOpenAdvancedSearch = new JButton("Advanced Search");
		springLayout.putConstraint(SpringLayout.NORTH, btnOpenAdvancedSearch, 17, SpringLayout.SOUTH, btnOpenCatalog);
		springLayout.putConstraint(SpringLayout.EAST, btnOpenAdvancedSearch, 0, SpringLayout.EAST, btnOpenLoans);
		getContentPane().add(btnOpenAdvancedSearch);		
	}
	
	public JButton getOpenLoansButton() {
		return btnOpenLoans;
	}
	
	public JButton getOpenCatalogButton() {
		return btnOpenCatalog;
	}
	
	public JButton getAdvancedSearchButton() {
		return btnOpenAdvancedSearch;
	}
}
