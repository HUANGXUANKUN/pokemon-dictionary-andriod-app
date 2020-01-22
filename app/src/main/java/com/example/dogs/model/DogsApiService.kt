package com.example.dogs.model

import io.reactivex.Single
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory

class DogsApiService {

//    private val BASE_URL = "https://raw.githubusercontent.com"
    private val BASE_URL = "https://raw.githubusercontent.com/"

    private val api = Retrofit.Builder()
        .baseUrl(BASE_URL)
        .addConverterFactory(GsonConverterFactory.create()) // convert list of object to observable
        .addCallAdapterFactory(RxJava2CallAdapterFactory.create())  // conver from json to single
        .build()
        .create(DogsApi::class.java)

    fun getDogs(): Single<List<Pokemon>> {
        return api.getDogs()
    }

}