package org.unibl.etf.bp.gui;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.cell.PropertyValueFactory;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

import org.unibl.etf.bp.data.PatientDataAccess;
import org.unibl.etf.bp.data.mysql.PatientDataAccessMySQL;
import org.unibl.etf.bp.data.mysql.UtilsMySQL;
import org.unibl.etf.bp.model.Patient;

import javafx.event.ActionEvent;

import javafx.scene.control.MenuItem;
import javafx.scene.control.TableColumn;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;

import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import javafx.scene.input.KeyEvent;

import javafx.scene.control.TableView;

public class HomepageController {
	@FXML
	private MenuItem newRegistrationMenuItem;
	@FXML
	private MenuItem newExamMenuItem;
	@FXML
	private MenuItem newAppointementMenuItem;
	@FXML
	private MenuItem newPrescriptionMenuItem;
	@FXML
	private MenuItem newOrderMenuItem;
	@FXML
	private Label nameAndTeamLbl;
	@FXML
	private TextField searchTF;
	@SuppressWarnings("rawtypes")
	@FXML
	private TableView pacientsTableView;
	
	private PatientDataAccess dao;

	// true - doctor
	// false - technician
	private boolean typeOfUser = false;
	private String username;
	private int teamId = -1;
	
	public HomepageController(String username, boolean typeOfUser) {
		super();
		this.typeOfUser = typeOfUser;
		this.username = username;
	}

	@FXML
	public void initialize() {
		dao = new PatientDataAccessMySQL();
		if (typeOfUser) {
			newAppointementMenuItem.setVisible(false);
			newRegistrationMenuItem.setVisible(false);
		} else {
			newExamMenuItem.setVisible(false);
			newPrescriptionMenuItem.setVisible(false);
			newOrderMenuItem.setVisible(false);
		}
		try {
			String info = UtilsMySQL.getAmbulanceInfo(username);
			String team = info.split("#")[0];
			String name = info.split("#")[1];
			teamId = Integer.parseInt(team);
			nameAndTeamLbl.setText(name + ": Tim " + team);
		} catch (SQLException e) {
			UtilsGUI.showAlert((Stage)searchTF.getScene().getWindow(), AlertType.ERROR, "Greška", "Greška!");
		}
		try {
			List<Patient> list = dao.getPatients(teamId);
			populateTable(list);
		} catch (Exception e) {
			UtilsGUI.showAlert((Stage)searchTF.getScene().getWindow(), AlertType.ERROR, "Greška", "Greška!");
		}
	}
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	private void populateTable(List<Patient> list) {
		((TableColumn) pacientsTableView.getColumns().get(0)).setCellValueFactory(new PropertyValueFactory<>("jmb"));
		((TableColumn) pacientsTableView.getColumns().get(1)).setCellValueFactory(new PropertyValueFactory<>("firstName"));
		((TableColumn) pacientsTableView.getColumns().get(2)).setCellValueFactory(new PropertyValueFactory<>("lastName"));
		((TableColumn) pacientsTableView.getColumns().get(3)).setCellValueFactory(new PropertyValueFactory<>("dateOfBirth"));
		((TableColumn) pacientsTableView.getColumns().get(4)).setCellValueFactory(new PropertyValueFactory<>("email"));
		((TableColumn) pacientsTableView.getColumns().get(5)).setCellValueFactory(new PropertyValueFactory<>("address"));
		((TableColumn) pacientsTableView.getColumns().get(6)).setCellValueFactory(new PropertyValueFactory<>("medicalCardNumber"));
		((TableColumn) pacientsTableView.getColumns().get(7)).setCellValueFactory(new PropertyValueFactory<>("hasInsurance"));
		pacientsTableView.getItems().clear();
		list.stream().forEach(p -> pacientsTableView.getItems().add(p));
	}

	// Event Listener on MenuItem.onAction
	@FXML
	public void onCloseAction(ActionEvent event) {
		try {
			FXMLLoader loader = new FXMLLoader(getClass().getResource("Login.fxml"));
			Parent root = loader.load();
			Scene scene = new Scene(root, 500, 300);
			Stage newStage = new Stage();
			newStage.setResizable(false);
			newStage.setTitle("Prijava");
			newStage.setScene(scene);
			Stage stage = (Stage) nameAndTeamLbl.getScene().getWindow();
			stage.hide();
			newStage.show();
		} catch (IOException ex) {
			UtilsGUI.showAlert((Stage)searchTF.getScene().getWindow(), AlertType.ERROR, "Greška", "Greška!");
		}
	}

	// Event Listener on MenuItem[#newRegistrationMenuItem].onAction
	@FXML
	public void onNewRegistrationAction(ActionEvent event) {
		try {
			FXMLLoader loader = new FXMLLoader(getClass().getResource("Registration.fxml"));
			loader.setControllerFactory(c -> new RegistrationController(teamId));
			Parent root = loader.load();
			Scene scene = new Scene(root);
			Stage newStage = new Stage();
			newStage.setTitle("Registracija");
			newStage.setResizable(false);
			newStage.setScene(scene);
			newStage.show();
		} catch (IOException ex) {
			UtilsGUI.showAlert((Stage)searchTF.getScene().getWindow(), AlertType.ERROR, "Greška", "Greška!");
		}
	}

