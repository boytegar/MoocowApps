package id.boytegar.moocow.db.entity

import androidx.annotation.NonNull
import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverters
import id.boytegar.moocow.helper.DataConverter

@Entity(tableName = "transactions")
class Transactions() {
    @NonNull
    @PrimaryKey(autoGenerate = true)
    var id: Int? = null
    var name_cashier: String = ""
    var name_buyer: String = ""
    var time = ""
    var date = ""
    var total_price = 0.0
    var total_cash = 0.0
    var total_change = 0.0
    var status = ""
    @TypeConverters(DataConverter::class)
    var menu: List<Cart> = listOf()

    constructor(
        name_cashier: String,
        name_buyer: String,
        time: String,
        date: String,
        total_price: Double,
        total_cash: Double,
        total_change: Double,
        status: String,
        menu: List<Cart>
        ) : this() {
        this.name_cashier = name_cashier
        this.name_buyer = name_buyer
        this.time = time
        this.date = date
        this.total_price = total_price
        this.total_cash = total_cash
        this.total_change = total_change
        this.status = status
        this.menu = menu
    }
}