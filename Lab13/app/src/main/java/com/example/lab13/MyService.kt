package com.example.lab13

import android.app.Service
import android.content.Intent
import android.os.IBinder

class MyService : Service() {
    private var channel = ""
    private lateinit var thread: Thread

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        // 解析 Intent 取得字串訊息
        intent?.extras?.let {
            channel = it.getString("channel", "")
        }

        sendBroadcast(
            when(channel) {
                "music" -> "歡迎來到音樂頻道"
                "new" -> "歡迎來到新聞頻道"
                "sport" -> "歡迎來到體育頻道"
                else -> "頻道錯誤"
            }
        )
        // 若 thread 被初始化過且正在運行，則中斷它
        if (::thread.isInitialized && thread.isAlive)
            thread.interrupt()

        thread = Thread {
            try {
                Thread.sleep(3000) // 延遲三秒
                sendBroadcast(
                    when(channel) {
                        "music" -> "即將播放本月TOP10音樂"
                        "new" -> "即將為您提供獨家新聞"
                        "sport" -> "即將播報本週NBA賽事"
                        else -> "頻道錯誤"
                    }
                )
            } catch (e: InterruptedException) {
                e.printStackTrace()
            }
        }

        thread.start() // 啟動執行緒
        return START_STICKY
    }

    override fun onBind(intent: Intent): IBinder? = null

    // 發送廣播訊息
    private fun sendBroadcast(msg: String) =
        sendBroadcast(Intent(channel).putExtra("msg", msg))
}