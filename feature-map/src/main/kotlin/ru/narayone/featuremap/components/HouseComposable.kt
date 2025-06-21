package ru.narayone.featuremap.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

@Composable
fun HouseComposable(
    id: String,
    color: Color = Color(0xFF6A9EDA),
    onClick: (String) -> Unit
) {
    Box(
        modifier = Modifier
            .size(60.dp)
            .background(color)
            .clickable { onClick(id) }
    )
} 