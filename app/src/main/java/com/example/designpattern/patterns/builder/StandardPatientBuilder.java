package com.example.designpattern.patterns.builder;

import java.util.Date;

public class StandardPatientBuilder implements PatientBuilder {

  private final Patient patient;

  public StandardPatientBuilder() {
    this.patient = new Patient();
  }

  @Override
  public PatientBuilder setFirstName(String firstName) {
    this.patient.setFirstName(firstName);
    return this;
  }

  @Override
  public PatientBuilder setLastName(String lastName) {
    this.patient.setLastName(lastName);
    return this;
  }

  @Override
  public PatientBuilder setDateOfBirth(Date dateOfBirth) {
    this.patient.setDateOfBirth(dateOfBirth);
    return this;
  }

  @Override
  public PatientBuilder setGender(String gender) {
    this.patient.setGender(gender);
    return this;
  }

  @Override
  public PatientBuilder setContactNumber(String contactNumber) {
    this.patient.setContactNumber(contactNumber);
    return this;
  }

  @Override
  public PatientBuilder setAddress(String address) {
    this.patient.setAddress(address);
    return this;
  }

  @Override
  public PatientBuilder setEmail(String email) {
    this.patient.setEmail(email);
    return this;
  }

  @Override
  public PatientBuilder setMedicalHistory(String medicalHistory) {
    this.patient.setMedicalHistory(medicalHistory);
    return this;
  }

  @Override
  public Patient build() {
    if (this.patient.getFirstName() == null || this.patient.getFirstName().trim().isEmpty()) {
      throw new IllegalStateException("Tên không được để trống");
    }
    if (this.patient.getLastName() == null || this.patient.getLastName().trim().isEmpty()) {
      throw new IllegalStateException("Họ không được để trống");
    }
    if (this.patient.getDateOfBirth() == null) {
      throw new IllegalStateException("Ngày sinh không được để trống");
    }
    if (this.patient.getGender() == null || this.patient.getGender().trim().isEmpty()) {
      throw new IllegalStateException("Giới tính không được để trống");
    }
    if (this.patient.getContactNumber() == null || this.patient.getContactNumber().trim().isEmpty()) {
      throw new IllegalStateException("Số điện thoại không được để trống");
    }

    Patient builtPatient = new Patient();
    builtPatient.setFirstName(this.patient.getFirstName());
    builtPatient.setLastName(this.patient.getLastName());
    builtPatient.setDateOfBirth(this.patient.getDateOfBirth());
    builtPatient.setGender(this.patient.getGender());
    builtPatient.setContactNumber(this.patient.getContactNumber());
    builtPatient.setAddress(this.patient.getAddress());
    builtPatient.setEmail(this.patient.getEmail());
    builtPatient.setMedicalHistory(this.patient.getMedicalHistory());
    return builtPatient;
  }
}
