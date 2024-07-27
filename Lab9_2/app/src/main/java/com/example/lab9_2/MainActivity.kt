package com.example.lab9_2

import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.LinearLayout
import android.widget.ProgressBar
import android.widget.RadioButton
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import kotlin.math.pow

class MainActivity : AppCompatActivity() {
    // 建立變數以利後續綁定元件
    private lateinit var btnCalculate: Button
    private lateinit var edHeight: EditText
    private lateinit var edWeight: EditText
    private lateinit var edAge: EditText
    private lateinit var tvWeightResult: TextView
    private lateinit var tvFatResult: TextView
    private lateinit var tvBmiResult: TextView
    private lateinit var tvProgress: TextView
    private lateinit var progressBar: ProgressBar
    private lateinit var llProgress: LinearLayout
    private lateinit var btnBoy: RadioButton

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        // 將變數與 XML 元件綁定
        btnCalculate = findViewById(R.id.btnCalculate)
        edHeight = findViewById(R.id.edHeight)
        edWeight = findViewById(R.id.edWeight)
        edAge = findViewById(R.id.edAge)
        tvWeightResult = findViewById(R.id.tvWeightResult)
        tvFatResult = findViewById(R.id.tvFatResult)
        tvBmiResult = findViewById(R.id.tvBmiResult)
        tvProgress = findViewById(R.id.tvProgress)
        progressBar = findViewById(R.id.progressBar)
        llProgress = findViewById(R.id.llProgress)
        btnBoy = findViewById(R.id.btnBoy)

        // 對計算按鈕設定監聽器
        btnCalculate.setOnClickListener {
            when {
                edHeight.text.isEmpty() -> showToast("請輸入身高")
                edWeight.text.isEmpty() -> showToast("請輸入體重")
                edAge.text.isEmpty() -> showToast("請輸入年齡")
                else -> runThread() // 執行 runThread 方法
            }
        }
    }

    // 建立 showToast 方法顯示 Toast 訊息
    private fun showToast(msg: String) =
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show()

    // 用 Thread 模擬檢測過程
    private fun runThread() {
        tvWeightResult.text = "標準體重\n無"
        tvFatResult.text = "體脂肪\n無"
        tvBmiResult.text = "BMI\n無"
        // 初始化進度條
        progressBar.progress = 0
        tvProgress.text = "0%"
        // 顯示進度條
        llProgress.visibility = View.VISIBLE

        Thread {
            var progress = 0
            // 建立迴圈執行一百次共延長五秒
            while (progress < 100) {
                // 執行緒延遲 50ms 後執行
                try {
                    Thread.sleep(50)
                } catch (ignored: InterruptedException) {
                }
                // 計數加一
                progress++
                // 切換到 Main Thread 執行進度更新
                runOnUiThread {
                    progressBar.progress = progress
                    tvProgress.text = "$progress%"
                }
            }

            val height = edHeight.text.toString().toDouble() // 身高
            val weight = edWeight.text.toString().toDouble() // 體重
            val age = edAge.text.toString().toDouble() // 年齡
            val bmi = weight / ((height / 100).pow(2)) // BMI
            // 計算男女的體脂率並使用 Pair 類別進行解構宣告
            val (standWeight, bodyFat) = if (btnBoy.isChecked) {
                Pair((height - 80) * 0.7, 1.39 * bmi + 0.16 * age - 19.34)
            } else {
                Pair((height - 70) * 0.6, 1.39 * bmi + 0.16 * age - 9)
            }
            // 切換到 Main Thread 更新畫面
            runOnUiThread {
                llProgress.visibility = View.GONE
                tvWeightResult.text = "標準體重 \n${String.format("%.2f", standWeight)}"
                tvFatResult.text = "體脂肪 \n${String.format("%.2f", bodyFat)}"
                tvBmiResult.text = "BMI \n${String.format("%.2f", bmi)}"
            }
        }.start()
    }
}