package com.example.lab8

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class MyAdapter(
    private val data: ArrayList<Contact>
) : RecyclerView.Adapter<MyAdapter.ViewHolder>() {
    // 實作 RecyclerView.ViewHolder 來儲存View
    class ViewHolder(v: View) : RecyclerView.ViewHolder(v) {
        // 儲存 View 元件
        private val tvName: TextView = v.findViewById(R.id.tvName)
        private val tvPhone: TextView = v.findViewById(R.id.tvPhone)
        private val imgDelete: ImageView = v.findViewById(R.id.imgDelete)

        // 連結資料與 View
        fun bind(item: Contact, clickListener: (Contact) -> Unit) {
            tvName.text = item.name
            tvPhone.text = item.phone
            // 設定監聽器
            imgDelete.setOnClickListener {
                // 呼叫 clickListener 回傳刪除的資料
                clickListener.invoke(item)
            }
        }
    }

    // 建立ViewHolder與Layout並連結彼此
    override fun onCreateViewHolder(viewGroup: ViewGroup, position: Int): ViewHolder {
        val v = LayoutInflater.from(viewGroup.context)
            .inflate(R.layout.adapter_row, viewGroup, false)
        return ViewHolder(v)
    }

    // 回傳資料數量
    override fun getItemCount() = data.size

    // 將資料指派給 ViewHolder 顯示
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(data[position]) { item ->
            // 使用 remove() 刪除指定的資料
            data.remove(item)
            notifyDataSetChanged()
        }
    }
}
