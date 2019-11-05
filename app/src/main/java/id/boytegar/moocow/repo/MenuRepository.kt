package id.boytegar.moocow.repo

import android.app.Application
import androidx.lifecycle.LiveData
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

    fun update(menuItem: MenuItem) {
        Async {
            menuDao.update(menuItem)
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

    fun getAllUser(): DataSource.Factory<Int, MenuItem> {
        val list = menuDao.getListMenu()
        return list
    }

}