package id.boytegar.moocow.db.entity

import androidx.annotation.NonNull
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "menu_item")
class MenuItem(name: String, desc : String, price: Double, price_discount: Double, discount: Int) {
    @NonNull
    @PrimaryKey(autoGenerate = true)
    var id: Int? = null
    val name = name
    val desc = desc
    val price = price
    val price_discount = price_discount
    val discount  = discount
}