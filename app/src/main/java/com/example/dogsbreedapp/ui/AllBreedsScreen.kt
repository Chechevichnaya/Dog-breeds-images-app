package com.example.dogsbreedapp.ui

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Button
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.example.dogsbreedapp.R
import com.example.dogsbreedapp.data.model.Breed
import com.example.dogsbreedapp.data.model.SearchWidgetState
import com.example.dogsbreedapp.ui.viewModels.AllBreedsViewModel
import com.example.dogsbreedapp.ui.viewModels.UiState
import org.koin.androidx.compose.koinViewModel

@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Composable
fun AllBreedsScreen(
    modifier: Modifier = Modifier,
    onClickBreedButton: (String) -> Unit,
    viewModel: AllBreedsViewModel = koinViewModel()
) {
    val allBreedsScreenState by viewModel.screenState.collectAsState()
    Scaffold(
        topBar = {
            TopBarForListOfDogsBreed(
                screenTitle = stringResource(id = R.string.allBreeds),
                canNavigateBack = false,
                searchWidgetState = allBreedsScreenState.searchWidgetState,
                searchTextState = allBreedsScreenState.searchTextState,
                onTextChanges = {
                    viewModel.updateSearchTextState(newText = it)
                },
                onCloseClicked = {
                    viewModel.updateSearchWidgetState(newValue = SearchWidgetState.CLOSE)
                    viewModel.returnAllBreedsButtons()
                },
                onSearchClickedInCloseState = {
                    viewModel.updateSearchWidgetState(newValue = SearchWidgetState.OPEN)
                },
                searchWidgetVisibility = allBreedsScreenState.searchWidgetVisibility
            )
        }
    ) {
        when (allBreedsScreenState.loadingStatus) {
            is UiState.Loading -> LoadingScreen(modifier)
            is UiState.Success -> ResultScreen(
                searchText = allBreedsScreenState.searchTextState,
                updateListOfBreeds = { viewModel.updateListOfBreeds() },
                listOfBreeds = allBreedsScreenState.listOfBreeds,
                modifier = modifier,
                onClickBreedButton = onClickBreedButton
            )
            is UiState.Error -> ErrorScreen(modifier)
        }
    }
}

@Composable
fun ResultScreen(
    searchText: String,
    updateListOfBreeds: () -> Unit,
    listOfBreeds: List<Breed>,
    modifier: Modifier = Modifier,
    onClickBreedButton: (String) -> Unit
) {
    if (searchText.isNotEmpty()) {
        updateListOfBreeds()
    }
    Column(modifier) {
        ListOfBreedsButtons(
            listOfBreeds = listOfBreeds,
            onClickBreedButton = onClickBreedButton
        )
        Spacer(Modifier.height(8.dp))
    }
}

@Composable
fun ListOfBreedsButtons(
    modifier: Modifier = Modifier,
    listOfBreeds: List<Breed>,
    onClickBreedButton: (String) -> Unit
) {
    LazyColumn(
        modifier = modifier.padding(horizontal = 8.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        items(listOfBreeds) { breed ->
            DogBreedButton(
                dogBreed = breed,
                onClickButton = onClickBreedButton
            )
        }
    }
}

@Composable
fun DogBreedButton(
    modifier: Modifier = Modifier,
    dogBreed: Breed,
    onClickButton: (String) -> Unit
) {
    Button(onClick = { onClickButton(dogBreed.name) }, modifier = modifier.fillMaxWidth()) {
        Text(text = dogBreed.name)
    }
}














