package com.bnp.movietmdb.navigation

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.adaptive.ExperimentalMaterial3AdaptiveApi
import androidx.compose.material3.adaptive.currentWindowAdaptiveInfo
import androidx.compose.material3.windowsizeclass.ExperimentalMaterial3WindowSizeClassApi
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation3.runtime.NavEntry
import androidx.navigation3.ui.Scene
import androidx.navigation3.ui.SceneStrategy
import androidx.window.core.layout.WindowSizeClass.Companion.WIDTH_DP_MEDIUM_LOWER_BOUND

class TwoPaneScene<T : Any>(
    override val key: Any,
    override val previousEntries: List<NavEntry<T>>,
    val firstEntry: NavEntry<T>,
    val secondEntry: NavEntry<T>
) : Scene<T> {
    override val entries: List<NavEntry<T>> = listOf(firstEntry, secondEntry)
    override val content: @Composable (() -> Unit) = {
        Row(modifier = Modifier.fillMaxSize()) {
            Column(modifier = Modifier.weight(0.5f)) {
                firstEntry.Content()
            }
            Column(modifier = Modifier.weight(0.5f)) {
                secondEntry.Content()
            }
        }
    }

    companion object {
        internal const val TWO_PANE_KEY = "TwoPane"
        fun twoPane() = mapOf(TWO_PANE_KEY to true)
    }
}

class TwoPaneSceneStrategy<T : Any> : SceneStrategy<T> {
    @OptIn(
        ExperimentalMaterial3AdaptiveApi::class,
        ExperimentalMaterial3WindowSizeClassApi::class
    ) // Opt-in for adaptive and window size class APIs
    @Composable
    override fun calculateScene(
        entries: List<NavEntry<T>>,
        onBack: (Int) -> Unit
    ): Scene<T>? {

        val windowSizeClass = currentWindowAdaptiveInfo().windowSizeClass
        if (!windowSizeClass.isWidthAtLeastBreakpoint(WIDTH_DP_MEDIUM_LOWER_BOUND)) {
            return null
        }

        val lastTwoEntries = entries.takeLast(2)
        return if (lastTwoEntries.size == 2
            && lastTwoEntries.all { it.metadata.containsKey(TwoPaneScene.TWO_PANE_KEY) }
        ) {
            val firstEntry = lastTwoEntries.first()
            val secondEntry = lastTwoEntries.last()

            val sceneKey = Pair(firstEntry.contentKey, secondEntry.contentKey)

            TwoPaneScene(
                key = sceneKey,
                previousEntries = entries.dropLast(1),
                firstEntry = firstEntry,
                secondEntry = secondEntry
            )

        } else {
            null
        }
    }
}
