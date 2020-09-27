package model.dao;

import java.util.List;

import model.entities.Plans;

public interface PlansDao {
	void insert(Plans obj);
	void update(Plans obj);
	void deleteById(Integer id);
	Plans findById(Integer id);
	List<Plans> findAll();
}
