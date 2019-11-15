package id.boytegar.moocow.repo

import android.app.Application
import androidx.paging.DataSource
import id.boytegar.moocow.db.MoocowDb
import id.boytegar.moocow.db.entity.MenuItem
import id.boytegar.moocow.helper.Async

class MenuRepository(application: Application) {
    val db = MoocowDb.getInstance(application)
    private val menuDao = db.DaoMenuItem()


    fun insert(menuItem: MenuItem) {
        Async {
            menuDao.insert(menuItem)
        }

    }

    fun update(id: Int,
        name: String,
        desc: String,
        price: Double,
        priceDiskon: Double,
        diskon: Int,
        avail: Int,
        catId: Int
    ) {
        Async {
            menuDao.updateMenuItemById(id,name, desc, price, priceDiskon, diskon, avail, catId)
        }
    }

    fun delete(menuItem: MenuItem) {
        Async {
            menuDao.delete(menuItem)
        }
    }

    fun deleteById(id: Int) {
        Async {
            menuDao.deleteById(id)
        }
    }

    fun getsearchMenu(search: String) : DataSource.Factory<Int, MenuItem>{
       return menuDao.searchMenu(search)
    }

    fun getListDiscount(): DataSource.Factory<Int, MenuItem>{
        return menuDao.getDiskonMenu()
    }

    fun getListByCategory(cat_id: Int): DataSource.Factory<Int, MenuItem>{
        return menuDao.getByCategory(cat_id)
    }

    fun getAllUser(): DataSource.Factory<Int, MenuItem> {
        val list = menuDao.getListMenu()
        return list
    }

}