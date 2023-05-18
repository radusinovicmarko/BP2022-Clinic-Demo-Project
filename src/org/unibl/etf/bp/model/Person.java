package org.unibl.etf.bp.model;

import java.util.Date;

public abstract class Person {

	protected int id;
	protected String jmb, firstName, lastName, email, address;
	protected Date dateOfBirth;
	
	public Person(int id, String jmb, String firstName, String lastName, String email, String address,
			Date dateOfBirth) {
		super();
		this.id = id;
		this.jmb = jmb;
		this.firstName = firstName;
		this.lastName = lastName;
		this.email = email;
		this.address = address;
		this.dateOfBirth = dateOfBirth;
	}

	public int getId() {
		return id;
	}

	public String getJmb() {
		return jmb;
	}

	public String getFirstName() {
		return firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public String getEmail() {
		return email;
	}

	public String getAddress() {
		return address;
	}

	public Date getDateOfBirth() {
		return dateOfBirth;
	}

}
