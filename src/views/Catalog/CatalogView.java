package views.Catalog;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.File;
import java.util.List;

import javax.swing.JButton;
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
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.table.DefaultTableModel;

import controllers.views.CatalogController;
import controllers.views.LandingPageController;
import models.db.BookDAO;
import models.users.Utente;
import utils.Observer;

public class CatalogView extends JFrame implements Observer{

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JTable catalogTable;
	private JButton btnLoadData;
	private CatalogController controller = null;
	//private List<BookDAO> book_catalog = null;
	private final Object[] columns = {"Book ID", "Titolo", "Autore", "Casa Editrice", "Anno", "Descrizione", "ISBN" };
	private BookDAO selectedBook;
	private BookDetailView bookDetailView = null;
	//private JButton btnRefresh;
	private DefaultTableModel model;
	private Utente admin = null;
	private JTextField txtCodiceFiscale;
	private JTextField txtCognome;
	private JTextField txtNome;
	private CatalogRowSelectionListener catalogRowSelectionListener = new CatalogRowSelectionListener();
	
	/**
	 * Create the frame.
	 */
	public CatalogView(LandingPageController landingPageController) {
		
		controller = new CatalogController();
		this.setTitle("Gestione Catalogo");
		setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
		setBounds(100, 100, 1200, 650);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setLocationRelativeTo(null);
		setContentPane(contentPane);
		SpringLayout sl_contentPane = new SpringLayout();
		contentPane.setLayout(sl_contentPane);

		// Label "AMMINISTRATORE"
		JLabel lblAmministratore = new JLabel("AMMINISTRATORE");
		sl_contentPane.putConstraint(SpringLayout.NORTH, lblAmministratore, 10, SpringLayout.NORTH, contentPane);
		sl_contentPane.putConstraint(SpringLayout.WEST, lblAmministratore, 10, SpringLayout.WEST, contentPane);
		contentPane.add(lblAmministratore);

		// Campo "Nome"
		JLabel lblNome = new JLabel("Nome:");
		sl_contentPane.putConstraint(SpringLayout.NORTH, lblNome, 15, SpringLayout.SOUTH, lblAmministratore);
		sl_contentPane.putConstraint(SpringLayout.WEST, lblNome, 10, SpringLayout.WEST, contentPane);
		contentPane.add(lblNome);

		txtNome = new JTextField();
		sl_contentPane.putConstraint(SpringLayout.NORTH, txtNome, 10, SpringLayout.SOUTH, lblAmministratore);
		sl_contentPane.putConstraint(SpringLayout.WEST, txtNome, 60, SpringLayout.WEST, lblNome);
		sl_contentPane.putConstraint(SpringLayout.EAST, txtNome, 250, SpringLayout.WEST, lblNome);
		contentPane.add(txtNome);
		txtNome.setColumns(25);
		txtNome.setEditable(false);

		// Campo "Cognome"
		JLabel lblCognome = new JLabel("Cognome:");
		sl_contentPane.putConstraint(SpringLayout.NORTH, lblCognome, 15, SpringLayout.SOUTH, lblNome);
		sl_contentPane.putConstraint(SpringLayout.WEST, lblCognome, 10, SpringLayout.WEST, contentPane);
		contentPane.add(lblCognome);

		txtCognome = new JTextField();
		sl_contentPane.putConstraint(SpringLayout.NORTH, txtCognome, 10, SpringLayout.SOUTH, txtNome);
		sl_contentPane.putConstraint(SpringLayout.WEST, txtCognome, 60, SpringLayout.WEST, lblCognome);
		sl_contentPane.putConstraint(SpringLayout.EAST, txtCognome, 250, SpringLayout.WEST, lblCognome);
		contentPane.add(txtCognome);
		txtCognome.setColumns(25);
		txtCognome.setEditable(false);

		// Campo "Codice Fiscale"
		JLabel lblCodiceFiscale = new JLabel("Cod. Fisc:");
		sl_contentPane.putConstraint(SpringLayout.NORTH, lblCodiceFiscale, 15, SpringLayout.SOUTH, lblCognome);
		sl_contentPane.putConstraint(SpringLayout.WEST, lblCodiceFiscale, 10, SpringLayout.WEST, contentPane);
		contentPane.add(lblCodiceFiscale);

		txtCodiceFiscale = new JTextField();
		sl_contentPane.putConstraint(SpringLayout.NORTH, txtCodiceFiscale, 10, SpringLayout.SOUTH, txtCognome);
		sl_contentPane.putConstraint(SpringLayout.WEST, txtCodiceFiscale, 100, SpringLayout.WEST, lblCodiceFiscale);
		sl_contentPane.putConstraint(SpringLayout.EAST, txtCodiceFiscale, 250, SpringLayout.WEST, lblCodiceFiscale);
		contentPane.add(txtCodiceFiscale);
		txtCodiceFiscale.setColumns(25);
		txtCodiceFiscale.setEditable(false);

		// Riorganizzazione dei pulsanti e della tabella
		JButton btnClose = new JButton("Chiudi");
		btnClose.addActionListener(new ActionListener() {
		    public void actionPerformed(ActionEvent e) {
		    	catalogTable.removeMouseListener(catalogRowSelectionListener);
		        landingPageController.openLandingPanel();
		    }
		});
		sl_contentPane.putConstraint(SpringLayout.WEST, btnClose, 0, SpringLayout.WEST, contentPane);
		sl_contentPane.putConstraint(SpringLayout.SOUTH, btnClose, -10, SpringLayout.SOUTH, contentPane);
		contentPane.add(btnClose);

		btnLoadData = new JButton("Caricare data");
		sl_contentPane.putConstraint(SpringLayout.NORTH, btnLoadData, 20, SpringLayout.SOUTH, txtCodiceFiscale);
		sl_contentPane.putConstraint(SpringLayout.WEST, btnLoadData, 0, SpringLayout.WEST, btnClose);
		btnLoadData.addActionListener(new ActionListener() {
		    public void actionPerformed(ActionEvent e) {
		        JFileChooser fileChooser = new JFileChooser();
		        
		        FileNameExtensionFilter filter = new FileNameExtensionFilter("CSV Files", "csv");
		        fileChooser.setFileFilter(filter);

		        int result = fileChooser.showOpenDialog(CatalogView.this);

		        if (result == JFileChooser.APPROVE_OPTION) {
		            File selectedFile = fileChooser.getSelectedFile();
		            int confirm = JOptionPane.showConfirmDialog(CatalogView.this, 
		                "File selezionato: " + selectedFile.getAbsolutePath() + "\nVuoi processarlo?",
		                "Conferma processamento File", JOptionPane.YES_NO_OPTION);
		            
		            if (confirm == JOptionPane.YES_OPTION) {
		                JOptionPane.showMessageDialog(CatalogView.this, 
		                        "Lettura e caricamento del dataset su DB, l'operazione potrebbe richiedere qualche minuto");
		                catalogTable.setEnabled(false);
		                btnLoadData.setEnabled(false);
		                CatalogView.this.setTitle("Caricamento in corso...");
		                loadCSV(selectedFile);
		            }
		        } else if (result == JFileChooser.CANCEL_OPTION) {
		            JOptionPane.showMessageDialog(CatalogView.this, 
		                "Selezione del file annullata.");
		        }
		    }
		});
		contentPane.add(btnLoadData);

		// Pulsante "Insert new book"
		JButton btnInsertNewBook = new JButton("Aggiungi Libro");
		sl_contentPane.putConstraint(SpringLayout.NORTH, btnInsertNewBook, 17, SpringLayout.SOUTH, btnLoadData);
		sl_contentPane.putConstraint(SpringLayout.WEST, btnInsertNewBook, 0, SpringLayout.WEST, contentPane);
		btnInsertNewBook.addActionListener(new ActionListener() {
		    public void actionPerformed(ActionEvent e) {	    	
			//	if (!catalogTable.getSelectionModel().isSelectionEmpty()) {		
			        BookDetailView insertBook = new BookDetailView(CatalogView.this.controller, null);
			        insertBook.setVisible(true);
			//	} else {
	       	//		JOptionPane.showMessageDialog(CatalogView.this, "Select book to edit!");
			//	}
		    }
		});
		contentPane.add(btnInsertNewBook);

		// ScrollPane e JTable
		JScrollPane scrollPane = new JScrollPane();
		sl_contentPane.putConstraint(SpringLayout.NORTH, scrollPane, 36, SpringLayout.NORTH, contentPane);
		sl_contentPane.putConstraint(SpringLayout.WEST, scrollPane, 350, SpringLayout.WEST, contentPane); // Ridotta la larghezza
		sl_contentPane.putConstraint(SpringLayout.SOUTH, scrollPane, -20, SpringLayout.SOUTH, contentPane);
		sl_contentPane.putConstraint(SpringLayout.EAST, scrollPane, -10, SpringLayout.EAST, contentPane);
		contentPane.add(scrollPane);

		catalogTable = new JTable();
		scrollPane.setViewportView(catalogTable);
		sl_contentPane.putConstraint(SpringLayout.WEST, catalogTable, 350, SpringLayout.WEST, contentPane); // Allineata con lo scrollPane
		sl_contentPane.putConstraint(SpringLayout.SOUTH, catalogTable, -211, SpringLayout.NORTH, btnClose);
		sl_contentPane.putConstraint(SpringLayout.EAST, catalogTable, -320, SpringLayout.EAST, contentPane);

//		catalogTable.addMouseListener(catalogRowSelectionListener);
	
		
		this.addWindowListener(new WindowAdapter() {
	         @Override
	         public void windowClosing(WindowEvent e) {
	        	 landingPageController.openLandingPanel();
	         }
	     });
		
	    ComponentListener visibilityListener = new ComponentListener() {
	        public void componentShown(ComponentEvent evt) {
//	        	catalogTable.addMouseListener(catalogRowSelectionListener);
	        }

	        public void componentHidden(ComponentEvent evt) {}

	        public void componentMoved(ComponentEvent evt) {}

	        public void componentResized(ComponentEvent evt) {}
	      };
		
//		if(getBookCatalog().size() == 0)
		initializeTable();
		
		controller.addObserver(this);
		landingPageController.addObserver(this);
		CatalogView.this.addComponentListener(visibilityListener);
	}

