package views.Loan;

import java.awt.Dimension;
import java.awt.Frame;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.Date;
import java.util.List;
import java.util.Properties;

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
import javax.swing.border.EmptyBorder;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.table.DefaultTableModel;

import org.jdatepicker.impl.JDatePanelImpl;
import org.jdatepicker.impl.JDatePickerImpl;
import org.jdatepicker.impl.UtilDateModel;

import controllers.views.LoansController;
import models.db.BookDAO;
import models.db.LoanDAO;
import models.db.UserDAO;
import models.users.Utente;
import utils.DateLabelFormatter;
import utils.Observer;

public class NewLoanViewForUser extends JDialog implements Observer {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private LoansController controller = null;
	private JTextField textFieldBook;
	private JTable tableBooks;
	private JLabel lblBooks;
	private JButton btnSave;
	private JLabel lblUserSelected;
	private JLabel lblSelectedUserTessera;
	private JLabel lblBookSelected;
	private JLabel lblSelectedBookValueID;
	private JScrollPane scrollPaneBooks;
	private JLabel lblSelectedBookValueTitle;
	private JLabel lblSelectedBookValueAuthor;
	private JLabel lblSelectedBookValueYear;
	private BookDAO selectedBook;
	private UserDAO selectedUser;
	private LoanDAO loan;
	private JLabel lblEnd_date;
	private JDatePickerImpl datePickerEnd;
	private UtilDateModel modelEnd;
	private JLabel lblSelectedUserId;
	private JLabel lblSelectedUserValueName;
	private JLabel lblSelectedUserValueSurname;
	private DefaultTableModel modelBooks;
	int centerX;
	int centerY;
	private BooksRowSelectionListener tableBooksListener = new BooksRowSelectionListener();
	private Utente user = null;


