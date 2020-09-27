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
import model.entities.Instructor;
import model.services.InstructorService;
import model.services.PlansService;

public class InstructorListController implements Initializable {
	private InstructorService service;

	@FXML
	private TableView<Instructor> tableViewInstructor;

	@FXML
	private TableColumn<Instructor, Integer> tableColumnId;

	@FXML
	private TableColumn<Instructor, String> tableColumnNome;

	@FXML
	private TableColumn<Instructor, Double> tableColumnSalario;

	@FXML
	private TableColumn<Instructor, String> tableColumnPlanVin;

	@FXML
	private TableColumn<Instructor, Instructor> tableColumnEdit;

	@FXML
	private TableColumn<Instructor, Instructor> tableColumnRemove;

	@FXML
	private TableColumn<Instructor, Instructor> tableColumnVisualize;

	@FXML
	private Button btRegistration;

	@FXML
	private Button btAtualizar;

	private ObservableList<Instructor> obsList;

	@FXML
	public void onBtRegistrationAction(ActionEvent event) {
		Stage parentStage = Utils.currentStage(event);
		Instructor obj = new Instructor();
		createForm(obj, "/gui/InstructorForm.fxml", parentStage);
	}

	@FXML
	public void onBtAtualizarAction() {
		updateTableView();
	}

	public void setInstructorService(InstructorService service) {
		this.service = service;
	}

	@Override
	public void initialize(URL url, ResourceBundle arg1) {
		initializeNodes();
	}

	private void initializeNodes() {
		tableColumnId.setCellValueFactory(new PropertyValueFactory<>("id"));
		tableColumnNome.setCellValueFactory(new PropertyValueFactory<>("nome"));
		tableColumnSalario.setCellValueFactory(new PropertyValueFactory<>("salario"));
		tableColumnPlanVin.setCellValueFactory(new PropertyValueFactory<>("planVin"));
		Stage stage = (Stage) Main.getMainScene().getWindow();
		tableViewInstructor.prefHeightProperty().bind(stage.heightProperty());
	}

	public void updateTableView() {
		if (service == null) {
			throw new IllegalStateException("Serviço vazio");
		}
		List<Instructor> list = service.findAll();
		obsList = FXCollections.observableArrayList(list);
		tableViewInstructor.setItems(obsList);
		initEditButtons();
		initRemoveButtons();
		initVisualizeButtons();
	}

	private void initEditButtons() {
		tableColumnEdit.setCellValueFactory(param -> new ReadOnlyObjectWrapper<>(param.getValue()));
		tableColumnEdit.setCellFactory(param -> new TableCell<Instructor, Instructor>() {
			private final Button button = new Button("EDITAR");

			@Override
			protected void updateItem(Instructor obj, boolean empty) {
				super.updateItem(obj, empty);
				if (obj == null) {
					setGraphic(null);
					return;
				}
				setGraphic(button);
				button.setOnAction(event -> createForm(obj, "/gui/InstructorForm.fxml", Utils.currentStage(event)));
			}
		});
	}

	private void initVisualizeButtons() {
		tableColumnVisualize.setCellValueFactory(param -> new ReadOnlyObjectWrapper<>(param.getValue()));
		tableColumnVisualize.setCellFactory(param -> new TableCell<Instructor, Instructor>() {
			private final Button button = new Button("VISUALIZAR");

			@Override
			protected void updateItem(Instructor obj, boolean empty) {
				super.updateItem(obj, empty);
				if (obj == null) {
					setGraphic(null);
					return;
				}
				setGraphic(button);
				button.setOnAction(event -> createForm(obj, "/gui/InstructorView.fxml", Utils.currentStage(event)));
			}
		});
	}

	private void initRemoveButtons() {
		tableColumnRemove.setCellValueFactory(param -> new ReadOnlyObjectWrapper<>(param.getValue()));
		tableColumnRemove.setCellFactory(param -> new TableCell<Instructor, Instructor>() {
			private final Button button = new Button("REMOVER");

			@Override
			protected void updateItem(Instructor obj, boolean empty) {
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

	public void createForm(Instructor obj, String absoluteName, Stage parentStage) {
		try {
			FXMLLoader loader = new FXMLLoader(getClass().getResource(absoluteName));
			Pane pane = loader.load();
			InstructorFormController controller = loader.getController();
			controller.setInstructor(obj);
			controller.setInstructorService(new InstructorService());
			controller.setPlanService(new PlansService());
			controller.loadAssociatedObjects();
			controller.updateFormData();
			Stage dialogStage = new Stage();
			dialogStage.setTitle("Dados do cliente");
			dialogStage.setScene(new Scene(pane));
			dialogStage.setResizable(false);
			dialogStage.initOwner(parentStage);
			dialogStage.initModality(Modality.WINDOW_MODAL);
			dialogStage.showAndWait();
		} catch (IOException e) {
			e.printStackTrace();
			Alerts.showAlert("Error", null, e.getMessage(), AlertType.ERROR);
		}
	}

	protected Object removeEntity(Instructor obj) {
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
