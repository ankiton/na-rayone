package ru.narayone.featureauth.presentation.screens

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import kotlinx.coroutines.flow.collectLatest
import ru.narayone.featureauth.presentation.mvi.*
import ru.narayone.featureauth.presentation.viewmodel.AuthViewModel
import ru.narayone.featureauth.*
import ru.narayone.sharedui.theme.NaRayoneTheme
import android.widget.Toast
import androidx.compose.ui.platform.LocalContext

/**
 * Главный экран аутентификации с навигацией
 */
@Composable
fun AuthScreens(
    viewModel: AuthViewModel = hiltViewModel(),
    onNavigateToMain: () -> Unit = {}
) {
    val state by viewModel.state.collectAsState()
    val context = LocalContext.current
    
    // Обработка эффектов
    LaunchedEffect(Unit) {
        viewModel.effect.collectLatest { effect ->
            when (effect) {
                is AuthEffect.NavigateToMain -> onNavigateToMain()
                is AuthEffect.ShowError -> {
                    Toast.makeText(context, effect.message, Toast.LENGTH_SHORT).show()
                }
                is AuthEffect.ShowToast -> {
                    Toast.makeText(context, effect.message, Toast.LENGTH_SHORT).show()
                }
                else -> { /* Обработка других эффектов */ }
            }
        }
    }
    
    // Показ загрузки
    if (state.isLoading) {
        Box(modifier = Modifier.fillMaxSize()) {
            CircularProgressIndicator(
                modifier = Modifier.align(androidx.compose.ui.Alignment.Center)
            )
        }
        return
    }
    
    // Показ ошибки
    state.error?.let { error ->
        LaunchedEffect(error) {
            // Показать ошибку пользователю
        }
    }
    
    // Навигация по экранам
    when (state.currentScreen) {
        AuthScreen.Welcome -> {
            WelcomeScreen(
                onNavigateToLogin = {
                    viewModel.processIntent(AuthIntent.NavigateToLogin)
                },
                onNavigateToSignUp = {
                    viewModel.processIntent(AuthIntent.NavigateToSignUp)
                }
            )
        }
        AuthScreen.Login -> {
            LoginScreen(
                email = state.loginEmail,
                password = state.loginPassword,
                rememberMe = state.loginRememberMe,
                isLoading = state.isLoading,
                error = state.error,
                onEmailChange = { email ->
                    viewModel.processIntent(AuthIntent.UpdateLoginEmail(email))
                },
                onPasswordChange = { password ->
                    viewModel.processIntent(AuthIntent.UpdateLoginPassword(password))
                },
                onRememberMeChange = { rememberMe ->
                    viewModel.processIntent(AuthIntent.UpdateLoginRememberMe(rememberMe))
                },
                onLoginClick = {
                    viewModel.processIntent(
                        AuthIntent.Login(
                            email = state.loginEmail,
                            password = state.loginPassword,
                            rememberMe = state.loginRememberMe
                        )
                    )
                },
                onNavigateToSignUp = {
                    viewModel.processIntent(AuthIntent.NavigateToSignUp)
                },
                onNavigateToMain = {
                    viewModel.processIntent(AuthIntent.NavigateToMain)
                },
                onBackClick = {
                    viewModel.processIntent(AuthIntent.NavigateToWelcome)
                }
            )
        }
        AuthScreen.SignUp -> {
            SignUpScreen(
                fullName = state.signUpFullName,
                email = state.signUpEmail,
                password = state.signUpPassword,
                confirmPassword = state.signUpConfirmPassword,
                agreeToTerms = state.signUpAgreeToTerms,
                isLoading = state.isLoading,
                error = state.error,
                onFullNameChange = { fullName ->
                    viewModel.processIntent(AuthIntent.UpdateSignUpFullName(fullName))
                },
                onEmailChange = { email ->
                    viewModel.processIntent(AuthIntent.UpdateSignUpEmail(email))
                },
                onPasswordChange = { password ->
                    viewModel.processIntent(AuthIntent.UpdateSignUpPassword(password))
                },
                onConfirmPasswordChange = { confirmPassword ->
                    viewModel.processIntent(AuthIntent.UpdateSignUpConfirmPassword(confirmPassword))
                },
                onAgreeToTermsChange = { agreeToTerms ->
                    viewModel.processIntent(AuthIntent.UpdateSignUpAgreeToTerms(agreeToTerms))
                },
                onSignUpClick = {
                    viewModel.processIntent(
                        AuthIntent.SignUp(
                            fullName = state.signUpFullName,
                            email = state.signUpEmail,
                            password = state.signUpPassword,
                            confirmPassword = state.signUpConfirmPassword,
                            agreeToTerms = state.signUpAgreeToTerms
                        )
                    )
                },
                onNavigateToLogin = {
                    viewModel.processIntent(AuthIntent.NavigateToLogin)
                },
                onNavigateToMain = {
                    viewModel.processIntent(AuthIntent.NavigateToMain)
                },
                onBackClick = {
                    viewModel.processIntent(AuthIntent.NavigateToLogin)
                }
            )
        }
        AuthScreen.Main -> {
            // Здесь будет главный экран приложения
            Box(modifier = Modifier.fillMaxSize()) {
                Text("Главный экран")
            }
        }
    }
} 