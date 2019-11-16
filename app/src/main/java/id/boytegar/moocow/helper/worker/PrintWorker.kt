package id.boytegar.moocow.helper.worker

import android.bluetooth.BluetoothAdapter
import android.bluetooth.BluetoothDevice
import android.bluetooth.BluetoothSocket
import android.content.Context
import android.graphics.BitmapFactory
import android.util.Log
import androidx.work.Data
import androidx.work.Worker
import androidx.work.WorkerParameters
import id.boytegar.moocow.helper.PrinterCommands
import id.boytegar.moocow.helper.Utils
import java.io.IOException
import java.io.InputStream
import java.io.OutputStream
import java.util.*


class PrintWorker(ctx: Context, params: WorkerParameters) : Worker(ctx, params) {
    lateinit var btsocket: BluetoothSocket
    lateinit var outputStream: OutputStream
    // android built in classes for bluetooth operations
    lateinit var mmDevice: BluetoothDevice
    val mBluetoothAdapter = BluetoothAdapter.getDefaultAdapter()
    var mmInputStream: InputStream? = null
    //BT SOCKET BELUM
    // private val SPP_UUID = UUID.fromString("8ce255c0-200a-11e0-ac64-0800200c9a66")
    var context = ctx


    override fun doWork(): Result {
        val taskData = inputData
        val bt_name = taskData.getString("bt_name")!!
      //  btsocket = taskData.get
        val pairedDevices = mBluetoothAdapter.bondedDevices
        if (pairedDevices.size > 0) {
            for (device in pairedDevices) {
                Log.e("BT_NAME", device.name)
                if (device.getName() == bt_name) {
                    mmDevice = device
                    break
                }
            }
        }

        try {
            val uuid = UUID.fromString("00001101-0000-1000-8000-00805f9b34fb")
            btsocket = mmDevice!!.createRfcommSocketToServiceRecord(uuid)
            btsocket.connect()
            outputStream = btsocket.outputStream
            mmInputStream = btsocket.inputStream
            printBill()
            val outputData = Data.Builder().putString("job", "Print Finished").build()
            return Result.success(outputData)
        } catch (e: java.lang.Exception) {
            val outputData = Data.Builder().putString("job", "Print Failed").build()
            return Result.failure(outputData)
        }


    }


    protected fun printBill() {

        Log.e("MASUK PRINT GAN", "MASUK PRINT")

        val opstream = btsocket.outputStream
            outputStream = opstream!!

     //   btsocket = DeviceList.getSocket();

      //  btsocket = DeviceList.getSocket();

            try {
                outputStream = btsocket.outputStream
                val printformat = byteArrayOf(0x1B, 0x21, 0x03)
                outputStream.write(printformat)
                printCustom("Fair Group BD", 2, 1)
                printCustom("Pepperoni Foods Ltd.", 0, 1)
              //  printPhoto(R.drawable.ic_icon_pos)
                printCustom("H-123, R-123, Dhanmondi, Dhaka-1212", 0, 1)
                printCustom("Hot Line: +88000 000000", 0, 1)
                printCustom("Vat Reg : 0000000000,Mushak : 11", 0, 1)

               // printText(leftRightAlign(dateTime[0], dateTime[1]))
                printText(leftRightAlign("Qty: Name", "Price "))
                printCustom(String(CharArray(32)).replace("\u0000", "."), 0, 1)
                printText(leftRightAlign("Total", "2,0000/="))
                printNewLine()
                printCustom("Thank you for coming & we look", 0, 1)
                printCustom("forward to serve you again", 0, 1)
                printNewLine()
                printNewLine()

                outputStream.flush()
            } catch (e: IOException) {
                e.printStackTrace()
            }
        finally {
            btsocket.close()
            outputStream.close()
        }
    }

    private fun printCustom(msg: String, size: Int, align: Int) {
        //Print config "mode"
        val cc = byteArrayOf(0x1B, 0x21, 0x03)  // 0- normal size text
        //byte[] cc1 = new byte[]{0x1B,0x21,0x00};  // 0- normal size text
        val bb = byteArrayOf(0x1B, 0x21, 0x08)  // 1- only bold text
        val bb2 = byteArrayOf(0x1B, 0x21, 0x20) // 2- bold with medium text
        val bb3 = byteArrayOf(0x1B, 0x21, 0x10) // 3- bold with large text
        try {
            when (size) {
                0 -> outputStream.write(cc)
                1 -> outputStream.write(bb)
                2 -> outputStream.write(bb2)
                3 -> outputStream.write(bb3)
            }

            when (align) {
                0 ->
                    //left align
                    outputStream.write(PrinterCommands.ESC_ALIGN_LEFT)
                1 ->
                    //center align
                    outputStream.write(PrinterCommands.ESC_ALIGN_CENTER)
                2 ->
                    //right align
                    outputStream.write(PrinterCommands.ESC_ALIGN_RIGHT)
            }
            outputStream.write(msg.toByteArray())
            outputStream.write(PrinterCommands.LF as ByteArray)
            //outputStream.write(cc);
            //printNewLine();
        } catch (e: IOException) {
            e.printStackTrace()
        }

    }
    //print photo
    fun printPhoto(img: Int) {
        try {
            val bmp = BitmapFactory.decodeResource(
             context.resources,
                img
            )
            if (bmp != null) {
                val command = Utils.decodeBitmap(bmp)
                outputStream!!.write(PrinterCommands.ESC_ALIGN_CENTER)
                printText(command!!)
            } else {
                Log.e("Print Photo error", "the file isn't exists")
            }
        } catch (e: Exception) {
            e.printStackTrace()
            Log.e("PrintTools", "the file isn't exists")
        }

    }

    //print new line
    private fun printNewLine() {
        try {
            outputStream.write(PrinterCommands.FEED_LINE)
        } catch (e: IOException) {
            e.printStackTrace()
        }

    }

    fun resetPrint() {
        try {
            outputStream.write(PrinterCommands.ESC_FONT_COLOR_DEFAULT)
            outputStream.write(PrinterCommands.FS_FONT_ALIGN)
            outputStream.write(PrinterCommands.ESC_ALIGN_LEFT)
            outputStream.write(PrinterCommands.ESC_CANCEL_BOLD)
            outputStream.write(PrinterCommands.LF.toInt())
        } catch (e: IOException) {
            e.printStackTrace()
        }

    }
    private fun printText(msg: String) {
        try {
            // Print normal text
            outputStream.write(msg.toByteArray())
        } catch (e: IOException) {
            e.printStackTrace()
        }

    }

    //print byte[]
    private fun printText(msg: ByteArray) {
        try {
            // Print normal text
            outputStream.write(msg)
            printNewLine()
        } catch (e: IOException) {
            e.printStackTrace()
        }

    }


    private fun leftRightAlign(str1: String, str2: String): String {
        var ans = str1 + str2
        if (ans.length < 31) {
            val n = 31 - str1.length + str2.length
            ans = str1 + String(CharArray(n)).replace("\u0000", " ") + str2
        }
        return ans
    }


}