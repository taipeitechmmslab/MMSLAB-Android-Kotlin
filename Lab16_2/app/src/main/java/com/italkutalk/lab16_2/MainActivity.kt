package com.italkutalk.lab16_2

import android.content.ContentValues
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.*

class MainActivity : AppCompatActivity() {
    private var items: ArrayList<String> = ArrayList()
    private lateinit var adapter: ArrayAdapter<String>
    //定義 Provider 的 Uri
    private val uri = Uri.parse("content://com.italkutalk.lab16")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        //宣告 Adapter 並連結 ListView
        adapter = ArrayAdapter(this,
                android.R.layout.simple_list_item_1, items)
        findViewById<ListView>(R.id.listView).adapter = adapter
        //設定監聽器
        setListener()
    }
    //設定監聽器
    private fun setListener() {
        val ed_book = findViewById<EditText>(R.id.ed_book)
        val ed_price = findViewById<EditText>(R.id.ed_price)
        findViewById<Button>(R.id.btn_insert).setOnClickListener {
            val name = ed_book.text.toString()
            val price = ed_price.text.toString()
            //判斷是否有填入書名或價格
            if (name.isEmpty() || price.isEmpty())
                showToast("欄位請勿留空")
            else {
                val values = ContentValues()
                values.put("book", name)
                values.put("price", price)
                //透過 Resolver 向 Provider 新增一筆書籍紀錄，並取得該紀錄的 Uri
                val contentUri = contentResolver.insert(uri, values)
                //判斷此紀錄的 Uri 是否為 null，若不是則新增成功
                if (contentUri != null) {
                    showToast("新增:$name,價格:$price")
                    cleanEditText()
                } else
                    showToast("新增失敗")
            }
        }
        findViewById<Button>(R.id.btn_update).setOnClickListener {
            val name = ed_book.text.toString()
            val price = ed_price.text.toString()
            //判斷是否有填入書名或價格
            if (name.isEmpty() || price.isEmpty())
                showToast("欄位請勿留空")
            else {
                val values = ContentValues()
                values.put("price", price)
                //透過 Resolver 向 Provider 更新特定書籍的價格，並取得更新的筆數
                val count = contentResolver.update(uri, values, name, null)
                //判斷更新筆數是否大於零，若是則更新成功
                if (count > 0) {
                    showToast("更新:$name,價格:$price")
                    cleanEditText()
                } else
                    showToast("更新失敗")
            }
        }
        findViewById<Button>(R.id.btn_delete).setOnClickListener {
            val name = ed_book.text.toString()
            //判斷是否有填入書名
            if (name.isEmpty())
                showToast("書名請勿留空")
            else {
                //透過 Resolver 向 Provider 刪除特定書籍，並取得刪除的筆數
                val count = contentResolver.delete(uri, name, null)
                //判斷刪除筆數是否大於零，若是則刪除成功
                if (count > 0) {
                    showToast("刪除:${name}")
                    cleanEditText()
                } else
                    showToast("刪除失敗")
            }
        }
        findViewById<Button>(R.id.btn_query).setOnClickListener {
            val name = ed_book.text.toString()
            //若無輸入書名則條件為 null，反之條件為特定書名
            val selection = if (name.isEmpty()) null else name
            //透過 Resolver 向 Provider 查詢書籍，並取得 Cursor
            val c = contentResolver.query(uri, null, selection, null, null)
            c ?: return@setOnClickListener //若 Cursor 為 null 則返回
            c.moveToFirst() //從第一筆開始輸出
            items.clear() //清空舊資料
            showToast("共有${c.count}筆資料")
            for (i in 0 until c.count) {
                //加入新資料
                items.add("書名:${c.getString(0)}\t\t\t\t 價格:${c.getInt(1)}")
                c.moveToNext() //移動到下一筆
            }
            adapter.notifyDataSetChanged() //更新列表資料
            c.close() //關閉 Cursor
        }
    }
    //建立 showToast 方法顯示 Toast 訊息
    private fun showToast(text: String) =
            Toast.makeText(this,text, Toast.LENGTH_LONG).show()
    //清空輸入的書名與價格
    private fun cleanEditText() {
        findViewById<EditText>(R.id.ed_book).setText("")
        findViewById<EditText>(R.id.ed_price).setText("")
    }
}