package org.unibl.etf.bp.data;

import java.util.List;

import org.unibl.etf.bp.model.TypeOfExam;


public interface TypeOfExamDataAccess {

	List<TypeOfExam> getTypesOfExam(String name) throws Exception;
	boolean addTypeOfExam(TypeOfExam type) throws Exception;
	boolean deleteTypeOfExam(TypeOfExam type) throws Exception;
	
}
