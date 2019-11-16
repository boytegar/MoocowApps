package id.boytegar.moocow

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.work.Data
import androidx.work.OneTimeWorkRequest
import androidx.work.WorkManager
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import id.boytegar.moocow.adapter.CartAdapter
import id.boytegar.moocow.adapter.MenuOrderAdapter
import id.boytegar.moocow.db.entity.Cart
import id.boytegar.moocow.db.entity.Transactions
import id.boytegar.moocow.helper.HelperFun
import id.boytegar.moocow.viewmodel.CartViewModel
import kotlinx.android.synthetic.main.activity_cart.*
import org.jetbrains.anko.doAsync
import org.jetbrains.anko.toast
import org.jetbrains.anko.uiThread
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList


class CartActivity : AppCompatActivity() {

    lateinit var cartViewModel: CartViewModel
    var total = 0.0
    var quantity = 0
    var ppn = 0.0
    var helper=HelperFun
    var list = ArrayList<Cart>()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_cart)
        setSupportActionBar(toolbar)
        title = "Cart"
        getSupportActionBar()!!.setDisplayHomeAsUpEnabled(true)
        getSupportActionBar()!!.setDisplayShowHomeEnabled(true)
        supportActionBar!!.setHomeAsUpIndicator(R.drawable.ic_navigate_back)
        cartViewModel = ViewModelProviders.of(this).get(CartViewModel::class.java)


//        val gotuuid = btDevices.getItem(position)
//            .fetchUuidsWithSdp()
//        val uuid = btDevices.getItem(position).getUuids()[0]
//            .getUuid()
//        mbtSocket = btDevices.getItem(position)
//            .createRfcommSocketToServiceRecord(uuid)
        txt_ppn.text = "Rp. "+helper.rupiahformat(ppn)
        cartViewModel.getAllDataCart().observe(this, Observer {
            total = 0.0
            quantity = 0
            list = it as ArrayList<Cart>
            val linearLayoutManager = LinearLayoutManager(this)
            list_cart.layoutManager = linearLayoutManager
            list_cart.hasFixedSize()
            val cartAdapter = CartAdapter(it)
            list_cart.adapter = cartAdapter
            for(i in it.indices){
                val car = it[i]
                val tot = car.price*car.quantity
                total += tot
                quantity += car.quantity
            }

            txt_price.text = "Rp. "+helper.rupiahformat(total)
            txt_total.text = "Rp. "+helper.rupiahformat(total+ppn)
            txt_item.text = quantity.toString()
        })

        check_ppn.setOnCheckedChangeListener { compoundButton, b ->
            if(b){
                ppn = (total/100)*10
                txt_total.text = "Rp. "+helper.rupiahformat(total+ppn)
                txt_ppn.text = "Rp. "+helper.rupiahformat(ppn)
            }else{
                ppn = 0.0
                txt_total.text = "Rp. "+helper.rupiahformat(total+ppn)
                txt_ppn.text = "Rp. "+helper.rupiahformat(ppn)
            }
        }

        btn_done.setOnClickListener {
          insertTransactions(1)
        }
        btn_save.setOnClickListener {
            insertTransactions(0)
        }
    }

    fun insertTransactions(status: Int){
        val trans = Transactions()
        val buyer = edt_buyer.text.toString()
        trans.date = getCurrentDate()
        trans.time = getCurrentTime()
        trans.tax = ppn
        trans.total_price = total
        trans.name_buyer = buyer
        trans.status = status
        trans.name_cashier = "Admin"
        trans.menu = list
        if(edt_buyer.text.isEmpty()){
            edt_buyer.error = "Wajib Di Isi"
        }else{
            doAsync {
                cartViewModel.insertTransactions(trans)
                uiThread {
                    toast("Transaksi Berhasil")
                    cartViewModel.deleteAll()
                    finish()
                }
            }
        }
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return super.onSupportNavigateUp()
    }

    private fun getCurrentDate(): String {
        val dateFormat = SimpleDateFormat("dd/MM/yyyy")
        val date = Date()
        return dateFormat.format(date)
    }
    fun getCurrentTime(): String{
        val dateformat = SimpleDateFormat(" HH:mm:ss")
        val date = Date()
        return dateformat.format(date)
    }
}
