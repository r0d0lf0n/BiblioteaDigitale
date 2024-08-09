package views.Loan;

import java.awt.Dimension;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.text.SimpleDateFormat;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.SpringLayout;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.table.DefaultTableModel;

import controllers.views.LandingPageController;
import controllers.views.LoansController;
import models.db.LoanDAO;
import utils.CustomDialog;
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
	UpdateLoanView updateLoanView = null;
	//List<LoanDAO> loans = null;
	private CustomDialog dialog;
	int centerX;
	int centerY;
	private LandingPageController landingPageController = null;

	

	/**
	 * Create the frame.
	 * @param landingPageController 
	 */
	public LoanView(LandingPageController landingPageController) {
		this.landingPageController = landingPageController;
		Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        int screenWidth = screenSize.width;
        int screenHeight = screenSize.height;
	    // Calcola il punto centrale dello schermo
	    centerX = screenWidth / 2;
	    centerY = screenHeight / 2;
	    
		controller = new LoansController();
		dialog = new CustomDialog();
		setTitle("Gestione Prestiti");
		setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
		setBounds(100, 100, 800, 600);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setLocationRelativeTo(null);
		setContentPane(contentPane);
		SpringLayout sl_contentPane = new SpringLayout();
		contentPane.setLayout(sl_contentPane);

		JButton btnClose = new JButton("Close");
		sl_contentPane.putConstraint(SpringLayout.SOUTH, btnClose, -60, SpringLayout.SOUTH, contentPane);
		btnClose.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				landingPageController.openLandingPanel();
			}
		});
		contentPane.add(btnClose);

		JButton btnNewLoan = new JButton("Inserisci nuovo prestito");
		sl_contentPane.putConstraint(SpringLayout.WEST, btnNewLoan, 8, SpringLayout.WEST, contentPane);
		sl_contentPane.putConstraint(SpringLayout.SOUTH, btnNewLoan, -148, SpringLayout.SOUTH, contentPane);
		btnNewLoan.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(newLoanView == null)
					newLoanView = new NewLoanView(controller);
				newLoanView.setVisible(true);
			}
		});
		contentPane.add(btnNewLoan);

		JButton btnEditLoan = new JButton("Modifica prestito selezionato");
		sl_contentPane.putConstraint(SpringLayout.NORTH, btnEditLoan, 8, SpringLayout.SOUTH, btnNewLoan);
		sl_contentPane.putConstraint(SpringLayout.WEST, btnEditLoan, 0, SpringLayout.WEST, btnNewLoan);
		sl_contentPane.putConstraint(SpringLayout.EAST, btnEditLoan, -2, SpringLayout.EAST, btnNewLoan);
		btnEditLoan.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String loanId = loanTable.getValueAt(loanTable.getSelectedRow(), 0) != null ? loanTable.getValueAt(loanTable.getSelectedRow(), 0).toString() : "-1";
        		LoanDAO selectedLoan = controller.getLoanById(Integer.valueOf(loanId));
        	    updateLoanView = new UpdateLoanView(controller, selectedLoan);
        	    updateLoanView.setVisible(true);
			}
		});
		contentPane.add(btnEditLoan);

		JScrollPane scrollPane = new JScrollPane();
		sl_contentPane.putConstraint(SpringLayout.EAST, btnClose, 0, SpringLayout.EAST, scrollPane);
		sl_contentPane.putConstraint(SpringLayout.SOUTH, scrollPane, -27, SpringLayout.NORTH, btnNewLoan);
		sl_contentPane.putConstraint(SpringLayout.EAST, scrollPane, -15, SpringLayout.EAST, contentPane);
		contentPane.add(scrollPane);

		loanTable = new JTable();
		scrollPane.setViewportView(loanTable);
		sl_contentPane.putConstraint(SpringLayout.WEST, loanTable, 0, SpringLayout.WEST, contentPane);
		sl_contentPane.putConstraint(SpringLayout.SOUTH, loanTable, -211, SpringLayout.NORTH, btnClose);
		sl_contentPane.putConstraint(SpringLayout.EAST, loanTable, -420, SpringLayout.EAST, contentPane);
		sl_contentPane.putConstraint(SpringLayout.NORTH, loanTable, 85, SpringLayout.SOUTH, btnEditLoan);
		
	    lblNoLoans = new JLabel("Non \u00E8 presente alcun prestito.");
	    sl_contentPane.putConstraint(SpringLayout.NORTH, scrollPane, 9, SpringLayout.SOUTH, lblNoLoans);
	    sl_contentPane.putConstraint(SpringLayout.EAST, btnNewLoan, 29, SpringLayout.EAST, lblNoLoans);
	    sl_contentPane.putConstraint(SpringLayout.WEST, lblNoLoans, 10, SpringLayout.WEST, contentPane);
		lblNoLoans.setHorizontalAlignment(SwingConstants.CENTER);
		contentPane.add(lblNoLoans);
		
		textField = new JTextField();
		sl_contentPane.putConstraint(SpringLayout.NORTH, lblNoLoans, 6, SpringLayout.SOUTH, textField);
		sl_contentPane.putConstraint(SpringLayout.WEST, scrollPane, 0, SpringLayout.WEST, textField);
		sl_contentPane.putConstraint(SpringLayout.WEST, textField, 10, SpringLayout.WEST, contentPane);
		sl_contentPane.putConstraint(SpringLayout.EAST, textField, 145, SpringLayout.WEST, contentPane);
		contentPane.add(textField);
		textField.setColumns(10);
		textField.getDocument().addDocumentListener(new DocumentListener() {
		    @Override
		    public void insertUpdate(DocumentEvent e) {
		    	String txt = textField.getText();
		    	if (isInteger(txt))
		    		filteredLoansByUser(txt);
		    }

		    @Override
		    public void removeUpdate(DocumentEvent e) {
		    	String txt = textField.getText();
		    	if (isInteger(txt))
		    		filteredLoansByUser(txt);
		    }

		    @Override
		    public void changedUpdate(DocumentEvent e) {}
		});
		
		JLabel lblNewLabel = new JLabel(" Ricerca prestito:");
		sl_contentPane.putConstraint(SpringLayout.NORTH, lblNewLabel, 7, SpringLayout.NORTH, contentPane);
		sl_contentPane.putConstraint(SpringLayout.WEST, lblNewLabel, 6, SpringLayout.WEST, contentPane);
		sl_contentPane.putConstraint(SpringLayout.NORTH, textField, 17, SpringLayout.SOUTH, lblNewLabel);
		contentPane.add(lblNewLabel);
		
		JButton btnRefresh = new JButton("Aggiorna");
		btnRefresh.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				initializeTable();
			}
		});
		sl_contentPane.putConstraint(SpringLayout.SOUTH, btnRefresh, -6, SpringLayout.NORTH, scrollPane);
		sl_contentPane.putConstraint(SpringLayout.EAST, btnRefresh, 0, SpringLayout.EAST, btnClose);
		contentPane.add(btnRefresh);
		
		this.addWindowListener(new WindowAdapter() {
	         @Override
	         public void windowClosing(WindowEvent e) {
	        	 landingPageController.openLandingPanel();
	         }
	     });

		controller.addObserver(this);
		landingPageController.addObserver(this);
		
	}
	
	public static boolean isInteger(String s) {
	    try { 
	        Integer.parseInt(s); 
	    } catch(NumberFormatException e) { 
	        return false; 
	    } catch(NullPointerException e) {
	        return false;
	    }
	    // only got here if we didn't return false
	    return true;
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
		Object[] columns = { "Id", "User", "Book", "Start Date", "End Date" };
		DefaultTableModel model = new DefaultTableModel();
		model.setColumnIdentifiers(columns);
		List<LoanDAO> loans = controller.getLoans();
		
		for(int i = 0; i < loans.size(); i++) {
			System.out.println(loans.get(i).getUser_id());
			SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
			String start = loans.get(i).getStart_date() != null ? sdf.format(loans.get(i).getStart_date()) : "";
			String end = loans.get(i).getEnd_date() != null ? sdf.format(loans.get(i).getEnd_date()) : "";
			model.addRow(new Object[] {loans.get(i).getId(), loans.get(i).getUser_id().getId(), loans.get(i).getBook_id().getId(), start, end});
		}

		loanTable.setModel(model);
	}
	
	
	
	private void filteredLoansByUser(String criteria) {
		Object[] columns = { "Id", "User", "Book", "Start Date", "End Date" };
		DefaultTableModel model = new DefaultTableModel();
		model.setColumnIdentifiers(columns);
		
    	List<LoanDAO> list = controller.getLoansByRegex(criteria);
		for (LoanDAO u : list) {
			SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
			String start = u.getStart_date() != null ? sdf.format(u.getStart_date()) : "";
			String end = u.getEnd_date() != null ? sdf.format(u.getEnd_date()) : "";
			model.addRow(new Object[] {u.getId(), u.getUser_id(), u.getBook_id(),start, end});
		}
		
		loanTable.setModel(model);
	}
	
	@Override
	public void update(String type, Object arg) {
		if(type.equals("OPEN_LOANS")) {
			if (controller.getLoans().size() == 0) { 
				lblNoLoans.setVisible(true);
				int width = 280;
				int height = 100;		
				JDialog d = dialog.buildDialog(this, "Warning!", "There are no loand yet!", "New Loan", "Cancel", centerX - width, centerY - height, width, height);
				JButton btnOne = dialog.getButtonOne();
				JButton btnTwo = dialog.getButtonTwo();
				btnOne.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
//				    	System.out.println("New loan!");
						d.setVisible(false);
						d.dispose();
						landingPageController.openLandingPanel();
						newLoanView = new NewLoanView(controller);
						controller.openNewLoan();
						//controller.setChanged("OPEN_NEW_LOAN", null);
						//LoanModel loanModel = new LoanModel();
						//NewLoanController newLoanController = new NewLoanController(newLoanView, loanModel);
					}
				});
				
				btnTwo.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						//System.out.println("Closing dialog!");
						d.setVisible(false);
						d.dispose();
						landingPageController.openLandingPanel();
					}
				});
				
				d.addWindowListener(new WindowAdapter() {
					
					@Override
					public void windowClosing(WindowEvent e) {
						dispose();
						landingPageController.openLandingPanel();
					}
				});
				
				d.setVisible(true);
				lblNoLoans.setVisible(true);
			} else {
				this.setVisible(true);
				lblNoLoans.setVisible(false);
				initializeTable();
			}
		} 
		
		if(type.equals("CLOSE_LOANS")){
			this.setVisible(false);
		}
		
		if(type.equals("REFRESH_LOANS_DETAIL")) {
			initializeTable();
		}
	}
}
