package com.example.designpattern.patterns.facade;

import com.example.designpattern.data.DatabaseConnectionFactory;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class AppointmentDAOImpl implements AppointmentDAO {
  private final DatabaseConnectionFactory dbFactory;

  public AppointmentDAOImpl(DatabaseConnectionFactory dbFactory) {
    this.dbFactory = dbFactory;
  }

  @Override
  public boolean save(Appointment appointment) {
    try (Connection conn = dbFactory.newConnection()) {
      return save(appointment, conn);
    } catch (SQLException e) {
      e.printStackTrace();
      return false;
    }
  }

  @Override
  public boolean save(Appointment appointment, Connection conn) {
    String sql = "INSERT INTO Appointments (patient_id, doctor_id, appointment_date, appointment_time, purpose) VALUES (?, ?, ?, ?, ?) RETURNING appointment_id";
    try (PreparedStatement stmt = conn.prepareStatement(sql)) {
      stmt.setInt(1, appointment.getPatientId());
      stmt.setInt(2, appointment.getDoctorId());

      if (appointment.getAppointmentDate() != null) {
        stmt.setDate(3, new java.sql.Date(appointment.getAppointmentDate().getTime()));
      } else {
        stmt.setDate(3, null);
      }

      stmt.setTime(4, appointment.getAppointmentTime());
      stmt.setString(5, appointment.getPurpose());

      try (ResultSet rs = stmt.executeQuery()) {
        if (rs.next()) {
          int newId = rs.getInt(1);
          appointment.setAppointmentId(newId);
          return true;
        }
      }
    } catch (SQLException e) {
      e.printStackTrace();
    }
    return false;
  }

  @Override
  public Appointment findById(int id) {
    String sql = "SELECT * FROM Appointments WHERE appointment_id = ?";
    try (Connection conn = dbFactory.newConnection();
        PreparedStatement stmt = conn.prepareStatement(sql)) {
      stmt.setInt(1, id);
      try (ResultSet rs = stmt.executeQuery()) {
        if (rs.next()) {
          Appointment appointment = new Appointment();
          appointment.setAppointmentId(rs.getInt("appointment_id"));
          appointment.setPatientId(rs.getInt("patient_id"));
          appointment.setDoctorId(rs.getInt("doctor_id"));
          appointment.setAppointmentDate(rs.getDate("appointment_date"));
          appointment.setAppointmentTime(rs.getTime("appointment_time"));
          appointment.setPurpose(rs.getString("purpose"));
          appointment.setStatus(rs.getString("status"));
          return appointment;
        }
      }
    } catch (SQLException e) {
      e.printStackTrace();
    }
    return null;
  }
}
