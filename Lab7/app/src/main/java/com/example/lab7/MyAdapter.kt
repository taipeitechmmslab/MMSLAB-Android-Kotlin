package com.example.lab7

import android.content.Context
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.ImageView
import android.widget.TextView

class MyAdapter(
    context: Context,
    data: List<Item>,
    private val layout: Int
) : ArrayAdapter<Item>(context, layout, data) {

    override fun getView(
        position: Int,
        convertView: View?,
        parent: ViewGroup
    ): View {
        // 依據傳入的 Layout 建立畫面，若是已經存在則直接使用
        val view = convertView ?: View.inflate(parent.context, layout, null)
        // 依據 position 取得對應的資料內容
        val item = getItem(position) ?: return view
        // 將圖片指派給 ImageView 呈現
        val imgPhoto = view.findViewById<ImageView>(R.id.imgPhoto)
        imgPhoto.setImageResource(item.photo)
        // 將訊息指派給 TextView 呈現，若是垂直排列則為名稱，否則為名稱及價格
        val tvMsg = view.findViewById<TextView>(R.id.tvMsg)
        tvMsg.text = if (layout == R.layout.adapter_vertical) {
            item.name
        } else {
            "${item.name}: ${item.price}元"
        }
        // 回傳此項目的畫面
        return view
    }
}