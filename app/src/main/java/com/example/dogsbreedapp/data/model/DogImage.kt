package com.example.dogsbreedapp.data.model

data class DogImage(
    val uri: String,
    val favorite: Boolean = false
) {
    fun toFavoriteImagesForDB(): FavoriteImageEntity {
        return FavoriteImageEntity(image_uri = this.uri)
    }
}

