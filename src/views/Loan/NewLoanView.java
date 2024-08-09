package views.Loan;

import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFormattedTextField;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.SpringLayout;
import javax.swing.border.EmptyBorder;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;

import controllers.views.LoansController;
import models.bl.CatalogModel;
import models.bl.UserModel;
import models.db.BookDAO;
import models.db.LoanDAO;
import models.db.UserDAO;
import utils.CustomDialog;
import utils.Observer;

public class NewLoanView extends JDialog implements Observer {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private LoansController controller = null;
	private JTextField textFieldUser;
	private JTable tableBooks;
	private JTextField textFieldBook;
	private JLabel lblTableLabel;
	private JTable tableUsers;
	private JLabel lblTableLabelUsers;
	private JButton btnSave;
	private JLabel lblUserSelected;
	private JLabel lblSelectedUserValueId;
	private JLabel lblBookSelected;
	private JLabel lblSelectedBookValueID;
	private JScrollPane scrollPaneBooks;
	private JScrollPane scrollPaneUsers;
	private JLabel lblSelectedBookValueTitle;
	private JLabel lblSelectedBookValueAuthor;
	private JLabel lblSelectedBookValueYear;
	private BookDAO selectedBook;
	private UserDAO selectedUser;
	private LoanDAO loan;
	private JLabel lblEnd_date;
	private JFormattedTextField formattedTextFieldEndDate;
	private JLabel lblSelectedUserValueName;
	private JLabel lblSelectedUserValueSurname;
	private DefaultTableModel modelUsers;
	private DefaultTableModel modelBooks;
	int centerX;
	int centerY;
	private BooksRowSelectionListener tableBooksListener = new BooksRowSelectionListener();
	private UsersRowSelectionListener tableUsersListener = new UsersRowSelectionListener();
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
//		cleanTextFields();
		controller = loanController;
		Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        int screenWidth = screenSize.width;
        int screenHeight = screenSize.height;
	    centerX = screenWidth / 2;
	    centerY = screenHeight / 2;
		
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 800, 600);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setLocationRelativeTo(null);
		setContentPane(contentPane);
		
		SpringLayout sl_contentPane = new SpringLayout();
		contentPane.setLayout(sl_contentPane);
	
		JButton btnClose = new JButton("Close");
		sl_contentPane.putConstraint(SpringLayout.SOUTH, btnClose, -10, SpringLayout.SOUTH, contentPane);
		sl_contentPane.putConstraint(SpringLayout.EAST, btnClose, -10, SpringLayout.EAST, contentPane);
		contentPane.add(btnClose);
		btnClose.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				tableBooks.removeMouseListener(tableBooksListener);
				tableUsers.removeMouseListener(tableUsersListener);
				cleanTextFields();
				controller.closeNewLoan();
			}
		});
		
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
		    	textFieldUser.getText();
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
//		    	cleanTextFields();
		    }

		    @Override
		    public void removeUpdate(DocumentEvent e) {
//		    	String txt = textFieldBook.getText();
//		    	filteredBooks(txt);
//		    	cleanTextFields();
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
		sl_contentPane.putConstraint(SpringLayout.NORTH, btnSave, 0, SpringLayout.NORTH, btnClose);
		sl_contentPane.putConstraint(SpringLayout.WEST, btnSave, 0, SpringLayout.WEST, contentPane);
		contentPane.add(btnSave);
		
		btnSave.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (formattedTextFieldEndDate.getText().length() > 0) {		
					loan = new LoanDAO();
					loan.setBook_id(selectedBook);
					loan.setUser_id(selectedUser);
					Date startDate = new Date(); 
					Date endDate = new Date(); 
					loan.setStart_date(startDate);
					
					SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
					String endDateTextField = formattedTextFieldEndDate.getText();
//					System.out.println(endDateTextField); 
					try {
						endDate = sdf.parse(endDateTextField);
						loan.setEnd_date(endDate);
					} catch (ParseException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					} 

//					System.out.println(startDate);  
//					System.out.println(endDate); 
					
					if (endDate.after(startDate)) {
						System.out.println("saving loans");
						cleanTextFields();
						controller.saveLoan(loan);
					} else {
						System.out.println("End date equals to start date");
					}
					
				} else {
					System.out.println("show dialog");
        			JOptionPane.showMessageDialog(NewLoanView.this, 
                            "The filed 'End Date' is required, please enter a date in the format DD/MM/YYYY");
				}
			}
		});
		
		lblUserSelected = new JLabel("Selected User:");
		sl_contentPane.putConstraint(SpringLayout.NORTH, lblUserSelected, 50, SpringLayout.SOUTH, tableUsers);
		sl_contentPane.putConstraint(SpringLayout.WEST, lblUserSelected, 0, SpringLayout.WEST, lblUser);
		contentPane.add(lblUserSelected);
		
		lblSelectedUserValueId = new JLabel("none");
		sl_contentPane.putConstraint(SpringLayout.WEST, lblSelectedUserValueId, 17, SpringLayout.EAST, lblUserSelected);
		sl_contentPane.putConstraint(SpringLayout.SOUTH, lblSelectedUserValueId, 0, SpringLayout.SOUTH, lblUserSelected);
		contentPane.add(lblSelectedUserValueId);
		
		lblBookSelected = new JLabel("Selected Book:");
		sl_contentPane.putConstraint(SpringLayout.NORTH, lblBookSelected, 25, SpringLayout.SOUTH, lblUserSelected);
		sl_contentPane.putConstraint(SpringLayout.WEST, lblBookSelected, 0, SpringLayout.WEST, lblUser);
		contentPane.add(lblBookSelected);
		
		lblSelectedBookValueID = new JLabel("none");
		lblSelectedBookValueID.setSize(30, 40);
		sl_contentPane.putConstraint(SpringLayout.WEST, lblSelectedBookValueID, 0, SpringLayout.WEST, lblSelectedUserValueId);
		sl_contentPane.putConstraint(SpringLayout.SOUTH, lblSelectedBookValueID, 0, SpringLayout.SOUTH, lblBookSelected);
		contentPane.add(lblSelectedBookValueID);
		
		scrollPaneBooks = new JScrollPane();
		sl_contentPane.putConstraint(SpringLayout.NORTH, scrollPaneBooks, 1, SpringLayout.NORTH, tableUsers);
		sl_contentPane.putConstraint(SpringLayout.WEST, scrollPaneBooks, 0, SpringLayout.WEST, lblTableLabel);
		sl_contentPane.putConstraint(SpringLayout.SOUTH, scrollPaneBooks, 198, SpringLayout.NORTH, tableUsers);
		sl_contentPane.putConstraint(SpringLayout.EAST, scrollPaneBooks, 334, SpringLayout.EAST, lblTableLabel);
		contentPane.add(scrollPaneBooks);
		scrollPaneBooks.setViewportView(tableBooks);
		tableBooks.addMouseListener(tableBooksListener);
		
		scrollPaneUsers = new JScrollPane();
		sl_contentPane.putConstraint(SpringLayout.NORTH, scrollPaneUsers, 0, SpringLayout.NORTH, scrollPaneBooks);
		sl_contentPane.putConstraint(SpringLayout.WEST, scrollPaneUsers, 10, SpringLayout.WEST, contentPane);
		sl_contentPane.putConstraint(SpringLayout.SOUTH, scrollPaneUsers, 0, SpringLayout.SOUTH, scrollPaneBooks);
		sl_contentPane.putConstraint(SpringLayout.EAST, scrollPaneUsers, -6, SpringLayout.WEST, scrollPaneBooks);
		contentPane.add(scrollPaneUsers);
		scrollPaneUsers.setViewportView(tableUsers);
		tableUsers.addMouseListener(tableUsersListener);
		
		lblSelectedBookValueTitle = new JLabel("none");
		lblSelectedBookValueTitle.setSize(100, 40);
		sl_contentPane.putConstraint(SpringLayout.NORTH, lblSelectedBookValueTitle, 0, SpringLayout.NORTH, lblBookSelected);
		sl_contentPane.putConstraint(SpringLayout.WEST, lblSelectedBookValueTitle, 27, SpringLayout.EAST, lblSelectedBookValueID);
		contentPane.add(lblSelectedBookValueTitle);
		
		lblSelectedBookValueAuthor = new JLabel("none");
		sl_contentPane.putConstraint(SpringLayout.NORTH, lblSelectedBookValueAuthor, 0, SpringLayout.NORTH, lblBookSelected);
		sl_contentPane.putConstraint(SpringLayout.WEST, lblSelectedBookValueAuthor, 102, SpringLayout.EAST, lblSelectedBookValueTitle);
		lblSelectedBookValueAuthor.setSize(100, 40);
		contentPane.add(lblSelectedBookValueAuthor);
		
		lblSelectedBookValueYear = new JLabel("none");
		sl_contentPane.putConstraint(SpringLayout.NORTH, lblSelectedBookValueYear, 0, SpringLayout.NORTH, lblBookSelected);
		sl_contentPane.putConstraint(SpringLayout.WEST, lblSelectedBookValueYear, 108, SpringLayout.EAST, lblSelectedBookValueAuthor);
		lblSelectedBookValueYear.setSize(100, 40);
		contentPane.add(lblSelectedBookValueYear);
		
		lblEnd_date = new JLabel("End Date:");
		sl_contentPane.putConstraint(SpringLayout.NORTH, lblEnd_date, 21, SpringLayout.SOUTH, lblBookSelected);
		sl_contentPane.putConstraint(SpringLayout.WEST, lblEnd_date, 0, SpringLayout.WEST, lblUser);
		contentPane.add(lblEnd_date);
		
	    formattedTextFieldEndDate = new JFormattedTextField();
		sl_contentPane.putConstraint(SpringLayout.NORTH, formattedTextFieldEndDate, -5, SpringLayout.NORTH, lblEnd_date);
		sl_contentPane.putConstraint(SpringLayout.WEST, formattedTextFieldEndDate, 6, SpringLayout.EAST, lblEnd_date);
		sl_contentPane.putConstraint(SpringLayout.EAST, formattedTextFieldEndDate, 238, SpringLayout.WEST, contentPane);
		contentPane.add(formattedTextFieldEndDate);
		
		lblSelectedUserValueName = new JLabel("none");
		sl_contentPane.putConstraint(SpringLayout.NORTH, lblSelectedUserValueName, 0, SpringLayout.NORTH, lblUserSelected);
		sl_contentPane.putConstraint(SpringLayout.EAST, lblSelectedUserValueName, 0, SpringLayout.EAST, lblSelectedBookValueTitle);
		contentPane.add(lblSelectedUserValueName);
		
		lblSelectedUserValueSurname = new JLabel("none");
		sl_contentPane.putConstraint(SpringLayout.NORTH, lblSelectedUserValueSurname, 0, SpringLayout.NORTH, lblUserSelected);
		sl_contentPane.putConstraint(SpringLayout.EAST, lblSelectedUserValueSurname, 0, SpringLayout.EAST, lblSelectedBookValueAuthor);
		contentPane.add(lblSelectedUserValueSurname);

		
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
		Object[] columns = { "id", "Name", "Surname"};
		modelUsers = new DefaultTableModel();
		modelUsers.setColumnIdentifiers(columns);
		
    	List<UserDAO> list = controller.getUsersByRegex(criteria);
		for (UserDAO u : list) {
			System.out.println("*************************");
			System.out.println(u.getName());
			modelUsers.addRow(new Object[] {u.getId(), u.getName(), u.getSurname()});
		}
		
		tableUsers.setModel(modelUsers);
	}
	
	
	private void filteredBooks(String criteria) {
		Object[] columns = { "id", "Title", "Author", "Year"};
		modelBooks = new DefaultTableModel();
		modelBooks.setColumnIdentifiers(columns);
		
    	List<BookDAO> list = controller.getBooksByRegex(criteria);
		for (BookDAO b : list) {
//			System.out.println(String.format("size: %d", list.size()));
//			System.out.println("*************************");
//			System.out.println(b.getTitle());
			modelBooks.addRow(new Object[] {b.getId(), b.getTitle(), b.getAuthor(), b.getYear()});
		}
		
		tableBooks.setModel(modelBooks);
	}
	
	private void cleanTextFields() {
		lblSelectedUserValueId.setText("none");
		lblSelectedUserValueName.setText("none");
		lblSelectedUserValueSurname.setText("none");
		
		lblSelectedBookValueID.setText("none");
		lblSelectedBookValueTitle.setText("none");
		lblSelectedBookValueAuthor.setText("none");
		lblSelectedBookValueYear.setText("none");
		
		formattedTextFieldEndDate.setText("");
		selectedUser = new UserDAO();
		selectedBook = new BookDAO();
		
		modelBooks = new DefaultTableModel();
		tableBooks.setModel(modelBooks);
		modelUsers = new DefaultTableModel();
		tableUsers.setModel(modelUsers);
	
		tableBooks.clearSelection();
		textFieldBook.setText("");
		textFieldUser.setText("");
	}
	
	public class BooksRowSelectionListener implements MouseListener {
		@Override
		public void mouseClicked(MouseEvent e) {
			// TODO Auto-generated method stub
			System.out.println("clicking.....");
			getDataFromBooksTable();
		}

		@Override
		public void mousePressed(MouseEvent e) {
			// TODO Auto-generated method stub
			getDataFromBooksTable();
		}

		@Override
		public void mouseReleased(MouseEvent e) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void mouseEntered(MouseEvent e) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void mouseExited(MouseEvent e) {
			// TODO Auto-generated method stub
			
		}
	}
	
	public class UsersRowSelectionListener implements MouseListener {
		@Override
		public void mouseClicked(MouseEvent e) {
			getDataFromUsersTable();
		}

		@Override
		public void mousePressed(MouseEvent e) {
			// TODO Auto-generated method stub
			getDataFromUsersTable();
		}

		@Override
		public void mouseReleased(MouseEvent e) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void mouseEntered(MouseEvent e) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void mouseExited(MouseEvent e) {
			// TODO Auto-generated method stub
			
		}
	}
	
	private void getDataFromBooksTable() {
//    	if (!event.getValueIsAdjusting()) {
		String bookId = tableBooks.getValueAt(tableBooks.getSelectedRow(), 0).toString();
		String title = tableBooks.getValueAt(tableBooks.getSelectedRow(), 1).toString();
		String author = tableBooks.getValueAt(tableBooks.getSelectedRow(), 2).toString();
		String year = tableBooks.getValueAt(tableBooks.getSelectedRow(), 3).toString();
		System.out.println(bookId);
		System.out.println(title);
		System.out.println(author);
		System.out.println(year);
		
	    selectedBook = new BookDAO();
	    selectedBook.setId(Integer.valueOf(bookId));
	    selectedBook.setTitle(title);
	    selectedBook.setAuthor(author);
	    selectedBook.setYear(year);
		
	    
	    int titleMaxLenght = 20;
	    if (!(selectedBook.getTitle().length() > 20)) {
	    	titleMaxLenght = selectedBook.getTitle().length();
	    }
		lblSelectedBookValueID.setText(String.valueOf(bookId));
		lblSelectedBookValueTitle.setText(selectedBook.getTitle().substring(0, titleMaxLenght));
		lblSelectedBookValueAuthor.setText(selectedBook.getAuthor());
		lblSelectedBookValueYear.setText(selectedBook.getYear());
//    	}
	}
	
	
	private void getDataFromUsersTable() {
		String userId = tableUsers.getValueAt(tableUsers.getSelectedRow(), 0).toString();
		String name = tableUsers.getValueAt(tableUsers.getSelectedRow(), 1).toString();
		String surname = tableUsers.getValueAt(tableUsers.getSelectedRow(), 2).toString();

		selectedUser = new UserDAO();
		selectedUser.setId(Integer.valueOf(userId));
		selectedUser.setName(name);
		selectedUser.setSurname(surname);
		
		lblSelectedUserValueId.setText(String.valueOf(userId));
		lblSelectedUserValueName.setText(selectedUser.getName());
		lblSelectedUserValueSurname.setText(selectedUser.getSurname());
	}
}
