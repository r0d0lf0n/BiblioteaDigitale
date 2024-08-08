package models.db;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

@DatabaseTable(tableName = "book")
public class BookDAO {
    
	@DatabaseField(generatedId = true)
    private int id;

    @DatabaseField()
    private String title;

    @DatabaseField()
    private String author;

    @DatabaseField()
    private String year;
    
    @DatabaseField()
    private String description;
    
    @DatabaseField()
    private String isbn;
    
    @DatabaseField()
    private String editor;
   
    public BookDAO() {
        // ORMLite needs a no-arg constructor
    }

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getAuthor() {
		return author;
	}

	public void setAuthor(String author) {
		this.author = author;
	}

	public String getYear() {
		return year;
	}

	public void setYear(String year) {
		this.year = year;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getIsbn() {
		return isbn;
	}

	public void setIsbn(String isbn) {
		this.isbn = isbn;
	}
	
	public String getEditor() {
		return editor;
	}

	public void setEditor(String editor) {
		this.editor = editor;
	}
	
	public String toString() {
		return this.id+" "+this.title+" "+this.author+" "+this.editor+" "+this.year+" "+this.description+" "+this.isbn;
	}
	
	public boolean equals(BookDAO book) {
		if(book.getIsbn().equals(this.isbn) &&
				book.getId() == this.id &&
				book.getAuthor().equals(this.author) &&
				book.getDescription().equals(this.description) &&
				book.getTitle().equals(this.title) &&
				book.getYear().equals(this.year))
			return true;
		return false;
	}
}