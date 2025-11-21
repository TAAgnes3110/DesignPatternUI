package com.example.designpattern.patterns.facade;

import java.sql.Time;
import java.sql.Timestamp;
import java.util.Date;

public class Appointment {
  private int appointmentId;
  private int patientId;
  private int doctorId;
  private Date appointmentDate;
  private Time appointmentTime;
  private String purpose;
  private String status;
  private Timestamp createdAt;

  public Appointment() {

  }

  public Appointment(int appointmentId, int patientId, int doctorId, Date appointmentDate, Time appointmentTime) {
    this.appointmentId = appointmentId;
    this.patientId = patientId;
    this.doctorId = doctorId;
    this.appointmentDate = appointmentDate;
    this.appointmentTime = appointmentTime;
    this.purpose = "General Checkup";
    this.status = "Scheduled";
    this.createdAt = new Timestamp(System.currentTimeMillis());
  }

  public Appointment(int appointmentId, int patientId, int doctorId, Date appointmentDate, Time appointmentTime,
      String purpose, String status) {
    this.appointmentId = appointmentId;
    this.patientId = patientId;
    this.doctorId = doctorId;
    this.appointmentDate = appointmentDate;
    this.appointmentTime = appointmentTime;
    this.purpose = purpose;
    this.status = status;
    this.createdAt = new Timestamp(System.currentTimeMillis());
  }

  public int getAppointmentId() {
    return appointmentId;
  }

  public int getPatientId() {
    return patientId;
  }

  public int getDoctorId() {
    return doctorId;
  }

  public Date getAppointmentDate() {
    return appointmentDate;
  }

  public Time getAppointmentTime() {
    return appointmentTime;
  }

  public String getPurpose() {
    return purpose;
  }

  public String getStatus() {
    return status;
  }

  public Timestamp getCreatedAt() {
    return createdAt;
  }

  public void setAppointmentId(int appointmentId) {
    this.appointmentId = appointmentId;
  }

  public void setPatientId(int patientId) {
    this.patientId = patientId;
  }

  public void setDoctorId(int doctorId) {
    this.doctorId = doctorId;
  }

  public void setAppointmentDate(Date appointmentDate) {
    this.appointmentDate = appointmentDate;
  }

  public void setAppointmentTime(Time appointmentTime) {
    this.appointmentTime = appointmentTime;
  }

  public void setPurpose(String purpose) {
    this.purpose = purpose;
  }

  public void setStatus(String status) {
    this.status = status;
  }

  public void setCreatedAt(Timestamp createdAt) {
    this.createdAt = createdAt;
  }
}
