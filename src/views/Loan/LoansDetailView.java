package views.Loan;

import java.awt.Dimension;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.SpringLayout;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;

import controllers.views.LoansController;
import models.db.LoanDAO;
import utils.Observer;

public class LoansDetailView extends JDialog implements Observer {
	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JTable loanTable;
	private JLabel lblNoLoans;
	private LoansController controller = null;
	NewLoanView newLoanView = null;
	List<LoanDAO> loans = null;
	int centerX;
	int centerY;

	

	/**
	 * Create the frame.
	 * @param landingPageController 
	 */
	public LoansDetailView(LoansController loansController, int idUtente) {
		Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        int screenWidth = screenSize.width;
        int screenHeight = screenSize.height;
	    // Calcola il punto centrale dello schermo
	    centerX = screenWidth / 2;
	    centerY = screenHeight / 2;
	    
		controller = new LoansController();
		setTitle("Gestione Prestiti - Id Utente:" + idUtente);
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
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
                WindowEvent windowClosing = new WindowEvent(LoansDetailView.this, WindowEvent.WINDOW_CLOSING);
                Toolkit.getDefaultToolkit().getSystemEventQueue().postEvent(windowClosing);
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
				//FIXME empty
			}
		});
		contentPane.add(btnEditLoan);

		JScrollPane scrollPane = new JScrollPane();
		sl_contentPane.putConstraint(SpringLayout.WEST, scrollPane, 0, SpringLayout.WEST, btnNewLoan);
		sl_contentPane.putConstraint(SpringLayout.SOUTH, scrollPane, -30, SpringLayout.NORTH, btnNewLoan);
		sl_contentPane.putConstraint(SpringLayout.EAST, scrollPane, -13, SpringLayout.EAST, contentPane);
		contentPane.add(scrollPane);

		loanTable = new JTable();
		scrollPane.setViewportView(loanTable);
		sl_contentPane.putConstraint(SpringLayout.WEST, loanTable, 0, SpringLayout.WEST, contentPane);
		sl_contentPane.putConstraint(SpringLayout.SOUTH, loanTable, -211, SpringLayout.NORTH, btnClose);
		sl_contentPane.putConstraint(SpringLayout.EAST, loanTable, -420, SpringLayout.EAST, contentPane);
		sl_contentPane.putConstraint(SpringLayout.NORTH, loanTable, 85, SpringLayout.SOUTH, btnEditLoan);
		
	    lblNoLoans = new JLabel("Non \u00E8 presente alcun prestito.");
	    sl_contentPane.putConstraint(SpringLayout.NORTH, scrollPane, 6, SpringLayout.SOUTH, lblNoLoans);
	    sl_contentPane.putConstraint(SpringLayout.EAST, btnNewLoan, 29, SpringLayout.EAST, lblNoLoans);
	    sl_contentPane.putConstraint(SpringLayout.WEST, lblNoLoans, 10, SpringLayout.WEST, contentPane);
		lblNoLoans.setHorizontalAlignment(SwingConstants.CENTER);
		contentPane.add(lblNoLoans);
		
		getLoans(idUtente);
		initializeTable();
		if (loans.size() == 0) { 
			lblNoLoans.setVisible(true);
			initializeTable();
		}
		else
		{
			lblNoLoans.setVisible(false);
		}

		controller.addObserver(this);
		
	}
	
	
	
	private void initializeTable() {
		Object[] columns = { "User", "Book", "Start Date", "End Date" };
		DefaultTableModel model = new DefaultTableModel();
		model.setColumnIdentifiers(columns);
		
		for(int i = 1; i < loans.size(); i++) {
				model.addRow(new Object[] {loans.get(i).getUser_id(), loans.get(i).getBook_id(), loans.get(i).getStart_date(), loans.get(i).getEnd_date()});
		}
		
		loanTable.setModel(model);
	}
	
	
	private List<LoanDAO> getLoans(int id) {
		loans = controller.getLoansByUserId(id);
		return loans;
	}



	@Override
	public void update(String type, Object arg) {
		if (loans.size() == 0) { 
			lblNoLoans.setVisible(true);
			initializeTable();
		}
		else
		{
			lblNoLoans.setVisible(false);
		}
		this.setVisible(true);	
	}
	
	
}
