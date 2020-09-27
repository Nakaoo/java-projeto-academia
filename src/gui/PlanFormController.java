package gui;

import java.net.URL;
import java.util.ResourceBundle;

import db.DBException;
import gui.util.Alerts;
import gui.util.Constraints;
import gui.util.Utils;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import model.entities.Plans;
import model.services.PlansService;

public class PlanFormController implements Initializable {

	private Plans entity;
	private PlansService service;
	
	@FXML
	private TextField txtId;

	@FXML
	private TextField txtNome;
	
	@FXML
	private TextField txtPreco;

	@FXML
	private Button btSalvar;

	@FXML
	private Button btCancelar;

	public void setPlans(Plans entity) {
		this.entity = entity;
	}

	public void setPlanService(PlansService service) {
		this.service = service;
	}

	@FXML
	public void onBtSalvarAction(ActionEvent event) {
		if(entity == null) {
			throw new IllegalStateException("Entidade vazia");
		}
		if(service == null) {
			throw new IllegalStateException("Serviço vazia");
		}
		
		try{
			entity = getFormData();
			service.saveOrUpdate(entity);
			Utils.currentStage(event).close();
		} 
		catch (DBException e) {
			Alerts.showAlert("Error em salvar", null, e.getMessage(), AlertType.ERROR);
		}
	}

	private Plans getFormData() {
		Plans obj = new Plans();
		obj.setId(Utils.tryParseToInt(txtId.getText()));
		obj.setName(txtNome.getText());	
		obj.setPrice(Utils.tryParseToFloat(txtPreco.getText()));
		return obj;
	}

	@FXML
	public void onBtCancelarAction(ActionEvent event) {
		Utils.currentStage(event).close();
	}

	@Override
	public void initialize(URL url, ResourceBundle rb) {
		Constraints.setTextFieldInteger(txtId);
		Constraints.setTextFieldMaxLength(txtNome, 30);
	}
	
	public void updateFormData() {
		if (entity == null)
			throw new IllegalStateException("Entidade vazia");
		txtId.setText(String.valueOf(entity.getId()));
		txtNome.setText(entity.getName());
		txtPreco.setText(String.valueOf(entity.getPrice()));
	}
}
