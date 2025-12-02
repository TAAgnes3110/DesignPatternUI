package com.example.designpattern.patterns.facade;

import com.example.designpattern.data.DatabaseConnectionFactory;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ServiceDAOImpl implements ServiceDAO {
  private final DatabaseConnectionFactory dbFactory;

  public ServiceDAOImpl(DatabaseConnectionFactory dbFactory) {
    this.dbFactory = dbFactory;
  }

  @Override
  public List<Service> findAll() {
    List<Service> services = new ArrayList<>();
    String sql = "SELECT * FROM Services";
    try (Connection conn = dbFactory.newConnection();
        PreparedStatement stmt = conn.prepareStatement(sql);
        ResultSet rs = stmt.executeQuery()) {

      while (rs.next()) {
        Service service = new Service(
            rs.getInt("service_id"),
            rs.getString("service_name"),
            rs.getString("description"),
            rs.getBigDecimal("price"));
        services.add(service);
      }
    } catch (SQLException e) {
      e.printStackTrace();
    }
    return services;
  }

  @Override
  public Service findById(int id) {
    Service service = null;
    String sql = "SELECT * FROM Services WHERE service_id = ?";
    try (Connection conn = dbFactory.newConnection();
        PreparedStatement stmt = conn.prepareStatement(sql)) {

      stmt.setInt(1, id);
      try (ResultSet rs = stmt.executeQuery()) {
        if (rs.next()) {
          service = new Service(
              rs.getInt("service_id"),
              rs.getString("service_name"),
              rs.getString("description"),
              rs.getBigDecimal("price"));
        }
      }
    } catch (SQLException e) {
      e.printStackTrace();
    }
    return service;
  }

  @Override
  public void save(Service service) {
    // Implement if needed
  }
}
