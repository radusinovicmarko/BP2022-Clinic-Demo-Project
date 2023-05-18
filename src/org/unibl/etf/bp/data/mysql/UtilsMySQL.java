package org.unibl.etf.bp.data.mysql;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Types;

public class UtilsMySQL {
	
	public static String getAmbulanceInfo(String username) throws SQLException {
		String res = "";
		Connection conn = null;
		CallableStatement cs = null;

		String callStatement = "{call vrati_info_o_timu_i_ambulanti(?, ?, ?)}";

		try {
			conn = ConnectionPool.getInstance().checkOut();
			cs = conn.prepareCall(callStatement);
			cs.setString(1, username);
			cs.registerOutParameter(2, Types.INTEGER);
			cs.registerOutParameter(3, Types.VARCHAR);

			cs.executeUpdate();

			res = cs.getInt(2) + "#" + cs.getString(3);
			
		} catch (SQLException  e) {
			throw e;
		} finally {
			ConnectionPool.getInstance().checkIn(conn);
			close(cs);
		}
		return res;
	}
	
	public static String login(String username, String password) throws Exception {
		Connection conn = null;
		CallableStatement cs = null;
		boolean loginSuccessful = false, typeOfUser = false;

		String callStatement = "{call prijava(?, ?, ?, ?)}";

		try {
			conn = ConnectionPool.getInstance().checkOut();
			cs = conn.prepareCall(callStatement);
			cs.setString(1, username);
			cs.setString(2, password);
			cs.registerOutParameter(3, Types.BOOLEAN);
			cs.registerOutParameter(4, Types.BOOLEAN);

			cs.executeUpdate();

			loginSuccessful = cs.getBoolean(3);
			typeOfUser = cs.getBoolean(4);
			
			return loginSuccessful + "#" + typeOfUser;

			
		} catch (SQLException e) {
			throw e;
		} finally {
			ConnectionPool.getInstance().checkIn(conn);
			close(cs);
		}
	}

	public static void close(Connection conn) throws SQLException {
		if (conn != null) {
			conn.close();
		}
	}

	public static void close(Statement s) throws SQLException {
		if (s != null) {
			s.close();
		}
	}

	public static void close(ResultSet rs) throws SQLException {
		if (rs != null) {
			rs.close();
		}
	}

	public static void close(Connection conn, Statement s) throws SQLException {
		close(s);
		close(conn);
	}

	public static void close(Connection conn, ResultSet rs) throws SQLException {
		close(rs);
		close(conn);
	}

	public static void close(Statement s, ResultSet rs) throws SQLException {
		close(rs);
		close(s);
	}

	public static void close(Connection conn, Statement s, ResultSet rs) throws SQLException {
		close(rs);
		close(s);
		close(conn);
	}
	
}
