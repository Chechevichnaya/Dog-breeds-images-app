package com.example.dogsbreedapp.data

import androidx.room.*
import com.example.dogsbreedapp.data.model.FavoriteImageEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface FavoriteImagesDao {

    @Insert (onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(favoriteImage: FavoriteImageEntity)


    @Query ("DELETE FROM favorite_images WHERE image_uri = :imageUri")
    suspend fun deleteImageByUri(imageUri:String)


    @Query("SELECT * FROM favorite_images")
    fun getAllItems(): Flow<List<FavoriteImageEntity>>


}



