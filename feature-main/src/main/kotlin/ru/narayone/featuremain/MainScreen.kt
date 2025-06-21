package ru.narayone.featuremain

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import ru.narayone.sharedui.theme.NaRayoneTheme

@Composable
fun MainScreen(onMapClick: () -> Unit = {}) {
    NaRayoneTheme {
        Column(modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)) {
            Text(text = "Привет, мы на районе!")
            Button(onClick = { onMapClick() }) {
                Text(text = "Посмотреть карту")
            }
        }
    }
} 