package id.boytegar.moocow

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.work.Data
import androidx.work.OneTimeWorkRequest
import androidx.work.WorkManager
import id.boytegar.moocow.helper.worker.PrintWorker
import androidx.core.app.ComponentActivity.ExtraData
import androidx.core.content.ContextCompat.getSystemService
import android.icu.lang.UCharacter.GraphemeClusterBreak.T



class CartActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_cart)
        val compressionWork = OneTimeWorkRequest.Builder(PrintWorker::class.java)
        val data = Data.Builder()
//Add parameter in Data class. just like bundle. You can also add Boolean and Number in parameter.
        data.putString("file_path", "put_file_path_here")
//Set Input Data
        compressionWork.setInputData(data.build())
//enque worker
        WorkManager.getInstance().enqueue(compressionWork.build())
//        val gotuuid = btDevices.getItem(position)
//            .fetchUuidsWithSdp()
//        val uuid = btDevices.getItem(position).getUuids()[0]
//            .getUuid()
//        mbtSocket = btDevices.getItem(position)
//            .createRfcommSocketToServiceRecord(uuid)
    }
}
