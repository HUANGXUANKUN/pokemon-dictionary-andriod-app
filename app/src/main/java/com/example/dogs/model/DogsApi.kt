package com.example.dogs.model

import io.reactivex.Single
import retrofit2.http.GET

interface DogsApi {
//    @GET("/DevTides/DogsApi/master/dogs.json")
    @GET("HUANGXUANKUN/pokemon-Api/master/pokemon_data.json")
    fun getDogs(): Single<List<Pokemon>>
}