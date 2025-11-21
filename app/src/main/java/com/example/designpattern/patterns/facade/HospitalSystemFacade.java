package com.example.designpattern.patterns.facade;

import com.example.designpattern.data.DatabaseConnectionFactory;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Time;
public class HospitalSystemFacade {

    private final DatabaseConnectionFactory factory;

    public HospitalSystemFacade() {
        this.factory = DatabaseConnectionFactory.fromConfig();
    }

    public VisitResult scheduleVisit(int patientId,
                                     int doctorId,
                                     String dateIso,
                                     String timeIso,
                                     String diagnosis,
                                     double amount) throws SQLException {

        try (Connection conn = factory.newConnection()) {
            conn.setAutoCommit(false);
            try {
                int appointmentId = insertAppointment(conn, patientId, doctorId, dateIso, timeIso, diagnosis);
                int recordId = insertMedicalRecord(conn, patientId, doctorId, appointmentId, diagnosis);
                int billId = insertBilling(conn, patientId, appointmentId, amount);
                conn.commit();
                
                return new VisitResult(appointmentId, recordId, billId);
            } catch (SQLException ex) {
                conn.rollback();
                throw ex;
            }
        }
    }
    private int insertAppointment(Connection conn,
                                  int patientId,
                                  int doctorId,
                                  String dateIso,
                                  String timeIso,
                                  String diagnosis) throws SQLException {
        String sql = "INSERT INTO Appointments (patient_id, doctor_id, appointment_date, appointment_time, purpose, status) " +
                "VALUES (?, ?, ?, ?, ?, 'Scheduled') RETURNING appointment_id";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, patientId);
            stmt.setInt(2, doctorId);
            stmt.setDate(3, Date.valueOf(dateIso));
            stmt.setTime(4, Time.valueOf(padSeconds(timeIso)));
            stmt.setString(5, diagnosis);
            try (ResultSet rs = stmt.executeQuery()) {
                rs.next();
                return rs.getInt(1);
            }
        }
    }

    private int insertMedicalRecord(Connection conn,
                                    int patientId,
                                    int doctorId,
                                    int appointmentId,
                                    String diagnosis) throws SQLException {
        String sql = "INSERT INTO Medical_Records (patient_id, doctor_id, appointment_id, diagnosis, treatment, prescription) " +
                "VALUES (?, ?, ?, ?, 'Pending treatment plan', 'N/A') RETURNING record_id";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, patientId);
            stmt.setInt(2, doctorId);
            stmt.setInt(3, appointmentId);
            stmt.setString(4, diagnosis);
            try (ResultSet rs = stmt.executeQuery()) {
                rs.next();
                return rs.getInt(1);
            }
        }
    }

    private int insertBilling(Connection conn,
                              int patientId,
                              int appointmentId,
                              double amount) throws SQLException {
        String sql = "INSERT INTO Billing (patient_id, appointment_id, total_amount, payment_status) " +
                "VALUES (?, ?, ?, 'Pending') RETURNING bill_id";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, patientId);
            stmt.setInt(2, appointmentId);
            stmt.setDouble(3, amount);
            try (ResultSet rs = stmt.executeQuery()) {
                rs.next();
                return rs.getInt(1);
            }
        }
    }

    private String padSeconds(String timeIso) {
        return timeIso.length() == 5 ? timeIso + ":00" : timeIso;
    }
}
