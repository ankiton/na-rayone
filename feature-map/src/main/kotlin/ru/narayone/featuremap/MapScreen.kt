package ru.narayone.featuremap

import android.graphics.Typeface
import android.util.Log
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.gestures.detectTransformGestures
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Rect
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.drawscope.DrawScope
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.graphics.drawscope.scale
import androidx.compose.ui.graphics.drawscope.translate
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.graphics.nativeCanvas
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import kotlinx.coroutines.launch
import ru.narayone.sharedui.theme.NaRayoneTheme
import ru.narayone.sharedui.utils.drawBackground
import ru.narayone.sharedui.utils.drawRect
import androidx.compose.ui.graphics.toArgb

@Composable
fun MapScreen(onHouseClick: (String) -> Unit) {
    var selectedHouse by remember { mutableStateOf<House?>(null) }
    
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFF8BC34A)) // Зеленый фон для травы
    ) {
        IsometricDistrictMap(
            onHouseClick = { house ->
                selectedHouse = house
                onHouseClick(house.id)
            },
            selectedHouse = selectedHouse
        )
        
        // Названия улиц отображаем поверх карты
        Box(modifier = Modifier.fillMaxSize()) {
            Text(
                text = "ул. Пушкина",
                color = Color.LightGray,
                fontSize = 10.sp,
                modifier = Modifier
                    .offset(x = 150.dp, y = 290.dp)
            )
            
            Text(
                text = "ул. Владимирская",
                color = Color.LightGray,
                fontSize = 10.sp,
                modifier = Modifier
                    .offset(x = 70.dp, y = 420.dp)
            )
            
            Text(
                text = "ул. Демидова",
                color = Color.LightGray,
                fontSize = 10.sp,
                modifier = Modifier
                    .offset(x = 100.dp, y = 520.dp)
            )
        }
        
        // Отображаем информацию о доме при выборе
        selectedHouse?.let { house ->
            Card(
                modifier = Modifier
                    .align(Alignment.BottomCenter)
                    .padding(16.dp)
                    .fillMaxWidth(),
                shape = RoundedCornerShape(16.dp),
                colors = CardDefaults.cardColors(
                    containerColor = Color(0xFF2D2D2D)
                )
            ) {
                Column(
                    modifier = Modifier
                        .padding(16.dp)
                ) {
                    Text(
                        text = "Дом №${house.id}",
                        fontSize = 18.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color.White
                    )
                    Spacer(modifier = Modifier.height(4.dp))
                    Text(
                        text = house.address,
                        fontSize = 14.sp,
                        color = Color.White.copy(alpha = 0.8f)
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                    Row {
                        Box(
                            modifier = Modifier
                                .size(12.dp)
                                .background(Color(0xFF4CAF50), shape = RoundedCornerShape(50))
                        )
                        Spacer(modifier = Modifier.width(4.dp))
                        Text(
                            text = "Доступен для посещения",
                            fontSize = 12.sp,
                            color = Color.White.copy(alpha = 0.8f)
                        )
                    }
                }
            }
        }
    }
}

