package org.unibl.etf.bp.data;

import java.util.List;

import org.unibl.etf.bp.model.Illness;

public interface IllnessesDataAccess {
	
	List<Illness> getIllnesses(String name) throws Exception;
	boolean addIllness(Illness illness) throws Exception;
	boolean deleteIllness(Illness illness) throws Exception;
	
}
