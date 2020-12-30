package com.italkutalk.lab9_2

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlin.math.pow

class MainActivity : AppCompatActivity() {
    //建立變數以利後續綁定元件
    private lateinit var btn_calculate: Button
    private lateinit var ed_height: EditText
    private lateinit var ed_weight: EditText
    private lateinit var ed_age: EditText
    private lateinit var tv_weight: TextView
    private lateinit var tv_fat: TextView
    private lateinit var tv_bmi: TextView
    private lateinit var tv_progress: TextView
    private lateinit var progressBar2: ProgressBar
    private lateinit var ll_progress: LinearLayout
    private lateinit var btn_boy: RadioButton
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        //將變數與 XML 元件綁定
        btn_calculate = findViewById(R.id.btn_calculate)
        ed_height = findViewById(R.id.ed_height)
        ed_weight = findViewById(R.id.ed_weight)
        ed_age = findViewById(R.id.ed_age)
        tv_weight = findViewById(R.id.tv_weight)
        tv_fat = findViewById(R.id.tv_fat)
        tv_bmi = findViewById(R.id.tv_bmi)
        tv_progress = findViewById(R.id.tv_progress)
        progressBar2 = findViewById(R.id.progressBar2)
        ll_progress = findViewById(R.id.ll_progress)
        btn_boy = findViewById(R.id.btn_boy)
        //對計算按鈕設定監聽器
        btn_calculate.setOnClickListener {
            when {
                ed_height.length() < 1 -> showToast("請輸入身高")
                ed_weight.length() < 1 -> showToast("請輸入體重")
                ed_age.length() < 1 -> showToast("請輸入年齡")
                else -> runCoroutines() //執行 runCoroutines 方法
            }
        }
    }
    //建立 showToast 方法顯示 Toast 訊息
    private fun showToast(msg: String) =
            Toast.makeText(this, msg, Toast.LENGTH_SHORT).show()
    //用 Coroutines 模擬檢測過程
    private fun runCoroutines() {
        tv_weight.text = "標準體重\n 無"
        tv_fat.text = "體脂肪\n 無"
        tv_bmi.text = "BMI\n 無"
        //初始化進度條
        progressBar2.progress = 0
        tv_progress.text = "0%"
        //顯示進度條
        ll_progress.visibility = View.VISIBLE
        GlobalScope.launch(Dispatchers.Main) {
            var progress = 0
            //建立迴圈執行一百次共延長五秒
            while (progress < 100) {
                //執行緒延遲 50ms 後執行
                delay(50)
                //執行進度更新
                progressBar2.progress = progress
                tv_progress.text = "$progress%"
                //計數加一
                progress++
            }
            ll_progress.visibility = View.GONE
            val height = ed_height.text.toString().toDouble() //身高
            val weight = ed_weight.text.toString().toDouble() //體重
            val age = ed_age.text.toString().toDouble() //年齡
            val bmi = weight / ((height / 100).pow(2)) //BMI
            //計算男女的體脂率並使用 Pair 類別進行解構宣告
            val (stand_weight, body_fat) = if (btn_boy.isChecked) {
                Pair((height - 80) * 0.7, 1.39 * bmi + 0.16 * age - 19.34)
            } else {
                Pair((height - 70) * 0.6, 1.39 * bmi + 0.16 * age - 9)
            }
            tv_weight.text = "標準體重 \n${String.format("%.2f", stand_weight)}"
            tv_fat.text = "體脂肪 \n${String.format("%.2f", body_fat)}"
            tv_bmi.text = "BMI \n${String.format("%.2f", bmi)}"
        }
    }
}