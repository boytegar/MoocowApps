package id.boytegar.moocow

import android.bluetooth.BluetoothAdapter
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_add_printer.*
import android.bluetooth.BluetoothDevice
import android.content.BroadcastReceiver
import android.content.Context
import android.widget.ArrayAdapter
import android.content.IntentFilter
import android.util.Log
import java.util.*
import kotlin.collections.ArrayList
import java.nio.file.Files.size
import android.widget.Toast
import androidx.core.app.ComponentActivity.ExtraData
import androidx.core.content.ContextCompat.getSystemService
import android.icu.lang.UCharacter.GraphemeClusterBreak.T
import android.R.attr.name
import androidx.core.app.ComponentActivity.ExtraData
import androidx.core.content.ContextCompat.getSystemService
import android.icu.lang.UCharacter.GraphemeClusterBreak.T
import android.R.attr.name




class AddPrinterActivity : AppCompatActivity() {

    val EXTRA_DEVICE_ADDRESS = "device_address"
    private val applicationUUID = UUID
        .fromString("00001101-0000-1000-8000-00805F9B34FB")
    //   lateinit var newDeviceAdapter: ArrayAdapter<String>
    var list_data = ArrayList<String>()
    var bAdapter = BluetoothAdapter.getDefaultAdapter()
    val filter = IntentFilter(BluetoothDevice.ACTION_FOUND)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_printer)
        switch_bt.setOnCheckedChangeListener { compoundButton, b ->
            if (b) {
                startActivityForResult(Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE), 1)
                registerReceiver(mReceiver!!, filter)
                bAdapter.startDiscovery()
                //   getBluetoothPairedDevices(list_data)
            } else {
                bAdapter.disable()
                unregisterReceiver(mReceiver)
                bAdapter.cancelDiscovery()
                list_bluetooth.adapter = null
                txt_bt.text = "Bluetooth Status : Not Active"
            }
        }

        list_bluetooth.setOnItemClickListener { adapterView, view, i, l ->
            Toast.makeText(this,i.toString(),Toast.LENGTH_SHORT).show()
        }

    }


    override fun onResume() {
        super.onResume()
        if (bAdapter.isEnabled) {
            switch_bt.isChecked = true
            registerReceiver(mReceiver!!, filter)
            //   getBluetoothPairedDevices(list_data)
            txt_bt.text = "Bluetooth Status : Active"
        }else{
          //  unregisterReceiver(mReceiver)
            txt_bt.text = "Bluetooth Status : Not Active"
        }
    }

    private fun getBluetoothPairedDevices(deviceList: ArrayList<String>) {
        val bluetoothAdapter = BluetoothAdapter.getDefaultAdapter()
        if (bluetoothAdapter == null) {
            Toast.makeText(
                applicationContext,
                "This device not support bluetooth",
                Toast.LENGTH_LONG
            ).show()
        } else {
            if (!bluetoothAdapter.isEnabled) {
                val enableAdapter = Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE)
                startActivityForResult(enableAdapter, 0)
            }
            val all_devices = bluetoothAdapter.bondedDevices
            if (all_devices.size > 0) {
                for (currentDevice in all_devices) {
                    Log.e("DEVICE LIST ", "${currentDevice!!.name} ++ ${currentDevice!!.address}")
                    deviceList.add("Device Name: " + currentDevice.name + "\nDevice Address: " + currentDevice.address)
                    list_bluetooth.setAdapter(
                        ArrayAdapter(
                            application,
                            android.R.layout.simple_list_item_1, deviceList
                        )
                    )
                }
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        unregisterReceiver(mReceiver)
    }

    private val mReceiver = object : BroadcastReceiver() {
        override fun onReceive(context: Context, intent: Intent) {
            val action = intent.action

            Log.e("MASUK LIST ", "")
            if (BluetoothDevice.ACTION_FOUND == action) {
                val device =
                    intent.getParcelableExtra<BluetoothDevice>(BluetoothDevice.EXTRA_DEVICE)
                Log.e("DEVICE LIST ", "${device!!.name} ++ ${device!!.address}")
                list_data.add(device!!.name + "\n" + device.address)
                list_bluetooth.adapter = ArrayAdapter<String>(
                    context,
                    android.R.layout.simple_list_item_1, list_data
                )

            }
        }
    }

    private fun pairDevice(device: BluetoothDevice) {
        try {
            Log.d("pairDevice()", "Start Pairing...")
            val m = device.javaClass.getMethod("createBond", *null as Array<Class<*>>?)
            m.invoke(device, null as Array<Any>?)
            Log.d("pairDevice()", "Pairing finished.")
        } catch (e: Exception) {
            Log.e("pairDevice()", e.message)
        }

    }


    //For UnPairing
    private fun unpairDevice(device: BluetoothDevice) {
        try {
            Log.d("unpairDevice()", "Start Un-Pairing...")
            val m = device.javaClass.getMethod("removeBond", *null as Array<Class<*>>?)
            m.invoke(device, null as Array<Any>?)
            Log.d("unpairDevice()", "Un-Pairing finished.")
        } catch (e: Exception) {
            Log.e("unpairDevice()", e.message)
        }

    }

}
