package ru.narayone.featureauth.domain.validator

/**
 * Результат валидации поля
 */
sealed class ValidationResult {
    object Valid : ValidationResult()
    data class Invalid(val errorMessage: String) : ValidationResult()
    
    val isValid: Boolean get() = this is Valid
    val isInvalid: Boolean get() = this is Invalid
}

/**
 * Ошибки валидации
 */
object ValidationErrors {
    const val EMAIL_EMPTY = "Email не может быть пустым"
    const val EMAIL_INVALID = "Введите корректный email адрес"
    const val PASSWORD_EMPTY = "Пароль не может быть пустым"
    const val PASSWORD_TOO_SHORT = "Пароль должен содержать минимум 6 символов"
    const val PASSWORD_TOO_WEAK = "Пароль должен содержать буквы и цифры"
    const val PASSWORDS_DONT_MATCH = "Пароли не совпадают"
    const val FULL_NAME_EMPTY = "Имя не может быть пустым"
    const val FULL_NAME_TOO_SHORT = "Имя должно содержать минимум 2 символа"
    const val TERMS_NOT_AGREED = "Необходимо согласиться с условиями"
    const val USER_ALREADY_EXISTS = "Пользователь с таким email уже существует"
    const val USER_NOT_FOUND = "Пользователь не найден"
    const val INVALID_CREDENTIALS = "Неверный email или пароль"
} 