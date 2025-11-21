package com.example.designpattern;

import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.designpattern.data.PatientDatabaseHelper;
import com.example.designpattern.patterns.builder.*;
import com.google.android.material.textfield.TextInputEditText;

import java.sql.Date;

public class BuilderPatternActivity extends AppCompatActivity {

  private PatientDatabaseHelper dbHelper;

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

    dbHelper = new PatientDatabaseHelper();
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
    findViewById(R.id.buttonBuilderInsert).setOnClickListener(v -> savePatient());
    findViewById(R.id.buttonFetchRecent).setOnClickListener(v -> loadPatients());
  }

  private void savePatient() {
    try {
      PatientBuilder builder = new StandardPatientBuilder();
      builder.setFirstName(getText(inputFirstName))
          .setLastName(getText(inputLastName))
          .setGender(getText(inputGender))
          .setDateOfBirth(Date.valueOf(getText(inputDob)))
          .setContactNumber(getText(inputPhone))
          .setEmail(getText(inputEmail))
          .setAddress("Default Address")
          .setMedicalHistory("No history recorded");

      Patient patient = new PatientDirector(builder).buildPatient();
      log("Builder: Đã tạo object Patient hợp lệ.");

      new Thread(() -> {
        try {
          String result = dbHelper.addPatient(patient);

          runOnUiThread(() -> {
            log("DB: " + result);
            loadPatients();
          });
        } catch (Exception e) {
          runOnUiThread(() -> log("Lỗi DB: " + e.getMessage()));
        }
      }).start();

    } catch (IllegalArgumentException e) {
      Toast.makeText(this, "Lỗi ngày (yyyy-mm-dd): " + e.getMessage(), Toast.LENGTH_SHORT).show();
    } catch (Exception e) {
      Toast.makeText(this, "Lỗi: " + e.getMessage(), Toast.LENGTH_SHORT).show();
    }
  }

  private void loadPatients() {
    new Thread(() -> {
      try {
        String result = dbHelper.getRecentPatients();
        runOnUiThread(() -> textDataPreview.setText(result));
      } catch (Exception e) {
        runOnUiThread(() -> log("Lỗi tải danh sách: " + e.getMessage()));
      }
    }).start();
  }

  private void log(String msg) {
    textLog.setText(msg + "\n" + textLog.getText());
  }

  private String getText(TextInputEditText et) {
    return et.getText() == null ? "" : et.getText().toString().trim();
  }
}
