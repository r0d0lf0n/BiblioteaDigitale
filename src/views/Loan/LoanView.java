package views.Loan;

import java.awt.EventQueue;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.SpringLayout;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;
import javax.swing.JLabel;
import javax.swing.SwingConstants;
import javax.swing.JTextField;

public class LoanView extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JTable LoanTable;
	private DefaultTableModel model;
	private JLabel lblNoLoans;
	private JTextField textField;
	
	
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
	public LoanView() {
		setTitle("Loans");
		setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
		setBounds(100, 100, 600, 600);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

		setContentPane(contentPane);
		SpringLayout sl_contentPane = new SpringLayout();
		contentPane.setLayout(sl_contentPane);

		JButton btnClose = new JButton("Close");
		btnClose.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				  WindowEvent windowClosing = new WindowEvent(LoanView.this, WindowEvent.WINDOW_CLOSING);
	              Toolkit.getDefaultToolkit().getSystemEventQueue().postEvent(windowClosing);
			}
		});
		sl_contentPane.putConstraint(SpringLayout.WEST, btnClose, 0, SpringLayout.WEST, contentPane);
		sl_contentPane.putConstraint(SpringLayout.SOUTH, btnClose, -10, SpringLayout.SOUTH, contentPane);
		contentPane.add(btnClose);

		JButton btnNewLoan = new JButton("New Loan");
		sl_contentPane.putConstraint(SpringLayout.WEST, btnNewLoan, 0, SpringLayout.WEST, contentPane);
		btnNewLoan.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
			}
		});
		contentPane.add(btnNewLoan);

		JButton btnEditLoan = new JButton("Edit Loan");
		sl_contentPane.putConstraint(SpringLayout.NORTH, btnEditLoan, 19, SpringLayout.SOUTH, btnNewLoan);
		sl_contentPane.putConstraint(SpringLayout.WEST, btnEditLoan, 0, SpringLayout.WEST, btnClose);
		btnEditLoan.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
			}
		});
		contentPane.add(btnEditLoan);
		
		JButton btnReturnLoan = new JButton("Return Loan");
		sl_contentPane.putConstraint(SpringLayout.NORTH, btnReturnLoan, 21, SpringLayout.SOUTH, btnEditLoan);
		sl_contentPane.putConstraint(SpringLayout.WEST, btnReturnLoan, 0, SpringLayout.WEST, btnClose);
		btnReturnLoan.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
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

		LoanTable = new JTable();
		scrollPane.setViewportView(LoanTable);
		sl_contentPane.putConstraint(SpringLayout.WEST, LoanTable, 0, SpringLayout.WEST, contentPane);
		sl_contentPane.putConstraint(SpringLayout.SOUTH, LoanTable, -211, SpringLayout.NORTH, btnClose);
		sl_contentPane.putConstraint(SpringLayout.EAST, LoanTable, -420, SpringLayout.EAST, contentPane);
		sl_contentPane.putConstraint(SpringLayout.NORTH, LoanTable, 85, SpringLayout.SOUTH, btnEditLoan);
		
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
	             dispose();
	         }
	     });
	}

	public JTable getLoanTable() {
		return LoanTable;
	}

	public void setLoanTable(JTable LoanTable) {
		this.LoanTable = LoanTable;
	}
	
	public JLabel getNoLoanBtn() {
		return lblNoLoans;
	}

	public void setNoLoanBtn(JLabel lblNoLoans) {
		this.lblNoLoans = lblNoLoans;
	}
}
