package org.unibl.etf.bp.gui;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.cell.PropertyValueFactory;

import java.io.IOException;
import java.util.List;

import org.unibl.etf.bp.data.MedicationDataAccess;
import org.unibl.etf.bp.data.mysql.MedicationDataAccessMySQL;
import org.unibl.etf.bp.model.Medication;

import javafx.event.ActionEvent;

import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import javafx.scene.input.KeyEvent;

import javafx.scene.control.TableView;

public class MedicationController {
	@FXML
	private TextField searchTF;
	@SuppressWarnings("rawtypes")
	@FXML
	private TableView medicationsTableView;
	@FXML
	private Button addBtn;
	@FXML
	private Button deleteBtn;
	
	private MedicationDataAccess dao;
	
	@FXML
	public void initialize() {
		dao = new MedicationDataAccessMySQL();
		search("%");
	}
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	private void search(String name) {
		List<Medication> list = null;
		try {
			list = dao.getMedications(name + "%");
			((TableColumn) medicationsTableView.getColumns().get(0))
					.setCellValueFactory(new PropertyValueFactory<>("genericName"));
			((TableColumn) medicationsTableView.getColumns().get(1))
					.setCellValueFactory(new PropertyValueFactory<>("factoryName"));
			medicationsTableView.getItems().clear();
			list.stream().forEach(i -> medicationsTableView.getItems().add(i));
		} catch (Exception ex) {
			UtilsGUI.showAlert((Stage)searchTF.getScene().getWindow(), AlertType.ERROR, "Greška", "Greška!");
		}
	}

	// Event Listener on TextField[#searchTF].onKeyTyped
	@FXML
	public void onSearchAction(KeyEvent event) {
		search(searchTF.getText());
	}
	
	// Event Listener on TableView[#medicationsTableView].onMouseClicked
	@FXML
	public void onMedicationSelected(MouseEvent event) {
		deleteBtn.setDisable(false);
	}
	
	// Event Listener on Button[#addBtn].onAction
	@FXML
	public void onAddAction(ActionEvent event) {
		try {
			FXMLLoader loader = new FXMLLoader(getClass().getResource("AddMedication.fxml"));
			Parent root = loader.load();
			Scene scene = new Scene(root);
			Stage newStage = new Stage();
			newStage.setTitle("Dodaj lijek");
			newStage.setScene(scene);
			newStage.showAndWait();
			search("%");
		} catch (IOException ex) {
			UtilsGUI.showAlert((Stage)searchTF.getScene().getWindow(), AlertType.ERROR, "Greška", "Greška!");
		}
	}
	
	// Event Listener on Button[#deleteBtn].onAction
	@FXML
	public void onDeleteAction(ActionEvent event) {
		Stage stage = (Stage)medicationsTableView.getScene().getWindow();
		try {
			Medication m = (Medication) medicationsTableView.getSelectionModel().getSelectedItem();
			if (dao.deleteMedication(m))
				UtilsGUI.showAlert(stage, AlertType.INFORMATION, "Informacija", "Brisanje uspješno!");
			else
				UtilsGUI.showAlert(stage, AlertType.INFORMATION, "Informacija", "Brisanje neuspješno!");
			search("%");
			deleteBtn.setDisable(true);
		} catch (Exception ex) {
			UtilsGUI.showAlert(stage, AlertType.ERROR, "Greška", "Greška!");
		}
	}
}
