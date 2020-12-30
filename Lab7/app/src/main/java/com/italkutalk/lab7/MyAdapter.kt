package com.italkutalk.lab7

import android.content.Context
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.ImageView
import android.widget.TextView

class MyAdapter(context: Context, data: ArrayList<Item>, private val layout: Int) : ArrayAdapter<Item>(context, layout, data) {
    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        //依據傳入的 Layout 建立畫面
        val view = View.inflate(parent.context, layout, null)
        //依據 position 取得對應的資料內容
        val item = getItem(position) ?: return view
        //將圖片指派給 ImageView 呈現
        val img_photo = view.findViewById<ImageView>(R.id.img_photo)
        img_photo.setImageResource(item.photo)
        //將訊息指派給 TextView 呈現，若是垂直排列則為名稱，否則為名稱及價格
        val tv_msg = view.findViewById<TextView>(R.id.tv_msg)
        tv_msg.text = if (layout == R.layout.adapter_vertical)
            item.name
        else
            "${item.name}: ${item.price}元"
        //回傳此項目的畫面
        return view
    }
}