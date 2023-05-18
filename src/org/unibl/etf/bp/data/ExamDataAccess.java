package org.unibl.etf.bp.data;

import java.util.List;

import org.unibl.etf.bp.model.Exam;

public interface ExamDataAccess {
	
	List<Exam> getExamByPatient(String jmb) throws Exception;
	boolean logExam(String diagnoseCode, String examCode, String jmb, int teamId, List<String> services) throws Exception;
	boolean logService(String serviceCode, int examId, String jmb) throws Exception;
	boolean logReceipt(int examId, String jmb) throws Exception;
}
