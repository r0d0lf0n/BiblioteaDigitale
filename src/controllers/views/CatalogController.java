package controllers.views;

import java.util.ArrayList;
import java.util.List;

import controllers.bl.GestoreCatalogo;
import controllers.bl.GestoreRicerche;
import models.bl.CatalogModel;
import models.db.BookDAO;
import utils.Observable;
import utils.Observer;

public class CatalogController implements Observable{
	private CatalogModel catalogModel;
	private List<Observer> observers = null;
    private Object obj;
	
	
	public CatalogController() {
		this.catalogModel = new CatalogModel();
		GestoreRicerche.getInstance().setCatalog(this.catalogModel);
		observers = new ArrayList<Observer>();
	}

	public List<BookDAO> getBookCatalog(){
		return catalogModel.getAllBooks();
	}


    private void setChanged(String type, Object obj) {
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

	public void loadCSV(String selectedFile) {
		new LoaderThread(selectedFile).start();

	}
	
	private class LoaderThread extends Thread{
		private String selectedFile;
		
		public LoaderThread(String selectedFile) {
			this.selectedFile = selectedFile;
		}
		
		@Override
		public void run() {
			boolean isCharged = GestoreCatalogo.getInstance().populateCatalog(selectedFile);
			if(isCharged)
				setChanged("LOAD_BOOKS", null);
		}
	}
	
	public void closeBookDetailPanel(boolean editing) {
		setChanged("CLOSE_BOOK_DETAIL", null);	
		if(editing)
			setChanged("REFRESH_BOOK_DETAIL", null);	
	}

	public void deleteBook(BookDAO book) {
		GestoreCatalogo.getInstance().removeBook(book);
		setChanged("BOOK_DELETED", null);
		setChanged("REFRESH_BOOK_DETAIL", null);	
	}

	public void addNewBook(BookDAO newBook) {
		GestoreCatalogo.getInstance().addNewBook(newBook);
		setChanged("BOOK_ADDED", null);
		setChanged("REFRESH_BOOK_DETAIL", null);	
	}

	public void saveBook(BookDAO newBook) {
		catalogModel.saveBook(newBook);
		setChanged("REFRESH_BOOK_DETAIL", null);	
	}
}
