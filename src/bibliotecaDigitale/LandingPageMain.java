package bibliotecaDigitale;

import controllers.views.LandingPageController;
import views.LandingPageView;

public class LandingPageMain {
	
	public LandingPageMain() {
		ShowView();
	}

	public void ShowView() {
		LandingPageView landingPageView = new LandingPageView();
		LandingPageController landingPageController = new LandingPageController(landingPageView);
	}
}
