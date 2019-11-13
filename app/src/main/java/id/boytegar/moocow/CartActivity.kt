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
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import id.boytegar.moocow.adapter.CartAdapter
import id.boytegar.moocow.adapter.MenuOrderAdapter
import id.boytegar.moocow.helper.HelperFun
import id.boytegar.moocow.viewmodel.CartViewModel
import kotlinx.android.synthetic.main.activity_cart.*


class CartActivity : AppCompatActivity() {

    lateinit var cartViewModel: CartViewModel
    var total = 0.0
    var quantity = 0
    var ppn = 0.0
    var helper=HelperFun
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_cart)
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
    }
}
