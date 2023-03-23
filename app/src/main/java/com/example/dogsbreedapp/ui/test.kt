package com.example.dogsbreedapp.ui

import android.annotation.SuppressLint
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material.icons.filled.Search
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.rememberAsyncImagePainter
import coil.compose.rememberImagePainter
import com.example.dogsbreedapp.R

@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Composable
fun DogBreedsScreen(dogBreeds: List<String>) {
    var searchText by remember { mutableStateOf("") }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(text = "Dog Breeds") }
            )
        },
        content = { padding ->
            Column {
                TextField(
                    value = searchText,
                    onValueChange = { searchText = it },
                    label = { Text("Search for a dog breed") },
                    leadingIcon = { Icon(Icons.Filled.Search, contentDescription = null) }
                )
                DogBreedsList(dogBreeds, searchText)
            }
        }
    )
}

@Composable
fun DogBreedsList(dogBreeds: List<String>, searchText: String) {
    val filteredBreeds = dogBreeds.filter {
        it.contains(searchText, ignoreCase = true)
    }
    LazyColumn {
        items(filteredBreeds) { breed ->
            Text(text = breed, style = MaterialTheme.typography.body1)
        }
    }
}

@Composable
fun ImageWithLikeButton(imageId: Int) {
    // remember whether the image has been liked or not
    val isLiked = remember { mutableStateOf(false) }

    Box(modifier = Modifier.fillMaxSize()) {
        Image(
            painter = painterResource(id = imageId),
            contentDescription = null,
            modifier = Modifier.fillMaxWidth(),
            contentScale = ContentScale.Crop
        )
        IconToggleButton(
            checked = isLiked.value,
            onCheckedChange = { isLiked.value = it },
            modifier = Modifier.padding(8.dp)
        ) {
            if (isLiked.value) {
                Icons.Filled.Favorite
            } else {
                Icons.Filled.FavoriteBorder
            }
        }
    }
}

@Composable
fun FavoriteButton(
    modifier: Modifier = Modifier,
    color: Color = Color.White
) {

    var isFavorite by remember { mutableStateOf(false) }

    IconToggleButton(
        checked = isFavorite,
        onCheckedChange = {
            isFavorite = !isFavorite
        }
    ) {
        Icon(
            tint = color,
            imageVector = if (isFavorite) {
                Icons.Filled.Favorite
            } else {
                Icons.Default.FavoriteBorder
            },
            contentDescription = null
        )
    }

}

@Composable
fun MyComponent(
    imageUrl:Int,
    modifier: Modifier = Modifier,
) {
    Box(contentAlignment = Alignment.TopEnd) {
        Image(
            contentScale = ContentScale.None,
            modifier = modifier,
            painter = painterResource(id = imageUrl),
            contentDescription = null
        )
        FavoriteButton(modifier = Modifier.padding(12.dp))
    }
}

@Preview
@Composable
fun test(){
    val dogBreeds = listOf(
        "Golden Retriever",
        "Labrador Retriever",
        "German Shepherd",
        "Bulldog",
        "Poodle",
        "Beagle",
        "Dachshund",
        "Chihuahua",
        "Siberian Husky",
        "Great Dane",
        "Boxer",
        "Pug")
    MyComponent(R.drawable.dog_451643)
}