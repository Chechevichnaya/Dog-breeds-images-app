package com.example.dogsbreedapp.ui.viewModels

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.dogsbreedapp.data.Repository
import com.example.dogsbreedapp.data.model.DogImage
import com.example.dogsbreedapp.ui.model.FavoriteImagesScreenState
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import retrofit2.HttpException
import java.io.IOException

class FavoriteImagesViewModel(private val repo: Repository) : ViewModel() {


    private val _screenState = MutableStateFlow(FavoriteImagesScreenState())
    val screenState: StateFlow<FavoriteImagesScreenState> = _screenState.asStateFlow()

    init {
        showFavoritePhotos()
    }

    private fun showFavoritePhotos() {
        var flowListDogImages = emptyFlow<List<DogImage>>()

        viewModelScope.launch {
            viewModelScope.launch {
                val uiState = try {
                    flowListDogImages = repo.getAllFavoriteImagesFromDB()
                        .map { list ->
                            list.map { image -> image.toDogImage() }
                        }
                    UiState.Success
                } catch (e: IOException) {
                    UiState.Error
                } catch (e: HttpException) {
                    UiState.Error
                }

                flowListDogImages.collect {
                    _screenState.update { state ->
                        state.copy(favoriteImages = it, loadingStatus = uiState)
                    }
                }
            }
        }
    }

    fun deleteImageFromDB(dogImage: DogImage) {
        viewModelScope.launch {
            repo.deleteFavoriteImage(dogImage.toFavoriteImagesForDB().image_uri)
        }
    }

    fun getBreedOfDog(dogPhoto: DogImage): String {
        val regex = Regex(".*/breeds/(.*?)/.*")
        val matchResult = regex.find(dogPhoto.uri)
        val breed = matchResult?.groupValues
            ?.getOrNull(1)
            ?.replace("-", " ")
            ?.replaceFirstChar(Char::uppercase)
        return breed ?: ""
    }
}
