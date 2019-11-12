package id.boytegar.moocow.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import id.boytegar.moocow.R
import id.boytegar.moocow.db.entity.Cart
import id.boytegar.moocow.helper.HelperFun
import kotlinx.android.synthetic.main.list_cart.view.*

class CartAdapter(
    val b: List<Cart>
) : RecyclerView.Adapter<CartAdapter.ViewHolder>() {

    var onItemClick : ((Int) -> Unit)? = null
    var index = 0

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val name = b[position]

        holder.bind(name, position)
    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        fun bind(name: Cart,  position: Int) {
            val helper = HelperFun
            itemView.txt_price.text = "Rp. "+helper.rupiahformat(name.price)
            itemView.txt_name.text = name.name
            itemView.txt_price_total.text = "Rp. "+helper.rupiahformat(name.price*name.quantity)
            itemView.txt_pcs.text = "Items : "+name.quantity.toString()
            itemView.setOnClickListener {
                //onItemClick?.invoke(room)
              //  index = position
              //  notifyDataSetChanged()
               // onItemClick!!.invoke(position)
            }
          //  itemView.btn


        }
    }
    override fun onCreateViewHolder(views: ViewGroup, position: Int): ViewHolder {
        val layoutInflater =
            LayoutInflater.from(views.context).inflate(R.layout.list_cart, views, false)
        return ViewHolder(layoutInflater)
    }

    override fun getItemCount(): Int {
        return b.size
    }
}