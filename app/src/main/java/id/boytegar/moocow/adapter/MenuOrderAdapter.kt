package id.boytegar.moocow.adapter

import android.content.Context
import android.graphics.Paint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.paging.PagedListAdapter
import androidx.recyclerview.widget.RecyclerView
import id.boytegar.moocow.db.entity.Cart
import id.boytegar.moocow.db.entity.MenuItem
import id.boytegar.moocow.helper.HelperFun
import id.boytegar.moocow.helper.MenuCallback
import id.boytegar.moocow.viewmodel.MenuItemViewModel
import kotlinx.android.synthetic.main.list_menu_order.view.*
import kotlinx.android.synthetic.main.list_menu_settings.view.ic_discount
import kotlinx.android.synthetic.main.list_menu_settings.view.txt_name
import kotlinx.android.synthetic.main.list_menu_settings.view.txt_price
import kotlinx.android.synthetic.main.list_menu_settings.view.txt_price_discount
import org.jetbrains.anko.doAsync
import org.jetbrains.anko.uiThread
import kotlin.collections.ArrayList

class MenuOrderAdapter(
    val context: Context,
    val layout: Int,
    val menuItemViewModel: MenuItemViewModel
) : PagedListAdapter<MenuItem, MenuOrderAdapter.PersonViewHolder>(MenuCallback()){

    var list = ArrayList<MenuItem>()
    var onItemClick: ((MenuItem) -> Unit)? = null
    var onCartClick: ((Cart) -> Unit)? = null


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
        lateinit var cart: Cart
        fun bind(profile: MenuItem) {

            holder.txt_name.text = profile.name
            val helper = HelperFun
            if(profile.discount == 1){
                holder.txt_price.text = "Rp. " + helper.rupiahformat(profile.price_discount)
                holder.txt_price_discount.text = "Rp. " + helper.rupiahformat(profile.price)
                holder.txt_price_discount.paintFlags = holder.txt_price_discount.paintFlags or Paint.STRIKE_THRU_TEXT_FLAG
                holder.ic_discount.visibility = View.VISIBLE
            }else{
                holder.txt_price.text = "Rp. " + helper.rupiahformat(profile.price)
                holder.txt_price_discount.visibility = View.GONE
                holder.ic_discount.visibility = View.GONE
            }
            doAsync {
                val b = menuItemViewModel.checkItemCart(profile.id!!)
               // cart = menuItemViewModel.getItemCartById(profile.id!!)
                uiThread {
                    if(b){
                        holder.btn_add.visibility = View.GONE
                        holder.btn_edit.visibility = View.VISIBLE
                        doAsync {
                            cart = menuItemViewModel.getItemCartById(profile.id!!)
                            uiThread {
                                holder.txt_pcs.text = "Pcs: ${cart.quantity}"
                            }
                        }

                    }else{
                        holder.btn_add.visibility = View.VISIBLE
                        holder.btn_edit.visibility = View.GONE
                    }
                }
            }

            holder.btn_edit.setOnClickListener {
                onCartClick?.invoke(cart)
            }
            holder.btn_add.setOnClickListener {
                onItemClick?.invoke(profile)
            }

        }

    }
}