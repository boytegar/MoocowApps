package id.boytegar.moocow

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import id.boytegar.moocow.viewmodel.MenuItemViewModel
import kotlinx.android.synthetic.main.activity_menu.*
import androidx.recyclerview.widget.LinearLayoutManager





class MenuActivity : AppCompatActivity() {

    lateinit var menuItemViewModel: MenuItemViewModel

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
            menuAdapter.onItemDelete = {

            }
        })
    }
}
