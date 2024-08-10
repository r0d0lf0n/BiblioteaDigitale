package controllers.views;

import java.util.ArrayList;
import java.util.List;

import utils.Observable;
import utils.Observer;

public class LandingPageController implements Observable{

	private List<Observer> observers = null;
    private Object obj;

	public LandingPageController() {
		observers = new ArrayList<Observer>();
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

	public void openLoansPanel() {
		setChanged("CLOSE_LANDING", null);
		setChanged("CLOSE_SEARCH", null);
		setChanged("CLOSE_CATALOG", null);
		setChanged("OPEN_LOANS", null);
	}

	public void openSearchPanel() {
		setChanged("CLOSE_LANDING", null);
		setChanged("CLOSE_LOANS", null);
		setChanged("CLOSE_CATALOG", null);
		setChanged("OPEN_SEARCH", null);
	}

	public void openCatalogPanel() {
		setChanged("CLOSE_LANDING", null);
		setChanged("CLOSE_SEARCH", null);
		setChanged("CLOSE_LOANS", null);
		setChanged("OPEN_CATALOG", null);
		setChanged("OPEN_CATALOG_LITE", null);
	}
	
	public void openLandingPanel() {
		setChanged("CLOSE_SEARCH", null);
		setChanged("CLOSE_LOANS", null);
		setChanged("CLOSE_CATALOG", null);
		setChanged("OPEN_LANDING", null);

	}
	
//	public void ShowView() {
//		System.out.println("Loading view.......");
//		landingPageView.setVisible(true);
//		
//		JButton openLoans = landingPageView.getOpenLoansButton();
//		openLoans.addActionListener(new ActionListener() {
//			public void actionPerformed(ActionEvent e) {
//				System.out.println("Clicking openLoans");
//				closeOpenedPanels();		
//				LoansMain loan = new LoansMain();
//				instanciatedPanels.add(loan.getView());
//			}
//		});
//		
//		JButton openCatalog = landingPageView.getOpenCatalogButton();
//		openCatalog.addActionListener(new ActionListener() {
//			public void actionPerformed(ActionEvent e) {
//				System.out.println("Clicking openCatalog");
//				closeOpenedPanels();		
//				CatalogMain catalog = new CatalogMain();
//				instanciatedPanels.add(catalog.getView());
//			}
//		});
//		
//		JButton openAdvancedSearch = landingPageView.getAdvancedSearchButton();
//		openAdvancedSearch.addActionListener(new ActionListener() {
//			public void actionPerformed(ActionEvent e) {
//				System.out.println("Clicking openAdvancedSearch");
//				closeOpenedPanels();		
//				
//		
//				instanciatedPanels.add(orchestrator.getUserAdminView());
//				instanciatedPanels.add(orchestrator.getRegisteredUserView());
//				instanciatedPanels.add(orchestrator.getExternalUserView());
//				System.out.println(instanciatedPanels);
//			}
//		});
//	}
	
	
    

}