@Composable
fun IsometricDistrictMap(
    onHouseClick: (House) -> Unit,
    selectedHouse: House?
) {
    var scale by remember { mutableStateOf(1f) }
    var offset by remember { mutableStateOf(Offset.Zero) }

    val streets = remember {
        listOf(
            Street("ул. Владимира Селедкина", 0f),
            Street("ул. Владимира Ацуты", 500f),
            Street("ул. Бориса Стругацкого", 1000f)
        )
    }

    val houses = remember(streets) {
        val houseWidth = 80f
        val houseHeight = 70f
        val xSpacing = 100f
        val startX = 40f
        
        streets.flatMapIndexed { streetIndex, street ->
            val topY = 250f + street.yOffset
            val bottomY = 460f + street.yOffset
            val topRow = (0..8).map { i ->
                House("s${streetIndex}_t${i + 1}", "ул. Верхняя, ${2 + i * 2}", HouseType.values()[i % 3], Offset(startX + i * xSpacing, topY), houseWidth, houseHeight)
            }
            val bottomRow = (0..8).map { i ->
                House("s${streetIndex}_b${i + 1}", "ул. Нижняя, ${2 + i * 2}", HouseType.values()[(i + 1) % 3], Offset(startX + i * xSpacing, bottomY), houseWidth, houseHeight)
            }
            topRow + bottomRow
        }
    }

    Canvas(
        modifier = Modifier
            .fillMaxSize()
            .pointerInput(Unit) {
                detectTransformGestures { centroid, pan, zoom, _ ->
                    val oldScale = scale
                    val newScale = (scale * zoom).coerceIn(0.5f, 3f)
                    
                    // Restore pan and centered zoom, temporarily removing boundaries to debug the movement issue.
                    offset = (offset - centroid) * (newScale / oldScale) + centroid + pan
                    scale = newScale
                }
            }
            .pointerInput(Unit) {
                detectTapGestures(
                    onTap = { tapOffset ->
                        val transformedOffset = (tapOffset - offset) / scale
                        checkIfHouseClicked(transformedOffset, houses, onHouseClick)
                    }
                )
            }
            .graphicsLayer {
                scaleX = scale
                scaleY = scale
                translationX = offset.x
                translationY = offset.y
            }
    ) {
        // Рисуем фон - зеленую траву
        drawRect(
            color = Color(0xFF8BC34A),
            topLeft = Offset.Zero,
            size = Size(1000f, 1550f) 
        )
        
        // Рисуем дороги и деревья для всех улиц
        streets.forEach { street ->
            drawRoad(street)
            drawTreesForStreet(street.yOffset)
        }
        
        // Рисуем дома
        houses.forEach { house ->
            val isSelected = selectedHouse?.id == house.id
            
            // Рисуем дом с учетом его типа
            translate(left = house.offset.x, top = house.offset.y) {
                drawIsometricHouse(house.type, house.width, house.height, isSelected)
                
                // Если дом выбран, рисуем вокруг него обводку или выделение
                if (isSelected) {
                    drawCircle(
                        color = Color.White,
                        radius = 20f,
                        center = Offset(house.width / 2, house.height / 2),
                        style = Stroke(width = 4f)
                    )
                }
            }
        }
    }
}

private fun DrawScope.drawRoad(street: Street) {
    val roadTopY = 350f + street.yOffset
    val roadTextY = 395f + street.yOffset
    
    // Рисуем главную дорогу
    drawRect(
        color = Color(0xFFBDB9A6), // Цвет асфальта
        topLeft = Offset(0f, roadTopY),
        size = Size(size.width, 80f)
    )

    // Рисуем разметку дороги
    val dashWidth = 25f
    val dashGap = 15f
    val startX = 20f
    val centerY = roadTopY + 40f
    
    var currentX = startX
    while (currentX < size.width) {
        drawRect(
            color = Color.White,
            topLeft = Offset(currentX, centerY - 4),
            size = Size(dashWidth, 8f)
        )
        currentX += dashWidth + dashGap
    }

    val textPaint = androidx.compose.ui.graphics.Paint().asFrameworkPaint().apply {
        isAntiAlias = true
        textSize = 36f
        color = Color.DarkGray.toArgb()
        textAlign = android.graphics.Paint.Align.CENTER
        typeface = Typeface.create(Typeface.DEFAULT, Typeface.BOLD)
    }

    drawContext.canvas.nativeCanvas.drawText(
        street.name,
        size.width / 2,
        roadTextY,
        textPaint
    )
}

private fun DrawScope.drawTreesForStreet(yOffset: Float) {
    // Рисуем деревья
    val treeLocations = listOf(
        // Верхний ряд деревьев
        Offset(100f, 230f), Offset(200f, 230f), Offset(300f, 230f), Offset(400f, 230f),
        Offset(500f, 230f), Offset(600f, 230f), Offset(700f, 230f), Offset(800f, 230f), Offset(900f, 230f),
        // Нижний ряд деревьев
        Offset(100f, 440f), Offset(200f, 440f), Offset(300f, 440f), Offset(400f, 440f),
        Offset(500f, 440f), Offset(600f, 440f), Offset(700f, 440f), Offset(800f, 440f), Offset(900f, 440f)
    ).map { it.copy(y = it.y + yOffset) }
    
    treeLocations.forEach { location ->
        drawTree(location)
    }
}

