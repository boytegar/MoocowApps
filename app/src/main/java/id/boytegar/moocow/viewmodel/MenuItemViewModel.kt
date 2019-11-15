package id.boytegar.moocow.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.paging.DataSource
import androidx.paging.LivePagedListBuilder
import androidx.paging.PagedList
import id.boytegar.moocow.db.entity.Cart
import id.boytegar.moocow.db.entity.Category
import id.boytegar.moocow.db.entity.MenuItem
import id.boytegar.moocow.repo.CartRepository
import id.boytegar.moocow.repo.CategoryRepository
import id.boytegar.moocow.repo.MenuRepository


class MenuItemViewModel (application: Application): AndroidViewModel(application){
    private val categoryRepository = CategoryRepository(application)
    val menuRepository  = MenuRepository(application)
    var cartRepository = CartRepository(application)
    var filterTextAll = MutableLiveData<String>()


    fun getAllData(): LiveData<PagedList<MenuItem>> {
        var personsLiveData: LiveData<PagedList<MenuItem>>

        return Transformations.switchMap<String, PagedList<MenuItem>>(
            filterTextAll
        ) { input ->
            if (input == null || input.equals("") || input.equals("%%")) {
                //check if the current value is empty load all data else search
                val factory: DataSource.Factory<Int, MenuItem> = menuRepository.getAllUser()
                val pagedListBuilder: LivePagedListBuilder<Int, MenuItem> = LivePagedListBuilder(factory,
                    20)
                 personsLiveData = pagedListBuilder.build()
                return@switchMap personsLiveData
            }else if(input == "promo"){
                val factory: DataSource.Factory<Int, MenuItem> = menuRepository.getListDiscount()
                val pagedListBuilder: LivePagedListBuilder<Int, MenuItem> = LivePagedListBuilder(factory,
                    20)
                personsLiveData = pagedListBuilder.build()
                return@switchMap personsLiveData
            }else if(checkNumeric(input)){
                val factory: DataSource.Factory<Int, MenuItem> = menuRepository.getListByCategory(input.toInt())
                val pagedListBuilder: LivePagedListBuilder<Int, MenuItem> = LivePagedListBuilder(factory,
                    20)
                personsLiveData = pagedListBuilder.build()
                return@switchMap personsLiveData
            } else {
                val factory: DataSource.Factory<Int, MenuItem> = menuRepository.getsearchMenu(input)
                val pagedListBuilder: LivePagedListBuilder<Int, MenuItem> = LivePagedListBuilder(factory,
                    20)
                 personsLiveData = pagedListBuilder.build()
                return@switchMap personsLiveData
            }

        }
    }



    fun checkNumeric(str: String): Boolean{
        var numeric = str.matches("-?\\d+(\\.\\d+)?".toRegex())
        numeric = numeric
        return numeric
    }

    fun insertMenu(menuItem: MenuItem){
        menuRepository.insert(menuItem)
    }
    fun updateMenu(id: Int,
        name: String,
        desc: String,
        price: Double,
        price_diskon: Double,
        diskon: Int,
        avail: Int,
        catId: Int
    ){
        menuRepository.update(id, name, desc, price, price_diskon, diskon, avail, catId)
    }
    fun deleteMenu(menuItem: MenuItem){
        menuRepository.delete(menuItem)
    }

    fun insertCategory(category: Category){
        categoryRepository.insert(category)
    }
   fun getListCategory(): LiveData<List<Category>>{
       return categoryRepository.getAllCategory()
   }
    fun insertCart(cart: Cart){
        cartRepository.insert(cart)
    }
    fun getCountCart(): LiveData<Int>{
        val dats = cartRepository.getCount()
        return dats
    }
    fun checkItemCart(id: Int):Boolean{
        val b = cartRepository.checkItemCart(id)
        return b
    }

    fun getItemCartById(id: Int):Cart{
        val b = cartRepository.getItemCartById(id)
        return b
    }
    fun updateQuantityById(id: Int, quantity: Int){
     cartRepository.updateById(id, quantity)
    }

}