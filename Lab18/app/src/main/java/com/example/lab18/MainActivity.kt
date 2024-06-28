package com.example.lab18

import android.Manifest
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import android.widget.Button
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class MainActivity : AppCompatActivity() {
    // 宣告靜態變數
    companion object {
        private const val REQUEST_CODE = 0 // 請求代碼
        private const val CHANNEL_ID = "Lab18" // 通知頻道Id
        private const val CHANNEL_NAME = "My Channel" // 通知頻道名稱
        private const val NOTIFICATION_ID = 0 // 通知Id
    }

    // 宣告通知管理物件
    private lateinit var nm: NotificationManagerCompat

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray,
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == REQUEST_CODE && grantResults.isNotEmpty() &&
            grantResults[0] == PackageManager.PERMISSION_GRANTED
        ) {
            sendNotification() // 重新發送通知
        } else {
            Toast.makeText(this, "你沒有通知權限", Toast.LENGTH_SHORT).show()
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

        // 建立通知管理物件
        nm = NotificationManagerCompat.from(this)
        // 建立通知頻道
        createChannel()

        findViewById<Button>(R.id.btnShow).setOnClickListener {
            // 檢查權限後發送通知
            sendNotification()
        }
    }

    private fun createChannel() {
        // 若Android版本在8.0以上必須先建立通知頻道
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            // 設定頻道Id、名稱及訊息優先權
            val importance = NotificationManager.IMPORTANCE_HIGH
            val channel = NotificationChannel(CHANNEL_ID, CHANNEL_NAME, importance)
            // 建立頻道
            nm.createNotificationChannel(channel)
        }
    }

    private fun sendNotification() {
        // 如果Android版本為13或以上，需要檢查是否有通知權限
        if (
            Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU &&
            ActivityCompat.checkSelfPermission(
                this, Manifest.permission.POST_NOTIFICATIONS
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            // 請求通知權限
            ActivityCompat.requestPermissions(
                this, arrayOf(Manifest.permission.POST_NOTIFICATIONS), REQUEST_CODE
            )
        } else {
            // 建立Intent、PendingIntent，當通知被點選時開啟應用程式
            val intent = Intent(this, MainActivity::class.java)
            intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
            val pendingIntent = PendingIntent.getActivity(
                this, 0, intent, PendingIntent.FLAG_IMMUTABLE
            )
            // 定義通知的訊息內容並發送
            val builder = NotificationCompat.Builder(this, CHANNEL_ID)
                .setSmallIcon(android.R.drawable.btn_star_big_on) // 通知圖示
                .setContentTitle("折價券") // 通知標題
                .setContentText("您還有一張五折折價券，滿額消費即贈現金回饋") // 通知內容
                .setContentIntent(pendingIntent) // 通知被點選後的意圖
                .setAutoCancel(true) // 通知被點選後自動消失
                .setPriority(NotificationCompat.PRIORITY_DEFAULT) // 通知優先權
            nm.notify(NOTIFICATION_ID, builder.build()) // 發送通知於裝置
        }
    }
}