package com.example.designpattern.patterns.facade;

import com.example.designpattern.data.DatabaseConnectionFactory;
import com.example.designpattern.patterns.builder.Patient;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class PatientDAOImpl implements PatientDAO {
  private final DatabaseConnectionFactory factory;

  public PatientDAOImpl(DatabaseConnectionFactory factory) {
    this.factory = factory;
  }

  @Override
  public boolean save(Patient patient) {
    String sql = "INSERT INTO Patients (first_name, last_name) VALUES (?, ?) RETURNING patient_id";
    try (Connection conn = factory.newConnection();
        PreparedStatement stmt = conn.prepareStatement(sql)) {

      stmt.setString(1, patient.getFirstName());
      stmt.setString(2, patient.getLastName());

      try (ResultSet rs = stmt.executeQuery()) {
        if (rs.next()) {
          patient.setPatientId(rs.getInt(1));
          return true;
        }
      }
    } catch (Exception e) {
      e.printStackTrace();
    }
    return false;
  }

  @Override
  public Patient findById(int id) {
    String sql = "SELECT patient_id, first_name, last_name FROM Patients WHERE patient_id = ?";
    try (Connection conn = factory.newConnection();
        PreparedStatement stmt = conn.prepareStatement(sql)) {

      stmt.setInt(1, id);
      try (ResultSet rs = stmt.executeQuery()) {
        if (rs.next()) {
          Patient patient = new Patient();
          patient.setPatientId(rs.getInt("patient_id"));
          patient.setFirstName(rs.getString("first_name"));
          patient.setLastName(rs.getString("last_name"));
          return patient;
        }
      }
    } catch (Exception e) {
      e.printStackTrace();
    }
    return null;
  }

  @Override
  public java.util.List<Patient> findAll() {
    java.util.List<Patient> patients = new java.util.ArrayList<>();
    String sql = "SELECT patient_id, first_name, last_name FROM Patients ORDER BY patient_id DESC LIMIT 10";
    try (Connection conn = factory.newConnection();
        PreparedStatement stmt = conn.prepareStatement(sql);
        ResultSet rs = stmt.executeQuery()) {

      while (rs.next()) {
        Patient patient = new Patient();
        patient.setPatientId(rs.getInt("patient_id"));
        patient.setFirstName(rs.getString("first_name"));
        patient.setLastName(rs.getString("last_name"));
        patients.add(patient);
      }
    } catch (Exception e) {
      e.printStackTrace();
    }
    return patients;
  }
}
