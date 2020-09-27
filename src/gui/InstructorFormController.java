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
import model.entities.Instructor;
import model.entities.Plans;
import model.services.InstructorService;
import model.services.PlansService;



public class InstructorFormController implements Initializable {

	private Instructor entity;
	private InstructorService service;
	private PlansService planService;

	@FXML
	private TextField txtId;

	@FXML
	private TextField txtNome;

	@FXML
	private TextField txtCpf;

	@FXML
	private DatePicker dateDataNascimento;

	@FXML
	private ComboBox<Plans> comboBoxPlans;

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

	private ObservableList<Plans> obsList;

	public void setInstructor(Instructor entity) {
		this.entity = entity;
	}

	public void setInstructorService(InstructorService service) {
		this.service = service;
	}

	public void setPlanService(PlansService planService) {
		this.planService = planService;
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

	private Instructor getFormData() {
		Instructor obj = new Instructor();
		obj.setId(Utils.tryParseToInt(txtId.getText()));
		obj.setNome(txtNome.getText());	
		obj.setEmail(txtEmail.getText());
		obj.setEndereco(txtEndereco.getText());
		obj.setBairro(txtBairro.getText());
		obj.setEstado(txtEstado.getText());
		obj.setSalario(Utils.tryParseToDouble(txtSalario.getText()));
		obj.setCpf(txtCpf.getText());
		if (dateDataNascimento.getValue() == null) {
			Alerts.showAlert("Erro na data de nascimento", null, "", AlertType.ERROR);
		}
		else {
			Instant instant = Instant.from(dateDataNascimento.getValue().atStartOfDay(ZoneId.systemDefault()));
			obj.setDataNascimento(Date.from(instant));
		}
		obj.setPlanNome(comboBoxPlans.getValue());
		return obj;
	}
	
	public void updateFormData() {
		if (entity == null)
			throw new IllegalStateException("Entidade vazia");
		txtId.setText(String.valueOf(entity.getId()));
		txtNome.setText(entity.getNome());
		txtCpf.setText(entity.getCpf());
		txtSalario.setText(String.valueOf(entity.getSalario()));
		txtEndereco.setText(entity.getEndereco());
		txtBairro.setText(entity.getBairro());
		txtEstado.setText(entity.getEstado());
		txtEmail.setText(entity.getEmail());
		if (entity.getDataNascimento() != null) {
			dateDataNascimento.setValue(LocalDate.ofInstant(entity.getDataNascimento().toInstant(), ZoneId.systemDefault()));
		}
		if(entity.getPlanNome() == null)
			comboBoxPlans.getSelectionModel().selectFirst();
		else
			comboBoxPlans.setValue(entity.getPlanNome());
	}

	@FXML
	public void onBtCancelarAction(ActionEvent event) {
		Utils.currentStage(event).close();
	}

	@Override
	public void initialize(URL url, ResourceBundle rb) {
		Constraints.setTextFieldInteger(txtId);
		Constraints.setTextFieldMaxLength(txtNome, 30);
		Constraints.setTextFieldDouble(txtSalario);
		Constraints.setTextFieldMaxLength(txtEstado, 2);
		Constraints.setTextFieldMaxLength(txtCpf, 15);
		Utils.formatDatePicker(dateDataNascimento, "dd/MM/yyyy");
		initializeComboBoxPlans();
	}

	public void loadAssociatedObjects() {
		if(planService == null)
			throw new IllegalStateException("Planos vazio");
		List<Plans> list = planService.findAll();
		obsList = FXCollections.observableArrayList(list);
		comboBoxPlans.setItems(obsList);
	}

	private void initializeComboBoxPlans() {
		Callback<ListView<Plans>, ListCell<Plans>> factory = lv -> new ListCell<Plans>() {
			@Override
			protected void updateItem(Plans item, boolean empty) {
				super.updateItem(item, empty);
				setText(empty ? "" : item.getName());
			}
		};
		comboBoxPlans.setCellFactory(factory);
		comboBoxPlans.setButtonCell(factory.call(null));
	}
}
