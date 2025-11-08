package com.example.uthsmarttasksapi.data.model

data class Task(
    val id: Int,
    val title: String,
    val description: String,
    val status: String,
    val time: String,

    // ✅ Thêm các trường mới để hiển thị chi tiết Task
    val category: String = "General",
    val priority: String = "Medium",

    // ✅ Dạng dữ liệu an toàn để tránh lỗi Gson: BEGIN_OBJECT
    val subtasks: List<Map<String, Any>> = emptyList(),

    // ✅ Nếu API trả về object thay vì string, tránh crash
    val attachments: List<Map<String, Any>> = emptyList()
)
