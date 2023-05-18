package org.unibl.etf.bp.data.mysql;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.unibl.etf.bp.data.IllnessesDataAccess;
import org.unibl.etf.bp.model.Illness;

public class IllnessDataAccessMySQL implements IllnessesDataAccess {

	@Override
	public List<Illness> getIllnesses(String name) throws SQLException {
		List<Illness> list = new ArrayList<>();
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		
		String query = "select * from bolest";
		if (name != null)
			query += " where NazivBolesti like ?";
		
		try {
			conn = ConnectionPool.getInstance().checkOut();
			ps = conn.prepareStatement(query);
			
			if (name != null)
				ps.setString(1, name);
			
			rs = ps.executeQuery();
			while (rs.next()) 
				list.add(new Illness(rs.getInt(1), rs.getString(2), rs.getString(3)));
		} finally {
			ConnectionPool.getInstance().checkIn(conn);
			UtilsMySQL.close(ps, rs);
		}
		return list;
	}

	@Override
	public boolean addIllness(Illness illness) throws SQLException {
		boolean success = false;
		Connection conn = null;
		PreparedStatement ps = null;
		
		String query = "insert into bolest(NazivBolesti, SifraBolesti) values (?, ?)";
		
		try {
			conn = ConnectionPool.getInstance().checkOut();
			ps = conn.prepareStatement(query);
			ps.setString(1, illness.getName());
			ps.setString(2, illness.getCode());
			
			success = ps.executeUpdate() == 1;
		} finally {
			ConnectionPool.getInstance().checkIn(conn);
			UtilsMySQL.close(ps);
		}
		return success;
	}

	@Override
	public boolean deleteIllness(Illness illness) throws SQLException {
		boolean success = false;
		Connection conn = null;
		PreparedStatement ps = null;
		
		String query = "delete from bolest where IdBolesti=?";
		
		try {
			conn = ConnectionPool.getInstance().checkOut();
			ps = conn.prepareStatement(query);
			ps.setInt(1, illness.getId());
			
			success = ps.executeUpdate() == 1;
		} finally {
			ConnectionPool.getInstance().checkIn(conn);
			UtilsMySQL.close(ps);
		}
		return success;
	}

}
