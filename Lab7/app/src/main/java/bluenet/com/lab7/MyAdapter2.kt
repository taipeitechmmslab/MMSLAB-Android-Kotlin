package bluenet.com.lab7

import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.ImageView
import android.widget.TextView
//Implement ViewHolder
class MyAdapter2 constructor(private val layout: Int, private val data: ArrayList<Item>)
    : BaseAdapter() {
    //ViewHolder類別，用來緩存畫面中的元件
    private class ViewHolder(v: View) {
        val img_photo: ImageView = v.findViewById(R.id.img_photo)
        val tv_name: TextView = v.findViewById(R.id.tv_name)
    }
    //回傳項目筆數
    override fun getCount() = data.size
    //回傳某筆項目
    override fun getItem(position: Int) = data[position]
    //回傳某筆項目Id
    override fun getItemId(position: Int) = 0L
    //取得畫面
    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        val view: View  //由於convertView無法賦值，必須額外建立一個View物件
        val holder: ViewHolder  //宣告ViewHolder

        if(convertView==null){
            //建立畫面
            view = View.inflate(parent.context, layout, null)
            //建立ViewHolder
            holder = ViewHolder(view)
            //將ViewHolder作為View的Tag
            view.tag = holder
        }else{
            //從Tag取得ViewHolder
            holder = convertView.tag as ViewHolder
            //取得畫面
            view = convertView
        }
        //根據position顯示圖片與名稱
        holder.img_photo.setImageResource(data[position].photo)
        holder.tv_name.text = data[position].name

        return view
    }
}