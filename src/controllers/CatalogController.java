package controllers;

import java.util.List;

import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

import views.CatalogView;

public class CatalogController {

	private List<String[]> book_catalog;
	private CatalogView view;
	private DefaultTableModel model;
	private JTable catalogTable;

	public CatalogController(CatalogView view, List<String[]> catalog) {
		this.view = view;
		this.book_catalog = catalog;
		view.setVisible(true);
		InitialiazeTable();
	}

	public void InitialiazeTable() {
		Object[] columns = { "Author", "Year" };
		model = new DefaultTableModel();
		model.setColumnIdentifiers(columns);
//		model.addRow(new Object[] { "John Wick", "2022" });
//		model.addRow(new Object[] { "John Wick1", "2023" });
		for (String[] book : book_catalog) { 
//			System.out.println(book[1]);
			model.addRow(new Object[] {book[0], book[1]});
		}

		catalogTable = view.getCatalogTable();
		catalogTable.setModel(model);
		view.setCatalogTable(catalogTable);
	}
}
