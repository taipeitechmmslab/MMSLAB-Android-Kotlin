package com.example.lab21

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.example.lab21.databinding.ItemKeyboardBinding

// 鍵盤 Adapter，傳遞鍵盤的按鍵和點擊事件
class KeyboardAdapter(
    private val keys: List<String>,
    private val onKeyClick: (String) -> Unit
) : RecyclerView.Adapter<KeyboardAdapter.KeyboardViewHolder>() {

    class KeyboardViewHolder(
        private val binding: ItemKeyboardBinding
    ) : ViewHolder(binding.root) {
        // 綁定按鍵名稱和點擊事件
        fun bind(
            item: String,
            onKeyClick: (String) -> Unit
        ) {
            // 綁定按鍵名稱
            binding.key = item
            // 判斷是否為數字
            binding.isNumber = item.toIntOrNull() != null
            // 設定點擊事件
            binding.root.setOnClickListener { onKeyClick(item) }
            // 立即更新 UI
            binding.executePendingBindings()
        }

        companion object {
            // 建立 ViewHolder
            fun from(parent: ViewGroup) = KeyboardViewHolder(
                ItemKeyboardBinding.inflate(
                    LayoutInflater.from(parent.context),
                    parent, false
                )
            )
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): KeyboardViewHolder {
        return KeyboardViewHolder.from(parent)
    }

    override fun getItemCount(): Int {
        return keys.size
    }

    override fun onBindViewHolder(holder: KeyboardViewHolder, position: Int) {
        holder.bind(keys[position], onKeyClick)
    }
}