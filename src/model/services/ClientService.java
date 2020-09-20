package model.services;

import java.util.ArrayList;
import java.util.List;

import model.dao.ClientDao;
import model.dao.DaoFactory;
import model.entities.Client;

public class ClientService {
	
	private ClientDao dao = DaoFactory.createClientDao();
	
	public List<Client> findAll(){
		return dao.findAll();
	}
}
