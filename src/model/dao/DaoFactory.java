package model.dao;

import db.DB;
import model.dao.impl.ClientDaoJdbc;

public class DaoFactory {
	
	public static ClientDao createClientDao() {
		return new ClientDaoJdbc(DB.getConnection());
	}	
	
}
