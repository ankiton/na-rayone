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
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
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
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(CoralPrimary)
            .padding(12.dp)
    ) {
        LazyVerticalGrid(
            columns = GridCells.Adaptive(minSize = 180.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp),
            horizontalArrangement = Arrangement.spacedBy(12.dp),
            contentPadding = PaddingValues(bottom = 12.dp)
        ) {
            items(items, key = { it.id }) { item ->
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


