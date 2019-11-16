package id.boytegar.moocow

import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.bottomsheet.BottomSheetDialog
import id.boytegar.moocow.adapter.MenuAdapter
import id.boytegar.moocow.db.entity.Category
import id.boytegar.moocow.db.entity.MenuItem
import id.boytegar.moocow.viewmodel.MenuItemViewModel
import kotlinx.android.synthetic.main.activity_menu.*
import kotlinx.android.synthetic.main.dialog_edit_menu.view.*
import org.jetbrains.anko.toast


class MenuActivity : AppCompatActivity() {

    lateinit var menuItemViewModel: MenuItemViewModel
    lateinit var list_category: List<Category>
    var cart_numb = 0
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_menu)
        setSupportActionBar(toolbar3)
        title = "Daftar Menu"
        getSupportActionBar()!!.setDisplayHomeAsUpEnabled(true)
        getSupportActionBar()!!.setDisplayShowHomeEnabled(true)
        supportActionBar!!.setHomeAsUpIndicator(R.drawable.ic_navigate_back)
        menuItemViewModel = ViewModelProviders.of(this).get(MenuItemViewModel::class.java)

        fab.setOnClickListener {
            val intent = Intent(this, AddMenuActivity::class.java)
            cart_numb = 1
            startActivity(intent)
        }


        menuItemViewModel.getAllData().observe(this, Observer {
            Log.e("LIST MENU",it.toString())
            val linearLayoutManager = LinearLayoutManager(this)
            list_menu.layoutManager = linearLayoutManager
            list_menu.hasFixedSize()
            val menuAdapter =
                MenuAdapter(this, R.layout.list_menu_settings)
            menuAdapter.submitList(it)
            list_menu.adapter = menuAdapter
            menuAdapter.onItemDelete = { menu ->
                val builder = AlertDialog.Builder(this)
                builder.setCancelable(true)
                builder.setTitle("Mau Menghapus Item Ini ?")
                builder.setPositiveButton(
                    "HAPUS"
                ) { dialog, which ->
                    menuItemViewModel.deleteMenu(menu)
                    dialog.dismiss()
                }
                builder.setNegativeButton("JANGAN") { dialog, which ->
                    dialog.dismiss()
                }
                builder.show()

            }
            menuAdapter.onItemEdit = { menu ->
                showEditMenu(menu)
            }
        })

        menuItemViewModel.filterTextAll.value = ""

        menuItemViewModel.getListCategory().observe(this, Observer {
            list_category = it
        })

        edt_search.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(charSequence: CharSequence, i: Int, i1: Int, i2: Int) {}

            override fun onTextChanged(charSequence: CharSequence, i: Int, i1: Int, i2: Int) {}

            override fun afterTextChanged(editable: Editable) {
                //just set the current value to search.
                menuItemViewModel.filterTextAll.value = "%$editable%"
            }
        })
    }

    fun showEditMenu(it: MenuItem) {
        val view = layoutInflater.inflate(R.layout.dialog_edit_menu, null)
        val dialog = BottomSheetDialog(this)
        view.edt_name.setText(it.name)
        view.edt_keterangan.setText(it.descipt)
        view.edt_price.setText(it.price.toInt().toString())
        view.edt_price_diskon.setText(it.price_discount.toInt().toString())
        if (it.discount == 1) {
            view.checkBox.isChecked = true
            view.edt_price_diskon.isEnabled = true
        }
        if (it.avail == 1) {
            view.radio_ada.isChecked = true
        } else {
            view.radio_tidak.isChecked = true
        }

        view.checkBox.setOnCheckedChangeListener { compoundButton, b ->
            if (b) {
                view.edt_price_diskon.isEnabled = true
            } else {
                view.edt_price_diskon.isEnabled = false
                view.edt_price_diskon.setText("0")
            }
        }

        val list = ArrayList<String>()
        for (i in list_category.indices) {
            list.add(list_category[i].name)
        }
        val areasAdapter =
            ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, list)
        areasAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        view.spinner.adapter = areasAdapter

        view.spinner.setSelection(it.cat_id - 1)
        view.btn_save.setOnClickListener { vi ->

            val name = view.edt_name.text.toString()
            val desc = view.edt_keterangan.text.toString()
            val price = view.edt_price.text.toString().toDouble()
            var price_diskon = 0.0
            var diskon = 0
            if (view.checkBox.isChecked) {
                price_diskon = view.edt_price_diskon.text.toString().toDouble()
                diskon = 1
            }
            var avail = 1
            if (view.radio_tidak.isChecked) {
                avail = 0
            }
            var cat_id = it.cat_id

            view.spinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
                override fun onItemSelected(
                    parent: AdapterView<*>,
                    view: View,
                    position: Int,
                    id: Long
                ) {
                    cat_id = list_category[position - 1].id!!
                }

                override fun onNothingSelected(parent: AdapterView<*>) {}
            }
            val menuItem = MenuItem()
            menuItem.id = it.id
            menuItem.name = name
            menuItem.descipt = desc
            menuItem.price = price
            menuItem.price_discount = price_diskon
            menuItem.discount = diskon
            menuItem.avail = avail
            menuItem.cat_id = cat_id
            menuItemViewModel.updateMenu(
                it.id!!,
                name,
                desc,
                price,
                price_diskon,
                diskon,
                avail,
                cat_id
            )
            dialog.dismiss()
        }

        view.btn_cancel.setOnClickListener {
            dialog.dismiss()
        }


        dialog.setContentView(view)
        dialog.show()
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return super.onSupportNavigateUp()
    }

    fun checkText(view: View): Boolean {
        var bool = true
        if (view.edt_name.text.isEmpty()) {
            bool = false
            view.edt_name.error = "Wajib Di isi"
        }
        if (view.edt_price.text.isEmpty()) {
            bool = false
            view.edt_price.error = "Wajib Di isi"
        }
        if (view.checkBox.isChecked) {
            if (view.edt_price_diskon.text.isEmpty()) {
                bool = false
                view.edt_price_diskon.error = "Wajib Di isi"
            }
        }
        if (view.spinner.selectedItem.toString() == "pilih kategori") {
            bool = false
            toast("Kategori Belum Dipilih")
        }
        return bool
    }
}
