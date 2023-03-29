package com.example.dogsbreedapp.ui

import androidx.compose.foundation.layout.padding
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.dogsbreedapp.R
import com.example.dogsbreedapp.data.model.SearchWidgetState
import com.example.dogsbreedapp.data.model.SearchWidgetVisibility
import com.example.dogsbreedapp.ui.model.Tabs

import com.example.dogsbreedapp.ui.theme.DogsBreedAppTheme


@Composable
fun TopBarApp(
    screenTitle: String,
    canNavigateBack: Boolean,
    searchWidgetVisibility: SearchWidgetVisibility,
    searchWidgetState: SearchWidgetState,
    searchTextState: String = "",
    onTextChanges: (String) -> Unit,
    onCloseClicked: () -> Unit,
    onSearchClickedInOpenState: (String) -> Unit,
    onSearchClickedInCloseState: () -> Unit
) {
    when (searchWidgetState) {
        SearchWidgetState.CLOSE -> {
            DefaultedTopBar(
                screenTitle = screenTitle,
                canNavigateBack = canNavigateBack,
                searchWidgetVisibility = searchWidgetVisibility,
                onSearchClicked = onSearchClickedInCloseState,
            )
        }
        SearchWidgetState.OPEN -> {
            SearchAppBar(
                text = searchTextState,
                onTextChange = onTextChanges,
                onCloseClicked = onCloseClicked,
                onSearchClicked = onSearchClickedInOpenState
            )
        }
    }
}


@Composable
fun DefaultedTopBar(
    screenTitle: String,
    canNavigateBack: Boolean,
    searchWidgetVisibility: SearchWidgetVisibility,
    onSearchClicked: () -> Unit,
    modifier: Modifier = Modifier
) {
    TopAppBar(
        title = {
            if (!canNavigateBack) {
                Text(text = stringResource(id = R.string.app_name))
            } else {
                Text(screenTitle)
            }
        },
        modifier = modifier,
        actions = {
            if (searchWidgetVisibility == SearchWidgetVisibility.SHOW) {
                IconButton(
                    onClick = { onSearchClicked() }
                ) {
                    Icon(
                        painter = painterResource(id = R.drawable.find_dog),
                        contentDescription = null,
                        tint = Color.White,
                        modifier = modifier.padding(8.dp)
                    )
                }
            }
        }
    )
}

@Composable
fun TopBarAppWithImages(
    screenTitle: String,
    modifier: Modifier = Modifier
) {
    TopAppBar(
        title = { Text(screenTitle) },
        modifier = modifier
    )

}

@Preview
@Composable
fun PreviewTopBar() {
    DogsBreedAppTheme() {
        TopBarApp(
            screenTitle = "Terrier Russel",
            canNavigateBack = false,
            searchWidgetState = SearchWidgetState.CLOSE,
            searchTextState = "",
            onTextChanges = {},
            onCloseClicked = { /*TODO*/ },
            onSearchClickedInOpenState = {},
            searchWidgetVisibility = SearchWidgetVisibility.SHOW
        ) {}
    }
}