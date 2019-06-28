package bluenet.com.lab10

import android.app.Service
import android.content.Intent
import android.os.Handler
import android.os.IBinder

class MyService : Service() {
    override fun onCreate() {
        super.onCreate()
        //延遲5秒
        Handler().postDelayed({
            //宣告Intent啟動Main2Activity
            val intent = Intent(this@MyService, Main2Activity::class.java)
            //Service要啟動Activity要加入Flag定義要去產生一個新的Activity
            intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK
            this@MyService.startActivity(intent)
        }, 5000)
    }

    override fun onStartCommand(intent: Intent, flags: Int, startid: Int): Int {
        super.onStartCommand(intent, flags, startid)
        //結束後不再重啟
        return START_NOT_STICKY
    }

    override fun onBind(intent: Intent): IBinder? {
        return null
    }
}
