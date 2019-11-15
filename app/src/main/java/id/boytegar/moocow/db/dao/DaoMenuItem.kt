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

    @Query("update menu_item set name=:name, descipt=:desc, price=:price, price_discount=:priceDiskon, discount=:diskon1, avail=:avail, cat_id=:catId  where id=:id ")
    fun updateMenuItemById(
        id: Int,
        name: String,
        desc: String,
        price: Double,
        priceDiskon: Double,
        diskon1: Int,
        avail: Int,
        catId: Int
    )
    @Query("SELECT * from menu_item WHERE name LIKE :query")
    fun searchMenu(query: String): DataSource.Factory<Int, MenuItem>
    @Query("SELECT * from menu_item where discount=1")
    fun getDiskonMenu(): DataSource.Factory<Int, MenuItem>
    @Query("SELECT * from menu_item where cat_id=:cat_id")
    fun getByCategory(cat_id: Int): DataSource.Factory<Int, MenuItem>
}