package id.boytegar.moocow

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import id.boytegar.moocow.db.entity.Category
import id.boytegar.moocow.viewmodel.MenuItemViewModel
import kotlinx.android.synthetic.main.activity_add_menu.*
import android.widget.ArrayAdapter
import android.widget.AdapterView
import androidx.core.app.ComponentActivity.ExtraData
import androidx.core.content.ContextCompat.getSystemService
import android.icu.lang.UCharacter.GraphemeClusterBreak.T
import android.util.Log
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.bottomsheet.BottomSheetDialog
import id.boytegar.moocow.db.entity.MenuItem
import kotlinx.android.synthetic.main.activity_add_menu.btn_save
import kotlinx.android.synthetic.main.activity_add_menu.edt_name
import kotlinx.android.synthetic.main.dialog_insert_category.*
import kotlinx.android.synthetic.main.dialog_insert_category.view.*
import org.jetbrains.anko.toast


class AddMenuActivity : AppCompatActivity() {

    lateinit var menuItemViewModel: MenuItemViewModel
    lateinit var list_category: List<Category>
    var cat_id = 0
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_menu)
        setSupportActionBar(toolbar)
        title = "Tambah Menu Baru"
        menuItemViewModel = ViewModelProviders.of(this).get(MenuItemViewModel::class.java)

        menuItemViewModel.getListCategory().observe(this, Observer {
            list_category = it
            showCategory(it)
        })
        radio_ada.isChecked = true

        btn_save.setOnClickListener {
            if(checkText()){
                val name = edt_name.text.toString()
                val desc = edt_keterangan.text.toString()
                val price = edt_price.text.toString().toDouble()
                var price_diskon = 0.0
                var diskon = 0
                if(checkBox.isChecked){
                    price_diskon = edt_price_diskon.text.toString().toDouble()
                    diskon = 1
                }
                var avail = 1
                if(radio_tidak.isChecked){
                    avail = 0
                }
                val menu = MenuItem(name, desc, price, price_diskon, diskon,avail, cat_id)
                menuItemViewModel.insertMenu(menu)
                finish()
            }
        }

        checkBox.setOnCheckedChangeListener { compoundButton, b ->
            if(b){
                edt_price_diskon.isEnabled = true
            }else{
                edt_price_diskon.isEnabled = false
                edt_price_diskon.setText("0")
            }
        }

    }

    fun showCategory(it: List<Category>) {
        val list = ArrayList<String>()
        list.add("Pilih kategori")
        for (i in it.indices){
            list.add(it[i].name)
        }
        list.add("Tambah kategori")

        val areasAdapter =
            ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, list)
        areasAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        spinner.adapter = areasAdapter
        spinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>, view: View, position: Int, id: Long) {
                if(list[position] == "Tambah kategori"){
                    showDialogInsertCategory()
                }else if(list[position] != "pilih kategori"){
                    cat_id = it[position-1].id!!
                }
            }

            override fun onNothingSelected(parent: AdapterView<*>) {}
        }
    }

    fun checkText(): Boolean{
        var bool = true
        if(edt_name.text.isEmpty()){
            bool = false
            edt_name.error = "Wajib Di isi"
        }
        if(edt_price.text.isEmpty()){
            bool = false
            edt_price.error = "Wajib Di isi"
        }
        if(checkBox.isChecked){
            if(edt_price_diskon.text.isEmpty()){
                bool = false
                edt_price_diskon.error = "Wajib Di isi"
            }
        }
        if(spinner.selectedItem.toString()=="pilih kategori"){
            bool = false
            toast("Kategori Belum Dipilih")
        }
        return bool
    }

    fun showDialogInsertCategory(){
        val view = layoutInflater.inflate(R.layout.dialog_insert_category, null)
        val dialog = BottomSheetDialog(this)
        dialog.setContentView(view)
        dialog.show()
        var name = ""
        var desc = ""
        view.btn_cancel.setOnClickListener {
         dialog.dismiss()
        }
        view.btn_save.setOnClickListener {
            if(view.edt_name.text.isEmpty()){
                view.edt_name.error = "Harus Di isi"
            }else{
                name = view.edt_name.text.toString()
            }
            if(!view.edt_desc.text.toString().isEmpty()){
                desc = view.edt_desc.text.toString()
            }

            val cat = Category(name, desc)
            menuItemViewModel.insertCategory(cat)
            dialog.dismiss()
        }
    }
}
