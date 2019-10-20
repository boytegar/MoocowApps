package id.boytegar.moocow.db.entity

import androidx.annotation.NonNull
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "category")
class Catergory(name: String, desc : String) {
    @NonNull
    @PrimaryKey(autoGenerate = true)
    var id: Int? = null
    val name = name
    val desc = desc
}