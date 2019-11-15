package id.boytegar.moocow.db.dao

import androidx.lifecycle.LiveData
import androidx.paging.DataSource
import androidx.room.*
import id.boytegar.moocow.db.entity.Transactions

@Dao
interface DaoTransactions{
    @Insert
    fun insert(transaction: Transactions)
    @Update
    fun update(transaction: Transactions)
    @Delete
    fun delete(transaction: Transactions)
    @Query("delete from transactions")
    fun deleteAll()
    @Query("select * from transactions where status=:status")
    fun getListTransactionsByStatus(status: Int): DataSource.Factory<Int, Transactions>
    @Query("select * from transactions order by id desc")
    fun getAllTransactions(): DataSource.Factory<Int, Transactions>
    @Query("delete from transactions where id=:id")
    fun  deleteById(id: Int)
    @Query("select * from transactions where id=:id")
    fun getTransactionById(id: Int): Transactions
//    @Query("update category set name=:name where id=:id ")
//    fun updateById(id: Int, name: String)
}