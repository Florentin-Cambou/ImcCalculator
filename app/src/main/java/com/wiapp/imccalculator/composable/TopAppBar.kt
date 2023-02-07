package com.wiapp.imccalculator.composable

import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.material.TopAppBar

@Composable
fun TopAppBar(){
    TopAppBar(
        content = { Text(text = "Imc Calculator")}
    )
}