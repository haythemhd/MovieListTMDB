package com.bnp.movietmdb.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.navigation3.rememberViewModelStoreNavEntryDecorator
import androidx.navigation3.runtime.NavEntry
import androidx.navigation3.runtime.NavKey
import androidx.navigation3.runtime.rememberNavBackStack
import androidx.navigation3.runtime.rememberSavedStateNavEntryDecorator
import androidx.navigation3.ui.NavDisplay
import androidx.navigation3.ui.rememberSceneSetupNavEntryDecorator
import com.bnp.movietmdb.common.theme.ThemeViewModel
import com.bnp.movietmdb.detail.DetailScreenUi
import com.bnp.movietmdb.home.HomeScreenUi
import kotlinx.serialization.Serializable

@Serializable
data object HomeScreen : NavKey

@Serializable
data class DetailScreen(val id: Int) : NavKey

@Composable
fun NavigationRoot(
    modifier: Modifier = Modifier,
    themeViewModel: ThemeViewModel
) {
    val backStack = rememberNavBackStack(HomeScreen)
    NavDisplay(
        modifier = modifier,
        backStack = backStack,
        entryDecorators = listOf(
            rememberSavedStateNavEntryDecorator(),
            rememberViewModelStoreNavEntryDecorator(),
            rememberSceneSetupNavEntryDecorator()
        ),
        sceneStrategy = TwoPaneSceneStrategy(),
        entryProvider = { key ->
            when (key) {
                is HomeScreen -> {
                    NavEntry(
                        key = key,
                        metadata = TwoPaneScene.twoPane()
                    ) {
                        HomeScreenUi(
                            onNavigateToMovieDetail = { id ->
                                backStack.add(DetailScreen(id = id))
                            },
                            themeViewModel = themeViewModel
                        )
                    }
                }

                is DetailScreen -> {
                    NavEntry(
                        key = key,
                        metadata = TwoPaneScene.twoPane()
                    ) {
                        DetailScreenUi(
                            onBackClick = { backStack.remove(key) },
                            movieId = key.id
                        )
                    }
                }

                else -> throw RuntimeException("Invalid NavKey.")
            }
        },
    )
}