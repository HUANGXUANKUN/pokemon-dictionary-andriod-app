package com.example.dogs.model

import com.google.gson.annotations.SerializedName

data class DogBreed(

    // serialized with the name in json
    @SerializedName("id")
    val breedId: String?,

    @SerializedName("name")
    val lifeSpan: String?,

    @SerializedName("life_span")
    val breedGroup: String?,

    @SerializedName("breed_group")
    val dogBreed: String?,

    @SerializedName("bred_for")
    val bredFor: String?,

    @SerializedName("temperament")
    val temperament: String?,

    @SerializedName("url")
    val imageUrl: String?
)