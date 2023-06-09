package org.unibl.etf.bp.gui;

import javafx.fxml.FXML;

import javafx.scene.control.Button;

import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.stage.Stage;

import org.unibl.etf.bp.data.mysql.MedicationDataAccessMySQL;
import org.unibl.etf.bp.model.Medication;

import javafx.event.ActionEvent;

public class AddMedicationController {
	@FXML
	private TextField genericNameTF;
	@FXML
	private TextField factoryNameTF;
	@FXML
	private Button addBtn;

	// Event Listener on Button[#addBtn].onAction
	@FXML
	public void onAddAction(ActionEvent event) {
		Stage stage = (Stage)addBtn.getScene().getWindow();
		try {
			if (genericNameTF.getText().equals("") || factoryNameTF.getText().equals("")) {
				UtilsGUI.showAlert(stage, AlertType.ERROR, "Gre�ka", "Niste unijeli parametre!");
				return;
			}
			boolean success = new MedicationDataAccessMySQL().addMedication(new Medication(0, genericNameTF.getText(), factoryNameTF.getText()));
			if (success)
				UtilsGUI.showAlert(stage, AlertType.INFORMATION, "Informacija", "Dodavanje uspje�no!");
			else
				UtilsGUI.showAlert(stage, AlertType.INFORMATION, "Informacija", "Dodavanje neuspje�no!");
			stage.close();
		} catch (Exception ex) {
			UtilsGUI.showAlert(stage, AlertType.ERROR, "Gre�ka", "Gre�ka!");
		}
	}
}
