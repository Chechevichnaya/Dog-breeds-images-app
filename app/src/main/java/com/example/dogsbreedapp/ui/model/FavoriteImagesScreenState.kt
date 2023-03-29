package com.example.dogsbreedapp.ui.model

import com.example.dogsbreedapp.data.model.DogImage

data class FavoriteImagesScreenState(
    val favoriteImages: List<DogImage> = listOf()
)
