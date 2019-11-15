package id.boytegar.moocow.adapter

import android.content.Context
import android.graphics.Paint.STRIKE_THRU_TEXT_FLAG
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.paging.PagedListAdapter
import androidx.recyclerview.widget.RecyclerView
import id.boytegar.moocow.db.entity.MenuItem
import id.boytegar.moocow.helper.callback.MenuCallback
import id.boytegar.moocow.helper.HelperFun
import kotlinx.android.synthetic.main.list_menu_settings.view.*


class MenuAdapter(
    val context: Context,
    val layout: Int
) : PagedListAdapter<MenuItem, MenuAdapter.PersonViewHolder>(MenuCallback()){

    var list = ArrayList<MenuItem>()
    var onItemDelete: ((MenuItem) -> Unit)? = null
    var onItemEdit:  ((MenuItem) -> Unit)? = null

    //  var view
    override fun onBindViewHolder(holderPerson: PersonViewHolder, position: Int) {
        var person = getItem(position)!!

        holderPerson.bind(person)

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PersonViewHolder {
        return PersonViewHolder(LayoutInflater.from(context).inflate(layout, parent, false))
    }

    inner class PersonViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val holder = view
        fun bind(profile: MenuItem) {
            val helper = HelperFun
            holder.txt_name.text = profile.name

            if(profile.discount == 1){
                holder.txt_price.text = "Rp. " + helper.rupiahformat(profile.price_discount)
                holder.txt_price_discount.text = "Rp. " + helper.rupiahformat(profile.price)
                holder.txt_price_discount.paintFlags = holder.txt_price_discount.paintFlags or STRIKE_THRU_TEXT_FLAG
                holder.ic_discount.visibility = View.VISIBLE
            }else{
                holder.txt_price.text = "Rp. " + helper.rupiahformat(profile.price)
                holder.txt_price_discount.visibility = View.GONE
                holder.ic_discount.visibility = View.GONE
            }

            itemView.btn_edit.setOnClickListener {
                onItemEdit?.invoke(profile)
            }
            itemView.btn_delete.setOnClickListener {
                onItemDelete?.invoke(profile)
            }
        }

    }
}