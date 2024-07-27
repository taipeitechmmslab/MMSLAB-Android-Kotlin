package com.example.lab17

import android.os.Bundle
import android.widget.Button
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.google.gson.Gson
import okhttp3.Call
import okhttp3.Callback
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.Response
import java.io.IOException

class MainActivity : AppCompatActivity() {
    private lateinit var btnQuery: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        // 與XML中的元件綁定
        btnQuery = findViewById(R.id.btnQuery)
        // 設定按鈕點擊事件
        btnQuery.setOnClickListener {
            // 關閉按鈕避免重複點擊
            btnQuery.isEnabled = false
            // 發送請求
            setRequest()
        }
    }

    // 發送請求
    private fun setRequest() {
        // 空氣品質指標API
        val url = "https://api.italkutalk.com/api/air"
        // 建立Request.Builder物件，藉由url()將網址傳入，再建立Request物件
        val req = Request.Builder()
            .url(url)
            .build()

        // 建立OkHttpClient物件，藉由newCall()發送請求，並在enqueue()接收回傳
        OkHttpClient().newCall(req).enqueue(object : Callback {
            // 發送成功執行此方法
            override fun onResponse(call: Call, response: Response) {
                // 使用response.body?.string()取得JSON字串
                val json = response.body?.string()
                // 建立Gson並使用其fromJson()方法，將JSON字串以MyObject格式輸出
                val myObject = Gson().fromJson(json, MyObject::class.java)
                // 顯示結果
                showDialog(myObject)
            }

            // 發送失敗執行此方法
            override fun onFailure(call: Call, e: IOException) {
                runOnUiThread {
                    // 開啟按鈕可再次查詢
                    btnQuery.isEnabled = true
                    // 顯示錯誤訊息
                    Toast.makeText(this@MainActivity,
                        "查詢失敗$e", Toast.LENGTH_SHORT
                    ).show()
                }
            }
        })
    }

    // 顯示結果
    private fun showDialog(myObject: MyObject) {
        // 建立一個字串陣列，用於存放SiteName與Status資訊
        val items = mutableListOf<String>()
        // 將API資料取出並建立字串，並存放到字串陣列
        myObject.result.records.forEach { data ->
            items.add("地區：${data.SiteName}, 狀態：${data.Status}")
        }
        // 切換到主執行緒將畫面更新
        runOnUiThread {
            // 開啟按鈕可再次查詢
            btnQuery.isEnabled = true
            // 建立AlertDialog物件並顯示字串陣列
            AlertDialog.Builder(this@MainActivity)
                .setTitle("臺北市空氣品質")
                .setItems(items.toTypedArray(), null)
                .show()
        }
    }
}