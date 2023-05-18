package org.unibl.etf.bp.gui;

import javafx.fxml.FXML;

import javafx.scene.control.Button;

import javafx.scene.layout.HBox;
import javafx.stage.Stage;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.scene.control.Alert.AlertType;

import java.sql.Date;

import org.unibl.etf.bp.data.mysql.PatientDataAccessMySQL;
import org.unibl.etf.bp.model.Patient;

import javafx.event.ActionEvent;

import javafx.scene.control.RadioButton;

import javafx.scene.control.CheckBox;

import javafx.scene.control.DatePicker;

public class RegistrationController {
	@FXML
	private TextField jmbTF;
	@FXML
	private TextField firstNameTF;
	@FXML
	private TextField lastNameTF;
	@FXML
	private DatePicker datePicker;
	@FXML
	private TextField emailTF;
	@FXML
	private TextField addressTF;
	@FXML
	private TextField placeTF;
	@FXML
	private TextField medicalCardTF;
	@FXML
	private CheckBox insuranceCB;
	@FXML
	private HBox insuranceNumberHB;
	@FXML
	private TextField insuranceNumberTF;
	@FXML
	private RadioButton aRB;
	@FXML
	private RadioButton bRB;
	@FXML
	private RadioButton abRB;
	@FXML
	private RadioButton zeroRB;
	@FXML
	private TextField marriageTF;
	@FXML
	private TextField phoneTF;
	@FXML
	private Button registerBtn;
	
	private boolean cbPressed = false;
	private ToggleGroup group = new ToggleGroup();
	private int teamId;
	
	public RegistrationController(int teamId) {
		this.teamId = teamId;
	}
	
	@FXML
	public void initialize() {
		aRB.setToggleGroup(group);
		bRB.setToggleGroup(group);
		abRB.setToggleGroup(group);
		zeroRB.setToggleGroup(group);
		insuranceNumberTF.setDisable(!cbPressed);
	}

	// Event Listener on CheckBox[#insuranceCB].onAction
	@FXML
	public void onChecked(ActionEvent event) {
		cbPressed = !cbPressed;
		insuranceNumberTF.setDisable(!cbPressed);
	}
	
	// Event Listener on Button[#registerBtn].onAction
	@FXML
	public void onRegisterAction(ActionEvent event) {
		try {
			Stage stage = (Stage)registerBtn.getScene().getWindow();
			if (jmbTF.getText().equals("") || firstNameTF.getText().equals("") || emailTF.getText().equals("")
					|| addressTF.getText().equals("") || datePicker.getValue() == null || medicalCardTF.getText().equals("")
					|| (cbPressed && insuranceNumberTF.getText().equals("")) || marriageTF.getText().equals("") || phoneTF.getText().equals("") 
					|| group.getSelectedToggle() == null) {
				UtilsGUI.showAlert(stage, AlertType.ERROR, "Greška", "Niste unijeli parametre!");
				return;
			}
			
			Patient p = new Patient(0, jmbTF.getText(), firstNameTF.getText(), lastNameTF.getText(), emailTF.getText(), addressTF.getText(), 
					Date.valueOf(datePicker.getValue()), medicalCardTF.getText(), (cbPressed ? insuranceNumberTF.getText() : ""), 
					((RadioButton) group.getSelectedToggle()).getText(), marriageTF.getText(), phoneTF.getText(), cbPressed);
			
			boolean success = new PatientDataAccessMySQL().registerPatient(p, placeTF.getText(), teamId);
			if (success)
				UtilsGUI.showAlert(stage, AlertType.INFORMATION, "Informacija", "Registracija uspješna!");
			else
				UtilsGUI.showAlert(stage, AlertType.INFORMATION, "Informacija", "Registracija neuspješna!");
			stage.close();
		} catch (Exception ex) {
			UtilsGUI.showAlert((Stage)registerBtn.getScene().getWindow(), AlertType.ERROR, "Greška", "Greška!");
		}
	}
}
