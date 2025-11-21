package com.example.designpattern.patterns.facade;

import com.example.designpattern.data.DatabaseConnectionFactory;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.math.BigDecimal;

public class BillingDAOImpl implements BillingDAO {
  private final DatabaseConnectionFactory dbFactory;

  public BillingDAOImpl(DatabaseConnectionFactory dbFactory) {
    this.dbFactory = dbFactory;
  }

  @Override
  public boolean save(Billing billing) {
    String sql = "INSERT INTO Billing (patient_id, total_amount) VALUES (?, ?) RETURNING bill_id";
    try (Connection conn = dbFactory.newConnection();
        PreparedStatement stmt = conn.prepareStatement(sql)) {
      stmt.setInt(1, billing.getPatientId());
      stmt.setBigDecimal(2, billing.getTotalAmount());

      try (ResultSet rs = stmt.executeQuery()) {
        if (rs.next()) {
          int newId = rs.getInt(1);
          billing.setBillId(newId);
          return true;
        }
      }
    } catch (SQLException e) {
      e.printStackTrace();
    }
    return false;
  }

  @Override
  public Billing findById(int id) {
    String sql = "SELECT * FROM Billing WHERE bill_id = ?";
    try (Connection conn = dbFactory.newConnection();
        PreparedStatement stmt = conn.prepareStatement(sql)) {
      stmt.setInt(1, id);
      try (ResultSet rs = stmt.executeQuery()) {
        if (rs.next()) {
          return new Billing(
              rs.getInt("bill_id"),
              rs.getInt("patient_id"),
              rs.getBigDecimal("total_amount"));
        }
      }
    } catch (SQLException e) {
      e.printStackTrace();
    }
    return null;
  }
}
