package ru.narayone.featureauth.presentation.mvi

import ru.narayone.core.common.Result
import ru.narayone.featureauth.data.model.AuthResponse
import ru.narayone.featureauth.data.model.User

/**
 * Состояние экрана аутентификации
 */
data class AuthState(
    // Общее состояние
    val isLoading: Boolean = false,
    val error: String? = null,
    val isLoggedIn: Boolean = false,
    val currentUser: User? = null,
    
    // Навигация
    val currentScreen: AuthScreen = AuthScreen.Welcome,
    
    // Форма входа
    val loginEmail: String = "",
    val loginPassword: String = "",
    val loginRememberMe: Boolean = false,
    val loginValidationErrors: Map<String, String> = emptyMap(),
    
    // Форма регистрации
    val signUpFullName: String = "",
    val signUpEmail: String = "",
    val signUpPassword: String = "",
    val signUpConfirmPassword: String = "",
    val signUpAgreeToTerms: Boolean = false,
    val signUpValidationErrors: Map<String, String> = emptyMap(),
    
    // Результат аутентификации
    val authResult: Result<AuthResponse>? = null
) {
    
    /**
     * Проверка валидности формы входа
     */
    val isLoginFormValid: Boolean
        get() = loginEmail.isNotBlank() && 
                loginPassword.isNotBlank() && 
                loginValidationErrors.isEmpty()
    
    /**
     * Проверка валидности формы регистрации
     */
    val isSignUpFormValid: Boolean
        get() = signUpFullName.isNotBlank() && 
                signUpEmail.isNotBlank() && 
                signUpPassword.isNotBlank() && 
                signUpConfirmPassword.isNotBlank() && 
                signUpAgreeToTerms && 
                signUpValidationErrors.isEmpty()
}

/**
 * Экраны аутентификации
 */
enum class AuthScreen {
    Welcome,
    Login,
    SignUp,
    Main
} 