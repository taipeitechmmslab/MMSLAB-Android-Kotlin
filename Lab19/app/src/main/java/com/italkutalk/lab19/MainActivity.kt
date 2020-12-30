package com.italkutalk.lab19

import android.content.ActivityNotFoundException
import android.content.Intent
import android.graphics.Bitmap
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.MediaStore
import android.widget.*
import androidx.core.view.drawToBitmap
import com.google.mlkit.vision.common.InputImage
import com.google.mlkit.vision.label.ImageLabeling
import com.google.mlkit.vision.label.defaults.ImageLabelerOptions
import java.io.IOException

class MainActivity : AppCompatActivity() {
    private var angle = 0f
    //取得返回的影像資料
    override fun onActivityResult(requestCode: Int,
                                  resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        //識別返回對象及執行結果
        if (requestCode == 0 && resultCode == RESULT_OK) {
            val image = data?.extras?.get("data") ?: return //取得資料
            val bitmap = image as Bitmap //將資料轉換成 Bitmap
            val imageView = findViewById<ImageView>(R.id.imageView)
            imageView.setImageBitmap(bitmap) //使用 Bitmap 設定圖像
            recognizeImage(bitmap) //使用 Bitmap 進行辨識
        }
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        findViewById<Button>(R.id.btn_photo).setOnClickListener {
            //建立一個要進行影像獲取的 Intent 物件
            val intent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
            //用 try-catch 避免例外產生，若產生則顯示 Toast
            try {
                startActivityForResult(intent, 0) //發送 Intent
            } catch (e: ActivityNotFoundException) {
                Toast.makeText(this,
                        "此裝置無相機應用程式", Toast.LENGTH_SHORT).show()
            }
        }
        findViewById<Button>(R.id.btn_rotate).setOnClickListener {
            val imageView = findViewById<ImageView>(R.id.imageView)
            angle += 90f //原本角度再加上 90 度
            imageView.rotation = angle //使 ImageView 旋轉
            val bitmap = imageView.drawToBitmap() //取得 Bitmap
            recognizeImage(bitmap) //使用 Bitmap 進行辨識
        }
    }
    //辨識圖像
    private fun recognizeImage(bitmap: Bitmap) {
        try {
            //取得辨識標籤
            val labeler = ImageLabeling.getClient(
                    ImageLabelerOptions.DEFAULT_OPTIONS
            )
            //建立 InputImage 物件
            val inputImage = InputImage.fromBitmap(bitmap, 0)
            //匹配辨識標籤與圖像，並建立執行成功與失敗的監聽器
            labeler.process(inputImage)
                    .addOnSuccessListener { labels ->
                        //取得辨識結果與可信度
                        val result = arrayListOf<String>()
                        for (label in labels) {
                            val text = label.text
                            val confidence = label.confidence
                            result.add("$text, 可信度：$confidence")
                        }
                        //將結果顯示於 ListView
                        val listView = findViewById<ListView>(R.id.listView)
                        listView.adapter = ArrayAdapter(this,
                                android.R.layout.simple_list_item_1,
                                result
                        )
                    }
                    .addOnFailureListener { e ->
                        Toast.makeText(this,
                                "發生錯誤", Toast.LENGTH_SHORT).show()
                    }
        } catch (e: IOException) {
            e.printStackTrace()
        }
    }
}