package com.example.designpattern.patterns.facade;

public class VisitResult {
    public final int appointmentId;
    public final int recordId;
    public final int billId;

    public VisitResult(int appointmentId, int recordId, int billId) {
        this.appointmentId = appointmentId;
        this.recordId = recordId;
        this.billId = billId;
    }

    @Override
    public String toString() {
        return "appointment=" + appointmentId + ", record=" + recordId + ", bill=" + billId;
    }
}
