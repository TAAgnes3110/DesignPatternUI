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

import android.app.DatePickerDialog;
import android.net.Uri;
import android.widget.ImageView;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import java.util.Calendar;
import java.sql.Date;

public class BuilderPatternActivity extends AppCompatActivity {

  private PatientDatabaseHelper dbHelper;

  private TextInputEditText inputFirstName, inputLastName, inputGender, inputDob, inputPhone, inputEmail, inputHistory;
  private TextView textLog, textDataPreview;
  private ImageView imageAvatar;
  private ActivityResultLauncher<String> pickImageLauncher;
  private Uri selectedImageUri;

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

    pickImageLauncher = registerForActivityResult(new ActivityResultContracts.GetContent(), uri -> {
      if (uri != null) {
        selectedImageUri = uri;
        imageAvatar.setImageURI(uri);
        log("Đã chọn ảnh: " + uri.getPath());
      }
    });

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
    inputHistory = findViewById(R.id.inputBuilderHistory);
    textLog = findViewById(R.id.textBuilderLog);
    textDataPreview = findViewById(R.id.textBuilderData);
    imageAvatar = findViewById(R.id.imageBuilderAvatar);
  }

  private void setupClickListeners() {
    findViewById(R.id.buttonBack).setOnClickListener(v -> finish());
    findViewById(R.id.buttonBuilderInsert).setOnClickListener(v -> savePatient());
    findViewById(R.id.buttonFetchRecent).setOnClickListener(v -> loadPatients());

    inputDob.setOnClickListener(v -> showDatePicker());
    inputDob.setFocusable(false);
    inputDob.setClickable(true);

    // Image Picker
    findViewById(R.id.buttonBuilderSelectImage).setOnClickListener(v -> pickImageLauncher.launch("image/*"));
  }

  private void showDatePicker() {
    Calendar calendar = Calendar.getInstance();
    new DatePickerDialog(this, (view, year, month, dayOfMonth) -> {
      String dateStr = String.format("%d-%02d-%02d", year, month + 1, dayOfMonth);
      inputDob.setText(dateStr);
    }, calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH)).show();
  }

  private void savePatient() {
    try {
      if (!validateInputs())
        return;

      PatientBuilder builder = new StandardPatientBuilder();
      builder.setFirstName(getText(inputFirstName))
          .setLastName(getText(inputLastName))
          .setGender(getText(inputGender))
          .setDateOfBirth(Date.valueOf(getText(inputDob)))
          .setContactNumber(getText(inputPhone))
          .setEmail(getText(inputEmail))
          .setAddress(getText(findViewById(R.id.inputBuilderAddress)))
          .setMedicalHistory(getText(inputHistory));

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

  private boolean validateInputs() {
    boolean isValid = true;
    if (getText(inputFirstName).isEmpty()) {
      inputFirstName.setError("Bắt buộc");
      isValid = false;
    }
    if (getText(inputLastName).isEmpty()) {
      inputLastName.setError("Bắt buộc");
      isValid = false;
    }
    if (getText(inputDob).isEmpty()) {
      inputDob.setError("Bắt buộc");
      isValid = false;
    }
    if (getText(inputPhone).isEmpty()) {
      inputPhone.setError("Bắt buộc");
      isValid = false;
    }
    return isValid;
  }
}
