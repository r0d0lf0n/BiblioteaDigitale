package models.db;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

@DatabaseTable(tableName = "user")
public class UserDAO {

    @DatabaseField(generatedId = true)
    private int id;

    @DatabaseField()
    private String name;
   
    @DatabaseField()
    private String surname;

    @DatabaseField()
    private String phone;
    
    @DatabaseField()
    private String address;
    
    @DatabaseField()
    private int role;
    
    @DatabaseField
    private String codiceFiscale;
    
    public UserDAO() {
        // ORMLite needs a no-arg constructor
    }

    // Getters and setters
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSurname() {
		return surname;
	}

	public void setSurname(String surname) {
		this.surname = surname;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public int getRole() {
		return role;
	}

	public void setRole(int role) {
		this.role = role;
	}

	public void setCodiceFiscale(String codiceFiscale) {
		this.codiceFiscale = codiceFiscale;
	}
	
	public String getCodiceFiscale() {
		return codiceFiscale;
	}
}