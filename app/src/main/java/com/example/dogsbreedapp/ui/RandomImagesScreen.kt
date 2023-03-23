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
import com.example.dogsbreedapp.ui.viewModels.RandomImagesViewModel


@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Composable
fun RandomImagesScreen(
    modifier: Modifier = Modifier,
    navigateUp: () -> Unit,
    viewModelRandomImages: RandomImagesViewModel = viewModel()
) {
    val screenState by viewModelRandomImages.screenState.collectAsState()

    Scaffold(
        topBar = {
            TopBarApp(
                screenTitle = stringResource(id = R.string.randomImages),
                canNavigateBack = true,
                navigateUp = navigateUp,
                searchWidgetState = screenState.searchWidgetState,
                onTextChanges = {},
                onCloseClicked = {},
                onSearchClickedInOpenState = {},
                onSearchClickedInCloseState = {},
                searchWidgetVisibility = screenState.searchWidgetVisibility
            )
        }
    ) { _ ->
        PhotosGridScreen(photos = screenState.images,
            onClickFavorite = { favoriteState, dogPhoto ->
                viewModelRandomImages.updateFavoriteState(
                    favoriteState,
                    dogPhoto
                )
            })
    }

}

