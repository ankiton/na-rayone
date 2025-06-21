package ru.narayone.sharedui.components

import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Outline
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.unit.Density
import androidx.compose.ui.unit.LayoutDirection

class WavyTopShape : Shape {
    override fun createOutline(
        size: androidx.compose.ui.geometry.Size,
        layoutDirection: LayoutDirection,
        density: Density
    ): Outline {
        val path = Path().apply {
            val width = size.width
            val height = size.height
            
            // Начинаем снизу слева
            moveTo(0f, height)
            
            // Координаты из Figma (пересчитанные в относительные):
            // Первая точка: x=0, y=460.73
            // Вторая точка: x=91.73, y=449.8  
            // Третья точка: x=171.44, y=476.83
            // Четвертая точка: x=270.19, y=543.55
            // Пятая точка: x=345.4, y=543.55
            // Шестая точка: x=393, y=528.6
            
            // Предполагаем что холст Figma примерно 393x600 пикселей
            // Пересчитываем Y координаты (в Figma Y растет вниз, у нас вверх)
            
            // Левая сторона до первой точки (y=460.73 ≈ 77% от высоты)
            lineTo(0f, height * 0.23f) // 100% - 77% = 23%
            
            // Кривая до второй точки (x=91.73≈23%, y=449.8≈75%)
            cubicTo(
                width * 0.12f, height * 0.20f,   // Контрольная точка 1
                width * 0.20f, height * 0.22f,   // Контрольная точка 2  
                width * 0.233f, height * 0.25f   // Точка 2: x=91.73/393≈0.233
            )
            
            // Кривая до третьей точки (x=171.44≈44%, y=476.83≈79%)
            cubicTo(
                width * 0.28f, height * 0.28f,   // Контрольная точка 1
                width * 0.38f, height * 0.18f,   // Контрольная точка 2
                width * 0.436f, height * 0.21f   // Точка 3: x=171.44/393≈0.436
            )
            
            // Кривая до четвертой точки (x=270.19≈69%, y=543.55≈91%) - самая глубокая
            cubicTo(
                width * 0.50f, height * 0.24f,   // Контрольная точка 1
                width * 0.62f, height * 0.08f,   // Контрольная точка 2 (поднимается)
                width * 0.687f, height * 0.09f   // Точка 4: x=270.19/393≈0.687, y инвертирован
            )
            
            // Кривая до пятой точки (x=345.4≈88%, y=543.55≈91%)
            cubicTo(
                width * 0.73f, height * 0.10f,   // Контрольная точка 1
                width * 0.82f, height * 0.08f,   // Контрольная точка 2
                width * 0.879f, height * 0.09f   // Точка 5: x=345.4/393≈0.879
            )
            
            // Кривая до шестой точки (x=393≈100%, y=528.6≈88%)
            cubicTo(
                width * 0.92f, height * 0.10f,   // Контрольная точка 1
                width * 0.96f, height * 0.15f,   // Контрольная точка 2
                width, height * 0.12f            // Точка 6: x=393/393=1.0, y инвертирован
            )
            
            // Правая сторона
            lineTo(width, height)
            
            // Замыкаем путь
            close()
        }
        
        return Outline.Generic(path)
    }
}

class WavyBackgroundShape : Shape {
    override fun createOutline(
        size: androidx.compose.ui.geometry.Size,
        layoutDirection: LayoutDirection,
        density: Density
    ): Outline {
        val path = Path().apply {
            val width = size.width
            val height = size.height
            
            // Создаем простую органическую форму для декоративных элементов
            moveTo(width * 0.2f, height * 0.3f)
            
            cubicTo(
                width * 0.4f, height * 0.15f,
                width * 0.6f, height * 0.45f,
                width * 0.8f, height * 0.3f
            )
            
            cubicTo(
                width * 0.9f, height * 0.5f,
                width * 0.7f, height * 0.7f,
                width * 0.5f, height * 0.65f
            )
            
            cubicTo(
                width * 0.3f, height * 0.6f,
                width * 0.1f, height * 0.45f,
                width * 0.2f, height * 0.3f
            )
            
            close()
        }
        
        return Outline.Generic(path)
    }
} 