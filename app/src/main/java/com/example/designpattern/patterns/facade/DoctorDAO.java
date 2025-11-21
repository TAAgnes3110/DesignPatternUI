package com.example.designpattern.patterns.facade;

import java.util.List;

public interface DoctorDAO {
  List<Doctor> findAll();

  Doctor findById(int id);
}
