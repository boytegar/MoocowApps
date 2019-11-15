package id.boytegar.moocow.helper.callback

import androidx.recyclerview.widget.DiffUtil
import id.boytegar.moocow.db.entity.MenuItem

class MenuCallback : DiffUtil.ItemCallback<MenuItem>() {

    override fun areItemsTheSame(oldItem: MenuItem, newItem: MenuItem): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: MenuItem, newItem: MenuItem): Boolean {
        return oldItem.equals(newItem)
    }
}