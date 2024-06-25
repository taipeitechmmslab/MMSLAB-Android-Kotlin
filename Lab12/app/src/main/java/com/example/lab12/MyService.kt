package com.example.lab12

import android.app.Service
import android.content.Intent
import android.os.IBinder

class MyService : Service() {

    override fun onCreate() {
        super.onCreate()
        // 使用 Thread 執行耗時任務
        Thread {
            try {
                Thread.sleep(3000) // 延遲三秒
                // 宣告 Intent，從 MyService 啟動 SecActivity
                val intent = Intent(this, SecActivity::class.java)
                // 加入 Flag 表示要產生一個新的 Activity 實例
                intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK
                startActivity(intent)
            } catch (e: InterruptedException) {
                e.printStackTrace()
            }
        }.start()
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        // 返回 START_NOT_STICKY 表示 Service 結束後不會重啟
        return START_NOT_STICKY
    }

    // 綁定 Service 時調用，用於與 Activity 進行溝通
    // 這裡不需要，所以返回 null
    override fun onBind(intent: Intent): IBinder? = null
}