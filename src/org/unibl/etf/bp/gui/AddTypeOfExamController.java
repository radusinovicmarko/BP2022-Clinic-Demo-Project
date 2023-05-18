package org.unibl.etf.bp.gui;

import javafx.fxml.FXML;

import javafx.scene.control.Button;

import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.stage.Stage;

import org.unibl.etf.bp.data.mysql.TypeOfExamDataAccessMySQL;
import org.unibl.etf.bp.model.TypeOfExam;

import javafx.event.ActionEvent;

public class AddTypeOfExamController {
	@FXML
	private TextField codeTF;
	@FXML
	private TextField nameTF;
	@FXML
	private TextField priceInsuranceTF;
	@FXML
	private TextField priceNoInsuranceTF;
	@FXML
	private Button addBtn;

	// Event Listener on Button[#addBtn].onAction
	@FXML
	public void onAddAction(ActionEvent event) {
		Stage stage = (Stage)addBtn.getScene().getWindow();
		try {
			if (codeTF.getText().equals("") || nameTF.getText().equals("") || priceInsuranceTF.getText().equals("") || priceNoInsuranceTF.getText().equals("")) {
				UtilsGUI.showAlert(stage, AlertType.ERROR, "Greška", "Niste unijeli parametre!");
				return;
			}
			boolean success = new TypeOfExamDataAccessMySQL().addTypeOfExam(
					new TypeOfExam(0, codeTF.getText(), nameTF.getText(), Double.parseDouble(priceInsuranceTF.getText()), Double.parseDouble(priceNoInsuranceTF.getText())));
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
