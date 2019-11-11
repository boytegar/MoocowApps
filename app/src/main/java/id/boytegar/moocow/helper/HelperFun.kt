package id.boytegar.moocow.helper

import java.text.DecimalFormat
import java.text.NumberFormat
import java.util.*

class HelperFun(){
    companion object{
        fun rupiahformat(price: Double): String{
            val rupiahFormat = NumberFormat.getInstance(Locale.GERMANY)
            val df = DecimalFormat("#.##")
            val price_ = df.format(price).toDouble()
            val formatter = rupiahFormat.format(price_).toString()
            return formatter
        }
    }
}