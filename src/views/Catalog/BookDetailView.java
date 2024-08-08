package views.Catalog;

import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import controllers.views.CatalogController;
import controllers.views.LoansController;
import models.bl.CatalogModel;
import models.db.BookDAO;
import models.db.LoanDAO;
import models.db.UserDAO;
import utils.Observer;
import javax.swing.JLabel;
import javax.swing.SpringLayout;
import javax.swing.JTextField;
import javax.swing.JButton;

public class BookDetailView extends JFrame implements Observer {
	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private LoansController controller = null;
	private CatalogModel catalogModel;
	private BookDAO selectedBook;
	private UserDAO selectedUser;
	private LoanDAO loan;
	int centerX;
	int centerY;
	private BookDAO book;
	private JTextField textFieldId;
	
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public BookDetailView(CatalogController catalogController, BookDAO b) {
		book = b;
		System.out.println(book.getId());
		CatalogController controller = catalogController;
//		Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
//        int screenWidth = screenSize.width;
//        int screenHeight = screenSize.height;
//	    centerX = screenWidth / 2;
//	    centerY = screenHeight / 2;
		
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 800, 600);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setLocationRelativeTo(null);
		setContentPane(contentPane);		
		SpringLayout sl_contentPane = new SpringLayout();
		contentPane.setLayout(sl_contentPane);
		
		JLabel lblBookId = new JLabel("Book id:");
		sl_contentPane.putConstraint(SpringLayout.NORTH, lblBookId, 20, SpringLayout.NORTH, contentPane);
		sl_contentPane.putConstraint(SpringLayout.WEST, lblBookId, 20, SpringLayout.WEST, contentPane);
		contentPane.add(lblBookId);
		
		textFieldId = new JTextField();
		textFieldId.setText(String.valueOf(book.getId()));
		sl_contentPane.putConstraint(SpringLayout.NORTH, textFieldId, -5, SpringLayout.NORTH, lblBookId);
		sl_contentPane.putConstraint(SpringLayout.WEST, textFieldId, 16, SpringLayout.EAST, lblBookId);
		sl_contentPane.putConstraint(SpringLayout.EAST, textFieldId, 155, SpringLayout.EAST, lblBookId);
		contentPane.add(textFieldId);
		textFieldId.setColumns(10);
		
		JButton btnClose = new JButton("Close");
		sl_contentPane.putConstraint(SpringLayout.SOUTH, btnClose, -10, SpringLayout.SOUTH, contentPane);
		sl_contentPane.putConstraint(SpringLayout.EAST, btnClose, -10, SpringLayout.EAST, contentPane);
		contentPane.add(btnClose);
		btnClose.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				catalogController.closeBookDetailPanel();
			}
		});
		
		
		
		
		
		
		controller.addObserver(this);
		
		this.addWindowListener(new WindowAdapter() {
	        @Override
	        public void windowClosing(WindowEvent e) {
	        	System.out.println("Cleaning.....");
	        	textFieldId.setText("");
	        }
	    });
	}

	@Override
	public void update(String type, Object arg) {
		System.out.println("Book details!");
		if(type.equals("OPEN_BOOK_DETAIL")) {
			this.setVisible(true);
		}
		if(type.equals("CLOSE_BOOK_DETAIL")){
			this.setVisible(false);
		}
	}
}
