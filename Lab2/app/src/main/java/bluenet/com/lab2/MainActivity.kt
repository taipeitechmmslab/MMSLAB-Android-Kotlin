package bluenet.com.lab2

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        //『猜拳』按鈕點擊監聽
        btn_mora.setOnClickListener {
            //判斷使用者是否輸入姓名
            if(ed_name.length()<1)
                tv_text.text = "請輸入玩家姓名"
            else{
                //顯示使用者姓名、出拳
                tv_name.text = "名字\n${ed_name.text}"
                tv_mmora.text = "我方出拳\n${when {
                    btn_scissor.isChecked -> "剪刀"
                    btn_stone.isChecked -> "石頭"
                    else -> "布"
                }}"
                //使用亂數產生電腦出拳
                val computer = (Math.random()*3).toInt()
                tv_cmora.text = "電腦出拳\n${when (computer) {
                    0 -> "剪刀"
                    1 -> "石頭"
                    else -> "布"
                }}"
                //判斷勝負
                when{
                    btn_scissor.isChecked && computer==2 || btn_stone.isChecked && computer==0 ||
                            btn_paper.isChecked && computer==1 ->{
                        tv_winner.text = "勝利者\n${ed_name.text}"
                        tv_text.text = "恭喜你獲勝了！！！"
                    }
                    btn_scissor.isChecked && computer==1 || btn_stone.isChecked && computer==2 ||
                            btn_paper.isChecked && computer==0 ->{
                        tv_winner.text = "勝利者\n電腦"
                        tv_text.text = "可惜，電腦獲勝了！"
                    }
                    else ->{
                        tv_winner.text = "勝利者\n平手"
                        tv_text.text = "平局，請再試一次！"
                    }
                }
            }
        }
    }
}