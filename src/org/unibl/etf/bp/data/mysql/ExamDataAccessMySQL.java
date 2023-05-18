package org.unibl.etf.bp.data.mysql;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.util.ArrayList;
import java.util.List;

import org.unibl.etf.bp.data.ExamDataAccess;
import org.unibl.etf.bp.model.Exam;

public class ExamDataAccessMySQL implements ExamDataAccess {

	@Override
	public boolean logExam(String diagnoseCode, String examCode, String jmb, int teamId, List<String> services) throws SQLException {
		Connection conn = null;
		CallableStatement cs = null;

		String callStatement = "{call evidentiraj_pregled(?, ?, ?, ?, ?)}";

		try {
			conn = ConnectionPool.getInstance().checkOut();
			cs = conn.prepareCall(callStatement);
			cs.setString(1, diagnoseCode);
			cs.setString(2, examCode);
			cs.setString(3, jmb);
			cs.setInt(4, teamId);
			cs.registerOutParameter(5, Types.INTEGER);

			cs.executeUpdate();
			
			int examId = cs.getInt(5);
			
			for (String serviceCode : services)
				logService(serviceCode, examId, jmb);
			
			return logReceipt(examId, jmb);
			
		} catch (SQLException  e) {
			throw e;
		} finally {
			ConnectionPool.getInstance().checkIn(conn);
			UtilsMySQL.close(cs);
		}
	}

	@Override
	public boolean logService(String serviceCode, int examId, String jmb) throws SQLException {
		Connection conn = null;
		CallableStatement cs = null;

		String callStatement = "{call evidentiraj_uslugu(?, ?, ?)}";

		try {
			conn = ConnectionPool.getInstance().checkOut();
			cs = conn.prepareCall(callStatement);
			cs.setString(1, serviceCode);
			cs.setInt(2, examId);
			cs.setString(3, jmb);

			return cs.executeUpdate() == 1;
			
		} catch (SQLException  e) {
			throw e;
		} finally {
			ConnectionPool.getInstance().checkIn(conn);
			UtilsMySQL.close(cs);
		}
	}

	@Override
	public boolean logReceipt(int examId, String jmb) throws SQLException {
		Connection conn = null;
		CallableStatement cs = null;

		String callStatement = "{call evidentiraj_racun(?, ?)}";

		try {
			conn = ConnectionPool.getInstance().checkOut();
			cs = conn.prepareCall(callStatement);
			cs.setInt(1, examId);
			cs.setString(2, jmb);

			return cs.executeUpdate() == 1;
			
		} catch (SQLException  e) {
			throw e;
		} finally {
			ConnectionPool.getInstance().checkIn(conn);
			UtilsMySQL.close(cs);
		}
	}

	@Override
	public List<Exam> getExamByPatient(String jmb) throws SQLException {
		List<Exam> list = new ArrayList<>();
		Connection conn = null;
		CallableStatement cs = null;
		ResultSet rs = null;

		String callStatement = "{call vrati_preglede_po_pacijentu(?)}";

		try {
			conn = ConnectionPool.getInstance().checkOut();
			cs = conn.prepareCall(callStatement);
			cs.setString(1, jmb);
			
			rs = cs.executeQuery();
			
			while (rs.next()) 
				list.add(new Exam(rs.getInt(1), rs.getString(2), rs.getTimestamp(3).toString(), rs.getString(4), rs.getString(5), rs.getString(6), rs.getDouble(7)));
			
			return list;
		} catch (SQLException  e) {
			throw e;
		} finally {
			ConnectionPool.getInstance().checkIn(conn);
			UtilsMySQL.close(cs);
		}
	}

}
