package com.example.lab20

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.ViewModelProvider

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        // 取得 ViewModel
        val viewModel = ViewModelProvider(this)[MainViewModel::class.java]

        // 取得元件
        val tvHint = findViewById<TextView>(R.id.tvHint)
        val edAccount = findViewById<TextView>(R.id.edAccount)
        val edPassword = findViewById<TextView>(R.id.edPassword)
        val btnRegister = findViewById<Button>(R.id.btnRegister)

        // 註冊帳號
        btnRegister.setOnClickListener {
            viewModel.registerAccount(
                edAccount.text.toString(),
                edPassword.text.toString()
            )
        }

        // 觀察註冊結果
        viewModel.registerResult.observe(this) { result ->
            // 顯示註冊結果
            Toast.makeText(
                this, result.second, Toast.LENGTH_SHORT
            ).show()
            // 判斷是否註冊成功
            if (result.first) {
                tvHint.text = result.second
                tvHint.setTextColor(
                    getColor(android.R.color.holo_green_dark)
                )
                // 跳轉至 SecActivity
                val i = Intent(this, SecActivity::class.java)
                startActivity(i)
            } else {
                tvHint.text = "註冊失敗：${result.second}"
                tvHint.setTextColor(
                    getColor(android.R.color.holo_red_dark)
                )
            }
        }
    }
}