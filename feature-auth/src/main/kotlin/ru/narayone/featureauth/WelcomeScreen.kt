package ru.narayone.featureauth

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.drawscope.DrawScope
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import ru.narayone.sharedui.theme.*
import ru.narayone.sharedui.components.WavyTopShape

@Composable
fun WelcomeScreen(
    onContinueClick: () -> Unit = {}
) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(
                brush = Brush.verticalGradient(
                    colors = listOf(
                        CoralPrimary,
                        CoralBackground
                    )
                )
            )
    ) {
        // Волнистые декоративные элементы на фоне
        Canvas(
            modifier = Modifier.fillMaxSize()
        ) {
            drawWavyBackgroundPatterns()
        }

        // Основное содержимое
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(bottom = 60.dp)
        ) {
            // Белая область с волнистой верхней границей
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(350.dp)
                    .align(Alignment.BottomCenter)
                    .clip(WavyTopShape())
                    .background(Color.White)
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(horizontal = 32.dp, vertical = 40.dp),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.SpaceBetween
                ) {
                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Text(
                            text = "Welcome",
                            fontSize = 32.sp,
                            fontWeight = FontWeight.Bold,
                            color = Color.Black,
                            textAlign = TextAlign.Center
                        )
                        
                        Spacer(modifier = Modifier.height(16.dp))
                        
                        Text(
                            text = "Lorem ipsum dolor sit amet consectetur.\nLorem id sit",
                            fontSize = 16.sp,
                            color = GrayText,
                            textAlign = TextAlign.Center,
                            lineHeight = 24.sp
                        )
                    }
                    
                    // Кнопка Continue - выровнена по правому краю
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.End
                    ) {
                        Button(
                            onClick = onContinueClick,
                            modifier = Modifier
                                .width(124.dp)
                                .height(36.dp),
                            colors = ButtonDefaults.buttonColors(
                                containerColor = Color.White
                            ),
                            shape = RoundedCornerShape(18.dp),
                            contentPadding = PaddingValues(horizontal = 8.dp, vertical = 0.dp)
                        ) {
                            Row(
                                verticalAlignment = Alignment.CenterVertically,
                                horizontalArrangement = Arrangement.SpaceBetween,
                                modifier = Modifier.fillMaxWidth()
                            ) {
                                Text(
                                    text = "Continue",
                                    fontSize = 14.sp,
                                    fontWeight = FontWeight.Medium,
                                    color = Color.Black
                                )
                                
                                // Круглая иконка стрелки справа
                                Box(
                                    modifier = Modifier
                                        .size(24.dp)
                                        .background(
                                            CoralIconButton,
                                            CircleShape
                                        ),
                                    contentAlignment = Alignment.Center
                                ) {
                                    Icon(
                                        painter = painterResource(R.drawable.group_13),
                                        contentDescription = "Стрелка",
                                        tint = Color.Unspecified, // Использует оригинальные цвета иконки
                                        modifier = Modifier.size(18.dp)
                                    )
                                }
                            }
                        }
                    }
                }
            }
        }
        
        // Индикатор внизу
        Box(
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .padding(bottom = 20.dp)
        ) {
            Row(
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                Box(
                    modifier = Modifier
                        .width(24.dp)
                        .height(4.dp)
                        .background(
                            Color.White,
                            RoundedCornerShape(2.dp)
                        )
                )
                Box(
                    modifier = Modifier
                        .width(8.dp)
                        .height(4.dp)
                        .background(
                            Color.White.copy(alpha = 0.5f),
                            RoundedCornerShape(2.dp)
                        )
                )
                Box(
                    modifier = Modifier
                        .width(8.dp)
                        .height(4.dp)
                        .background(
                            Color.White.copy(alpha = 0.5f),
                            RoundedCornerShape(2.dp)
                        )
                )
            }
        }
    }
}

private fun DrawScope.drawWavyBackgroundPatterns() {
    val width = size.width
    val height = size.height
    
    // Простые волнистые линии как в дизайне Figma
    val wavyLine1 = Path().apply {
        moveTo(width * 0.1f, height * 0.25f)
        
        cubicTo(
            width * 0.3f, height * 0.15f,
            width * 0.7f, height * 0.35f,
            width * 0.9f, height * 0.25f
        )
    }
    
    val wavyLine2 = Path().apply {
        moveTo(width * 0.05f, height * 0.45f)
        
        cubicTo(
            width * 0.25f, height * 0.35f,
            width * 0.75f, height * 0.55f,
            width * 0.95f, height * 0.45f
        )
    }
    
    // Рисуем простые волнистые линии
    drawPath(
        path = wavyLine1,
        color = Color.White.copy(alpha = 0.08f),
        style = androidx.compose.ui.graphics.drawscope.Stroke(width = 1.5.dp.toPx())
    )
    
    drawPath(
        path = wavyLine2,
        color = Color.White.copy(alpha = 0.06f),
        style = androidx.compose.ui.graphics.drawscope.Stroke(width = 1.dp.toPx())
    )
}

@Preview(showBackground = true)
@Composable
fun WelcomeScreenPreview() {
    NaRayoneTheme {
        WelcomeScreen()
    }
} 