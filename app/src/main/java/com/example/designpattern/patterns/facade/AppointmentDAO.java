package com.example.designpattern.patterns.facade;

public interface AppointmentDAO {
  boolean save(Appointment appointment);

  boolean save(Appointment appointment, java.sql.Connection conn);

  Appointment findById(int id);
}
