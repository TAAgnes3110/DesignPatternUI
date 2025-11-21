# Hướng dẫn chạy Demo Design Pattern với Database

Dự án này minh họa việc áp dụng các Design Pattern (Builder, Facade) trong Android, kết nối trực tiếp với PostgreSQL Database.

## 1. Cấu hình Database (pgAdmin 4)

Đảm bảo bạn đã có database từ https://github.com/TAAgnes3110/DesignPatterns/tree/main/database

### Bước 1: Thêm dữ liệu mẫu
Mở **pgAdmin 4**, chọn Database của bạn, mở **Query Tool** và chạy các lệnh trong phần **1. CHUẨN BỊ DỮ LIỆU** của file `schema.spl` và file "sample_data.sql".
Điều này sẽ tạo ra các Bác sĩ và Bệnh nhân để ứng dụng có thể hiển thị.

## 2. Chạy Ứng dụng Android

1.  Mở project trong Android Studio.
2.  Chạy ứng dụng trên máy ảo hoặc thiết bị thật.
3.  Tại màn hình chính, bạn có thể chọn demo cho **Builder Pattern** hoặc **Facade Pattern**.

---

## 3. Demo: Facade Pattern (Quản lý Đặt lịch)

### Chức năng
Minh họa việc sử dụng Facade để đơn giản hóa quy trình phức tạp gồm nhiều bước: Chọn Bác sĩ -> Đặt lịch -> Tạo hóa đơn.

### Hướng dẫn kiểm thử
1.  **Chọn Bác sĩ & Bệnh nhân**:
    *   Bấm vào ô **ID Bác sĩ** hoặc **ID Bệnh nhân** để chọn từ danh sách (dữ liệu lấy từ DB).
2.  **Đặt lịch & Tạo hóa đơn**:
    *   Chọn Ngày, Giờ.
    *   Nhập **Số tiền khám**.
    *   Bấm **Đặt lịch & Tạo hóa đơn**.
3.  **Kết quả**:
    *   App sẽ báo thành công và hiển thị thông tin hóa đơn.
    *   Kiểm tra trong Database: Bảng `Appointments` và `Billing` sẽ có dữ liệu mới.

### Cấu trúc Code (Facade)
*   **`HospitalFacade`**: Lớp trung gian. Phương thức `bookAppointment()` và `processBilling()` che giấu các thao tác phức tạp với DAO.
*   **`DoctorDAO`, `PatientDAO`, `AppointmentDAO`, `BillingDAO`**: Các lớp truy cập dữ liệu trực tiếp.
*   **`FacadePatternActivity`**: Gọi `HospitalFacade`, không can thiệp sâu vào logic xử lý.

---

## 4. Demo: Builder Pattern (Tạo Hồ sơ Bệnh nhân)

### Chức năng
Minh họa việc sử dụng Builder để tạo một đối tượng `Patient` phức tạp với nhiều thuộc tính tùy chọn một cách rõ ràng, dễ đọc.

### Hướng dẫn kiểm thử
1.  **Nhập thông tin**:
    *   Điền Tên, Họ, Giới tính, Ngày sinh, SĐT, Email.
2.  **Lưu thông tin**:
    *   Bấm nút **Lưu thông tin**.
    *   App sẽ dùng Builder để tạo đối tượng `Patient` và lưu vào Database.
3.  **Xem danh sách**:
    *   Bấm **Tải danh sách** để xem các bệnh nhân vừa tạo (lấy từ DB).

### Cấu trúc Code (Builder)
*   **`PatientBuilder`** (Interface): Định nghĩa các bước để xây dựng đối tượng Patient.
*   **`StandardPatientBuilder`**: Triển khai cụ thể, từng bước set dữ liệu (setName, setPhone...).
*   **`PatientDirector`**: (Tùy chọn) Điều phối quá trình build nếu cần quy trình chuẩn.
*   **`BuilderPatternActivity`**: Sử dụng Builder để tạo đối tượng từ giao diện nhập liệu.

---
