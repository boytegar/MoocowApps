package id.boytegar.moocow.repo

import android.app.Application
import androidx.lifecycle.LiveData
import id.boytegar.moocow.db.MoocowDb
import id.boytegar.moocow.db.entity.Cart
import id.boytegar.moocow.helper.Async

class CartRepository(application: Application) {
    val db = MoocowDb.getInstance(application)
    private val cartDao = db.DaoCart()
    lateinit var list: LiveData<List<Cart>>


    fun insert(cart: Cart) {
            cartDao.insert(cart)
    }

    fun update(cart: Cart) {
            cartDao.update(cart)
    }

    fun delete(cart: Cart) {
            cartDao.delete(cart)
    }

    fun deleteById(id: Int) {
       Async {
            cartDao.deleteById(id)
        }
    }

//    fun updateById(id: Int, name: String, email: String){
//        Async{
//            dataImageDao.updateById(id, name, email)
//        }
//    }

    fun getAllCategory(): LiveData<List<Cart>> {
        list = cartDao.getListCart()
        return list
    }

    fun getCount():LiveData<Int>{
        val data = cartDao.getCountCart()
        return data
    }

}