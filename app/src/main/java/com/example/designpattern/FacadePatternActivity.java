package com.example.designpattern;

import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.text.TextUtils;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.designpattern.patterns.facade.HospitalSystemFacade;
import com.example.designpattern.patterns.facade.VisitResult;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputEditText;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class FacadePatternActivity extends AppCompatActivity {

    private final ExecutorService executor = Executors.newSingleThreadExecutor();
    private final Handler handler = new Handler(Looper.getMainLooper());

    private HospitalSystemFacade facade;

    private TextInputEditText inputPatientId;
    private TextInputEditText inputDoctorId;
    private TextInputEditText inputDate;
    private TextInputEditText inputTime;
    private TextInputEditText inputDiagnosis;
    private TextInputEditText inputAmount;
    private TextView textLog;
    private TextView textSummary;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_singleton_pattern);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.singletonRoot), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        facade = new HospitalSystemFacade();

        bindViews();
        bindActions();
    }

    private void bindViews() {
        inputPatientId = findViewById(R.id.inputSingletonPatientId);
        inputDoctorId = findViewById(R.id.inputSingletonDoctorId);
        inputDate = findViewById(R.id.inputSingletonDate);
        inputTime = findViewById(R.id.inputSingletonTime);
        inputDiagnosis = findViewById(R.id.inputSingletonDiagnosis);
        inputAmount = findViewById(R.id.inputSingletonAmount);
        textLog = findViewById(R.id.textSingletonLog);
        textSummary = findViewById(R.id.textSingletonSummary);
    }

    private void bindActions() {
        ImageButton backButton = findViewById(R.id.buttonBack);
        backButton.setOnClickListener(v -> finish());

        MaterialButton scheduleButton = findViewById(R.id.buttonSingletonSchedule);
        scheduleButton.setOnClickListener(v -> runSchedule());
    }

    private void runSchedule() {
        appendLog("Đang gọi Facade.scheduleVisit()...");
        executor.execute(() -> {
            try {
                VisitResult result = facade.scheduleVisit(
                        parseInt(inputPatientId, "patient id"),
                        parseInt(inputDoctorId, "doctor id"),
                        textOf(inputDate),
                        textOf(inputTime),
                        textOf(inputDiagnosis),
                        parseDouble(inputAmount, "amount")
                );
                handler.post(() -> {
                    textSummary.setText("Cuộc hẹn #" + result.appointmentId +
                            " · Hồ sơ #" + result.recordId +
                            " · Hóa đơn #" + result.billId);
                    appendLog("Facade hoàn tất giao dịch: " + result);
                });
            } catch (Exception e) {
                handler.post(() -> {
                    appendLog("Lỗi từ Facade: " + e.getMessage());
                    Toast.makeText(this, e.getMessage(), Toast.LENGTH_SHORT).show();
                });
            }
        });
    }

    private void appendLog(String message) {
        String time = new SimpleDateFormat("HH:mm:ss", Locale.getDefault()).format(new Date());
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

    private int parseInt(TextInputEditText editText, String label) {
        try {
            return Integer.parseInt(textOf(editText));
        } catch (NumberFormatException ex) {
            throw new IllegalArgumentException("Giá trị " + label + " không hợp lệ");
        }
    }

    private double parseDouble(TextInputEditText editText, String label) {
        try {
            return Double.parseDouble(textOf(editText));
        } catch (NumberFormatException ex) {
            throw new IllegalArgumentException("Giá trị " + label + " không hợp lệ");
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        executor.shutdownNow();
    }
}
