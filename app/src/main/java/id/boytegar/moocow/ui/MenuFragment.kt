package id.boytegar.moocow.ui


import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.*
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.fragment.app.Fragment
import id.boytegar.moocow.R
import kotlinx.android.synthetic.main.fragment_menu.view.*
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import id.boytegar.moocow.db.entity.Category
import id.boytegar.moocow.viewmodel.MenuItemViewModel
import id.boytegar.moocow.adapter.MenuOrderAdapter
import id.boytegar.moocow.adapter.CategoryAdapter
class MenuFragment : Fragment() {

    lateinit var viewz: View
    lateinit var menuItemViewModel: MenuItemViewModel
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
                Toast.makeText(activity!!, "CLICKED POS $it", Toast.LENGTH_SHORT).show()
            }
        })
        menuItemViewModel.getAllData().observe(this, Observer {
            val linearLayoutManager = LinearLayoutManager(activity)
            v.list_menu.layoutManager = linearLayoutManager
            v.list_menu.hasFixedSize()
            val menuAdapter = MenuOrderAdapter(activity!!, R.layout.list_menu_settings)
            menuAdapter.submitList(it)
            v.list_menu.adapter = menuAdapter
            menuAdapter.onItemDelete = { menu ->
                // menuItemViewModel.deleteMenu(menu)
            }
            menuAdapter.onItemEdit = { menu ->
                //showEditMenu(menu)
            }
        })
        viewz = v
        return v
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
