package id.boytegar.moocow.ui


import android.content.Intent
import android.graphics.Paint
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.*
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.bottomsheet.BottomSheetDialog
import id.boytegar.moocow.CartActivity
import id.boytegar.moocow.R
import id.boytegar.moocow.adapter.CategoryAdapter
import id.boytegar.moocow.adapter.MenuOrderAdapter
import id.boytegar.moocow.db.entity.Cart
import id.boytegar.moocow.helper.HelperFun
import id.boytegar.moocow.viewmodel.MenuItemViewModel
import kotlinx.android.synthetic.main.dialog_insert_cart.view.*
import kotlinx.android.synthetic.main.fragment_menu.view.*
import org.jetbrains.anko.doAsync
import org.jetbrains.anko.uiThread

class MenuFragment : Fragment() {

    lateinit var viewz: View
    lateinit var menuItemViewModel: MenuItemViewModel
    lateinit var menuAdapter: MenuOrderAdapter
    var count = 0
    var cart_numb = 0
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
        menuItemViewModel = ViewModelProviders.of(this).get(MenuItemViewModel::class.java)
        menuItemViewModel.filterTextAll.value = ""
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val v = inflater.inflate(R.layout.fragment_menu, container, false)
        (activity as AppCompatActivity).setSupportActionBar(v.toolbar)

