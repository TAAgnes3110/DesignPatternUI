package com.example.designpattern.data;

import com.example.designpattern.patterns.builder.Patient;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class PatientDatabaseHelper {
  private final DatabaseConnectionFactory factory;

  public PatientDatabaseHelper() {
    this.factory = DatabaseConnectionFactory.fromConfig();
  }

  public String addPatient(Patient p) throws Exception {
    String sql = "INSERT INTO Patients (first_name, last_name, gender, date_of_birth, contact_number, email, address, medical_history) VALUES (?, ?, ?, ?, ?, ?, ?, ?) RETURNING patient_id";

    try (Connection conn = factory.newConnection();
        PreparedStatement stmt = conn.prepareStatement(sql)) {

      stmt.setString(1, p.getFirstName());
      stmt.setString(2, p.getLastName());
      stmt.setString(3, p.getGender());
      stmt.setDate(4, new Date(p.getDateOfBirth().getTime()));
      stmt.setString(5, p.getContactNumber());
      stmt.setString(6, p.getEmail());
      stmt.setString(7, p.getAddress());
      stmt.setString(8, p.getMedicalHistory());

      try (ResultSet rs = stmt.executeQuery()) {
        if (rs.next()) {
          return "Đã insert thành công ID: " + rs.getInt(1);
        }
      }
    }
    return "Lỗi: Không lấy được ID";
  }

  public String getRecentPatients() throws Exception {
    StringBuilder sb = new StringBuilder();
    String sql = "SELECT patient_id, first_name, last_name, contact_number FROM Patients ORDER BY patient_id DESC LIMIT 5";

    try (Connection conn = factory.newConnection();
        Statement stmt = conn.createStatement();
        ResultSet rs = stmt.executeQuery(sql)) {

      while (rs.next()) {
        sb.append(String.format("#%d %s %s (%s)\n",
            rs.getInt("patient_id"),
            rs.getString("first_name"),
            rs.getString("last_name"),
            rs.getString("contact_number")));
      }
    }

    return sb.length() > 0 ? sb.toString() : "Chưa có dữ liệu.";
  }
}
