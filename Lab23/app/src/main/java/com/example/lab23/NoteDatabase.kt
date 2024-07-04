package com.example.lab23

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Database
import androidx.room.Delete
import androidx.room.Entity
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.PrimaryKey
import androidx.room.Query
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.Update

@Entity(tableName = "notes")
data class Note(
    @PrimaryKey(autoGenerate = true)
    val id: Int,
    val title: String,
    val content: String,
    val timestamp: Long
)

@Dao
interface NoteDao {
    @Query("SELECT * FROM notes ORDER BY timestamp DESC")
    fun getAllNotes(): LiveData<List<Note>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertNote(note: Note)

    @Delete
    suspend fun deleteNote(note: Note)

    @Update
    suspend fun updateNote(note: Note)
}

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
                NoteDatabase::class.java, "note_database"
            ).build()
    }
}
