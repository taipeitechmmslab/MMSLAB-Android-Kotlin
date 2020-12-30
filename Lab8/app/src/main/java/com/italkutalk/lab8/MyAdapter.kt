package com.italkutalk.lab8

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.recyclerview.widget.RecyclerView

class MyAdapter(private val data: ArrayList<Contact>) :
    RecyclerView.Adapter<MyAdapter.ViewHolder>() {
    //實作 RecyclerView.ViewHolder 來儲存 View
    class ViewHolder(v: View): RecyclerView.ViewHolder(v) {
        //連結畫面中的元件
        val tv_name = v.findViewById<TextView>(R.id.tv_name)
        val tv_phone = v.findViewById<TextView>(R.id.tv_phone)
        val img_delete = v.findViewById<ImageView>(R.id.img_delete)
    }
    //回傳資料數量
    override fun getItemCount() = data.size
    //建立 ViewHolder 與 Layout 並連結彼此
    override fun onCreateViewHolder(viewGroup: ViewGroup, position: Int):
            ViewHolder {
        val v = LayoutInflater.from(viewGroup.context)
            .inflate(R.layout.adapter_row, viewGroup, false)
        return ViewHolder(v)
    }
    //將資料指派給元件呈現
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.tv_name.text = data[position].name
        holder.tv_phone.text = data[position].phone
        //設定監聽器，使用 removeAt()刪除指定位置的資料
        holder.img_delete.setOnClickListener {
            data.removeAt(position)
            notifyDataSetChanged()
        }
    }
}