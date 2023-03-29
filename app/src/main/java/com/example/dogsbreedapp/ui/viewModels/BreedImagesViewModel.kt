package com.example.dogsbreedapp.ui.viewModels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.dogsbreedapp.data.Repository
import com.example.dogsbreedapp.data.model.DogImage
import com.example.dogsbreedapp.data.network.DogsBreedApi
import com.example.dogsbreedapp.data.network.DogsBreedApiService
import com.example.dogsbreedapp.ui.model.BreedImagesScreenState
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import retrofit2.HttpException
import java.io.IOException

class BreedImagesViewModel(private val breedName: String, private val repo: Repository) :
    ViewModel() {

    private val _screenState = MutableStateFlow(BreedImagesScreenState())
    val screenState: StateFlow<BreedImagesScreenState> = _screenState.asStateFlow()

    private val apiService: DogsBreedApiService = DogsBreedApi.retrofitService

    init {
        getCurrentBreedImages()
    }


    private suspend fun getAllKindsOfBreedImagesFromApi(breedName: String): List<DogImage> {
        return apiService.getCurrentBreedImages(breedName).toDogImages()
    }

    private fun getCurrentBreedImages() {
        var images = listOf<DogImage>()
        viewModelScope.launch {
            val uiState = try {
                images = getAllKindsOfBreedImagesFromApi(formatBreedName(breedName))
                if (breedName.contains(" ")) {
                    val currentKindOfBreed = breedName.replace(" ", "-").lowercase()
                    images = images.filter { it.uri.contains(currentKindOfBreed) }
                }
                UiState.Success
            } catch (e: IOException) {
                UiState.Error
            } catch (e: HttpException) {
                UiState.Error
            }

            updateFavoriteStatusInLIstWithDogImages(images)
        }
    }

    private suspend fun updateFavoriteStatusInLIstWithDogImages(images: List<DogImage>) {
        val allBreedImageFlow = flowOf(images)

        val favoriteImagesUris =
            repo.getAllFavoriteImagesFromDB()
                .map { list ->
                    list.map { image -> image.image_uri }
                }


        favoriteImagesUris
            .combine(allBreedImageFlow) { favoriteImages, allImages ->
                allImages.map { image ->
                    image.copy(favorite = favoriteImages.contains(image.uri))
                }
            }
            .collect {
                _screenState.update { state ->
                    state.copy(breedImages = it)
                }
            }
    }


    private fun formatBreedName(breedName: String): String {
        return breedName.replaceAfter(" ", "").trim().lowercase()
    }


    private fun addImageToDB(dogPhoto: DogImage) {
        viewModelScope.launch {
            repo.insertFavoriteImage(dogPhoto.toFavoriteImagesForDB())
        }
    }

    private fun deleteImageFromDB(dogPhoto: DogImage) {
        viewModelScope.launch {
            repo.deleteFavoriteImage(dogPhoto.toFavoriteImagesForDB().image_uri)
        }
    }

    fun updateDB(imageIsFavorite: Boolean, dogPhoto: DogImage) {
        if (imageIsFavorite) {
            addImageToDB(dogPhoto)
        } else {
            deleteImageFromDB(dogPhoto)
        }
    }

    fun changeDogFavoriteStatus(favoriteState: Boolean, dogPhoto: DogImage) {
        _screenState.update { state ->
            state.copy(breedImages = state.breedImages.map { image ->
                if (image == dogPhoto) {
                    image.copy(favorite = favoriteState)
                } else {
                    image
                }
            })
        }
    }
}

