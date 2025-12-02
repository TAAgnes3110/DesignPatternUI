package com.example.designpattern.patterns.facade;

import com.example.designpattern.patterns.builder.Patient;

import java.math.BigDecimal;
import java.sql.Time;
import java.sql.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.example.designpattern.data.DatabaseConnectionFactory;
import java.sql.Connection;
import java.sql.SQLException;

public class HospitalFacade {
  private final PatientDAO patientDAO;
  private final AppointmentDAO appointmentDAO;
  private final BillingDAO billingDAO;
  private final DoctorDAO doctorDAO;
  private final ServiceDAO serviceDAO;
  private final AppointmentServiceDAO appointmentServiceDAO;
  private final DatabaseConnectionFactory dbFactory;

  public HospitalFacade(PatientDAO patientDAO, AppointmentDAO appointmentDAO, BillingDAO billingDAO,
      DoctorDAO doctorDAO, ServiceDAO serviceDAO, AppointmentServiceDAO appointmentServiceDAO,
      DatabaseConnectionFactory dbFactory) {
    this.patientDAO = patientDAO;
    this.appointmentDAO = appointmentDAO;
    this.billingDAO = billingDAO;
    this.doctorDAO = doctorDAO;
    this.serviceDAO = serviceDAO;
    this.appointmentServiceDAO = appointmentServiceDAO;
    this.dbFactory = dbFactory;
  }

  public Patient registerPatient(Map<String, Object> patientData) {
    Patient patient = new Patient();
    patient.setFirstName((String) patientData.get("firstName"));
    patient.setLastName((String) patientData.get("lastName"));
    patientDAO.save(patient);
    return patient;
  }

  public Appointment bookAppointment(int patientId, int doctorId, Date date, Time time) {
    Appointment appointment = new Appointment(0, patientId, doctorId, date, time);
    appointmentDAO.save(appointment);
    return appointment;
  }

  public void addServiceToAppointment(int appointmentId, Service service, int quantity) {
    AppointmentService as = new AppointmentService(0, appointmentId, service.getServiceId(), quantity,
        service.getPrice());
    appointmentServiceDAO.save(as);
  }

  public Billing processBilling(int appointmentId, BigDecimal amount) {
    Appointment appointment = appointmentDAO.findById(appointmentId);
    if (appointment == null) {
      throw new RuntimeException("Không tìm thấy cuộc hẹn với ID: " + appointmentId);
    }
    Billing billing = new Billing(0, appointment.getAppointmentId(), amount);
    billingDAO.save(billing);
    return billing;
  }

  public Map<String, Object> getPatientRecords(int patientId) {
    Map<String, Object> records = new HashMap<>();
    records.put("patient", patientDAO.findById(patientId));
    return records;
  }

  public List<Patient> getAllPatients() {
    return patientDAO.findAll();
  }

  public List<Doctor> getAllDoctors() {
    return doctorDAO.findAll();
  }

  public List<Service> getAllServices() {
    return serviceDAO.findAll();
  }

  public Appointment scheduleCompleteAppointment(int patientId, int doctorId, Date date, Time time, String purpose,
      List<Service> selectedServices) {
    Connection conn = null;
    try {
      conn = dbFactory.newConnection();
      conn.setAutoCommit(false); // Bắt đầu transaction

      // 1. Tạo cuộc hẹn
      Appointment appt = new Appointment(0, patientId, doctorId, date, time);
      appt.setPurpose(purpose);
      if (!appointmentDAO.save(appt, conn)) {
        throw new SQLException("Không thể tạo cuộc hẹn");
      }

      BigDecimal totalAmount = BigDecimal.ZERO;

      // 2. Thêm dịch vụ và tính tổng tiền
      if (selectedServices != null) {
        for (Service s : selectedServices) {
          AppointmentService as = new AppointmentService(0, appt.getAppointmentId(), s.getServiceId(), 1, s.getPrice());
          appointmentServiceDAO.save(as, conn);
          totalAmount = totalAmount.add(s.getPrice());
        }
      }

      // 3. Tạo hóa đơn
      Billing billing = new Billing(0, appt.getAppointmentId(), totalAmount);
      if (!billingDAO.save(billing, conn)) {
        throw new SQLException("Không thể tạo hóa đơn");
      }

      conn.commit();
      return appt;

    } catch (SQLException e) {
      if (conn != null) {
        try {
          conn.rollback();
        } catch (SQLException ex) {
          ex.printStackTrace();
        }
      }
      throw new RuntimeException("Lỗi khi đặt lịch: " + e.getMessage());
    } finally {
      if (conn != null) {
        try {
          conn.close();
        } catch (SQLException e) {
          e.printStackTrace();
        }
      }
    }
  }

  public Doctor getDoctorById(int id) {
    return doctorDAO.findById(id);
  }
}
