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
fun SignUpScreen(
    fullName: String = "",
    email: String = "",
    password: String = "",
    confirmPassword: String = "",
    agreeToTerms: Boolean = false,
    isLoading: Boolean = false,
    error: String? = null,
    onFullNameChange: (String) -> Unit = {},
    onEmailChange: (String) -> Unit = {},
    onPasswordChange: (String) -> Unit = {},
    onConfirmPasswordChange: (String) -> Unit = {},
    onAgreeToTermsChange: (Boolean) -> Unit = {},
    onSignUpClick: () -> Unit = {},
    onNavigateToLogin: () -> Unit = {},
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
                .height(700.dp)
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
                        text = stringResource(id = R.string.sign_in_reg),
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
                
                // Форма регистрации
                Column(
                    modifier = Modifier.padding(vertical = 40.dp),
                    verticalArrangement = Arrangement.spacedBy(24.dp)
                ) {
                    // Поле Full Name
                    Column {
                        Text(
                            text = stringResource(id = R.string.full_name),
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
                                painter = painterResource(id = R.drawable.ic_person),
                                contentDescription = "Person",
                                tint = GrayText,
                                modifier = Modifier.size(20.dp)
                            )
                            
                            Spacer(modifier = Modifier.width(12.dp))
                            
                            BasicTextField(
                                value = fullName,
                                onValueChange = { onFullNameChange(it) },
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
                    
                    // Поле Confirm Password
                    Column {
                        Text(
                            text = stringResource(id = R.string.confirm_password),
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
                                contentDescription = "Confirm Password",
                                tint = GrayText,
                                modifier = Modifier.size(20.dp)
                            )
                            
                            Spacer(modifier = Modifier.width(12.dp))
                            
                            BasicTextField(
                                value = confirmPassword,
                                onValueChange = { onConfirmPasswordChange(it) },
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
                    
                    // Согласие с условиями
                    Row(
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Checkbox(
                            checked = agreeToTerms,
                            onCheckedChange = { onAgreeToTermsChange(it) },
                            colors = CheckboxDefaults.colors(
                                checkedColor = CoralPrimary,
                                uncheckedColor = GrayText
                            )
                        )
                        
                        Text(
                            text = stringResource(id = R.string.i_agree_to_the_terms_and_conditions),
                            fontSize = 14.sp,
                            color = CoralPrimary
                        )
                    }
                }
                
                // Кнопка Sign Up
                Button(
                    onClick = onSignUpClick,
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
                            text = stringResource(id = R.string.sign_in_zareg),
                            color = Color.White,
                            fontSize = 16.sp,
                            fontWeight = FontWeight.Medium
                        )
                    }
                }
                
                // Ссылка на вход
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(bottom = 24.dp),
                    horizontalArrangement = Arrangement.Center
                ) {
                    Text(
                        text = stringResource(id = R.string.already_have_an_account),
                        fontSize = 14.sp,
                        color = GrayText
                    )
                    
                    Text(
                        text = stringResource(id = R.string.login),
                        fontSize = 14.sp,
                        color = CoralPrimary,
                        fontWeight = FontWeight.Medium,
                        modifier = Modifier.clickable { onNavigateToLogin() }
                    )
                }
            }
        }

        // Фоновый рисунок (рисуется над контентом)
        Image(
            painter = painterResource(id = R.drawable.bgimage_signup),
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
                tint = Color.Black
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun SignUpScreenPreview() {
    NaRayoneTheme {
        SignUpScreen()
    }
} 