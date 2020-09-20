package gui;

import java.net.URL;
import java.util.Date;
import java.util.List;
import java.util.ResourceBundle;

import application.Main;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import model.entities.Client;
import model.services.ClientService;


public class ClientListController implements Initializable{

	private ClientService service;
	
	@FXML
	private TableView<Client> tableViewClient;

	@FXML
	private TableColumn<Client, Integer> tableColumnId;
	
	@FXML
	private TableColumn<Client, String> tableColumnNome;
	
	@FXML
	private TableColumn<Client, String> tableColumnEndereco;
	
	@FXML
	private TableColumn<Client, String> tableColumnBairro;
	
	@FXML
	private TableColumn<Client, String> tableColumnEstado;
	
	@FXML
	private TableColumn<Client, String> tableColumnTelefone;
	
	@FXML
	private TableColumn<Client, String> tableColumnCpf;
	
	@FXML 
	private TableColumn<Client, Date> tableColumnDataNascimento;
	
	@FXML
	private TableColumn<Client, Boolean> tableColumnFaturaPaga;
	
	@FXML
	private TableColumn<Client, Boolean> tableColumnDataVencimentoFat;
	
	@FXML
	private Button btRegistration;
	
	private ObservableList<Client> obsList;
	
	@FXML
	public void onBtRegistrationAction() {
		System.out.println("onRegistrationAction");
	}
	
	public void setClientService(ClientService service) {
		this.service = service;
	}
	
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		initializeNodes();
	}

	private void initializeNodes() {
		tableColumnId.setCellValueFactory(new PropertyValueFactory<>("id"));
		tableColumnNome.setCellValueFactory(new PropertyValueFactory<>("nome"));
		tableColumnFaturaPaga.setCellValueFactory(new PropertyValueFactory<>("faturaPaga"));
		tableColumnDataVencimentoFat.setCellValueFactory(new PropertyValueFactory<>("dataVencimentoFat"));
		
		Stage stage = (Stage) Main.getMainScene().getWindow();
		tableViewClient.prefHeightProperty().bind(stage.heightProperty());
	}
	
	public void updateTableView() {
		if(service == null) {
			throw new IllegalStateException("Serviço vazio");
		}
		List<Client> list = service.findAll();
		obsList = FXCollections.observableArrayList(list);
		tableViewClient.setItems(obsList);
	}
	
}