private fun DrawScope.drawTree(location: Offset) {
    // Ствол дерева
    drawRect(
        color = Color(0xFF8B4513), // Коричневый
        topLeft = Offset(location.x + 15, location.y + 30),
        size = Size(10f, 30f)
    )
    
    // Крона дерева
    drawCircle(
        color = Color(0xFF2E7D32), // Темно-зеленый
        radius = 25f,
        center = Offset(location.x + 20, location.y + 20)
    )
}

private fun DrawScope.drawIsometricHouse(type: HouseType, width: Float, height: Float, isSelected: Boolean) {
    val houseWidth = width * 0.7f
    val houseHeight = height * 0.6f
    val roofHeight = height * 0.4f
    
    // Цвета для дома
    val wallColor = if (isSelected) Color(0xFFFFF9C4) else Color(0xFFF5F5DC) // Бежевый
    val wallShadowColor = if (isSelected) Color(0xFFE6E1B5) else Color(0xFFE6DCC3) // Тень для стен
    val roofColor = when(type) {
        HouseType.RED_ROOF -> Color(0xFFE53935) // Красный
        HouseType.BLACK_ROOF -> Color(0xFF424242) // Черный
        HouseType.YELLOW_ROOF -> Color(0xFFFFA000) // Желтый/оранжевый
    }
    val roofShadowColor = when(type) {
        HouseType.RED_ROOF -> Color(0xFFD32F2F) // Темно-красный
        HouseType.BLACK_ROOF -> Color(0xFF212121) // Темно-черный
        HouseType.YELLOW_ROOF -> Color(0xFFFF8F00) // Темно-оранжевый
    }
    val windowColor = Color(0xFFBBDEFB) // Светло-голубой
    
    // Рисуем основание дома (фронтальная стена)
    drawRect(
        color = wallColor,
        topLeft = Offset(0f, roofHeight),
        size = Size(houseWidth, houseHeight)
    )
    
    // Рисуем боковую стену для объемности
    val sidePath = Path().apply {
        moveTo(houseWidth, roofHeight)
        lineTo(houseWidth + houseWidth * 0.3f, roofHeight + houseHeight * 0.2f)
        lineTo(houseWidth + houseWidth * 0.3f, roofHeight + houseHeight * 1.2f)
        lineTo(houseWidth, roofHeight + houseHeight)
        close()
    }
    
    drawPath(
        path = sidePath,
        color = wallShadowColor
    )
    
    // Рисуем крышу (фронтальная часть)
    val frontRoofPath = Path().apply {
        moveTo(0f, roofHeight)
        lineTo(houseWidth / 2, 0f)
        lineTo(houseWidth, roofHeight)
        close()
    }
    
    drawPath(
        path = frontRoofPath,
        color = roofColor
    )
    
    // Рисуем боковую часть крыши для объемности
    val sideRoofPath = Path().apply {
        moveTo(houseWidth, roofHeight)
        lineTo(houseWidth / 2, 0f)
        lineTo(houseWidth / 2 + houseWidth * 0.3f, roofHeight * 0.2f)
        lineTo(houseWidth + houseWidth * 0.3f, roofHeight + houseHeight * 0.2f)
        close()
    }
    
    drawPath(
        path = sideRoofPath,
        color = roofShadowColor
    )
    
    // Рисуем дверь
    drawRect(
        color = Color(0xFF5D4037), // Коричневый
        topLeft = Offset(houseWidth * 0.4f, roofHeight + houseHeight * 0.5f),
        size = Size(houseWidth * 0.2f, houseHeight * 0.5f)
    )
    
    // Дверная ручка
    drawCircle(
        color = Color(0xFFFFD700), // Золотистый
        radius = 3f,
        center = Offset(houseWidth * 0.53f, roofHeight + houseHeight * 0.75f)
    )
    
    // Рисуем окна
    // Окно слева
    drawRect(
        color = windowColor,
        topLeft = Offset(houseWidth * 0.15f, roofHeight + houseHeight * 0.25f),
        size = Size(houseWidth * 0.15f, houseHeight * 0.25f)
    )
    
    // Крестообразная рама левого окна
    drawLine(
        color = Color(0xFF5D4037),
        start = Offset(houseWidth * 0.15f, roofHeight + houseHeight * 0.375f),
        end = Offset(houseWidth * 0.3f, roofHeight + houseHeight * 0.375f),
        strokeWidth = 2f
    )
    
    drawLine(
        color = Color(0xFF5D4037),
        start = Offset(houseWidth * 0.225f, roofHeight + houseHeight * 0.25f),
        end = Offset(houseWidth * 0.225f, roofHeight + houseHeight * 0.5f),
        strokeWidth = 2f
    )
    
    // Окно справа
    drawRect(
        color = windowColor,
        topLeft = Offset(houseWidth * 0.7f, roofHeight + houseHeight * 0.25f),
        size = Size(houseWidth * 0.15f, houseHeight * 0.25f)
    )
    
    // Крестообразная рама правого окна
    drawLine(
        color = Color(0xFF5D4037),
        start = Offset(houseWidth * 0.7f, roofHeight + houseHeight * 0.375f),
        end = Offset(houseWidth * 0.85f, roofHeight + houseHeight * 0.375f),
        strokeWidth = 2f
    )
    
    drawLine(
        color = Color(0xFF5D4037),
        start = Offset(houseWidth * 0.775f, roofHeight + houseHeight * 0.25f),
        end = Offset(houseWidth * 0.775f, roofHeight + houseHeight * 0.5f),
        strokeWidth = 2f
    )
    
    // Для домов с красной крышей или черной добавляем чердачное окно
    if (type == HouseType.RED_ROOF || type == HouseType.BLACK_ROOF) {
        val atticWindowPath = Path().apply {
            val centerX = houseWidth / 2
            val windowY = roofHeight * 0.4f
            val windowWidth = houseWidth * 0.15f
            val windowHeight = roofHeight * 0.3f
            
            moveTo(centerX - windowWidth / 2, windowY)
            lineTo(centerX + windowWidth / 2, windowY)
            lineTo(centerX + windowWidth / 2, windowY + windowHeight)
            lineTo(centerX - windowWidth / 2, windowY + windowHeight)
            close()
        }
        
        drawPath(
            path = atticWindowPath,
            color = windowColor
        )
        
        // Крестообразная рама чердачного окна
        drawLine(
            color = Color(0xFF5D4037),
            start = Offset(houseWidth / 2 - houseWidth * 0.075f, roofHeight * 0.55f),
            end = Offset(houseWidth / 2 + houseWidth * 0.075f, roofHeight * 0.55f),
            strokeWidth = 2f
        )
        
        drawLine(
            color = Color(0xFF5D4037),
            start = Offset(houseWidth / 2, roofHeight * 0.4f),
            end = Offset(houseWidth / 2, roofHeight * 0.7f),
            strokeWidth = 2f
        )
    }
    
    // Добавляем дымоход для некоторых домов
    if (type == HouseType.BLACK_ROOF || type == HouseType.RED_ROOF) {
        drawRect(
            color = Color(0xFFBF360C), // Кирпичный цвет
            topLeft = Offset(houseWidth * 0.8f, roofHeight * 0.3f),
            size = Size(houseWidth * 0.1f, roofHeight * 0.5f)
        )
    }
}

private fun checkIfHouseClicked(offset: Offset, houses: List<House>, onHouseClick: (House) -> Unit) {
    for (house in houses) {
        // Расширяем область клика для домов (делаем немного больше, чем сам дом)
        val clickArea = 30f // Дополнительная область вокруг дома для удобства клика
        
        if (offset.x >= house.offset.x - clickArea &&
            offset.x <= house.offset.x + house.width + clickArea &&
            offset.y >= house.offset.y - clickArea &&
            offset.y <= house.offset.y + house.height + clickArea
        ) {
            onHouseClick(house)
            return
        }
    }
}

enum class HouseType {
    RED_ROOF, BLACK_ROOF, YELLOW_ROOF
}

data class Street(
    val name: String,
    val yOffset: Float
)

data class House(
    val id: String,
    val address: String,
    val type: HouseType,
    val offset: Offset,
    val width: Float,
    val height: Float
) 