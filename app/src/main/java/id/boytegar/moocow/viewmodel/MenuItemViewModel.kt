package id.boytegar.moocow.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import id.boytegar.moocow.repo.CategoryRepository
import id.boytegar.moocow.repo.MenuRepository

class DataImageViewModel (application: Application): AndroidViewModel(application){
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

    fun insert(dataImage: DataAllImage){
        dataImageRepository.insert(dataImage)
    }
    fun update(dataImage: DataAllImage){
        dataImageRepository.update(dataImage)
    }
    fun delete(dataImage: DataAllImage){
        dataImageRepository.delete(dataImage)
    }

    fun updatePosition(newId: Int, oldId: Int){
        dataImageRepository.updatePosition(newId, oldId)
    }

    fun  deleteById(id: Int){
        dataImageRepository.deleteById(id)
    }
    fun updateAll(list: List<DataAllImage>){

        dataImageRepository.updateAll(list)
    }

    fun insertDate(dateImage: DateImage){
        dateImageRepository.insert(dateImage)
    }

    fun getAllImages(user_id : Int): List<DataAllImage>{
        return dataImageRepository.getAllsImage(user_id)
    }

    fun getAllDate(user_id : Int): List<DateImage>{
        return dateImageRepository.getAllDate(user_id)
    }

    fun getAllUsers(): LiveData<List<User>> {
        val us = userRepository.getAllUser()
        return us
    }

}