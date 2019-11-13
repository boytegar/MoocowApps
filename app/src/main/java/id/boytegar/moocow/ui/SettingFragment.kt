package id.boytegar.moocow.ui


import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.work.Data
import androidx.work.OneTimeWorkRequest
import androidx.work.WorkManager

import id.boytegar.moocow.R
import kotlinx.android.synthetic.main.fragment_setting.view.*
import id.boytegar.moocow.AddPrinterActivity
import id.boytegar.moocow.MenuActivity
import id.boytegar.moocow.helper.worker.PrintWorker
import android.bluetooth.BluetoothAdapter
import android.widget.Toast
import id.boytegar.moocow.helper.SharedData

/**
 * A simple [Fragment] subclass.
 */
class SettingFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val v =  inflater.inflate(R.layout.fragment_setting, container, false)
        v.btn_device.setOnClickListener {
            checkBluetooth()
        }
        v.btn_menu.setOnClickListener {
            val intent = Intent(activity!!, MenuActivity::class.java)
            startActivity(intent)
        }
        return v
    }

    fun checkBluetooth(){
        val mBluetoothAdapter = BluetoothAdapter.getDefaultAdapter()
        if (mBluetoothAdapter == null) {
            // Device does not support Bluetooth
            Toast.makeText(activity!!, "DEVICE NOT SUPPORT BLUETOOTH", Toast.LENGTH_SHORT).show()
        } else if (!mBluetoothAdapter.isEnabled) {
            startActivityForResult(Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE), 1)
        } else {
            val intent = Intent(activity!!, AddPrinterActivity::class.java)
            startActivity(intent)
        }
    }
    fun checkBluetoothPrint(){
        val mBluetoothAdapter = BluetoothAdapter.getDefaultAdapter()
        if (mBluetoothAdapter == null) {
            // Device does not support Bluetooth
            Toast.makeText(activity!!, "DEVICE NOT SUPPORT BLUETOOTH", Toast.LENGTH_SHORT).show()
        } else if (!mBluetoothAdapter.isEnabled) {
            startActivityForResult(Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE), 1)
        } else {
            val intent = Intent(activity!!, AddPrinterActivity::class.java)
            startActivity(intent)
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        view.btn_report.setOnClickListener {
            val compressionWork = OneTimeWorkRequest.Builder(PrintWorker::class.java)
            val data = Data.Builder()
//Add parameter in Data class. just like bundle. You can also add Boolean and Number in parameter.
            val isi = SharedData.getKeyString(activity!!, "bt_name")
            data.putString("bt_name", isi)
//Set Input Data
            compressionWork.setInputData(data.build())
//enque worker
            WorkManager.getInstance().enqueue(compressionWork.build())
        }
//        val compressionWork = OneTimeWorkRequest.Builder(ExportDataWorker::class.java)
//        val data = Data.Builder()
////Add parameter in Data class. just like bundle. You can also add Boolean and Number in parameter.
//        data.putString("file_path", "put_file_path_here")
////Set Input Data
//        compressionWork.setInputData(data.build())
////enque worker
//        WorkManager.getInstance().enqueue(compressionWork.build())
    }


}
