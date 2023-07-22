package com.ahr.gigihfinalproject.presentation.main

import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.BottomSheetScaffold
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.unit.dp

@ExperimentalMaterialApi
@Composable
fun MainScreen() {
    BottomSheetScaffold(
        sheetContent = { MainSheetContent() },
        sheetShape = RoundedCornerShape(topStart = 32.dp, topEnd = 32.dp),
        sheetBackgroundColor = MaterialTheme.colorScheme.surface,
        sheetPeekHeight = 72.dp
    ) {

    }
}