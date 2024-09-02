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
import javax.swing.JTextField;
import javax.swing.SpringLayout;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.table.DefaultTableModel;

import controllers.views.LandingPageController;
import controllers.views.LoansController;
import models.db.BookDAO;
import models.db.LoanDAO;
import models.db.UserDAO;
import utils.CustomDialog;
import utils.Observer;

public class LoanView extends JFrame implements Observer{

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JTable loanTable;
	//private DefaultTableModel model;
	private JLabel lblNoLoans;
	private JTextField textFieldByUser;
	private LoansController controller = null;
	NewLoanView newLoanView = null;
	UpdateLoanView updateLoanView = null;
	//List<LoanDAO> loans = null;
	private CustomDialog dialog;
	private final Object[] columns = { "Id", "Utente", "Libro", "Inizio Prestito", "Fine Prestito" };
	int centerX;
	int centerY;
	private LandingPageController landingPageController = null;
	private JTextField textFieldByBook;
	private int idTessera = -1;

	

	/**
	 * Create the frame.
	 * @param landingPageController 
	 */
	public LoanView(LandingPageController landingPageController, int id) {
		this.idTessera = id;
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

		JButton btnClose = new JButton("Chiudi");
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
				//if(newLoanView == null)
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
				if (!loanTable.getSelectionModel().isSelectionEmpty()) {		
					String loanId = loanTable.getValueAt(loanTable.getSelectedRow(), 0) != null ? loanTable.getValueAt(loanTable.getSelectedRow(), 0).toString() : "-1";
	        		LoanDAO selectedLoan = controller.getLoanById(Integer.valueOf(loanId));
	        	    updateLoanView = new UpdateLoanView(controller, selectedLoan, false);
	        	    updateLoanView.setVisible(true);
				} else {
	       			JOptionPane.showMessageDialog(updateLoanView, "Selezionare il prestito per modificare!");
				}
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
		
		textFieldByUser = new JTextField();
		sl_contentPane.putConstraint(SpringLayout.NORTH, lblNoLoans, 6, SpringLayout.SOUTH, textFieldByUser);
		sl_contentPane.putConstraint(SpringLayout.WEST, scrollPane, 0, SpringLayout.WEST, textFieldByUser);
		sl_contentPane.putConstraint(SpringLayout.WEST, textFieldByUser, 10, SpringLayout.WEST, contentPane);
		sl_contentPane.putConstraint(SpringLayout.EAST, textFieldByUser, 145, SpringLayout.WEST, contentPane);
		contentPane.add(textFieldByUser);
		textFieldByUser.setColumns(10);
		textFieldByUser.getDocument().addDocumentListener(new DocumentListener() {
		    @Override
		    public void insertUpdate(DocumentEvent e) {
		    	String txt = textFieldByUser.getText();
		    	if (txt.length() > 0 && txt.matches("^[-a-zA-Z0-9._]+")) {
		    		filteredLoansByUser(txt);
		    	} else {
		    		initializeTable();
		    	}
		    }

		    @Override
		    public void removeUpdate(DocumentEvent e) {
		    	String txt = textFieldByUser.getText();
		    	if (txt.length() > 0 && txt.matches("^[-a-zA-Z0-9._]+")) {
		    		filteredLoansByUser(txt);
		    	} else {
		    		initializeTable();
		    	}
		    }

		    @Override
		    public void changedUpdate(DocumentEvent e) {}
		});
		
		JLabel lblNewLabel = new JLabel(" Ricerca prestito:");
		sl_contentPane.putConstraint(SpringLayout.NORTH, textFieldByUser, 24, SpringLayout.SOUTH, lblNewLabel);
		sl_contentPane.putConstraint(SpringLayout.NORTH, lblNewLabel, 0, SpringLayout.NORTH, contentPane);
		sl_contentPane.putConstraint(SpringLayout.WEST, lblNewLabel, 0, SpringLayout.WEST, btnNewLoan);
		contentPane.add(lblNewLabel);
		
		JButton btnRefresh = new JButton("Aggiorna");
		btnRefresh.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (idTessera == -1) {
					initializeTable();
				} else {
					UserDAO user = controller.getUserByTesseraId(idTessera);
					filteredLoansByTessera(String.valueOf(user.getId()));
				}
			}
		});
		sl_contentPane.putConstraint(SpringLayout.SOUTH, btnRefresh, -6, SpringLayout.NORTH, scrollPane);
		sl_contentPane.putConstraint(SpringLayout.EAST, btnRefresh, 0, SpringLayout.EAST, btnClose);
		contentPane.add(btnRefresh);
		
		JLabel lblSearchByUser = new JLabel("Per utente");
		sl_contentPane.putConstraint(SpringLayout.NORTH, lblSearchByUser, 6, SpringLayout.SOUTH, lblNewLabel);
		sl_contentPane.putConstraint(SpringLayout.WEST, lblSearchByUser, 0, SpringLayout.WEST, btnNewLoan);
		contentPane.add(lblSearchByUser);
		
		textFieldByBook = new JTextField();
		sl_contentPane.putConstraint(SpringLayout.NORTH, textFieldByBook, 0, SpringLayout.NORTH, textFieldByUser);
		sl_contentPane.putConstraint(SpringLayout.WEST, textFieldByBook, 48, SpringLayout.EAST, textFieldByUser);
		textFieldByBook.setColumns(10);
		contentPane.add(textFieldByBook);
		textFieldByBook.getDocument().addDocumentListener(new DocumentListener() {
		    @Override
		    public void insertUpdate(DocumentEvent e) {
		    	String txt = textFieldByBook.getText();
		    	if (txt.length() > 0 && txt.matches("^[-a-zA-Z0-9._]+")) {
		    		filteredLoansByTitle(txt);
		    	} else {
		    		initializeTable();
		    	}
		    }

		    @Override
		    public void removeUpdate(DocumentEvent e) {
		    	String txt = textFieldByBook.getText();
		    	if (txt.length() > 0 && txt.matches("^[-a-zA-Z0-9._]+")) {
		    		filteredLoansByTitle(txt);	
		    	} else {
		    		initializeTable();
		    	}
		    }

		    @Override
		    public void changedUpdate(DocumentEvent e) {}
		});
		
		JLabel lblSearchByTtitle = new JLabel("Per titolo");
		sl_contentPane.putConstraint(SpringLayout.NORTH, lblSearchByTtitle, 0, SpringLayout.NORTH, lblSearchByUser);
		sl_contentPane.putConstraint(SpringLayout.WEST, lblSearchByTtitle, 0, SpringLayout.WEST, textFieldByBook);
		contentPane.add(lblSearchByTtitle);
		
		this.addWindowListener(new WindowAdapter() {
	         @Override
	         public void windowClosing(WindowEvent e) {
	        	 landingPageController.openLandingPanel();
	         }
	     });

		controller.addObserver(this);
		landingPageController.addObserver(this);
		
		if (idTessera == -1) {
			initializeTable();
		} else {
			UserDAO user = controller.getUserByTesseraId(idTessera);
			filteredLoansByTessera(String.valueOf(user.getId()));
		}
		
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

	
	private void initializeTable() {
		//Object[] columns = { "Id", "User", "Book", "Start Date", "End Date" };
		DefaultTableModel model = new DefaultTableModel();
		model.setColumnIdentifiers(columns);
		
		List<LoanDAO> loans = controller.getLoans();
		for (LoanDAO l : loans) {
			UserDAO u  = controller.getUserById(l.getUser_id().getId());
			BookDAO b  = controller.getBookById(l.getBook_id().getId());
			SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
			String start = l.getStart_date() != null ? sdf.format(l.getStart_date()) : "";
			String end = l.getEnd_date() != null ? sdf.format(l.getEnd_date()) : "";
			if (u != null && b != null) 
				model.addRow(new Object[] {l.getId(), u.getName() + " " + u.getSurname(), b.getTitle(), start, end});
		}

		loanTable.setModel(model);
	}	
	
	private void filteredLoansByTessera(String criteria) {
		//Object[] columns = { "Id", "User", "Book", "Start Date", "End Date" };
		DefaultTableModel model = new DefaultTableModel();
		model.setColumnIdentifiers(columns);	
		
    	List<LoanDAO> list = controller.getLoansByTessera(Integer.valueOf(criteria));
		for (LoanDAO l : list) {
			UserDAO u  = controller.getUserById(l.getUser_id().getId());
			BookDAO b  = controller.getBookById(l.getBook_id().getId());
			SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
			String start = l.getStart_date() != null ? sdf.format(l.getStart_date()) : "";
			String end = l.getEnd_date() != null ? sdf.format(l.getEnd_date()) : "";
			if (u != null && b != null) {
				model.addRow(new Object[] {l.getId(), u.getName() + " " + u.getSurname(), b.getTitle(), start, end});
			}
		}
		
		loanTable.setModel(model);
	}
	
	private void filteredLoansByUser(String criteria) {
		//Object[] columns = { "Id", "User", "Book", "Start Date", "End Date" };
		DefaultTableModel model = new DefaultTableModel();
		model.setColumnIdentifiers(columns);	
		
    	List<LoanDAO> list = controller.getLoansByUser(criteria);
		for (LoanDAO l : list) {
			UserDAO u  = controller.getUserById(l.getUser_id().getId());
			BookDAO b  = controller.getBookById(l.getBook_id().getId());
			SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
			String start = l.getStart_date() != null ? sdf.format(l.getStart_date()) : "";
			String end = l.getEnd_date() != null ? sdf.format(l.getEnd_date()) : "";
			if (u != null && b != null) {
				model.addRow(new Object[] {l.getId(), u.getName() + " " + u.getSurname(), b.getTitle(), start, end});
			}
		}
		
		loanTable.setModel(model);
	}
	
	private void filteredLoansByTitle(String criteria) {
		//Object[] columns = { "Id", "User", "Book", "Start Date", "End Date" };
		DefaultTableModel model = new DefaultTableModel();
		model.setColumnIdentifiers(columns);	
		
    	List<LoanDAO> list = controller.getLoansByTitle(criteria);
		for (LoanDAO l : list) {
			UserDAO u  = controller.getUserById(l.getUser_id().getId());
			BookDAO b  = controller.getBookById(l.getBook_id().getId());
			SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
			String start = l.getStart_date() != null ? sdf.format(l.getStart_date()) : "";
			String end = l.getEnd_date() != null ? sdf.format(l.getEnd_date()) : "";
			
			if (u != null && b != null) {
				model.addRow(new Object[] {l.getId(), u.getName() + " " + u.getSurname(), b.getTitle(), start, end});
			}
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
			this.idTessera = -1;
			this.setVisible(false);
		}
		
		if(type.equals("REFRESH_LOANS_DETAIL")) {
			initializeTable();
		}
	}
}
