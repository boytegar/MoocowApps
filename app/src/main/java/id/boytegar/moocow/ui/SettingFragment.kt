package id.boytegar.moocow.ui


import android.bluetooth.BluetoothAdapter
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProviders
import androidx.work.*
import com.google.android.material.bottomsheet.BottomSheetDialog
import id.boytegar.moocow.AddPrinterActivity
import id.boytegar.moocow.MenuActivity
import id.boytegar.moocow.R
import id.boytegar.moocow.helper.HelperFun
import id.boytegar.moocow.helper.SharedData
import id.boytegar.moocow.helper.worker.ExportDataWorker
import id.boytegar.moocow.helper.worker.PrintWorker
import id.boytegar.moocow.viewmodel.TransactionsViewModel
import kotlinx.android.synthetic.main.dialog_report.view.*
import kotlinx.android.synthetic.main.fragment_setting.view.*
import org.jetbrains.anko.doAsync
import org.jetbrains.anko.uiThread
import java.text.SimpleDateFormat
import java.util.*
import java.util.concurrent.TimeUnit


/**
 * A simple [Fragment] subclass.
 */
class SettingFragment : Fragment() {

    lateinit var transactionsViewModel: TransactionsViewModel
    var helperFun = HelperFun
    lateinit var dialog: BottomSheetDialog
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val v =  inflater.inflate(R.layout.fragment_setting, container, false)
        transactionsViewModel = ViewModelProviders.of(this).get(TransactionsViewModel::class.java)
        (activity as AppCompatActivity).setSupportActionBar(v.toolbar)
        v.toolbar.title = "Settings"
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

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

//
        view.btn_report.setOnClickListener {
            showDialogReport()
        }
        view.btn_print.setOnClickListener {
            val bt_name = SharedData.getKeyString(activity!!, "bt_name")
            if(bt_name==null || bt_name ==""){
                Toast.makeText(activity!!, "Device Belum Dipilih", Toast.LENGTH_SHORT).show()
            }else{
                checkPrint()
            }

        }
    }

    fun showDialogReport() {
        val view = layoutInflater.inflate(R.layout.dialog_report, null)
        dialog = BottomSheetDialog(activity!!)
        dialog.setContentView(view)
        dialog.show()
        view.btn_1.setOnClickListener {
            doAsync {
                val data = transactionsViewModel.getTransactionsByDate(getCurrentDate())
                uiThread {
                    val da = helperFun.serializeToJsonTransactions(data)
                    createReport(getCurrentDate(), da)
                }
            }
        }
        view.btn_2.setOnClickListener {
            doAsync {
                val data = transactionsViewModel.getTransactionsByDate(getYesterDayDate())
                uiThread {
                    val da = helperFun.serializeToJsonTransactions(data)
                    createReport(getYesterDayDate(), da)
                }
            }
        }
        view.btn_3.setOnClickListener {
            doAsync {
                val data = transactionsViewModel.getTransactionByDateToDate(
                    getcurrentWeek(),
                    getCurrentDate()
                )
                uiThread {
                    val da = helperFun.serializeToJsonTransactions(data)
                    createReport(getcurrentWeek() + " - " + getCurrentDate(), da)
                }
            }
        }
        view.btn_4.setOnClickListener {
            doAsync {
                val data = transactionsViewModel.getTransactionByDateToDate(
                    getCurrentMonth(),
                    getCurrentDate()
                )
                uiThread {
                    val da = helperFun.serializeToJsonTransactions(data)
                    createReport(getCurrentMonth() + " - " + getCurrentDate(), da)
                }
            }
        }
    }

    private fun getCurrentDate(): String {
        val dateFormat = SimpleDateFormat("dd/MM/yyyy")
        val date = Date()
        return dateFormat.format(date)
    }

    private fun getYesterDayDate(): String {
        val dateFormat = SimpleDateFormat("dd/MM/yyyy")
        val cal = Calendar.getInstance()
        cal.add(Calendar.DATE, -1)
        return dateFormat.format(cal.time)
    }

    fun getcurrentWeek(): String {
        val cal = Calendar.getInstance()
        cal[Calendar.HOUR_OF_DAY] = 0 // ! clear would not reset the hour of day !
        cal.clear(Calendar.MINUTE)
        cal.clear(Calendar.SECOND)
        cal.clear(Calendar.MILLISECOND)
        cal[Calendar.DAY_OF_WEEK] = cal.firstDayOfWeek
        val weeks = cal.time
        val format = SimpleDateFormat("dd/MM/yyyy")
        return format.format(weeks)
    }

    fun getCurrentMonth(): String {
        val cal = Calendar.getInstance()
        cal[Calendar.HOUR_OF_DAY] = 0 // ! clear would not reset the hour of day !
        cal.clear(Calendar.MINUTE)
        cal.clear(Calendar.SECOND)
        cal.clear(Calendar.MILLISECOND)
        cal[Calendar.DAY_OF_MONTH] = 1
        val months = cal.time
        val format = SimpleDateFormat("dd/MM/yyyy")
        return format.format(months)
    }

    fun checkPrint(){
        val bt_name = SharedData.getKeyString(activity!!, "bt_name")
        val data = Data.Builder()
        data.putString("bt_name", bt_name)

        val yourWorkRequest = OneTimeWorkRequestBuilder<PrintWorker>()
            .setInputData(data.build())
            .build()
        val workManager = WorkManager.getInstance()
        workManager.enqueue(yourWorkRequest)
        workManager.getWorkInfoByIdLiveData(yourWorkRequest.id)
            .observe(this, androidx.lifecycle.Observer {
                if (it?.state == null)
                    return@Observer
                when (it.state) {
                    WorkInfo.State.SUCCEEDED -> {
                        val successOutputData = it.outputData
                        val a = successOutputData.getString("job")
                        Toast.makeText(activity!!, a, Toast.LENGTH_SHORT).show()

                    }
                    WorkInfo.State.FAILED -> {
                        val failureOutputData = it.outputData
                        val a = failureOutputData.getString("job")
                        Toast.makeText(activity!!, a, Toast.LENGTH_SHORT).show()


                    }
                }
            })
    }

    fun createReport(title: String, datas: String) {

        val myConstraints = Constraints.Builder()
            .setRequiresDeviceIdle(true) //checks whether device should be idle for the WorkRequest to run
            .setRequiresCharging(true) //checks whether device should be charging for the WorkRequest to run
            .setRequiredNetworkType(NetworkType.CONNECTED) //checks whether device should have Network Connection
            .setRequiresBatteryNotLow(true) // checks whether device battery should have a specific level to run the work request
            .setRequiresStorageNotLow(true) // checks whether device storage should have a specific level to run the work request
            .build()

        val data = Data.Builder()
        data.putString("title", title)
        data.putString("data", datas!!)

        val yourWorkRequest = OneTimeWorkRequestBuilder<ExportDataWorker>()
            .setInputData(data.build())
            .build()

        val yourPeriodicWorkRequest =
            PeriodicWorkRequestBuilder<ExportDataWorker>(1, TimeUnit.HOURS)
                .setConstraints(myConstraints)
                .build()

        val workManager = WorkManager.getInstance()
        workManager.enqueue(yourWorkRequest)
        workManager.getWorkInfoByIdLiveData(yourWorkRequest.id)
            .observe(this, androidx.lifecycle.Observer {
                if (it?.state == null)
                    return@Observer
                when (it.state) {
                    WorkInfo.State.SUCCEEDED -> {
                        val successOutputData = it.outputData
                        val a = successOutputData.getString("job")
                        Toast.makeText(activity!!, a, Toast.LENGTH_SHORT).show()
                        dialog.dismiss()
                    }
                    WorkInfo.State.FAILED -> {
                        val failureOutputData = it.outputData
                        val a = failureOutputData.getString("job")
                        Toast.makeText(activity!!, a, Toast.LENGTH_SHORT).show()
                        dialog.dismiss()

                    }
                }
            })

    }
}

