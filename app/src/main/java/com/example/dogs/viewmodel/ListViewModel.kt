package com.example.dogs.viewmodel

import android.app.Application
import android.widget.Toast
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.dogs.model.DogBreed
import com.example.dogs.model.DogDatabase
import com.example.dogs.model.DogsApiService
import com.example.dogs.util.NotificationsHelper
import com.example.dogs.util.SharedPreferencesHelper
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.observers.DisposableSingleObserver
import io.reactivex.schedulers.Schedulers
import kotlinx.coroutines.launch

class ListViewModel(application: Application) : BaseViewModel(application) {

    private var prefHelper = SharedPreferencesHelper(getApplication())

    //5 minutes in nano seconds
    private var refreshTime = 5 * 60 * 1000 * 1000 * 1000L

    private val dogsService = DogsApiService()
    private val disposable = CompositeDisposable() // avoid memory leak

    val dogs = MutableLiveData<List<DogBreed>>()
    val dogsLoadError = MutableLiveData<Boolean>()
    val loading = MutableLiveData<Boolean>()

    fun refresh() {
        // check update time
        val updateTime = prefHelper.getUpdateTime()

        /**
         *  if last retrive less than 5 mins, get from local db.
         *  else get from remote db.
         */

        if(updateTime != null && updateTime != 0L &&
            System.nanoTime() - updateTime < refreshTime) {
            fetchFromDatabase()
        }
        else{
            fetchFromRemote()
        }
    }

    /**
     * fetch data from local db.
     * for refresh click on screen.
     */
    fun refreshByPassCache() {
        fetchFromRemote()
    }

    private fun fetchFromDatabase() {
        loading.value = true
        launch {
            val dogs = DogDatabase(getApplication()).dogDao().getAllDogs()
            dogsRetrieved(dogs)
            Toast.makeText(getApplication(), "Dog retrieved from database", Toast.LENGTH_SHORT).show()
        }
    }

    // retrive data from backend api
    private fun fetchFromRemote(){
        loading.value = true // set loading flag true

        // use a background thread
        disposable.add(
            dogsService.getDogs()
                .subscribeOn(Schedulers.newThread()) // background data retrieve thread
                .observeOn(AndroidSchedulers.mainThread()) // main Thread
                .subscribeWith(object : DisposableSingleObserver<List<DogBreed>>(){

                    override fun onSuccess(dogList: List<DogBreed>) {
                        // After retreve info from endpoint db, store in local db
                        storeDogsLocally(dogList)
                        Toast.makeText(getApplication(), "Dog retrieved from endpoint", Toast.LENGTH_SHORT).show()
                        NotificationsHelper(getApplication()).createNotification()
                    }

                    override fun onError(e: Throwable) {
                        dogsLoadError.value = true
                        loading.value = false
                        e.printStackTrace()
                    }
                })
        )
    }

    private fun dogsRetrieved(dogList: List<DogBreed>){
        dogs.value = dogList
        dogsLoadError.value = false
        loading.value = false
    }

    private fun storeDogsLocally(list: List<DogBreed>){
        launch{
            // delete all info first
            val dao = DogDatabase(getApplication()).dogDao()
            dao.deleteAllDogs()
            val result = dao.insertAll(*list.toTypedArray())
            var i = 0
            while(i < list.size){
                list[i].uuid = result[i].toInt()
                ++i
            }
            dogsRetrieved(list)
        }
        prefHelper.saveUpdateTime(System.nanoTime())
    }

    // Life cycle managment
    override fun onCleared() {
        super.onCleared()
        disposable.clear()
    }
}