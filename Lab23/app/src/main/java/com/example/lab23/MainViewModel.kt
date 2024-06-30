package com.example.lab23

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class MainViewModel(
    application: Application
) : AndroidViewModel(application) {

    private val noteDao = NoteDatabase.getDatabase(application).noteDao()
    val allNotes: LiveData<List<Note>> = noteDao.getAllNotes()

    fun insert(note: Note) = viewModelScope.launch(Dispatchers.IO) {
        noteDao.insertNote(note)
    }

    fun update(note: Note) = viewModelScope.launch(Dispatchers.IO) {
        noteDao.updateNote(note)
    }

    fun delete(note: Note) = viewModelScope.launch(Dispatchers.IO) {
        noteDao.deleteNote(note)
    }
}