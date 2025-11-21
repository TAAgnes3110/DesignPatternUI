package com.example.designpattern.patterns.facade;

import com.example.designpattern.data.DatabaseConnectionFactory;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class DoctorDAOImpl implements DoctorDAO {
  private final DatabaseConnectionFactory dbFactory;

  public DoctorDAOImpl(DatabaseConnectionFactory dbFactory) {
    this.dbFactory = dbFactory;
  }

  @Override
  public List<Doctor> findAll() {
    List<Doctor> doctors = new ArrayList<>();
    String sql = "SELECT doctor_id, first_name, last_name, specialty FROM Doctors";

    try (Connection conn = dbFactory.newConnection();
        PreparedStatement stmt = conn.prepareStatement(sql);
        ResultSet rs = stmt.executeQuery()) {

      while (rs.next()) {
        Doctor doctor = new Doctor();
        doctor.setDoctorId(rs.getInt("doctor_id"));
        doctor.setFirstName(rs.getString("first_name"));
        doctor.setLastName(rs.getString("last_name"));
        doctor.setSpecialty(rs.getString("specialty"));
        doctors.add(doctor);
      }
    } catch (Exception e) {
      e.printStackTrace();
    }
    return doctors;
  }

  @Override
  public Doctor findById(int id) {
    String sql = "SELECT doctor_id, first_name, last_name, specialty FROM Doctors WHERE doctor_id = ?";
    try (Connection conn = dbFactory.newConnection();
        PreparedStatement stmt = conn.prepareStatement(sql)) {

      stmt.setInt(1, id);
      try (ResultSet rs = stmt.executeQuery()) {
        if (rs.next()) {
          return new Doctor(
              rs.getInt("doctor_id"),
              rs.getString("first_name"),
              rs.getString("last_name"),
              rs.getString("specialty"));
        }
      }
    } catch (Exception e) {
      e.printStackTrace();
    }
    return null;
  }
}
