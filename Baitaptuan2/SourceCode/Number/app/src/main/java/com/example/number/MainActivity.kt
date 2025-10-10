package com.example.number

import android.os.Bundle
import android.view.inputmethod.EditorInfo
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class MainActivity : AppCompatActivity() {

    private lateinit var edtQuantity: EditText
    private lateinit var btnCreate: Button
    private lateinit var tvError: TextView
    private lateinit var rv: RecyclerView
    private val adapter = NumberAdapter()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        edtQuantity = findViewById(R.id.edtQuantity)
        btnCreate = findViewById(R.id.btnCreate)
        tvError = findViewById(R.id.tvError)
        rv = findViewById(R.id.rvNumbers)

        rv.layoutManager = LinearLayoutManager(this)
        rv.adapter = adapter

        fun createList() {
            val text = edtQuantity.text.toString().trim()
            val n = text.toIntOrNull()
            if (n == null || n <= 0) {
                tvError.visibility = TextView.VISIBLE
                adapter.submit(emptyList())
            } else {
                tvError.visibility = TextView.GONE
                adapter.submit((1..n).toList())
            }
        }

        btnCreate.setOnClickListener { createList() }
        edtQuantity.setOnEditorActionListener { _, actionId, _ ->
            if (actionId == EditorInfo.IME_ACTION_DONE) {
                createList()
                true
            } else false
        }
    }
}
