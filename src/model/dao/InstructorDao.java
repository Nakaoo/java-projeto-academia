package model.dao;

import java.util.List;

import model.entities.Instructor;
import model.entities.Plans;

public interface InstructorDao {
	void insert(Instructor obj);
	void update(Instructor obj);
	void deleteById(Integer id);
	Plans findById(Integer id);
	List<Instructor> findAll();
}
