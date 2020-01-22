package com.example.dogs.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName

// data class doesn't necessary need a body
@Entity
data class Pokemon(

    @ColumnInfo(name = "pokemonId") // column name stored in db
    @SerializedName("id") // serialized with the name in json
    val pokemonId: String?,

    @ColumnInfo(name = "name")
    @SerializedName("name")
    val name: String?,

    @ColumnInfo(name = "hp")
    @SerializedName("hp")
    val hp: String?,

    @ColumnInfo(name = "attack")
    @SerializedName("attack")
    val attack: String?,

    @ColumnInfo(name = "defense")
    @SerializedName("defense")
    val defense: String?,

    @ColumnInfo(name = "sp_attack")
    @SerializedName("sp_attack")
    val spAttack: String?,

    @ColumnInfo(name = "sp_defense")
    @SerializedName("sp_defense")
    val spDefense: String?,

    @ColumnInfo(name = "speed")
    @SerializedName("speed")
    val speed: String?,

    @ColumnInfo(name = "url")
    @SerializedName("url")
    val url: String?
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