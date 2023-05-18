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
import java.sql.SQLException;
import java.util.List;

import org.unibl.etf.bp.data.mysql.IllnessDataAccessMySQL;
import org.unibl.etf.bp.model.Illness;

import javafx.event.ActionEvent;

import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import javafx.scene.input.KeyEvent;

import javafx.scene.control.TableView;

public class IllnessController {
	@FXML
	private TextField searchTF;
	@SuppressWarnings("rawtypes")
	@FXML
	private TableView illnessesTableView;
	@FXML
	private Button addBtn;
	@FXML
	private Button deleteBtn;

	private IllnessDataAccessMySQL dao;

	@FXML
	public void initialize() {
		dao = new IllnessDataAccessMySQL();
		search("%");
	}

	// Event Listener on TextField[#searchTF].onKeyTyped
	@FXML
	public void onSearchAction(KeyEvent event) {
		search(searchTF.getText());
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	private void search(String name) {
		List<Illness> list = null;
		try {
			list = dao.getIllnesses(name + "%");
			((TableColumn) illnessesTableView.getColumns().get(0))
					.setCellValueFactory(new PropertyValueFactory<>("name"));
			((TableColumn) illnessesTableView.getColumns().get(1))
					.setCellValueFactory(new PropertyValueFactory<>("code"));
			illnessesTableView.getItems().clear();
			list.stream().forEach(i -> illnessesTableView.getItems().add(i));
		} catch (SQLException ex) {
			UtilsGUI.showAlert((Stage)searchTF.getScene().getWindow(), AlertType.ERROR, "Greška", "Greška!");
		}
	}

	// Event Listener on TableView[#illnessesTableView].onMouseClicked
	@FXML
	public void onIllnessSelected(MouseEvent event) {
		deleteBtn.setDisable(false);
	}

	// Event Listener on Button[#addBtn].onAction
	@FXML
	public void onAddAction(ActionEvent event) {
		try {
			FXMLLoader loader = new FXMLLoader(getClass().getResource("AddIllness.fxml"));
			Parent root = loader.load();
			Scene scene = new Scene(root);
			Stage newStage = new Stage();
			newStage.setTitle("Dodaj bolest");
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
		Stage stage = (Stage)illnessesTableView.getScene().getWindow();
		try {
			Illness i = (Illness) illnessesTableView.getSelectionModel().getSelectedItem();
			if (dao.deleteIllness(i))
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
