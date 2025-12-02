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

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import java.util.Calendar;
import java.math.BigDecimal;
import java.sql.Date;
import java.sql.Time;
import java.util.ArrayList;
import java.util.List;

public class FacadePatternActivity extends AppCompatActivity {

  private HospitalFacade facade;
  private TextInputEditText inputPatientId, inputDoctorId, inputDate, inputTime, inputAmount, inputServices;
  private TextView textLog, textSummary;
  private List<Service> selectedServices = new ArrayList<>();

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_facade_pattern);

    DatabaseConnectionFactory dbFactory = DatabaseConnectionFactory.fromConfig();
    facade = new HospitalFacade(
        new PatientDAOImpl(dbFactory),
        new AppointmentDAOImpl(dbFactory),
        new BillingDAOImpl(dbFactory),
        new DoctorDAOImpl(dbFactory),
        new ServiceDAOImpl(dbFactory),
        new AppointmentServiceDAOImpl(dbFactory));

    initViews();
    setupEvents();
  }

  private void initViews() {
    inputPatientId = findViewById(R.id.inputFacadePatientId);
    inputDoctorId = findViewById(R.id.inputFacadeDoctorId);
    inputDate = findViewById(R.id.inputFacadeDate);
    inputTime = findViewById(R.id.inputFacadeTime);
    inputServices = findViewById(R.id.inputFacadeServices);
    inputAmount = findViewById(R.id.inputFacadeAmount);
    textLog = findViewById(R.id.textFacadeLog);
    textSummary = findViewById(R.id.textFacadeSummary);
  }

  private void setupEvents() {
    findViewById(R.id.buttonBack).setOnClickListener(v -> finish());
    findViewById(R.id.buttonFacadeSchedule).setOnClickListener(v -> bookAppointment());
    inputPatientId.setOnClickListener(v -> selectPatient());
    inputDoctorId.setOnClickListener(v -> selectDoctor());
    inputServices.setOnClickListener(v -> selectServices());

    // Date & Time Pickers
    inputDate.setOnClickListener(v -> showDatePicker());
    inputDate.setFocusable(false);
    inputDate.setClickable(true);

    inputTime.setOnClickListener(v -> showTimePicker());
    inputTime.setFocusable(false);
    inputTime.setClickable(true);
  }

  private void showDatePicker() {
    Calendar calendar = Calendar.getInstance();
    new DatePickerDialog(this, (view, year, month, dayOfMonth) -> {
      String dateStr = String.format("%d-%02d-%02d", year, month + 1, dayOfMonth);
      inputDate.setText(dateStr);
    }, calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH)).show();
  }

  private void showTimePicker() {
    Calendar calendar = Calendar.getInstance();
    new TimePickerDialog(this, (view, hourOfDay, minute) -> {
      String timeStr = String.format("%02d:%02d", hourOfDay, minute);
      inputTime.setText(timeStr);
    }, calendar.get(Calendar.HOUR_OF_DAY), calendar.get(Calendar.MINUTE), true).show();
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

  private void selectServices() {
    new Thread(() -> {
      List<Service> services = facade.getAllServices();
      String[] items = new String[services.size()];
      boolean[] checkedItems = new boolean[services.size()];

      for (int i = 0; i < services.size(); i++) {
        items[i] = String.format("%s ($%s)", services.get(i).getServiceName(), services.get(i).getPrice());
      }

      runOnUiThread(() -> {
        new AlertDialog.Builder(this)
            .setTitle("Chọn Dịch vụ")
            .setMultiChoiceItems(items, checkedItems, (dialog, which, isChecked) -> {
              if (isChecked) {
                selectedServices.add(services.get(which));
              } else {
                selectedServices.remove(services.get(which));
              }
              updateTotalAmount();
            })
            .setPositiveButton("OK", (dialog, which) -> {
              StringBuilder sb = new StringBuilder();
              for (Service s : selectedServices) {
                if (sb.length() > 0)
                  sb.append(", ");
                sb.append(s.getServiceName());
              }
              inputServices.setText(sb.toString());
            })
            .show();
      });
    }).start();
  }

  private void updateTotalAmount() {
    BigDecimal total = BigDecimal.ZERO;
    for (Service s : selectedServices) {
      total = total.add(s.getPrice());
    }
    BigDecimal finalTotal = total;
    runOnUiThread(() -> inputAmount.setText(finalTotal.toString()));
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
        if (inputPatientId.getText().toString().isEmpty()) {
          runOnUiThread(() -> inputPatientId.setError("Chọn bệnh nhân"));
          return;
        }
        if (inputDoctorId.getText().toString().isEmpty()) {
          runOnUiThread(() -> inputDoctorId.setError("Chọn bác sĩ"));
          return;
        }
        if (inputDate.getText().toString().isEmpty()) {
          runOnUiThread(() -> inputDate.setError("Chọn ngày"));
          return;
        }
        if (inputTime.getText().toString().isEmpty()) {
          runOnUiThread(() -> inputTime.setError("Chọn giờ"));
          return;
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

        // Add services
        for (Service s : selectedServices) {
          facade.addServiceToAppointment(appt.getAppointmentId(), s, 1);
        }

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
