package com.example.lab4

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.RadioButton
import android.widget.RadioGroup
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.os.bundleOf
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class SecActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_sec)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        // Step6：定義元件變數，並通過 findViewById 取得元件
        val edDrink = findViewById<TextView>(R.id.edDrink)
        val rgSugar = findViewById<RadioGroup>(R.id.rgSugar)
        val rgIce = findViewById<RadioGroup>(R.id.rgIce)
        val btnSend = findViewById<Button>(R.id.btnSend)
        // Step7：設定 btnSend 的點擊事件
        btnSend.setOnClickListener {
            // Step8：如果 edDrink 為空，則顯示提示文字
            if (edDrink.text.isEmpty()) {
                Toast.makeText(this, "請輸入飲料名稱", Toast.LENGTH_SHORT).show()
            } else {
                // Step9：宣告 Bundle，並將飲料名稱、甜度、冰塊的值放入 Bundle 中
                val b = bundleOf(
                    "drink" to edDrink.text.toString(),
                    "sugar" to rgSugar.findViewById<RadioButton>(
                        rgSugar.checkedRadioButtonId
                    ).text.toString(),
                    "ice" to rgIce.findViewById<RadioButton>(
                        rgIce.checkedRadioButtonId
                    ).text.toString()
                )
                // Step10：宣告 Intent，並將 Bundle 放入 Intent 中
                val i = Intent().putExtras(b)
                // Step11：設定 Activity 的結果，並關閉 Activity
                setResult(RESULT_OK, i)
                finish()
            }
        }
    }
}