package com.example.dogsbreedapp.data

import com.example.dogsbreedapp.data.model.FavoriteImageEntity
import kotlinx.coroutines.flow.Flow

class Repository(private val favoriteImagesDao: FavoriteImagesDao) {
    fun getAllFavoriteImages(): Flow<List<FavoriteImageEntity>> = favoriteImagesDao.getAllItems()

    suspend fun insertFavoriteImages(images:Set<FavoriteImageEntity>) = favoriteImagesDao.insert(images)
    suspend fun updateFavoriteImages(images:Set<FavoriteImageEntity>) = favoriteImagesDao.update(images)
    suspend fun deleteFavoriteImages(images:Set<FavoriteImageEntity>) = favoriteImagesDao.delete(images)
}


