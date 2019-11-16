package id.boytegar.moocow.repo

import android.app.Application
import androidx.paging.DataSource
import id.boytegar.moocow.db.MoocowDb
import id.boytegar.moocow.db.entity.Transactions
import id.boytegar.moocow.helper.Async

class TransactionsRepository(application: Application) {
    val db = MoocowDb.getInstance(application)
    private val transactionDao = db.DaoTransactions()


    fun insert(transactions: Transactions) {
        Async {
            transactionDao.insert(transactions)
        }

    }

    fun update(transactions: Transactions) {
        Async {
            transactionDao.update(transactions)
        }
    }

    fun delete(transactions: Transactions) {
        Async {
            transactionDao.delete(transactions)
        }
    }

    fun deleteById(id: Int) {
        Async {
            transactionDao.deleteById(id)
        }
    }

    fun getTransactionById(id: Int): Transactions {
        val trans = transactionDao.getTransactionById(id)
        return trans
    }
    fun getListAllTransactions(): DataSource.Factory<Int, Transactions>{
        val trans = transactionDao.getAllTransactions()
        return trans
    }
    fun getListTransactionsByStatus(status: Int): DataSource.Factory<Int, Transactions> {
        val trans = transactionDao.getListTransactionsByStatus(status)
        return trans
    }

    fun getTransactionByDate(date: String): List<Transactions>{
        val data = transactionDao.getTransactionByDate(date)
        return data
    }

    fun getTransactionByDateToDate(fromDate: String, toDate: String): List<Transactions>{
        val data = transactionDao.getTransactionByDateToDate(fromDate, toDate)
        return data
    }
}