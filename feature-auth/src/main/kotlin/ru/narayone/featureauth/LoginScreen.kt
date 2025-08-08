package ru.narayone.featureauth

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import ru.narayone.sharedui.theme.*

@Composable
fun LoginScreen(
    email: String = "",
    password: String = "",
    rememberMe: Boolean = false,
    isLoading: Boolean = false,
    error: String? = null,
    onEmailChange: (String) -> Unit = {},
    onPasswordChange: (String) -> Unit = {},
    onRememberMeChange: (Boolean) -> Unit = {},
    onLoginClick: () -> Unit = {},
    onNavigateToSignUp: () -> Unit = {},
    onNavigateToMain: () -> Unit = {},
    onBackClick: () -> Unit = {}
) {
    
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
    ) {
        // Белая область с контентом
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(570.dp)
                .align(Alignment.BottomCenter)
                .background(Color.White)
        ) {
            // Контент
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(24.dp),
                horizontalAlignment = Alignment.Start,
                verticalArrangement = Arrangement.SpaceBetween
            ) {
                // Заголовок
                Column(
                    modifier = Modifier.padding(top = 40.dp)
                ) {
                    Text(
                        text = stringResource(id = R.string.sign_in),
                        fontSize = 32.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color.Black
                    )
                    
                    // Подчеркивание заголовка
                    Box(
                        modifier = Modifier
                            .padding(top = 8.dp)
                            .width(60.dp)
                            .height(3.dp)
                            .background(CoralLight)
                    )
                }
                
                // Форма входа
                Column(
                    modifier = Modifier.padding(vertical = 40.dp),
                    verticalArrangement = Arrangement.spacedBy(24.dp)
                ) {
                    // Поле Email
                    Column {
                        Text(
                            text = stringResource(id = R.string.email),
                            fontSize = 16.sp,
                            fontWeight = FontWeight.Medium,
                            color = Color.Black,
                            modifier = Modifier.padding(bottom = 8.dp)
                        )
                        
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(bottom = 8.dp)
                        ) {
                            Icon(
                                painter = painterResource(id = R.drawable.ic_email),
                                contentDescription = "Email",
                                tint = GrayText,
                                modifier = Modifier.size(20.dp)
                            )
                            
                            Spacer(modifier = Modifier.width(12.dp))
                            
                            BasicTextField(
                                value = email,
                                onValueChange = { onEmailChange(it) },
                                textStyle = androidx.compose.ui.text.TextStyle(
                                    fontSize = 16.sp,
                                    color = Color.Black
                                ),
                                modifier = Modifier.weight(1f)
                            )
                        }
                        
                        // Подчеркивание поля
                        Box(
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(1.dp)
                                .background(GrayText)
                        )
                    }
                    
                    // Поле Password
                    Column {
                        Text(
                            text = stringResource(id = R.string.password),
                            fontSize = 16.sp,
                            fontWeight = FontWeight.Medium,
                            color = Color.Black,
                            modifier = Modifier.padding(bottom = 8.dp)
                        )
                        
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(bottom = 8.dp)
                        ) {
                            Icon(
                                painter = painterResource(id = R.drawable.ic_eye),
                                contentDescription = "Password",
                                tint = GrayText,
                                modifier = Modifier.size(20.dp)
                            )
                            
                            Spacer(modifier = Modifier.width(12.dp))
                            
                            BasicTextField(
                                value = password,
                                onValueChange = { onPasswordChange(it) },
                                visualTransformation = PasswordVisualTransformation(),
                                textStyle = androidx.compose.ui.text.TextStyle(
                                    fontSize = 16.sp,
                                    color = Color.Black
                                ),
                                modifier = Modifier.weight(1f)
                            )
                        }
                        
                        // Подчеркивание поля
                        Box(
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(1.dp)
                                .background(GrayText)
                        )
                    }
                    
                    // Remember Me и Forgot Password
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Row(
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Checkbox(
                                checked = rememberMe,
                                onCheckedChange = { onRememberMeChange(it) },
                                colors = CheckboxDefaults.colors(
                                    checkedColor = CoralPrimary,
                                    uncheckedColor = GrayText
                                )
                            )
                            
                            Text(
                                text = stringResource(id = R.string.remember_me),
                                fontSize = 14.sp,
                                color = CoralPrimary
                            )
                        }
                        
                        Text(
                            text = stringResource(id = R.string.forgot_password),
                            fontSize = 14.sp,
                            color = CoralPrimary
                        )
                    }
                }
                
                // Кнопка Login
                Button(
                    onClick = onLoginClick,
                    enabled = !isLoading,
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(48.dp),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = CoralLight
                    ),
                    shape = MaterialTheme.shapes.medium
                ) {
                    if (isLoading) {
                        CircularProgressIndicator(
                            color = Color.White,
                            modifier = Modifier.size(20.dp)
                        )
                    } else {
                        Text(
                            text = stringResource(id = R.string.login),
                            color = Color.White,
                            fontSize = 16.sp,
                            fontWeight = FontWeight.Medium
                        )
                    }
                }
                
                // Ссылка на регистрацию
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(bottom = 24.dp),
                    horizontalArrangement = Arrangement.Center
                ) {
                    Text(
                        text = stringResource(id = R.string.dont_have_an_account),
                        fontSize = 14.sp,
                        color = GrayText
                    )
                    
                    Text(
                        text = stringResource(id = R.string.sign_in_reg),
                        fontSize = 14.sp,
                        color = CoralPrimary,
                        fontWeight = FontWeight.Medium,
                        modifier = Modifier.clickable { onNavigateToSignUp() }
                    )
                }
            }
        }

        // Фоновый рисунок (рисуется над контентом)
        Image(
            painter = painterResource(id = R.drawable.bgimage_login),
            contentDescription = "Background Pattern",
            modifier = Modifier.fillMaxSize(),
            contentScale = ContentScale.FillBounds
        )

        // Стрелка назад (самая верхняя, ниже статус-бара)
        IconButton(
            onClick = onBackClick,
            modifier = Modifier
                .statusBarsPadding()
                .padding(start = 12.dp, top = 12.dp)
                .align(Alignment.TopStart)
        ) {
            Icon(
                painter = painterResource(id = R.drawable.ic_arrow_back),
                contentDescription = "Back",
                tint = Color.White
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun LoginScreenPreview() {
    NaRayoneTheme {
        LoginScreen()
    }
} 