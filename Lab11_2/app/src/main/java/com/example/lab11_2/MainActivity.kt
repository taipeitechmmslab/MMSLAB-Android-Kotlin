package com.example.lab11_2

import android.content.ActivityNotFoundException
import android.content.Intent
import android.graphics.Bitmap
import android.os.Bundle
import android.provider.MediaStore
import android.widget.Button
import android.widget.ImageView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class MainActivity : AppCompatActivity() {
    private var angle = 0f

    //取得返回的影像資料
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        //識別返回對象及執行結果
        if (requestCode == 0 && resultCode == RESULT_OK) {
            //取得影像並顯示於ImageView
            val image = data?.extras?.get("data") ?: return
            findViewById<ImageView>(R.id.imageView).setImageBitmap(image as Bitmap)
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

        findViewById<Button>(R.id.btn_photo).setOnClickListener {
            //建立一個要進行影像獲取的Intent物件
            val intent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
            //用try-catch避免例外產生，若產生則顯示Toast
            try {
                startActivityForResult(intent, 0) //發送Intent
            } catch (e: ActivityNotFoundException) {
                Toast.makeText(this, "此裝置無相機應用程式", Toast.LENGTH_SHORT).show()
            }
        }
        findViewById<Button>(R.id.btn_rotate).setOnClickListener {
            angle += 90f //原本角度再加上90度
            findViewById<ImageView>(R.id.imageView).rotation = angle //使ImageView旋轉
        }
    }
}