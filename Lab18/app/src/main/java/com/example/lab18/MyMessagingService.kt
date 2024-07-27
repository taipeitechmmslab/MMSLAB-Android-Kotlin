package com.example.lab18

import android.util.Log
import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage

// 建立一個類別，使其繼承FirebaseMessagingService類別
class MyMessagingService : FirebaseMessagingService() {
    // 取得新的Token時被調用，通常會於第一次啟動應用程式時進入
    override fun onNewToken(token: String) {
        super.onNewToken(token)
        Log.e("onNewToken", token)
    }
    // 應用程式正呈現於螢幕且收到推播通知時進入
    override fun onMessageReceived(msg: RemoteMessage) {
        super.onMessageReceived(msg)
        // 藉由forEach將通知附帶的資料取出
        msg.data.entries.forEach {
            Log.e("data", "key:${it.key}, value:${it.value}")
        }
    }
}
