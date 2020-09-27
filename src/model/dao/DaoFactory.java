package model.dao;

import db.DB;
import model.dao.impl.ClientDaoJdbc;
import model.dao.impl.InstructorDaoJdbc;
import model.dao.impl.PlansDaoJdbc;

public class DaoFactory {
	
	public static ClientDao createClientDao() {
		return new ClientDaoJdbc(DB.getConnection());
	}	
	
	public static PlansDao createPlansDao() {
		return new PlansDaoJdbc(DB.getConnection());
	}

	public static InstructorDao createInstructorDao() {
		return new InstructorDaoJdbc(DB.getConnection());
	}	
}
