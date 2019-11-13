package id.boytegar.moocow.helper.worker

import android.content.Context
import android.os.Environment
import androidx.work.Data
import androidx.work.Worker
import androidx.work.WorkerParameters
import com.opencsv.bean.StatefulBeanToCsvBuilder
import com.opencsv.bean.StatefulBeanToCsv
import java.io.FileWriter
import android.widget.Toast
import com.opencsv.CSVWriter
import android.os.Environment.getExternalStorageDirectory
import java.io.File


class ExportDataWorker(ctx: Context, params: WorkerParameters) : Worker(ctx, params) {

    var context = ctx

    override fun doWork(): Result {
        val taskData = inputData
        val title = taskData.getString("title")
        val desc = taskData.getString("desc")
        //  btsocket = taskData.get
        val outputData = Data.Builder().putString("job", "Jobs Finished").build()

        return Result.success(outputData)
    }

//    fun shiw(){
//        val exportDir = File(context.getExternalFilesDir(null), "")
//        if (!exportDir.exists()) {
//            exportDir.mkdirs()
//        }
//
//        val file = File(exportDir, "export.xls" + ".csv")
//        try {
//            file.createNewFile()
//            val csvWrite = CSVWriter(FileWriter(file))
//            val curCSV = db.query("SELECT * FROM $TableName", null)
//            csvWrite.writeNext(curCSV.getColumnNames())
//            while (curCSV.moveToNext()) {
//                //Which column you want to exprort
//                val arrStr = arrayOfNulls<String>(curCSV.getColumnCount())
//                for (i in 0 until curCSV.getColumnCount() - 1)
//                    arrStr[i] = curCSV.getString(i)
//                csvWrite.writeNext(arrStr)
//            }
//            csvWrite.close()
//            curCSV.close()
//        } catch (sqlEx: Exception) {
//
//        }
//
//
//    }
}
