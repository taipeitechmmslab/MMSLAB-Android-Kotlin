package com.example.lab12

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

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

        // 為按鈕設定監聽器
        findViewById<Button>(R.id.btnStart).setOnClickListener {
            // 使用 startService() 方法啟動 Service
            startService(Intent(this, MyService::class.java))
            // 顯示 Toast 訊息
            Toast.makeText(
                this, "啟動Service", Toast.LENGTH_SHORT
            ).show()
            // 關閉Activity
            finish()
        }
    }
}