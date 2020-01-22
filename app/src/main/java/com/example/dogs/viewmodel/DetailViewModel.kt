package com.example.dogs.viewmodel

import android.app.Application
import androidx.lifecycle.MutableLiveData
import com.example.dogs.model.Pokemon
import com.example.dogs.model.PokemonDatabase
import kotlinx.coroutines.launch

class DetailViewModel(application: Application) : BaseViewModel(application)  {

    val dogLiveData = MutableLiveData<Pokemon>()

    fun fetch(uuid: Int) {
        launch {
            val dog = PokemonDatabase(getApplication()).dogDao().getDog(uuid)
            dogLiveData.value = dog
        }
    }

}