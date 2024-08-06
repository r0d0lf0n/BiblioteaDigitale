package views.Loan;

import java.awt.EventQueue;

import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.table.DefaultTableModel;

import controllers.views.LoansController;
import models.bl.CatalogModel;
import models.bl.UserModel;
import models.db.BookDAO;
import models.db.UserDAO;
import utils.Observer;

import javax.swing.JButton;
import javax.swing.SpringLayout;
import java.util.List;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.JTable;
import javax.swing.JScrollBar;
import javax.swing.JScrollPane;

public class NewLoanView extends JDialog implements Observer {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private LoansController controller = null;
	private JTextField textFieldUser;
	private JTable tableBooks;
	private JTable table_1;
	private JTable table_2;
	private JTextField textFieldBook;
	private JLabel lblTableLabel;
	private JTable tableUsers;
	private JLabel lblTableLabelUsers;
	private JButton btnSave;
	private JLabel lblUserSelected;
	private JLabel lblSelectedUserValue;
	private JLabel lblBookSelected;
	private JLabel lblSelectedBookValue;
	private List<BookDAO> filteredBooks = null;
	private List<UserDAO> filteredUsers = null;
	private CatalogModel catalogModel;
	private UserModel userModel;
	private JScrollPane scrollPaneBooks;
	private JTable table;
	private JScrollPane scrollPaneUsers;

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
	
		JButton btnNewButton = new JButton("Close");
		sl_contentPane.putConstraint(SpringLayout.SOUTH, btnNewButton, -10, SpringLayout.SOUTH, contentPane);
		sl_contentPane.putConstraint(SpringLayout.EAST, btnNewButton, -10, SpringLayout.EAST, contentPane);
		contentPane.add(btnNewButton);
		
		JLabel lblUser = new JLabel("User Name:");
		sl_contentPane.putConstraint(SpringLayout.NORTH, lblUser, 26, SpringLayout.NORTH, contentPane);
		sl_contentPane.putConstraint(SpringLayout.WEST, lblUser, 10, SpringLayout.WEST, contentPane);
		contentPane.add(lblUser);
		
		textFieldUser = new JTextField(40);
		sl_contentPane.putConstraint(SpringLayout.NORTH, textFieldUser, 6, SpringLayout.SOUTH, lblUser);
		sl_contentPane.putConstraint(SpringLayout.WEST, textFieldUser, 10, SpringLayout.WEST, contentPane);
		sl_contentPane.putConstraint(SpringLayout.SOUTH, textFieldUser, 87, SpringLayout.NORTH, contentPane);
		textFieldUser.setColumns(100);
		contentPane.add(textFieldUser);
		
		textFieldUser.getDocument().addDocumentListener(new DocumentListener() {
		    @Override
		    public void insertUpdate(DocumentEvent e) {
		    	String txt = textFieldUser.getText();
		    	filteredUsers(txt);
		    }

		    @Override
		    public void removeUpdate(DocumentEvent e) {
		    	String txt = textFieldUser.getText();
		    	filteredUsers(txt);
		    }

		    @Override
		    public void changedUpdate(DocumentEvent e) {}
		});
		
		JLabel lblBook = new JLabel("Book Name:");
		sl_contentPane.putConstraint(SpringLayout.NORTH, lblBook, 0, SpringLayout.NORTH, lblUser);
		sl_contentPane.putConstraint(SpringLayout.WEST, lblBook, 314, SpringLayout.EAST, lblUser);
		contentPane.add(lblBook);
		
		
		textFieldBook = new JTextField(40);
		sl_contentPane.putConstraint(SpringLayout.EAST, textFieldUser, -224, SpringLayout.WEST, textFieldBook);
		sl_contentPane.putConstraint(SpringLayout.NORTH, textFieldBook, 0, SpringLayout.NORTH, textFieldUser);
		sl_contentPane.putConstraint(SpringLayout.WEST, textFieldBook, 0, SpringLayout.WEST, lblBook);
		sl_contentPane.putConstraint(SpringLayout.SOUTH, textFieldBook, 87, SpringLayout.NORTH, contentPane);
		sl_contentPane.putConstraint(SpringLayout.EAST, textFieldBook, -195, SpringLayout.EAST, contentPane);
		textFieldBook.setColumns(100);
		contentPane.add(textFieldBook);
		
		textFieldBook.getDocument().addDocumentListener(new DocumentListener() {
		    @Override
		    public void insertUpdate(DocumentEvent e) {
		    	String txt = textFieldBook.getText();
		    	filteredBooks(txt);
		    }

		    @Override
		    public void removeUpdate(DocumentEvent e) {
		    	String txt = textFieldBook.getText();
		    	filteredBooks(txt);
		    }

		    @Override
		    public void changedUpdate(DocumentEvent e) {}
		});
		
		tableBooks = new JTable();
		sl_contentPane.putConstraint(SpringLayout.WEST, tableBooks, 396, SpringLayout.WEST, contentPane);
		sl_contentPane.putConstraint(SpringLayout.SOUTH, tableBooks, -234, SpringLayout.SOUTH, contentPane);
		sl_contentPane.putConstraint(SpringLayout.EAST, tableBooks, -10, SpringLayout.EAST, contentPane);
		
		lblTableLabel = new JLabel("Books");
		sl_contentPane.putConstraint(SpringLayout.NORTH, tableBooks, 11, SpringLayout.SOUTH, lblTableLabel);
		sl_contentPane.putConstraint(SpringLayout.EAST, lblTableLabel, -349, SpringLayout.EAST, contentPane);
		contentPane.add(lblTableLabel);
		
