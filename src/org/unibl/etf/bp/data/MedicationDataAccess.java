package org.unibl.etf.bp.data;

import java.util.List;

import org.unibl.etf.bp.model.Medication;


public interface MedicationDataAccess {

	List<Medication> getMedications(String name) throws Exception;
	boolean addMedication(Medication medication) throws Exception;
	boolean deleteMedication(Medication medication) throws Exception;
	
}
