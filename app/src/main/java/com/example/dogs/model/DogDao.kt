package com.example.dogs.model

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query

/**
 * To access the database.
 */
@Dao
interface DogDao {

    /**
     * Inserts data to database and returns a list.
     *
     * Suspend to allow running in background thread,
     * thus not interrupting main thread
     *
     */
    @Insert
    suspend fun insertAll(vararg dogs: Pokemon): List<Long>

    /**
     *
     */
    @Query("Select * FROM pokemon")
    suspend fun getAllDogs():List<Pokemon>

    @Query("SELECT * FROM pokemon WHERE uuid = :dogId")
    suspend fun getDog(dogId: Int): Pokemon

    /**
     * delete every entity in db
     */
    @Query("DELETE FROM pokemon")
    suspend fun deleteAllDogs()

}