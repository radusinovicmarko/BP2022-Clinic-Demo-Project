package org.unibl.etf.bp.gui;

import javafx.fxml.FXML;

import javafx.scene.control.Button;

import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.stage.Stage;

import org.unibl.etf.bp.data.mysql.IllnessDataAccessMySQL;
import org.unibl.etf.bp.model.Illness;

import javafx.event.ActionEvent;

public class AddIllnessController {
	@FXML
	private TextField nameTF;
	@FXML
	private TextField codeTF;
	@FXML
	private Button addBtn;

	// Event Listener on Button[#addBtn].onAction
	@FXML
	public void onAddAction(ActionEvent event) {
		Stage stage = (Stage)addBtn.getScene().getWindow();
		try {
			if (nameTF.getText().equals("") || codeTF.getText().equals("")) {
				UtilsGUI.showAlert(stage, AlertType.ERROR, "Greška", "Niste unijeli parametre!");
				return;
			}
			boolean success = new IllnessDataAccessMySQL().addIllness(new Illness(0, nameTF.getText(), codeTF.getText()));
			if (success)
				UtilsGUI.showAlert(stage, AlertType.INFORMATION, "Informacija", "Dodavanje uspješno!");
			else
				UtilsGUI.showAlert(stage, AlertType.INFORMATION, "Informacija", "Dodavanje neuspješno!");
			stage.close();
		} catch (Exception ex) {
			UtilsGUI.showAlert(stage, AlertType.ERROR, "Greška", "Greška!");
		}
	}
}
