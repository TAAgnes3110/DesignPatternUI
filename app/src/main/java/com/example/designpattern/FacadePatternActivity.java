package com.example.designpattern;

import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.example.designpattern.data.DatabaseConnectionFactory;
import com.example.designpattern.patterns.builder.Patient;
import com.example.designpattern.patterns.facade.*;
import com.google.android.material.textfield.TextInputEditText;

import java.math.BigDecimal;
import java.sql.Date;
import java.sql.Time;
import java.util.List;

public class FacadePatternActivity extends AppCompatActivity {

  private HospitalFacade facade;
  private TextInputEditText inputPatientId, inputDoctorId, inputDate, inputTime, inputAmount;
  private TextView textLog, textSummary;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_facade_pattern);

    DatabaseConnectionFactory dbFactory = DatabaseConnectionFactory.fromConfig();
    facade = new HospitalFacade(
        new PatientDAOImpl(dbFactory),
        new AppointmentDAOImpl(dbFactory),
        new BillingDAOImpl(dbFactory),
        new DoctorDAOImpl(dbFactory));

    initViews();
    setupEvents();
  }

  private void initViews() {
    inputPatientId = findViewById(R.id.inputFacadePatientId);
    inputDoctorId = findViewById(R.id.inputFacadeDoctorId);
    inputDate = findViewById(R.id.inputFacadeDate);
    inputTime = findViewById(R.id.inputFacadeTime);
    inputAmount = findViewById(R.id.inputFacadeAmount);
    textLog = findViewById(R.id.textFacadeLog);
    textSummary = findViewById(R.id.textFacadeSummary);
  }

  private void setupEvents() {
    findViewById(R.id.buttonBack).setOnClickListener(v -> finish());
    findViewById(R.id.buttonFacadeSchedule).setOnClickListener(v -> bookAppointment());
    inputPatientId.setOnClickListener(v -> selectPatient());
    inputDoctorId.setOnClickListener(v -> selectDoctor());
  }

  private void selectPatient() {
    new Thread(() -> {
      List<Patient> patients = facade.getAllPatients();
      String[] items = new String[patients.size()];
      final int[] ids = new int[patients.size()];

      for (int i = 0; i < patients.size(); i++) {
        Patient p = patients.get(i);
        items[i] = String.format("#%d - %s %s", p.getPatientId(), p.getLastName(), p.getFirstName());
        ids[i] = p.getPatientId();
      }

      runOnUiThread(() -> showDialog("Chọn Bệnh nhân", items, ids, inputPatientId));
    }).start();
  }

  private void selectDoctor() {
    new Thread(() -> {
      List<Doctor> doctors = facade.getAllDoctors();
      String[] items = new String[doctors.size()];
      final int[] ids = new int[doctors.size()];

      for (int i = 0; i < doctors.size(); i++) {
        Doctor d = doctors.get(i);
        items[i] = String.format("#%d - %s %s (%s)", d.getDoctorId(), d.getLastName(), d.getFirstName(),
            d.getSpecialty());
        ids[i] = d.getDoctorId();
      }

      runOnUiThread(() -> showDialog("Chọn Bác sĩ", items, ids, inputDoctorId));
    }).start();
  }

  private void showDialog(String title, String[] items, int[] ids, TextInputEditText targetInput) {
    if (items.length == 0) {
      Toast.makeText(this, "Danh sách trống", Toast.LENGTH_SHORT).show();
      return;
    }
    new AlertDialog.Builder(this)
        .setTitle(title)
        .setItems(items, (dialog, which) -> targetInput.setText(String.valueOf(ids[which])))
        .show();
  }

  private void bookAppointment() {
    new Thread(() -> {
      try {
        if (inputPatientId.getText().toString().isEmpty() || inputDoctorId.getText().toString().isEmpty()) {
          throw new IllegalArgumentException("Vui lòng chọn Bệnh nhân và Bác sĩ");
        }

        int pId = Integer.parseInt(inputPatientId.getText().toString());
        int dId = Integer.parseInt(inputDoctorId.getText().toString());
        Date date = Date.valueOf(inputDate.getText().toString());
        Time time = Time.valueOf(inputTime.getText().toString() + ":00");

        String amountStr = inputAmount.getText().toString().trim();
        if (amountStr.isEmpty())
          amountStr = "0";
        BigDecimal amount = new BigDecimal(amountStr);

        Appointment appt = facade.bookAppointment(pId, dId, date, time);
        Billing bill = facade.processBilling(appt.getAppointmentId(), amount);

        showResult(pId, dId, appt, bill);

      } catch (Exception e) {
        runOnUiThread(() -> {
          Toast.makeText(this, "Lỗi: " + e.getMessage(), Toast.LENGTH_SHORT).show();
          textLog.setText("Lỗi: " + e.getMessage());
        });
      }
    }).start();
  }

  private void showResult(int pId, int dId, Appointment appt, Billing bill) {
    Patient p = (Patient) facade.getPatientRecords(pId).get("patient");
    String patientName = (p != null) ? p.getLastName() + " " + p.getFirstName() : "ID " + pId;

    Doctor d = facade.getDoctorById(dId);
    String doctorName = (d != null) ? d.getFullName() : "ID " + dId;

    runOnUiThread(() -> {
      textSummary.setText(String.format(
          "ĐẶT LỊCH THÀNH CÔNG\n\nBệnh nhân: %s\nBác sĩ: %s\nThời gian: %s %s\nHóa đơn #%d: $%s",
          patientName, doctorName, appt.getAppointmentDate(), appt.getAppointmentTime(), bill.getBillId(),
          bill.getTotalAmount()));
      textLog.setText("Giao dịch hoàn tất.");
    });
  }
}
