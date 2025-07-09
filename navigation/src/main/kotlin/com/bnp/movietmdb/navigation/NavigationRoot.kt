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
import com.bnp.movietmdb.detail.DetailScreenUi
import com.bnp.movietmdb.home.HomeScreenUi
import kotlinx.serialization.Serializable

@Serializable
data object HomeScreen : NavKey

@Serializable
data class DetailScreen(val id: Int) : NavKey

@Composable
fun NavigationRoot(
    modifier: Modifier = Modifier
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
                        HomeScreenUi()
                    }
                }

                is DetailScreen -> {
                    NavEntry(
                        key = key,
                        metadata = TwoPaneScene.twoPane()
                    ) {
                        DetailScreenUi()
                    }
                }

                else -> throw RuntimeException("Invalid NavKey.")
            }
        },
    )
}