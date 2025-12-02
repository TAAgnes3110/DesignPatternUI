-- 1. Bảng Khoa/Phòng ban (Departments)
CREATE TABLE Departments (
    department_id SERIAL PRIMARY KEY,
    department_name VARCHAR(100) NOT NULL UNIQUE,
    location VARCHAR(100)
);

-- 2. Bảng Bác sĩ (Doctors) - Có liên kết với Khoa
CREATE TABLE Doctors (
    doctor_id SERIAL PRIMARY KEY,
    first_name VARCHAR(50) NOT NULL,
    last_name VARCHAR(50) NOT NULL,
    specialty VARCHAR(100),
    phone VARCHAR(20) UNIQUE NOT NULL,
    email VARCHAR(100) UNIQUE,
    department_id INTEGER,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (department_id) REFERENCES Departments(department_id) ON DELETE SET NULL
);

-- 3. Bảng Bệnh nhân (Patients)
CREATE TABLE Patients (
    patient_id SERIAL PRIMARY KEY,
    first_name VARCHAR(50) NOT NULL,
    last_name VARCHAR(50) NOT NULL,
    date_of_birth DATE NOT NULL,
    gender CHAR(1) CHECK (gender IN ('M', 'F', 'O')),
    contact_number VARCHAR(20) UNIQUE NOT NULL,
    address VARCHAR(255),
    email VARCHAR(100),
    medical_history TEXT,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

-- 4. Bảng Cuộc hẹn (Appointments)
CREATE TABLE Appointments (
    appointment_id SERIAL PRIMARY KEY,
    patient_id INTEGER NOT NULL,
    doctor_id INTEGER NOT NULL,
    appointment_date DATE NOT NULL,
    appointment_time TIME NOT NULL,
    purpose VARCHAR(255),
    status VARCHAR(20) DEFAULT 'Scheduled' CHECK (status IN ('Scheduled', 'Completed', 'Cancelled')),
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (patient_id) REFERENCES Patients(patient_id) ON DELETE CASCADE,
    FOREIGN KEY (doctor_id) REFERENCES Doctors(doctor_id) ON DELETE CASCADE,
    -- Ràng buộc: Một bác sĩ không thể có 2 cuộc hẹn cùng ngày giờ
    CONSTRAINT unique_doctor_schedule UNIQUE (doctor_id, appointment_date, appointment_time)
);

-- 5. Bảng Hồ sơ bệnh án (Medical_Records)
CREATE TABLE Medical_Records (
    record_id SERIAL PRIMARY KEY,
    appointment_id INTEGER UNIQUE NOT NULL, -- 1 Cuộc hẹn -> 1 Hồ sơ
    diagnosis TEXT NOT NULL,
    treatment TEXT,
    notes TEXT,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (appointment_id) REFERENCES Appointments(appointment_id) ON DELETE CASCADE
);

-- 6. Bảng Thuốc (Medicine)
CREATE TABLE Medicine (
    medicine_id SERIAL PRIMARY KEY,
    name VARCHAR(100) NOT NULL UNIQUE,
    type VARCHAR(50),
    stock_quantity INTEGER CHECK (stock_quantity >= 0),
    unit_price DECIMAL(10, 2) NOT NULL,
    expiry_date DATE
);

-- 7. Bảng Đơn thuốc (Prescriptions)
CREATE TABLE Prescriptions (
    prescription_id SERIAL PRIMARY KEY,
    record_id INTEGER NOT NULL,
    medicine_id INTEGER NOT NULL,
    quantity INTEGER NOT NULL CHECK (quantity > 0),
    dosage_instructions VARCHAR(255),
    FOREIGN KEY (record_id) REFERENCES Medical_Records(record_id) ON DELETE CASCADE,
    FOREIGN KEY (medicine_id) REFERENCES Medicine(medicine_id) ON DELETE CASCADE
);

-- 8. Bảng Hóa đơn (Billing)
CREATE TABLE Billing (
    bill_id SERIAL PRIMARY KEY,
    appointment_id INTEGER UNIQUE NOT NULL,
    total_amount DECIMAL(10, 2) NOT NULL CHECK (total_amount >= 0),
    payment_status VARCHAR(20) DEFAULT 'Pending' CHECK (payment_status IN ('Pending', 'Paid', 'Cancelled')),
    payment_date TIMESTAMP,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (appointment_id) REFERENCES Appointments(appointment_id) ON DELETE CASCADE
);

-- 9. Bảng Nhân viên (Staff) - Ngoài bác sĩ
CREATE TABLE Staff (
    staff_id SERIAL PRIMARY KEY,
    full_name VARCHAR(100) NOT NULL,
    role VARCHAR(50) NOT NULL, -- Nurse, Receptionist, Admin
    department_id INTEGER,
    phone VARCHAR(20),
    hire_date DATE DEFAULT CURRENT_DATE,
    FOREIGN KEY (department_id) REFERENCES Departments(department_id) ON DELETE SET NULL
);

-- 10. Bảng Tài khoản hệ thống (Accounts)
CREATE TABLE Accounts (
    account_id SERIAL PRIMARY KEY,
    username VARCHAR(50) UNIQUE NOT NULL,
    password_hash VARCHAR(255) NOT NULL,
    role VARCHAR(20) CHECK (role IN ('Admin', 'Doctor', 'Staff')),
    doctor_id INTEGER UNIQUE, -- Nếu là bác sĩ
    staff_id INTEGER UNIQUE,  -- Nếu là nhân viên khác
    FOREIGN KEY (doctor_id) REFERENCES Doctors(doctor_id) ON DELETE CASCADE,
    FOREIGN KEY (staff_id) REFERENCES Staff(staff_id) ON DELETE CASCADE
);

-- 11. Bảng Dịch vụ (Services)
CREATE TABLE Services (
    service_id SERIAL PRIMARY KEY,
    service_name VARCHAR(100) NOT NULL UNIQUE,
    description TEXT,
    price DECIMAL(10, 2) NOT NULL CHECK (price >= 0)
);

-- 12. Bảng Chi tiết Dịch vụ (Appointment_Services)
CREATE TABLE Appointment_Services (
    app_service_id SERIAL PRIMARY KEY,
    appointment_id INTEGER NOT NULL,
    service_id INTEGER NOT NULL,
    quantity INTEGER DEFAULT 1 CHECK (quantity > 0),
    unit_price DECIMAL(10, 2) NOT NULL,
    FOREIGN KEY (appointment_id) REFERENCES Appointments(appointment_id) ON DELETE CASCADE,
    FOREIGN KEY (service_id) REFERENCES Services(service_id) ON DELETE CASCADE
);

-- Indexes for optimization
CREATE INDEX idx_patients_name ON Patients(last_name, first_name);
CREATE INDEX idx_appointments_date ON Appointments(appointment_date);
CREATE INDEX idx_appointment_services ON Appointment_Services(appointment_id);
