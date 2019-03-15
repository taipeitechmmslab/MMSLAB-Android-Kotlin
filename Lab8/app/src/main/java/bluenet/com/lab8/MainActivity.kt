package bluenet.com.lab8

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    private lateinit var adapter : MyAdapter
    private val contacts =  ArrayList<Contact>()

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        data?.extras?.let {
            //驗證發出對象與回傳狀態
            if(requestCode==1 && resultCode== Activity.RESULT_OK){
                //建立Contact物件，讀取Bundle中的資料
                contacts.add(Contact(it.getString("name"), it.getString("phone")))
                //更新列表資料
                adapter.notifyDataSetChanged()
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        //建立LinearLayoutManager物件
        val linearLayoutManager = LinearLayoutManager(this)
        //設定垂直顯示
        linearLayoutManager.orientation = RecyclerView.VERTICAL
        recyclerView.layoutManager = linearLayoutManager

        adapter = MyAdapter(contacts)
        //連結Adapter
        recyclerView.adapter = adapter

        btn_add.setOnClickListener {
            //透過startActivityForResult發出Intent，並紀錄請求對象
            startActivityForResult(Intent(this, Main2Activity::class.java),1)
        }
    }
}
//自定義聯絡人類別
data class Contact(
    val name: String,   //姓名
    val phone: String   //電話
)