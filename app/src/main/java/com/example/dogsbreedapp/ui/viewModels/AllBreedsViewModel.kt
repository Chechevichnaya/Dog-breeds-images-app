package com.example.dogsbreedapp.ui.viewModels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.dogsbreedapp.data.Repository
import com.example.dogsbreedapp.data.model.Breed
import com.example.dogsbreedapp.data.model.DogImage
import com.example.dogsbreedapp.data.model.SearchWidgetState
import com.example.dogsbreedapp.data.model.SearchWidgetVisibility
import com.example.dogsbreedapp.data.network.DogsBreedApi
import com.example.dogsbreedapp.data.network.DogsBreedApiService
import com.example.dogsbreedapp.ui.model.AllBreedsScreenState
import com.example.dogsbreedapp.ui.model.Tabs

import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import retrofit2.HttpException
import java.io.IOException


sealed interface UiState {
    object Success : UiState
    object Error : UiState
    object Loading : UiState
}


class AllBreedsViewModel(private val repo:Repository) : ViewModel() {

    private val _screenState = MutableStateFlow(AllBreedsScreenState())
    val screenState: StateFlow<AllBreedsScreenState> = _screenState.asStateFlow()


    private val apiService: DogsBreedApiService = DogsBreedApi.retrofitService


    fun updateSearchWidgetState(newValue: SearchWidgetState) {
        _screenState.update { state ->
            state.copy(
                searchWidgetState = newValue,
            )
        }

    }

    fun updateSearchWidgetVisibility(newValue: SearchWidgetVisibility) {
        _screenState.update { state ->
            state.copy(searchWidgetVisibility = newValue)
        }
    }

    fun updateSearchTextState(newText: String) {
        _screenState.update { state ->
            state.copy(searchTextState = newText)
        }
    }

    init {
        getAllBreeds()
    }


    private fun getAllBreeds() {
        var listOfBreeds = listOf<Breed>()
        viewModelScope.launch {
            val uiStateNEW = try {
                listOfBreeds = getAllBreedsNames()
                UiState.Success
            } catch (e: IOException) {
                UiState.Error
            } catch (e: HttpException) {
                UiState.Error
            }
            _screenState.update { state ->
                state.copy(
                    loadingStatus = uiStateNEW,
                    listOfBreeds = listOfBreeds,
                    originalLIstOfAllBreeds = listOfBreeds
                )
            }
        }
    }


    private suspend fun getAllBreedsNames(): List<Breed> {
        return apiService.getAllBreeds().toAllDogBreeds()
    }


    fun updateListOfBreeds() {
        val allBreeds = _screenState.value.originalLIstOfAllBreeds
        val filteredListOfDogBreeds = allBreeds.filter {
            it.name.contains(
                _screenState.value.searchTextState,
                ignoreCase = true
            )
        }
        _screenState.update { state ->
            state.copy(
                listOfBreeds = filteredListOfDogBreeds
            )
        }
    }

    fun returnAllBreedsButtons() {
        _screenState.update { state ->
            state.copy(listOfBreeds = _screenState.value.originalLIstOfAllBreeds)
        }
    }


}

