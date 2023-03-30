package com.example.dogsbreedapp.ui

import android.util.Log
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.BottomNavigation
import androidx.compose.material.BottomNavigationItem
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.NavDestination
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import androidx.navigation.NavOptionsBuilder
import androidx.navigation.compose.rememberNavController

import com.example.dogsbreedapp.ui.model.getListOfScreens
import com.example.dogsbreedapp.ui.theme.DogsBreedAppTheme


@Composable
fun BottomNavigationMenuApp(
    currentDestination: NavDestination?,
    navController: NavHostController,
    modifier: Modifier = Modifier
) {
    BottomNavigation {
        getListOfScreens().forEach { screen ->
            BottomNavigationItem(
                icon = {
                    Icon(
                        painter = painterResource(id = screen.icon),
                        contentDescription = null,
                        modifier = modifier
                            .height(30.dp)
                            .padding(top = 4.dp)
                    )
                },
                label = { Text(stringResource(id = screen.title)) },
                selected = currentDestination?.hierarchy?.any { it.route == screen.title.toString() } == true,
                onClick = {
                    navController.navigate(screen.title.toString()) {
                        popUpToTop(navController)
//                        navController.graph.startDestinationRoute?.let { screen_route->
//                            popUpTo(screen_route) {
//                                inclusive = true
//                                saveState = true
//                            }
//                        }
//                        launchSingleTop = true
//                        restoreState = true
                    }
                }
            )
        }
    }
}

fun NavOptionsBuilder.popUpToTop(navController: NavController) {
    popUpTo(navController.currentBackStackEntry?.destination?.route ?: return) {
        inclusive =  true
    }
}

@Preview
@Composable
fun PreviewBottomBar() {
    DogsBreedAppTheme {
        val navController = rememberNavController()
        BottomNavigationMenuApp(
            navController = navController,
            currentDestination = null
        )
    }
}