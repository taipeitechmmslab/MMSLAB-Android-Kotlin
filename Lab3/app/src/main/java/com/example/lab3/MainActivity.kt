package com.example.lab3

import android.os.Bundle
import android.widget.Button
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import android.widget.EditText
import android.widget.RadioGroup
import android.widget.TextView

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        // Step1 定義元件變數，並通過 findViewById 取得元件
        val edName = findViewById<EditText>(R.id.edName)
        val tvText = findViewById<TextView>(R.id.tvText)
        val radioGroup = findViewById<RadioGroup>(R.id.radioGroup)
        val btnMora = findViewById<Button>(R.id.btnMora)
        val tvName = findViewById<TextView>(R.id.tvName)
        val tvWinner = findViewById<TextView>(R.id.tvWinner)
        val tvMyMora = findViewById<TextView>(R.id.tvMyMora)
        val tvTargetMora = findViewById<TextView>(R.id.tvTargetMora)

        // Step2 設定 btnMora 的點擊事件
        btnMora.setOnClickListener {
            // Step3 如果 edName 為空，則顯示提示文字
            if (edName.text.isEmpty()) {
                tvText.text = "請輸入玩家姓名"
                return@setOnClickListener
            }
            // Step4 從 edName 取得玩家姓名
            val playerName = edName.text.toString()
            // Step5 使用 (0..2).random() 會回傳 0~2 的整數，以此作為電腦的出拳
            val targetMora = (0..2).random()
            // Step6 透過 radioGroup.checkedRadioButtonId 取得選取的 RadioButton ID，
            // 並透過 when 判斷選取的是哪個 RadioButton ID，並回傳 0~2 作為玩家的出拳
            val myMora = when (radioGroup.checkedRadioButtonId) {
                R.id.btnScissor -> 0
                R.id.btnStone -> 1
                else -> 2
            }
            // Step8 設定玩家姓名、我方出拳、電腦出拳的文字
            tvName.text = "名字\n$playerName"
            tvMyMora.text = "我方出拳\n${getMoraString(myMora)}"
            tvTargetMora.text = "電腦出拳\n${getMoraString(targetMora)}"
            // Step9 判斷玩家和電腦誰獲勝
            when {
                myMora == targetMora -> {
                    tvWinner.text = "勝利者\n平手"
                    tvText.text = "平局，請再試一次！"
                }

                (myMora == 0 && targetMora == 2) ||
                        (myMora == 1 && targetMora == 0) ||
                        (myMora == 2 && targetMora == 1) -> {
                    tvWinner.text = "勝利者\n$playerName"
                    tvText.text = "恭喜你獲勝了！！！"
                }

                else -> {
                    tvWinner.text = "勝利者\n電腦"
                    tvText.text = "可惜，電腦獲勝了！"
                }
            }
        }
    }

    // Step7 傳入 0, 1, 2，回傳對應的文字，分別是剪刀、石頭、布
    private fun getMoraString(mora: Int): String {
        return when (mora) {
            0 -> "剪刀"
            1 -> "石頭"
            else -> "布"
        }
    }
}
