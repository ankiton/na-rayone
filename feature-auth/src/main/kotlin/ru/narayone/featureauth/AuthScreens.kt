package ru.narayone.featureauth

// Публичные экспорты для экранов авторизации

// Экран приветствия
@Suppress("unused")
object WelcomeScreenDestination

// Экран входа
@Suppress("unused")
object LoginScreenDestination

// Экран регистрации  
@Suppress("unused")
object SignUpScreenDestination

// Навигационные действия для авторизации
sealed class AuthNavigationAction {
    object NavigateToLogin : AuthNavigationAction()
    object NavigateToSignUp : AuthNavigationAction()
    object NavigateToWelcome : AuthNavigationAction()
    object NavigateToMain : AuthNavigationAction()
} 