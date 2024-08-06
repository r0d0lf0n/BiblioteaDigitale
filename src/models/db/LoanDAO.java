package models.db;

import java.awt.print.Book;
import java.util.Date;
import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

@DatabaseTable(tableName = "loan")
public class LoanDAO {
	
	@DatabaseField(canBeNull = false, foreign = true, columnName = "user_id")
    private UserDAO user;
    
    @DatabaseField(canBeNull = false, foreign = true, columnName = "book_id")
    private BookDAO book;

    @DatabaseField()
    private Date start_date;
    
    @DatabaseField()
    private Date end_date;
 
    public LoanDAO() {
    	// ORMLite needs a no-arg constructor
    }

	public UserDAO getUser_id() {
		return user;
	}

	public void setUser_id(UserDAO id) {
		this.user = id;
	}

	public BookDAO getBook_id() {
		return book;
	}

	public void setBook_id(BookDAO id) {
		this.book = id;
	}

	public Date getStart_date() {
		return start_date;
	}

	public void setStart_date(Date start_date) {
		this.start_date = start_date;
	}

	public Date getEnd_date() {
		return end_date;
	}

	public void setEnd_date(Date end_date) {
		this.end_date = end_date;
	}
}