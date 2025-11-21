package com.example.designpattern.patterns.facade;

import java.math.BigDecimal;

public class Billing {
    private int billId;
    private int patientId;
    private BigDecimal totalAmount;
    public Billing(int billId, int patientId, BigDecimal totalAmount) {
        this.billId = billId;
        this.patientId = patientId;
        this.totalAmount = totalAmount;
    }
    public int getBillId() { return billId; }
    public int getPatientId() { return patientId; }
    public BigDecimal getTotalAmount() { return totalAmount; }
    public void setBillId(int billId) { this.billId = billId; }
    public void setPatientId(int patientId) { this.patientId = patientId; }
    public void setTotalAmount(BigDecimal totalAmount) { this.totalAmount = totalAmount; }
}