package model.services;
import java.util.List;
import model.dao.ClientDao;
import model.dao.DaoFactory;
import model.entities.Client;
import model.entities.Instructor;

public class ClientService {
	/*
	 * Serviços do Dao para interação com classe controladora
	 */
	private ClientDao dao = DaoFactory.createClientDao();
	
	public List<Client> findAll(){
		return dao.findAll();
	}
	
	//metodo para verificar se é para salvar ou atualizar
	public void saveOrUpdate(Client obj) {
		if(obj.getId() == null) { // caso o id esteja vazio será entendido q é para inserção 
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
