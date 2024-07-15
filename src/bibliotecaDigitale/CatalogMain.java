package bibliotecaDigitale;

import java.io.FileReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import controllers.CatalogController;
import views.CatalogView;

public class CatalogMain {

	public CatalogMain() {
		ShowView();
	}

	public void ShowView() {
		CatalogView catalogView = new CatalogView();
		CatalogController catalogController = new CatalogController(catalogView);
	}

	public void readCsv() {
//		List<List<String>> records = new ArrayList<List<String>>();
//		try (CSVReader csvReader = new CSVReader(new FileReader("book.csv"));) {
//			String[] values = null;
//			while ((values = csvReader.readNext()) != null) {
//				records.add(Arrays.asList(values));
//			}
//		}
	}
}
