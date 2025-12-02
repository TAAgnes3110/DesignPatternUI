package com.example.designpattern.patterns.facade;

import java.math.BigDecimal;

public class AppointmentService {
  private int appServiceId;
  private int appointmentId;
  private int serviceId;
  private int quantity;
  private BigDecimal unitPrice;

  public AppointmentService() {
  }

  public AppointmentService(int appServiceId, int appointmentId, int serviceId, int quantity, BigDecimal unitPrice) {
    this.appServiceId = appServiceId;
    this.appointmentId = appointmentId;
    this.serviceId = serviceId;
    this.quantity = quantity;
    this.unitPrice = unitPrice;
  }

  public int getAppServiceId() {
    return appServiceId;
  }

  public void setAppServiceId(int appServiceId) {
    this.appServiceId = appServiceId;
  }

  public int getAppointmentId() {
    return appointmentId;
  }

  public void setAppointmentId(int appointmentId) {
    this.appointmentId = appointmentId;
  }

  public int getServiceId() {
    return serviceId;
  }

  public void setServiceId(int serviceId) {
    this.serviceId = serviceId;
  }

  public int getQuantity() {
    return quantity;
  }

  public void setQuantity(int quantity) {
    this.quantity = quantity;
  }

  public BigDecimal getUnitPrice() {
    return unitPrice;
  }

  public void setUnitPrice(BigDecimal unitPrice) {
    this.unitPrice = unitPrice;
  }
}
