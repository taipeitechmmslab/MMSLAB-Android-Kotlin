package com.example.lab23

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update

@Dao
interface NoteDao {
    // 取得所有記事本
    @Query("SELECT * FROM notes")
    suspend fun getAllNotes(): List<Note>
    // 新增記事本
    @Insert
    suspend fun insertNote(note: Note)
    // 刪除記事本
    @Delete
    suspend fun deleteNote(note: Note)
    // 更新記事本
    @Update
    suspend fun updateNote(note: Note)
}