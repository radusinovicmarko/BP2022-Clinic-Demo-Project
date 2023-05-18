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

import org.unibl.etf.bp.data.TypeOfExamDataAccess;
import org.unibl.etf.bp.data.mysql.TypeOfExamDataAccessMySQL;
import org.unibl.etf.bp.model.TypeOfExam;

import javafx.event.ActionEvent;

import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import javafx.scene.input.KeyEvent;

import javafx.scene.control.TableView;

public class TypeOfExamController {
	@FXML
	private TextField searchTF;
	@SuppressWarnings("rawtypes")
	@FXML
	private TableView typesOfExamsTableView;
	@FXML
	private Button addBtn;
	@FXML
	private Button deleteBtn;

	private TypeOfExamDataAccess dao;
	
	@FXML
	public void initialize() {
		dao = new TypeOfExamDataAccessMySQL();
		search("%");
	}
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	private void search(String name) {
		List<TypeOfExam> list = null;
		try {
			list = dao.getTypesOfExam(name + "%");
			((TableColumn) typesOfExamsTableView.getColumns().get(0))
					.setCellValueFactory(new PropertyValueFactory<>("code"));
			((TableColumn) typesOfExamsTableView.getColumns().get(1))
					.setCellValueFactory(new PropertyValueFactory<>("name"));
			((TableColumn) typesOfExamsTableView.getColumns().get(2))
			.setCellValueFactory(new PropertyValueFactory<>("priceInsurance"));
			((TableColumn) typesOfExamsTableView.getColumns().get(3))
			.setCellValueFactory(new PropertyValueFactory<>("priceNoInsurance"));
			typesOfExamsTableView.getItems().clear();
			list.stream().forEach(i -> typesOfExamsTableView.getItems().add(i));
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}
	
	// Event Listener on TextField[#searchTF].onKeyTyped
	@FXML
	public void onSearchAction(KeyEvent event) {
		search(searchTF.getText());
	}
	
	// Event Listener on TableView[#typesOfExamsTableView].onMouseClicked
	@FXML
	public void onTypeSelected(MouseEvent event) {
		deleteBtn.setDisable(false);
	}
	
	// Event Listener on Button[#addBtn].onAction
	@FXML
	public void onAddAction(ActionEvent event) {
		try {
			FXMLLoader loader = new FXMLLoader(getClass().getResource("AddTypeOfExam.fxml"));
			Parent root = loader.load();
			Scene scene = new Scene(root);
			Stage newStage = new Stage();
			newStage.setTitle("Dodaj vrstu pregleda");
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
		Stage stage = (Stage)typesOfExamsTableView.getScene().getWindow();
		try {
			TypeOfExam type = (TypeOfExam) typesOfExamsTableView.getSelectionModel().getSelectedItem();
			if (dao.deleteTypeOfExam(type))
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
