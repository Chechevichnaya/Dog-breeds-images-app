package com.example.dogsbreedapp.ui.model

import com.example.dogsbreedapp.data.model.DogImage
import com.example.dogsbreedapp.data.model.SearchWidgetState
import com.example.dogsbreedapp.data.model.SearchWidgetVisibility

data class BreedImagesScreenState(
    val searchWidgetState: SearchWidgetState = SearchWidgetState.CLOSE,
    val searchWidgetVisibility: SearchWidgetVisibility = SearchWidgetVisibility.HIDE,
    val breedImages: List<DogImage> = listOf(),
    val breed:String = ""
)