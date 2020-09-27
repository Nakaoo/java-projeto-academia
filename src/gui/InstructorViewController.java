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
import javafx.scene.control.TextField;
import model.entities.Instructor;
import model.entities.Plans;
import model.services.InstructorService;
import model.services.PlansService;



public class InstructorViewController implements Initializable {

	private Instructor entity;
	@SuppressWarnings("unused")
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
	public void onBtCancelarAction(ActionEvent event) {
		Utils.currentStage(event).close();
	}

	
	@FXML
	public void onBtSalvarAction(ActionEvent event) {
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

	public void loadAssociatedObjects() {
		if(planService == null)
			throw new IllegalStateException("Planos vazio");
		List<Plans> list = planService.findAll();
		obsList = FXCollections.observableArrayList(list);
		comboBoxPlans.setItems(obsList);
	}

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		// TODO Auto-generated method stub
		
	}
}
