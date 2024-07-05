package com.example.lab23

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.lab23.databinding.ItemNoteBinding
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

class NoteAdapter(
    // 點擊監聽器
    private val onNoteClick: (Note) -> Unit,
    // 長按監聽器
    private val onNoteLongClick: (Note) -> Unit
) : RecyclerView.Adapter<NoteAdapter.NoteViewHolder>() {

    private var notes: List<Note> = emptyList()

    fun setNotes(newNotes: List<Note>) {
        this.notes = newNotes
        notifyDataSetChanged()
    }

    class NoteViewHolder(
        private val binding: ItemNoteBinding
    ) : RecyclerView.ViewHolder(binding.root) {
        fun bind(
            note: Note,
            onNoteClick: (Note) -> Unit,
            onNoteLongClick: (Note) -> Unit
        ) {
            // 設定 DataBinding 的參數
            binding.note = note
            // 設定格式化日期字串
            binding.formatTime = toFormattedDateString(note.timestamp)
            // 設定點擊監聽器
            binding.root.setOnClickListener { onNoteClick(note) }
            binding.root.setOnLongClickListener {
                onNoteLongClick(note)
                true
            }
            // 立即更新 UI
            binding.executePendingBindings()
        }

        // 將時間戳記轉換為格式化日期字串
        private fun toFormattedDateString(time: Long): String {
            // 設定日期格式
            val dateFormat = SimpleDateFormat(
                "yyyy/MM/dd HH:mm", Locale.getDefault()
            )
            // 將時間戳記轉換為日期字串
            return dateFormat.format(Date(time))
        }

        companion object {
            fun from(parent: ViewGroup) = NoteViewHolder(
                ItemNoteBinding.inflate(
                    LayoutInflater.from(parent.context),
                    parent, false
                )
            )
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NoteViewHolder {
        return NoteViewHolder.from(parent)
    }

    override fun getItemCount(): Int = notes.size

    override fun onBindViewHolder(holder: NoteViewHolder, position: Int) {
        holder.bind(notes[position], onNoteClick, onNoteLongClick)
    }
}