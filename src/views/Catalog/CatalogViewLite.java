package views.Catalog;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.SpringLayout;
import javax.swing.SwingUtilities;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;

import controllers.views.CatalogController;
import models.db.BookDAO;
import models.users.Utente;
import utils.Observer;

public class CatalogViewLite extends JFrame implements Observer{

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JTable catalogTable;
	private CatalogController controller = null;
	//private List<BookDAO> book_catalog = null;
	private Object[] columns = {"Book ID", "Titolo", "Autore", "Casa Editrice", "Anno", "Descrizione", "ISBN" };
	private BookDAO selectedBook;
	//private BookDetailView bookDetailView = null;
	//private JButton btnRefresh;
	private DefaultTableModel model;
	private CatalogRowSelectionListener catalogRowSelectionListener = new CatalogRowSelectionListener();
	private Utente user;
	private List<BookDAO> books_catalog = null;
	
	/**
	 * Create the frame.
	 * @param user 
	 */
	public CatalogViewLite(Utente user, CatalogController catalogController, List<BookDAO> books) {
		
		controller = catalogController;
		this.user = user;
		this.books_catalog = books;
		this.setTitle("Risultati Ricerca - "+user.getNome()+" "+user.getCognome());
		setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
		setBounds(100, 100, 800, 600);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setLocationRelativeTo(null);
		setContentPane(contentPane);
		SpringLayout sl_contentPane = new SpringLayout();
		contentPane.setLayout(sl_contentPane);

		JButton btnClose = new JButton("Close");
		btnClose.addActionListener(new ActionListener() {
		    public void actionPerformed(ActionEvent e) {
		        dispose();
		    }
		});
		sl_contentPane.putConstraint(SpringLayout.WEST, btnClose, 0, SpringLayout.WEST, contentPane);
		sl_contentPane.putConstraint(SpringLayout.SOUTH, btnClose, -10, SpringLayout.SOUTH, contentPane);
		contentPane.add(btnClose);

		JScrollPane scrollPane = new JScrollPane();
		sl_contentPane.putConstraint(SpringLayout.NORTH, scrollPane, 20, SpringLayout.NORTH, contentPane);  // Posiziona più in alto
		sl_contentPane.putConstraint(SpringLayout.WEST, scrollPane, 20, SpringLayout.WEST, contentPane);   // Posiziona più a sinistra
		sl_contentPane.putConstraint(SpringLayout.SOUTH, scrollPane, -100, SpringLayout.SOUTH, contentPane); // Riduce lo spazio in basso
		sl_contentPane.putConstraint(SpringLayout.EAST, scrollPane, -20, SpringLayout.EAST, contentPane);   // Riduce lo spazio a destra
		contentPane.add(scrollPane);

		catalogTable = new JTable();
		scrollPane.setViewportView(catalogTable);

		// Rimuovi o aggiusta questi vincoli se non sono necessari
		// sl_contentPane.putConstraint(SpringLayout.WEST, catalogTable, 350, SpringLayout.WEST, contentPane); // Rimuovi o modifica
		// sl_contentPane.putConstraint(SpringLayout.SOUTH, catalogTable, -211, SpringLayout.NORTH, btnClose); // Rimuovi o modifica
		// sl_contentPane.putConstraint(SpringLayout.EAST, catalogTable, -320, SpringLayout.EAST, contentPane); // Rimuovi o modifica

		model = new DefaultTableModel();
		model.setColumnIdentifiers(columns);
		catalogTable.setModel(model);
		catalogTable.addMouseListener(catalogRowSelectionListener);
	
		
		this.addWindowListener(new WindowAdapter() {
	         @Override
	         public void windowClosing(WindowEvent e) {
	        	 dispose();
	         }
	     });
		
		new LoadBooksThread().start();
	}


	
	@Override
	public void update(String type, Object arg) {

	}
	
	private class LoadBooksThread extends Thread {
	    public void run() {
	        DefaultTableModel currentModel = (DefaultTableModel) catalogTable.getModel();

	        SwingUtilities.invokeLater(new Runnable() {
	            public void run() {
	                for (BookDAO book : CatalogViewLite.this.books_catalog) {
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
	    			catalogTable.setModel(currentModel);
	    			catalogTable.setEnabled(true);
	            }
	        });
	    }
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
        
        BookDetailView detailView = new BookDetailView(null, selectedBook);
        detailView.setVisible(true);
        
	}
}


