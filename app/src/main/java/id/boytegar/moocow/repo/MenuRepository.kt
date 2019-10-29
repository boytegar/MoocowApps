package id.boytegar.moocow.repo

import android.app.Application
import androidx.lifecycle.LiveData
import id.boytegar.moocow.db.MoocowDb
import id.boytegar.moocow.db.entity.MenuItem
import id.boytegar.moocow.helper.Async

class MenuRepository(application: Application) {
    val db = MoocowDb.getInstance(application)
    private val menuDao = db.DaoMenuItem()
    lateinit var list: LiveData<List<MenuItem>>

    init {
        Async {
            //list = userDao.getListUsers()
        }
    }

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

//    fun updateById(id: Int, name: String, email: String){
//        Async{
//            dataImageDao.updateById(id, name, email)
//        }
//    }

    fun getAllUser(): LiveData<List<MenuItem>> {
        return list
    }

}