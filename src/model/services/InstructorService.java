package model.services;
import java.util.List;

import model.dao.DaoFactory;
import model.dao.InstructorDao;
import model.entities.Instructor;

public class InstructorService {
	
	private InstructorDao dao = DaoFactory.createInstructorDao();
	
	public List<Instructor> findAll(){
		return dao.findAll();
	}
	
	public void saveOrUpdate(Instructor obj) {
		if(obj.getId() == null) {
			dao.insert(obj);
		}
		else {	
			dao.update(obj);
		}
	}
	
	public void remove(Instructor obj) {
		dao.deleteById(obj.getId());
	}
}
