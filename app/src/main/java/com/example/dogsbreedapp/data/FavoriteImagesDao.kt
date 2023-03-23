package com.example.dogsbreedapp.data

import androidx.room.*
import com.example.dogsbreedapp.data.model.FavoriteImageEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface FavoriteImagesDao {

    @Insert (onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(favoriteImage: List<FavoriteImageEntity>)

    @Update
    suspend fun update(favoriteImage: List<FavoriteImageEntity>)

    @Delete
    suspend fun delete(favoriteImage: List<FavoriteImageEntity>)

    @Query("SELECT * FROM favorite_images")
    fun getAllItems(): Flow<List<FavoriteImageEntity>>
}

