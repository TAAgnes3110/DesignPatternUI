package com.example.designpattern.patterns.facade;

import java.math.BigDecimal;

public class Billing {
  private int billId;
  private int appointmentId;
  private BigDecimal totalAmount;

  public Billing(int billId, int appointmentId, BigDecimal totalAmount) {
    this.billId = billId;
    this.appointmentId = appointmentId;
    this.totalAmount = totalAmount;
  }

  public int getBillId() {
    return billId;
  }

  public int getAppointmentId() {
    return appointmentId;
  }

  public BigDecimal getTotalAmount() {
    return totalAmount;
  }

  public void setBillId(int billId) {
    this.billId = billId;
  }

  public void setAppointmentId(int appointmentId) {
    this.appointmentId = appointmentId;
  }

  public void setTotalAmount(BigDecimal totalAmount) {
    this.totalAmount = totalAmount;
  }
}
