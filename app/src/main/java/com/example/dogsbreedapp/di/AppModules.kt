package com.example.dogsbreedapp.di

import com.example.dogsbreedapp.data.Repository
import com.example.dogsbreedapp.data.model.Breed
import com.example.dogsbreedapp.data.model.FavoriteImagesDataBase
import com.example.dogsbreedapp.ui.viewModels.AllBreedsViewModel
import com.example.dogsbreedapp.ui.viewModels.BreedImagesViewModel
import com.example.dogsbreedapp.ui.viewModels.RandomImagesViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module


val daoModule = module {
    single { FavoriteImagesDataBase.getDatabase(get()) }
    single { get<FavoriteImagesDataBase>().favoriteImagesDao() }
}

val repoModule = module {
    factory { Repository(get()) }
}

val viewModelModule = module {
    viewModel { AllBreedsViewModel(get()) }
    viewModel { params -> BreedImagesViewModel(breedName = params.get(), repo = get()) }
    viewModel { RandomImagesViewModel(get()) }
}