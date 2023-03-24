package com.example.dogsbreedapp.data.model

data class DogImage(
    val id: String,
    val favorite: Boolean = false
) {
    fun toFavoriteImagesForDB(): FavoriteImageEntity {
        val regex = Regex(".*/breeds/(.*?)/.*")
        val matchResult = regex.find(this.id)
        val breed = matchResult?.groupValues?.getOrNull(1)
        return FavoriteImageEntity(image_uri = this.id, breed = breed ?: "")
    }
}
