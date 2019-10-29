package id.boytegar.moocow.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import id.boytegar.moocow.db.entity.Category
import id.boytegar.moocow.db.entity.MenuItem
import id.boytegar.moocow.repo.CategoryRepository
import id.boytegar.moocow.repo.MenuRepository

class MenuItemViewModel (application: Application): AndroidViewModel(application){
    private val categoryRepository = CategoryRepository(application)
    val menuRepository  = MenuRepository(application)

//    private var personsLiveData: LiveData<PagedList<DataAllImage>>
//    init {
//        val factory: DataSource.Factory<Int, DataAllImage> = dataImageRepository.getAllImage()
//
//        val pagedListBuilder: LivePagedListBuilder<Int, DataAllImage> = LivePagedListBuilder(factory,
//            20)
//        personsLiveData = pagedListBuilder.build()
//    }
//    fun getPersonsLiveData() = personsLiveData

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
}