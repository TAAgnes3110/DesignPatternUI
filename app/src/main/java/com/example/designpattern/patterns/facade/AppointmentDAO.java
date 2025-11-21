package com.example.designpattern.patterns.facade;

public interface AppointmentDAO {
    boolean save(Appointment appointment);
    Appointment findById(int id);
}
