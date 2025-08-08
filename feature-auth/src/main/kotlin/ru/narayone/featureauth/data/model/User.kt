package ru.narayone.featureauth.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey

/**
 * Модель пользователя для хранения в локальной базе данных
 */
@Entity(tableName = "users")
data class User(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,
    val fullName: String,
    val email: String,
    val password: String, // В реальном приложении пароль должен быть захеширован
    val createdAt: Long = System.currentTimeMillis(),
    val isActive: Boolean = true
)

/**
 * Модель для входа пользователя
 */
data class LoginRequest(
    val email: String,
    val password: String,
    val rememberMe: Boolean = false
)

/**
 * Модель для регистрации пользователя
 */
data class SignUpRequest(
    val fullName: String,
    val email: String,
    val password: String,
    val confirmPassword: String,
    val agreeToTerms: Boolean
)

/**
 * Модель ответа аутентификации
 */
data class AuthResponse(
    val user: User,
    val token: String? = null, // Для будущей интеграции с сервером
    val isLoggedIn: Boolean = true
) 