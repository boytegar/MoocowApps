package id.boytegar.moocow.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.paging.PagedListAdapter
import androidx.recyclerview.widget.RecyclerView
import id.boytegar.moocow.R
import id.boytegar.moocow.db.entity.Transactions
import id.boytegar.moocow.helper.HelperFun
import id.boytegar.moocow.helper.callback.TransactionsCallback
import kotlinx.android.synthetic.main.list_transaction.view.*

class TransactionsAdapter(
    val context: Context,
    val layout: Int
) : PagedListAdapter<Transactions, TransactionsAdapter.PersonViewHolder>(TransactionsCallback()) {

    var list = ArrayList<Transactions>()
    var onItemClick: ((Transactions) -> Unit)? = null

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
        fun bind(profile: Transactions) {
            val helper = HelperFun
            holder.txt_name.text = profile.name_buyer
            holder.txt_price.text = "Rp. " + helper.rupiahformat(profile.total_price)
            when(profile.status){
                1->{
                    holder.txt_status.text = "Sudah Bayar"
                    holder.txt_status.setBackgroundResource(R.drawable.bg_done_pay)
                }
                else->{
                    holder.txt_status.text = "Belum Bayar"
                    holder.txt_status.setBackgroundResource(R.drawable.bg_undone_pay)
                }
            }

            itemView.setOnClickListener {
                onItemClick?.invoke(profile)
            }
        }

    }
}