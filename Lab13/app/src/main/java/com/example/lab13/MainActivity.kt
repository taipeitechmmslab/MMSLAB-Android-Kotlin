package com.example.lab13

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.os.Build
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class MainActivity : AppCompatActivity() {
    // 建立 BroadcastReceiver 物件
    private val receiver =
        object : BroadcastReceiver() {
            override fun onReceive(context: Context, intent: Intent) {
                // 接收廣播後，解析 Intent 取得字串訊息
                intent.extras?.let {
                    val tvMsg = findViewById<TextView>(R.id.tvMsg)
                    tvMsg.text = "${it.getString("msg")}"
                }
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

        findViewById<Button>(R.id.btnMusic).setOnClickListener {
            register("music")
        }

        findViewById<Button>(R.id.btnNew).setOnClickListener {
            register("new")
        }

        findViewById<Button>(R.id.btnSport).setOnClickListener {
            register("sport")
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        // 解除註冊廣播接收器
        unregisterReceiver(receiver)
    }

    private fun register(channel: String) {
        // 建立 IntentFilter 物件來指定接收的頻道
        val intentFilter = IntentFilter(channel)
        // 註冊廣播接收器
        // 如果是 Android 12 以上的版本，則需要加上 RECEIVER_EXPORTED
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            registerReceiver(receiver, intentFilter, RECEIVER_EXPORTED)
        } else {
            registerReceiver(receiver, intentFilter)
        }
        // 建立 Intent 物件，使其夾帶頻道資料，並啟動 MyService 服務
        val i = Intent(this, MyService::class.java)
        startService(i.putExtra("channel", channel))
    }
}