	/**
	 * Create the frame.
	 */
	public NewLoanViewForUser(LoansController loanController, Utente user) {
//		cleanTextFields();
		super((Frame)null, "Nuovo prestito - "+user.getNome()+" "+user.getCognome(), true);
		controller = loanController;
		this.user = user;
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
		
		modelEnd = new UtilDateModel();
		Properties p=new Properties();
		p.put("text.today","Today");
		p.put("text.month","Month");
		p.put("text.year","Year");
		JDatePanelImpl datePanel2 = new JDatePanelImpl(modelEnd, p);
		datePickerEnd = new JDatePickerImpl(datePanel2, new DateLabelFormatter());
		contentPane.add(datePickerEnd);
	
		JButton btnClose = new JButton("Close");
		sl_contentPane.putConstraint(SpringLayout.SOUTH, btnClose, -10, SpringLayout.SOUTH, contentPane);
		sl_contentPane.putConstraint(SpringLayout.EAST, btnClose, -10, SpringLayout.EAST, contentPane);
		contentPane.add(btnClose);
		btnClose.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				tableBooks.removeMouseListener(tableBooksListener);
				cleanTextFields();
				dispose();
				controller.closeNewLoan();
			}
		});
		
		JLabel lblBook = new JLabel("Titolo:");
		sl_contentPane.putConstraint(SpringLayout.NORTH, lblBook, 26, SpringLayout.NORTH, contentPane);
		sl_contentPane.putConstraint(SpringLayout.WEST, lblBook, 10, SpringLayout.WEST, contentPane);
		contentPane.add(lblBook);
		
		textFieldBook = new JTextField(40);
		sl_contentPane.putConstraint(SpringLayout.NORTH, textFieldBook, 6, SpringLayout.SOUTH, lblBook);
		sl_contentPane.putConstraint(SpringLayout.WEST, textFieldBook, 10, SpringLayout.WEST, contentPane);
		sl_contentPane.putConstraint(SpringLayout.SOUTH, textFieldBook, 87, SpringLayout.NORTH, contentPane);
		sl_contentPane.putConstraint(SpringLayout.EAST, textFieldBook, -604, SpringLayout.EAST, contentPane);
		textFieldBook.setColumns(100);
		contentPane.add(textFieldBook);
		
		textFieldBook.getDocument().addDocumentListener(new DocumentListener() {
		    @Override
		    public void insertUpdate(DocumentEvent e) {
		    	String txt = textFieldBook.getText();
		    	if (txt.length() > 0) {
		    		filteredBooks(txt);
		    	} else {
		    		cleanTextFields();
		    	}
		    }

		    @Override
		    public void removeUpdate(DocumentEvent e) {
		    	String txt = textFieldBook.getText();		    	
		    	if (txt.length() > 0) {
		    		filteredBooks(txt);
		    	} else {
		    		cleanTextFields();
		    	}
		    }

		    @Override
		    public void changedUpdate(DocumentEvent e) {}
		});
		
		tableBooks = new JTable();
		sl_contentPane.putConstraint(SpringLayout.WEST, tableBooks, 10, SpringLayout.WEST, contentPane);
		sl_contentPane.putConstraint(SpringLayout.SOUTH, tableBooks, -234, SpringLayout.SOUTH, contentPane);
		
		lblBooks = new JLabel("Libri");
		sl_contentPane.putConstraint(SpringLayout.WEST, lblBooks, 10, SpringLayout.WEST, contentPane);
		sl_contentPane.putConstraint(SpringLayout.NORTH, tableBooks, 11, SpringLayout.SOUTH, lblBooks);
		sl_contentPane.putConstraint(SpringLayout.NORTH, lblBooks, 17, SpringLayout.SOUTH, textFieldBook);
		contentPane.add(lblBooks);
		
		btnSave = new JButton("Save");
		sl_contentPane.putConstraint(SpringLayout.NORTH, btnSave, 0, SpringLayout.NORTH, btnClose);
		sl_contentPane.putConstraint(SpringLayout.WEST, btnSave, 0, SpringLayout.WEST, contentPane);
		contentPane.add(btnSave);
		
		btnSave.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {				
				String bookId = lblSelectedBookValueID.getText();
				String title = lblSelectedBookValueTitle.getText();
				String author = lblSelectedBookValueAuthor.getText();
				String year = lblSelectedBookValueYear.getText();
				
				String id = lblSelectedUserId.getText();
				String name = lblSelectedUserValueName.getText();
				String surname = lblSelectedUserValueSurname.getText();
				
				String endDateString = datePickerEnd.getModel().getValue() != null ? datePickerEnd.getModel().getValue().toString() : "";
									
				if (bookId.length() == 0 || title.length() == 0 || 
						author.length() == 0 || year.length() == 0 ||
								id.length() == 0 || name.length() == 0 ||
						surname.length() == 0 || endDateString.length() == 0) {
	       			JOptionPane.showMessageDialog(NewLoanViewForUser.this, 
                            "All field are required!");
				} else {
					loan = new LoanDAO();
					loan.setBook_id(selectedBook);
					loan.setUser_id(selectedUser);
					Date startDate = new Date(); 
					loan.setStart_date(startDate);
					Date endDate = (Date) datePickerEnd.getModel().getValue();
					loan.setEnd_date(endDate);
					
					if (endDate.after(startDate)) {
						//System.out.println("saving loans");
						cleanTextFields();
						controller.saveLoan(loan);
					} else {
						System.out.println("End date equals to start date");
					}
				}
			}
		});
		
		lblUserSelected = new JLabel("Selected User:");
		sl_contentPane.putConstraint(SpringLayout.NORTH, lblUserSelected, 50, SpringLayout.SOUTH, tableBooks);
		sl_contentPane.putConstraint(SpringLayout.WEST, lblUserSelected, 0, SpringLayout.WEST, lblBook);
		contentPane.add(lblUserSelected);
		
		lblSelectedUserTessera = new JLabel();
		sl_contentPane.putConstraint(SpringLayout.WEST, datePickerEnd, 0, SpringLayout.WEST, lblSelectedUserTessera);
		sl_contentPane.putConstraint(SpringLayout.WEST, lblSelectedUserTessera, 17, SpringLayout.EAST, lblUserSelected);
		sl_contentPane.putConstraint(SpringLayout.SOUTH, lblSelectedUserTessera, 0, SpringLayout.SOUTH, lblUserSelected);
		contentPane.add(lblSelectedUserTessera);
		
		lblBookSelected = new JLabel("Selected Book:");
		sl_contentPane.putConstraint(SpringLayout.NORTH, lblBookSelected, 25, SpringLayout.SOUTH, lblUserSelected);
		sl_contentPane.putConstraint(SpringLayout.WEST, lblBookSelected, 0, SpringLayout.WEST, lblBook);
		contentPane.add(lblBookSelected);
		
		lblSelectedBookValueID = new JLabel("none");
		lblSelectedBookValueID.setSize(30, 40);
		sl_contentPane.putConstraint(SpringLayout.WEST, lblSelectedBookValueID, 0, SpringLayout.WEST, lblSelectedUserTessera);
		sl_contentPane.putConstraint(SpringLayout.SOUTH, lblSelectedBookValueID, 0, SpringLayout.SOUTH, lblBookSelected);
		contentPane.add(lblSelectedBookValueID);
		tableBooks.addMouseListener(tableBooksListener);
		
		scrollPaneBooks = new JScrollPane();
		sl_contentPane.putConstraint(SpringLayout.NORTH, scrollPaneBooks, 12, SpringLayout.SOUTH, lblBooks);
		sl_contentPane.putConstraint(SpringLayout.SOUTH, scrollPaneBooks, -49, SpringLayout.NORTH, lblUserSelected);
		sl_contentPane.putConstraint(SpringLayout.EAST, scrollPaneBooks, -383, SpringLayout.EAST, contentPane);
		sl_contentPane.putConstraint(SpringLayout.WEST, scrollPaneBooks, 10, SpringLayout.WEST, contentPane);
		contentPane.add(scrollPaneBooks);
		scrollPaneBooks.setViewportView(tableBooks);
		
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
		
		lblEnd_date = new JLabel("Fine Prestito:");
		sl_contentPane.putConstraint(SpringLayout.SOUTH, datePickerEnd, 0, SpringLayout.SOUTH, lblEnd_date);
		sl_contentPane.putConstraint(SpringLayout.NORTH, lblEnd_date, 21, SpringLayout.SOUTH, lblBookSelected);
		sl_contentPane.putConstraint(SpringLayout.WEST, lblEnd_date, 0, SpringLayout.WEST, lblBook);
		contentPane.add(lblEnd_date);
		
		lblSelectedUserValueName = new JLabel("none");
		sl_contentPane.putConstraint(SpringLayout.NORTH, lblSelectedUserValueName, 0, SpringLayout.NORTH, lblUserSelected);
		contentPane.add(lblSelectedUserValueName);
		
		lblSelectedUserValueSurname = new JLabel("none");
		sl_contentPane.putConstraint(SpringLayout.NORTH, lblSelectedUserValueSurname, 0, SpringLayout.NORTH, lblUserSelected);
		sl_contentPane.putConstraint(SpringLayout.WEST, lblSelectedUserValueSurname, 58, SpringLayout.EAST, lblSelectedUserValueName);
		contentPane.add(lblSelectedUserValueSurname);
		
	    lblSelectedUserId = new JLabel("none");
		sl_contentPane.putConstraint(SpringLayout.WEST, lblSelectedUserValueName, 52, SpringLayout.EAST, lblSelectedUserId);
		sl_contentPane.putConstraint(SpringLayout.NORTH, lblSelectedUserId, 0, SpringLayout.NORTH, lblUserSelected);
		sl_contentPane.putConstraint(SpringLayout.WEST, lblSelectedUserId, 0, SpringLayout.WEST, datePickerEnd);
		contentPane.add(lblSelectedUserId);

		
		this.selectedUser = controller.getUserByTesseraId(user.getIdTessera());
		setTextFieldsForUser();
		controller.addObserver(this);
	}

	@Override
	public void update(String type, Object arg) {
		//System.out.println("New loan user!");
		if(type.equals("OPEN_NEW_LOAN_USER")) {
			tableBooks.addMouseListener(tableBooksListener);
			this.setVisible(true);
		}
		if(type.equals("CLOSE_NEW_LOAN_USER")){
			this.setVisible(false);
		}
	}
	
	
	private void filteredBooks(String criteria) {
		Object[] columns = { "Book id", "Titolo", "Autore", "Casa editrice", "Anno"};
		modelBooks = new DefaultTableModel();
		modelBooks.setColumnIdentifiers(columns);
		
    	List<BookDAO> list = controller.getBooksByRegex(criteria);
		for (BookDAO b : list) {
//			System.out.println(String.format("size: %d", list.size()));
//			System.out.println("*************************");
//			System.out.println(b.getTitle());
			modelBooks.addRow(new Object[] {b.getId(), b.getTitle(), b.getAuthor(), b.getEditor(), b.getYear()});
		}
		
		tableBooks.setModel(modelBooks);
	}
	
	private void setTextFieldsForUser() {
		lblSelectedUserId.setText(String.valueOf(user.getIdTessera()));
		lblSelectedUserValueName.setText(user.getNome());
		lblSelectedUserValueSurname.setText(user.getCognome());
	}
	
	private void cleanTextFields() {
		lblSelectedBookValueID.setText("none");
		lblSelectedBookValueTitle.setText("none");
		lblSelectedBookValueAuthor.setText("none");
		lblSelectedBookValueYear.setText("none");
		
		modelEnd = new UtilDateModel();
		Properties p=new Properties();
		p.put("text.today","Today");
		p.put("text.month","Month");
		p.put("text.year","Year");
		JDatePanelImpl datePanel2 = new JDatePanelImpl(modelEnd, p);
		datePickerEnd = new JDatePickerImpl(datePanel2, new DateLabelFormatter());
		
		selectedBook = new BookDAO();
		
		modelBooks = new DefaultTableModel();
		tableBooks.setModel(modelBooks);
	
		tableBooks.clearSelection();
		textFieldBook.setText("");
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
}
