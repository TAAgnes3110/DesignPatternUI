package com.example.designpattern.patterns.facade;

import java.util.List;

public interface ServiceDAO {
  List<Service> findAll();

  Service findById(int id);

  void save(Service service);
}
