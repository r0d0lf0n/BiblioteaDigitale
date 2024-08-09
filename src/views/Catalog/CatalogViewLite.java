package views.Catalog;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.File;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.SpringLayout;
import javax.swing.SwingUtilities;
import javax.swing.border.EmptyBorder;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.table.DefaultTableModel;

import controllers.views.CatalogController;
import controllers.views.ExternalUserController;
import controllers.views.LandingPageController;
import models.db.BookDAO;
import models.db.LoanDAO;
import models.users.Utente;
import utils.Observer;
import views.Loan.UpdateLoanView;
import views.Loan.NewLoanView.BooksRowSelectionListener;

public class CatalogViewLite extends JFrame implements Observer{

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JTable catalogTable;
	private CatalogController controller = null;
	private ExternalUserController controllerExternal = null;
	//private List<BookDAO> book_catalog = null;
	private Object[] columns = {"Book ID", "Titolo", "Autore", "Casa Editrice", "Anno", "Descrizione", "ISBN" };
	private BookDAO selectedBook;
	private BookDetailView bookDetailView = null;
	//private JButton btnRefresh;
	private DefaultTableModel model;
	private Utente admin = null;
	private CatalogRowSelectionListener catalogRowSelectionListener = new CatalogRowSelectionListener();
	private List<BookDAO> books_found = null;
	
	/**
	 * Create the frame.
	 */
	public CatalogViewLite(LandingPageController landingPageController, List<BookDAO> books) {
		
		controller = new CatalogController();
		controllerExternal = new ExternalUserController();
		this.setTitle("Gestione Catalogo");
		setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
		setBounds(100, 100, 800, 600);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setLocationRelativeTo(null);
		setContentPane(contentPane);
		SpringLayout sl_contentPane = new SpringLayout();
		contentPane.setLayout(sl_contentPane);

		// Riorganizzazione dei pulsanti e della tabella
		JButton btnClose = new JButton("Close");
		btnClose.addActionListener(new ActionListener() {
		    public void actionPerformed(ActionEvent e) {
		    	catalogTable.removeMouseListener(catalogRowSelectionListener);
		        landingPageController.openLandingPanel();
		    }
		});
		sl_contentPane.putConstraint(SpringLayout.WEST, btnClose, 0, SpringLayout.WEST, contentPane);
		sl_contentPane.putConstraint(SpringLayout.SOUTH, btnClose, -10, SpringLayout.SOUTH, contentPane);
		contentPane.add(btnClose);

		// Pulsante "Insert new book"
		JButton btnInsertNewBook = new JButton("Gestione Libro");
		sl_contentPane.putConstraint(SpringLayout.NORTH, btnInsertNewBook, 10, SpringLayout.NORTH, contentPane);
		sl_contentPane.putConstraint(SpringLayout.WEST, btnInsertNewBook, 0, SpringLayout.WEST, btnClose);
		btnInsertNewBook.addActionListener(new ActionListener() {
		    public void actionPerformed(ActionEvent e) {	    	
				if (!catalogTable.getSelectionModel().isSelectionEmpty()) {		
			        BookDetailView insertBook = new BookDetailView(CatalogViewLite.this.controller, selectedBook);
			        insertBook.setVisible(true);
				} else {
	       			JOptionPane.showMessageDialog(CatalogViewLite.this, "Select book to edit!");
				}
		    }
		});
		contentPane.add(btnInsertNewBook);

		// ScrollPane e JTable
		JScrollPane scrollPane = new JScrollPane();
		sl_contentPane.putConstraint(SpringLayout.NORTH, scrollPane, 4, SpringLayout.NORTH, btnInsertNewBook);
		sl_contentPane.putConstraint(SpringLayout.WEST, scrollPane, 30, SpringLayout.EAST, btnInsertNewBook);
		sl_contentPane.putConstraint(SpringLayout.SOUTH, scrollPane, -42, SpringLayout.SOUTH, contentPane);
		sl_contentPane.putConstraint(SpringLayout.EAST, scrollPane, -15, SpringLayout.EAST, contentPane);
		contentPane.add(scrollPane);

		catalogTable = new JTable();
		scrollPane.setViewportView(catalogTable);
		sl_contentPane.putConstraint(SpringLayout.WEST, catalogTable, 350, SpringLayout.WEST, contentPane); // Allineata con lo scrollPane
		sl_contentPane.putConstraint(SpringLayout.SOUTH, catalogTable, -211, SpringLayout.NORTH, btnClose);
		sl_contentPane.putConstraint(SpringLayout.EAST, catalogTable, -320, SpringLayout.EAST, contentPane);

		catalogTable.addMouseListener(catalogRowSelectionListener);
	
		
		this.addWindowListener(new WindowAdapter() {
	         @Override
	         public void windowClosing(WindowEvent e) {
	        	 landingPageController.openLandingPanel();
	         }
	     });
		
//		if(getBookCatalog().size() == 0)
		initializeTable();
		this.books_found = books;
		controller.addObserver(this);
		controllerExternal.addObserver(this);
		landingPageController.addObserver(this);
	}

