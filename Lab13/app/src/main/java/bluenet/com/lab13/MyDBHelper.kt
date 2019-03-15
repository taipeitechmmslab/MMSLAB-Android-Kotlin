package bluenet.com.lab13

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

class MySQLiteOpenHelper (context: Context, name: String = database, factory: SQLiteDatabase.CursorFactory? = null,
                          version: Int = v): SQLiteOpenHelper(context, name, factory, version) {
    companion object {
        private const val database = "mdatabase.db" //資料庫名稱
        private const val v = 1 //資料庫版本
    }

    override fun onCreate(db: SQLiteDatabase) {
        //建立資料表『myTable』，包含一個book字串欄位和一個price整數欄位
        db.execSQL("CREATE TABLE myTable(book text PRIMARY KEY, price integer NOT NULL)")
    }

    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        //刪除資料表
        db.execSQL("DROP TABLE IF EXISTS myTable")
        //重建資料庫
        onCreate(db)
    }
}