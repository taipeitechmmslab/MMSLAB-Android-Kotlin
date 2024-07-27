package com.example.lab16_2

import android.content.ContentValues
import android.net.Uri
import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.EditText
import android.widget.ListView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class MainActivity : AppCompatActivity() {
    private var items: ArrayList<String> = ArrayList()
    private lateinit var adapter: ArrayAdapter<String>
    // 定義Provider的Uri
    private val uri = Uri.parse("content://com.example.lab16")

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        // 宣告Adapter並連結ListView
        adapter = ArrayAdapter(this,
            android.R.layout.simple_list_item_1, items)
        findViewById<ListView>(R.id.listView).adapter = adapter
        // 設定監聽器
        setListener()
    }

    // 設定監聽器
    private fun setListener() {
        val edBook = findViewById<EditText>(R.id.edBook)
        val edPrice = findViewById<EditText>(R.id.edPrice)

        findViewById<Button>(R.id.btnInsert).setOnClickListener {
            val name = edBook.text.toString()
            val price = edPrice.text.toString()
            // 判斷是否有填入書名或價格
            if (name.isEmpty() || price.isEmpty())
                showToast("欄位請勿留空")
            else {
                val values = ContentValues()
                values.put("book", name)
                values.put("price", price)
                // 透過Resolver向Provider新增一筆書籍紀錄，並取得該紀錄的Uri
                val contentUri = contentResolver.insert(uri, values)
                // 判斷此紀錄的Uri是否為null，若不是則新增成功
                if (contentUri != null) {
                    showToast("新增:$name,價格:$price")
                    cleanEditText()
                } else
                    showToast("新增失敗")
            }
        }
        findViewById<Button>(R.id.btnUpdate).setOnClickListener {
            val name = edBook.text.toString()
            val price = edPrice.text.toString()
            // 判斷是否有填入書名或價格
            if (name.isEmpty() || price.isEmpty())
                showToast("欄位請勿留空")
            else {
                val values = ContentValues()
                values.put("price", price)
                // 透過Resolver向Provider更新特定書籍的價格，並取得更新的筆數
                val count = contentResolver.update(uri, values, name, null)
                // 判斷更新筆數是否大於零，若是則更新成功
                if (count > 0) {
                    showToast("更新:$name,價格:$price")
                    cleanEditText()
                } else
                    showToast("更新失敗")
            }
        }
        findViewById<Button>(R.id.btnDelete).setOnClickListener {
            val name = edBook.text.toString()
            // 判斷是否有填入書名
            if (name.isEmpty())
                showToast("書名請勿留空")
            else {
                // 透過Resolver向Provider刪除特定書籍，並取得刪除的筆數
                val count = contentResolver.delete(uri, name, null)
                // 判斷刪除筆數是否大於零，若是則刪除成功
                if (count > 0) {
                    showToast("刪除:${name}")
                    cleanEditText()
                } else
                    showToast("刪除失敗")
            }
        }
        findViewById<Button>(R.id.btnQuery).setOnClickListener {
            val name = edBook.text.toString()
            // 若無輸入書名則條件為null，反之條件為特定書名
            val selection = name.ifEmpty { null }
            // 透過Resolver向Provider查詢書籍，並取得Cursor
            val c = contentResolver.query(uri, null, selection, null, null)
            c ?: return@setOnClickListener // 若Cursor為null則返回
            c.moveToFirst() // 從第一筆開始輸出
            items.clear() // 清空舊資料
            showToast("共有${c.count}筆資料")
            for (i in 0 until c.count) {
                // 加入新資料
                items.add("書名:${c.getString(0)}\t\t\t\t價格:${c.getInt(1)}")
                c.moveToNext() // 移動到下一筆
            }
            adapter.notifyDataSetChanged() // 更新列表資料
            c.close() // 關閉Cursor
        }
    }

    // 建立showToast方法顯示Toast訊息
    private fun showToast(text: String) =
        Toast.makeText(this,text, Toast.LENGTH_LONG).show()

    // 清空輸入的書名與價格
    private fun cleanEditText() {
        findViewById<EditText>(R.id.edBook).setText("")
        findViewById<EditText>(R.id.edPrice).setText("")
    }
}