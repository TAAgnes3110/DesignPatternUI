package com.example.designpattern;

import android.content.Intent;
import android.os.Bundle;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.android.material.button.MaterialButton;

public class MainActivity extends AppCompatActivity {

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    EdgeToEdge.enable(this);
    setContentView(R.layout.activity_main);
    ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
      Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
      v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
      return insets;
    });

    MaterialButton form1Button = findViewById(R.id.buttonForm1);
    MaterialButton form2Button = findViewById(R.id.buttonForm2);

    form1Button.setOnClickListener(v -> startActivity(
        new Intent(this, BuilderPatternActivity.class)));

    form2Button.setOnClickListener(v -> startActivity(
        new Intent(this, FacadePatternActivity.class)));
  }
}
