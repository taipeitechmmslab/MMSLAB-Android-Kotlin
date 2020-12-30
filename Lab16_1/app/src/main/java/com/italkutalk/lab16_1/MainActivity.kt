package com.italkutalk.lab16_1

import android.database.sqlite.SQLiteDatabase
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.*

class MainActivity : AppCompatActivity() {
    private var items: ArrayList<String> = ArrayList()
    private lateinit var adapter: ArrayAdapter<String>
    private lateinit var dbrw: SQLiteDatabase
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        //取得資料庫實體
        dbrw = MyDBHelper(this).writableDatabase
        //宣告 Adapter 並連結 ListView
        adapter = ArrayAdapter(this,
                android.R.layout.simple_list_item_1, items)
        findViewById<ListView>(R.id.listView).adapter = adapter
        //設定監聽器
        setListener()
    }
    override fun onDestroy() {
        dbrw.close() //關閉資料庫
        super.onDestroy()
    }
    //設定監聽器
    private fun setListener() {
        val ed_book = findViewById<EditText>(R.id.ed_book)
        val ed_price = findViewById<EditText>(R.id.ed_price)
        findViewById<Button>(R.id.btn_insert).setOnClickListener {
            //判斷是否有填入書名或價格
            if (ed_book.length() < 1 || ed_price.length() < 1)
                showToast("欄位請勿留空")
            else
                try {
                    //新增一筆書籍紀錄於 myTable 資料表
                    dbrw.execSQL(
                            "INSERT INTO myTable(book, price) VALUES(?,?)",
                            arrayOf(ed_book.text.toString(),
                                    ed_price.text.toString())
                    )
                    showToast("新增:${ed_book.text},價格:${ed_price.text}")
                    cleanEditText()
                } catch (e: Exception) {
                    showToast("新增失敗:$e")
                }
        }
        findViewById<Button>(R.id.btn_update).setOnClickListener {
            //判斷是否有填入書名或價格
            if (ed_book.length() < 1 || ed_price.length() < 1)
                showToast("欄位請勿留空")
            else
                try {
                    //尋找相同書名的紀錄並更新 price 欄位的值
                    dbrw.execSQL("UPDATE myTable SET price = ${ed_price.text} WHERE book LIKE '${ed_book.text}'")
                    showToast("更新:${ed_book.text},價格:${ed_price.text}")
                    cleanEditText()
                } catch (e: Exception) {
                    showToast("更新失敗:$e")
                }
        }
        findViewById<Button>(R.id.btn_delete).setOnClickListener {
            //判斷是否有填入書名
            if (ed_book.length() < 1)
                showToast("書名請勿留空")
            else
                try {
                    //從 myTable 資料表刪除相同書名的紀錄
                    dbrw.execSQL("DELETE FROM myTable WHERE book LIKE '${ed_book.text}'")
                    showToast("刪除:${ed_book.text}")
                    cleanEditText()
                } catch (e: Exception) {
                    showToast("刪除失敗:$e")
                }
        }
        findViewById<Button>(R.id.btn_query).setOnClickListener {
            //若無輸入書名則 SQL 語法為查詢全部書籍，反之查詢該書名資料
            val queryString = if (ed_book.length() < 1)
                "SELECT * FROM myTable"
            else
                "SELECT * FROM myTable WHERE book LIKE '${ed_book.text}'"
            val c = dbrw.rawQuery(queryString, null)
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