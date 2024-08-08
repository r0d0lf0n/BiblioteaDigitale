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
import utils.Observer;

public class BookDetailView extends JDialog implements Observer {
	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private CatalogController controller = null;
	private CatalogModel catalogModel;
	//private BookDAO selectedBook;
	//private UserDAO selectedUser;
	//private LoanDAO loan;
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

