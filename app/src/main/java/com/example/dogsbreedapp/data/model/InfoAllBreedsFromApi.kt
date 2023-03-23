package com.example.dogsbreedapp.data.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import java.util.*


@Serializable
data class InfoAllBreedsFromApi(
    @SerialName("message") val result: Map<String, List<String>>,
    val status: String
) {
    fun toAllDogBreeds(): List<Breed> {
        return result.flatMap { (name, kindsOfBreed) ->
            if (kindsOfBreed.isEmpty()) {
                listOf(name.upperCaseFirstChar())
            } else {
                kindsOfBreed.map { kind ->
                    "${name.upperCaseFirstChar()} ${
                        kind.upperCaseFirstChar()
                    }"
                }
            }
        }.map(::Breed)
    }

    private fun String.upperCaseFirstChar(): String {
        return replaceFirstChar(Char::titlecase)
    }
}

@Serializable
data class InfoImagesFromApi(
    @SerialName("message") val result: List<String>,
    val status: String
) {
    fun toDogImages(): List<DogImage> {
        return result.map { image -> DogImage(image) }
    }
}


