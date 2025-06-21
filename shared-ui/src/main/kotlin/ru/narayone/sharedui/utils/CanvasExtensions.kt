package ru.narayone.sharedui.utils

import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.DrawScope

/**
 * Функция расширения для рисования фона на Canvas
 */
fun DrawScope.drawBackground(color: Color) {
    drawRect(
        color = color,
        topLeft = Offset.Zero,
        size = Size(size.width, size.height)
    )
}

/**
 * Функция расширения для рисования прямоугольника на Canvas
 */
fun DrawScope.drawRect(color: Color, offset: Offset, width: Float, height: Float) {
    drawRect(
        color = color,
        topLeft = offset,
        size = Size(width, height)
    )
} 