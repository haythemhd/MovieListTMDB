package com.bnp.movietmdb.navigation

import androidx.compose.animation.ExperimentalSharedTransitionApi
import androidx.compose.animation.SharedTransitionLayout
import androidx.compose.animation.SharedTransitionScope
import androidx.compose.foundation.layout.Box
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.ProvidableCompositionLocal
import androidx.compose.runtime.compositionLocalOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.navigation3.runtime.NavKey
import androidx.navigation3.runtime.entryProvider
import androidx.navigation3.runtime.entry
import androidx.navigation3.runtime.navEntryDecorator
import androidx.navigation3.runtime.rememberNavBackStack
import androidx.navigation3.runtime.rememberSavedStateNavEntryDecorator
import androidx.navigation3.ui.LocalNavAnimatedContentScope
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

@OptIn(ExperimentalSharedTransitionApi::class)
@Composable
fun NavigationRoot(
    modifier: Modifier = Modifier,
    themeViewModel: ThemeViewModel
) {
    val backStack = rememberNavBackStack(HomeScreen)

    val localNavSharedTransitionScope: ProvidableCompositionLocal<SharedTransitionScope> =
        compositionLocalOf {
            throw IllegalStateException(
                "Unexpected access to LocalNavSharedTransitionScope. You must provide a " +
                        "SharedTransitionScope from a call to SharedTransitionLayout() or " +
                        "SharedTransitionScope()"
            )
        }

    val sharedEntryInSceneNavEntryDecorator = navEntryDecorator<NavKey> { entry ->
        with(localNavSharedTransitionScope.current) {
            Box(
                Modifier.sharedElement(
                    rememberSharedContentState(entry.contentKey),
                    animatedVisibilityScope = LocalNavAnimatedContentScope.current,
                ),
            ) {
                entry.Content()
            }
        }
    }

    val twoPaneStrategy = remember { TwoPaneSceneStrategy<Any>() }
    SharedTransitionLayout {
        CompositionLocalProvider(localNavSharedTransitionScope provides this) {
            NavDisplay(
                modifier = modifier,
                backStack = backStack,
                onBack = { backStack.removeLastOrNull() },
                entryDecorators = listOf(
                    sharedEntryInSceneNavEntryDecorator,
                    rememberSceneSetupNavEntryDecorator(),
                    rememberSavedStateNavEntryDecorator()
                ),
                sceneStrategy = twoPaneStrategy,
                entryProvider = entryProvider {
                    entry<HomeScreen>(
                        metadata = TwoPaneScene.twoPane()
                    ) {
                        HomeScreenUi(
                            onNavigateToMovieDetail = { id ->
                                val last = backStack.lastOrNull()
                                if (last is DetailScreen) {
                                    backStack.remove(last)
                                }
                                backStack.add(DetailScreen(id = id))
                            },
                            themeViewModel = themeViewModel
                        )
                    }
                    entry<DetailScreen>(
                        metadata = TwoPaneScene.twoPane()
                    ) { detail ->
                        DetailScreenUi(
                            onBackClick = { backStack.remove(detail) },
                            movieId = detail.id
                        )
                    }
                }
            )
        }
    }
}