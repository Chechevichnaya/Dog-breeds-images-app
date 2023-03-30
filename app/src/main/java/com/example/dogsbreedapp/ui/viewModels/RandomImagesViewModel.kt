package com.example.dogsbreedapp.ui.viewModels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.dogsbreedapp.data.Repository
import com.example.dogsbreedapp.data.model.DogImage
import com.example.dogsbreedapp.data.network.DogsBreedApi
import com.example.dogsbreedapp.data.network.DogsBreedApiService
import com.example.dogsbreedapp.ui.model.RandomImagesScreenState
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import retrofit2.HttpException
import java.io.IOException

class RandomImagesViewModel(private val repo: Repository) : ViewModel() {

    private val apiService: DogsBreedApiService = DogsBreedApi.retrofitService

    private val _screenState = MutableStateFlow(RandomImagesScreenState())
    val screenState: StateFlow<RandomImagesScreenState> = _screenState.asStateFlow()

init {
    getRandomImages()
}

    private fun getRandomImages() {
        var images = setOf<DogImage>()
        viewModelScope.launch {
            val uiState = try {
                images = getRandomImagesFromApi().toSet()
                UiState.Success
            } catch (e: IOException) {
                UiState.Error
            } catch (e: HttpException) {
                UiState.Error
            }

            updateFavoriteStatusInListWithDogImages(images, uiState)

        }
    }

    private suspend fun updateFavoriteStatusInListWithDogImages(
        images: Set<DogImage>,
        uiState: UiState
    ) {
        val allBreedImageFlow = flowOf(images)

        val favoriteImagesFlow =
            repo.getAllFavoriteImagesFromDB()
                .map { list ->
                    list.map { image -> image.toDogImage() }
                }

        favoriteImagesFlow
            .combine(allBreedImageFlow) { favoriteImages, allImages ->
                allImages.map { image ->
                    image.copy(favorite = favoriteImages.contains(image))
                }
            }
            .collect {
                _screenState.update { state ->
                    state.copy(images =  it, loadingStatus = uiState)
                }
            }
    }

    private suspend fun getRandomImagesFromApi():List<DogImage>{
         return apiService.getRandomImages().toDogImages()
     }

    fun updateFavoriteState(newState: Boolean, dogPhoto: DogImage) {
        _screenState.update { state ->
            val breedImages = state.images.map { image ->
                if (image == dogPhoto) {
                    image.copy(favorite = newState)
                } else {
                    image
                }
            }
            state.copy(images = breedImages)
        }
    }
}
