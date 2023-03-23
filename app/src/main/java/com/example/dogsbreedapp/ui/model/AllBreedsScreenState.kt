package com.example.dogsbreedapp.ui.model

import com.example.dogsbreedapp.data.model.Breed
import com.example.dogsbreedapp.data.model.DogImage
import com.example.dogsbreedapp.data.model.SearchWidgetState
import com.example.dogsbreedapp.data.model.SearchWidgetVisibility
import com.example.dogsbreedapp.ui.viewModels.UiState

data class AllBreedsScreenState(
    val originalLIstOfAllBreeds:List<Breed> = listOf(),
    val listOfBreeds: List<Breed> = listOf(),
    val searchWidgetState: SearchWidgetState = SearchWidgetState.CLOSE,
    val searchWidgetVisibility: SearchWidgetVisibility = SearchWidgetVisibility.SHOW,
    val searchTextState: String = "",
    val loadingStatus: UiState = UiState.Loading,
    val images: List<DogImage> = listOf()
)


