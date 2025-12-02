package com.example.designpattern.patterns.facade;

public interface BillingDAO {
  boolean save(Billing billing);

  boolean save(Billing billing, java.sql.Connection conn);

  Billing findById(int id);
}