	protected void loadCSV(File selectedFile) {
		controller.loadCSV(selectedFile.getPath());
	}


	private void initializeTable() {
	    model = new DefaultTableModel();
		model.setColumnIdentifiers(columns);
		List<BookDAO> book_catalog = controller.getBookCatalog();
//		List<BookDAO> book_catalog = this.books_found;
		
		for(int i = 0; i < book_catalog.size(); i++) {
			model.addRow(new Object[] {book_catalog.get(i).getId(), book_catalog.get(i).getTitle(), book_catalog.get(i).getAuthor(), book_catalog.get(i).getEditor(), book_catalog.get(i).getYear(), book_catalog.get(i).getDescription(), book_catalog.get(i).getIsbn()});
		}

		catalogTable.setModel(model);
	}
	
	@Override
	public void update(String type, Object arg) {
		if(type.equals("CLOSE_CATALOG_LITE")){
			this.setVisible(false);
		}
		
		if(type.equals("NEW_SEARCH_RESULTS")) {
			this.setVisible(true);
			this.books_found = (List<BookDAO>) arg;
			System.out.println("Opening catalogview lite");
//			System.out.println(this.books_found);
		}
		
		if(type.equals("REFRESH_BOOK_DETAIL")) {
			initializeTable();
		}
	}
	
	private class LoadBooksThread extends Thread {
	    public void run() {
	        DefaultTableModel currentModel = (DefaultTableModel) catalogTable.getModel();

	        SwingUtilities.invokeLater(new Runnable() {
	            public void run() {
	                for (BookDAO book : controller.getBookCatalog()) {
	                    currentModel.addRow(new Object[]{
	                        book.getId(),
	                        book.getTitle(),
	                        book.getAuthor(),
	                        book.getEditor(),
	                        book.getYear(),
	                        book.getDescription(),
	                        book.getIsbn()
	                    });
	                }
	    			JOptionPane.showMessageDialog(CatalogViewLite.this, 
	                        "Aggiornamento completato, presenti: "+ controller.getBookCatalog().size()+" testi in archivio");
	    			catalogTable.setModel(currentModel);
	    			catalogTable.setEnabled(true);
	    			CatalogViewLite.this.setTitle("Gestione Catalogo");
	            }
	        });
	    }
	}

	public void addUser(Utente admin) {
		this.admin = admin;
	}
	
	public class CatalogRowSelectionListener implements MouseListener {
		@Override
		public void mouseClicked(MouseEvent e) {
			getDataFromDatble();
		}

		@Override
		public void mousePressed(MouseEvent e) {
			// TODO Auto-generated method stub
			getDataFromDatble();
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
	
	private void getDataFromDatble() {
        String bookId = catalogTable.getValueAt(catalogTable.getSelectedRow(), 0).toString();
        String title = catalogTable.getValueAt(catalogTable.getSelectedRow(), 1).toString();
        String author = catalogTable.getValueAt(catalogTable.getSelectedRow(), 2).toString();
        String editor = catalogTable.getValueAt(catalogTable.getSelectedRow(), 3).toString();
        String year = catalogTable.getValueAt(catalogTable.getSelectedRow(), 4).toString();
        String desc = catalogTable.getValueAt(catalogTable.getSelectedRow(), 5).toString();
        String ISBN = catalogTable.getValueAt(catalogTable.getSelectedRow(), 6).toString();
        
        selectedBook = new BookDAO();
        selectedBook.setId(Integer.valueOf(bookId));
        selectedBook.setTitle(title);
        selectedBook.setAuthor(author);
        selectedBook.setEditor(editor);
        selectedBook.setYear(year);
        selectedBook.setDescription(desc);
        selectedBook.setIsbn(ISBN);
        
        bookDetailView = new BookDetailView(controller, selectedBook);
        bookDetailView.setVisible(true);
	}
}


