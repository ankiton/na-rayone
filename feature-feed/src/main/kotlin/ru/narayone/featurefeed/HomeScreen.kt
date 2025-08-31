package ru.narayone.featurefeed

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Badge
import androidx.compose.material3.BadgedBox
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Icon
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material.icons.filled.AddCircle
import androidx.compose.material.icons.filled.Chat
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.Alignment
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.tooling.preview.Preview
import ru.narayone.sharedui.theme.CoralPrimary
import ru.narayone.sharedui.theme.NaRayoneTheme
import ru.narayone.sharedui.theme.GrayText

data class FeedItem(
    val id: String,
    val title: String,
    val district: String,
    val price: String,
    val imageUrl: String? = null
)

@Composable
fun HomeScreen(items: List<FeedItem>, onItemClick: (FeedItem) -> Unit = {}) {
    var query by remember { mutableStateOf("") }
    val filteredItems = remember(query, items) {
        if (query.isBlank()) items else items.filter {
            it.title.contains(query, ignoreCase = true) ||
            it.district.contains(query, ignoreCase = true) ||
            it.price.contains(query, ignoreCase = true)
        }
    }

    var selectedTab by remember { mutableStateOf(0) }

    Scaffold(
        containerColor = CoralPrimary,
        bottomBar = {
            NavigationBar(containerColor = Color(0xFF2A2A2A)) {
                NavigationBarItem(
                    selected = selectedTab == 0,
                    onClick = { selectedTab = 0 },
                    icon = { Icon(imageVector = Icons.Filled.Search, contentDescription = "Поиск", tint = if (selectedTab == 0) CoralPrimary else Color.LightGray) },
                    label = { Text("Поиск", color = if (selectedTab == 0) CoralPrimary else Color.LightGray) }
                )
                NavigationBarItem(
                    selected = selectedTab == 1,
                    onClick = { selectedTab = 1 },
                    icon = { Icon(imageVector = Icons.Filled.FavoriteBorder, contentDescription = "Избранное", tint = Color.LightGray) },
                    label = { Text("Избранное", color = Color.LightGray) }
                )
                NavigationBarItem(
                    selected = selectedTab == 2,
                    onClick = { selectedTab = 2 },
                    icon = {
                        BadgedBox(badge = { Badge() }) {
                            Icon(imageVector = Icons.Filled.AddCircle, contentDescription = "Объявления", tint = CoralPrimary)
                        }
                    },
                    label = { Text("Объявления", color = CoralPrimary) }
                )
                NavigationBarItem(
                    selected = selectedTab == 3,
                    onClick = { selectedTab = 3 },
                    icon = {
                        BadgedBox(badge = { Badge { Text("1") } }) {
                            Icon(imageVector = Icons.Filled.Chat, contentDescription = "Сообщения", tint = Color.LightGray)
                        }
                    },
                    label = { Text("Сообщения", color = Color.LightGray) }
                )
                NavigationBarItem(
                    selected = selectedTab == 4,
                    onClick = { selectedTab = 4 },
                    icon = {
                        BadgedBox(badge = { Badge { Text("7") } }) {
                            Icon(imageVector = Icons.Filled.AccountCircle, contentDescription = "Профиль", tint = Color.LightGray)
                        }
                    },
                    label = { Text("Профиль", color = Color.LightGray) }
                )
            }
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(CoralPrimary)
                .padding(12.dp)
                .padding(bottom = innerPadding.calculateBottomPadding())
        ) {
        // Top search bar
        OutlinedTextField(
            value = query,
            onValueChange = { query = it },
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 12.dp)
                .heightIn(min = 48.dp, max = 48.dp),
            placeholder = { Text(text = "Поиск по объявлениям", color = GrayText) },
            singleLine = true,
            trailingIcon = {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    if (query.isNotBlank()) {
                        androidx.compose.material3.IconButton(onClick = { query = "" }) {
                            Icon(imageVector = Icons.Filled.Close, contentDescription = "Очистить", tint = GrayText)
                        }
                    }
                    androidx.compose.material3.IconButton(onClick = { query = query.trim() }) {
                        Icon(imageVector = Icons.Filled.Search, contentDescription = "Найти", tint = GrayText)
                    }
                }
            }
        )

        LazyVerticalGrid(
            columns = GridCells.Adaptive(minSize = 180.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp),
            horizontalArrangement = Arrangement.spacedBy(12.dp),
            contentPadding = PaddingValues(bottom = 12.dp)
        ) {
            items(filteredItems, key = { it.id }) { item ->
                Card(
                    colors = CardDefaults.cardColors(containerColor = Color.White),
                    elevation = CardDefaults.cardElevation(defaultElevation = 2.dp),
                    modifier = Modifier
                        .fillMaxWidth()
                ) {
                    Column(modifier = Modifier.fillMaxWidth()) {
                        // Image placeholder area
                        Box(
                            modifier = Modifier
                                .fillMaxWidth()
                                .aspectRatio(4f / 3f)
                                .background(Color(0xFFEDEDED)),
                            contentAlignment = Alignment.Center
                        ) {
                            if (item.imageUrl.isNullOrBlank()) {
                                Text(text = "Фото отсутствует", fontSize = 12.sp, color = GrayText)
                            } else {
                                Text(text = "Фото", fontSize = 12.sp, color = GrayText)
                            }
                        }
                        Column(modifier = Modifier.padding(12.dp)) {
                            Text(text = item.title, fontSize = 16.sp, fontWeight = FontWeight.SemiBold, color = Color.Black)
                            Spacer(Modifier.height(4.dp))
                            Text(text = item.district, fontSize = 12.sp, color = GrayText)
                            Spacer(Modifier.height(6.dp))
                            Text(text = item.price, fontSize = 14.sp, fontWeight = FontWeight.Medium, color = Color.Black)
                        }
                    }
                }
            }
        }
        }
    }
}

