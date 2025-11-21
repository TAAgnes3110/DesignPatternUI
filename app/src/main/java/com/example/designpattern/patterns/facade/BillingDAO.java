package com.example.designpattern.patterns.facade;

public interface BillingDAO {
    boolean save(Billing billing);
    Billing findById(int id);
}
