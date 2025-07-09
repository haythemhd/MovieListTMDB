package com.bnp.movietmdb.home

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview


@Composable
fun HomeScreenUi(){
    Box(modifier = Modifier.fillMaxSize()) {
        Text(
            text = "HomeScreenUi"
        )
    }
}





@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    HomeScreenUi()
}