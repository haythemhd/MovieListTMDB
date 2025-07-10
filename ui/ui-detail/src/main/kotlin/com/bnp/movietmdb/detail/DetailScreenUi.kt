package com.bnp.movietmdb.detail

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview


@Composable
fun DetailScreenUi(

){
    Box(modifier = Modifier.fillMaxSize()){
        Text(
            text = "DetailScreenUi"
        )
    }
}



@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    DetailScreenUi()
}