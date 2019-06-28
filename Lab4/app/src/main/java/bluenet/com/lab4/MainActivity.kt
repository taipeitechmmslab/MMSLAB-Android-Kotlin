package bluenet.com.lab4

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        //判斷Bundle是否不為空
        val b = data?.extras?: return
        //驗證請求對象與回傳狀態
        if(requestCode==1 && resultCode== Activity.RESULT_OK){
            //讀取Bundle中的資料
            tv_meal.text = String.format("飲料: %s\n\n甜度: %s\n\n冰塊: %s\n\n",
                b.getString("drink"), b.getString("sugar"), b.getString("ice"))
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        //『選擇』按鈕點擊監聽
        btn_choice.setOnClickListener {
            //透過startActivityForResult發出Intent，並紀錄請求對象
            startActivityForResult(Intent(this, Main2Activity::class.java),1)
        }
    }
}
