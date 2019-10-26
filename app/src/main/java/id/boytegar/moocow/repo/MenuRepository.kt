package id.boytegar.moocow.repo

import android.app.Application
import androidx.lifecycle.LiveData
import id.boytegar.moocow.db.MoocowDb
import id.boytegar.moocow.db.entity.MenuItem
import id.boytegar.moocow.helper.Async

class MenuRepository(application: Application) {
    val db = MoocowDb.getInstance(application)
    private val userDao = db.DaoMenuItem()
    lateinit var list: LiveData<List<MenuItem>>

    init {
        Async {
            list = userDao.getListUsers()
        }
    }

    fun insert(user: User) {
        Async {
            userDao.insert(user)
        }

    }

    fun update(user: User) {
        Async {
            userDao.update(user)
        }
    }

    fun delete(user: User) {
        Async {
            userDao.delete(user)
        }
    }

    fun deleteById(id: Int) {
        Async {
            userDao.deleteById(id)
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