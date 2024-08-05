package controllers.views;

import java.util.ArrayList;
import java.util.List;

import javax.swing.JLabel;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

import models.bl.CatalogModel;
import models.bl.LoanModel;
import models.db.BookDAO;
import utils.Observable;
import utils.Observer;
import views.Catalog.CatalogView;

public class CatalogController implements Observable{
	private CatalogModel catalogModel;
	private List<Observer> observers = null;
    private Object obj;

		
	
	public CatalogController() {
		this.catalogModel = new CatalogModel();
		observers = new ArrayList<Observer>();
	}

	public List<BookDAO> getBookCatalog(){
		return catalogModel.getAllBooks();
	}



    public void setChanged(String type, Object obj) {
        this.obj = obj;
        notifyObservers(type, obj); // Notifica gli osservatori con il nuovo stato
    }

    @Override
    public void addObserver(Observer observer) {
        observers.add(observer);
    }

    @Override
    public void removeObserver(Observer observer) {
        observers.remove(observer);
    }

    @Override
    public void notifyObservers(String type, Object arg) {
        for (Observer observer : observers) {
            observer.update(type, arg); // Aggiorna ciascun osservatore con il nuovo stato
        }
    }
}
