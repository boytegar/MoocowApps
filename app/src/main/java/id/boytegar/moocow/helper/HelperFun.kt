package id.boytegar.moocow.helper

import java.text.DecimalFormat
import java.text.NumberFormat
import java.util.*
import android.graphics.Bitmap
import com.google.gson.Gson
import id.boytegar.moocow.db.entity.Transactions
import kotlin.collections.ArrayList


class HelperFun(){
    companion object{
        fun rupiahformat(price: Double): String{
            val rupiahFormat = NumberFormat.getInstance(Locale.GERMANY)
            val df = DecimalFormat("#.##")
            val price_ = df.format(price).toDouble()
            val formatter = rupiahFormat.format(price_).toString()
            return formatter
        }
        fun serializeToJson(bmp: ArrayList<Transactions>): String {
            val gson = Gson()
            return gson.toJson(bmp)
        }

        // Deserialize to single object.

    }
}