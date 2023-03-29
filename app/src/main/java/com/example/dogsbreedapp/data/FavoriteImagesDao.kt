package com.example.dogsbreedapp.data

import android.util.Log
import androidx.room.*
import com.example.dogsbreedapp.data.model.FavoriteImageEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface FavoriteImagesDao {

    @Insert (onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(favoriteImage: FavoriteImageEntity)


    @Delete
    suspend fun delete(favoriteImage: FavoriteImageEntity)

    @Query("SELECT * FROM favorite_images")
    fun getAllItems(): Flow<List<FavoriteImageEntity>>
}

