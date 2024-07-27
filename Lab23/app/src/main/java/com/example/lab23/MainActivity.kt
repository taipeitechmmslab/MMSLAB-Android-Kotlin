package com.example.lab23

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.lab23.databinding.ActivityMainBinding
import com.example.lab23.databinding.DialogAddNoteBinding

class MainActivity : AppCompatActivity() {
    // 宣告 DataBinding 和 ViewModel
    private lateinit var binding: ActivityMainBinding
    private lateinit var viewModel: MainViewModel

    // 設定記事本 Adapter
    private lateinit var noteAdapter: NoteAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        doInitialize()
        setupNoteList()
        setListener()
    }

    // 執行初始化
    private fun doInitialize() {
        enableEdgeToEdge()
        // 設定 DataBinding
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        // 取得 ViewModel 實例
        viewModel = ViewModelProvider(this)[MainViewModel::class.java]
        // 設定 DataBinding 生命週期
        binding.lifecycleOwner = this
        // 設定 DataBinding 的參數
        binding.vm = viewModel
        // 取得所有記事本
        viewModel.queryAllNotes()
    }

    // 設定記事本列表
    private fun setupNoteList() {
        // 建立記事本 Adapter
        noteAdapter = NoteAdapter(
            onNoteClick = { note -> showNoteEditDialog(note) },
            onNoteLongClick = { note -> showDeleteDialog(note) }
        )
        // 設定 LayoutManager
        val layoutManager = LinearLayoutManager(this)
        // 設定 RecyclerView
        binding.rvNotes.layoutManager = layoutManager
        binding.rvNotes.adapter = noteAdapter
        // 觀察 LiveData
        viewModel.allNotes.observe(this) { notes ->
            // 更新 Adapter 的記事本列表
            noteAdapter.setNotes(notes)
        }
    }

    // 設定監聽器
    private fun setListener() {
        binding.fabAdd.setOnClickListener {
            showNoteEditDialog(null)
        }
    }

    // 顯示編輯記事本 Dialog
    private fun showNoteEditDialog(note: Note?) {
        // 載入自定義編輯記事本 Dialog 的 DataBinding
        val dialogBinding = DialogAddNoteBinding.inflate(layoutInflater)
        // 如果是編輯記事本，則顯示原本的標題和內容
        if (note != null) {
            dialogBinding.edTitle.setText(note.title)
            dialogBinding.edContent.setText(note.content)
        }
        // 建立記事本 Dialog
        val dialog = AlertDialog.Builder(this)
            // 設定 Dialog 的標題
            .setTitle(
                if (note == null) "新增記事本" else "編輯記事本"
            )
            // 設定 Dialog 的顯示內容
            .setView(dialogBinding.root)
            // 設定 Dialog 的按鈕
            .setPositiveButton("保存") { _, _ ->
                // 取得使用者輸入的標題和內容
                val title = dialogBinding.edTitle.text.toString()
                val content = dialogBinding.edContent.text.toString()
                // 建立新的記事本
                val newNote = Note(
                    id = note?.id ?: 0,
                    title = title,
                    content = content,
                    timestamp = System.currentTimeMillis()
                )
                // 判斷是否為編輯記事本
                if (note != null) {
                    viewModel.update(newNote)
                } else {
                    viewModel.insert(newNote)
                }
            }
            .setNegativeButton("取消", null)
            // 建立 Dialog
            .create()
        // 顯示 Dialog
        dialog.show()
    }

    // 顯示刪除記事本 Dialog
    private fun showDeleteDialog(note: Note) {
        // 建立刪除 Dialog
        val dialog = AlertDialog.Builder(this)
            // 設定 Dialog 的標題
            .setTitle("刪除記事本")
            // 設定 Dialog 的訊息
            .setMessage("確定要刪除這篇記事本嗎？")
            // 設定 Dialog 的按鈕
            .setPositiveButton("確定") { _, _ ->
                viewModel.delete(note)
            }
            .setNegativeButton("取消", null)
            // 建立 Dialog
            .create()
        // 顯示 Dialog
        dialog.show()
    }
}