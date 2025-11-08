package com.example.uthsmarttasksapi.data.network

import com.example.uthsmarttasksapi.data.model.Task
import com.example.uthsmarttasksapi.data.model.TaskResponse
import com.example.uthsmarttasksapi.data.model.TaskDetailResponse
import retrofit2.Response
import retrofit2.http.*

interface ApiService {
    @GET("researchUTH/tasks")
    suspend fun getTasks(): Response<TaskResponse>

    @GET("researchUTH/task/{id}")
    suspend fun getTaskDetail(@Path("id") id: Int): Response<TaskDetailResponse>

    @DELETE("researchUTH/task/{id}")
    suspend fun deleteTask(@Path("id") id: Int): Response<Unit>
}
