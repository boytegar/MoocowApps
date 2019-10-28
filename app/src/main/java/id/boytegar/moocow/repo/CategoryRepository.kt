package id.boytegar.moocow.repo

import android.app.Application
import androidx.lifecycle.LiveData
import id.boytegar.moocow.db.MoocowDb
import id.boytegar.moocow.db.entity.Category
import id.boytegar.moocow.db.entity.MenuItem
import id.boytegar.moocow.helper.Async

class CategoryRepository(application: Application) {
    val db = MoocowDb.getInstance(application)
    private val categoryDao = db.DaoCategory()
    lateinit var list: LiveData<List<Category>>

    init {
        Async {
            list = categoryDao.getListCategory()
        }
    }

    fun insert(category: Category) {
        Async {
            categoryDao.insert(category)
        }

    }

    fun update(category: Category) {
        Async {
            categoryDao.update(category)
        }
    }

    fun delete(category: Category) {
        Async {
            categoryDao.delete(category)
        }
    }

    fun deleteById(id: Int) {
        Async {
            categoryDao.deleteById(id)
        }
    }

//    fun updateById(id: Int, name: String, email: String){
//        Async{
//            dataImageDao.updateById(id, name, email)
//        }
//    }

    fun getAllCategory(): LiveData<List<Category>> {
        return list
    }

}