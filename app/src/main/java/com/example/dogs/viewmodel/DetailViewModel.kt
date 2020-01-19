package com.example.dogs.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.dogs.model.DogBreed

class DetailViewModel : ViewModel()  {

    val dogLiveData = MutableLiveData<DogBreed>()

    fun fetch() {
        val dog1 = DogBreed("1","Corgi","15 years", "brreGroup", "bredFor", "temperament","")
        dogLiveData.value = dog1
    }
}