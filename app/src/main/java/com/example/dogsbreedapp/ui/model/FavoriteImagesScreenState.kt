package com.example.dogsbreedapp.ui.model

import com.example.dogsbreedapp.data.model.DogImage
import com.example.dogsbreedapp.ui.viewModels.UiState

data class FavoriteImagesScreenState(
    val favoriteImages: List<DogImage> = listOf(),
    val loadingStatus: UiState = UiState.Loading
)
