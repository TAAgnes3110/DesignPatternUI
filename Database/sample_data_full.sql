-- 1. Departments
INSERT INTO Departments (department_name, location) VALUES
('Cardiology', 'Building A, Floor 1'),
('Neurology', 'Building A, Floor 2'),
('Pediatrics', 'Building B, Floor 1'),
('Orthopedics', 'Building B, Floor 2'),
('Dermatology', 'Building C, Floor 1');

-- 2. Doctors
INSERT INTO Doctors (first_name, last_name, specialty, phone, email, department_id) VALUES
('John', 'Smith', 'Cardiologist', '0901234567', 'john.smith@hospital.com', 1),
('Sarah', 'Johnson', 'Neurologist', '0902345678', 'sarah.johnson@hospital.com', 2),
('Michael', 'Williams', 'Pediatrician', '0903456789', 'michael.williams@hospital.com', 3),
('Emily', 'Brown', 'Orthopedic Surgeon', '0904567890', 'emily.brown@hospital.com', 4),
('David', 'Jones', 'Dermatologist', '0905678901', 'david.jones@hospital.com', 5);

-- 3. Patients
INSERT INTO Patients (first_name, last_name, date_of_birth, gender, contact_number, address, email, medical_history) VALUES
('Alice', 'Davis', '1985-04-12', 'F', '0912345678', '123 Main St, Hanoi', 'alice.davis@email.com', 'None'),
('Bob', 'Miller', '1990-08-25', 'M', '0913456789', '456 Le Loi, HCM', 'bob.miller@email.com', 'Allergic to Penicillin'),
('Charlie', 'Wilson', '2010-12-05', 'M', '0914567890', '789 Nguyen Hue, Da Nang', 'charlie.wilson@email.com', 'Asthma'),
('Diana', 'Moore', '1978-02-15', 'F', '0915678901', '321 Tran Hung Dao, Can Tho', 'diana.moore@email.com', 'Hypertension'),
('Ethan', 'Taylor', '1995-06-30', 'M', '0916789012', '654 Ba Trieu, Hue', 'ethan.taylor@email.com', 'Diabetes Type 2');

-- 4. Appointments
INSERT INTO Appointments (patient_id, doctor_id, appointment_date, appointment_time, purpose, status) VALUES
(1, 1, '2023-10-25', '09:00:00', 'Annual Heart Checkup', 'Completed'),
(2, 2, '2023-10-26', '10:30:00', 'Headache Consultation', 'Scheduled'),
(3, 3, '2023-10-27', '14:00:00', 'Vaccination', 'Scheduled'),
(4, 4, '2023-10-28', '11:00:00', 'Knee Pain Assessment', 'Cancelled'),
(5, 5, '2023-10-29', '15:30:00', 'Skin Rash Examination', 'Scheduled');

-- 5. Medical_Records
INSERT INTO Medical_Records (appointment_id, diagnosis, treatment, notes) VALUES
(1, 'Normal Heart Rhythm', 'Maintain healthy diet', 'Follow up in 6 months'),
(2, 'Migraine', 'Prescribed painkillers', 'Avoid stress'),
(3, 'Healthy Child', 'Administered Flu Vaccine', 'Monitor for fever'),
(4, 'Mild Arthritis', 'Physical Therapy recommended', 'Patient cancelled follow-up'),
(5, 'Eczema', 'Topical cream prescribed', 'Keep skin moisturized');

-- 6. Medicine
INSERT INTO Medicine (name, type, stock_quantity, unit_price, expiry_date) VALUES
('Paracetamol 500mg', 'Tablet', 1000, 0.50, '2025-12-31'),
('Ibuprofen 400mg', 'Tablet', 800, 0.75, '2025-06-30'),
('Amoxicillin 250mg', 'Capsule', 500, 1.20, '2024-11-30'),
('Vitamin C 1000mg', 'Effervescent', 200, 0.30, '2026-01-01'),
('Hydrocortisone Cream', 'Tube', 150, 5.00, '2025-08-15');

-- 7. Prescriptions
INSERT INTO Prescriptions (record_id, medicine_id, quantity, dosage_instructions) VALUES
(1, 4, 30, 'Take 1 tablet daily'),
(2, 1, 20, 'Take 1 tablet every 6 hours for pain'),
(3, 1, 10, 'Take half tablet if fever occurs'),
(4, 2, 30, 'Take 1 tablet twice daily after meals'),
(5, 5, 2, 'Apply to affected area twice daily');

-- 8. Billing
INSERT INTO Billing (appointment_id, total_amount, payment_status, payment_date) VALUES
(1, 150.00, 'Paid', '2023-10-25 10:00:00'),
(2, 80.00, 'Pending', NULL),
(3, 50.00, 'Pending', NULL),
(4, 0.00, 'Cancelled', NULL),
(5, 60.00, 'Pending', NULL);

-- 9. Staff
INSERT INTO Staff (full_name, role, department_id, phone, hire_date) VALUES
('Linda White', 'Nurse', 1, '0921234567', '2020-01-15'),
('Robert Green', 'Receptionist', 1, '0922345678', '2019-05-20'),
('Susan Hall', 'Nurse', 3, '0923456789', '2021-03-10'),
('James King', 'Admin', NULL, '0924567890', '2018-11-01'),
('Patricia Scott', 'Nurse', 5, '0925678901', '2022-07-01');

-- 10. Accounts
INSERT INTO Accounts (username, password_hash, role, doctor_id, staff_id) VALUES
('admin_james', 'hash123', 'Admin', NULL, 4),
('dr_john', 'hash456', 'Doctor', 1, NULL),
('dr_sarah', 'hash789', 'Doctor', 2, NULL),
('nurse_linda', 'hashabc', 'Staff', NULL, 1),
('reception_bob', 'hashdef', 'Staff', NULL, 2);

-- 11. Services
INSERT INTO Services (service_name, description, price) VALUES
('General Consultation', 'Standard checkup with a doctor', 30.00),
('Blood Test', 'Complete Blood Count (CBC)', 20.00),
('X-Ray', 'Chest X-Ray', 50.00),
('Vaccination', 'Flu Shot', 25.00),
('MRI Scan', 'Full body MRI scan', 200.00);

-- 12. Appointment_Services
INSERT INTO Appointment_Services (appointment_id, service_id, quantity, unit_price) VALUES
(1, 1, 1, 30.00),
(1, 2, 1, 20.00),
(2, 1, 1, 30.00),
(3, 4, 1, 25.00),
(5, 1, 1, 30.00);
