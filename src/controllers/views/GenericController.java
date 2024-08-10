/**
 * 
 */
package controllers.views;

import java.util.List;

import models.db.BookDAO;
import models.users.Utente;

/**
 * 
 */
public interface GenericController {
	//MARKER IF
	
	public void returnSearchResults(List<BookDAO> books, Utente user);

}
