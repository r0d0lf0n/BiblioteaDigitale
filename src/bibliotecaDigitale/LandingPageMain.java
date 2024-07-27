package bibliotecaDigitale;

import com.j256.ormlite.support.ConnectionSource;

import controllers.views.LandingPageController;
import database.DatabaseConfig;
import views.LandingPageView;

public class LandingPageMain {
	DatabaseConfig config;
	ConnectionSource connectionSource;
	
	public LandingPageMain() {
		ShowView();
	}

	public void ShowView() {
		LandingPageView landingPageView = new LandingPageView();
		LandingPageController landingPageController = new LandingPageController(landingPageView);
		
		configDatabase();
	}
	
	public void configDatabase() {
		config = new DatabaseConfig();
		connectionSource = config.getdbConnection();
		System.out.println(connectionSource);
	}
}
