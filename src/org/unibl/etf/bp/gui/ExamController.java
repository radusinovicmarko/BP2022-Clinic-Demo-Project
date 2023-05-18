package org.unibl.etf.bp.gui;

import javafx.fxml.FXML;

import javafx.scene.control.Button;

import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.stage.Stage;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.unibl.etf.bp.data.mysql.ExamDataAccessMySQL;

import javafx.event.ActionEvent;

import javafx.scene.control.ListView;

public class ExamController {
	@FXML
	private TextField diagnosisTF;
	@FXML
	private TextField examTF;
	@FXML
	private TextField jmbTF;
	@FXML
	private TextField serviceTF;
	@FXML
	private Button addServiceBtn;
	@SuppressWarnings("rawtypes")
	@FXML
	private ListView servicesLV;
	@FXML
	private Button createBtn;
	
	private String jmb = null;
	
	private int teamId;

	public ExamController(int teamId) {
		this.teamId = teamId;
	}
	
	public ExamController(String jmb) {
		this.jmb = jmb;
	}
	
	@FXML
	public void initialize() {
		if (jmb != null)
			jmbTF.setText(jmb);
	}

	// Event Listener on Button[#addServiceBtn].onAction
	@SuppressWarnings("unchecked")
	@FXML
	public void onAddServiceAction(ActionEvent event) {
		if (serviceTF.getText().equals("")) {
			return;
		}
		servicesLV.getItems().add(serviceTF.getText());
	}
	
	// Event Listener on Button[#createBtn].onAction
	@SuppressWarnings("unchecked")
	@FXML
	public void onCreateAction(ActionEvent event) {
		Stage stage = (Stage) createBtn.getScene().getWindow();
		List<String> list = new ArrayList<>();
		servicesLV.getItems().stream().forEach(i -> { list.add((String) i); });
		try {
			boolean success = new ExamDataAccessMySQL().logExam(diagnosisTF.getText(), examTF.getText(), jmbTF.getText(), teamId, list);
			if (success)
				UtilsGUI.showAlert(stage, AlertType.INFORMATION, "Informacija", "Evidentiranje pregleda uspješno!");
			else
				UtilsGUI.showAlert(stage, AlertType.INFORMATION, "Informacija", "Evidentiranje pregleda neuspješno!");
			stage.close();
		} catch (SQLException ex) {
			UtilsGUI.showAlert(stage, AlertType.ERROR, "Greška", "Greška!");
		}
	}
}
