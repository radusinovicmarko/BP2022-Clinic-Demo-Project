package org.unibl.etf.bp.model;

public class Illness {
	
	private int id;
	private String name, code;

	public Illness(int id, String name, String code) {
		super();
		this.id = id;
		this.name = name;
		this.code = code;
	}

	public String getName() {
		return name;
	}

	public String getCode() {
		return code;
	}
	
	public int getId() {
		return id;
	}

}
