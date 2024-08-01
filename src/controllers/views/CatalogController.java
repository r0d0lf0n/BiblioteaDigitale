package controllers.views;

import java.util.List;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import database.Book;
import models.bl.BookModel;
import views.CatalogView;

public class CatalogController {

	private List<Book> book_catalog;
	private CatalogView catalogView;
	private DefaultTableModel model;
	private JTable catalogTable;
	BookModel bookModel;

	public CatalogController(CatalogView view, BookModel bookModel) {
		catalogView = view;
		this.bookModel = bookModel;
		view.setVisible(true);
		InitialiazeTable();
	}

	public void InitialiazeTable() {
		book_catalog = bookModel.getAllBooks();
		Object[] columns = { "id", "Author", "Year" };
		model = new DefaultTableModel();
		model.setColumnIdentifiers(columns);
		
		for(int i = 1; i < book_catalog.size(); i++) {
//			System.out.println(book_catalog.get(i)[0]);
				model.addRow(new Object[] {i, book_catalog.get(i).getAuthor(), book_catalog.get(i).getYear()});
		}

		catalogTable = catalogView.getCatalogTable();
		catalogTable.setModel(model);
		catalogView.setCatalogTable(catalogTable);
	}
}
