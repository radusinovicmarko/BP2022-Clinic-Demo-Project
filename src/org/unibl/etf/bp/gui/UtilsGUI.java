package org.unibl.etf.bp.gui;

import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.stage.Stage;

public class UtilsGUI {
	
	public static void showAlert(Stage stage, AlertType type, String header, String content) {
		Alert alert = new Alert(type);
		alert.setTitle("Alert");
		alert.setHeaderText(header);
		alert.setContentText(content);
		alert.showAndWait();
	}

}
