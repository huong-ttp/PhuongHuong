package com.example.uthsmarttasksapi.data.network

object RetrofitInstance {
    private const val BASE_URL = "https://amock.io/api/"

    val api: ApiService by lazy {
        retrofit2.Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(retrofit2.converter.gson.GsonConverterFactory.create())
            .build()
            .create(ApiService::class.java)
    }
}
