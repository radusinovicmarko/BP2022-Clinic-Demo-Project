package org.unibl.etf.bp.data.mysql;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import org.unibl.etf.bp.data.TypeOfExamDataAccess;
import org.unibl.etf.bp.model.TypeOfExam;

public class TypeOfExamDataAccessMySQL implements TypeOfExamDataAccess {

	@Override
	public List<TypeOfExam> getTypesOfExam(String name) throws Exception {
		List<TypeOfExam> list = new ArrayList<>();
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		
		String query = "select * from vrsta_pregleda";
		if (name != null)
			query += " where NazivPregleda like ?";
		
		try {
			conn = ConnectionPool.getInstance().checkOut();
			ps = conn.prepareStatement(query);
			
			if (name != null)
				ps.setString(1, name);
			
			rs = ps.executeQuery();
			while (rs.next()) 
				list.add(new TypeOfExam(rs.getInt(1), rs.getString(2), rs.getString(3), rs.getDouble(4), rs.getDouble(5)));
		} finally {
			ConnectionPool.getInstance().checkIn(conn);
			UtilsMySQL.close(ps, rs);
		}
		return list;
	}

	@Override
	public boolean addTypeOfExam(TypeOfExam type) throws Exception {
		boolean success = false;
		Connection conn = null;
		PreparedStatement ps = null;
		
		String query = "insert into vrsta_pregleda(SifraPregleda, NazivPregleda, TrenutnaCijenaPregledaOsiguranje, TrenutnaCijenaPregledaBezOsiguranja)"
				+ " values (?, ?, ?, ?)";
		
		try {
			conn = ConnectionPool.getInstance().checkOut();
			ps = conn.prepareStatement(query);
			ps.setString(1, type.getCode());
			ps.setString(2, type.getName());
			ps.setDouble(3, type.getPriceInsurance());
			ps.setDouble(4, type.getPriceNoInsurance());
			
			success = ps.executeUpdate() == 1;
		} finally {
			ConnectionPool.getInstance().checkIn(conn);
			UtilsMySQL.close(ps);
		}
		return success;
	}

	@Override
	public boolean deleteTypeOfExam(TypeOfExam type) throws Exception {
		boolean success = false;
		Connection conn = null;
		PreparedStatement ps = null;
		
		String query = "delete from vrsta_pregleda where IdVrstePregleda=?";
		
		try {
			conn = ConnectionPool.getInstance().checkOut();
			ps = conn.prepareStatement(query);
			ps.setInt(1, type.getId());
			
			success = ps.executeUpdate() == 1;
		} finally {
			ConnectionPool.getInstance().checkIn(conn);
			UtilsMySQL.close(ps);
		}
		return success;
	}

}
