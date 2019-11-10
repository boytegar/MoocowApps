
package id.boytegar.moocow.db.entity
import androidx.annotation.NonNull
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "cart")
class Cart() {
    @NonNull
    @PrimaryKey(autoGenerate = true)
    var id: Int? = null
    var id_menu = 0
    var name = ""
    var quantity = 0
    var price = 0.0

    constructor(id_menu: Int, name: String, quantity: Int, price: Double): this(){
        this.id_menu = id_menu
        this.name = name
        this.quantity = quantity
        this.price = price
    }
}

