package id.boytegar.moocow.db.dao

import androidx.paging.DataSource
import androidx.room.*
import id.boytegar.moocow.db.entity.Catergory

@Dao
interface DaoCategory{
    @Insert
    fun insert(catergory: Catergory)
    @Update
    fun update(catergory: Catergory)
    @Delete
    fun delete(catergory: Catergory)
    @Query("delete from category")
    fun deleteAll()
    @Query("select * from category")
    fun getListUsers(): DataSource.Factory<Int, Catergory>
    @Query("delete from category where id=:id")
    fun  deleteById(id: Int)
    @Query("update category set name=:name where id=:id ")
    fun updateById(id: Int, name: String)
}