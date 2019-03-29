package bluenet.com.lab8

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_main2.*

class Main2Activity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main2)

        btn_send.setOnClickListener {
            when{
                //判斷是否輸入姓名與電話
                ed_name.length()<1 ->Toast.makeText(this,"請輸入姓名",Toast.LENGTH_SHORT).show()
                ed_phone.length()<1 ->Toast.makeText(this,"請輸入電話",Toast.LENGTH_SHORT).show()
                else->{
                    //將姓名與電話放入Bundle
                    val b = Bundle()
                    b.putString("name", ed_name.text.toString())
                    b.putString("phone", ed_phone.text.toString())
                    //透過setResult將資料回傳
                    setResult(Activity.RESULT_OK, Intent().putExtras(b))
                    //結束Main2Activity
                    finish()
                }
            }
        }
    }
}
