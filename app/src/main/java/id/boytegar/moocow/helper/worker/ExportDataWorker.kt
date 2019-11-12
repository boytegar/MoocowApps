package id.boytegar.moocow.helper.worker

import android.content.Context
import androidx.work.Data
import androidx.work.Worker
import androidx.work.WorkerParameters

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

    fun shiw(){}
}
