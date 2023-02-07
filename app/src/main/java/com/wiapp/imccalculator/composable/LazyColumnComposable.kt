package com.wiapp.imccalculator.composable

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.runtime.Composable

@Composable
fun LazyColumnComposable(paddingValues: PaddingValues){
    LazyColumn{
        item { ImcCalculatorHomeView(paddingValues = PaddingValues()) }
    }
}