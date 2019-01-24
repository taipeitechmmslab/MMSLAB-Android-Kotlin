package bluenet.com.lab7

import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.ImageView
import android.widget.TextView
//Implement ViewHolder
class MyAdapter2 constructor(private val layout: Int, private val data: ArrayList<Item>)
    : BaseAdapter() {

    private class ViewHolder {
        lateinit var img_photo: ImageView
        lateinit var tv_name: TextView
    }

    override fun getCount() = data.size

    override fun getItem(position: Int) = data[position]

    override fun getItemId(position: Int) = 0L

    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
        val view: View
        val holder: ViewHolder

        if(convertView==null){
            view = View.inflate(parent?.context, layout, null)
            holder = ViewHolder()
            view.tag = holder

            holder.img_photo = view.findViewById(R.id.img_photo)
            holder.tv_name = view.findViewById(R.id.tv_name)
        }else{
            holder = convertView.tag as ViewHolder
            view = convertView
        }

        holder.img_photo.setImageResource(data[position].photo)
        holder.tv_name.text = data[position].name

        return view
    }
}