package controllers;

import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

import views.CatalogView;

public class CatalogController {

	private CatalogView view;
	private DefaultTableModel model;
	private JTable catalogTable;

	public CatalogController(CatalogView view) {
		this.view = view;
		view.setVisible(true);
		InitialiazeTable();
	}

	public void InitialiazeTable() {
		Object[] columns = { "Author", "Year" };
		model = new DefaultTableModel();
		model.setColumnIdentifiers(columns);
		model.addRow(new Object[] { "John Wick", "2022" });
		model.addRow(new Object[] { "John Wick1", "2023" });

		catalogTable = view.getCatalogTable();
		catalogTable.setModel(model);
		view.setCatalogTable(catalogTable);
	}
}
