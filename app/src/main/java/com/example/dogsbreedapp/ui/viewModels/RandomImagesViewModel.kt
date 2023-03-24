package com.example.dogsbreedapp.ui.viewModels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.dogsbreedapp.data.Repository
import com.example.dogsbreedapp.data.model.DogImage
import com.example.dogsbreedapp.data.network.DogsBreedApi
import com.example.dogsbreedapp.data.network.DogsBreedApiService
import com.example.dogsbreedapp.ui.model.BreedImagesScreenState
import com.example.dogsbreedapp.ui.model.RandomImagesScreenState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import retrofit2.HttpException
import java.io.IOException

class RandomImagesViewModel(val repo: Repository) : ViewModel() {

    private val apiService: DogsBreedApiService = DogsBreedApi.retrofitService

    private val _screenState = MutableStateFlow(RandomImagesScreenState())
    val screenState: StateFlow<RandomImagesScreenState> = _screenState.asStateFlow()

    init {
        getRandomImages()
    }

    private fun getRandomImages() {
        var images = listOf<DogImage>()
        viewModelScope.launch {
            val uiState = try {
                images = getRandomImagesFromApi()
                UiState.Success
            } catch (e: IOException) {
                UiState.Error
            } catch (e: HttpException) {
                UiState.Error
            }
            _screenState.update { state ->
                state.copy(images = images)
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
