package views.Loan;

import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SpringLayout;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;

import controllers.views.LoansController;
import models.db.LoanDAO;
import utils.Observer;


public class UpdateLoanView extends JFrame implements Observer {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JTextField textFieldUser;
	private JTextField textFieldBook;
	private JLabel lblBook;
	private JTextField textStartDate;
	private JLabel lblStartDate;
	private JLabel lblEndDate;
	private JButton btnSave;
	private JButton btnClose;
	private LoansController controller = null;
	private LoanDAO loan = null;
	private int rowsUpdated = -1;
	private JTextField textEndDate;
	
	/**
	 * Create the frame.
	 */
	public UpdateLoanView(LoansController controller, LoanDAO loan) {
		this.controller = controller;
		this.loan = loan;
		setTitle("Modifica prestito");
		setBounds(100, 100, 450, 300);
        setLocationRelativeTo(null);


		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

		setContentPane(contentPane);
		SpringLayout sl_contentPane = new SpringLayout();
		contentPane.setLayout(sl_contentPane);

		JLabel lblUser = new JLabel("Utente:");
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
		sl_contentPane.putConstraint(SpringLayout.NORTH, lblBook, 11, SpringLayout.SOUTH, textFieldUser);
		sl_contentPane.putConstraint(SpringLayout.NORTH, textFieldBook, 6, SpringLayout.SOUTH, lblBook);
		sl_contentPane.putConstraint(SpringLayout.WEST, lblBook, 0, SpringLayout.WEST, lblUser);
		lblBook.setHorizontalAlignment(SwingConstants.CENTER);
		contentPane.add(lblBook);

		textStartDate = new JTextField();
		sl_contentPane.putConstraint(SpringLayout.WEST, textStartDate, 10, SpringLayout.WEST, contentPane);
		sl_contentPane.putConstraint(SpringLayout.EAST, textStartDate, 0, SpringLayout.EAST, textFieldUser);
		textStartDate.setColumns(10);
		contentPane.add(textStartDate);

		lblStartDate = new JLabel("Data inizio (dd/MM/yyyy):");
		sl_contentPane.putConstraint(SpringLayout.SOUTH, lblStartDate, -107, SpringLayout.SOUTH, contentPane);
		sl_contentPane.putConstraint(SpringLayout.NORTH, textStartDate, 6, SpringLayout.SOUTH, lblStartDate);
		sl_contentPane.putConstraint(SpringLayout.WEST, lblStartDate, 10, SpringLayout.WEST, contentPane);
		lblStartDate.setHorizontalAlignment(SwingConstants.CENTER);
		contentPane.add(lblStartDate);

		lblEndDate = new JLabel("Data fine (dd/MM/yyyy):");
		sl_contentPane.putConstraint(SpringLayout.NORTH, lblEndDate, 10, SpringLayout.SOUTH, textStartDate);
		sl_contentPane.putConstraint(SpringLayout.WEST, lblEndDate, 0, SpringLayout.WEST, lblUser);
		lblEndDate.setHorizontalAlignment(SwingConstants.CENTER);
		contentPane.add(lblEndDate);

		btnSave = new JButton("Salva");
		sl_contentPane.putConstraint(SpringLayout.SOUTH, btnSave, 0, SpringLayout.SOUTH, contentPane);
		btnSave.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
               saveLoan();
               if (rowsUpdated>0){
            	   	JOptionPane.showMessageDialog(null, "Loan saved successfully!", "Loan saved", JOptionPane.PLAIN_MESSAGE);
      		   } else {
   	   				JOptionPane.showMessageDialog(null, "Save failed!", "Warning", JOptionPane.PLAIN_MESSAGE);
      		   }
               controller.closeLoansDetailPanel();
            }
        });
		contentPane.add(btnSave);
		
		btnClose = new JButton("Chiudi");
		sl_contentPane.putConstraint(SpringLayout.EAST, btnSave, -11, SpringLayout.WEST, btnClose);
		sl_contentPane.putConstraint(SpringLayout.NORTH, btnClose, 0, SpringLayout.NORTH, btnSave);
		sl_contentPane.putConstraint(SpringLayout.EAST, btnClose, -10, SpringLayout.EAST, contentPane);
        btnClose.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                WindowEvent windowClosing = new WindowEvent(UpdateLoanView.this, WindowEvent.WINDOW_CLOSING);
                Toolkit.getDefaultToolkit().getSystemEventQueue().postEvent(windowClosing);
            }
        });
		contentPane.add(btnClose);
		
		textEndDate = new JTextField();
		sl_contentPane.putConstraint(SpringLayout.NORTH, textEndDate, 205, SpringLayout.NORTH, contentPane);
		sl_contentPane.putConstraint(SpringLayout.WEST, textEndDate, 0, SpringLayout.WEST, lblUser);
		sl_contentPane.putConstraint(SpringLayout.EAST, textEndDate, 0, SpringLayout.EAST, textFieldUser);
		textEndDate.setText("");
		textEndDate.setColumns(10);
		contentPane.add(textEndDate);
		
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
		SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
		textFieldUser.setText(loan.getUser_id().toString());
		textFieldUser.setEditable(false);
		textFieldBook.setText(loan.getBook_id().toString());
		textFieldBook.setEditable(false);
		textStartDate.setText(loan.getStart_date() != null ? sdf.format(loan.getStart_date()) : "null");
		textEndDate.setText(loan.getEnd_date() != null ? sdf.format(loan.getEnd_date()) : "null");
	}
	
	private void saveLoan() {		
		try {
			SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
			String startDate = textStartDate.getText();
			String endDate = textEndDate.getText();
			Date start = sdf.parse(startDate);
			Date end = sdf.parse(endDate); 
			rowsUpdated = controller.updateLoan(loan.getId(), start, end);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 
	}
	
	@Override
	public void update(String type, Object arg) {
		System.out.println("Loan details!");
		if(type.equals("OPEN_LOANS_DETAIL")) {
			this.setVisible(true);
		}
		if(type.equals("CLOSE_LOANS_DETAIL")){
			this.setVisible(false);
		}
	}
}