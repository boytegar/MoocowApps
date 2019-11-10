package id.boytegar.moocow.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import id.boytegar.moocow.R
import id.boytegar.moocow.db.entity.Category
import kotlinx.android.synthetic.main.list_category_menu.view.*

class CategoryAdapter(
    val b: List<String>
) : RecyclerView.Adapter<CategoryAdapter.ViewHolder>() {

    var onItemClick : ((Int) -> Unit)? = null
    var index = 0

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val name = b[position]

        holder.bind(name, position)
    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        fun bind(name: String,  position: Int) {
            itemView.txt_name.text = name
            itemView.setOnClickListener {
                //onItemClick?.invoke(room)
                index = position
                notifyDataSetChanged()
                onItemClick!!.invoke(position)
            }


        }
    }
    override fun onCreateViewHolder(views: ViewGroup, position: Int): ViewHolder {
        val layoutInflater =
            LayoutInflater.from(views.context).inflate(R.layout.list_category_menu, views, false)
        return ViewHolder(layoutInflater)
    }

    override fun getItemCount(): Int {
        return b.size
    }
}