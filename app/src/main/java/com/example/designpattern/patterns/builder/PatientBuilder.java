package com.example.designpattern.patterns.builder;

import java.util.Date;

public interface PatientBuilder {
  PatientBuilder setFirstName(String firstName);

  PatientBuilder setLastName(String lastName);

  PatientBuilder setDateOfBirth(Date dateOfBirth);

  PatientBuilder setGender(String gender);

  PatientBuilder setContactNumber(String contactNumber);

  PatientBuilder setAddress(String address);

  PatientBuilder setEmail(String email);

  PatientBuilder setMedicalHistory(String medicalHistory);

  Patient build();
}
