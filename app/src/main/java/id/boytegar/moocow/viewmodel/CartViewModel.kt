package id.boytegar.moocow.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import id.boytegar.moocow.db.entity.Cart
import id.boytegar.moocow.repo.CartRepository

class CartViewModel (application: Application): AndroidViewModel(application){
    val cartRepository = CartRepository(application)

    fun insert(cart: Cart){
        cartRepository.insert(cart)
    }
    fun delete(cart: Cart){
        cartRepository.delete(cart)
    }
    fun update(cart: Cart){
        cartRepository.update(cart)
    }

    fun getAllDataCart():LiveData<List<Cart>>{
        val data = cartRepository.getAllCart()
        return data
    }
}