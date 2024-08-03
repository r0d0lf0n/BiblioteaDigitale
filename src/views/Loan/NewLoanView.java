package views.Loan;

import java.awt.EventQueue;

import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import controllers.views.LoansController;
import utils.Observer;

public class NewLoanView extends JDialog implements Observer{

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
					NewLoanView frame = new NewLoanView();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public NewLoanView() {
		controller = new LoansController();
		
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setLocationRelativeTo(null);
		setContentPane(contentPane);
		
		
		
		controller.addObserver(this);
	}

	@Override
	public void update(String type, Object arg) {
		// TODO Auto-generated method stub
		
	}

}
