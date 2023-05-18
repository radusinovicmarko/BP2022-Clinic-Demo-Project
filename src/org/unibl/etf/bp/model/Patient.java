package org.unibl.etf.bp.model;

import java.util.Date;

public class Patient extends Person {
	private String medicalCardNumber, insuranceRegistrationNumber, bloodType, marriageStatus, phoneNumber;
	private boolean hasInsurance;
	
	public Patient(int id, String jmb, String firstName, String lastName, String email, String address,
			Date dateOfBirth, String medicalCardNumber, String insuranceRegistrationNumber, String bloodType,
			String marriageStatus, String phoneNumber, boolean hasInsurance) {
		super(id, jmb, firstName, lastName, email, address, dateOfBirth);
		this.medicalCardNumber = medicalCardNumber;
		this.insuranceRegistrationNumber = insuranceRegistrationNumber;
		this.bloodType = bloodType;
		this.marriageStatus = marriageStatus;
		this.phoneNumber = phoneNumber;
		this.hasInsurance = hasInsurance;
	}

	public String getMedicalCardNumber() {
		return medicalCardNumber;
	}

	public String getInsuranceRegistrationNumber() {
		return insuranceRegistrationNumber;
	}

	public String getBloodType() {
		return bloodType;
	}

	public String getMarriageStatus() {
		return marriageStatus;
	}

	public String getPhoneNumber() {
		return phoneNumber;
	}

	public boolean isHasInsurance() {
		return hasInsurance;
	}

}
