package id.boytegar.moocow.helper

import com.google.gson.Gson
import id.boytegar.moocow.db.entity.Transactions
import java.text.DecimalFormat
import java.text.NumberFormat
import java.text.ParseException
import java.text.SimpleDateFormat
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
        fun serializeToJsonTransactions(bmp: List<Transactions>): String {
            val gson = Gson()
            return gson.toJson(bmp)
        }

        fun stringToDate(data: String): Date {
            val format = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'")
            val date: Date = format.parse(data)
            return date
        }
        fun dateToString(date: Date): String{
            val format = SimpleDateFormat("dd_MM_yyyy")
            return format.format(date)
        }

    }
}