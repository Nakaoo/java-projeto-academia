package model.services;
import java.util.List;
import model.dao.ClientDao;
import model.dao.DaoFactory;
import model.entities.Client;
import model.entities.Instructor;

public class ClientService {
	/*
	 * Servi�os do Dao para intera��o com classe controladora
	 */
	private ClientDao dao = DaoFactory.createClientDao();
	
	public List<Client> findAll(){
		return dao.findAll();
	}
	
	//metodo para verificar se � para salvar ou atualizar
	public void saveOrUpdate(Client obj) {
		if(obj.getId() == null) { // caso o id esteja vazio ser� entendido q � para inser��o 
			dao.insert(obj); 
		}
		else {
			dao.update(obj);
		}
	}
	
	public void remove(Client obj) {
		dao.deleteById(obj.getId());
	}
}
