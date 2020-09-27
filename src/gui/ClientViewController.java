package gui;

import java.net.URL;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.List;
import java.util.ResourceBundle;

import gui.util.Utils;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.util.Callback;
import model.entities.Client;
import model.entities.Instructor;
import model.services.ClientService;
import model.services.InstructorService;

public class ClientViewController implements Initializable{
	private Client entity;
	@SuppressWarnings("unused")
	private ClientService service;
	private InstructorService instructorService;

	@FXML
	private TextField txtId;

	@FXML
	private TextField txtNome;

	@FXML
	private TextField txtCpf;

	@FXML
	private DatePicker dateDataNascimento;

	@FXML
	private ComboBox<Instructor> comboBoxInstructor;

	@FXML
	private TextField txtEndereco;

	@FXML
	private TextField txtBairro;

	@FXML
	private TextField txtEstado;

	@FXML
	private TextField txtEmail;

	@FXML
	private TextField txtSalario;

	@FXML
	private Button btSalvar;

	@FXML
	private Button btCancelar;

	private ObservableList<Instructor> obsList;
	

	public void setClient(Client entity) {
		this.entity = entity;
	}

	public void setClientService(ClientService service) {
		this.service = service;
	}

	public void setInstructorService(InstructorService instructorService) {
		this.instructorService = instructorService;
	}
	
	public void updateFormData() {
		if (entity == null)
			throw new IllegalStateException("Entidade vazia");
		txtId.setText(String.valueOf(entity.getId()));
		txtNome.setText(entity.getNome());
		txtCpf.setText(entity.getCpf());
		txtEndereco.setText(entity.getEndereco());
		txtBairro.setText(entity.getBairro());
		txtEstado.setText(entity.getEstado());
		txtEmail.setText(entity.getEmail());
		if (entity.getDataNascimento() != null) {
			dateDataNascimento.setValue(LocalDate.ofInstant(entity.getDataNascimento().toInstant(), ZoneId.systemDefault()));
		}
		if(entity.getInstructorName() == null)
			comboBoxInstructor.getSelectionModel().selectFirst();
		else
			comboBoxInstructor.setValue(entity.getInstructorName());
	}

	@FXML
	public void onBtCancelarAction(ActionEvent event) {
		Utils.currentStage(event).close();
	}

	@Override
	public void initialize(URL url, ResourceBundle rb) {
		Utils.formatDatePicker(dateDataNascimento, "dd/MM/yyyy");
		initializeComboBoxInstructor();
	}

	public void loadAssociatedObjects() {
		if(instructorService == null)
			throw new IllegalStateException("Instrutores vazios");
		List<Instructor> list = instructorService.findAll();
		obsList = FXCollections.observableArrayList(list);
		comboBoxInstructor.setItems(obsList);
	}
	
	private void initializeComboBoxInstructor() {
		Callback<ListView<Instructor>, ListCell<Instructor>> factory = lv -> new ListCell<Instructor>() {
			@Override
			protected void updateItem(Instructor item, boolean empty) {
				super.updateItem(item, empty);
				setText(empty ? "" : item.getNome());
			}
		};
		comboBoxInstructor.setCellFactory(factory);
		comboBoxInstructor.setButtonCell(factory.call(null));
	}
}
