package com.example.email

import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {

    private lateinit var edtEmail: EditText
    private lateinit var btnCheck: Button
    private lateinit var tvMessage: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        edtEmail = findViewById(R.id.edtEmail)
        btnCheck = findViewById(R.id.btnCheck)
        tvMessage = findViewById(R.id.tvMessage)

        fun show(msg: String, ok: Boolean = false) {
            tvMessage.text = msg
            tvMessage.setTextColor(if (ok) 0xFF2E7D32.toInt() else 0xFFD32F2F.toInt())
            tvMessage.visibility = TextView.VISIBLE
        }

        btnCheck.setOnClickListener {
            val email = edtEmail.text?.toString()?.trim() ?: ""

            when {
                email.isEmpty() -> show("Email không hợp lệ")
                !email.contains("@") -> show("Email không đúng định dạng")
                else -> show("Bạn đã nhập email hợp lệ", ok = true)
            }
        }
    }
}
