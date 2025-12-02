package com.example.designpattern.patterns.facade;

import com.example.designpattern.data.DatabaseConnectionFactory;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class AppointmentServiceDAOImpl implements AppointmentServiceDAO {
  private final DatabaseConnectionFactory dbFactory;

  public AppointmentServiceDAOImpl(DatabaseConnectionFactory dbFactory) {
    this.dbFactory = dbFactory;
  }

  @Override
  public void save(AppointmentService appointmentService) {
    String sql = "INSERT INTO Appointment_Services (appointment_id, service_id, quantity, unit_price) VALUES (?, ?, ?, ?) RETURNING app_service_id";
    try (Connection conn = dbFactory.newConnection();
        PreparedStatement stmt = conn.prepareStatement(sql)) {

      stmt.setInt(1, appointmentService.getAppointmentId());
      stmt.setInt(2, appointmentService.getServiceId());
      stmt.setInt(3, appointmentService.getQuantity());
      stmt.setBigDecimal(4, appointmentService.getUnitPrice());

      try (ResultSet rs = stmt.executeQuery()) {
        if (rs.next()) {
          appointmentService.setAppServiceId(rs.getInt(1));
        }
      }
    } catch (SQLException e) {
      e.printStackTrace();
    }
  }
}
