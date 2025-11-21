package com.example.designpattern.patterns.builder;

public class PatientDirector {
    private final PatientBuilder builder;

    public PatientDirector(PatientBuilder builder) {
        this.builder = builder;
    }

    public Patient buildPatient() {
        return builder.build();
    }
}
