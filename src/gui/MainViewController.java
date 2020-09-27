package gui;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.function.Consumer;

import application.Main;
import gui.util.Alerts;
import gui.util.Utils;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.MenuItem;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.VBox;
import model.services.ClientService;
import model.services.InstructorService;
import model.services.PlansService;

public class MainViewController implements Initializable {
	
	@FXML
	private MenuItem menuItemPlanListar;
	
	@FXML
	private MenuItem menuItemInstListar;
	
	@FXML
	private MenuItem menuItemCancelar;
	
	@FXML 
	private MenuItem menuItemAjudaSobre;

	@FXML
	public void onMenuitemCancelar(ActionEvent event){
		Utils.currentStage(event).close();
	}
	
	@FXML
	public void onMenuIemPlanListar() {
		this.loadView("/gui/PlanList.fxml", (PlanListController controller) -> {
			controller.setPlanService(new PlansService());
			controller.updateTableView();
		});
	}
	
	@FXML
	public void onMenuIemInstListar() {
		this.loadView("/gui/InstructorList.fxml", (InstructorListController controller) -> {
			controller.setInstructorService(new InstructorService());
			controller.updateTableView();
		});
	}
	
	
	@FXML
	public void onMenuItemCliListarAction() {
		this.loadView("/gui/ClientList.fxml", (ClientListController controller) -> {
			controller.setClientService(new ClientService());
			controller.updateTableView();
		});
	}
	
	
	@FXML
	public void onMenuItemAjudaSobreAction() {
		loadView("/gui/About.fxml", x -> {});
	}
	
	
	@Override
	public void initialize(URL uri, ResourceBundle rb) {
		
	}
	
	private synchronized <T> void loadView(String absoluteName, Consumer<T> initializingAction) {
		try {
			FXMLLoader loader = new FXMLLoader(getClass().getResource(absoluteName));
			VBox newVBox = loader.load();
			
			Scene mainScene = Main.getMainScene();
			VBox mainVBox = (VBox) ((ScrollPane) mainScene.getRoot()).getContent();
			
			Node mainMenu = mainVBox.getChildren().get(0);
			mainVBox.getChildren().clear();
			mainVBox.getChildren().add(mainMenu);
			mainVBox.getChildren().addAll(newVBox.getChildren());
			
			T controller = loader.getController();
			initializingAction.accept(controller);
		}
		catch(IOException e) {
			Alerts.showAlert("IOException", "Erro carregando pagina", e.getMessage(), AlertType.ERROR);
		}
	}

}
