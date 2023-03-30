package com.example.dogsbreedapp.ui

import android.annotation.SuppressLint
import androidx.compose.material.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.dogsbreedapp.R
import com.example.dogsbreedapp.ui.model.RandomImagesScreenState
import com.example.dogsbreedapp.ui.viewModels.RandomImagesViewModel
import com.example.dogsbreedapp.ui.viewModels.UiState
import org.koin.androidx.compose.koinViewModel


@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Composable
fun RandomImagesScreen(
    modifier: Modifier = Modifier,
    viewModelRandomImages: RandomImagesViewModel = koinViewModel()
) {
    val screenState by viewModelRandomImages.screenState.collectAsState()
    Scaffold(
        topBar = {
            TopBarAppWithImages(
                screenTitle = stringResource(id = R.string.randomImages),
            )
        }
    ) {
        when (screenState.loadingStatus) {
            is UiState.Loading -> LoadingScreen(modifier)
            is UiState.Success -> ResultScreen(
                screenState = screenState,
                viewModelRandomImages = viewModelRandomImages
            )
            is UiState.Error -> ErrorScreen(modifier)
        }
    }
}

@Composable
private fun ResultScreen(
    screenState: RandomImagesScreenState,
    viewModelRandomImages: RandomImagesViewModel
) {
    PhotosGridScreen(photos = screenState.images,
        onClickFavorite = { favoriteState, dogPhoto ->
            viewModelRandomImages.updateFavoriteState(
                favoriteState,
                dogPhoto
            )
        })
}

