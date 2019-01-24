package bluenet.com.lab8

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView

class MyAdapter(private val contacts:ArrayList<Contact>) : RecyclerView.Adapter<MyAdapter.ViewHolder>(){
    class ViewHolder(v: View):RecyclerView.ViewHolder(v){
        val tv_name = v.findViewById<TextView>(R.id.tv_name)
        val tv_phone = v.findViewById<TextView>(R.id.tv_phone)
        val img_delete = v.findViewById<ImageView>(R.id.img_delete)
    }

    override fun onCreateViewHolder(viewGroup: ViewGroup, position: Int): ViewHolder {
        val v = LayoutInflater.from(viewGroup.context).inflate(R.layout.adapter_row, viewGroup,false)
        return ViewHolder(v)
    }

    override fun getItemCount() = contacts.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.tv_name.text = contacts[position].name
        holder.tv_phone.text = contacts[position].phone

        holder.img_delete.setOnClickListener {
            contacts.removeAt(position)
            notifyDataSetChanged()
        }
    }
}