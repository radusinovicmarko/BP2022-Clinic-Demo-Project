package org.unibl.etf.bp.model;

public class Exam {
	
	private int examId;
	private String diagonsisCode, dateTimeOfExam, examCode, examName, services;
	private double totalCost;
	
	public Exam(int examId, String diagonsisCode, String dateTimeOfExam, String examCode, String examName,
			String services, double totalCost) {
		super();
		this.examId = examId;
		this.diagonsisCode = diagonsisCode;
		this.dateTimeOfExam = dateTimeOfExam;
		this.examCode = examCode;
		this.examName = examName;
		this.services = services;
		this.totalCost = totalCost;
	}

	public int getExamId() {
		return examId;
	}

	public String getDiagonsisCode() {
		return diagonsisCode;
	}

	public String getDateTimeOfExam() {
		return dateTimeOfExam;
	}

	public String getExamCode() {
		return examCode;
	}
	
	public String getExamName() {
		return examName;
	}

	public String getServices() {
		return services;
	}

	public double getTotalCost() {
		return totalCost;
	}

}
