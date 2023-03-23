package com.example.dogsbreedapp.data.model

data class Breed(
    val name: String,
    val images:List<DogImage> = listOf()
)
