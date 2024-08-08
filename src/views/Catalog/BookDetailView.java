package views.Catalog;

import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import com.j256.ormlite.field.DatabaseField;

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
	private JTextField textFieldTitle;
	private JTextField textFieldAuthor;
	private JTextField textFieldDescription;
	private JTextField textFieldISBN;
	private JTextField textFieldEditor;
	private JTextField textFieldYear;
	private Boolean editing = false;
	
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
		catalogModel = new CatalogModel();
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
		sl_contentPane.putConstraint(SpringLayout.EAST, textFieldId, 200, SpringLayout.EAST, lblBookId);
		textFieldId.setEditable(false);
		sl_contentPane.putConstraint(SpringLayout.NORTH, textFieldId, 6, SpringLayout.SOUTH, lblBookId);
		sl_contentPane.putConstraint(SpringLayout.WEST, textFieldId, 0, SpringLayout.WEST, lblBookId);
		contentPane.add(textFieldId);
		textFieldId.setColumns(10);
		
		JButton btnClose = new JButton("Close");
		sl_contentPane.putConstraint(SpringLayout.SOUTH, btnClose, -10, SpringLayout.SOUTH, contentPane);
		sl_contentPane.putConstraint(SpringLayout.EAST, btnClose, -10, SpringLayout.EAST, contentPane);
		contentPane.add(btnClose);
		
		JLabel lblTitle = new JLabel("Title: ");
		sl_contentPane.putConstraint(SpringLayout.NORTH, lblTitle, 16, SpringLayout.SOUTH, textFieldId);
		sl_contentPane.putConstraint(SpringLayout.WEST, lblTitle, 0, SpringLayout.WEST, lblBookId);
		contentPane.add(lblTitle);
		
		JLabel lblAuthor = new JLabel("Author:");
		sl_contentPane.putConstraint(SpringLayout.WEST, lblAuthor, 0, SpringLayout.WEST, lblBookId);
		contentPane.add(lblAuthor);
		
		JLabel lblYear = new JLabel("Year:");
		sl_contentPane.putConstraint(SpringLayout.NORTH, lblYear, 0, SpringLayout.NORTH, lblAuthor);
		contentPane.add(lblYear);
		
		JLabel lblDescription = new JLabel("Description:");
		sl_contentPane.putConstraint(SpringLayout.WEST, lblDescription, 0, SpringLayout.WEST, lblBookId);
		contentPane.add(lblDescription);
		
		JLabel lblISBN = new JLabel("ISBN:");
		sl_contentPane.putConstraint(SpringLayout.WEST, lblYear, 0, SpringLayout.WEST, lblISBN);
		sl_contentPane.putConstraint(SpringLayout.WEST, lblISBN, 329, SpringLayout.EAST, lblBookId);
		sl_contentPane.putConstraint(SpringLayout.NORTH, lblISBN, 0, SpringLayout.NORTH, lblBookId);
		contentPane.add(lblISBN);
		
		JLabel lblEditor = new JLabel("Editor:");
		sl_contentPane.putConstraint(SpringLayout.NORTH, lblEditor, 0, SpringLayout.NORTH, lblTitle);
		sl_contentPane.putConstraint(SpringLayout.WEST, lblEditor, 0, SpringLayout.WEST, lblYear);
		contentPane.add(lblEditor);
		
		textFieldTitle = new JTextField();
		sl_contentPane.putConstraint(SpringLayout.WEST, textFieldTitle, 20, SpringLayout.WEST, contentPane);
		sl_contentPane.putConstraint(SpringLayout.NORTH, lblAuthor, 29, SpringLayout.SOUTH, textFieldTitle);
		textFieldTitle.setEditable(false);
		sl_contentPane.putConstraint(SpringLayout.NORTH, textFieldTitle, 6, SpringLayout.SOUTH, lblTitle);
		contentPane.add(textFieldTitle);
		textFieldTitle.setColumns(10);
		
		textFieldAuthor = new JTextField();
		sl_contentPane.putConstraint(SpringLayout.WEST, textFieldAuthor, 20, SpringLayout.WEST, contentPane);
		sl_contentPane.putConstraint(SpringLayout.NORTH, lblDescription, 40, SpringLayout.SOUTH, textFieldAuthor);
		textFieldAuthor.setEditable(false);
		sl_contentPane.putConstraint(SpringLayout.NORTH, textFieldAuthor, 6, SpringLayout.SOUTH, lblAuthor);
		contentPane.add(textFieldAuthor);
		textFieldAuthor.setColumns(10);
		
		textFieldDescription = new JTextField();
		sl_contentPane.putConstraint(SpringLayout.EAST, textFieldDescription, 0, SpringLayout.EAST, textFieldId);
		textFieldDescription.setEditable(false);
		sl_contentPane.putConstraint(SpringLayout.NORTH, textFieldDescription, 6, SpringLayout.SOUTH, lblDescription);
		sl_contentPane.putConstraint(SpringLayout.WEST, textFieldDescription, 0, SpringLayout.WEST, lblBookId);
		sl_contentPane.putConstraint(SpringLayout.SOUTH, textFieldDescription, 110, SpringLayout.SOUTH, lblDescription);
		contentPane.add(textFieldDescription);
		textFieldDescription.setColumns(10);
		
		textFieldISBN = new JTextField();
		sl_contentPane.putConstraint(SpringLayout.NORTH, textFieldISBN, 6, SpringLayout.SOUTH, lblISBN);
		sl_contentPane.putConstraint(SpringLayout.EAST, textFieldISBN, -145, SpringLayout.EAST, contentPane);
		textFieldISBN.setEditable(false);
		sl_contentPane.putConstraint(SpringLayout.WEST, textFieldISBN, 0, SpringLayout.WEST, lblYear);
		contentPane.add(textFieldISBN);
		textFieldISBN.setColumns(10);
		
		textFieldEditor = new JTextField();
		sl_contentPane.putConstraint(SpringLayout.EAST, textFieldTitle, -129, SpringLayout.WEST, textFieldEditor);
		sl_contentPane.putConstraint(SpringLayout.WEST, textFieldEditor, 400, SpringLayout.WEST, contentPane);
		sl_contentPane.putConstraint(SpringLayout.EAST, textFieldEditor, 0, SpringLayout.EAST, textFieldISBN);
		textFieldEditor.setEditable(false);
		sl_contentPane.putConstraint(SpringLayout.NORTH, textFieldEditor, 6, SpringLayout.SOUTH, lblEditor);
		contentPane.add(textFieldEditor);
		textFieldEditor.setColumns(10);
		
		textFieldYear = new JTextField();
		sl_contentPane.putConstraint(SpringLayout.EAST, textFieldAuthor, -129, SpringLayout.WEST, textFieldYear);
		sl_contentPane.putConstraint(SpringLayout.WEST, textFieldYear, 400, SpringLayout.WEST, contentPane);
		sl_contentPane.putConstraint(SpringLayout.EAST, textFieldYear, 0, SpringLayout.EAST, textFieldISBN);
		textFieldYear.setEditable(false);
		sl_contentPane.putConstraint(SpringLayout.NORTH, textFieldYear, 6, SpringLayout.SOUTH, lblYear);
		contentPane.add(textFieldYear);
		textFieldYear.setColumns(10);
		
		JButton btnEdit = new JButton("Edit");
		btnEdit.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (!editing) {
					toggleTextFieldEditing(true);
					btnEdit.setText("Save");
					editing = true;
				} else {
					toggleTextFieldEditing(false);
					btnEdit.setText("Edit");
					editing = false;
					saveBook();
					catalogController.closeBookDetailPanel();
				}
			}
		});
		sl_contentPane.putConstraint(SpringLayout.WEST, btnEdit, 0, SpringLayout.WEST, lblBookId);
		sl_contentPane.putConstraint(SpringLayout.SOUTH, btnEdit, -22, SpringLayout.SOUTH, contentPane);
		contentPane.add(btnEdit);
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
//	        	textFieldId.setText("");
	        }
	    });
		
		setTextFieldValues();
	}
	
	private void saveBook() {
		String id = textFieldId.getText();
		String title = textFieldTitle.getText();
		String author = textFieldAuthor.getText();
		String editor = textFieldEditor.getText();
		String year = textFieldYear.getText();
		String desc = textFieldDescription.getText();
		String ISBN = textFieldISBN.getText();
		
		BookDAO newBook = new BookDAO();
		newBook.setId(Integer.valueOf(id));
		newBook.setTitle(title);
		newBook.setAuthor(author);
		newBook.setEditor(editor);
		newBook.setYear(year);
		newBook.setDescription(desc);
		newBook.setIsbn(ISBN);
			
		catalogModel.saveBook(newBook);
	}
	
	private void setTextFieldValues() {
		textFieldId.setText(String.valueOf(book.getId()));
		textFieldTitle.setText(book.getTitle());
		textFieldAuthor.setText(book.getAuthor());
		textFieldEditor.setText(book.getEditor());
		textFieldYear.setText(book.getYear());
		textFieldDescription.setText(book.getDescription());
		textFieldISBN.setText(book.getIsbn());
	}
	
	private void toggleTextFieldEditing(Boolean flag) {
//		textFieldId.setEditable(true);
		textFieldTitle.setEditable(flag);
		textFieldAuthor.setEditable(flag);
		textFieldEditor.setEditable(flag);
		textFieldYear.setEditable(flag);
		textFieldDescription.setEditable(flag);
		textFieldISBN.setEditable(flag);
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

