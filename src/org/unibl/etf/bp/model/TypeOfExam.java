package org.unibl.etf.bp.model;

public class TypeOfExam {
	
	private int id;
	private String code, name;
	private double priceInsurance, priceNoInsurance;
	
	public TypeOfExam(int id, String code, String name, double priceInsurance, double priceNoInsurance) {
		super();
		this.id = id;
		this.code = code;
		this.name = name;
		this.priceInsurance = priceInsurance;
		this.priceNoInsurance = priceNoInsurance;
	}

	public int getId() {
		return id;
	}

	public String getCode() {
		return code;
	}

	public String getName() {
		return name;
	}

	public double getPriceInsurance() {
		return priceInsurance;
	}

	public double getPriceNoInsurance() {
		return priceNoInsurance;
	}

}
