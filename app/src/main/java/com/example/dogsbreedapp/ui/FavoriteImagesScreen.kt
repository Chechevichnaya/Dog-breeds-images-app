package com.example.dogsbreedapp.ui

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material.Card
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.example.dogsbreedapp.R
import com.example.dogsbreedapp.data.model.DogImage
import com.example.dogsbreedapp.ui.model.FavoriteImagesScreenState
import com.example.dogsbreedapp.ui.viewModels.FavoriteImagesViewModel
import com.example.dogsbreedapp.ui.viewModels.UiState
import org.koin.androidx.compose.koinViewModel

@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Composable
fun FavoriteImagesScreen(
    modifier: Modifier = Modifier,
    onImageClicked: (String) -> Unit,
    viewModelFavorite: FavoriteImagesViewModel = koinViewModel()
) {
    val screenState by viewModelFavorite.screenState.collectAsState()

    Scaffold(
        topBar = {
            TopBarAppWithImages(
                screenTitle = stringResource(id = R.string.favorite)
            )
        }) {
        when (screenState.loadingStatus) {
            is UiState.Loading -> LoadingScreen(modifier = modifier)
            is UiState.Success -> ResultScreen(
                screenState = screenState,
                viewModelFavorite = viewModelFavorite,
                onImageClicked = onImageClicked
            )
            is UiState.Error -> ErrorScreen(modifier = modifier)
        }
    }
}

@Composable
private fun ResultScreen(
    screenState: FavoriteImagesScreenState,
    viewModelFavorite: FavoriteImagesViewModel,
    onImageClicked: (String) -> Unit
) {
    if (screenState.favoriteImages.isEmpty()) {
        NoPhoto(text = stringResource(R.string.no_favorite_photos))
    }
    PhotosGridScreenFavoriteImages(
        photos = screenState.favoriteImages,
        onClickDeleteButton = { image -> viewModelFavorite.deleteImageFromDB(image) },
        onImageClicked = { onImageClicked(viewModelFavorite.getBreedOfDog(it)) }
    )
}

@Composable
private fun PhotosGridScreenFavoriteImages(
    modifier: Modifier = Modifier,
    photos: List<DogImage>,
    onClickDeleteButton: (DogImage) -> Unit,
    onImageClicked: (DogImage) -> Unit
) {
    LazyVerticalGrid(
        columns = GridCells.Adaptive(150.dp),
        modifier = modifier.fillMaxWidth(),
        contentPadding = PaddingValues(4.dp)
    ) {
        items(items = photos, key = { photo -> photo.uri }) { photo ->
            DogPhotoWithDeleteButton(
                dogPhoto = photo,
                onClickedToDelete = { onClickDeleteButton(photo) },
                onImageClicked = { onImageClicked(photo) })
        }
    }

}

@Composable
fun DogPhotoWithDeleteButton(
    modifier: Modifier = Modifier,
    dogPhoto: DogImage,
    onClickedToDelete: () -> Unit,
    onImageClicked: () -> Unit
) {
    Card(
        modifier = modifier
            .padding(4.dp)
            .fillMaxWidth()
            .aspectRatio(1f),
        elevation = 8.dp
    )
    {
        DogPhoto(photo = dogPhoto, onImageClicked = onImageClicked, enabledClickOnImage = true)
        DeleteButton(onClickDeleteButton = onClickedToDelete)
    }
}

@Composable
fun DeleteButton(
    modifier: Modifier = Modifier,
    onClickDeleteButton: () -> Unit,
) {
    Box(
        modifier = Modifier.padding(12.dp)
    ) {
        IconButton(
            onClick = onClickDeleteButton,
            modifier = Modifier
                .size(24.dp)
                .align(Alignment.TopEnd)
        ) {
            Icon(painter = painterResource(id = R.drawable.delete), contentDescription = null)
        }
    }

}