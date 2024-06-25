package com.example.lab11_2

import android.content.ActivityNotFoundException
import android.graphics.Bitmap
import android.os.Bundle
import android.widget.Button
import android.widget.ImageView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class MainActivity : AppCompatActivity() {
    // 當前的圖片旋轉角度
    private var angle = 0f

    // 宣告 ActivityResultLauncher，取得回傳的照片
    private val startForResult = registerForActivityResult(
        ActivityResultContracts.TakePicturePreview()
    ) { bitmap: Bitmap? ->
        if (bitmap != null) {
            findViewById<ImageView>(R.id.imgPhoto).setImageBitmap(bitmap)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        findViewById<Button>(R.id.btnCapture).setOnClickListener {
            // 用 try-catch 避免例外錯誤產生，若產生錯誤則使用 Toast 顯示
            try {
                // 使用 startForResult 來拍攝照片
                startForResult.launch(null)
            } catch (e: ActivityNotFoundException) {
                Toast.makeText(this, "無相機應用程式", Toast.LENGTH_SHORT).show()
            }
        }

        findViewById<Button>(R.id.btnRotate).setOnClickListener {
            // 原本角度再加上90度
            angle += 90f
            // 使 ImageView 旋轉
            findViewById<ImageView>(R.id.imgPhoto).rotation = angle
        }
    }
}