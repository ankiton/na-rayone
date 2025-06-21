package ru.narayone.featureauth

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.drawscope.DrawScope
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import ru.narayone.sharedui.theme.*
import ru.narayone.sharedui.components.WavyTopShape

@Composable
fun LoginScreen(
    onLoginClick: () -> Unit = {},
    onSignUpClick: () -> Unit = {}
) {
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var rememberMe by remember { mutableStateOf(false) }
    var passwordVisible by remember { mutableStateOf(false) }

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
                    .height(570.dp)
                    .align(Alignment.BottomCenter)
                    .clip(WavyTopShape())
                    .background(Color.White)
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(horizontal = 32.dp, vertical = 40.dp),
                    horizontalAlignment = Alignment.Start
                ) {
                    // Заголовок с подчеркиванием
                    Column(
                        modifier = Modifier.padding(bottom = 40.dp)
                    ) {
                        Text(
                            text = "Sign in",
                            fontSize = 28.sp,
                            fontWeight = FontWeight.Bold,
                            color = Color.Black
                        )
                        Box(
                            modifier = Modifier
                                .width(60.dp)
                                .height(3.dp)
                                .background(CoralPrimary)
                                .padding(top = 4.dp)
                        )
                    }
                    
                    // Email поле
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(bottom = 24.dp)
                    ) {
                        Text(
                            text = "Email",
                            fontSize = 16.sp,
                            fontWeight = FontWeight.Medium,
                            color = Color.Black,
                            modifier = Modifier.padding(bottom = 8.dp)
                        )
                        
                        BasicTextField(
                            value = email,
                            onValueChange = { email = it },
                            textStyle = TextStyle(
                                fontSize = 16.sp,
                                color = Color.Black
                            ),
                            modifier = Modifier.fillMaxWidth(),
                            decorationBox = { innerTextField ->
                                Column {
                                    Box(
                                        modifier = Modifier
                                            .fillMaxWidth()
                                            .padding(vertical = 12.dp)
                                    ) {
                                        if (email.isEmpty()) {
                                            Text(
                                                text = "demo@email.com",
                                                color = GrayText,
                                                fontSize = 16.sp
                                            )
                                        }
                                        innerTextField()
                                    }
                                    Box(
                                        modifier = Modifier
                                            .fillMaxWidth()
                                            .height(1.dp)
                                            .background(GrayText.copy(alpha = 0.3f))
                                    )
                                }
                            },
                            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email),
                            singleLine = true
                        )
                    }
                    
                    // Password поле
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(bottom = 24.dp)
                    ) {
                        Text(
                            text = "Password",
                            fontSize = 16.sp,
                            fontWeight = FontWeight.Medium,
                            color = Color.Black,
                            modifier = Modifier.padding(bottom = 8.dp)
                        )
                        
                        BasicTextField(
                            value = password,
                            onValueChange = { password = it },
                            textStyle = TextStyle(
                                fontSize = 16.sp,
                                color = Color.Black
                            ),
                            modifier = Modifier.fillMaxWidth(),
                            visualTransformation = if (passwordVisible) VisualTransformation.None else PasswordVisualTransformation(),
                            decorationBox = { innerTextField ->
                                Column {
                                    Row(
                                        modifier = Modifier
                                            .fillMaxWidth()
                                            .padding(vertical = 12.dp),
                                        horizontalArrangement = Arrangement.SpaceBetween,
                                        verticalAlignment = Alignment.CenterVertically
                                    ) {
                                        Box(modifier = Modifier.weight(1f)) {
                                            if (password.isEmpty()) {
                                                Text(
                                                    text = "enter your password",
                                                    color = GrayText,
                                                    fontSize = 16.sp
                                                )
                                            }
                                            innerTextField()
                                        }
                                        Text(
                                            text = "👁",
                                            modifier = Modifier.clickable { 
                                                passwordVisible = !passwordVisible 
                                            },
                                            color = GrayText
                                        )
                                    }
                                    Box(
                                        modifier = Modifier
                                            .fillMaxWidth()
                                            .height(1.dp)
                                            .background(GrayText.copy(alpha = 0.3f))
                                    )
                                }
                            },
                            singleLine = true
                        )
                    }
                    
                    // Remember Me и Forgot Password
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(bottom = 32.dp),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Row(
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Checkbox(
                                checked = rememberMe,
                                onCheckedChange = { rememberMe = it },
                                colors = CheckboxDefaults.colors(
                                    checkedColor = CoralPrimary,
                                    uncheckedColor = GrayText.copy(alpha = 0.6f)
                                ),
                                modifier = Modifier.size(20.dp)
                            )
                            Spacer(modifier = Modifier.width(8.dp))
                            Text(
                                text = "Remember Me",
                                fontSize = 14.sp,
                                color = CoralPrimary
                            )
                        }
                        
                        Text(
                            text = "Forgot Password?",
                            fontSize = 14.sp,
                            color = CoralPrimary,
                            modifier = Modifier.clickable { /* TODO: Обработка забытого пароля */ }
                        )
                    }
                    
                    // Кнопка Login
                    Button(
                        onClick = onLoginClick,
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(56.dp),
                        colors = ButtonDefaults.buttonColors(
                            containerColor = CoralPrimary
                        ),
                        shape = RoundedCornerShape(28.dp)
                    ) {
                        Text(
                            text = "Login",
                            fontSize = 18.sp,
                            fontWeight = FontWeight.Medium,
                            color = Color.White
                        )
                    }
                    
                    Spacer(modifier = Modifier.height(24.dp))
                    
                    // Sign up ссылка
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.Center,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            text = "Don't have an Account? ",
                            fontSize = 16.sp,
                            color = GrayText
                        )
                        Text(
                            text = "Sign up",
                            fontSize = 16.sp,
                            color = CoralPrimary,
                            fontWeight = FontWeight.Medium,
                            modifier = Modifier.clickable { onSignUpClick() }
                        )
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
                        .width(8.dp)
                        .height(4.dp)
                        .background(
                            Color.White.copy(alpha = 0.5f),
                            RoundedCornerShape(2.dp)
                        )
                )
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
            }
        }
    }
}

private fun DrawScope.drawWavyBackgroundPatterns() {
    val width = size.width
    val height = size.height
    
    // Простые волнистые линии как в дизайне Figma
    val wavyLine1 = Path().apply {
        moveTo(width * 0.1f, height * 0.2f)
        
        cubicTo(
            width * 0.35f, height * 0.1f,
            width * 0.65f, height * 0.3f,
            width * 0.9f, height * 0.2f
        )
    }
    
    val wavyLine2 = Path().apply {
        moveTo(width * 0.05f, height * 0.4f)
        
        cubicTo(
            width * 0.3f, height * 0.3f,
            width * 0.7f, height * 0.5f,
            width * 0.95f, height * 0.4f
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
fun LoginScreenPreview() {
    NaRayoneTheme {
        LoginScreen()
    }
} 