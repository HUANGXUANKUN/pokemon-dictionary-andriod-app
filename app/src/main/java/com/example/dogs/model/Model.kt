package com.example.dogs.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName

// data class doesn't necessary need a body
@Entity
data class DogBreed(

    @ColumnInfo(name = "breed_id") // column name stored in db
    @SerializedName("id") // serialized with the name in json
    val breedId: String?,

    @ColumnInfo(name = "dog_name")
    @SerializedName("name")
    val dogBreed: String?,

    @ColumnInfo(name = "breed_group")
    @SerializedName("type")
    val breedGroup: String?,

    @ColumnInfo(name = "life_span")
    @SerializedName("hp")
    val lifeSpan: String?,

    @ColumnInfo(name = "bred_for")
    @SerializedName("attack")
    val bredFor: String?,

    @ColumnInfo(name = "temperament")
    @SerializedName("defense")
    val temperament: String?,

    @ColumnInfo(name = "sp_attack")
    @SerializedName("sp_attack")
    val spAttack: String?,

    @ColumnInfo(name = "sp_defense")
    @SerializedName("sp_defense")
    val spDefense: String?,

    @ColumnInfo(name = "speed")
    @SerializedName("speed")
    val speed: String?,

    @ColumnInfo(name = "dog_url")
    @SerializedName("url")
    val imageUrl: String?
) {
    // define primary key in the body
    @PrimaryKey(autoGenerate = true)
    var uuid: Int = 0
}

data class DogPalette(var color: Int)

//data class SmsInfo(
//    var to: String,
//    var text: String,
//    var imageUrl: String
//)