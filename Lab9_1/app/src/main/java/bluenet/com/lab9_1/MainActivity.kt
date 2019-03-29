package bluenet.com.lab9_1

import android.annotation.SuppressLint
import android.os.AsyncTask
import android.os.Bundle
import android.os.Handler
import android.os.Message
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {
    //建立兩個計數暫存
    private var rabprogress = 0
    private var torprogress = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        btn_start.setOnClickListener {
            //關閉Button
            btn_start.isEnabled = false
            //初始化暫存與SeekBar
            rabprogress = 0
            torprogress = 0
            seekBar.progress = 0
            seekBar2.progress = 0
            //執行龜兔賽跑
            runThread()
            runAsyncTask()
        }
    }

    private fun runThread() {
        object : Thread() {
            override fun run() {
                //重複執行到計數器不小於100為止
                while (rabprogress < 100 && torprogress < 100)
                    try {
                        //延遲100ms
                        sleep(100)
                        //隨機增加計數器0~2的值
                        rabprogress += (Math.random() * 3).toInt()
                        //建立Message物件
                        val msg = Message()
                        //加入代號
                        msg.what = 1
                        //透過sendMessage傳送訊息
                        mHandler.sendMessage(msg)
                    } catch (e: InterruptedException) {
                        e.printStackTrace()
                    }
            }
        }.start()   //執行Thread
    }

    private val mHandler = Handler(Handler.Callback { msg ->
        when (msg.what) {
            //寫入計數器數值到SeekBar
            1 -> {
                seekBar.progress = rabprogress
                if (rabprogress >= 100 && torprogress < 100) {
                    Toast.makeText(this, "兔子勝利", Toast.LENGTH_SHORT).show()
                    //啟動Button
                    btn_start.isEnabled = true
                }
            }
        }
        true
    })

    @SuppressLint("StaticFieldLeak")
    private fun runAsyncTask() {
        object : AsyncTask<Void, Int, Boolean>() {
            override fun doInBackground(vararg voids: Void): Boolean? {
                //重複執行到計數器不小於100為止
                while (torprogress < 100 && rabprogress < 100)
                    try {
                        //延遲100ms
                        Thread.sleep(100)
                        //隨機增加計數器0~2的值
                        torprogress += (Math.random() * 3).toInt()
                        //更新進度
                        publishProgress(torprogress)
                    } catch (e: InterruptedException) {
                        e.printStackTrace()
                    }

                return true
            }

            override fun onProgressUpdate(vararg values: Int?) {
                super.onProgressUpdate(*values)
                values[0]?.let {
                    //寫入計數器數值到SeekBar
                    seekBar2.progress = it
                }
            }

            override fun onPostExecute(status: Boolean?) {
                if (torprogress >= 100 && rabprogress < 100) {
                    Toast.makeText(this@MainActivity,"烏龜勝利", Toast.LENGTH_SHORT).show()
                    //啟動Button
                    btn_start.isEnabled = true
                }
            }
        }.execute() //執行AsyncTask
    }
}
