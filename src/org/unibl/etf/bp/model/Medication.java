package org.unibl.etf.bp.model;

public class Medication {

	private int id;
	private String genericName, factoryName;
	
	public Medication(int id, String genericName, String factoryName) {
		super();
		this.id = id;
		this.genericName = genericName;
		this.factoryName = factoryName;
	}

	public int getId() {
		return id;
	}

	public String getGenericName() {
		return genericName;
	}

	public String getFactoryName() {
		return factoryName;
	}
	
}
