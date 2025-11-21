package com.example.designpattern.patterns.facade;

import com.example.designpattern.patterns.builder.Patient;

public interface PatientDAO {
  boolean save(Patient patient);

  Patient findById(int id);

  java.util.List<Patient> findAll();
}
