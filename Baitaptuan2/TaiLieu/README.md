1) Bài Number – nhập số → tạo N “khung số”
Mục tiêu: Nhập số nguyên dương N, sinh N nút đỏ đánh số 1..N; sai định dạng → báo lỗi.
Cấu trúc chính
activity_main.xml
TextView tiêu đề.
EditText @+id/edtQuantity nhập N (đặt inputType số hoặc text để test lỗi).
Button @+id/btnCreate (xanh).
TextView @+id/tvError (ẩn mặc định) để hiện lỗi.
RecyclerView @+id/rvNumbers hiển thị danh sách nút.
item_number.xml
Một Button @+id/btnNumber nền @drawable/bg_red_rounded để tạo “khung số”.
bg_red_rounded.xml (drawable)
shape/ripple bo góc + màu đỏ cho các nút.
MainActivity.kt (Kotlin)
Khởi tạo RecyclerView với LinearLayoutManager và NumberAdapter.
Hàm createList():
Lấy chuỗi từ edtQuantity, toIntOrNull() (tránh crash).
Nếu null hoặc <=0 → tvError.visible = true, adapter.submit(emptyList()).
Ngược lại → tvError.gone, adapter.submit((1..n).toList()).
Gán setOnClickListener cho nút Tạo, và setOnEditorActionListener IME_ACTION_DONE trên EditText.
NumberAdapter.kt
submit(data: List<Int>) → gán data + notifyDataSetChanged().
onCreateViewHolder inflate item_number.xml.
onBindViewHolder → đặt btnNumber.text = value.toString().
Lý do chọn cách làm
toIntOrNull() giúp validate an toàn.
RecyclerView thích hợp khi số lượng phần tử thay đổi động theo N.
2) Bài Email – kiểm tra định dạng đơn giản
Mục tiêu: Rỗng/null → “Email không hợp lệ”; không chứa @ → “Email không đúng định dạng”; còn lại → hợp lệ.
Cấu trúc chính
activity_main.xml (hoặc activity_email.xml)
EditText @+id/edtEmail (có thể dùng inputType="textEmailAddress").
Button @+id/btnCheck (xanh, dùng bg_blue_pill hoặc backgroundTint).
TextView @+id/tvMessage để hiển thị thông báo (ẩn mặc định).
(Tuỳ chọn) bg_edittext_outline_black.xml cho viền đen EditText.
bg_blue_pill.xml (drawable) → nền xanh bo góc cho nút.
EmailActivity/MainActivity.kt
Tìm edtEmail, btnCheck, tvMessage.
btnCheck.setOnClickListener { ... }:
val email = edtEmail.text?.toString()?.trim().orEmpty()
when {
email.isEmpty() → show “Email không hợp lệ”
!email.contains("@") → show “Email không đúng định dạng”
else → show “Bạn đã nhập email hợp lệ”
Đổi màu tvMessage (đỏ cho lỗi, xanh lá cho OK) và bật VISIBLE.
Lý do chọn cách làm
Yêu cầu bài chỉ đòi kiểm tra rỗng và ký tự @ → không cần regex phức tạp.
Dùng orEmpty() tránh null.
3) Bài AgeCheck – nhập họ tên + tuổi → phân nhóm tuổi
Mục tiêu: hiển thị lại tên, tuổi và nhóm:
<2: Em bé, 2–<6: Trẻ em, 6–65: Người lớn, >65: Người già.
Cấu trúc chính
activity_main.xml
TextView tiêu đề.
Khung LinearLayout (nền bg_panel.xml) chứa 2 hàng:
Hàng 1: TextView "Họ và tên" + EditText @+id/edtName.
Hàng 2: TextView "Tuổi" + EditText @+id/edtAge (inputType="number").
Button @+id/btnCheck (nền bg_blue_button.xml).
TextView @+id/tvResult (ẩn mặc định) hiển thị kết quả.
bg_panel.xml → nền xám bo góc cho khung nhập.
bg_blue_button.xml → nút xanh bo góc.
MainActivity.kt
Lấy tham chiếu edtName, edtAge, btnCheck, tvResult.
btnCheck.setOnClickListener { check() }
check():
val name = edtName.text?.toString()?.trim().orEmpty()
val age = edtAge.text?.toString()?.trim()?.toIntOrNull()
Nếu name.isEmpty() hoặc age==null || age<0 → báo “nhập hợp lệ” (màu đỏ), VISIBLE, return.
val group = when { age < 2 -> "Em bé"; age < 6 -> "Trẻ em"; age <= 65 -> "Người lớn"; else -> "Người già" }
tvResult.text = "Họ tên: $name\nTuổi: $age\n⇒ Nhóm: $group"; màu xanh lá; VISIBLE.
Lý do chọn cách làm
toIntOrNull() chống crash khi tuổi không phải số.
Dùng when để biểu diễn khoảng tuổi rõ ràng, dễ chỉnh biên.