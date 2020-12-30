package com.italkutalk.lab4

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.*

class MainActivity : AppCompatActivity() {

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        data?.extras?.let {
            //驗證發出對象，確認 SecActivity 執行的狀態
            if (requestCode == 1 && resultCode == Activity.RESULT_OK) {
                //讀取 Bundle 資料
                findViewById<TextView>(R.id.tv_meal).text =
                        "飲料: ${it.getString("drink")}\n\n" +
                                "甜度: ${it.getString("sugar")}\n\n" +
                                "冰塊: ${it.getString("ice")}"
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        findViewById<Button>(R.id.btn_choice).setOnClickListener {
            //透過 Intent 切換至 SecActivity 並傳遞 requestCode 作為識別編號
            val intent = Intent(this, SecActivity::class.java)
            startActivityForResult(intent, 1)
        }
    }
}