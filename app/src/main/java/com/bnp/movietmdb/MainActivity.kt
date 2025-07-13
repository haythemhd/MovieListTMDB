package com.bnp.movietmdb

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.ui.Modifier
import com.bnp.movietmdb.common.theme.AppTheme
import com.bnp.movietmdb.navigation.NavigationRoot
import dagger.hilt.android.AndroidEntryPoint
import androidx.hilt.navigation.compose.hiltViewModel
import com.bnp.movietmdb.common.theme.ThemeViewModel

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            val themeViewModel: ThemeViewModel = hiltViewModel()
            AppTheme(themeViewModel = themeViewModel) {
                NavigationRoot(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(),
                    themeViewModel = themeViewModel
                )

            }
        }
    }
}