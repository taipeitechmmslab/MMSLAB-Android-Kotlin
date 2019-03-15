package bluenet.com.lab11

import android.app.Service
import android.content.Intent
import android.os.Bundle
import android.os.IBinder

class MyService : Service() {
    companion object {
        //計數器狀態
        var flag: Boolean = false
    }
    //計數器數值
    private var h = 0
    private var m = 0
    private var s = 0

    override fun onBind(intent: Intent): IBinder {
        TODO("Return the communication channel to the service.")
    }

    override fun onStartCommand(intent: Intent, flags: Int, startID: Int): Int {
        flag = intent.getBooleanExtra("flag", false)

        object : Thread() {
            override fun run() {
                while (flag) {
                    try {
                        //延遲1s
                        Thread.sleep(1000)
                    } catch (e: InterruptedException) {
                        e.printStackTrace()
                    }
                    //計數器加一
                    s++
                    //秒數大於60進位
                    if (s >= 60) {
                        s = 0
                        m++
                        //分鐘數大於60進位
                        if (m >= 60) {
                            m = 0
                            h++
                        }
                    }
                    //發送帶有『MyMessage』識別字串的廣播
                    val intent = Intent("MyMessage")
                    //將時間放入Bundle
                    val bundle = Bundle()
                    bundle.putInt("H", h)
                    bundle.putInt("M", m)
                    bundle.putInt("S", s)
                    //發送廣播
                    sendBroadcast(intent.putExtras(bundle))
                }
            }
        }.start()
        //自動重啟，但不會保留Intent
        return Service.START_STICKY
    }
}
