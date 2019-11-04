package id.boytegar.moocow.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.paging.DataSource
import androidx.paging.LivePagedListBuilder
import androidx.paging.PagedList
import id.boytegar.moocow.db.entity.Category
import id.boytegar.moocow.db.entity.MenuItem
import id.boytegar.moocow.repo.CategoryRepository
import id.boytegar.moocow.repo.MenuRepository
import android.icu.lang.UCharacter.GraphemeClusterBreak.T
import java.util.function.Function


class MenuItemViewModel (application: Application): AndroidViewModel(application){
    private val categoryRepository = CategoryRepository(application)
    val menuRepository  = MenuRepository(application)

    lateinit var teamAllList: LiveData<PagedList<MenuItem>>
    var filterTextAll = MutableLiveData<String>()

    private var personsLiveData: LiveData<PagedList<MenuItem>>
    init {
        val factory: DataSource.Factory<Int, MenuItem> = menuRepository.getAllUser()

        val pagedListBuilder: LivePagedListBuilder<Int, MenuItem> = LivePagedListBuilder(factory,
            20)
        personsLiveData = pagedListBuilder.build()
    }

    fun initAllTeams() {
        val config = (PagedList.Config.Builder())
            .setPageSize(20)
            .build()


        teamAllList = Transformations.switchMap(filterTextAll,())
    }



    fun getPersonsLiveData() = personsLiveData

    fun insertMenu(menuItem: MenuItem){
        menuRepository.insert(menuItem)
    }
    fun updateMenu(menuItem: MenuItem){
        menuRepository.update(menuItem)
    }
    fun deleteMenu(menuItem: MenuItem){
        menuRepository.delete(menuItem)
    }

    fun insertCategory(category: Category){
        categoryRepository.insert(category)
    }
   fun getListCategory(): LiveData<List<Category>>{

       return categoryRepository.getAllCategory()
   }
    fun getListMenu(): LiveData<PagedList<MenuItem>> {
        return personsLiveData
    }
}