package views.Loan;

import java.awt.EventQueue;

import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import controllers.views.LoansController;
import utils.Observer;
import javax.swing.JButton;
import javax.swing.SpringLayout;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.JLabel;

public class NewLoanView extends JDialog implements Observer {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private LoansController controller = null;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
//					NewLoanView frame = new NewLoanView();
//					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public NewLoanView(LoansController loanController) {
		controller = loanController;
		
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 800, 600);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setLocationRelativeTo(null);
		setContentPane(contentPane);
		SpringLayout sl_contentPane = new SpringLayout();
		contentPane.setLayout(sl_contentPane);
		
		JButton btnClose = new JButton("Close");
		sl_contentPane.putConstraint(SpringLayout.WEST, btnClose, 28, SpringLayout.WEST, contentPane);
		sl_contentPane.putConstraint(SpringLayout.SOUTH, btnClose, -22, SpringLayout.SOUTH, contentPane);
		btnClose.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				controller.setChanged("CLOSE_NEW_LOAN", null);
			}
		});
		contentPane.add(btnClose);
		
		JLabel lblNewLabel = new JLabel("New label");
		sl_contentPane.putConstraint(SpringLayout.NORTH, lblNewLabel, 58, SpringLayout.NORTH, contentPane);
		sl_contentPane.putConstraint(SpringLayout.WEST, lblNewLabel, 0, SpringLayout.WEST, btnClose);
		contentPane.add(lblNewLabel);
		
		
		
		controller.addObserver(this);
	}

	@Override
	public void update(String type, Object arg) {
		System.out.println("New loan!");
		if(type.equals("OPEN_NEW_LOAN")) {
			this.setVisible(true);
		}
		if(type.equals("CLOSE_NEW_LOAN")){
			this.setVisible(false);
		}
	}
}
