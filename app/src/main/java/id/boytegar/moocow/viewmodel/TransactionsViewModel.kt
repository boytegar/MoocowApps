package id.boytegar.moocow.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.paging.DataSource
import androidx.paging.LivePagedListBuilder
import androidx.paging.PagedList
import id.boytegar.moocow.db.entity.Transactions
import id.boytegar.moocow.repo.TransactionsRepository

class TransactionsViewModel (application: Application): AndroidViewModel(application){
    val transactionsRepository = TransactionsRepository(application)

    var filterTextAll = MutableLiveData<String>()


    fun getAllData(): LiveData<PagedList<Transactions>> {
        var personsLiveData: LiveData<PagedList<Transactions>>

        return Transformations.switchMap<String, PagedList<Transactions>>(
            filterTextAll
        ) { input ->
            if (input == null || input.equals("") || input.equals("%%")) {
                //check if the current value is empty load all data else search
                val factory: DataSource.Factory<Int, Transactions> = transactionsRepository.getListAllTransactions()
                val pagedListBuilder: LivePagedListBuilder<Int, Transactions> = LivePagedListBuilder(factory,
                    20)
                personsLiveData = pagedListBuilder.build()
                return@switchMap personsLiveData
            }else {
                val factory: DataSource.Factory<Int, Transactions> = transactionsRepository.getListTransactionsByStatus(input.toInt())
                val pagedListBuilder: LivePagedListBuilder<Int, Transactions> = LivePagedListBuilder(factory,
                    20)
                personsLiveData = pagedListBuilder.build()
                return@switchMap personsLiveData
            }

        }
    }


    fun insert(transactions: Transactions){
        transactionsRepository.insert(transactions)
    }
    fun delete(transactions: Transactions){
        transactionsRepository.delete(transactions)
    }
    fun update(transactions: Transactions){
        transactionsRepository.update(transactions)
    }

    fun insertTransactions(transactions: Transactions){
        transactionsRepository.insert(transactions)
    }

}