-- Insert Departments (5 records)
INSERT INTO Departments (department_name, location) VALUES
('Cardiology', 'Building A, Floor 1'),
('Neurology', 'Building A, Floor 2'),
('Pediatrics', 'Building B, Floor 1'),
('Orthopedics', 'Building B, Floor 2'),
('Emergency', 'Building C, Ground Floor');

-- Insert Doctors (5 records)
INSERT INTO Doctors (first_name, last_name, specialty, phone, email, department_id) VALUES
('Alice', 'Smith', 'Cardiologist', '0901111111', 'alice.smith@hospital.com', 1),
('Bob', 'Johnson', 'Neurologist', '0902222222', 'bob.johnson@hospital.com', 2),
('Charlie', 'Williams', 'Pediatrician', '0903333333', 'charlie.williams@hospital.com', 3),
('David', 'Brown', 'Orthopedic Surgeon', '0904444444', 'david.brown@hospital.com', 4),
('Eva', 'Davis', 'Emergency Physician', '0905555555', 'eva.davis@hospital.com', 5);

-- Insert Patients (5 records)
INSERT INTO Patients (first_name, last_name, date_of_birth, gender, contact_number, address, email, medical_history) VALUES
('John', 'Doe', '1985-05-15', 'M', '0911111111', '123 Main St', 'john.doe@email.com', 'None'),
('Jane', 'Roe', '1990-08-20', 'F', '0912222222', '456 Oak St', 'jane.roe@email.com', 'Asthma'),
('Michael', 'Man', '1978-12-01', 'M', '0913333333', '789 Pine St', 'michael.man@email.com', 'Diabetes'),
('Sarah', 'Woman', '1995-03-10', 'F', '0914444444', '321 Elm St', 'sarah.woman@email.com', 'Hypertension'),
('Chris', 'Child', '2015-06-01', 'M', '0915555555', '654 Maple St', 'chris.child@email.com', 'Peanut Allergy');

-- Insert Appointments (5 records)
INSERT INTO Appointments (patient_id, doctor_id, appointment_date, appointment_time, purpose, status) VALUES
(1, 1, '2025-12-01', '09:00:00', 'Routine Checkup', 'Completed'),
(2, 2, '2025-12-01', '10:00:00', 'Headache consultation', 'Scheduled'),
(3, 3, '2025-12-02', '09:30:00', 'Vaccination', 'Scheduled'),
(4, 4, '2025-12-02', '14:00:00', 'Knee pain', 'Scheduled'),
(5, 5, '2025-12-03', '08:00:00', 'Emergency check', 'Cancelled');

-- Insert Medical_Records (5 records - linked to Appointments)
-- Note: Only for completed or existing appointments
INSERT INTO Medical_Records (appointment_id, diagnosis, treatment, notes) VALUES
(1, 'Healthy', 'None', 'Patient is in good health'),
(2, 'Migraine', 'Prescribed painkillers', 'Follow up in 2 weeks'),
(3, 'Flu', 'Rest and fluids', 'Seasonal flu'),
(4, 'Arthritis', 'Physical therapy', 'Mild symptoms'),
(5, 'Food Poisoning', 'Hydration', 'Recovered');

-- Insert Medicine (5 records)
INSERT INTO Medicine (name, type, stock_quantity, unit_price, expiry_date) VALUES
('Paracetamol', 'Tablet', 1000, 5.00, '2026-01-01'),
('Ibuprofen', 'Tablet', 500, 8.50, '2025-12-31'),
('Amoxicillin', 'Capsule', 200, 12.00, '2025-06-30'),
('Cough Syrup', 'Liquid', 100, 15.00, '2025-09-15'),
('Vitamin C', 'Tablet', 2000, 3.00, '2027-01-01');

-- Insert Prescriptions (5 records)
INSERT INTO Prescriptions (record_id, medicine_id, quantity, dosage_instructions) VALUES
(1, 5, 30, 'Take 1 daily'),
(2, 1, 20, 'Take 2 when needed for pain'),
(3, 1, 10, 'Take 1 every 6 hours for fever'),
(4, 2, 30, 'Take 1 twice daily'),
(5, 4, 1, 'Take 10ml before sleep');

-- Insert Billing (5 records)
INSERT INTO Billing (appointment_id, total_amount, payment_status, payment_date) VALUES
(1, 50.00, 'Paid', '2025-12-01 10:00:00'),
(2, 100.00, 'Pending', NULL),
(3, 30.00, 'Pending', NULL),
(4, 150.00, 'Paid', '2025-12-02 15:00:00'),
(5, 0.00, 'Cancelled', NULL);

-- Insert Staff (5 records)
INSERT INTO Staff (full_name, role, department_id, phone) VALUES
('Mary Nurse', 'Nurse', 1, '0921111111'),
('Peter Tech', 'Technician', 2, '0922222222'),
('Paul Admin', 'Admin', 5, '0923333333'),
('Linda Reception', 'Receptionist', 1, '0924444444'),
('James Driver', 'Driver', 5, '0925555555');

-- Insert Accounts (5 records)
INSERT INTO Accounts (username, password_hash, role, doctor_id, staff_id) VALUES
('admin', 'hashed_password_123', 'Admin', NULL, 3),
('dralice', 'hashed_password_456', 'Doctor', 1, NULL),
('drbob', 'hashed_password_789', 'Doctor', 2, NULL),
('marynurse', 'hashed_password_abc', 'Staff', NULL, 1),
('lindarecep', 'hashed_password_def', 'Staff', NULL, 4);

-- Insert Services (5 records)
INSERT INTO Services (service_name, description, price) VALUES
('General Checkup', 'Comprehensive physical examination', 50.00),
('Blood Test', 'Complete blood count and basic metabolic panel', 30.00),
('X-Ray', 'Chest X-Ray for diagnostic imaging', 80.00),
('Dental Cleaning', 'Professional teeth cleaning and polishing', 45.00),
('MRI Scan', 'Magnetic Resonance Imaging for detailed body scan', 250.00);
