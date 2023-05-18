package org.unibl.etf.bp.gui;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

import org.unibl.etf.bp.data.mysql.ExamDataAccessMySQL;
import org.unibl.etf.bp.model.Exam;
import org.unibl.etf.bp.model.Patient;

import javafx.fxml.FXML;

import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TableColumn;
import javafx.scene.control.CheckBox;

import javafx.scene.control.DatePicker;

import javafx.scene.control.TableView;

public class PatientController {
	@FXML
	private TextField jmbTF;
	@FXML
	private TextField firstNameTF;
	@FXML
	private TextField lastNameTF;
	@FXML
	private TextField emailTF;
	@FXML
	private TextField addressTF;
	@FXML
	private DatePicker dateOfBirthTF;
	@FXML
	private TextField medicalCardTF;
	@FXML
	private CheckBox insuranceCB;
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
	@SuppressWarnings("rawtypes")
	@FXML
	private TableView examsTV;

	private Patient p;

	private ToggleGroup group = new ToggleGroup();

	public PatientController(Patient p) {
		this.p = p;
	}

	@FXML
	public void initialize() {

		aRB.setToggleGroup(group);
		bRB.setToggleGroup(group);
		abRB.setToggleGroup(group);
		zeroRB.setToggleGroup(group);
		jmbTF.setText(p.getJmb());
		firstNameTF.setText(p.getFirstName());
		lastNameTF.setText(p.getLastName());
		emailTF.setText(p.getEmail());
		addressTF.setText(p.getAddress());
		DateTimeFormatter dtf = DateTimeFormatter.ISO_LOCAL_DATE;
		LocalDate dt = LocalDate.parse(p.getDateOfBirth().toString(), dtf);
		dateOfBirthTF.setValue(dt);
		medicalCardTF.setText(p.getMedicalCardNumber());
		insuranceCB.setSelected(p.isHasInsurance());
		if (p.isHasInsurance())
			insuranceNumberTF.setText(p.getInsuranceRegistrationNumber());
		marriageTF.setText(p.getMarriageStatus());
		phoneTF.setText(p.getPhoneNumber());
		
		if ("A".equals(p.getBloodType()))
			aRB.setSelected(true);
		else if ("B".equals(p.getBloodType()))
			bRB.setSelected(true);
		else if ("AB".equals(p.getBloodType()))
			abRB.setSelected(true);
		else
			zeroRB.setSelected(true);
		
		try {
			List<Exam> list = new ExamDataAccessMySQL().getExamByPatient(p.getJmb());
			populateTable(list);
		} catch (Exception ex) {
		}
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	private void populateTable(List<Exam> list) {
		((TableColumn) examsTV.getColumns().get(0)).setCellValueFactory(new PropertyValueFactory<>("diagonsisCode"));
		((TableColumn) examsTV.getColumns().get(1)).setCellValueFactory(new PropertyValueFactory<>("dateTimeOfExam"));
		((TableColumn) examsTV.getColumns().get(2)).setCellValueFactory(new PropertyValueFactory<>("examCode"));
		((TableColumn) examsTV.getColumns().get(3)).setCellValueFactory(new PropertyValueFactory<>("services"));
		((TableColumn) examsTV.getColumns().get(4)).setCellValueFactory(new PropertyValueFactory<>("totalCost"));
		examsTV.getItems().clear();
		list.stream().forEach(e -> examsTV.getItems().add(e));
	}

}
