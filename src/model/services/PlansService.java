package model.services;
import java.util.List;

import model.dao.DaoFactory;
import model.dao.PlansDao;
import model.entities.Plans;

public class PlansService {
	
	private PlansDao dao = DaoFactory.createPlansDao();
	
	public List<Plans> findAll(){
		return dao.findAll();
	}
	
	public void saveOrUpdate(Plans obj) {
		if(obj.getId() == null) {
			dao.insert(obj);
		}
		else {
			dao.update(obj);
		}
	}
	
	public void remove(Plans obj) {
		dao.deleteById(obj.getId());
	}
}
