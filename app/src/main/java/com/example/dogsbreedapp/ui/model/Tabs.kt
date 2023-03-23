package com.example.dogsbreedapp.ui.model

import androidx.annotation.StringRes
import com.example.dogsbreedapp.R


sealed class Tabs(val icon: Int, @StringRes val title: Int) {
    object AllBreeds : Tabs(R.drawable.many_dogs, R.string.allBreeds)
    object RandomImages : Tabs(R.drawable.one_dog, R.string.randomImages)
    object FavoriteImages : Tabs(R.drawable.favorite_dog, R.string.favorite)
}



fun getListOfScreens(): List<Tabs> {
    return listOf(Tabs.AllBreeds, Tabs.RandomImages, Tabs.FavoriteImages)
}







