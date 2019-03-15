package bluenet.com.lab6

import android.os.Bundle
import android.view.Gravity
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        //宣告要顯示在的列表上的字串
        val array = arrayOf("項目1", "項目2", "項目3", "項目4", "項目5")

        btn_toast.setOnClickListener {
            //使用預設Toast顯示文字訊息
            Toast.makeText(this,"預設Toast", Toast.LENGTH_SHORT).show()
        }

        btn_custom.setOnClickListener {
            //宣告Toast物件
            val toast = Toast(this)
            //設定Toast的顯示位置
            toast.setGravity(Gravity.TOP, 0, 50)
            //設定Toast持續時間
            toast.duration = Toast.LENGTH_SHORT
            //設定自定義的Toast畫面
            toast.view = View.inflate(this, R.layout.toast_custom,null)
            //顯示Toast
            toast.show()
        }

        btn_dialog1.setOnClickListener {
            //宣告AlertDialog物件，setButton可以在Dialog對應位置顯示按鈕
            AlertDialog.Builder(this)
                .setTitle("按鍵式對話框") //顯示標題
                .setMessage("對話框內容")    //顯示文字內容
                .setNeutralButton("取消") { dialog, which ->
                    Toast.makeText(this,"取消", Toast.LENGTH_SHORT).show()
                }
                .setNegativeButton("拒絕") { dialog, which ->
                    Toast.makeText(this,"拒絕", Toast.LENGTH_SHORT).show()
                }
                .setPositiveButton("確定") { dialog, which ->
                    Toast.makeText(this,"確定", Toast.LENGTH_SHORT).show()
                }.show()    //顯示Dialog
        }

        btn_dialog2.setOnClickListener {
            //宣告AlertDialog物件，setItems可以在Dialog顯示列表
            AlertDialog.Builder(this)
                .setTitle("列表式對話框") //顯示標題
                .setItems(array) { dialogInterface, i ->
                    Toast.makeText(this,"你選的是" + array[i], Toast.LENGTH_SHORT).show()
                }.show()    //顯示Dialog
        }

        btn_dialog3.setOnClickListener {
            //宣告變數用以保存選擇位置
            var position = 0
            //宣告AlertDialog物件，setSingleChoiceItems可以在Dialog顯示單選框
            AlertDialog.Builder(this)
                .setTitle("單選式對話框") //顯示標題
                .setSingleChoiceItems(array, 0) { dialogInterface, i ->
                    position = i
                }
                .setPositiveButton("確定") { dialog, which ->
                    Toast.makeText(this,"你選的是" + array[position], Toast.LENGTH_SHORT).show()
                }.show()    //顯示Dialog
        }
    }
}
