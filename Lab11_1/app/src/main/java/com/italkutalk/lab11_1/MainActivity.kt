package com.italkutalk.lab11_1

import android.content.pm.PackageManager
import android.media.MediaPlayer
import android.media.MediaRecorder
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.core.app.ActivityCompat
import java.io.File
import java.util.*

class MainActivity : AppCompatActivity() {
    private val recorder = MediaRecorder()
    private val player = MediaPlayer()
    private lateinit var folder: File
    private var fileName = ""
    //回傳權限要求後的結果
    override fun onRequestPermissionsResult(
            requestCode: Int,
            permissions: Array<out String>,
            grantResults: IntArray
    ) {
        //判斷是否有結果且識別標籤相同
        if (grantResults.isNotEmpty() && requestCode == 0) {
            //取出結果並判斷是否允許權限
            val result = grantResults[0]
            if (result == PackageManager.PERMISSION_DENIED)
                finish() //若拒絕給予錄音權限，則關閉應用程式
            else {
                //因允許錄音權限，所以正常執行應用程式
                setFolder()
                setListener()
            }
        }
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        //宣告錄音權限
        val permission = android.Manifest.permission.RECORD_AUDIO
        //判斷使用者是否已允許錄音權限
        if (ActivityCompat.checkSelfPermission(this, permission)
                != PackageManager.PERMISSION_GRANTED) {
            //向使用者要求權限
            ActivityCompat.requestPermissions(this,
                    arrayOf(permission), 0)
        } else {
            //因已允許錄音權限，所以正常執行應用程式
            setFolder()
            setListener()
        }
    }
    override fun onDestroy() {
        recorder.release() //釋放錄音器佔用資源
        player.release() //釋放播放器佔用資源
        super.onDestroy()
    }
    private fun setFolder() { //設定資料夾
        folder = File(filesDir.absolutePath+"/record") //定義資料夾名稱
        if (!folder.exists()) {
            folder.mkdirs() //建立存放錄音檔的資料夾
        }
    }
    private fun setListener() { //設定監聽器
        //將變數與 XML 元件綁定
        val btn_record = findViewById<Button>(R.id.btn_record)
        val btn_stop_record = findViewById<Button>(R.id.btn_stop_record)
        val btn_play = findViewById<Button>(R.id.btn_play)
        val btn_stop_play = findViewById<Button>(R.id.btn_stop_play)
        val textView = findViewById<TextView>(R.id.textView)
        btn_record.setOnClickListener { //設定開始錄音的監聽器
            fileName = "${Calendar.getInstance().time.time}" //定義檔案名稱為目前時間
            recorder.setAudioSource(MediaRecorder.AudioSource.MIC) //聲音來源為麥克風
            recorder.setOutputFormat(MediaRecorder.OutputFormat.MPEG_4) //設定輸出格式
            recorder.setAudioEncoder(MediaRecorder.AudioEncoder.AMR_NB) //設定編碼器
            recorder.setOutputFile(File(folder, fileName).absolutePath) //設定輸出路徑
            recorder.prepare() //準備錄音
            recorder.start() //開始錄音
            textView.text = "錄音中..."
            btn_record.isEnabled = false //關閉錄音按鈕
            btn_stop_record.isEnabled = true //開啟停止錄音按鈕
            btn_play.isEnabled = false //關閉播放按鈕
            btn_stop_play.isEnabled = false //關閉停止播放按鈕
        }
        btn_stop_record.setOnClickListener { //設定停止錄音的監聽器
            try { //若使用模擬器停止錄音容易產生例外，所以使用 try-catch 處理
                val file = File(folder, fileName) //定義錄音檔案
                recorder.stop() //結束錄音
                textView.text = "已儲存至${file.absolutePath}"
                btn_record.isEnabled = true //開啟錄音按鈕
                btn_stop_record.isEnabled = false //關閉停止錄音按鈕
                btn_play.isEnabled = true //開啟播放按鈕
                btn_stop_play.isEnabled = false //關閉停止播放按鈕
            } catch (e: Exception) {
                e.printStackTrace()
                recorder.reset() //重置錄音器
                textView.text = "錄音失敗"
                btn_record.isEnabled = true //開啟錄音按鈕
                btn_stop_record.isEnabled = false //關閉停止錄音按鈕
                btn_play.isEnabled = false //關閉播放按鈕
                btn_stop_play.isEnabled = false //關閉停止播放按鈕
            }
        }
        btn_play.setOnClickListener { //設定開始播放的監聽器
            val file = File(folder, fileName) //定義播放檔案
            player.setDataSource(applicationContext, Uri.fromFile(file)) //設定音訊來源
            player.setVolume(1f, 1f) //設定左右聲道音量
            player.prepare() //準備播放
            player.start() //開始播放
            textView.text = "播放中..."

            btn_record.isEnabled = false //關閉錄音按鈕
            btn_stop_record.isEnabled = false //關閉停止錄音按鈕
            btn_play.isEnabled = false //關閉播放按鈕
            btn_stop_play.isEnabled = true //開啟停止播放按鈕
        }
        btn_stop_play.setOnClickListener { //設定停止播放的監聽器
            player.stop() //停止播放
            player.reset() //重置播放器
            textView.text = "播放結束"

            btn_record.isEnabled = true //開啟錄音按鈕
            btn_stop_record.isEnabled = false //關閉停止錄音按鈕
            btn_play.isEnabled = true //開啟播放按鈕
            btn_stop_play.isEnabled = false //關閉停止播放按鈕
        }
        player.setOnCompletionListener { //設定播放器播放完畢的監聽器
            it.reset() //重置播放器
            textView.text = "播放結束"
            btn_record.isEnabled = true //開啟錄音按鈕
            btn_stop_record.isEnabled = false //關閉停止錄音按鈕
            btn_play.isEnabled = true //開啟播放按鈕
            btn_stop_play.isEnabled = false //關閉停止播放按鈕
        }
    }
}