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
        //判斷Bundle是否不為空
        val b = data?.extras?: return
        //驗證發出對象與回傳狀態
        if(requestCode==1 && resultCode== Activity.RESULT_OK){
            //新增聯絡人
            contacts.add(Contact(b.getString("name", ""), b.getString("phone", "")))
            //更新列表資料
            adapter.notifyDataSetChanged()
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        //建立LinearLayoutManager物件
        val layoutManager = LinearLayoutManager(this)
        //建立GridLayoutManager物件
        //val layoutManager = GridLayoutManager(this, 3)
        //設定垂直顯示
        layoutManager.orientation = RecyclerView.VERTICAL
        recyclerView.layoutManager = layoutManager

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