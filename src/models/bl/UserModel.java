package models.bl;

import java.util.List;

import com.j256.ormlite.dao.Dao;

import controllers.bl.GestoreUtenti;
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
			e.printStackTrace();
		}
		return null;
	}
	
	public List<UserDAO> getUsersByRegex(String criteria) {
		List<UserDAO> list = null;
		try {
			list = GestoreUtenti.getInstance().getUserDao().queryBuilder()
//					  .selectColumns("name")
					  .where()
					  .like("name", "%"+criteria+"%")
					  .query();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return list;
	}
	
	public UserDAO getUserById(int id) {
		UserDAO user = null;
		try {
			List<UserDAO> list = GestoreUtenti.getInstance().getUserDao().queryBuilder()
					  .where()
					  .eq("id", id)
					  .query();
			if (list.size()>0)
				return list.get(0);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return user;
	}
	
	public UserDAO getUserByTesseraId(int id) {
		UserDAO user = null;
		try {
			List<UserDAO> list = GestoreUtenti.getInstance().getUserDao().queryBuilder()
					  .where()
					  .eq("numTessera", id)
					  .query();
			if (list.size()>0)
				return list.get(0);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return user;
	}
}