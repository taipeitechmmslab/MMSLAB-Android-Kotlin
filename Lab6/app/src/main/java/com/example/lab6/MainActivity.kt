package com.example.lab6

import android.os.Bundle
import android.widget.Button
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.google.android.material.snackbar.Snackbar

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
        // 定義元件變數，並通過 findViewById 取得元件
        val btnToast = findViewById<Button>(R.id.btnToast)
        val btnSnackBar = findViewById<Button>(R.id.btnSnackBar)
        val btnDialog1 = findViewById<Button>(R.id.btnDialog1)
        val btnDialog2 = findViewById<Button>(R.id.btnDialog2)
        val btnDialog3 = findViewById<Button>(R.id.btnDialog3)

        // 建立要顯示在的列表上的字串陣列
        val item = arrayOf("選項 1", "選項 2", "選項 3", "選項 4", "選項 5")

        // 設定按鈕的點擊事件
        btnToast.setOnClickListener {
            // 呼叫 showToast 方法，顯示 Toast 訊息
            showToast("預設 Toast")
        }

        btnSnackBar.setOnClickListener {
            // 建立 Snackbar 物件，並顯示 Snackbar 訊息
            Snackbar.make(it, "按鈕式 Snackbar", Snackbar.LENGTH_SHORT)
                // 設定 Snackbar 按鈕的文字與點擊事件
                .setAction("按鈕") {
                    showToast("已回應")
                }.show()
        }

        btnDialog1.setOnClickListener {
            // 建立 AlertDialog 物件
            AlertDialog.Builder(this)
                // 設定標題
                .setTitle("按鈕式 AlertDialog")
                // 設定內容
                .setMessage("AlertDialog 內容")
                // 設定按鈕文字與點擊事件
                .setNeutralButton("左按鈕") { dialogInterface, which ->
                    showToast("左按鈕")
                }.setNegativeButton("中按鈕") { dialogInterface, which ->
                    showToast("中按鈕")
                }.setPositiveButton("右按鈕") { dialogInterface, which ->
                    showToast("右按鈕")
                }.show()
        }

        btnDialog2.setOnClickListener {
            // 建立 AlertDialog 物件
            AlertDialog.Builder(this)
                // 設定標題
                .setTitle("列表式 AlertDialog")
                // 設定列表項目及點擊事件
                .setItems(item) { dialogInterface, i ->
                    // 顯示 Toast 訊息
                    showToast("你選的是${item[i]}")
                }.show()
        }

        btnDialog3.setOnClickListener {
            // 宣告變數 position 用來記錄選擇的項目
            var position = 0
            // 建立 AlertDialog 物件
            AlertDialog.Builder(this)
                // 設定標題
                .setTitle("單選式 AlertDialog")
                // 設定列表項目及點擊事件，預設選擇第一個項目
                .setSingleChoiceItems(item, 0) { dialogInterface, i ->
                    // 更新變數 position 的值
                    position = i
                }.setPositiveButton("確定") { dialog, which ->
                    // 顯示 Toast 訊息
                    showToast("你選的是${item[position]}")
                }.show()
        }
    }

    // 建立 showToast 方法，顯示 Toast 訊息
    private fun showToast(msg: String) {
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show()
    }
}