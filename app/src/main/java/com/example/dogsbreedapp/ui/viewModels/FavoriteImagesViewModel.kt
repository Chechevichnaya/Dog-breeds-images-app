package com.example.dogsbreedapp.ui.viewModels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.dogsbreedapp.data.Repository
import com.example.dogsbreedapp.data.model.DogImage
import com.example.dogsbreedapp.ui.model.FavoriteImagesScreenState
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch

class FavoriteImagesViewModel(private val repo: Repository) : ViewModel() {


    private val _screenState = MutableStateFlow(FavoriteImagesScreenState())
    val screenState: StateFlow<FavoriteImagesScreenState> = _screenState.asStateFlow()

    init {
        showFavoritePhotos()
    }

    private fun showFavoritePhotos() {
        viewModelScope.launch {
            repo.getAllFavoriteImagesFromDB()
                .map { list ->
                    list.map { image -> image.toDogImage() }
                }
                .collect {
                    _screenState.update { state ->
                        state.copy(favoriteImages = it)
                    }
                }
        }
    }

    fun deleteImageFromDB(dogImage: DogImage) {
        viewModelScope.launch {
//            repo.getDogImageId(dogImage.toFavoriteImagesForDB())
            repo.deleteFavoriteImage(dogImage.toFavoriteImagesForDB().image_uri)
        }
    }
}