        menuItemViewModel.getListCategory().observe(this, Observer {
            val list_cat = ArrayList<String>()
            list_cat.add("All")
            list_cat.add("Promo")
            for (i in it.indices){
                list_cat.add(it[i].name)
            }
            val catAdapter = CategoryAdapter(list_cat)
            val linearLayoutManager = LinearLayoutManager(activity, LinearLayoutManager.HORIZONTAL, false)
            viewz.list_category.layoutManager = linearLayoutManager
            // viewz.list_menu.hasFixedSize()
            viewz.list_category.adapter = catAdapter
            catAdapter.onItemClick={its ->
                when(its){
                    0->{
                        menuItemViewModel.filterTextAll.value = ""
                    }
                    1->{
                        menuItemViewModel.filterTextAll.value = "promo"
                    }
                    else->{
                        val a = it[its-2]
                        menuItemViewModel.filterTextAll.value = "${a.id}"
                    }
                }

            }
        })
        menuItemViewModel.getAllData().observe(this, Observer {
            val linearLayoutManager = LinearLayoutManager(activity)
            v.list_menu.layoutManager = linearLayoutManager
            v.list_menu.hasFixedSize()
             menuAdapter =
                MenuOrderAdapter(activity!!, R.layout.list_menu_order, menuItemViewModel)
            menuAdapter.submitList(it)
            v.list_menu.adapter = menuAdapter
            menuAdapter.onItemClick = { menu ->
                // menuItemViewModel.deleteMenu(menu)
                showDialogMenu(menu)
            }
            menuAdapter.onCartClick = {cart ->
                editMenuDialog(cart, menuAdapter)
            }
        })
        menuItemViewModel.getCountCart().observe(this, Observer {
            count = it
            v.txt_count.text = it.toString()
        })
        viewz = v
        return v
    }

    override fun onResume() {
        if(cart_numb == 1){
            cart_numb = 0
            menuAdapter.notifyDataSetChanged()
        }
        super.onResume()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        view.edt_search.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(charSequence: CharSequence, i: Int, i1: Int, i2: Int) {}

            override fun onTextChanged(charSequence: CharSequence, i: Int, i1: Int, i2: Int) {}

            override fun afterTextChanged(editable: Editable) {
                //just set the current value to search.
                menuItemViewModel.filterTextAll.value = "%$editable%"
            }
        })

        view.fab_cart.setOnClickListener {
            if (count==0){
                Toast.makeText(activity!!, "Belum Ada Menu Yang Di Order", Toast.LENGTH_SHORT).show()
            }
            else{
                val intent = Intent(activity!!, CartActivity::class.java)
                cart_numb = 1
                startActivity(intent)
            }

        }


    }

    fun showDialogMenu(menu: id.boytegar.moocow.db.entity.MenuItem) {
        var numb = 0
        var prices = 0.0
        val view = layoutInflater.inflate(R.layout.dialog_insert_cart, null)
        val dialog = BottomSheetDialog(activity!!)
        val helperFun = HelperFun

        view.txt_name.text = menu.name
        view.txt_total.text =  "Rp. "+helperFun.rupiahformat(numb*prices)
        view.edt_pcs_order.setText(numb.toString())
        if(menu.discount == 1){
            view.txt_price_discount.visibility = View.VISIBLE
            view.txt_price_discount.text = "Rp. " + helperFun.rupiahformat(menu.price)
            view.txt_price_discount.paintFlags = view.txt_price_discount.paintFlags or Paint.STRIKE_THRU_TEXT_FLAG
            view.txt_price.text = "Rp. "+helperFun.rupiahformat(menu.price_discount)
            prices = menu.price_discount
        }else{
            view.txt_price_discount.visibility = View.GONE
            view.txt_price.text = "Rp. "+helperFun.rupiahformat(menu.price)
            prices = menu.price
        }

        view.edt_pcs_order.addTextChangedListener(object : TextWatcher{
            override fun afterTextChanged(p0: Editable?) {
                if(p0.isNullOrEmpty()){
                    view.edt_pcs_order.setText("0")
                }else{
                    numb = p0.toString().toInt()
                    view.txt_total.text = "Rp. "+helperFun.rupiahformat(numb*prices)
                }
            }

            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {

            }

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {

            }

        })

        view.btn_remove.setOnClickListener {
            when (numb) {
                0 -> {
                    Toast.makeText(activity!!, "Sudah Tidak Bisa Dikurangi", Toast.LENGTH_SHORT)
                        .show()
                }
                else -> {
                    numb--
                    view.edt_pcs_order.setText("$numb")
                    view.txt_total.text = "Rp. "+helperFun.rupiahformat(numb*prices)
                }
            }
        }
        view.btn_adds.setOnClickListener {
            numb++
            view.edt_pcs_order.setText("$numb")
            view.txt_total.text = "Rp. "+helperFun.rupiahformat(numb*prices)
        }

        view.btn_save.setOnClickListener {
            val cart = Cart()
            cart.price = prices
            cart.quantity = numb
            cart.name = menu.name
            cart.id_menu = menu.id!!
            doAsync {
                menuItemViewModel.insertCart(cart)
                uiThread {
                    menuAdapter.notifyDataSetChanged()
                    dialog.dismiss()
                }
            }
        }
        view.btn_cancel.setOnClickListener {
            dialog.dismiss()
        }
        dialog.setContentView(view)
        dialog.show()
    }


    fun editMenuDialog(
        cart: Cart,
        menuAdapter: MenuOrderAdapter
    ) {

        val view = layoutInflater.inflate(R.layout.dialog_insert_cart, null)
        val dialog = BottomSheetDialog(activity!!)
        val helperFun = HelperFun

        view.txt_name.text = cart.name
        var prices = cart.price
        var numb = cart.quantity
        view.txt_name.text = cart.name
        view.txt_total.text = "Rp. " + helperFun.rupiahformat(numb * prices)
        view.edt_pcs_order.setText(numb.toString())
        view.txt_price_discount.visibility = View.GONE
        view.txt_price.text = "Rp. " + helperFun.rupiahformat(cart.price)



        view.btn_remove.setOnClickListener {
            when (numb) {
                0 -> {
                    Toast.makeText(activity!!, "Sudah Tidak Bisa Dikurangi", Toast.LENGTH_SHORT)
                        .show()
                }
                else -> {
                    numb--
                    view.edt_pcs_order.setText("$numb")
                    view.txt_total.text = "Rp. " + helperFun.rupiahformat(numb * prices)
                }
            }
        }
        view.btn_adds.setOnClickListener {
            numb++
            view.edt_pcs_order.setText("$numb")
            view.txt_total.text = "Rp. " + helperFun.rupiahformat(numb * prices)
        }

        view.btn_save.setOnClickListener {
            val quan = view.edt_pcs_order.text.toString()
            menuItemViewModel.updateQuantityById(cart.id!!,quan.toInt() )
            menuAdapter.notifyDataSetChanged()
            dialog.dismiss()
        }
        view.btn_cancel.setOnClickListener {
            dialog.dismiss()
        }
        dialog.setContentView(view)
        dialog.show()
    }



    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.menu, menu)
        super.onCreateOptionsMenu(menu, inflater)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.getItemId()) {

            R.id.filter ->{

                if(viewz.list_category.visibility == View.GONE){
                    viewz.lyt_search.visibility = View.GONE
                    viewz.list_category.visibility = View.VISIBLE
                    menuItemViewModel.filterTextAll.value = ""
                }else{
                    viewz.list_category.visibility = View.GONE
                }
                return true
            }

            R.id.search ->{
                if(viewz.lyt_search.visibility == View.GONE){
                    viewz.lyt_search.visibility = View.VISIBLE
                    viewz.list_category.visibility = View.GONE
                    menuItemViewModel.filterTextAll.value = ""
                }
                else{
                    viewz.lyt_search.visibility = View.GONE
                }
                return true
            }

        }
        return false
    }

}
