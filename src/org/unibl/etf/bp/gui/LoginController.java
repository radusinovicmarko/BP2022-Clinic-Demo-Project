package org.unibl.etf.bp.gui;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;

import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.stage.Stage;
import org.unibl.etf.bp.data.mysql.UtilsMySQL;

import javafx.event.ActionEvent;

import javafx.scene.control.PasswordField;

public class LoginController {
	@FXML
	private TextField usernameTB;
	@FXML
	private PasswordField passwordPF;
	@FXML
	private Button loginBtn;

	// Event Listener on Button[#loginBtn].onAction
	@FXML
	public void onLoginBtnClicked(ActionEvent event) {
		boolean loginSuccessful = false, typeOfUser = false;
		Stage stage = (Stage) loginBtn.getScene().getWindow();

		String username = usernameTB.getText(), password = passwordPF.getText();

		if (username.equals("") || password.equals("")) {
			UtilsGUI.showAlert(stage, AlertType.ERROR, "Greška", "Niste popunili sva polja!");
			return;
		}
		
		try {
			String res = UtilsMySQL.login(username, password);

			loginSuccessful = Boolean.parseBoolean(res.split("#")[0]);
			typeOfUser = Boolean.parseBoolean(res.split("#")[1]);

			if (loginSuccessful) {
				FXMLLoader loader = new FXMLLoader(getClass().getResource("Homepage.fxml"));
				boolean type = typeOfUser;
				loader.setControllerFactory(c -> new HomepageController(username, type));
				Parent root = loader.load();
				Scene scene = new Scene(root);
				Stage newStage = new Stage();
				newStage.setMaximized(true);
				newStage.setTitle("Aplikacija");
				newStage.setScene(scene);
				stage.hide();
				newStage.show();
			}
			else
				UtilsGUI.showAlert(stage, AlertType.ERROR, "Prijava", "Prijava neuspješna");
		} catch (Exception  e) {
			UtilsGUI.showAlert(stage, AlertType.ERROR, "Greška", e.getMessage());
		}
	}
}
