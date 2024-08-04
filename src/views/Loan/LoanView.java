package views.Loan;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.SpringLayout;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;

import controllers.views.LandingPageController;
import controllers.views.LoansController;
import models.db.LoanDAO;
import utils.Observer;

public class LoanView extends JFrame implements Observer{

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JTable loanTable;
	//private DefaultTableModel model;
	private JLabel lblNoLoans;
	private JTextField textField;
	private LoansController controller = null;
	NewLoanView newLoanView = null;
	

	/**
	 * Create the frame.
	 * @param landingPageController 
	 */
	public LoanView(LandingPageController landingPageController) {
		controller = new LoansController();
		
		setTitle("Gestione Prestiti");
		setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
		setBounds(100, 100, 600, 600);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setLocationRelativeTo(null);
		setContentPane(contentPane);
		SpringLayout sl_contentPane = new SpringLayout();
		contentPane.setLayout(sl_contentPane);

		JButton btnClose = new JButton("Close");
		btnClose.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				landingPageController.openLandingPanel();
			}
		});
		sl_contentPane.putConstraint(SpringLayout.WEST, btnClose, 0, SpringLayout.WEST, contentPane);
		sl_contentPane.putConstraint(SpringLayout.SOUTH, btnClose, -10, SpringLayout.SOUTH, contentPane);
		contentPane.add(btnClose);

		JButton btnNewLoan = new JButton("New Loan");
		sl_contentPane.putConstraint(SpringLayout.WEST, btnNewLoan, 0, SpringLayout.WEST, contentPane);
		btnNewLoan.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(newLoanView == null)
					newLoanView = new NewLoanView();
				newLoanView.setVisible(true);
			}
		});
		contentPane.add(btnNewLoan);

		JButton btnEditLoan = new JButton("Edit Loan");
		sl_contentPane.putConstraint(SpringLayout.NORTH, btnEditLoan, 19, SpringLayout.SOUTH, btnNewLoan);
		sl_contentPane.putConstraint(SpringLayout.WEST, btnEditLoan, 0, SpringLayout.WEST, btnClose);
		btnEditLoan.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				//FIXME empty
			}
		});
		contentPane.add(btnEditLoan);
		
		JButton btnReturnLoan = new JButton("Return Loan");
		sl_contentPane.putConstraint(SpringLayout.NORTH, btnReturnLoan, 21, SpringLayout.SOUTH, btnEditLoan);
		sl_contentPane.putConstraint(SpringLayout.WEST, btnReturnLoan, 0, SpringLayout.WEST, btnClose);
		btnReturnLoan.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				//FIXME empty
			}
		});
		contentPane.add(btnReturnLoan);

		JScrollPane scrollPane = new JScrollPane();
		sl_contentPane.putConstraint(SpringLayout.EAST, btnNewLoan, -41, SpringLayout.WEST, scrollPane);
		sl_contentPane.putConstraint(SpringLayout.EAST, btnEditLoan, -41, SpringLayout.WEST, scrollPane);
		sl_contentPane.putConstraint(SpringLayout.EAST, scrollPane, -10, SpringLayout.EAST, contentPane);
		sl_contentPane.putConstraint(SpringLayout.NORTH, scrollPane, 36, SpringLayout.NORTH, contentPane);
		sl_contentPane.putConstraint(SpringLayout.SOUTH, scrollPane, -20, SpringLayout.SOUTH, contentPane);
		contentPane.add(scrollPane);

		loanTable = new JTable();
		scrollPane.setViewportView(loanTable);
		sl_contentPane.putConstraint(SpringLayout.WEST, loanTable, 0, SpringLayout.WEST, contentPane);
		sl_contentPane.putConstraint(SpringLayout.SOUTH, loanTable, -211, SpringLayout.NORTH, btnClose);
		sl_contentPane.putConstraint(SpringLayout.EAST, loanTable, -420, SpringLayout.EAST, contentPane);
		sl_contentPane.putConstraint(SpringLayout.NORTH, loanTable, 85, SpringLayout.SOUTH, btnEditLoan);
		
	    lblNoLoans = new JLabel("There are no loans yet!");
		sl_contentPane.putConstraint(SpringLayout.SOUTH, lblNoLoans, -6, SpringLayout.NORTH, scrollPane);
		sl_contentPane.putConstraint(SpringLayout.EAST, lblNoLoans, -141, SpringLayout.EAST, contentPane);
		lblNoLoans.setHorizontalAlignment(SwingConstants.CENTER);
		contentPane.add(lblNoLoans);
		
		textField = new JTextField();
		sl_contentPane.putConstraint(SpringLayout.NORTH, btnNewLoan, 25, SpringLayout.SOUTH, textField);
		sl_contentPane.putConstraint(SpringLayout.WEST, scrollPane, 20, SpringLayout.EAST, textField);
		sl_contentPane.putConstraint(SpringLayout.NORTH, textField, -8, SpringLayout.NORTH, scrollPane);
		sl_contentPane.putConstraint(SpringLayout.WEST, textField, 0, SpringLayout.WEST, btnClose);
		sl_contentPane.putConstraint(SpringLayout.EAST, textField, 135, SpringLayout.WEST, contentPane);
		contentPane.add(textField);
		textField.setColumns(10);
		
		JLabel lblNewLabel = new JLabel(" Filter Loan");
		sl_contentPane.putConstraint(SpringLayout.NORTH, lblNewLabel, 0, SpringLayout.NORTH, lblNoLoans);
		sl_contentPane.putConstraint(SpringLayout.EAST, lblNewLabel, 0, SpringLayout.EAST, btnClose);
		contentPane.add(lblNewLabel);
		
		this.addWindowListener(new WindowAdapter() {
	         @Override
	         public void windowClosing(WindowEvent e) {
	        	 landingPageController.openLandingPanel();
	         }
	     });
		
		if (getLoans().size() == 0) { 
			initializeTable();
		}
		controller.addObserver(this);
		landingPageController.addObserver(this);
		
	}
	
	
