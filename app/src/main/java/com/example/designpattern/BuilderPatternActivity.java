package com.example.designpattern;

import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.designpattern.data.DatabaseConnectionFactory;
import com.example.designpattern.patterns.builder.Patient;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputEditText;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.Locale;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Activity demo cho Builder Pattern.
 * Tác động đến 1 bảng: Patients.
 */
public class BuilderPatternActivity extends AppCompatActivity {

    private final ExecutorService executor = Executors.newSingleThreadExecutor();
    private final Handler handler = new Handler(Looper.getMainLooper());
    private DatabaseConnectionFactory factory;

    private TextInputEditText inputFirstName;
    private TextInputEditText inputLastName;
    private TextInputEditText inputGender;
    private TextInputEditText inputDob;
    private TextInputEditText inputPhone;
    private TextInputEditText inputEmail;
    private TextView textLog;
    private TextView textDataPreview;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_command_pattern); 
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.commandRoot), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        factory = DatabaseConnectionFactory.fromConfig();
        bindViews();
        bindActions();

        findViewById(R.id.buttonQueueUpdate).setVisibility(View.GONE);
        findViewById(R.id.buttonExecuteQueue).setVisibility(View.GONE);
        findViewById(R.id.textCommandQueue).setVisibility(View.GONE);
        
        // Đổi tên nút insert
        MaterialButton btnInsert = findViewById(R.id.buttonQueueInsert);
        btnInsert.setText("Tạo & Lưu (Builder)");

        View backBtn = findViewById(R.id.buttonBack);
        if (backBtn != null && backBtn.getParent() instanceof ViewGroup) {
            ViewGroup header = (ViewGroup) backBtn.getParent();
            if (header.getChildCount() > 1 && header.getChildAt(1) instanceof TextView) {
                TextView titleView = (TextView) header.getChildAt(1);
                titleView.setText("Builder Pattern");
            }
        }
    }

    private void bindViews() {
        inputFirstName = findViewById(R.id.inputCommandFirstName);
        inputLastName = findViewById(R.id.inputCommandLastName);
        inputGender = findViewById(R.id.inputCommandGender);
        inputDob = findViewById(R.id.inputCommandDob);
        inputPhone = findViewById(R.id.inputCommandPhone);
        inputEmail = findViewById(R.id.inputCommandEmail);
        textLog = findViewById(R.id.textCommandLog);
        textDataPreview = findViewById(R.id.textCommandData);
    }

    private void bindActions() {
        ImageButton backButton = findViewById(R.id.buttonBack);
        backButton.setOnClickListener(v -> finish());

        MaterialButton btnSave = findViewById(R.id.buttonQueueInsert);
        btnSave.setOnClickListener(v -> savePatientUsingBuilder());

        findViewById(R.id.buttonFetchRecent).setOnClickListener(v -> fetchRecentPatients());
    }

    private void savePatientUsingBuilder() {
        try {
            Patient patient = new Patient.Builder()
                    .setFirstName(textOf(inputFirstName))
                    .setLastName(textOf(inputLastName))
                    .setGender(textOf(inputGender))
                    .setDob(textOf(inputDob))
                    .setPhone(textOf(inputPhone))
                    .setEmail(textOf(inputEmail))
                    .build();

            appendLog("Builder: Đã tạo object Patient hợp lệ.");
            executor.execute(() -> {
                try {
                    String result = insertPatientToDb(patient);
                    handler.post(() -> {
                        appendLog("DB: " + result);
                        fetchRecentPatients();
                    });
                } catch (Exception e) {
                    handler.post(() -> appendLog("Lỗi DB: " + e.getMessage()));
                }
            });

        } catch (IllegalStateException e) {
            Toast.makeText(this, "Lỗi Validation: " + e.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }

    private String insertPatientToDb(Patient p) throws Exception {
        String sql = "INSERT INTO Patients (first_name, last_name, gender, date_of_birth, contact_number, email) " +
                "VALUES (?, ?, ?, ?, ?, ?) RETURNING patient_id";
        try (Connection conn = factory.newConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, p.getFirstName());
            stmt.setString(2, p.getLastName());
            stmt.setString(3, p.getGender());
            stmt.setDate(4, Date.valueOf(p.getDob()));
            stmt.setString(5, p.getPhone());
            stmt.setString(6, p.getEmail());
            try (ResultSet rs = stmt.executeQuery()) {
                rs.next();
                return "Đã insert thành công ID: " + rs.getInt(1);
            }
        }
    }

    private void fetchRecentPatients() {
        appendLog("Đang tải danh sách...");
        executor.execute(() -> {
            try {
                StringBuilder sb = new StringBuilder();
                String sql = "SELECT patient_id, first_name, last_name, contact_number FROM Patients ORDER BY patient_id DESC LIMIT 5";
                
                try (Connection conn = factory.newConnection();
                     Statement stmt = conn.createStatement();
                     ResultSet rs = stmt.executeQuery(sql)) {
                    
                    while (rs.next()) {
                        sb.append(String.format("#%d %s %s (%s)\n",
                                rs.getInt("patient_id"),
                                rs.getString("first_name"),
                                rs.getString("last_name"),
                                rs.getString("contact_number")));
                    }
                }
                
                if (sb.length() == 0) sb.append("Chưa có dữ liệu.");
                
                String result = sb.toString();
                handler.post(() -> {
                    textDataPreview.setText(result);
                    appendLog("Đã cập nhật danh sách.");
                });
            } catch (Exception e) {
                handler.post(() -> appendLog("Lỗi tải danh sách: " + e.getMessage()));
            }
        });
    }

    private void appendLog(String message) {
        String time = new SimpleDateFormat("HH:mm:ss", Locale.getDefault()).format(new java.util.Date());
        CharSequence existing = textLog.getText();
        StringBuilder builder = new StringBuilder();
        builder.append('[').append(time).append("] ").append(message).append('\n');
        if (!TextUtils.isEmpty(existing)) {
            builder.append(existing);
        }
        textLog.setText(builder.toString());
    }

    private String textOf(TextInputEditText editText) {
        CharSequence cs = editText.getText();
        return cs == null ? "" : cs.toString().trim();
    }
}
