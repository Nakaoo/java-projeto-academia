package gui;

import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;

import application.Main;
import db.DbIntegrityException;
import gui.util.Alerts;
import gui.util.Utils;
import javafx.beans.property.ReadOnlyObjectWrapper;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.Pane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import model.entities.Plans;
import model.services.PlansService;

public class PlanListController implements Initializable {
	private PlansService service;

	@FXML
	private TableView<Plans> tableViewPlans;

	@FXML
	private TableColumn<Plans, Integer> tableColumnId;

	@FXML
	private TableColumn<Plans, String> tableColumnNome;

	@FXML
	private TableColumn<Plans, Float> tableColumnPreco;

	@FXML
	private TableColumn<Plans, Plans> tableColumnEdit;

	@FXML
	private TableColumn<Plans, Plans> tableColumnRemove;

	@FXML
	private Button btRegistration;

	@FXML
	private Button btFechar;

	private ObservableList<Plans> obsList;

	@FXML
	public void onBtRegistrationAction(ActionEvent event) {
		Stage parentStage = Utils.currentStage(event);
		Plans obj = new Plans();
		createForm(obj, "/gui/PlanForm.fxml", parentStage);
	}

	@FXML
	public void onBtAtualizarAction() {
		updateTableView();
	}

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		initializeNodes();
	}

	private void initializeNodes() {
		tableColumnId.setCellValueFactory(new PropertyValueFactory<>("id"));
		tableColumnNome.setCellValueFactory(new PropertyValueFactory<>("name"));
		tableColumnPreco.setCellValueFactory(new PropertyValueFactory<>("price"));
		Stage stage = (Stage) Main.getMainScene().getWindow();
		tableViewPlans.prefHeightProperty().bind(stage.heightProperty());
	}

	public void setPlanService(PlansService service) {
		this.service = service;
	}

	public void updateTableView() {
		if (service == null) {
			throw new IllegalStateException("Serviço vazio");
		}
		List<Plans> list = service.findAll();
		obsList = FXCollections.observableArrayList(list);
		tableViewPlans.setItems(obsList);
		initEditButtons();
		initRemoveButtons();
	}

	public void createForm(Plans obj, String absoluteName, Stage parentStage) {
		try {
			FXMLLoader loader = new FXMLLoader(getClass().getResource(absoluteName));
			Pane pane = loader.load();
			PlanFormController controller = loader.getController();
			controller.setPlans(obj);
			controller.setPlanService(new PlansService());
			controller.updateFormData();
			Stage dialogStage = new Stage();
			dialogStage.setTitle("Dados do plano");
			dialogStage.setScene(new Scene(pane));
			dialogStage.setResizable(false);
			dialogStage.initOwner(parentStage);
			dialogStage.initModality(Modality.WINDOW_MODAL);
			dialogStage.showAndWait();
		} catch (IOException e) {
			Alerts.showAlert("Error", null, e.getMessage(), AlertType.ERROR);
		}
	}

	private void initEditButtons() {
		tableColumnEdit.setCellValueFactory(param -> new ReadOnlyObjectWrapper<>(param.getValue()));
		tableColumnEdit.setCellFactory(param -> new TableCell<Plans, Plans>() {
			private final Button button = new Button("EDITAR");

			@Override
			protected void updateItem(Plans obj, boolean empty) {
				super.updateItem(obj, empty);
				if (obj == null) {
					setGraphic(null);
					return;
				}
				setGraphic(button);
				button.setOnAction(event -> createForm(obj, "/gui/PlanForm.fxml", Utils.currentStage(event)));
			}
		});
	}

	private void initRemoveButtons() {
		tableColumnRemove.setCellValueFactory(param -> new ReadOnlyObjectWrapper<>(param.getValue()));
		tableColumnRemove.setCellFactory(param -> new TableCell<Plans, Plans>() {
			private final Button button = new Button("REMOVER");

			@Override
			protected void updateItem(Plans obj, boolean empty) {
				super.updateItem(obj, empty);
				if (obj == null) {
					setGraphic(null);
					return;
				}
				setGraphic(button);
				button.setOnAction(event -> removeEntity(obj));
			}
		});
	}

	protected Object removeEntity(Plans obj) {
		Optional<ButtonType> result = Alerts.showConfirmation("Confirmação", "Tem certeza que deseja deletar?");

		if (result.get() == ButtonType.OK) {
			if (service == null) {
				throw new IllegalStateException("Serviço estava vazio");
			}
			try {
				service.remove(obj);
				updateTableView();
			} catch (DbIntegrityException e) {
				Alerts.showAlert("Error em remove o objeto", null, e.getMessage(), AlertType.ERROR);
			}
			service.remove(obj);
		}
		return null;

	}
}
