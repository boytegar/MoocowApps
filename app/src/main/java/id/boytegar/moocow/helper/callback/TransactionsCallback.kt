package id.boytegar.moocow.helper.callback

import androidx.recyclerview.widget.DiffUtil
import id.boytegar.moocow.db.entity.Transactions

class TransactionsCallback : DiffUtil.ItemCallback<Transactions>() {

    override fun areItemsTheSame(oldItem: Transactions, newItem: Transactions): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: Transactions, newItem: Transactions): Boolean {
        return oldItem.equals(newItem)
    }
}