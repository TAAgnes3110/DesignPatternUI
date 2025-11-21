package com.example.designpattern;

import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.designpattern.data.DatabaseConnectionFactory;
import com.example.designpattern.patterns.builder.*;
import com.google.android.material.textfield.TextInputEditText;

import java.sql.*;

public class BuilderPatternActivity extends AppCompatActivity {

  private final Handler handler = new Handler(Looper.getMainLooper());
  private DatabaseConnectionFactory factory;

  private TextInputEditText inputFirstName, inputLastName, inputGender, inputDob, inputPhone, inputEmail;
  private TextView textLog, textDataPreview;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    EdgeToEdge.enable(this);
    setContentView(R.layout.activity_builder_pattern);
    ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.builderRoot), (v, insets) -> {
      Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
      v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
      return insets;
    });

    factory = DatabaseConnectionFactory.fromConfig();
    initViews();
    setupClickListeners();
  }

  private void initViews() {
    inputFirstName = findViewById(R.id.inputBuilderFirstName);
    inputLastName = findViewById(R.id.inputBuilderLastName);
    inputGender = findViewById(R.id.inputBuilderGender);
    inputDob = findViewById(R.id.inputBuilderDob);
    inputPhone = findViewById(R.id.inputBuilderPhone);
    inputEmail = findViewById(R.id.inputBuilderEmail);
    textLog = findViewById(R.id.textBuilderLog);
    textDataPreview = findViewById(R.id.textBuilderData);
  }

  private void setupClickListeners() {
    findViewById(R.id.buttonBack).setOnClickListener(v -> finish());
    findViewById(R.id.buttonBuilderInsert).setOnClickListener(v -> luuThongTinBenhNhan());
    findViewById(R.id.buttonFetchRecent).setOnClickListener(v -> taiDanhSachBenhNhan());
  }

  private void luuThongTinBenhNhan() {
    try {
      PatientBuilder builder = new StandardPatientBuilder();
      builder.setFirstName(layNoiDung(inputFirstName))
          .setLastName(layNoiDung(inputLastName))
          .setGender(layNoiDung(inputGender))
          .setDateOfBirth(Date.valueOf(layNoiDung(inputDob)))
          .setContactNumber(layNoiDung(inputPhone))
          .setEmail(layNoiDung(inputEmail))
          .setAddress("Default Address")
          .setMedicalHistory("No history recorded");

      Patient patient = new PatientDirector(builder).buildPatient();
      ghiLog("Builder: Đã tạo object Patient hợp lệ.");

      new Thread(() -> {
        try {
          String result = themVaoDatabase(patient);
          handler.post(() -> {
            ghiLog("DB: " + result);
            taiDanhSachBenhNhan();
          });
        } catch (Exception e) {
          handler.post(() -> ghiLog("Lỗi DB: " + e.getMessage()));
        }
      }).start();

    } catch (IllegalArgumentException e) {
      Toast.makeText(this, "Lỗi ngày (yyyy-mm-dd): " + e.getMessage(), Toast.LENGTH_SHORT).show();
    } catch (Exception e) {
      Toast.makeText(this, "Lỗi: " + e.getMessage(), Toast.LENGTH_SHORT).show();
    }
  }

  private String themVaoDatabase(Patient p) throws Exception {
    String sql = "INSERT INTO Patients (first_name, last_name, gender, date_of_birth, contact_number, email, address, medical_history) VALUES (?, ?, ?, ?, ?, ?, ?, ?) RETURNING patient_id";
    try (Connection conn = factory.newConnection(); PreparedStatement stmt = conn.prepareStatement(sql)) {
      stmt.setString(1, p.getFirstName());
      stmt.setString(2, p.getLastName());
      stmt.setString(3, p.getGender());
      stmt.setDate(4, new Date(p.getDateOfBirth().getTime()));
      stmt.setString(5, p.getContactNumber());
      stmt.setString(6, p.getEmail());
      stmt.setString(7, p.getAddress());
      stmt.setString(8, p.getMedicalHistory());

      try (ResultSet rs = stmt.executeQuery()) {
        rs.next();
        return "Đã insert thành công ID: " + rs.getInt(1);
      }
    }
  }

  private void taiDanhSachBenhNhan() {
    ghiLog("Đang tải danh sách...");
    new Thread(() -> {
      try {
        StringBuilder sb = new StringBuilder();
        try (Connection conn = factory.newConnection();
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(
                "SELECT patient_id, first_name, last_name, contact_number FROM Patients ORDER BY patient_id DESC LIMIT 5")) {
          while (rs.next()) {
            sb.append(
                String.format("#%d %s %s (%s)\n", rs.getInt(1), rs.getString(2), rs.getString(3), rs.getString(4)));
          }
        }
        String result = sb.length() > 0 ? sb.toString() : "Chưa có dữ liệu.";
        handler.post(() -> {
          textDataPreview.setText(result);
          ghiLog("Đã cập nhật danh sách.");
        });
      } catch (Exception e) {
        handler.post(() -> ghiLog("Lỗi tải danh sách: " + e.getMessage()));
      }
    }).start();
  }

  private void ghiLog(String msg) {
    textLog.setText(String.format("[%s] %s\n%s", new java.util.Date(), msg, textLog.getText()));
  }

  private String layNoiDung(TextInputEditText et) {
    return et.getText() == null ? "" : et.getText().toString().trim();
  }
}
