package com.example.uthsmarttasksapi.data.model

data class TaskResponse(
    val isSuccess: Boolean,
    val message: String,
    val data: List<Task> // đây là danh sách Task
)