	// Event Listener on MenuItem[#newExamMenuItem].onAction
	@FXML
	public void onNewExamAction(ActionEvent event) {
		try {
			FXMLLoader loader = new FXMLLoader(getClass().getResource("Exam.fxml"));
			loader.setControllerFactory(c -> new ExamController(teamId));
			Parent root = loader.load();
			Scene scene = new Scene(root);
			Stage newStage = new Stage();
			newStage.setTitle("Pregled");
			newStage.setResizable(false);
			newStage.setScene(scene);
			newStage.show();
		} catch (IOException ex) {
			UtilsGUI.showAlert((Stage)searchTF.getScene().getWindow(), AlertType.ERROR, "Greška", "Greška!");
		}
	}

	// Event Listener on MenuItem.onAction
	@FXML
	public void onPreviousExamsAction(ActionEvent event) {
		// TODO Autogenerated
	}

	// Event Listener on MenuItem[#newAppointementMenuItem].onAction
	@FXML
	public void onNewAppointmentAction(ActionEvent event) {
		// TODO Autogenerated
	}

	// Event Listener on MenuItem.onAction
	@FXML
	public void onTodaysAppointmentsAction(ActionEvent event) {
		// TODO Autogenerated
	}

	// Event Listener on MenuItem.onAction
	@FXML
	public void onNextDaysAppointmentsAction(ActionEvent event) {
		// TODO Autogenerated
	}

	// Event Listener on MenuItem[#newPrescriptionMenuItem].onAction
	@FXML
	public void onNewPrescriptionAction(ActionEvent event) {
		// TODO Autogenerated
	}

	// Event Listener on MenuItem[#newOrderMenuItem].onAction
	@FXML
	public void onNewOrderAction(ActionEvent event) {
		// TODO Autogenerated
	}

	// Event Listener on MenuItem.onAction
	@FXML
	public void onIssuedPrescriptionsAction(ActionEvent event) {
		// TODO Autogenerated
	}

	// Event Listener on MenuItem.onAction
	@FXML
	public void onIssuedOrdersAction(ActionEvent event) {
		// TODO Autogenerated
	}

	// Event Listener on MenuItem.onAction
	@FXML
	public void onIllnessesAction(ActionEvent event) {
		try {
			FXMLLoader loader = new FXMLLoader(getClass().getResource("Illness.fxml"));
			Parent root = loader.load();
			Scene scene = new Scene(root);
			Stage newStage = new Stage();
			newStage.setTitle("Bolesti");
			newStage.setScene(scene);
			newStage.show();
		} catch (IOException ex) {
			UtilsGUI.showAlert((Stage)searchTF.getScene().getWindow(), AlertType.ERROR, "Greška", "Greška!");
		}
	}

	// Event Listener on MenuItem.onAction
	@FXML
	public void onMedicationsAction(ActionEvent event) {
		try {
			FXMLLoader loader = new FXMLLoader(getClass().getResource("Medication.fxml"));
			Parent root = loader.load();
			Scene scene = new Scene(root);
			Stage newStage = new Stage();
			newStage.setTitle("Lijekovi");
			newStage.setScene(scene);
			newStage.show();
		} catch (IOException ex) {
			UtilsGUI.showAlert((Stage)searchTF.getScene().getWindow(), AlertType.ERROR, "Greška", "Greška!");
		}
	}

	// Event Listener on MenuItem.onAction
	@FXML
	public void onTypesOfExamsAction(ActionEvent event) {
		try {
			FXMLLoader loader = new FXMLLoader(getClass().getResource("TypeOfExam.fxml"));
			Parent root = loader.load();
			Scene scene = new Scene(root);
			Stage newStage = new Stage();
			newStage.setTitle("Vrste pregleda");
			newStage.setScene(scene);
			newStage.show();
		} catch (IOException ex) {
			UtilsGUI.showAlert((Stage)searchTF.getScene().getWindow(), AlertType.ERROR, "Greška", "Greška!");
		}
	}

	// Event Listener on MenuItem.onAction
	@FXML
	public void onServicesAction(ActionEvent event) {
		// TODO Autogenerated
	}

	// Event Listener on MenuItem.onAction
	@FXML
	public void onAboutAction(ActionEvent event) {
		// TODO Autogenerated
	}

	// Event Listener on TextField[#searchTF].onKeyTyped
	@FXML
	public void onSearchAction(KeyEvent event) {
		try {
			List<Patient> list = dao.getPatients(teamId, searchTF.getText() + "%");
			populateTable(list);
		} catch (Exception e) {
			UtilsGUI.showAlert((Stage)searchTF.getScene().getWindow(), AlertType.ERROR, "Greška", "Greška!");
		}
	}

	// Event Listener on TableView[#pacientsTableView].onMouseClicked
	@FXML
	public void onItemClicked(MouseEvent event) {
		try {
			String jmb = ((Patient) pacientsTableView.getSelectionModel().getSelectedItem()).getJmb();
			Patient p = new PatientDataAccessMySQL().getPatient(jmb);
			FXMLLoader loader = new FXMLLoader(getClass().getResource("Patient.fxml"));
			loader.setControllerFactory(c -> new PatientController(p));
			Parent root = loader.load();
			Scene scene = new Scene(root);
			Stage newStage = new Stage();
			newStage.setTitle("Pacijent");
			newStage.setScene(scene);
			newStage.show();
		} catch (Exception ex) {
			ex.printStackTrace();
			UtilsGUI.showAlert((Stage)searchTF.getScene().getWindow(), AlertType.ERROR, "Greška", "Greška!");
		}
		pacientsTableView.getSelectionModel().clearSelection();
	}
}