		tableUsers = new JTable();
		sl_contentPane.putConstraint(SpringLayout.EAST, tableUsers, -6, SpringLayout.WEST, tableBooks);
		sl_contentPane.putConstraint(SpringLayout.WEST, tableUsers, 10, SpringLayout.WEST, contentPane);
		sl_contentPane.putConstraint(SpringLayout.SOUTH, tableUsers, -234, SpringLayout.SOUTH, contentPane);
		
		lblTableLabelUsers = new JLabel("Users");
		sl_contentPane.putConstraint(SpringLayout.WEST, lblTableLabelUsers, 10, SpringLayout.WEST, contentPane);
		sl_contentPane.putConstraint(SpringLayout.NORTH, tableUsers, 11, SpringLayout.SOUTH, lblTableLabelUsers);
		sl_contentPane.putConstraint(SpringLayout.NORTH, lblTableLabel, 0, SpringLayout.NORTH, lblTableLabelUsers);
		sl_contentPane.putConstraint(SpringLayout.NORTH, lblTableLabelUsers, 17, SpringLayout.SOUTH, textFieldUser);
		contentPane.add(lblTableLabelUsers);
		
		btnSave = new JButton("Save");
		sl_contentPane.putConstraint(SpringLayout.NORTH, btnSave, 0, SpringLayout.NORTH, btnNewButton);
		sl_contentPane.putConstraint(SpringLayout.WEST, btnSave, 0, SpringLayout.WEST, contentPane);
		contentPane.add(btnSave);
		
		lblUserSelected = new JLabel("Selected User:");
		sl_contentPane.putConstraint(SpringLayout.NORTH, lblUserSelected, 50, SpringLayout.SOUTH, tableUsers);
		sl_contentPane.putConstraint(SpringLayout.WEST, lblUserSelected, 0, SpringLayout.WEST, lblUser);
		contentPane.add(lblUserSelected);
		
		lblSelectedUserValue = new JLabel("none");
		sl_contentPane.putConstraint(SpringLayout.WEST, lblSelectedUserValue, 17, SpringLayout.EAST, lblUserSelected);
		sl_contentPane.putConstraint(SpringLayout.SOUTH, lblSelectedUserValue, 0, SpringLayout.SOUTH, lblUserSelected);
		contentPane.add(lblSelectedUserValue);
		
		lblBookSelected = new JLabel("Selected Book:");
		sl_contentPane.putConstraint(SpringLayout.NORTH, lblBookSelected, 25, SpringLayout.SOUTH, lblUserSelected);
		sl_contentPane.putConstraint(SpringLayout.WEST, lblBookSelected, 0, SpringLayout.WEST, lblUser);
		contentPane.add(lblBookSelected);
		
		lblSelectedBookValue = new JLabel("none");
		sl_contentPane.putConstraint(SpringLayout.WEST, lblSelectedBookValue, 0, SpringLayout.WEST, lblSelectedUserValue);
		sl_contentPane.putConstraint(SpringLayout.SOUTH, lblSelectedBookValue, 0, SpringLayout.SOUTH, lblBookSelected);
		contentPane.add(lblSelectedBookValue);
		
		scrollPaneBooks = new JScrollPane();
		sl_contentPane.putConstraint(SpringLayout.NORTH, scrollPaneBooks, 1, SpringLayout.NORTH, tableUsers);
		sl_contentPane.putConstraint(SpringLayout.WEST, scrollPaneBooks, 0, SpringLayout.WEST, lblTableLabel);
		sl_contentPane.putConstraint(SpringLayout.SOUTH, scrollPaneBooks, 198, SpringLayout.NORTH, tableUsers);
		sl_contentPane.putConstraint(SpringLayout.EAST, scrollPaneBooks, 334, SpringLayout.EAST, lblTableLabel);
		contentPane.add(scrollPaneBooks);
		scrollPaneBooks.setViewportView(tableBooks);
		
		scrollPaneUsers = new JScrollPane();
		sl_contentPane.putConstraint(SpringLayout.NORTH, scrollPaneUsers, 0, SpringLayout.NORTH, scrollPaneBooks);
		sl_contentPane.putConstraint(SpringLayout.WEST, scrollPaneUsers, 10, SpringLayout.WEST, contentPane);
		sl_contentPane.putConstraint(SpringLayout.SOUTH, scrollPaneUsers, 0, SpringLayout.SOUTH, scrollPaneBooks);
		sl_contentPane.putConstraint(SpringLayout.EAST, scrollPaneUsers, -6, SpringLayout.WEST, scrollPaneBooks);
		contentPane.add(scrollPaneUsers);
		scrollPaneUsers.setViewportView(tableUsers);
		
		
		
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
	
	private void filteredUsers(String criteria) {
		Object[] columns = { "id", "Name"};
		DefaultTableModel model = new DefaultTableModel();
		model.setColumnIdentifiers(columns);
		
    	List<UserDAO> list = controller.getUsersByRegex(criteria);
		for (UserDAO u : list) {
			System.out.println("*************************");
			System.out.println(u.getName());
			model.addRow(new Object[] {u.getId(), u.getName()});
		}
		
		tableUsers.setModel(model);
	}
	
	private void filteredBooks(String criteria) {
		Object[] columns = { "id", "Title", "Author", "Year"};
		DefaultTableModel model = new DefaultTableModel();
		model.setColumnIdentifiers(columns);
		
    	List<BookDAO> list = controller.getBooksByRegex(criteria);
		for (BookDAO b : list) {
			System.out.println(String.format("size: %d", list.size()));
			System.out.println("*************************");
			System.out.println(b.getTitle());
			model.addRow(new Object[] {b.getId(), b.getTitle(), b.getAuthor(), b.getYear()});
		}
		
		tableBooks.setModel(model);
	}
}
