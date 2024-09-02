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
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.SpringLayout;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;

import controllers.views.LandingPageController;
import controllers.views.LoansController;
import models.db.BookDAO;
import models.db.LoanDAO;
import models.db.UserDAO;
import models.users.Utente;
import utils.CustomDialog;
import utils.Observer;

public class LoanViewForUser extends JFrame implements Observer{

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JTable loanTable;
	//private DefaultTableModel model;
	private JLabel lblNoLoans;
	private LoansController controller = null;
	NewLoanViewForUser newLoanView = null;
	UpdateLoanView updateLoanView = null;
	//List<LoanDAO> loans = null;
	private CustomDialog dialog;
	int centerX;
	int centerY;
	private LandingPageController landingPageController = null;
	private int idTessera = -1;
	private UserDAO selectedUser;
	private Utente user;
	

	/**
	 * Create the frame.
	 * @param landingPageController 
	 */
	public LoanViewForUser(LandingPageController landingPageController, Utente user) {
//		this.user = u;
		this.user = user;
		this.idTessera = user.getIdTessera();
		this.landingPageController = landingPageController;
		Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        int screenWidth = screenSize.width;
        int screenHeight = screenSize.height;
	    // Calcola il punto centrale dello schermo
	    centerX = screenWidth / 2;
	    centerY = screenHeight / 2;
	    
		controller = new LoansController();
		dialog = new CustomDialog();
		setTitle("Gestione Prestiti - "+user.getNome()+" "+user.getCognome());
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
		sl_contentPane.putConstraint(SpringLayout.EAST, btnClose, -15, SpringLayout.EAST, contentPane);
		btnClose.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				landingPageController.openLandingPanelUser();
			}
		});
		contentPane.add(btnClose);

		JButton btnNewLoan = new JButton("Inserisci nuovo prestito");
		sl_contentPane.putConstraint(SpringLayout.WEST, btnNewLoan, 8, SpringLayout.WEST, contentPane);
		sl_contentPane.putConstraint(SpringLayout.SOUTH, btnNewLoan, -148, SpringLayout.SOUTH, contentPane);
		sl_contentPane.putConstraint(SpringLayout.EAST, btnNewLoan, -561, SpringLayout.EAST, contentPane);
		btnNewLoan.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				//if(newLoanView == null)
				newLoanView = new NewLoanViewForUser(controller, user);
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
				if (!loanTable.getSelectionModel().isSelectionEmpty()) {		
					String loanId = loanTable.getValueAt(loanTable.getSelectedRow(), 0) != null ? loanTable.getValueAt(loanTable.getSelectedRow(), 0).toString() : "-1";
	        		LoanDAO selectedLoan = controller.getLoanById(Integer.valueOf(loanId));
	        	    updateLoanView = new UpdateLoanView(controller, selectedLoan, true);
	        	    updateLoanView.setVisible(true);
				} else {
	       			JOptionPane.showMessageDialog(updateLoanView, "Selezionare il prestito per modificare!");
				}
			}
		});
		contentPane.add(btnEditLoan);

		JScrollPane scrollPane = new JScrollPane();
		sl_contentPane.putConstraint(SpringLayout.WEST, scrollPane, 10, SpringLayout.WEST, contentPane);
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
	    sl_contentPane.putConstraint(SpringLayout.NORTH, lblNoLoans, 29, SpringLayout.NORTH, contentPane);
	    sl_contentPane.putConstraint(SpringLayout.WEST, lblNoLoans, 0, SpringLayout.WEST, btnNewLoan);
		lblNoLoans.setHorizontalAlignment(SwingConstants.CENTER);
		contentPane.add(lblNoLoans);
		
		JButton btnRefresh = new JButton("Aggiorna");
		sl_contentPane.putConstraint(SpringLayout.NORTH, scrollPane, 8, SpringLayout.SOUTH, btnRefresh);
		sl_contentPane.putConstraint(SpringLayout.NORTH, btnRefresh, -5, SpringLayout.NORTH, lblNoLoans);
		sl_contentPane.putConstraint(SpringLayout.EAST, btnRefresh, 0, SpringLayout.EAST, btnClose);
		btnRefresh.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
//				if (idTessera == -1) {
//					initializeTable();
//				} else {
					UserDAO user = controller.getUserByTesseraId(idTessera);
					filteredLoansByTessera(String.valueOf(user.getId()));
//				}
			}
		});
		contentPane.add(btnRefresh);
		
		this.addWindowListener(new WindowAdapter() {
	         @Override
	         public void windowClosing(WindowEvent e) {
	        	 landingPageController.openLandingPanel();
	         }
	     });

		controller.addObserver(this);
		landingPageController.addObserver(this);
		
		selectedUser = controller.getUserByTesseraId(idTessera);
		filteredLoansByTessera(String.valueOf(selectedUser.getId()));
		
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
	
	private void filteredLoansByTessera(String criteria) {
		Object[] columns = { "Id", "Utente", "Libro", "Inizio Prestito", "Fine Prestito" };
		DefaultTableModel model = new DefaultTableModel();
		model.setColumnIdentifiers(columns);	
		
    	List<LoanDAO> list = controller.getLoansByTessera(Integer.valueOf(criteria));
    	if (list.size() > 0) {
    		lblNoLoans.setText("");
    	}
		for (LoanDAO l : list) {
//		    selectedUser  = controller.getUserById(idTessera);
			BookDAO b  = controller.getBookById(l.getBook_id().getId());
			SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
			String start = l.getStart_date() != null ? sdf.format(l.getStart_date()) : "";
			String end = l.getEnd_date() != null ? sdf.format(l.getEnd_date()) : "";
			if (selectedUser != null && b != null) {
				model.addRow(new Object[] {l.getId(), selectedUser.getName() + " " + selectedUser.getSurname(), b.getTitle(), start, end});
			}
		}
		
		loanTable.setModel(model);
	}
	
	
	@Override
	public void update(String type, Object arg) {
		if(type.equals("OPEN_LOANS_USER")) {
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
						newLoanView = new NewLoanViewForUser(controller, user);
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
//				initializeTable();
			}
		} 
		
		if(type.equals("CLOSE_LOANS_USER")){
			this.idTessera = -1;
			this.setVisible(false);
		}
		
//		if(type.equals("REFRESH_LOANS_DETAIL")) {
//			initializeTable();
//		}
	}
}
