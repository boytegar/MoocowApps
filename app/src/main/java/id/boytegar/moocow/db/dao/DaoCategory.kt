package id.boytegar.moocow.db.dao

import androidx.lifecycle.LiveData
import androidx.room.*
import id.boytegar.moocow.db.entity.Category

@Dao
interface DaoCategory{
    @Insert
    fun insert(category: Category)
    @Update
    fun update(category: Category)
    @Delete
    fun delete(category: Category)
    @Query("delete from category")
    fun deleteAll()
    @Query("select * from category")
    fun getListCategory(): LiveData<List<Category>>
    @Query("delete from category where id=:id")
    fun  deleteById(id: Int)
    @Query("update category set name=:name where id=:id ")
    fun updateById(id: Int, name: String)
}