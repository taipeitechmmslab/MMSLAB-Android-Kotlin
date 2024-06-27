package com.example.lab15

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

// 自訂建構子並繼承 SQLiteOpenHelper 類別
class MyDBHelper (
    context: Context,
    name: String = DB_NAME,
    factory: SQLiteDatabase.CursorFactory? = null,
    version: Int = VERSION
) : SQLiteOpenHelper(context, name, factory, version) {
    companion object {
        private const val DB_NAME = "myDatabase" // 資料庫名稱
        private const val VERSION = 1 // 資料庫版本
    }

    override fun onCreate(db: SQLiteDatabase) {
        // 建立 myTable 資料表，表內有 book 字串欄位和 price 整數欄位
        db.execSQL("CREATE TABLE myTable(book text PRIMARY KEY, price integer NOT NULL)")
    }

    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        // 升級資料庫版本時，刪除舊資料表，並重新執行 onCreate()，建立新資料表
        db.execSQL("DROP TABLE IF EXISTS myTable")
        onCreate(db)
    }
}