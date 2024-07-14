package controllers;

import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

import views.ShowCatalogView;

public class ShowCatalogController {
	
	private ShowCatalogView view;
	private DefaultTableModel model;
	private JTable catalogTable;
	
	
	public ShowCatalogController(ShowCatalogView view) {
		this.view = view;
		
		
//		Object[] columns = {"Author", "Year"};
//		model = new DefaultTableModel();
//		model.setColumnIdentifiers(columns);
//
//		catalogTable = view.getCatalogTable();
////		catalogTable.setModel(model);
//		view.setCatalogTable(catalogTable);
	}
}
