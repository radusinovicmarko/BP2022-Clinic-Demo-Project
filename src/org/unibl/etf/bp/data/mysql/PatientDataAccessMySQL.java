package org.unibl.etf.bp.data.mysql;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.unibl.etf.bp.data.PatientDataAccess;
import org.unibl.etf.bp.model.Patient;

public class PatientDataAccessMySQL implements PatientDataAccess {

	@Override
	public List<Patient> getPatients(int teamId) throws Exception {
		return getPatients(teamId, "%");
	}

	@Override
	public boolean addPatient(Patient patient) throws Exception {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean deletePatient(Patient patient) throws Exception {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean registerPatient(Patient patient, String place, int teamId) throws Exception {
		boolean success = false;
		Connection conn = null;
		CallableStatement cs = null;

		String callStatement = "{call registracija(?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)}";

		try {
			conn = ConnectionPool.getInstance().checkOut();
			cs = conn.prepareCall(callStatement);
			cs.setString(1, patient.getJmb());
			cs.setString(2, patient.getFirstName());
			cs.setString(3, patient.getLastName());
			cs.setDate(4, new java.sql.Date(patient.getDateOfBirth().getTime()));
			cs.setString(5, patient.getEmail());
			cs.setString(6, patient.getAddress());
			cs.setString(7, place);
			cs.setString(8, patient.getMedicalCardNumber());
			cs.setBoolean(9, patient.isHasInsurance());
			cs.setString(10, patient.getInsuranceRegistrationNumber());
			cs.setString(11, patient.getBloodType());
			cs.setString(12, patient.getMarriageStatus());
			cs.setString(13, patient.getPhoneNumber());
			cs.setInt(14, teamId);

			success = cs.executeUpdate() == 1;
		} catch (SQLException  e) {
			throw e;
		} finally {
			ConnectionPool.getInstance().checkIn(conn);
			UtilsMySQL.close(cs);
		}
		return success;
	}

	@Override
	public boolean deregisterPatient(Patient patient, int teamId) throws Exception {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public List<Patient> getPatients(int teamId, String name) throws Exception {
		List<Patient> list = new ArrayList<>();
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		
		String query = "select * from pacijenti_skraceno ps";
		if (teamId != -1)
			query += " inner join registrovanje r"
					+ " on r.PACIJENT_OSOBA_IdOsobe=ps.IdOsobe"
					+ " where r.TrenutnoRegistrovan=1 and r.TIM_PORODICNE_MEDICINE_SifraTima=? and ps.Prezime like ?";
		
		try {
			conn = ConnectionPool.getInstance().checkOut();
			ps = conn.prepareStatement(query);
			
			if (teamId != -1) {
				ps.setInt(1, teamId);
				ps.setString(2, name);
			}
			
			rs = ps.executeQuery();
			while (rs.next()) 
				list.add(new Patient(rs.getInt(1), rs.getString(2), rs.getString(3), rs.getString(4), rs.getString(6),
						rs.getString(7), rs.getDate(5), rs.getString(8), null, null, null, null, rs.getBoolean(9)));
		} finally {
			ConnectionPool.getInstance().checkIn(conn);
			UtilsMySQL.close(ps, rs);
		}
		return list;
	}

	@Override
	public Patient getPatient(String jmb) throws Exception {
		
		Patient p = null;
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		
		String query = "select * from osoba o inner join pacijent p"
				+ " on p.OSOBA_IdOsobe=o.IdOsobe where o.JMB=?";
		
		try {
			conn = ConnectionPool.getInstance().checkOut();
			ps = conn.prepareStatement(query);
			
			ps.setString(1, jmb);
			
			rs = ps.executeQuery();
			
			while (rs.next())
				p = new Patient(rs.getInt(1), rs.getString(2), rs.getString(3), rs.getString(4), rs.getString(6), rs.getString(7), rs.getDate(5), 
						rs.getString(9), rs.getString(11), rs.getString(12), rs.getString(13), rs.getString(14), rs.getBoolean(10));
			return p;
				
		} finally {
			ConnectionPool.getInstance().checkIn(conn);
			UtilsMySQL.close(ps, rs);
		}
	}

}
