package com.example.designpattern.patterns.builder;

public class Patient {
    private final String firstName;
    private final String lastName;
    private final String gender;
    private final String dob;
    private final String phone;
    private final String email;

    private Patient(Builder builder) {
        this.firstName = builder.firstName;
        this.lastName = builder.lastName;
        this.gender = builder.gender;
        this.dob = builder.dob;
        this.phone = builder.phone;
        this.email = builder.email;
    }

    public String getFirstName() { return firstName; }
    public String getLastName() { return lastName; }
    public String getGender() { return gender; }
    public String getDob() { return dob; }
    public String getPhone() { return phone; }
    public String getEmail() { return email; }

    public static class Builder {
        private String firstName;
        private String lastName;
        private String gender;
        private String dob;
        private String phone;
        private String email;

        public Builder() {}

        public Builder setFirstName(String firstName) {
            this.firstName = firstName;
            return this;
        }

        public Builder setLastName(String lastName) {
            this.lastName = lastName;
            return this;
        }

        public Builder setGender(String gender) {
            this.gender = gender;
            return this;
        }

        public Builder setDob(String dob) {
            this.dob = dob;
            return this;
        }

        public Builder setPhone(String phone) {
            this.phone = phone;
            return this;
        }

        public Builder setEmail(String email) {
            this.email = email;
            return this;
        }

        public Patient build() {
            if (firstName == null || firstName.trim().isEmpty()) {
                throw new IllegalStateException("Patient First Name is required");
            }
            if (lastName == null || lastName.trim().isEmpty()) {
                throw new IllegalStateException("Patient Last Name is required");
            }
            return new Patient(this);
        }
    }
}
