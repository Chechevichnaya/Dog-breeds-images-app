package com.example.dogsbreedapp.data

import com.example.dogsbreedapp.data.model.FavoriteImageEntity
import kotlinx.coroutines.flow.Flow

class Repository(private val favoriteImagesDao: FavoriteImagesDao) {
    fun getAllFavoriteImagesFromDB(): Flow<List<FavoriteImageEntity>> = favoriteImagesDao.getAllItems()

    suspend fun insertFavoriteImage(images:FavoriteImageEntity) = favoriteImagesDao.insert(images)
    suspend fun deleteFavoriteImage(images:FavoriteImageEntity) = favoriteImagesDao.delete(images)
}


