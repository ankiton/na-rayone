package ru.narayone.featureauth.presentation.mvi

import ru.narayone.featureauth.data.model.AuthResponse

/**
 * Эффекты для экрана аутентификации (однократные события)
 */
sealed class AuthEffect {
    
    // Навигация
    object NavigateToLogin : AuthEffect()
    object NavigateToSignUp : AuthEffect()
    object NavigateToWelcome : AuthEffect()
    object NavigateToMain : AuthEffect()
    
    // Успешная аутентификация
    data class AuthSuccess(val response: AuthResponse) : AuthEffect()
    
    // Ошибки
    data class ShowError(val message: String) : AuthEffect()
    object ClearError : AuthEffect()
    
    // Показ уведомлений
    data class ShowToast(val message: String) : AuthEffect()
    
    // Выход
    object LogoutSuccess : AuthEffect()
    
    // Валидация
    data class ShowValidationError(val field: String, val message: String) : AuthEffect()
    object ClearValidationErrors : AuthEffect()
} 