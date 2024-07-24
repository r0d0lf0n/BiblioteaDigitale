package database;

import java.sql.Date;
import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

@DatabaseTable(tableName = "loan")
public class Loan {
	
	@DatabaseField(canBeNull = false, foreign = true, columnName = "user_id")
    private User user;
    
    @DatabaseField(canBeNull = false, foreign = true, columnName = "book_id")
    private Book book;

    @DatabaseField()
    private Date start_date;
    
    @DatabaseField()
    private Date end_date;
 
    public Loan() {
    	// ORMLite needs a no-arg constructor
    }

	public User getUser_id() {
		return user;
	}

	public void setUser_id(User user_id) {
		this.user = user_id;
	}

	public Book getBook_id() {
		return book;
	}

	public void setBook_id(Book book_id) {
		this.book = book_id;
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