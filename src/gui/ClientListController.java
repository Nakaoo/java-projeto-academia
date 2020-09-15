package gui;

import java.net.URL;
import java.util.Date;
import java.util.ResourceBundle;

import application.Main;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import model.entities.Client;


public class ClientListController implements Initializable{

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
	
	@FXML
	public void onBtRegistrationAction() {
		System.out.println("onRegistrationAction");
	}
	
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		initializeNodes();
	}

	private void initializeNodes() {
		tableColumnId.setCellValueFactory(new PropertyValueFactory<>("id"));
		tableColumnNome.setCellValueFactory(new PropertyValueFactory<>("nome"));
		tableColumnEndereco.setCellValueFactory(new PropertyValueFactory<>("endereco"));
		tableColumnBairro.setCellValueFactory(new PropertyValueFactory<>("bairro"));
		tableColumnEstado.setCellValueFactory(new PropertyValueFactory<>("estado"));
		tableColumnTelefone.setCellValueFactory(new PropertyValueFactory<>("telefone"));
		tableColumnCpf.setCellValueFactory(new PropertyValueFactory<>("cpf"));
		tableColumnDataNascimento.setCellValueFactory(new PropertyValueFactory<>("dataNascimento"));
		tableColumnFaturaPaga.setCellValueFactory(new PropertyValueFactory<>("faturaPaga"));
		tableColumnDataVencimentoFat.setCellValueFactory(new PropertyValueFactory<>("dataVencimentoFat"));
		
		Stage stage = (Stage) Main.getMainScene().getWindow();
		tableViewClient.prefHeightProperty().bind(stage.heightProperty());
	}
	
	
}
