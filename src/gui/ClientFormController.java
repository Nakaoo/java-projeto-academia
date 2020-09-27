package gui;

import java.net.URL;
import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;
import java.util.List;
import java.util.ResourceBundle;

import db.DBException;
import gui.util.Alerts;
import gui.util.Constraints;
import gui.util.Utils;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert.AlertType;
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

public class ClientFormController implements Initializable{
	private Client entity;
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

	@FXML
	public void onBtSalvarAction(ActionEvent event) {
		if (entity == null) {
			throw new IllegalStateException("Entidade vazia");
		}
		if (service == null) {
			throw new IllegalStateException("Serviço vazia");
		}

		try {
			entity = getFormData();
			service.saveOrUpdate(entity);
			Utils.currentStage(event).close();
		} catch (DBException e) {
			Alerts.showAlert("Error em salvar", null, e.getMessage(), AlertType.ERROR);
		}
	}

	private Client getFormData() {
		Client obj = new Client();
		obj.setId(Utils.tryParseToInt(txtId.getText()));
		obj.setNome(txtNome.getText());	
		obj.setCpf(txtCpf.getText());
		obj.setEmail(txtEmail.getText());
		obj.setEndereco(txtEndereco.getText());
		obj.setBairro(txtBairro.getText());
		obj.setEstado(txtEstado.getText());
		if (dateDataNascimento.getValue() == null) {
			Alerts.showAlert("Erro na data de nascimento", null, "", AlertType.ERROR);
		}
		else {
			Instant instant = Instant.from(dateDataNascimento.getValue().atStartOfDay(ZoneId.systemDefault()));
			obj.setDataNascimento(Date.from(instant));
		}
		obj.setInstructorName(comboBoxInstructor.getValue());
		return obj;
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
		Constraints.setTextFieldInteger(txtId);
		Constraints.setTextFieldMaxLength(txtNome, 30);
		Utils.formatDatePicker(dateDataNascimento, "dd/MM/yyyy");
		initializeComboBoxInstructor();
	}

	public void loadAssociatedObjects() {
		if(instructorService == null)
			throw new IllegalStateException("Planos vazio");
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
