package com.italkutalk.lab18

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Intent
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        findViewById<Button>(R.id.btn_show).setOnClickListener {
            val nm = NotificationManagerCompat.from(this) //建立通知管理物件
            //若 Android 版本在 8.0 以上必須先建立通知頻道
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                //設定頻道 Id、名稱及訊息優先權
                val name = "My Channel"
                val importance = NotificationManager.IMPORTANCE_DEFAULT
                val channel = NotificationChannel("Lab18", name, importance)
                //建立頻道
                nm.createNotificationChannel(channel)
            }
            //建立 Intent、PendingIntent，當通知被點選時開啟應用程式
            val intent = Intent(this, MainActivity::class.java)
            intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or
                    Intent.FLAG_ACTIVITY_CLEAR_TASK
            val pendingIntent = PendingIntent.getActivity(this,0,intent,0)
            //定義通知的訊息內容並發送
            val text = "您還有一張五折折價券，滿額消費即贈現金回饋"
            val builder = NotificationCompat.Builder(this, "Lab18")
                    .setSmallIcon(android.R.drawable.btn_star_big_on) //通知圖示
                    .setContentTitle("折價券") //通知標題
                    .setContentText(text) //通知內容
                    .setContentIntent(pendingIntent) //通知被點選後的意圖
                    .setAutoCancel(true) //通知被點選後自動消失
                    .setPriority(NotificationCompat.PRIORITY_DEFAULT) //優先權
            nm.notify(0, builder.build()) //發送通知於裝置
        }
    }
}