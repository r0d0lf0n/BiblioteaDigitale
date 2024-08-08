package views.Catalog;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.File;
import java.io.IOException;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.SpringLayout;
import javax.swing.SwingUtilities;
import javax.swing.border.EmptyBorder;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.table.DefaultTableModel;

import controllers.views.CatalogController;
import controllers.views.LandingPageController;
import models.db.BookDAO;
import utils.Observer;

public class CatalogView extends JFrame implements Observer{

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JTable catalogTable;
	private JButton btnLoadData;
	private CatalogController controller = null;
	//private List<BookDAO> book_catalog = null;
	private Object[] columns = {"Book Num.", "Title", "Author", "Editor", "Year", "Description", "ISBN" };
	private BookDAO selectedBook;

	
	/**
	 * Create the frame.
	 */
	public CatalogView(LandingPageController landingPageController) {
		controller = new CatalogController();
		this.setTitle("Gestione Catalogo");
		setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
		setBounds(100, 100, 1000, 650);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setLocationRelativeTo(null);
		setContentPane(contentPane);
		SpringLayout sl_contentPane = new SpringLayout();
		contentPane.setLayout(sl_contentPane);

		JButton btnClose = new JButton("Close");
		btnClose.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				landingPageController.openLandingPanel();
			}
		});
		sl_contentPane.putConstraint(SpringLayout.WEST, btnClose, 0, SpringLayout.WEST, contentPane);
		sl_contentPane.putConstraint(SpringLayout.SOUTH, btnClose, -10, SpringLayout.SOUTH, contentPane);
		contentPane.add(btnClose);

		btnLoadData = new JButton("Load data");
		sl_contentPane.putConstraint(SpringLayout.NORTH, btnLoadData, 20, SpringLayout.NORTH, contentPane);
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
                        "File selection cancelled.");
                }
			}
		});
		sl_contentPane.putConstraint(SpringLayout.WEST, btnLoadData, 0, SpringLayout.WEST, btnClose);
		contentPane.add(btnLoadData);

//		JButton btnRefresh = new JButton("Refresh");
//		btnRefresh.addActionListener(new ActionListener() {
//			public void actionPerformed(ActionEvent e) {
//				//TODO
//			}
//		});
//		sl_contentPane.putConstraint(SpringLayout.NORTH, btnRefresh, 17, SpringLayout.SOUTH, btnLoadData);
//		sl_contentPane.putConstraint(SpringLayout.WEST, btnRefresh, 0, SpringLayout.WEST, contentPane);
//		sl_contentPane.putConstraint(SpringLayout.EAST, btnRefresh, 0, SpringLayout.EAST, btnLoadData);
//		contentPane.add(btnRefresh);

		JScrollPane scrollPane = new JScrollPane();
		sl_contentPane.putConstraint(SpringLayout.NORTH, scrollPane, 36, SpringLayout.NORTH, contentPane);
		sl_contentPane.putConstraint(SpringLayout.WEST, scrollPane, 85, SpringLayout.EAST, btnLoadData);
		sl_contentPane.putConstraint(SpringLayout.SOUTH, scrollPane, -20, SpringLayout.SOUTH, contentPane);
		sl_contentPane.putConstraint(SpringLayout.EAST, scrollPane, -10, SpringLayout.EAST, contentPane);
		contentPane.add(scrollPane);

		catalogTable = new JTable();
		scrollPane.setViewportView(catalogTable);
		sl_contentPane.putConstraint(SpringLayout.WEST, catalogTable, 0, SpringLayout.WEST, contentPane);
		sl_contentPane.putConstraint(SpringLayout.SOUTH, catalogTable, -211, SpringLayout.NORTH, btnClose);
		sl_contentPane.putConstraint(SpringLayout.EAST, catalogTable, -420, SpringLayout.EAST, contentPane);
		//sl_contentPane.putConstraint(SpringLayout.NORTH, catalogTable, 85, SpringLayout.SOUTH, btnRefresh);
		catalogTable.getSelectionModel().addListSelectionListener(new ListSelectionListener(){
	        public void valueChanged(ListSelectionEvent event) {
	        	if (!event.getValueIsAdjusting()) {
	        		String bookId = catalogTable.getValueAt(catalogTable.getSelectedRow(), 0).toString();
	        		System.out.println(bookId);
	        		
	        	    selectedBook = new BookDAO();
	        	    selectedBook.setId(Integer.valueOf(bookId));
//	        	    selectedBook.setTitle(title);
//	        	    selectedBook.setAuthor(author);
//	        	    selectedBook.setYear(year);
	        	}
	        }
	    });
		
		this.addWindowListener(new WindowAdapter() {
	         @Override
	         public void windowClosing(WindowEvent e) {
	        	 landingPageController.openLandingPanel();
	         }
	     });
		
//		if(getBookCatalog().size() == 0)
		initializeTable();
		
		controller.addObserver(this);
		landingPageController.addObserver(this);
	}

	protected void loadCSV(File selectedFile) {
		controller.loadCSV(selectedFile.getPath());
	}


	private void initializeTable() {
		DefaultTableModel model = new DefaultTableModel();
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
	                        "Caricamento catalogo completato, presenti: "+ controller.getBookCatalog().size()+" testi in archivio");
	    			catalogTable.setModel(currentModel);
	    			catalogTable.setEnabled(true);
	    			btnLoadData.setEnabled(true); 
	    			CatalogView.this.setTitle("Gestione Catalogo");
	            }
	        });
	    }
	}
}


