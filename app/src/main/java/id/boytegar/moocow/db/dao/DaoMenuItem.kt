package id.boytegar.moocow.db.dao

import androidx.paging.DataSource
import androidx.room.*
import id.boytegar.moocow.db.entity.MenuItem

@Dao
interface DaoMenuItem{
    @Insert
    fun insert(menuItem: MenuItem)
    @Update
    fun update(menuItem: MenuItem)
    @Delete
    fun delete(menuItem: MenuItem)
    @Query("delete from menu_item")
    fun deleteAll()
    @Query("select * from menu_item")
    fun getListMenu(): DataSource.Factory<Int, MenuItem>
    @Query("delete from menu_item where id=:id")
    fun  deleteById(id: Int)
    @Query("update menu_item set name=:name where id=:id ")
    fun updateById(id: Int, name: String)

    @Query("SELECT * from menu_item WHERE name LIKE :query")
    fun searchMenu(query: String): DataSource.Factory<Int, MenuItem>
}