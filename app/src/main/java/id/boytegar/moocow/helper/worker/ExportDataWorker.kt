package id.boytegar.moocow.helper.worker

import android.content.Context
import android.os.Environment
import android.util.Log
import androidx.work.Data
import androidx.work.Worker
import androidx.work.WorkerParameters
import com.opencsv.CSVWriter
import id.boytegar.moocow.helper.HelperFun
import org.json.JSONArray
import org.json.JSONObject
import java.io.File
import java.io.FileWriter
import java.text.SimpleDateFormat
import java.util.*


class ExportDataWorker(ctx: Context, params: WorkerParameters) : Worker(ctx, params) {

    var context = ctx
    var data = ""
    val helperFun = HelperFun
    override fun doWork(): Result {
        try {
            val title = inputData.getString("title")
            val data = inputData.getString("data")
            val json = JSONArray(data)
            printData(title, json)
            val outputData = Data.Builder().putString("job", "Create Report Finished").build()
            return Result.success(outputData)
        } catch (e: Exception) {
            val outputData = Data.Builder().putString("job", "Create Report Failure").build()
            return Result.failure(outputData)
        }
    }

    private fun getCurrentDate(): String {
        val dateFormat = SimpleDateFormat("dd_MM_yyyy")
        val date = Date()
        return dateFormat.format(date)
    }

    fun printData(title: String?, json: JSONArray) {
        var total = 0.0
        var tax = 0.0
        val dir = File(
            context.getExternalFilesDir(Environment.DIRECTORY_DOCUMENTS),
            "/Moocow"
        )
        if (!dir.exists()) {
            dir.mkdirs()
        }
        val file = File(dir, getCurrentDate()+"_REPORT.csv")
        try {
            file.createNewFile()
            val csvWrite = CSVWriter(FileWriter(file))
            csvWrite.writeNext(arrayOf("","Tanggal Report: $title","","","","",""))
            csvWrite.writeNext(arrayOf(""))
            csvWrite.writeNext(arrayOf(""))
            val column =
                arrayOf("No", "Nama Pemesan", "Total", "Pajak","Total Semua", "Waktu", "Tanggal", "Kasir")
            csvWrite.writeNext(column)
            for (i in 0 until json.length()) {
                val jo = json[i] as JSONObject
                val tot = jo.getDouble("total_price")+jo.getDouble("tax")
                val arrStr = arrayOf<String>(
                    (i+1).toString(),
                    jo.getString("name_buyer"),
                    helperFun.rupiahformat(jo.getDouble("total_price")),
                    helperFun.rupiahformat(jo.getDouble("tax")),
                    helperFun.rupiahformat(tot),
                    jo.getString("time"),
                    jo.getString("date"),
                    jo.getString("name_cashier")
                )
                total += jo.getDouble("total_price")
                tax  += jo.getDouble("tax")
                csvWrite.writeNext(arrStr)
            }

            csvWrite.writeNext(arrayOf(""))
            csvWrite.writeNext(arrayOf("", "Total Transaksi : ${json.length()} Transaksi"))
            csvWrite.writeNext(arrayOf("", "Total Pemasukan : Rp. ${helperFun.rupiahformat(total)}"))
            csvWrite.writeNext(arrayOf("", "Total Pajak : Rp. ${helperFun.rupiahformat(total)}"))
            csvWrite.writeNext(arrayOf("", "Total Semua : Rp. ${helperFun.rupiahformat(total+tax)}"))
            csvWrite.close()

        } catch (sqlEx: Exception) {
            Log.e("MainActivity", sqlEx.message, sqlEx)
        }


    }
}
