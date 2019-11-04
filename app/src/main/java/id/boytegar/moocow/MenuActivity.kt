package id.boytegar.moocow

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.bottomsheet.BottomSheetDialog
import id.boytegar.moocow.db.entity.Category
import id.boytegar.moocow.db.entity.MenuItem
import id.boytegar.moocow.viewmodel.MenuItemViewModel
import kotlinx.android.synthetic.main.activity_menu.*
import kotlinx.android.synthetic.main.dialog_edit_menu.view.*
import org.jetbrains.anko.toast


class MenuActivity : AppCompatActivity() {

    lateinit var menuItemViewModel: MenuItemViewModel
    lateinit var list_category: List<Category>
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_menu)
        setSupportActionBar(toolbar3)
        menuItemViewModel = ViewModelProviders.of(this).get(MenuItemViewModel::class.java)

        fab.setOnClickListener {
            val intent = Intent(this, AddMenuActivity::class.java)
            startActivity(intent)
        }


        menuItemViewModel.getListMenu().observe(this, Observer {
            Log.e("LIST MENU",it.toString())
            val linearLayoutManager = LinearLayoutManager(this)
            list_menu.layoutManager = linearLayoutManager
            list_menu.hasFixedSize()
            val menuAdapter = MenuAdapter(this,  R.layout.list_menu_settings)
            menuAdapter.submitList(it)
            list_menu.adapter = menuAdapter
            menuAdapter.onItemDelete = { menu ->
                menuItemViewModel.deleteMenu(menu)
            }
            menuAdapter.onItemEdit = { menu ->
                showEditMenu(menu)
            }
        })

        menuItemViewModel.getListCategory().observe(this, Observer {
            list_category = it
        })
    }

    fun showEditMenu(it: MenuItem) {
        val view = layoutInflater.inflate(R.layout.dialog_edit_menu, null)
        val dialog = BottomSheetDialog(this)
        view.edt_name.setText(it.name)
        view.edt_keterangan.setText(it.desc)
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
        view.btn_save.setOnClickListener {

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
            var cat_id = 0

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
            menuItem.desc = desc
            menuItem.price = price
            menuItem.price_discount = price_diskon
            menuItem.discount = diskon
            menuItem.avail = avail
            menuItem.cat_id = cat_id
            menuItemViewModel.updateMenu(menuItem)
            dialog.dismiss()
        }

        view.btn_cancel.setOnClickListener {
            dialog.dismiss()
        }


        dialog.setContentView(view)
        dialog.show()
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
