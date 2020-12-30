package com.italkutalk.lab13

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.TextView

class MainActivity : AppCompatActivity() {
    //建立 BroadcastReceiver 物件
    private val receiver = object : BroadcastReceiver() {
        override fun onReceive(context: Context, intent: Intent) {
            //接收廣播後，解析 Intent 取得字串訊息
            intent.extras?.let {
                val tv_msg = findViewById<TextView>(R.id.tv_msg)
                tv_msg.text = "${it.getString("msg")}"
            }
        }
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        findViewById<Button>(R.id.btn_music).setOnClickListener {
            register("music")
        }
        findViewById<Button>(R.id.btn_new).setOnClickListener {
            register("new")
        }
        findViewById<Button>(R.id.btn_sport).setOnClickListener {
            register("sport")
        }
    }
    override fun onDestroy() {
        unregisterReceiver(receiver) //註銷廣播
        super.onDestroy()
    }
    private fun register(channel: String) {
        //建立 IntentFilter 物件來指定接收的頻道，並註冊 Receiver
        val intentFilter = IntentFilter(channel)
        registerReceiver(receiver, intentFilter)
        //建立 Intent 物件，使其夾帶頻道資料，並啟動 MyService 服務
        val i = Intent(this, MyService::class.java)
        startService(i.putExtra("channel", channel))
    }
}