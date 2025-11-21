
<a name="_hlk206533123"></a>**TRƯỜNG ĐẠI HỌC GIAO THÔNG VẬN TẢI**

**KHOA CÔNG NGHỆ THÔNG TIN**

![](Aspose.Words.da648758-6e00-4531-9306-dcf6836844fb.001.jpeg)![](Aspose.Words.da648758-6e00-4531-9306-dcf6836844fb.002.png)












**BÁO CÁO DESIGN PATTERN**




**GIẢNG VIÊN HƯỚNG DẪN:**

**TS. TRẦN VĂN DŨNG**

**SINH VIÊN THỰC HIỆN:**

**VŨ TUẤN KIỆT (L) – 223630694**











**Hà Nội, tháng 9 năm 2025**
# **MỤC LỤC**
[**1.**	**Singleton.	**3****](#_toc213759696)

[**2.**	**Factory Method.	**5****](#_toc213759697)

[**3.**	**AbstractFactory.	**7****](#_toc213759698)

[**4.**	**Adapter.	**9****](#_toc213759699)

[**5.**	**Builder.	**11****](#_toc213759700)

[**6.**	**Command.	**13****](#_toc213759701)

[**7.**	**Façade.	**15****](#_toc213759702)

[**8.**	**Decorator.	**17****](#_toc213759703)

[**9.**	**Observer.	**19****](#_toc213759704)

[**10.**	**State Pattern.	**21****](#_toc213759705)

[**11.**	**Strategy Pattern.	**23****](#_toc213759706)

[**12.**	**TemplateMethod.	**25****](#_toc213759707)



**
1. # <a name="_toc213759696"></a>**Singleton.**
**Mô tả bài toán:** Giả sử bạn đang xây dựng một hệ thống quản lý bệnh viện với nhiều module khác nhau (PatientService, AppointmentService, BillingService, ...), và mọi module đều cần kết nối đến database để thao tác dữ liệu.

**Yêu cầu:**

- Chỉ có một đối tượng DatabaseConnection duy nhất trong toàn bộ hệ thống để quản lý kết nối database.
- Tất cả các module khác đều dùng chung DatabaseConnection này.
- DatabaseConnection cần cung cấp các phương thức:
  - getConnection(): Lấy connection từ database
  - closeConnection(): Đóng connection
  - testConnection(): Kiểm tra kết nối
  - Load cấu hình từ file database.properties
- Chống lại Reflection, Serialization, Cloning, ClassLoader khác nhau, đảm bảo đối tượng DatabaseConnection thật sự duy nhất (sử dụng Double-Checked Locking với volatile).

**Lý do sử dụng:**

- **Nếu ta không dùng Singleton**: Mỗi khi một module cần kết nối database, ta sẽ new DatabaseConnection() ở nhiều nơi. Điều này dẫn đến:
  - Tốn tài nguyên (nhiều kết nối database thừa thãi)
  - Khó quản lý và kiểm soát kết nối tập trung
  - Có thể gây ra vấn đề về hiệu năng và rò rỉ bộ nhớ (memory leak)
  - Khó theo dõi và debug các vấn đề liên quan đến database
- **Dùng Singleton**:
  - Đảm bảo chỉ có 1 instance duy nhất của DatabaseConnection
  - Dễ dàng quản lý, theo dõi và mở rộng (ví dụ thay đổi cấu hình database, thêm connection pooling)
  - Giúp toàn bộ hệ thống dùng cùng một kết nối database thống nhất
  - Tối ưu hiệu năng và tài nguyên hệ thống

![A screenshot of a computer&#x0A;&#x0A;AI-generated content may be incorrect.](Aspose.Words.da648758-6e00-4531-9306-dcf6836844fb.003.png)

![A screenshot of a computer&#x0A;&#x0A;AI-generated content may be incorrect.](Aspose.Words.da648758-6e00-4531-9306-dcf6836844fb.004.png)


















1. # <a name="_toc213759697"></a>**Factory Method.**
**Mô tả bài toán:** Giả sử bạn đang xây dựng một hệ thống quản lý nhân viên bệnh viện. Hệ thống cần tạo các loại nhân viên khác nhau như: Bác sĩ (Doctor), Y tá (Nurse), và Nhân viên hành chính (Admin). Mỗi loại nhân viên có các thuộc tính và tham số khởi tạo khác nhau.

**Yêu cầu:**

- Tạo các loại nhân viên khác nhau (Doctor, Nurse, Admin) thông qua một interface thống nhất.
- Mỗi loại nhân viên có các tham số khởi tạo khác nhau:
  - Doctor: cần specialty (chuyên khoa)
  - Nurse: cần specialization và shiftHours (ca làm việc)
  - Admin: cần department (phòng ban)
- Cho phép dễ dàng thêm các loại nhân viên mới trong tương lai mà không cần sửa đổi code hiện có.
- Ẩn logic tạo đối tượng phức tạp khỏi client code.

**Lý do sử dụng:**

- **Nếu ta không dùng Factory Method**: Mỗi khi cần tạo một nhân viên, client code phải:
  - Biết chi tiết về cách tạo từng loại nhân viên
  - Sử dụng nhiều câu lệnh if-else hoặc switch-case để phân biệt loại nhân viên
  - Dễ dàng tạo ra đối tượng không hợp lệ nếu truyền sai tham số
  - Code trở nên phức tạp và khó bảo trì khi thêm loại nhân viên mới
- **Dùng Factory Method**:
  - Tách biệt logic tạo đối tượng khỏi client code
  - Dễ dàng mở rộng để thêm loại nhân viên mới (chỉ cần tạo Creator mới)
  - Đảm bảo tính nhất quán trong cách tạo đối tượng
  - Code dễ đọc, dễ hiểu và tuân thủ nguyên tắc Open/Closed Principle

![A diagram of a computer program&#x0A;&#x0A;AI-generated content may be incorrect.](Aspose.Words.da648758-6e00-4531-9306-dcf6836844fb.005.png)

![A screenshot of a computer&#x0A;&#x0A;AI-generated content may be incorrect.](Aspose.Words.da648758-6e00-4531-9306-dcf6836844fb.006.png)















1. # <a name="_toc213759698"></a>**AbstractFactory.**
**Mô tả bài toán:** Giả sử bạn đang xây dựng một hệ thống quản lý bệnh viện cần làm việc với database. Hệ thống có nhiều loại DAO (Data Access Object) như PatientDAO, AppointmentDAO. Tuy nhiên, có hai cách phát triển khai khác nhau:

- StandardDAOFactory: Tạo các DAO cơ bản cho môi trường phát triển.
- OptimizedDAOFactory: Tạo các DAO tối ưu với caching và connection pooling cho môi trường production.

**Yêu cầu:**

- Tạo các families của các đối tượng DAO liên quan (PatientDAO, AppointmentDAO) mà không cần chỉ định các class cụ thể.
- Đảm bảo các DAO được tạo từ cùng một factory sẽ tương thích với nhau
- Cho phép chuyển đổi giữa các families một cách dễ dàng (từ Standard sang Optimzed) mà không cần sửa đổi client code.
- Dễ dàng thêm các DAO mới (ví dụ: Biling DAO vào cả hai families).

**Lý do sử dụng:**

- Nếu ta không dùng Abstract Factory: Client code phải:
- Biết chi tiết về từng loại DAO và cách tạo chúng.
- Tự quản lý việc đảm bảo các DAO tương thích với nhau.
- Sử dụng nhiều if – elsse để phân biệt loại Factory.
- Code trở nên phức tạp và khó bảo trì khi thêm DAO mới hoặc family mới.
- Dùng Abstract Factory:
- Đảm bảo các đối tượng trong cùng một family luôn tương thích.
- Dễ dàng thay đổi family (từ Standard sang Optimized) mà không ảnh hưởng client code.
- Tách biệt logic tạo đối tượng khỏi client code.
- Tuân thủ nguyên tắc Dependency Inversion Principle.

![A diagram of a company&#x0A;&#x0A;AI-generated content may be incorrect.](Aspose.Words.da648758-6e00-4531-9306-dcf6836844fb.007.png)

![A screen shot of a computer&#x0A;&#x0A;AI-generated content may be incorrect.](Aspose.Words.da648758-6e00-4531-9306-dcf6836844fb.008.png)
1. # <a name="_toc213759699"></a>**Adapter.**
**Mô tả bài toán:** Giả sử bạn đang xây dựng một hệ thống thanh toán mới cho bệnh viện với interface PaymentSystem. Tuy nhiên, hệ thống cũ (LegacyPaymentSystem) vẫn đang được sử dụng và có interface khác:

- PaymentSystem: Sử dụng BigDecimal và int PatientId.
- LegacyPaymentSystem: Sử dụng Double và String patientName.

**Yêu cầu:**

- Cho phép hệ thống mới sử dụng LegacyPaymentSystem thông qua interface PaymentSystem.
- Không cần sửa đổi code của LegacyPaymentSystem (có thể là code cũ, đã được test kỹ, hoặc không có quyền sửa).
- Chuyển đổi giữa các interface không tương thích (BigDecimal ↔ Double, int patientId ↔ String patientName).
- Dễ dàng thay thế LegacyPaymentSystem bằng hệ thống mới trong tương lai mà không cần sửa đổi client code.

**Lý do sử dụng:**

- Nếu ta không dùng Adapter:
- Sửa đổi LegacyPaymentSystem để phù hợp với PaymentSystem (nhưng có thể không được phép hoặc rủi ro cao).
- Viết lại toàn bộ LegacyPaymentSystem (tốn kém thời gian và công việc).
- Client code phải xử lý cả hai interface, làm code phức tạp.
- Khó bảo trì khi phải quản lý nhiều interface kh
- Nếu ta dùng Adapter:
- Tái sử dụng code cũ mà không cần sửa đổi.
- Cho phép các hệ thống không tương thích làm việc cùng nhau.
- Dễ dàng thay thế implementation trong tương lai: chỉ cần thay adapter.
- Client code chỉ cần làm việc với một interface (PaymentSystem).
- Tuân thủ nguyên tắc Open/Closed Principle.
- Giảm rủi ro khi tích hợp code cũ.

![A screenshot of a computer&#x0A;&#x0A;AI-generated content may be incorrect.](Aspose.Words.da648758-6e00-4531-9306-dcf6836844fb.009.png)

![A screenshot of a computer program&#x0A;&#x0A;AI-generated content may be incorrect.](Aspose.Words.da648758-6e00-4531-9306-dcf6836844fb.010.png)




1. # <a name="_toc213759700"></a>**Builder.**
**Mô tả bài toán:** Giả sử bạn đang xây dựng một hệ thống quản lý bệnh nhân. Đối tượng Patient có rất nhiều thuộc tính: firstName, lastName, dateOfBirth, contactNumber, gender, address, email, medicalHistory. Không phải lúc nào cũng cần tất cả các thuộc tính, và việc tạo đối tượng với constructor có quá nhiều tham số sẽ rất khó đọc và nhầm lẫn.

**Yêu cầu:**

- Xây dựng đối tượng Patient từng bước một, chỉ cần set các thuộc tính cần thiết.
- Cho phép tạo đối tượng với các thuộc tính tùy chọn khác nhau mà không cần tạo nhiều constructor overload.
- Code dễ đọc và dễ hiểu khi tạo đối tượng Patient.
- Hỗ trợ phương thức chain (fluent interface) để code gọn gàng hơn.
- Đảm bảo đối tượng được tạo ra hợp lệ (có thể thêm validation trong method build()).

**Lý do sử dụng:**

- Nếu ta không dùng Builder:
- Tạo nhiều constructor với các tham số khác nhau (telescoping constuctor anti – pattern)
- Sử dụng JavaBean pattern với setter, nhưng đối tượng có thể ở trạng thái không nhất quán trong quá trình tạo.
- Code khó đọc khi có quá nhiều tham số.
- Nếu ta dùng Builder:
- Code dễ đọc và tự giải thích.
- Linh hoạt trong việc set các thuộc tính tùy chọn.
- Đảm bảo đối tượng được tạo ra là immutable và hợp lệ.
- Dễ dàng thêm validation trong method build().

![A screenshot of a computer](Aspose.Words.da648758-6e00-4531-9306-dcf6836844fb.011.png)

![A screen shot of a computer&#x0A;&#x0A;AI-generated content may be incorrect.](Aspose.Words.da648758-6e00-4531-9306-dcf6836844fb.012.png)




1. # <a name="_toc213759701"></a>**Command.**
**Mô tả bài toán:** Giả sử bạn đang xây dựng một hệ thống quản lý lịch hẹn cho bệnh viện. Hệ thống cần thực hiện các thao tác như: tạo lịch hẹn (Create), cập nhật lịch hẹn (Update), hủy lịch hẹn (Cancel). Ngoài ra, hệ thống cần hỗ trợ: - Undo/Redo: hoàn tác các thao tác đã thực hiện - Logging: ghi lại lịch sử các thao tác - Queue: xếp hàng các thao tác để thực hiện sau.

**Yêu cầu:**

- Đóng gói mỗi thao tác thành một đối tượng Command riêng biệt.
- Hỗ trợ undo/redo: mỗi command có thể thực hiện (execute) và hoàn tác (undo).
- Hỗ trợ macro command: thực hiện nhiều command cùng lúc.
- Tách biệt đối tượng gọi thao tác (invoker) khỏi đối tượng thực hiện thao tác (receiver).

**Lý do sử dụng:**

- Nếu ta không dùng Command:
- Gọi trực tiếp các phương thức của AppointmentDAO trong client code.
- Khó hỗ trợ undo/redo: phải tự quản lý trạng thái trước khi thực hiện thao tác.
- Khó logging: phải thêm code logging vào nhiều nơi.
- Khó hỗ trợ queue hoặc batch processing.
- Client code phải biết chi tiết về cách thực hiện từng thao tác.
- Nếu ta dùng Command:
- Đóng gói thao tác thành đối tượng, dễ dàng truyền, lưu trữ, và thực thi.
- Dễ dàng hỗ trợ undo/redo: mỗi command biết cách hoàn tác chính nó.
- Dễ dàng logging: có thể log trước/sau khi thực thi command.
- Hỗ trợ macro command và batch processing.
- Tách biệt invoker và receiver, giảm coupling.
- Tuân thủ nguyên tác Single Responsibility Principle

![A diagram of a computer&#x0A;&#x0A;AI-generated content may be incorrect.](Aspose.Words.da648758-6e00-4531-9306-dcf6836844fb.013.png)

![A screen shot of a computer&#x0A;&#x0A;AI-generated content may be incorrect.](Aspose.Words.da648758-6e00-4531-9306-dcf6836844fb.014.png)





1. # <a name="_toc213759702"></a>**Façade.**
**Mô tả bài toán:** Giả sử bạn đang xây dựng một hệ thống quản lý bệnh viện với nhiều subsytem phức tạp: 

- PatientDAO: quản lý thông tin bệnh nhân.
- AppointmentDAO: quản lý lịch hẹn.
- BillingDAO: quản lý thanh toán.

Client code cần tương tác với nhiều subsystem này để thực hiện một tác vụ đơn giản. Ví dụ: để đăng ký bệnh nhân và tạo lịch hẹn, client phải: Tạo Patient thông qua PatientDAO → Tạo Appointment thông qua AppointmentDAO → Có thể cần tạo Biling thông qua BillingDAO

**Yêu cầu:**

- Cung cấp một giao diện đơn giản để truy cập các subsystem phức tạp.
- Ẩn đi sự phức tạp của việc tương tác với nhiều subsystem từ client code.
- Đơn giản hóa các tác vụ thường xuyên (ví dụ: registerPatient, bookAppointment, processBilling).

**Lý do sử dụng:**

- Nếu ta không dùng Façade Client code phải:
- Biết chi tiết về cách sử dụng từng subsystem (PatientDAO, AppointmentDAO, BillingDAO).
- Tự quản lý việc gọi các phương thức từ nhiều subsystem.
- Code trở nên phức tạp và khó đọc.
- Khó bảo trì: nếu logic thay đổi, phải sửa ở nhiều nơi.
- Tạo sự phụ thuộc chặt chẽ giữa client code và các subsystem.
- Nếu ta dùng Command:
- Đơn giản hóa giao diện cho client code.
- Giảm coupling giữa client code và các subsytem.
- Dễ dàng thay đổi implementation của các subsystem mà không ảnh hưởng client code.
- Code dễ đọc và dễ bảo trì

![A diagram of a patient](Aspose.Words.da648758-6e00-4531-9306-dcf6836844fb.015.png)

![A black screen with a white text&#x0A;&#x0A;AI-generated content may be incorrect.](Aspose.Words.da648758-6e00-4531-9306-dcf6836844fb.016.png)







1. # <a name="_toc213759703"></a>**Decorator.**
**Mô tả bài toán:** Giả sử bạn đang xây dựng một hệ thống quản lý hồ sơ y tế cho bệnh viện. Hệ thống cần lưu trữ hồ sơ y tế cơ bản (BasicMedicalRecord). Tuy nhiên, tùy theo yêu cầu, hồ sơ có thể cần thêm các tính năng:

- Mã hóa (Encryption): để bảo mật thông tin nhạy cảm.
- Ký số (Digital Signature): để đảm bảo tính toàn vẹn.
- Audit Log: để ghi lại lịch sử truy cập và chỉnh sửa.

Các tính năng này có thể được kết hợp với nhau (ví dụ: vừa mã hóa vừa có audit log).

**Yêu cầu:**

- Cho phép thêm các tính năng động cho hồ sơ y tế mà không cần sửa đổi class gốc (BasicMedicalRecord).
- Có thể kết hợp nhiều decorator với nhau (ví dụ: EncryptedMedicalRecordDecorator + SignedMedicalRecordDecorator + AuditLogMedicalRecordDecorator).
- Dễ dàng thêm decorator mới (ví dụ: CompressedMedicalRecordDecorator) mà không cần sửa đổi code hiện có.
- Đảm bảo tính nhất quán: decorator có thể được thêm/ bỏ một cách linh hoạt.

**Lý do sử dụng:**

- Nếu ta không dùng Decorator phải:
- Tạo nhiều subclass kết hợp các tính năng: BasicMedicalRecord, EncryptedMedicalRecord, SignedMedicalRecord, EncryptedAndSignedMedicalRecord, … (combinatorial explosion).
- Hoặc thêm tất cả tính năng vào BasicMedicalRecord, nhưng không phải lúc nào cũng cần tất cả.
- Khó mở rộng: mỗi khi thêm tính năng mới, phải tạo nhiều subclass mới.
- Code trở nên phức tạp và khó bảo trì.

- Nếu ta dùng Decorator:
- Linh hoạt trong việc thêm/bỏ tính năng tại runtime.
- Tránh được vấn đề combinatorial explosion của subclass.
- Dễ dàng kết hợp các tinh năng: chỉ cần wrap decorator này bằng decorator khác.
- Dễ dàng thêm decorator mới: chỉ cần tạo class mới extend MedicalRecordDecorator
- Tuân thủ nguyên tác Open/Closed Principle.
- Cho phép thêm tính năng mà không ảnh hưởng đến đối tượng gốc.

![A diagram of a medical record&#x0A;&#x0A;AI-generated content may be incorrect.](Aspose.Words.da648758-6e00-4531-9306-dcf6836844fb.017.png)

![A screenshot of a computer program&#x0A;&#x0A;AI-generated content may be incorrect.](Aspose.Words.da648758-6e00-4531-9306-dcf6836844fb.018.png)

1. # <a name="_toc213759704"></a>**Observer.**
**Mô tả bài toán:** Giả sử bạn đang xây dựng một hệ thống quản lý lịch hẹn cho bệnh viện. Khi có thay đổi về lịch hẹn (ví dụ: lịch hẹn bị hủy, thời gian thay đổi, lịch hẹn được xác nhận), nhiều đối tượng khác nhau cần được thông báo:

- Bệnh nhân  (Patient) cần nhận thông báo qua SMS hoặc email.
- Bác sĩ (Doctor) cần nhận thông báo để cập nhật lịch làm việc.
- Hệ thống quản lý cần ghi log.

**Yêu cầu:**

- Khi trạng thái của Appointment thay đổi, tự động thông báo cho tất cả đối tượng đã đăng ký (subscribe).
- Cho phép thêm hoặc xóa các observer một cách linh hoạt (ví dụ: bệnh nhân có thể đăng ký hoặc hủy đăng ký nhận thông báo).
- Tách biệt logic thông báo khỏi logic quản lý Appointment.
- Hỗ trợ nhiều loại observer khác nhau với cách xử lý thông báo khác nhau.

**Lý do sử dụng:**

- Nếu ta không dùng Observer phải:
- Appointment phải biết và gọi trực tiếp các phương thức của Patient, Doctor, NotificationService.
- Tạo sự phụ thuộc chặt chẽ (tight coupling) giữa Appointment.
- Vi phạm nguyên tắc Open/Closed Principle.
- Code trở nên phức tạp và khó bảo trì.
- Nếu ta dùng Decorator:
- Tách biệt logic thông báo khỏi logic quản lý Appointment.
- Appointment không cần biết chi tiết về các observer.
- Dễ dạng thêm hoặc xóa observer mà không cần sửa đổi Appointment.
- Hỗ trợ mối quan hệ một-nhiều (one-to-many) giữa subject và observers.
- Tuân thủ nguyên tắc Dependency Inversion Principle.

![A diagram of a computer&#x0A;&#x0A;AI-generated content may be incorrect.](Aspose.Words.da648758-6e00-4531-9306-dcf6836844fb.019.png)

![A screenshot of a computer program&#x0A;&#x0A;AI-generated content may be incorrect.](Aspose.Words.da648758-6e00-4531-9306-dcf6836844fb.020.png)



1. # <a name="_toc213759705"></a>**State Pattern.**
**Mô tả bài toán:** Giả sử bạn đang xây dựng một hệ thống quản lý lịch hẹn cho bệnh viện. Mỗi lịch hẹn có thể ở các trạng thái khác nhau: Scheduled (Đã đặt), Confirmed (Đã xác nhận), Completed (Đã hoàn thành), Cancelled (Đã hủy). Mỗi trạng thái có các hành vi và quy tắc chuyển đổi khác nhau.

**Yêu cầu:**

- Quản lý các trạng thái của Appointment một cách có tổ chức.
- Mỗi trạng thái biết được các trạng thái có thể chuyển đến tiếp theo (ví dụ: Scheduled có thể chuyển sang Confirmed hoặc Cancelled, nhưng không thể chuyển trực tiếp sang Completed).
- Mỗi trạng thái có thể có hành vi riêng (ví dụ: khi ở trạng thái Completed, không thể cập nhật thông tin).
- Dễ dàng thêm trạng thái mới (ví dụ: Rescheduled – Đã dời lịch) mà không cần sửa đổi code hiện có.

**Lý do sử dụng:**

- Nếu ta không dùng State phải:
- Sử dụng nhiều if-else hoặc switch-case để kiểm tra trạng thái và xử lý logic.
- Code trong AppointmentContext sẽ rất dài và phức tạp.
- Khó mở rộng: mỗi khi thêm trạng thái mới, phải sửa đổi nhiều nơi.
- Logic chuyển đổi trạng thái bị rải rác, khó kiểm soát.
- Dễ xảy ra lỗi khi quy tắc chuyển đổi trạng thái phức tạp.
- Nếu ta dùng State:
- Tách biệt logic của từng trạng thái thành các class riêng.
- Dễ dàng quản lý và kiểm soát quy tắc chuyển đổi trạng thái.
- Dễ dàng thêm trạng thái mới: chỉ cần tạo class mới imlenment AppointmentState.
- Code dễ đọc và dễ bảo trì.
- Tuân thủ nguyên tắc Open/Closed Principle.
- Loại bỏ các câu lệnh điều kiện phức tạp.

![A diagram of a application&#x0A;&#x0A;AI-generated content may be incorrect.](Aspose.Words.da648758-6e00-4531-9306-dcf6836844fb.021.png)

![A screenshot of a computer&#x0A;&#x0A;AI-generated content may be incorrect.](Aspose.Words.da648758-6e00-4531-9306-dcf6836844fb.022.png)







1. # <a name="_toc213759706"></a>**Strategy Pattern.**
**Mô tả bài toán:** Giả sử bạn đang xây dựng một hệ thống thanh toán cho bệnh viện. Bệnh nhân có thể thanh toán bằng nhiều phương thức khác nhau: Tiền mặt (Cash), thẻ tín dụng (Credit Card), hoặc bảo hiểm (Insurance). Mỗi phương thức thanh toán có logic xử ý kh

**Yêu cầu:**

- Cho phép chọn phương thức thanh toán linh hoạt tại runtime.
- Mỗi phương thức thanh toán có thể có các tham số riêng:
- Cash: không cần tham số.
- Credit Card: cần cardNumber và cardHolder.
- Insurance: cần insuranceCompany và policyNumber.
- Dễ dàng thêm phương thức thanh toán mới trong tương lai (ví dụ: PayPal, Bank Transfer)
- Client code không cần biết chi tiết về cách từng phương thức thanh toán hoạt động.

**Lý do sử dụng:**

- Nếu ta không dùng Strategy phải:
- Sử dụng nhiều câu lệnh if-else hoặc switch-case để xử lý từng phương thức thanh toán.
- Code trong PaymentProcessor sẽ rất dài và phức tạp.
- Khó mở rộng: mỗi khi thêm phương thức mới, phải sửa đổi PaymentProcessor.
- Vi phạm nguyên tắc Open/Closed Principle.
- Logic của từng phương thức thanh toán bị rải rác, khó kiểm thử.
- Nếu ta dùng Strategy:
- Tách biệt logic của từng phương thức thanh toán thành các class riêng.
- Dễ dàng thêm phương thức mới: chỉ cần tạo class mới implement PaymentStrategy.
- Client code đơn giản và dễ hiểu.
- Dễ dàng kiểm thử từng strategy độc lập.
- Tuân thủ nguyên tắc Open/Closed Principle và Single Responsibility Principle.

![A diagram of a payment method&#x0A;&#x0A;AI-generated content may be incorrect.](Aspose.Words.da648758-6e00-4531-9306-dcf6836844fb.023.png)

![A screenshot of a computer&#x0A;&#x0A;AI-generated content may be incorrect.](Aspose.Words.da648758-6e00-4531-9306-dcf6836844fb.024.png)







1. # <a name="_toc213759707"></a>**TemplateMethod.**
**Mô tả bài toán:** Giả sử bạn đang xây dựng một hệ thống tạo báo cáo cho bệnh viện. Hệ thống cần tạo nhiều loại báo cáo khác nhau: Báo cáo bệnh nhân (Patient Report), Báo cáo lịch hẹn (Appointment Report), Báo cáo thanh toán (Billing Report). Mỗi loại báo cáo có cách thu nhập dữ liệu và định dạng khác nhau, nhưng quy trình tạo báo cáo chung là giống nhau: thu thập dữ liệu → định dạng → kiểm tra tính hợp lệ → trả về báo cáo.

**Yêu cầu:**

- Định nghĩa skeleton của thuật toán tạo báo cáo trong một class trừu tượng (MedicalReport).
- Cho phép các subclass override các bước cụ thể (collectData, formatReport, validateReport) để tạo các loại báo cáo khác nhau.
- Đảm bảo quy trình chung (generateReport) không bị thay đổi bới các subclass.
- Dễ dàng thêm loại báo cáo mới (ví dụ: Báo cáo thuộc) mà không cần sửa đổi code hiện có.

**Lý do sử dụng:**

- Nếu ta không dùng Template Method phải:
- Lặp lại code chung (quy trình tạo báo cáo) trong mỗi class.
- Vi phạm nguyên tắc DRY (Don’t Repeat Yourself).
- Khó bảo trì: nếu quy trình chung thay đổi, phải sửa ở nhiều nơi.
- Code trở nên dài dòng và khó đọc.
- Nếu ta dùng Strategy:
- Tái sử dụng code chung, tránh lặp lại.
- Đảm bảo quy trình chung được thực thi nhất quán.
- Dễ dàng thêm loại báo cáo mới: chỉ cần override các phương thức abstract.
- Code dễ đọc và dễ bảo trì.
- Tuân thủ nguyên tắc Open/Closed Principle và DRY Principle.
- Kiểm soát được flow của thuật toán.

![A diagram of a program&#x0A;&#x0A;AI-generated content may be incorrect.](Aspose.Words.da648758-6e00-4531-9306-dcf6836844fb.025.png)

![](Aspose.Words.da648758-6e00-4531-9306-dcf6836844fb.026.png)


