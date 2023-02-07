package com.wiapp.imccalculator.composable

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.material.Scaffold
import androidx.compose.runtime.Composable

@Composable
fun ScaffoldComposable(){
    Scaffold(
        topBar = { TopAppBar() },
        content = { paddingValues ->  LazyColumnComposable(paddingValues = PaddingValues()) }
    )
}