package com.example.lab7

import android.content.Context
import android.view.LayoutInflater
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

    // 優化：使用 ViewHolder 快取元件，避免滑動時重複呼叫 findViewById()
    private class ViewHolder(view: View) {
        val imgPhoto: ImageView = view.findViewById(R.id.imgPhoto)
        val tvMsg: TextView = view.findViewById(R.id.tvMsg)
    }

    override fun getView(
        position: Int,
        convertView: View?,
        parent: ViewGroup
    ): View {
        val view: View
        val holder: ViewHolder

        // 優化：重複利用已移出畫面的 convertView
        if (convertView == null) {
            view = LayoutInflater.from(parent.context).inflate(layout, parent, false)
            holder = ViewHolder(view)
            view.tag = holder
        } else {
            view = convertView
            holder = view.tag as ViewHolder
        }

        val item = getItem(position) ?: return view
        holder.imgPhoto.setImageResource(item.photo)
        holder.tvMsg.text = if (layout == R.layout.adapter_vertical) {
            item.name
        } else {
            "${item.name}: ${item.price}元"
        }

        return view
    }
}
