package com.example.dogsbreedapp.data

import androidx.room.*
import com.example.dogsbreedapp.data.model.FavoriteImageEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface FavoriteImagesDao {

    @Insert (onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(favoriteImage: Set<FavoriteImageEntity>)

    @Update
    suspend fun update(favoriteImage: Set<FavoriteImageEntity>)

    @Delete
    suspend fun delete(favoriteImage: Set<FavoriteImageEntity>)

    @Query("SELECT * FROM favorite_images")
    fun getAllItems(): Flow<List<FavoriteImageEntity>>
}

