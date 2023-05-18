package org.unibl.etf.bp.data.mysql;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.unibl.etf.bp.data.MedicationDataAccess;
import org.unibl.etf.bp.model.Medication;

public class MedicationDataAccessMySQL implements MedicationDataAccess {

	@Override
	public List<Medication> getMedications(String name) throws SQLException {
		List<Medication> list = new ArrayList<>();
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		
		String query = "select * from lijek";
		if (name != null)
			query += " where GenerickiNazivLijeka like ?";
		
		try {
			conn = ConnectionPool.getInstance().checkOut();
			ps = conn.prepareStatement(query);
			
			if (name != null)
				ps.setString(1, name);
			
			rs = ps.executeQuery();
			while (rs.next()) 
				list.add(new Medication(rs.getInt(1), rs.getString(2), rs.getString(3)));
		} finally {
			ConnectionPool.getInstance().checkIn(conn);
			UtilsMySQL.close(ps, rs);
		}
		return list;
	}

	@Override
	public boolean addMedication(Medication medication) throws Exception {
		boolean success = false;
		Connection conn = null;
		PreparedStatement ps = null;
		
		String query = "insert into lijek(GenerickiNazivLijeka, TvornickiNazivLijeka) values (?, ?)";
		
		try {
			conn = ConnectionPool.getInstance().checkOut();
			ps = conn.prepareStatement(query);
			ps.setString(1, medication.getGenericName());
			ps.setString(2, medication.getFactoryName());
			
			success = ps.executeUpdate() == 1;
		} finally {
			ConnectionPool.getInstance().checkIn(conn);
			UtilsMySQL.close(ps);
		}
		return success;
	}

	@Override
	public boolean deleteMedication(Medication medication) throws Exception {
		boolean success = false;
		Connection conn = null;
		PreparedStatement ps = null;
		
		String query = "delete from lijek where IdLijeka=?";
		
		try {
			conn = ConnectionPool.getInstance().checkOut();
			ps = conn.prepareStatement(query);
			ps.setInt(1, medication.getId());
			
			success = ps.executeUpdate() == 1;
		} finally {
			ConnectionPool.getInstance().checkIn(conn);
			UtilsMySQL.close(ps);
		}
		return success;
	}

}
