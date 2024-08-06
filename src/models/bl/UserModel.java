package models.bl;

import java.util.List;

import com.j256.ormlite.dao.Dao;

import controllers.bl.GestoreCatalogo;
import controllers.bl.GestoreUtenti;
import models.db.BookDAO;
import models.db.UserDAO;

public class UserModel {
	Dao<UserDAO, String> userDao;

	public UserModel() {
		//empty
	}
	
	public List<UserDAO> getAllUsers() {
		try {
			return GestoreUtenti.getInstance().getUserDao().queryForAll();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
	
	public List<UserDAO> getUsersByRegex(String criteria) {
		List<UserDAO> list = null;
		try {
			userDao = GestoreUtenti.getInstance().getUserDao();
			list = userDao.queryBuilder().where().eq("name", criteria).query();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return list;
	}
}