package com.example.designpattern.patterns.facade;

public interface AppointmentServiceDAO {
  void save(AppointmentService appointmentService);

  void save(AppointmentService appointmentService, java.sql.Connection conn);
}
