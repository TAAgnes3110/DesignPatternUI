package com.example.designpattern.patterns.facade;

import com.example.designpattern.patterns.builder.Patient;
import java.util.List;

public interface PatientDAO {
  boolean save(Patient patient);

  Patient findById(int id);

  List<Patient> findAll();
}
