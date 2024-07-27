package com.example.lab19

import android.content.ActivityNotFoundException
import android.graphics.Bitmap
import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.ImageView
import android.widget.ListView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.core.view.drawToBitmap
import com.google.mlkit.vision.common.InputImage
import com.google.mlkit.vision.label.ImageLabeling
import com.google.mlkit.vision.label.defaults.ImageLabelerOptions
import java.io.IOException

class MainActivity : AppCompatActivity() {
    // 當前的圖片旋轉角度
    private var angle = 0f
    // 定義元件
    private lateinit var imgPhoto: ImageView
    private lateinit var btnCapture: Button
    private lateinit var btnRotate: Button

    // 宣告 ActivityResultLauncher，取得回傳的照片
    private val startForResult = registerForActivityResult(
        ActivityResultContracts.TakePicturePreview()
    ) { bitmap: Bitmap? ->
        if (bitmap != null) {
            // 顯示拍攝的照片
            imgPhoto.setImageBitmap(bitmap)
            // 進行圖像辨識
            recognizeImage(bitmap)
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

        // 綁定元件
        imgPhoto = findViewById(R.id.imgPhoto)
        btnCapture = findViewById(R.id.btnCapture)
        btnRotate = findViewById(R.id.btnRotate)

        // 拍攝照片
        btnCapture.setOnClickListener {
            // 用 try-catch 避免例外錯誤產生，若產生錯誤則使用 Toast 顯示
            try {
                // 使用 startForResult 來拍攝照片
                startForResult.launch(null)
            } catch (e: ActivityNotFoundException) {
                Toast.makeText(
                    this, "無相機應用程式", Toast.LENGTH_SHORT
                ).show()
            }
        }

        // 旋轉照片
        btnRotate.setOnClickListener {
            // 原本角度再加上90度
            angle += 90f
            // 使 ImageView 旋轉
            imgPhoto.rotation = angle
            // 取得 Bitmap
            val bitmap = imgPhoto.drawToBitmap()
            // 進行圖像辨識
            recognizeImage(bitmap)
        }
    }

    // 辨識圖像
    private fun recognizeImage(bitmap: Bitmap) {
        try {
            // 取得辨識標籤
            val labeler = ImageLabeling.getClient(
                ImageLabelerOptions.DEFAULT_OPTIONS
            )
            // 建立 InputImage 物件
            val inputImage = InputImage.fromBitmap(bitmap, 0)
            // 匹配辨識標籤與圖像，並建立執行成功與失敗的監聽器
            labeler.process(inputImage)
                .addOnSuccessListener { labels ->
                    // 取得辨識結果與可信度
                    val result = arrayListOf<String>()
                    for (label in labels) {
                        val text = label.text
                        val confidence = label.confidence
                        result.add("$text, 可信度：${confidence * 100}%")
                    }
                    // 將結果顯示於 ListView
                    val listView = findViewById<ListView>(R.id.listView)
                    listView.adapter = ArrayAdapter(
                        this, android.R.layout.simple_list_item_1, result
                    )
                }
                .addOnFailureListener { e ->
                    Toast.makeText(
                        this, "發生錯誤:${e.message}", Toast.LENGTH_SHORT
                    ).show()
                }
        } catch (e: IOException) {
            e.printStackTrace()
        }
    }
}