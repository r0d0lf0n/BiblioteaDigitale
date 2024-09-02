package views.Loan;

import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Properties;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SpringLayout;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;

import org.jdatepicker.impl.JDatePanelImpl;
import org.jdatepicker.impl.JDatePickerImpl;
import org.jdatepicker.impl.UtilDateModel;

import controllers.views.LoansController;
import models.db.BookDAO;
import models.db.LoanDAO;
import models.db.UserDAO;
import utils.DateLabelFormatter;
import utils.Observer;


public class UpdateLoanView extends JFrame implements Observer {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JTextField textFieldUser;
	private JTextField textFieldBook;
	private JLabel lblBook;
	private JDatePickerImpl datePickerStart;
	private JDatePickerImpl datePickerEnd;
	private UtilDateModel modelStart;
	private UtilDateModel modelEnd;
	private JLabel lblStartDate;
	private JLabel lblEndDate;
	private JButton btnSave;
	private JButton btnClose;
	private LoansController controller = null;
	private LoanDAO loan = null;
	private int rowsUpdated = -1;
	private boolean isUserRegistered = false;
	
	/**
	 * Create the frame.
	 */
	public UpdateLoanView(LoansController controller, LoanDAO loan, boolean registered) {
		this.controller = controller;
		this.loan = loan;
		this.isUserRegistered = registered;
		setTitle("Modifica prestito");
		setBounds(100, 100, 450, 340);
        setLocationRelativeTo(null);


		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

		setContentPane(contentPane);
		SpringLayout sl_contentPane = new SpringLayout();
		contentPane.setLayout(sl_contentPane);
		
		
		modelStart = new UtilDateModel();
		modelEnd = new UtilDateModel();
		Properties p=new Properties();
		p.put("text.today","Today");
		p.put("text.month","Month");
		p.put("text.year","Year");
		JDatePanelImpl datePanel = new JDatePanelImpl(modelStart, p);
		datePickerStart = new JDatePickerImpl(datePanel, new DateLabelFormatter());
		sl_contentPane.putConstraint(SpringLayout.NORTH, datePickerStart, 161, SpringLayout.NORTH, contentPane);
		JDatePanelImpl datePanel2 = new JDatePanelImpl(modelEnd, p);
		datePickerEnd = new JDatePickerImpl(datePanel2, new DateLabelFormatter());
		sl_contentPane.putConstraint(SpringLayout.EAST, datePickerEnd, 0, SpringLayout.EAST, datePickerStart);

		JLabel lblUser = new JLabel("Utente:");
		sl_contentPane.putConstraint(SpringLayout.WEST, datePickerStart, 0, SpringLayout.WEST, lblUser);
		sl_contentPane.putConstraint(SpringLayout.NORTH, lblUser, 20, SpringLayout.NORTH, contentPane);
		sl_contentPane.putConstraint(SpringLayout.WEST, lblUser, 10, SpringLayout.WEST, contentPane);
		lblUser.setHorizontalAlignment(SwingConstants.CENTER);
		contentPane.add(lblUser);

		textFieldUser = new JTextField();
		sl_contentPane.putConstraint(SpringLayout.NORTH, textFieldUser, 6, SpringLayout.SOUTH, lblUser);
		sl_contentPane.putConstraint(SpringLayout.WEST, textFieldUser, 0, SpringLayout.WEST, lblUser);
		sl_contentPane.putConstraint(SpringLayout.EAST, textFieldUser, -15, SpringLayout.EAST, contentPane);
		contentPane.add(textFieldUser);
		textFieldUser.setColumns(10);

		textFieldBook = new JTextField();
		sl_contentPane.putConstraint(SpringLayout.WEST, textFieldBook, 0, SpringLayout.WEST, lblUser);
		sl_contentPane.putConstraint(SpringLayout.EAST, textFieldBook, 0, SpringLayout.EAST, textFieldUser);
		textFieldBook.setColumns(10);
		contentPane.add(textFieldBook);

		lblBook = new JLabel("Libro:");
		sl_contentPane.putConstraint(SpringLayout.NORTH, textFieldBook, 6, SpringLayout.SOUTH, lblBook);
		sl_contentPane.putConstraint(SpringLayout.NORTH, lblBook, 6, SpringLayout.SOUTH, textFieldUser);
		sl_contentPane.putConstraint(SpringLayout.WEST, lblBook, 0, SpringLayout.WEST, lblUser);
		lblBook.setHorizontalAlignment(SwingConstants.CENTER);
		contentPane.add(lblBook);
		
		contentPane.add(datePickerStart);
		contentPane.add(datePickerEnd);
		
		lblStartDate = new JLabel("Data inizio (dd/MM/yyyy):");
		sl_contentPane.putConstraint(SpringLayout.WEST, lblStartDate, 0, SpringLayout.WEST, lblUser);
		sl_contentPane.putConstraint(SpringLayout.SOUTH, lblStartDate, -6, SpringLayout.NORTH, datePickerStart);
		lblStartDate.setHorizontalAlignment(SwingConstants.CENTER);
		contentPane.add(lblStartDate);

		lblEndDate = new JLabel("Data fine (dd/MM/yyyy):");
		sl_contentPane.putConstraint(SpringLayout.NORTH, datePickerEnd, 6, SpringLayout.SOUTH, lblEndDate);
		sl_contentPane.putConstraint(SpringLayout.NORTH, lblEndDate, 6, SpringLayout.SOUTH, datePickerStart);
		sl_contentPane.putConstraint(SpringLayout.WEST, lblEndDate, 0, SpringLayout.WEST, lblUser);
		lblEndDate.setHorizontalAlignment(SwingConstants.CENTER);
		contentPane.add(lblEndDate);

		btnSave = new JButton("Salva");
		btnSave.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
               saveLoan();
               if (rowsUpdated>0){
            	   	JOptionPane.showMessageDialog(null, "Prestito salvato con successo!", "Prestito salvato", JOptionPane.PLAIN_MESSAGE);
      		   } else {
   	   				JOptionPane.showMessageDialog(null, "Save failed!", "Warning", JOptionPane.PLAIN_MESSAGE);
      		   }
               controller.closeLoansDetailPanel();
            }
        });
		contentPane.add(btnSave);
		
		btnClose = new JButton("Chiudi");
		sl_contentPane.putConstraint(SpringLayout.NORTH, btnSave, 0, SpringLayout.NORTH, btnClose);
		sl_contentPane.putConstraint(SpringLayout.SOUTH, btnClose, 0, SpringLayout.SOUTH, contentPane);
		sl_contentPane.putConstraint(SpringLayout.EAST, btnClose, -10, SpringLayout.EAST, contentPane);
        btnClose.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                WindowEvent windowClosing = new WindowEvent(UpdateLoanView.this, WindowEvent.WINDOW_CLOSING);
                Toolkit.getDefaultToolkit().getSystemEventQueue().postEvent(windowClosing);
            }
        });
		contentPane.add(btnClose);
		
		JButton btnDelete = new JButton("Cancella");
		if (isUserRegistered) {
			btnDelete.setEnabled(true);
		} else {
			btnDelete.setEnabled(false);
		}
		
		sl_contentPane.putConstraint(SpringLayout.EAST, btnSave, -6, SpringLayout.WEST, btnDelete);
		sl_contentPane.putConstraint(SpringLayout.NORTH, btnDelete, 0, SpringLayout.NORTH, btnClose);
		sl_contentPane.putConstraint(SpringLayout.EAST, btnDelete, -6, SpringLayout.WEST, btnClose);
		
		btnDelete.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
            	System.out.println("Deleting loan!");
            	controller.deleteLoanForUser(loan); 
            	controller.closeLoansDetailPanel();
                WindowEvent windowClosing = new WindowEvent(UpdateLoanView.this, WindowEvent.WINDOW_CLOSING);
                Toolkit.getDefaultToolkit().getSystemEventQueue().postEvent(windowClosing);
            }
        });
		contentPane.add(btnDelete);
				
		 this.addWindowListener(new WindowAdapter() {
	         @Override
	         public void windowClosing(WindowEvent e) {
	             dispose();
	         }
	     });
		 
		initFields();
		
	    setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);

	}
	
	private void initFields() {
		UserDAO u  = controller.getUserById(loan.getUser_id().getId());
		BookDAO b  = controller.getBookById(loan.getBook_id().getId());
		
		textFieldUser.setText(String.valueOf(u.getName() + " " + u.getSurname()));
		textFieldUser.setEditable(false);
		textFieldBook.setText(b.getTitle());
		textFieldBook.setEditable(false);
		
		Calendar calendar = new GregorianCalendar();
		if (loan.getStart_date()!=null) {
			calendar.setTime(loan.getStart_date());
			modelStart.setDate(calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH));
			modelStart.setSelected(true);
		}
		if (loan.getEnd_date()!=null) {
			calendar.setTime(loan.getEnd_date());
			modelEnd.setDate(calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH));
			modelEnd.setSelected(true);			
		}
	}
	
	private void saveLoan() {		
		Date start = (Date) datePickerStart.getModel().getValue();
		Date end = (Date) datePickerEnd.getModel().getValue();
		rowsUpdated = controller.updateLoan(loan.getId(), start, end); 
	}
	
	@Override
	public void update(String type, Object arg) {
		//System.out.println("Loan details!");
		if(type.equals("OPEN_LOANS_DETAIL")) {
			this.setVisible(true);
		}
		if(type.equals("CLOSE_LOANS_DETAIL")){
			this.setVisible(false);
		}
	}
}