@Preview(showBackground = true, backgroundColor = 0xFF2C75FF)
@Composable
private fun HomeScreenPreview() {
    NaRayoneTheme {
        val sample = remember { demoFeedItems() }
        HomeScreen(items = sample)
    }
}

private fun demoFeedItems(): List<FeedItem> = listOf(
    FeedItem("1", "Сдам 1-к на сутки", "Селедкина / Центр", "1 500 ₽"),
    FeedItem("2", "Продам диван", "Грызодубовой", "7 000 ₽"),
    FeedItem("3", "Репетитор математики", "Южный район", "500 ₽/час"),
    FeedItem("4", "Починка ноутбуков", "Северный", "Договорная"),
    FeedItem("5", "Услуги сантехника", "Центр", "от 800 ₽"),
    FeedItem("6", "Монтаж дверей", "Западный", "Договорная"),
    FeedItem("7", "Отдам котят в добрые руки", "Центр", "Бесплатно"),
    FeedItem("8", "Продам велосипед", "Юго-Запад", "9 500 ₽"),
    FeedItem("9", "Уборка квартир", "Северный", "от 700 ₽"),
    FeedItem("10", "Аренда гаража", "Центр", "4 000 ₽/мес"),
    FeedItem("11", "Ремонт стиралок", "Южный", "Договорная"),
    FeedItem("12", "Продаю iPhone 12", "Селедкина", "42 000 ₽"),
    FeedItem("13", "Курсы английского", "Грызодубовой", "1 000 ₽/занятие"),
    FeedItem("14", "Такси по городу", "Весь город", "от 120 ₽"),
    FeedItem("15", "Продам комод", "Северный", "3 200 ₽"),
    FeedItem("16", "Груминг собак", "Западный", "от 700 ₽"),
    FeedItem("17", "Сдам склад", "Промзона", "20 000 ₽/мес"),
    FeedItem("18", "Уроки гитары", "Южный", "600 ₽/час"),
    FeedItem("19", "Сборка мебели", "Центр", "от 1 000 ₽"),
    FeedItem("20", "Продам микроволновку", "Грызодубовой", "2 200 ₽")
)


