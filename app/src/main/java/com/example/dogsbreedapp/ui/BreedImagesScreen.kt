package com.example.dogsbreedapp.ui

import android.annotation.SuppressLint
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*

import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.example.dogsbreedapp.R
import com.example.dogsbreedapp.data.model.DogImage
import com.example.dogsbreedapp.ui.model.BreedImagesScreenState
import com.example.dogsbreedapp.ui.viewModels.BreedImagesViewModel
import com.example.dogsbreedapp.ui.viewModels.UiState
import org.koin.androidx.compose.koinViewModel
import org.koin.core.parameter.parametersOf


@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Composable
fun BreedImagesScreen(
    modifier: Modifier = Modifier,
    arg: String,
    viewModelBreedImages: BreedImagesViewModel = koinViewModel {
        parametersOf(arg)
    }

) {
    val screenState by viewModelBreedImages.screenState.collectAsState()

    Scaffold(
        topBar = {
            TopBarAppWithImages(
                screenTitle = arg,
            )
        }
    ) {
        when (screenState.loadingStatus) {
            is UiState.Loading -> LoadingScreen(modifier = modifier)
            is UiState.Success -> ResultScreen(
                screenState = screenState,
                viewModelBreedImages = viewModelBreedImages
            )
            is UiState.Error -> ErrorScreen(modifier = modifier)
        }
    }
}

@Composable
private fun ResultScreen(
    screenState: BreedImagesScreenState,
    viewModelBreedImages: BreedImagesViewModel
) {
    if (screenState.breedImages.isEmpty()) {
        NoPhoto(text = stringResource(id = R.string.noDogPhoto))
    } else {
        PhotosGridScreen(
            photos = screenState.breedImages,
            onClickFavorite = { favoriteState, dogPhoto ->
                viewModelBreedImages.changeDogFavoriteStatus(favoriteState, dogPhoto)
                viewModelBreedImages.updateDB(
                    favoriteState,
                    dogPhoto
                )
            }
        )
    }
}

@Composable
fun NoPhoto(text: String, modifier: Modifier = Modifier) {
    Box(
        contentAlignment = Alignment.Center,
        modifier = Modifier.fillMaxSize()
    ) {
        Text(
            text = text,
            textAlign = TextAlign.Center,
        )
    }
}


@Composable
fun PhotosGridScreen(
    photos: List<DogImage>,
    onClickFavorite: (Boolean, DogImage) -> Unit,
    modifier: Modifier = Modifier
) {
    LazyVerticalGrid(
        columns = GridCells.Adaptive(150.dp),
        modifier = modifier.fillMaxWidth(),
        contentPadding = PaddingValues(4.dp)
    ) {
        items(items = photos, key = { photo -> photo.uri }) { photo ->
            DogPhotoWithFavoriteButton(
                photo = photo,
                onClickFavorite = onClickFavorite
            )
        }
    }
}

@Composable
fun FavoriteButton(
    isFavorite: Boolean,
    onClickFavorite: (Boolean) -> Unit,
    modifier: Modifier = Modifier
) {
    Box(
        modifier = Modifier.padding(12.dp)
    )
    {
        IconToggleButton(
            checked = isFavorite,
            onCheckedChange = { onClickFavorite(it) },
            modifier = Modifier
                .size(24.dp)
                .align(Alignment.TopEnd)
        )
        {
            Icon(
                painter = if (isFavorite) {
                    painterResource(id = R.drawable.full_heart)
                } else {
                    painterResource(id = R.drawable.empty_heart)
                },
                contentDescription = null
            )
        }
    }
}

@Composable
fun DogPhotoWithFavoriteButton(
    photo: DogImage,
    modifier: Modifier = Modifier,
    onClickFavorite: (Boolean, DogImage) -> Unit
) {
    Card(
        modifier = modifier
            .padding(4.dp)
            .fillMaxWidth()
            .aspectRatio(1f),
        elevation = 8.dp
    ) {
        DogPhoto(photo = photo, onImageClicked = {}, enabledClickOnImage = false)
        FavoriteButton(
            isFavorite = photo.favorite,
            onClickFavorite = {
                onClickFavorite(it, photo)
            }
        )
    }
}

@Composable
fun DogPhoto(
    photo: DogImage,
    onImageClicked: () -> Unit,
    enabledClickOnImage: Boolean
) {
    AsyncImage(
        model = ImageRequest.Builder(context = LocalContext.current)
            .data(photo.uri)
            .crossfade(true)
            .build(),
        contentScale = ContentScale.Crop,
        contentDescription = null,
        modifier = Modifier
            .fillMaxSize()
            .clickable(enabled = enabledClickOnImage, onClick = onImageClicked)
    )
}


