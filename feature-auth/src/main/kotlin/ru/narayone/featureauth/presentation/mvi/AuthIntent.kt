package ru.narayone.featureauth.presentation.mvi

import ru.narayone.featureauth.data.model.LoginRequest
import ru.narayone.featureauth.data.model.SignUpRequest

/**
 * Intent'ы для экрана аутентификации
 */
sealed class AuthIntent {
    
    // Навигация
    object NavigateToLogin : AuthIntent()
    object NavigateToSignUp : AuthIntent()
    object NavigateToMain : AuthIntent()
    
    // Вход
    data class Login(
        val email: String,
        val password: String,
        val rememberMe: Boolean = false
    ) : AuthIntent()
    
    // Регистрация
    data class SignUp(
        val fullName: String,
        val email: String,
        val password: String,
        val confirmPassword: String,
        val agreeToTerms: Boolean
    ) : AuthIntent()
    
    // Выход
    object Logout : AuthIntent()
    
    // Проверка авторизации
    object CheckAuthStatus : AuthIntent()
    
    // Очистка ошибок
    object ClearError : AuthIntent()
    
    // Обновление полей формы
    data class UpdateLoginEmail(val email: String) : AuthIntent()
    data class UpdateLoginPassword(val password: String) : AuthIntent()
    data class UpdateLoginRememberMe(val rememberMe: Boolean) : AuthIntent()
    
    data class UpdateSignUpFullName(val fullName: String) : AuthIntent()
    data class UpdateSignUpEmail(val email: String) : AuthIntent()
    data class UpdateSignUpPassword(val password: String) : AuthIntent()
    data class UpdateSignUpConfirmPassword(val confirmPassword: String) : AuthIntent()
    data class UpdateSignUpAgreeToTerms(val agreeToTerms: Boolean) : AuthIntent()
} 