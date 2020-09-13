package gui;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.MenuItem;

public class MainViewController implements Initializable {

	@FXML
	private MenuItem menuIemCadastrarCli;
	
	@FXML
	private MenuItem menuIemListarCli;
	
	@FXML
	public void onMenuItemCadastrarCliAction() {
		System.out.println("Cadastrar");
	}
	
	@FXML
	public void onMenuItemListarCliAction() {
		System.out.println("Listar");
	}
	
	@Override
	public void initialize(URL uri, ResourceBundle rb) {
		
	}
}
