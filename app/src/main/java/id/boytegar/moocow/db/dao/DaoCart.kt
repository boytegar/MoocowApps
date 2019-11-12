package id.boytegar.moocow.db.dao

import androidx.lifecycle.LiveData
import androidx.room.*
import id.boytegar.moocow.db.entity.Cart

@Dao
interface DaoCart{
    @Insert
    fun insert(cart: Cart)
    @Update
    fun update(cart: Cart)
    @Delete
    fun delete(cart: Cart)
    @Query("delete from cart")
    fun deleteAll()
    @Query("select * from cart")
    fun getListCart(): LiveData<List<Cart>>
    @Query("delete from cart where id=:id")
    fun  deleteById(id: Int)
    @Query("select count(*) from cart")
    fun  getCountCart(): LiveData<Int>
    @Query("select sum(quantity) from cart")
    fun getTotalQuantity(): LiveData<Int>
    @Query("select * from cart where id_menu=:id")
    fun checkItemCart(id: Int):Boolean
    @Query("select * from cart where id_menu=:id")
    fun getItemCartById(id: Int): Cart
    @Query("update cart set quantity=:quantity where id=:id ")
    fun updateById(id: Int, quantity: Int)
}