package com.example.dogsbreedapp.ui.viewModels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.dogsbreedapp.data.Repository
import com.example.dogsbreedapp.data.model.DogImage
import com.example.dogsbreedapp.data.network.DogsBreedApi
import com.example.dogsbreedapp.data.network.DogsBreedApiService
import com.example.dogsbreedapp.ui.model.BreedImagesScreenState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import retrofit2.HttpException
import java.io.IOException

class BreedImagesViewModel(private val breedName: String, private val repo: Repository) :
    ViewModel() {

    private val _screenState = MutableStateFlow(BreedImagesScreenState())
    val screenState: StateFlow<BreedImagesScreenState> = _screenState.asStateFlow()

    private val apiService: DogsBreedApiService = DogsBreedApi.retrofitService

    init {
        getCurrentBreedImages(breedName)
    }


    private suspend fun getBreedImages(breedName: String): List<DogImage> {
        return apiService.getCurrentBreedImages(breedName).toDogImages()
    }

    private fun getCurrentBreedImages(breedName: String) {
        var images = listOf<DogImage>()
        viewModelScope.launch {
            val uiState = try {
                images = getBreedImages(formatBreedName(breedName))
                if (breedName.contains(" ")) {
                    val breedNameInStringImage = breedName.replace(" ", "-").lowercase()
                    images = images.filter { it.id.contains(breedNameInStringImage) }
                }
                UiState.Success
            } catch (e: IOException) {
                UiState.Error
            } catch (e: HttpException) {
                UiState.Error
            }
            _screenState.update { state ->
                state.copy(breedImages = images)
            }

        }
    }


    private fun formatBreedName(breedName: String): String {
        return breedName.replaceAfter(" ", "").trim().lowercase()
    }

    private fun String.formatForApiService(): String {
        return this.replace(" ", "-").lowercase()
    }

    fun updateFavoriteState(newState: Boolean, dogPhoto: DogImage) {
        _screenState.update { state ->
            val breedImages = state.breedImages.map { image ->
                if (image == dogPhoto) {
                    image.copy(favorite = newState)
                } else {
                    image
                }
            }
            state.copy(breedImages = breedImages)
        }
//        if (newState) {
//            addFavoriteImageToDB(dogPhoto)
//        } else {
//            deleteFavoriteImageFromDB(dogPhoto)
//        }
    }

//    private fun addFavoriteImageToDB

    private fun addFavoriteImagesToDB() {
        val favoriteImages = _screenState.value.breedImages
            .filter { it.favorite }
            .map { it.toFavoriteImagesForDB() }
            .toSet()
        viewModelScope.launch {
            repo.insertFavoriteImages(favoriteImages)
        }

    }
}

