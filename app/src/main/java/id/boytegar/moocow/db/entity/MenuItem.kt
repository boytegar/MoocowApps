package id.boytegar.moocow.db.entity

import androidx.annotation.NonNull
import androidx.room.Entity
import androidx.room.Ignore
import androidx.room.PrimaryKey

@Entity(tableName = "menu_item")
class MenuItem() {
    @NonNull
    @PrimaryKey(autoGenerate = true)
    var id: Int? = null
    var cat_id: Int = 0
    var name = ""
    var descipt = ""
    var price = 0.0
    var price_discount = 0.0
    var discount  = 0
    var avail = 0
    constructor( name: String, desc : String, price: Double, price_discount: Double, discount: Int, avail: Int, cat_id: Int): this() {
        this.name = name
        this.descipt = desc
        this.price = price
        this.price_discount = price_discount
        this.discount = discount
        this.avail = avail
        this.cat_id = cat_id
    }

}