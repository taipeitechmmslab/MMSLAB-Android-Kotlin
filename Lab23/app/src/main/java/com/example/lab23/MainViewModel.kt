package com.example.lab23

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class MainViewModel(
    application: Application
) : AndroidViewModel(application) {
    // 取得 NoteDao，用於操作資料庫
    private val noteDao: NoteDao =
        NoteDatabase.getDatabase(application).noteDao()

    // 使用 LiveData 保存所有記事本資料
    private val _allNotes = MutableLiveData<List<Note>>()
    val allNotes: LiveData<List<Note>> get() = _allNotes

    // 取得所有記事本
    fun queryAllNotes() {
        viewModelScope.launch(Dispatchers.IO) {
            // 將取得的資料設定給 LiveData
            _allNotes.postValue(noteDao.getAllNotes())
        }
    }

    // 新增記事本
    fun insert(note: Note) {
        viewModelScope.launch(Dispatchers.IO) {
            noteDao.insertNote(note)
            queryAllNotes()
        }
    }
    // 刪除記事本
    fun delete(note: Note) {
        viewModelScope.launch(Dispatchers.IO) {
            noteDao.deleteNote(note)
            queryAllNotes()
        }
    }
    // 更新記事本
    fun update(note: Note) {
        viewModelScope.launch(Dispatchers.IO) {
            noteDao.updateNote(note)
            queryAllNotes()
        }
    }

    init {
        // 初始化 LiveData
        _allNotes.value = emptyList()
    }
}