//	private void populatePanel(LandingPageController landingPageController) {
//		if (getLoans().size() > 0) { 
//			configView();
//		} else {
//			showNoLoansLabel(true);
//			JDialog d = new CustomDialog().showDialog(this, "Warning!", "There are no loand yet!", "New Loan", "Cancel");
//			JButton btnOne = dialog.getButtonOne();
//			JButton btnTwo = dialog.getButtonTwo();
//			
//			btnOne.addActionListener(new ActionListener() {
//				public void actionPerformed(ActionEvent e) {
//					System.out.println("New loan!");
//					newLoanView = new NewLoanView(landingPageController);
//					d.setVisible(false);
//					d.dispose();
//					newLoanView.setVisible(true);
//					//LoanModel loanModel = new LoanModel();
//					//NewLoanController newLoanController = new NewLoanController(newLoanView, loanModel);
//				}
//			});
//		    
//			btnTwo.addActionListener(new ActionListener() {
//				public void actionPerformed(ActionEvent e) {
//					System.out.println("Closing dialog!");
//					d.setVisible(false);
//					d.dispose();
//				}
//			});
//
//			
//			d.setVisible(true);
//		}
//	}
	

//	private void configView() {
//		showNoLoansLabel(false);
//	}
	
	private void initializeTable() {
		Object[] columns = { "id", "User", "Book", "Start Loan", "End Loan" };
		DefaultTableModel model = new DefaultTableModel();
		model.setColumnIdentifiers(columns);
		
		for(int i = 1; i < getLoans().size(); i++) {
//			System.out.println(loans.get(i)[0]);
				model.addRow(new Object[] {i, getLoans().get(i).getUser_id(), getLoans().get(i).getBook_id()});
		}

		loanTable.setModel(model);
	}
	
	
	private List<LoanDAO> getLoans() {
		return controller.getLoans();
	}
	

	@Override
	public void update(String type, Object arg) {
		if(type.equals("OPEN_LOANS")) {
			this.setVisible(true);
		}
		if(type.equals("CLOSE_LOANS")){
			this.setVisible(false);
		}
	}
}
