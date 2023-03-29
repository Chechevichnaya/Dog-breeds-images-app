package com.example.dogsbreedapp.ui

//import com.example.dogsbreedapp.ui.model.Screens
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.dogsbreedapp.ui.model.BreedsImages
import com.example.dogsbreedapp.ui.model.Tabs
import com.example.dogsbreedapp.ui.theme.DogsBreedAppTheme


class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            DogsBreedAppTheme {
                DogBreedApp()
            }
        }
    }
}

@Composable
fun DogBreedApp(
    modifier: Modifier = Modifier,
) {
    val navController = rememberNavController()
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute =
        navBackStackEntry?.destination?.route ?: Tabs.AllBreeds.title.toString()
    val currentDestination = navBackStackEntry?.destination

    Scaffold(
        bottomBar = {
            BottomNavigationMenuApp(
                currentDestination,
                navController
            )
        }
    ) { innerPadding ->
        NavHost(
            navController = navController,
            startDestination = Tabs.AllBreeds.title.toString(),
            modifier = Modifier.padding(innerPadding)
        ) {
            composable(Tabs.AllBreeds.title.toString()) {
                AllBreedsScreen(
                    onClickBreedButton = {
                        navController.navigate(
                            BreedsImages().title + ("/$it")
                        )
                    })
            }
            composable(BreedsImages().title + ("/{chosenBreed}")) { backStackEntry ->
                val arg = backStackEntry.arguments?.getString("chosenBreed") ?: ""
                BreedImagesScreen(
                    arg = arg,
                    navigateUp = { navController.navigateUp() })
            }
            composable(Tabs.RandomImages.title.toString()) {
                RandomImagesScreen(navigateUp = { navController.navigateUp() })
            }
            composable(Tabs.FavoriteImages.title.toString()) {
                FavoriteImagesScreen(navigateUp = { navController.navigateUp() })
            }
        }


    }
}



