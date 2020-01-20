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
    suspend fun insertAll(vararg dogs: DogBreed): List<Long>

    /**
     *
     */
    @Query("Select * FROM dogbreed")
    suspend fun getAllDogs():List<DogBreed>

    @Query("SELECT * FROM dogbreed WHERE uuid = :dogId")
    suspend fun getDog(dogId: Int): DogBreed

    /**
     * delete every entity in db dogbreed
     */
    @Query("DELETE FROM dogbreed")
    suspend fun deleteAllDogs()

}