	protected void loadCSV(File selectedFile) {
		controller.loadCSV(selectedFile.getPath());
	}


	private void initializeTable() {
	    model = new DefaultTableModel();
		model.setColumnIdentifiers(columns);
		List<BookDAO> book_catalog = controller.getBookCatalog();
		
		for(int i = 0; i < book_catalog.size(); i++) {
//			System.out.println(book_catalog.get(i).getAuthor());
			model.addRow(new Object[] {book_catalog.get(i).getId(), book_catalog.get(i).getTitle(), book_catalog.get(i).getAuthor(), book_catalog.get(i).getEditor(), book_catalog.get(i).getYear(), book_catalog.get(i).getDescription(), book_catalog.get(i).getIsbn()});
		}

		catalogTable.setModel(model);
	}
	
	
	@Override
	public void update(String type, Object arg) {
		if(type.equals("OPEN_CATALOG")) {
			catalogTable.addMouseListener(catalogRowSelectionListener);
			this.setVisible(true);
		}
		if(type.equals("CLOSE_CATALOG")){
			this.setVisible(false);
		}
		
		if(type.equals("LOAD_BOOKS")) {
			JOptionPane.showMessageDialog(CatalogView.this, 
                    "Caricamento catalogo in corso, l'operazione potrebbe richiedere qualche minuto");
			new LoadBooksThread().start();
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
	    			JOptionPane.showMessageDialog(CatalogView.this, 
	                        "Aggiornamento completato, presenti: "+ controller.getBookCatalog().size()+" testi in archivio");
	    			catalogTable.setModel(currentModel);
	    			catalogTable.setEnabled(true);
	    			btnLoadData.setEnabled(true); 
	    			CatalogView.this.setTitle("Gestione Catalogo");
	            }
	        });
	    }
	}

	public void addUser(Utente admin) {
		this.admin = admin;
		txtCodiceFiscale.setText(admin.getCodiceFiscale());
		txtNome.setText(admin.getNome());
		txtCognome.setText(admin.getCognome());
	}
	
	public class CatalogRowSelectionListener implements MouseListener {
		@Override
		public void mouseClicked(MouseEvent e) {
			if(catalogTable.getSelectedRow() != -1)
				getDataFromTable();
		}

		@Override
		public void mousePressed(MouseEvent e) {
			if(catalogTable.getSelectedRow() != -1)
				getDataFromTable();
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
	
	private void getDataFromTable() {
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


