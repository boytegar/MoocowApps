package id.boytegar.moocow

import android.bluetooth.BluetoothAdapter
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_add_printer.*
import android.bluetooth.BluetoothDevice
import android.bluetooth.BluetoothSocket
import android.content.BroadcastReceiver
import android.content.Context
import android.widget.ArrayAdapter
import android.content.IntentFilter
import android.util.Log
import java.util.*
import kotlin.collections.ArrayList
import java.nio.file.Files.size
import android.widget.Toast
import androidx.lifecycle.ViewModelProviders
import id.boytegar.moocow.helper.SharedData
import id.boytegar.moocow.viewmodel.MenuItemViewModel
import org.jetbrains.anko.toast


class AddPrinterActivity : AppCompatActivity() {

    val EXTRA_DEVICE_ADDRESS = "device_address"
    private val applicationUUID = UUID
        .fromString("00001101-0000-1000-8000-00805F9B34FB")
   // static private BluetoothSocket mbtSocket = null
    lateinit var mbtSocket : BluetoothSocket
    //   lateinit var newDeviceAdapter: ArrayAdapter<String>
    var list_data = ArrayList<String>()
    var list_data_paired = ArrayList<String>()
    var list_blue= ArrayList<BluetoothDevice>()
    var list_blue_paired= ArrayList<BluetoothDevice>()
    var bAdapter = BluetoothAdapter.getDefaultAdapter()
    val filter = IntentFilter(BluetoothDevice.ACTION_FOUND)
    var name = ""
    lateinit var menuItemViewModel: MenuItemViewModel
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_printer)
        menuItemViewModel = ViewModelProviders.of(this).get(MenuItemViewModel::class.java)
        getSupportActionBar()!!.setDisplayHomeAsUpEnabled(true)
        getSupportActionBar()!!.setDisplayShowHomeEnabled(true)
        supportActionBar!!.setHomeAsUpIndicator(R.drawable.ic_navigate_back)
        switch_bt.setOnCheckedChangeListener { compoundButton, b ->
            if (b) {
                startActivityForResult(Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE), 1)
                registerReceiver(mReceiver!!, filter)
                bAdapter.startDiscovery()
                showlistPaired()
                //   getBluetoothPairedDevices(list_data)
            } else {
                bAdapter.disable()
                unregisterReceiver(mReceiver)
                bAdapter.cancelDiscovery()
                list_bluetooth.adapter = null
                txt_bt.text = "Bluetooth Status : Not Active"
            }
        }


    }


    fun showlistPaired(){
        name = SharedData.getKeyString(this, "bt_name")!!
        val   mBluetoothAdapter = BluetoothAdapter.getDefaultAdapter()
        val pairedDevices = mBluetoothAdapter.bondedDevices
        if (pairedDevices.size > 0) {
            for (device in pairedDevices) {
                if(device.name == name){
                    list_data_paired.add(device.name+" --- Selected")
                }else{
                    list_data_paired.add(device.name)
                }
                list_blue_paired.add(device)
                val adapter = ArrayAdapter<String>(
                    this,
                    android.R.layout.simple_list_item_1, list_data_paired
                )
                list_bluetooth_paired.adapter = adapter
                list_bluetooth_paired.setOnItemClickListener { adapterView, view, i, l ->
                    val name = list_blue_paired[i].name
                    SharedData.setKeyString(this,"bt_name",name)
                    showlistPaired()
                }
            }
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



    override fun onDestroy() {
        super.onDestroy()
        unregisterReceiver(mReceiver)
    }

    private val mReceiver = object : BroadcastReceiver() {
        override fun onReceive(context: Context, intent: Intent) {
            val action = intent.action
            if (BluetoothDevice.ACTION_FOUND == action) {
                val device = intent.getParcelableExtra<BluetoothDevice>(BluetoothDevice.EXTRA_DEVICE)
                list_data.add(device!!.name + "\n" + device.address)
                list_blue.add(device)
                val adapter = ArrayAdapter<String>(
                    context,
                    android.R.layout.simple_list_item_1, list_data
                )
                list_bluetooth.adapter = adapter
                list_bluetooth.setOnItemClickListener { adapterView, view, i, l ->
                    val bond = createBond(list_blue[i])
//                    val gotuuid = list_blue[i]
//                        .fetchUuidsWithSdp()
//                    val uuid =  list_blue[i].getUuids()[0]
//                        .getUuid()
//                    mbtSocket =  list_blue[i].createRfcommSocketToServiceRecord(uuid)
                    if(bond){
                        toast("PAIRED")
                        adapter.notifyDataSetChanged()
                    }else{
                        toast("NOT PAIRED")
                    }
                }
            }
        }
    }

    @Throws(Exception::class)
    fun createBond(btDevice: BluetoothDevice): Boolean {
        val class1 = Class.forName("android.bluetooth.BluetoothDevice")
        val createBondMethod = class1.getMethod("createBond")
        val returnValue = createBondMethod.invoke(btDevice) as Boolean
        return returnValue
    }

}
