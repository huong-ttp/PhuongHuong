TUẦN 1
1.	Mong muốn và định hướng của bạn là gì sau khi học xong môn học là gì?
2.	Theo bạn, trong tương lai gần (10 năm), lập trình di động có phát triển không? Giải thích tại sao?
3.	Viết một ứng dụng có UI như sau đẩy lên github
Trả lời
1.	Mong muốn và định hướng của bản thân em:
Mục tiêu: trở thành Junior Mobile Developer (sử dụng ưu tiên Flutter).
Mong muốn nắm vững được các kỹ thuật về Flutter/Dart, làm việc với API (REST/JSON), local storage (SQLite/Hive), các tính năng thiết bị như camera, NFC/QR, Notification, Background task. 
Hiểu rõ cách xây dựng ứng dụng di động từ A – Z từ thiết kế UI, xử lý logic, kết nối API đến lưu trữ dữ liệu.
Biết cách tích hợp các chức năng thường gặp như đăng nhập/đăng ký, quản lý hồ sơ, notification, camera, NFC.
Khi nắm vững được các kỹ thuật em sẽ hoàn thành được app cho bài tập lớn, xây dựng được một app hoàn chỉnh có thể demo trên android. 
Về định hướng, em mong sau môn học mình sẽ có đủ kiến thức và khả năng để xin thực tập vị trí Junior Mobile Developer, và qua quá trình học tập, tích lũy kinh nghiệm thêm có thể trở thành Mobile Developer chuyên nghiệp, phát triển được ứng dụng đa nền tảng.
2.	Theo em, lập trình di động chắc chắn vẫn phát triển mạnh trong 10 năm tới vì một số nguyên nhân:
+ Thiết bị di động vẫn là trung tâm của cuộc sống số vì điện thoại, máy tính bảng, smart TV, smartwatch,… ngày càng phổ biến và nhiều người dùng, họ gần như phụ thuộc vào app di động cho nhiều mục đích như liên lạc, học tập, thanh toán, giải trí,…
+ Hạ tầng mạng và phần cứng ngày càng phát triển: 5G, 6G, IoT, edge computing và thiết bị cấu hình mạnh giúp xuất hiện nhiều ứng dụng mới như AR/VR, game online thời gian thực, điều khiển thiết bị thông minh từ xa.
+ Sự bùng nổ của trí tuệ nhân tạo (AI) trên thiết bị di động: Các ứng dụng tích hợp AI on-device như dịch ngôn ngữ, nhận diện hình ảnh, đề xuất thông minh, cá nhân hóa trải nghiệm người dùng sẽ trở thành xu hướng tất yếu.
+ Thanh toán số và dịch vụ công trực tuyến ngày càng phổ biến: Ví điện tử, ngân hàng số, mua sắm trực tuyến và các dịch vụ công qua mobile app phát triển nhanh, kéo theo nhu cầu lớn về ứng dụng di động an toàn và tiện lợi.
+ Công cụ phát triển đa nền tảng ngày càng hoàn thiện: Flutter, React Native, Kotlin Multiplatform giúp lập trình viên phát triển nhanh hơn, tiết kiệm chi phí, nhưng ứng dụng native vẫn quan trọng để tối ưu hiệu năng và tính năng đặc thù như NFC, camera, AR.
3.   Giải thích một số hàm trong SourceCode:
class MyApp extends StatelessWidget
Widget gốc của ứng dụng.
Trả về MaterialApp:
debugShowCheckedModeBanner: false ẩn nhãn debug.
useMaterial3: true bật Material You.
scaffoldBackgroundColor: Colors.white đặt nền trắng cho toàn app.
home: const ProfileScreen() màn hình đầu tiên.
class ProfileScreen extends StatelessWidget
Màn hình giao diện chính.
Scaffold + SafeArea để tránh đè phần tai thỏ.
Dùng Stack để đặt chồng 2 nút trên góc và phần nội dung ở giữa:
Positioned(top: 12, left: 12, child: _SquareActionButton(... Icons.west_rounded ...))
→ nút Back dạng ô vuông bo góc.
Positioned(top: 12, right: 12, child: _SquareActionButton(child: _EditGlyph(...)))
→ nút Edit kiểu khung xanh với bút chéo.
Center → Column ở giữa:
CircleAvatar (ảnh tròn) lấy ảnh từ assets: assets/images/anh1.png.
2 Text: tên và địa chỉ.
class _SquareActionButton extends StatelessWidget
Vỏ nút dùng chung cho Back/Edit.
Nhận child (icon bên trong), onTap, size.
Tạo hộp vuông bo góc 10, viền xám mảnh + shadow nhẹ.
Bọc InkWell để có hiệu ứng chạm.
Tùy chỉnh nhanh: đổi kích thước bằng size, đổi độ bo bằng BorderRadius.circular(…), hoặc màu viền trong Border.all(...).
class _EditGlyph extends StatelessWidget
Biểu tượng bút giống mockup.
Dùng Stack:
Nền là khung vuông viền xanh (kích thước 18×18, bo 4).
Trên là icon Icons.edit_outlined dịch nhẹ bằng Transform.translate cho đúng “góc nghiêng”.
Nhận tham số color để đổi màu xanh nhạt/mint.
