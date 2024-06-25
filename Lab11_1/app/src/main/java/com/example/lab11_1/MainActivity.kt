package com.example.lab11_1

import android.content.pm.PackageManager
import android.media.MediaPlayer
import android.media.MediaRecorder
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import java.io.File
import java.util.Calendar

class MainActivity : AppCompatActivity() {
    // 定義 MediaRecorder 與 MediaPlayer 變數
    private lateinit var recorder: MediaRecorder
    private lateinit var player: MediaPlayer

    // 定義錄音檔案資料夾與檔案名稱
    private lateinit var folder: File
    private var fileName = ""

    // 回傳權限要求後的結果
    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        // 判斷是否有結果且識別標籤相同
        if (grantResults.isNotEmpty() && requestCode == 0) {
            // 取出結果並判斷是否允許權限
            if (grantResults[0] == PackageManager.PERMISSION_DENIED) {
                finish() //若拒絕給予錄音權限，則關閉應用程式
            } else {
                // 允許錄音權限，所以正常執行應用程式
                doInitialize()
                setListener()
            }
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

        // 宣告錄音權限
        val permission = android.Manifest.permission.RECORD_AUDIO
        // 判斷使用者是否已允許錄音權限
        if (ActivityCompat.checkSelfPermission(this, permission)
            != PackageManager.PERMISSION_GRANTED
        ) {
            // 向使用者要求權限
            ActivityCompat.requestPermissions(
                this, arrayOf(permission), 0
            )
        } else {
            // 由於已允許錄音權限，所以正常執行應用程式
            doInitialize()
            setListener()
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        // 如果 recorder 已經初始化過，則釋放錄音器佔用資源
        if (::recorder.isInitialized) recorder.release()
        // 如果 player 已經初始化過，則釋放播放器佔用資源
        if (::player.isInitialized) player.release()
    }

    // 執行初始化
    private fun doInitialize() {
        // 初始化 recorder
        // 如果 SDK 版本大於等于 31，則使用 MediaRecorder(Context)
        @Suppress("DEPRECATION")
        recorder = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
            MediaRecorder(this)
        } else {
            MediaRecorder()
        }
        // 初始化 player
        player = MediaPlayer()

        // 定義資料夾名稱
        folder = File(filesDir.absolutePath + "/record")

        // 如果不存在資料夾，則建立存放錄音檔的資料夾
        if (!folder.exists()) {
            folder.mkdirs()
        }
    }

    // 設定資料夾
    private fun setListener() {
        // 將變數與XML元件綁定
        val btnRecord = findViewById<Button>(R.id.btnRecord)
        val btnStopRecord = findViewById<Button>(R.id.btnStopRecord)
        val btnPlay = findViewById<Button>(R.id.btnPlay)
        val btnStopPlay = findViewById<Button>(R.id.btnStopPlay)
        val tvTitle = findViewById<TextView>(R.id.tvTitle)

        // 設定開始錄音的監聽器
        btnRecord.setOnClickListener {
            fileName = "${Calendar.getInstance().time.time}" // 定義檔案名稱為目前時間
            recorder.setAudioSource(MediaRecorder.AudioSource.MIC) // 聲音來源為麥克風
            recorder.setOutputFormat(MediaRecorder.OutputFormat.MPEG_4) // 設定輸出格式
            recorder.setAudioEncoder(MediaRecorder.AudioEncoder.AMR_NB) // 設定編碼器
            recorder.setOutputFile(File(folder, fileName).absolutePath) // 設定輸出路徑
            recorder.prepare() // 準備錄音
            recorder.start() // 開始錄音
            tvTitle.text = "錄音中..."
            btnRecord.isEnabled = false // 關閉錄音按鈕
            btnStopRecord.isEnabled = true // 開啟停止錄音按鈕
            btnPlay.isEnabled = false // 關閉播放按鈕
            btnStopPlay.isEnabled = false // 關閉停止播放按鈕
        }

        // 設定停止錄音的監聽器
        btnStopRecord.setOnClickListener {
            // 若使用模擬器停止錄音容易產生例外，所以使用 try-catch 處理
            try {
                val file = File(folder, fileName) // 定義錄音檔案
                recorder.stop() // 結束錄音
                tvTitle.text = "已儲存至${file.absolutePath}"
                btnRecord.isEnabled = true // 開啟錄音按鈕
                btnStopRecord.isEnabled = false // 關閉停止錄音按鈕
                btnPlay.isEnabled = true // 開啟播放按鈕
                btnStopPlay.isEnabled = false // 關閉停止播放按鈕
            } catch (e: Exception) {
                e.printStackTrace()
                recorder.reset() // 重置錄音器
                tvTitle.text = "錄音失敗"
                btnRecord.isEnabled = true // 開啟錄音按鈕
                btnStopRecord.isEnabled = false // 關閉停止錄音按鈕
                btnPlay.isEnabled = false // 關閉播放按鈕
                btnStopPlay.isEnabled = false // 關閉停止播放按鈕
            }
        }

        // 設定開始播放的監聽器
        btnPlay.setOnClickListener {
            val file = File(folder, fileName) // 定義播放檔案
            player.setDataSource(applicationContext, Uri.fromFile(file)) // 設定音訊來源
            player.setVolume(1f, 1f) // 設定左右聲道音量
            player.prepare() // 準備播放
            player.start() // 開始播放
            tvTitle.text = "播放中..."
            btnRecord.isEnabled = false // 關閉錄音按鈕
            btnStopRecord.isEnabled = false // 關閉停止錄音按鈕
            btnPlay.isEnabled = false // 關閉播放按鈕
            btnStopPlay.isEnabled = true // 開啟停止播放按鈕
        }

        // 設定停止播放的監聽器
        btnStopPlay.setOnClickListener {
            player.stop() // 停止播放
            player.reset() // 重置播放器
            tvTitle.text = "播放結束"
            btnRecord.isEnabled = true // 開啟錄音按鈕
            btnStopRecord.isEnabled = false // 關閉停止錄音按鈕
            btnPlay.isEnabled = true // 開啟播放按鈕
            btnStopPlay.isEnabled = false // 關閉停止播放按鈕
        }

        // 設定播放器播放完畢的監聽器
        player.setOnCompletionListener {
            it.reset() // 重置播放器
            tvTitle.text = "播放結束"
            btnRecord.isEnabled = true // 開啟錄音按鈕
            btnStopRecord.isEnabled = false // 關閉停止錄音按鈕
            btnPlay.isEnabled = true // 開啟播放按鈕
            btnStopPlay.isEnabled = false // 關閉停止播放按鈕
        }
    }
}