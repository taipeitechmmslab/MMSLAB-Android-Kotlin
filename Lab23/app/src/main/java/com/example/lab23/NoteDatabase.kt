package com.example.lab23

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(
    // 定義資料表類別
    entities = [Note::class],
    // 定義資料庫版本
    version = 1
)
abstract class NoteDatabase : RoomDatabase() {
    // 定義抽象方法，用來取得 DAO 操作介面
    abstract fun noteDao(): NoteDao

    // 定義靜態方法，用來取得資料庫實例
    companion object {
        // 定義資料庫實例變數
        @Volatile
        private var instance: NoteDatabase? = null

        // 定義靜態方法，用來取得資料庫實例
        fun getDatabase(context: Context): NoteDatabase =
            // 使用雙重鎖定機制，確保資料庫實例只會被建立一次
            instance ?: synchronized(this) {
                // 若資料庫實例為null，則建立資料庫實例
                instance ?: buildDatabase(context).also { instance = it }
            }

        // 定義靜態方法，用來建立資料庫實例
        private fun buildDatabase(context: Context) =
            Room.databaseBuilder(
                context.applicationContext,
                NoteDatabase::class.java,
                "note_database"
            ).fallbackToDestructiveMigration().build()
    }
}
