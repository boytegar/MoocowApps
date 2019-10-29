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
import android.view.View
import org.jetbrains.anko.toast


class AddMenuActivity : AppCompatActivity() {

    lateinit var menuItemViewModel: MenuItemViewModel
    lateinit var list_category: List<Category>

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
    }

    fun showCategory(it: List<Category>) {
        val list = ArrayList<String>()
        list.add("Pilih Category")
        for (i in it.indices){
            list.add(it[i].name)
        }
        list.add("Tambah Category")

        val areasAdapter =
            ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, list)
        areasAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        spinner.setAdapter(areasAdapter)
        spinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>, view: View, position: Int, id: Long) {
                if(list[position] == "Tambah Category"){
                    toast("Tambah Category")
                }
            }

            override fun onNothingSelected(parent: AdapterView<*>) {}
        }
    }
}
