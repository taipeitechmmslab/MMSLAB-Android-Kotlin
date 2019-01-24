package bluenet.com.lab13

import android.database.sqlite.SQLiteDatabase
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_main.*
import java.lang.Exception
import java.util.ArrayList

class MainActivity : AppCompatActivity() {
    private lateinit var dbrw: SQLiteDatabase

    private var items: ArrayList<String> = ArrayList()
    private lateinit var adapter: ArrayAdapter<String>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        dbrw = MySQLiteOpenHelper(this).writableDatabase

        adapter = ArrayAdapter(this, android.R.layout.simple_list_item_1, items)
        listView.adapter = adapter

        btn_query.setOnClickListener {
            val c = dbrw.rawQuery(if(ed_book.length()<1) "SELECT * FROM myTable"
            else "SELECT * FROM myTable WHERE book LIKE '${ed_book.text}'",null)

            c.moveToFirst()
            items.clear()
            Toast.makeText(this,"共有${c.count}筆資料", Toast.LENGTH_SHORT).show()
            for (i in 0 until c.count) {
                items.add("書名:${c.getString(0)}\t\t\t\t價格:${c.getInt(1)}")
                c.moveToNext()
            }

            adapter.notifyDataSetChanged()
            c.close()
        }

        btn_insert.setOnClickListener {
            if (ed_book.length()<1 || ed_price.length()<1)
                Toast.makeText(this,"欄位請勿留空", Toast.LENGTH_SHORT).show()
            else
                try{
                    dbrw.execSQL("INSERT INTO myTable(book, price) VALUES(?,?)", arrayOf<Any?>(ed_book.text.toString(), ed_price.text.toString()))
                    Toast.makeText(this,"新增書名${ed_book.text}    價格${ed_price.text}", Toast.LENGTH_SHORT).show()

                    ed_book.setText("")
                    ed_price.setText("")
                }catch (e: Exception){
                    Toast.makeText(this,"新增失敗:$e", Toast.LENGTH_LONG).show()
                }
        }

        btn_update.setOnClickListener {
            if (ed_book.length()<1 || ed_price.length()<1)
                Toast.makeText(this,"欄位請勿留空", Toast.LENGTH_SHORT).show()
            else
                try{
                    dbrw.execSQL("UPDATE myTable SET price = ${ed_price.text} WHERE book LIKE '${ed_book.text}'")
                    Toast.makeText(this,"更新書名${ed_book.text}    價格${ed_price.text}", Toast.LENGTH_SHORT).show()

                    ed_book.setText("")
                    ed_price.setText("")
                }catch (e: Exception){
                    Toast.makeText(this,"更新失敗:$e", Toast.LENGTH_LONG).show()
                }
        }

        btn_delete.setOnClickListener {
            if (ed_book.length()<1)
                Toast.makeText(this,"書名請勿留空", Toast.LENGTH_SHORT).show()
            else
                try{
                    dbrw.execSQL("DELETE FROM myTable WHERE book LIKE '${ed_book.text}'")
                    Toast.makeText(this,"刪除書名${ed_book.text}", Toast.LENGTH_SHORT).show()

                    ed_book.setText("")
                    ed_price.setText("")
                }catch (e: Exception){
                    Toast.makeText(this,"刪除失敗:$e", Toast.LENGTH_LONG).show()
                }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        dbrw.close()
    }
}