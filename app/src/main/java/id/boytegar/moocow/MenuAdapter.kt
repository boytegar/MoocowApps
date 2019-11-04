package id.boytegar.moocow

import android.content.Context
import android.graphics.Paint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.paging.PagedList
import androidx.paging.PagedListAdapter
import androidx.recyclerview.widget.RecyclerView
import id.boytegar.moocow.db.entity.MenuItem
import id.boytegar.moocow.helper.MenuCallback
import kotlinx.android.synthetic.main.list_menu_settings.view.*
import android.graphics.Paint.STRIKE_THRU_TEXT_FLAG
import java.text.DecimalFormat
import java.text.NumberFormat
import java.util.*
import kotlin.collections.ArrayList


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
            val rupiahFormat = NumberFormat.getInstance(Locale.GERMANY)
            val df = DecimalFormat("#.##")

            holder.txt_name.text = profile.name
            val price = df.format(profile.price)
            val price_discount = df.format(profile.price_discount)

                if(profile.discount == 1){
                holder.txt_price.text = "Rp. "+rupiahFormat.format(price_discount.toDouble()).toString()
                holder.txt_price_discount.text = "Rp. "+rupiahFormat.format(price.toDouble()).toString()
                holder.txt_price_discount.paintFlags = holder.txt_price_discount.paintFlags or STRIKE_THRU_TEXT_FLAG
                holder.ic_discount.visibility = View.VISIBLE
            }else{
                holder.txt_price.text = "Rp. "+rupiahFormat.format(price.toDouble()).toString()
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