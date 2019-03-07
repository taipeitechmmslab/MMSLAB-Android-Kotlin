package bluenet.com.lab6

import android.os.Bundle
import android.view.Gravity
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val list_item = arrayOf("項目1", "項目2", "項目3", "項目4", "項目5")

        btn_toast.setOnClickListener {
            Toast.makeText(this,"預設Toast", Toast.LENGTH_SHORT).show()
        }

        btn_custom.setOnClickListener {
            val toast = Toast(this)
            toast.setGravity(Gravity.TOP, 0, 50)
            toast.duration = Toast.LENGTH_SHORT
            toast.view = layoutInflater.inflate(R.layout.toast_custom,null)
            toast.show()
        }

        btn_dialog1.setOnClickListener {
            AlertDialog.Builder(this)
                .setTitle("按鍵式對話框")
                .setMessage("對話框內容")
                .setNeutralButton("取消") { dialog, which ->
                    Toast.makeText(this,"取消", Toast.LENGTH_SHORT).show()
                }
                .setNegativeButton("拒絕") { dialog, which ->
                    Toast.makeText(this,"拒絕", Toast.LENGTH_SHORT).show()
                }
                .setPositiveButton("確定") { dialog, which ->
                    Toast.makeText(this,"確定", Toast.LENGTH_SHORT).show()
                }.show()
        }

        btn_dialog2.setOnClickListener {
            AlertDialog.Builder(this)
                .setTitle("列表式對話框")
                .setItems(list_item) { dialogInterface, i ->
                    Toast.makeText(this,"你選的是" + list_item[i], Toast.LENGTH_SHORT).show()
                }.show()
        }

        btn_dialog3.setOnClickListener {
            var position = 0

            AlertDialog.Builder(this)
                .setTitle("單選式對話框")
                .setSingleChoiceItems(list_item, 0) { dialogInterface, i ->
                    position = i
                }
                .setPositiveButton("確定") { dialog, which ->
                    Toast.makeText(this,"你選的是" + list_item[position], Toast.LENGTH_SHORT).show()
                }.show()
        }
    }
}
