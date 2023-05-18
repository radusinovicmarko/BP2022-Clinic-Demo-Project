package org.unibl.etf.bp.data;

import java.util.List;

import org.unibl.etf.bp.model.Patient;

public interface PatientDataAccess {
	
	Patient getPatient(String jmb) throws Exception;
	List<Patient> getPatients(int teamId) throws Exception;
	List<Patient> getPatients(int teamId, String name) throws Exception;
	boolean addPatient(Patient patient) throws Exception;
	boolean deletePatient(Patient patient) throws Exception;
	boolean registerPatient(Patient patient, String place, int teamId) throws Exception;
	boolean deregisterPatient(Patient patient, int teamId) throws Exception;

}
