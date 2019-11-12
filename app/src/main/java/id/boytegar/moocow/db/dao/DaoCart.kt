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
//    @Query("update category set name=:name where id=:id ")
//    fun updateById(id: Int, name: String)
}