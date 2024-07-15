package bibliotecaDigitale;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;
import java.net.URI;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import com.opencsv.CSVReader;
import com.opencsv.exceptions.CsvValidationException;

import controllers.CatalogController;
import views.CatalogView;

public class CatalogMain {

	public CatalogMain() {
		ShowView();
	}

	public void ShowView() {
		List<String[]> book_catalog = new ArrayList<String[]>();
		try {
			book_catalog = readBookCatalog();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		CatalogView catalogView = new CatalogView();
		CatalogController catalogController = new CatalogController(catalogView, book_catalog);
	}
	
	public List<String[]> readBookCatalog() throws Exception {
		String filepath = "/assets/book_catalog.csv";
		URI resourcePath = this.getClass().getResource(filepath).toURI();
	    Path path = Paths.get(resourcePath);
	    return readAllLines(path);
	}
		
	public List<String[]> readAllLines(Path filePath) throws Exception {
//		System.out.println(filePath);
	    try (Reader reader = Files.newBufferedReader(filePath)) {
	        try (CSVReader csvReader = new CSVReader(reader)) {
	            return csvReader.readAll();
	        }
	    }
	}
}
