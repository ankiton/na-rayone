package ru.narayone.featureauth

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
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
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import ru.narayone.sharedui.theme.*
import ru.narayone.sharedui.components.WavyTopShape

@Composable
fun SignUpScreen(
    onCreateAccountClick: () -> Unit = {},
    onLoginClick: () -> Unit = {}
) {
    var email by remember { mutableStateOf("") }
    var phoneNumber by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var confirmPassword by remember { mutableStateOf("") }
    var passwordVisible by remember { mutableStateOf(false) }
    var confirmPasswordVisible by remember { mutableStateOf(false) }

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
                    .height(630.dp)
                    .align(Alignment.BottomCenter)
                    .clip(WavyTopShape())
                    .background(Color.White)
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(horizontal = 32.dp, vertical = 40.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(
                        text = "Sign up",
                        fontSize = 32.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color.Black,
                        modifier = Modifier.padding(bottom = 32.dp)
                    )
                    
                    // Email поле
                    Column(
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Text(
                            text = "Email",
                            fontSize = 16.sp,
                            fontWeight = FontWeight.Medium,
                            color = Color.Black,
                            modifier = Modifier.padding(bottom = 8.dp)
                        )
                        
                        OutlinedTextField(
                            value = email,
                            onValueChange = { email = it },
                            placeholder = {
                                Text(
                                    text = "demo@email.com",
                                    color = GrayText,
                                    fontSize = 16.sp
                                )
                            },
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(56.dp),
                            shape = RoundedCornerShape(12.dp),
                            colors = OutlinedTextFieldDefaults.colors(
                                focusedBorderColor = CoralPrimary,
                                unfocusedBorderColor = Color.Gray.copy(alpha = 0.3f),
                                focusedContainerColor = Color.Transparent,
                                unfocusedContainerColor = Color.Transparent
                            ),
                            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email),
                            singleLine = true
                        )
                    }
                    
                    Spacer(modifier = Modifier.height(16.dp))
                    
                    // Phone Number поле
                    Column(
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Text(
                            text = "Phone no",
                            fontSize = 16.sp,
                            fontWeight = FontWeight.Medium,
                            color = Color.Black,
                            modifier = Modifier.padding(bottom = 8.dp)
                        )
                        
                        OutlinedTextField(
                            value = phoneNumber,
                            onValueChange = { phoneNumber = it },
                            placeholder = {
                                Text(
                                    text = "+7 000 000-0000 000",
                                    color = GrayText,
                                    fontSize = 16.sp
                                )
                            },
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(56.dp),
                            shape = RoundedCornerShape(12.dp),
                            colors = OutlinedTextFieldDefaults.colors(
                                focusedBorderColor = CoralPrimary,
                                unfocusedBorderColor = Color.Gray.copy(alpha = 0.3f),
                                focusedContainerColor = Color.Transparent,
                                unfocusedContainerColor = Color.Transparent
                            ),
                            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Phone),
                            singleLine = true
                        )
                    }
                    
                    Spacer(modifier = Modifier.height(16.dp))
                    
                    // Password поле
                    Column(
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Text(
                            text = "Password",
                            fontSize = 16.sp,
                            fontWeight = FontWeight.Medium,
                            color = Color.Black,
                            modifier = Modifier.padding(bottom = 8.dp)
                        )
                        
                        OutlinedTextField(
                            value = password,
                            onValueChange = { password = it },
                            placeholder = {
                                Text(
                                    text = "enter your password",
                                    color = GrayText,
                                    fontSize = 16.sp
                                )
                            },
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(56.dp),
                            shape = RoundedCornerShape(12.dp),
                            colors = OutlinedTextFieldDefaults.colors(
                                focusedBorderColor = CoralPrimary,
                                unfocusedBorderColor = Color.Gray.copy(alpha = 0.3f),
                                focusedContainerColor = Color.Transparent,
                                unfocusedContainerColor = Color.Transparent
                            ),
                            visualTransformation = if (passwordVisible) VisualTransformation.None else PasswordVisualTransformation(),
                            trailingIcon = {
                                Text(
                                    text = if (passwordVisible) "👁" else "👁‍🗨",
                                    modifier = Modifier.clickable { 
                                        passwordVisible = !passwordVisible 
                                    }
                                )
                            },
                            singleLine = true
                        )
                    }
                    
                    Spacer(modifier = Modifier.height(16.dp))
                    
                    // Confirm Password поле
                    Column(
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Text(
                            text = "Confirm Password",
                            fontSize = 16.sp,
                            fontWeight = FontWeight.Medium,
                            color = Color.Black,
                            modifier = Modifier.padding(bottom = 8.dp)
                        )
                        
                        OutlinedTextField(
                            value = confirmPassword,
                            onValueChange = { confirmPassword = it },
                            placeholder = {
                                Text(
                                    text = "Confirm your password",
                                    color = GrayText,
                                    fontSize = 16.sp
                                )
                            },
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(56.dp),
                            shape = RoundedCornerShape(12.dp),
                            colors = OutlinedTextFieldDefaults.colors(
                                focusedBorderColor = CoralPrimary,
                                unfocusedBorderColor = Color.Gray.copy(alpha = 0.3f),
                                focusedContainerColor = Color.Transparent,
                                unfocusedContainerColor = Color.Transparent
                            ),
                            visualTransformation = if (confirmPasswordVisible) VisualTransformation.None else PasswordVisualTransformation(),
                            trailingIcon = {
                                Text(
                                    text = if (confirmPasswordVisible) "👁" else "👁‍🗨",
                                    modifier = Modifier.clickable { 
                                        confirmPasswordVisible = !confirmPasswordVisible 
                                    }
                                )
                            },
                            singleLine = true
                        )
                    }
                    
                    Spacer(modifier = Modifier.height(32.dp))
                    
                    // Кнопка Create Account
                    Button(
                        onClick = onCreateAccountClick,
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(56.dp),
                        colors = ButtonDefaults.buttonColors(
                            containerColor = CoralPrimary
                        ),
                        shape = RoundedCornerShape(28.dp)
                    ) {
                        Text(
                            text = "Create Account",
                            fontSize = 18.sp,
                            fontWeight = FontWeight.Medium,
                            color = Color.White
                        )
                    }
                    
                    Spacer(modifier = Modifier.height(24.dp))
                    
                    // Login ссылка
                    Row(
                        horizontalArrangement = Arrangement.Center,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            text = "Already have an Account! ",
                            fontSize = 16.sp,
                            color = GrayText
                        )
                        Text(
                            text = "Login",
                            fontSize = 16.sp,
                            color = CoralPrimary,
                            fontWeight = FontWeight.Medium,
                            modifier = Modifier.clickable { onLoginClick() }
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
            }
        }
    }
}

private fun DrawScope.drawWavyBackgroundPatterns() {
    val width = size.width
    val height = size.height
    
    // Простые волнистые линии как в дизайне Figma
    val wavyLine1 = Path().apply {
        moveTo(width * 0.08f, height * 0.22f)
        
        cubicTo(
            width * 0.32f, height * 0.12f,
            width * 0.68f, height * 0.32f,
            width * 0.92f, height * 0.22f
        )
    }
    
    val wavyLine2 = Path().apply {
        moveTo(width * 0.05f, height * 0.42f)
        
        cubicTo(
            width * 0.28f, height * 0.32f,
            width * 0.72f, height * 0.52f,
            width * 0.95f, height * 0.42f
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
fun SignUpScreenPreview() {
    NaRayoneTheme {
        SignUpScreen()
    }
} 