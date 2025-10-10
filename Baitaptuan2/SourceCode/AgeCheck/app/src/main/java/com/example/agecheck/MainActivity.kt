package com.example.agecheck

import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {

    private lateinit var edtName: EditText
    private lateinit var edtAge: EditText
    private lateinit var btnCheck: Button
    private lateinit var tvResult: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        edtName = findViewById(R.id.edtName)
        edtAge = findViewById(R.id.edtAge)
        btnCheck = findViewById(R.id.btnCheck)
        tvResult = findViewById(R.id.tvResult)

        btnCheck.setOnClickListener { check() }
    }

    private fun check() {
        val name = edtName.text?.toString()?.trim().orEmpty()
        val age = edtAge.text?.toString()?.trim()?.toIntOrNull()

        if (name.isEmpty() || age == null || age < 0) {
            tvResult.text = "Vui lòng nhập họ tên và tuổi hợp lệ."
            tvResult.setTextColor(0xFFD32F2F.toInt()) // đỏ
            tvResult.visibility = TextView.VISIBLE
            return
        }

        val group = when {
            age < 2 -> "Em bé"
            age < 6 -> "Trẻ em"
            age <= 65 -> "Người lớn"
            else -> "Người già"
        }

        tvResult.text = "Họ tên: $name\nTuổi: $age\n⇒ Nhóm: $group"
        tvResult.setTextColor(0xFF2E7D32.toInt()) // xanh lá
        tvResult.visibility = TextView.VISIBLE
    }
}
