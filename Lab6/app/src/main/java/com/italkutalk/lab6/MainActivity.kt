package com.italkutalk.lab6

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Gravity
import android.widget.*
import androidx.appcompat.app.AlertDialog
import com.google.android.material.snackbar.Snackbar

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        //將變數與 XML 元件綁定
        val btn_toast = findViewById<Button>(R.id.btn_toast)
        val btn_custom = findViewById<Button>(R.id.btn_custom)
        val btn_snackbar = findViewById<Button>(R.id.btn_snackbar)
        val btn_dialog1 = findViewById<Button>(R.id.btn_dialog1)
        val btn_dialog2 = findViewById<Button>(R.id.btn_dialog2)
        val btn_dialog3 = findViewById<Button>(R.id.btn_dialog3)
        //建立要顯示在的列表上的字串
        val item = arrayOf("選項 1", "選項 2", "選項 3", "選項 4", "選項 5")
        //Button 點擊事件
        btn_toast.setOnClickListener {
            showToast("預設 Toast") //執行 showToast 方法
        }
        btn_custom.setOnClickListener {
            //宣告 Toast
            val toast = Toast(this)
            //Toast 在畫面中顯示位置
            toast.setGravity(Gravity.TOP, 0, 50)
            //Toast 在畫面中顯示的持續時間
            toast.duration = Toast.LENGTH_SHORT
            //放入自定義的畫面 custom_toast.xml
            toast.view =
                    layoutInflater.inflate(R.layout.custom_toast, null)
            //顯示於螢幕
            toast.show()
        }
        btn_snackbar.setOnClickListener {
            //建立 Snackbar 物件
            Snackbar.make(it, "按鈕式 Snackbar", Snackbar.LENGTH_SHORT)
                    .setAction("按鈕") {
                        showToast("已回應")
                    }.show()
        }
        btn_dialog1.setOnClickListener {
            //建立 AlertDialog 物件
            AlertDialog.Builder(this)
                    .setTitle("按鈕式 AlertDialog")
                    .setMessage("AlertDialog 內容")
                    .setNeutralButton("左按鈕") { dialog, which ->
                        showToast("左按鈕")
                    }
                    .setNegativeButton("中按鈕") { dialog, which ->
                        showToast("中按鈕")
                    }
                    .setPositiveButton("右按鈕") { dialog, which ->
                        showToast("右按鈕")
                    }.show()
        }
        btn_dialog2.setOnClickListener {
            //建立 AlertDialog 物件
            AlertDialog.Builder(this)
                    .setTitle("列表式 AlertDialog")
                    .setItems(item) { dialogInterface, i ->
                        showToast("你選的是${item[i]}")
                    }.show()
        }
        btn_dialog3.setOnClickListener {
            var position = 0
            //建立 AlertDialog 物件
            AlertDialog.Builder(this)
                    .setTitle("單選式 AlertDialog")
                    .setSingleChoiceItems(item, 0) { dialogInterface, i ->
                        position = i
                    }
                    .setPositiveButton("確定") { dialog, which ->
                        showToast("你選的是${item[position]}")
                    }.show()
        }
    }
    //建立 showToast 方法顯示 Toast 訊息
    private fun showToast(msg: String) {
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show()
    }
}