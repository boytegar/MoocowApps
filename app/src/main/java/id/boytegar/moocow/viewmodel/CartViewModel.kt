package id.boytegar.moocow.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import id.boytegar.moocow.db.entity.Cart
import id.boytegar.moocow.db.entity.Transactions
import id.boytegar.moocow.repo.CartRepository
import id.boytegar.moocow.repo.TransactionsRepository

class CartViewModel (application: Application): AndroidViewModel(application){
    val cartRepository = CartRepository(application)
    val transactionsRepository = TransactionsRepository(application)
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
    fun insertTransactions(transactions: Transactions){
        transactionsRepository.insert(transactions)
    }
    fun deleteAll(){
        cartRepository.deleteAll()
    }
}