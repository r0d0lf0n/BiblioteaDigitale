package views.Catalog;

import java.awt.Dimension;
import java.awt.Frame;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;

import controllers.views.CatalogController;
import models.bl.CatalogModel;
import models.db.BookDAO;
import models.db.LoanDAO;
import models.db.UserDAO;
import utils.Observer;

public class BookDetailView extends JDialog implements Observer {
	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private CatalogController controller = null;
	private CatalogModel catalogModel;
	private BookDAO selectedBook;
	private UserDAO selectedUser;
	private LoanDAO loan;
	int centerX;
	int centerY;
	private BookDAO book;
	private BookDAO newBook;
	private JTextField textFieldId;
	private JTextField textFieldTitle;
	private JTextField textFieldAuthor;
	private JTextField textFieldDescription;
	private JTextField textFieldISBN;
	private JTextField textFieldEditor;
	private JTextField textFieldYear;
	private Boolean editing = false;
	
	/**
	 * Create the frame.
	 */
	public BookDetailView(CatalogController catalogController, BookDAO b) {
		super((Frame)null, "Dettaglio Libro", true);
		book = b;
		//System.out.println(book.getId());
		controller = catalogController;
		catalogModel = new CatalogModel();
//		Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
//        int screenWidth = screenSize.width;
//        int screenHeight = screenSize.height;
//	    centerX = screenWidth / 2;
//	    centerY = screenHeight / 2;
		
/*		setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
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
		textFieldYear.setColumns(10); */
		

        setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        setBounds(100, 100, 600, 400);
        setLocationRelativeTo(null);

        contentPane = new JPanel();
        contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
        setContentPane(contentPane);
        contentPane.setLayout(new GridBagLayout());

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.anchor = GridBagConstraints.WEST;
        gbc.fill = GridBagConstraints.HORIZONTAL; 

        JLabel lblBookId = new JLabel("Book ID:");
        JLabel lblTitle = new JLabel("Title:");
        JLabel lblAuthor = new JLabel("Author:");
        JLabel lblEditor = new JLabel("Editor:");
        JLabel lblYear = new JLabel("Year:");
        JLabel lblDescription = new JLabel("Description:");
        JLabel lblISBN = new JLabel("ISBN:");

        textFieldId = new JTextField(40);
        textFieldTitle = new JTextField(40);
        textFieldAuthor = new JTextField(40);
        textFieldEditor = new JTextField(40);
        textFieldYear = new JTextField(40);
        textFieldISBN = new JTextField(40);
        textFieldDescription = new JTextField(40); 

        JTextField[] textFields = {textFieldId, textFieldTitle, textFieldAuthor, textFieldEditor, textFieldYear, textFieldISBN, textFieldDescription};
        for (JTextField textField : textFields) {
            textField.setEditable(false);
            textField.setMinimumSize(new Dimension(240, 30)); 
        }

        JButton btnClose = new JButton("Close");
        JButton btnEdit = new JButton("Edit");

        gbc.gridx = 0;
        gbc.gridy = 0;
        contentPane.add(lblBookId, gbc);

        gbc.gridx = 1;
        contentPane.add(textFieldId, gbc);

        gbc.gridx = 0;
        gbc.gridy = 1;
        contentPane.add(lblTitle, gbc);

        gbc.gridx = 1;
        contentPane.add(textFieldTitle, gbc);

        gbc.gridx = 0;
        gbc.gridy = 2;
        contentPane.add(lblAuthor, gbc);

        gbc.gridx = 1;
        contentPane.add(textFieldAuthor, gbc);

        gbc.gridx = 0;
        gbc.gridy = 3;
        contentPane.add(lblEditor, gbc);

        gbc.gridx = 1;
        contentPane.add(textFieldEditor, gbc);

        gbc.gridx = 0;
        gbc.gridy = 4;
        contentPane.add(lblYear, gbc);

        gbc.gridx = 1;
        contentPane.add(textFieldYear, gbc);

        gbc.gridx = 0;
        gbc.gridy = 5;
        contentPane.add(lblISBN, gbc);

        gbc.gridx = 1;
        contentPane.add(textFieldISBN, gbc);

        gbc.gridx = 0;
        gbc.gridy = 6;
        gbc.gridwidth = 2; 
        contentPane.add(lblDescription, gbc);

        gbc.gridy = 7;
        contentPane.add(textFieldDescription, gbc);

        gbc.gridwidth = 1; 
        gbc.gridy = 8;
        gbc.gridx = 0;
        contentPane.add(btnEdit, gbc);

        gbc.gridx = 1;
        contentPane.add(btnClose, gbc);

		
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
					//catalogController.closeBookDetailPanel(editing);
					//editing = false;
				}
			}
		});
	//	sl_contentPane.putConstraint(SpringLayout.WEST, btnEdit, 0, SpringLayout.WEST, lblBookId);
	//	sl_contentPane.putConstraint(SpringLayout.SOUTH, btnEdit, -22, SpringLayout.SOUTH, contentPane);
	//	contentPane.add(btnEdit);
		btnClose.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				boolean isEdited = isBookEdited();
				if(isEdited)
					saveBook();
				catalogController.closeBookDetailPanel(isEdited);
			}
		});
		
		controller.addObserver(this);
		
		this.addWindowListener(new WindowAdapter() {
	        @Override
	        public void windowClosing(WindowEvent e) {
				boolean isEdited = isBookEdited();
				if(isEdited)
					saveBook();
	        	catalogController.closeBookDetailPanel(isEdited);
	        }

	    });
		
		setTextFieldValues();
	}
	
	private boolean isBookEdited() {
		String id = textFieldId.getText().trim();
		String title = textFieldTitle.getText().trim();
		String author = textFieldAuthor.getText().trim();
		String editor = textFieldEditor.getText().trim();
		String year = textFieldYear.getText().trim();
		String desc = textFieldDescription.getText().trim();
		String ISBN = textFieldISBN.getText().trim();
		
		newBook = new BookDAO();
		newBook.setId(Integer.valueOf(id));
		newBook.setTitle(title);
		newBook.setAuthor(author);
		newBook.setEditor(editor);
		newBook.setYear(year);
		newBook.setDescription(desc);
		newBook.setIsbn(ISBN);
		
		if(newBook.equals(book))
			return false;
		
		return true;
	}
	
	private void saveBook() {
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
		//System.out.println("Book details!");
		if(type.equals("OPEN_BOOK_DETAIL")) {
			this.setVisible(true);
			editing = false;
		}
		if(type.equals("CLOSE_BOOK_DETAIL")){
			this.setVisible(false);
		}
	}
}

