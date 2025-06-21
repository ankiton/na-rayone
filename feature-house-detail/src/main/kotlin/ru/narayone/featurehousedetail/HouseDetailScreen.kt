package ru.narayone.featurehousedetail

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import ru.narayone.sharedui.theme.NaRayoneTheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HouseDetailScreen(houseId: String, onBackClick: () -> Unit) {
    NaRayoneTheme {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(Color(0xFF1E1E1E))
        ) {
            // Верхняя панель с кнопкой назад
            TopAppBar(
                title = { Text("Дом №$houseId") },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = Color(0xFF2D2D2D),
                    titleContentColor = Color.White
                ),
                navigationIcon = {
                    IconButton(onClick = onBackClick) {
                        Icon(
                            painter = painterResource(id = R.drawable.ic_arrow_back),
                            contentDescription = "Назад",
                            tint = Color.White
                        )
                    }
                }
            )

            // Основной контент
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(top = 64.dp, start = 16.dp, end = 16.dp, bottom = 16.dp)
            ) {
                // Изображение дома
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(200.dp)
                        .clip(RoundedCornerShape(16.dp))
                        .background(Color(0xFF6A9EDA))
                ) {
                    // Тут должно быть изображение, но пока заглушка
                    Text(
                        text = "Фото дома",
                        color = Color.White,
                        modifier = Modifier.align(Alignment.Center)
                    )
                }

                Spacer(modifier = Modifier.height(16.dp))

                // Информация о доме
                Card(
                    modifier = Modifier.fillMaxWidth(),
                    colors = CardDefaults.cardColors(
                        containerColor = Color(0xFF2D2D2D)
                    ),
                    shape = RoundedCornerShape(16.dp)
                ) {
                    Column(
                        modifier = Modifier.padding(16.dp)
                    ) {
                        Text(
                            text = getHouseAddressById(houseId),
                            fontSize = 18.sp,
                            fontWeight = FontWeight.Bold,
                            color = Color.White
                        )
                        
                        Spacer(modifier = Modifier.height(8.dp))
                        
                        HouseInfoRow(title = "Тип:", value = "Жилой дом")
                        HouseInfoRow(title = "Год постройки:", value = "2010")
                        HouseInfoRow(title = "Этажность:", value = "9 этажей")
                        HouseInfoRow(title = "Количество квартир:", value = "108")
                        HouseInfoRow(title = "Материал стен:", value = "Монолит-кирпич")
                    }
                }

                Spacer(modifier = Modifier.height(16.dp))

                // Дополнительная информация
                Card(
                    modifier = Modifier.fillMaxWidth(),
                    colors = CardDefaults.cardColors(
                        containerColor = Color(0xFF2D2D2D)
                    ),
                    shape = RoundedCornerShape(16.dp)
                ) {
                    Column(
                        modifier = Modifier.padding(16.dp)
                    ) {
                        Text(
                            text = "Инфраструктура",
                            fontSize = 16.sp,
                            fontWeight = FontWeight.Bold,
                            color = Color.White
                        )
                        
                        Spacer(modifier = Modifier.height(8.dp))
                        
                        InfrastructureItem(title = "Детская площадка", isAvailable = true)
                        InfrastructureItem(title = "Парковка", isAvailable = true)
                        InfrastructureItem(title = "Магазин", isAvailable = true)
                        InfrastructureItem(title = "Школа", isAvailable = false)
                        InfrastructureItem(title = "Детский сад", isAvailable = false)
                    }
                }
            }
        }
    }
}

@Composable
fun HouseInfoRow(title: String, value: String) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 4.dp),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Text(
            text = title,
            fontSize = 14.sp,
            color = Color.White.copy(alpha = 0.7f)
        )
        Text(
            text = value,
            fontSize = 14.sp,
            color = Color.White
        )
    }
}

@Composable
fun InfrastructureItem(title: String, isAvailable: Boolean) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 4.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Box(
            modifier = Modifier
                .size(12.dp)
                .background(
                    color = if (isAvailable) Color(0xFF4CAF50) else Color(0xFFE57373),
                    shape = RoundedCornerShape(50)
                )
        )
        Spacer(modifier = Modifier.width(8.dp))
        Text(
            text = title,
            fontSize = 14.sp,
            color = Color.White
        )
    }
}

// Вспомогательная функция для получения адреса дома по ID
private fun getHouseAddressById(id: String): String {
    return when (id) {
        "1" -> "ул. Лукоморье, 5"
        "2" -> "ул. Пушкина, 10"
        "3" -> "ул. Кузнечная, 7" 
        "4" -> "пр. Владимирский, 12"
        "5" -> "ул. Шпака, 22"
        "6" -> "ул. Новая, 15"
        "7" -> "ул. Демидова, 8"
        "8" -> "ул. Ленина, 3"
        else -> "Адрес неизвестен"
    }
} 