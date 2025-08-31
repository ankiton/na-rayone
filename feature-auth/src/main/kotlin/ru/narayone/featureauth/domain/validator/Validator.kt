package ru.narayone.featureauth.domain.validator

import android.util.Patterns
import ru.narayone.featureauth.data.model.LoginRequest
import ru.narayone.featureauth.data.model.SignUpRequest

/**
 * Класс для валидации данных аутентификации
 */
class Validator {
    
    /**
     * Валидация email адреса
     */
    fun validateEmail(email: String): ValidationResult {
        return when {
            email.isBlank() -> ValidationResult.Invalid(ValidationErrors.EMAIL_EMPTY)
            !Patterns.EMAIL_ADDRESS.matcher(email).matches() -> 
                ValidationResult.Invalid(ValidationErrors.EMAIL_INVALID)
            else -> ValidationResult.Valid
        }
    }
    
    /**
     * Валидация пароля
     */
    fun validatePassword(password: String): ValidationResult {
        return when {
            password.isBlank() -> ValidationResult.Invalid(ValidationErrors.PASSWORD_EMPTY)
            password.length < 6 -> ValidationResult.Invalid(ValidationErrors.PASSWORD_TOO_SHORT)
            else -> ValidationResult.Valid
        }
    }
    
    /**
     * Валидация полного имени
     */
    fun validateFullName(fullName: String): ValidationResult {
        return when {
            fullName.isBlank() -> ValidationResult.Invalid(ValidationErrors.FULL_NAME_EMPTY)
            fullName.length < 2 -> ValidationResult.Invalid(ValidationErrors.FULL_NAME_TOO_SHORT)
            else -> ValidationResult.Valid
        }
    }
    
    /**
     * Валидация подтверждения пароля
     */
    fun validateConfirmPassword(password: String, confirmPassword: String): ValidationResult {
        return if (password != confirmPassword) {
            ValidationResult.Invalid(ValidationErrors.PASSWORDS_DONT_MATCH)
        } else {
            ValidationResult.Valid
        }
    }
    
    /**
     * Валидация согласия с условиями
     */
    fun validateTermsAgreement(agreeToTerms: Boolean): ValidationResult {
        return if (!agreeToTerms) {
            ValidationResult.Invalid(ValidationErrors.TERMS_NOT_AGREED)
        } else {
            ValidationResult.Valid
        }
    }
    
    /**
     * Полная валидация запроса входа
     */
    fun validateLoginRequest(request: LoginRequest): List<ValidationResult> {
        return listOf(
            validateEmail(request.email),
            validatePassword(request.password)
        )
    }
    
    /**
     * Полная валидация запроса регистрации
     */
    fun validateSignUpRequest(request: SignUpRequest): List<ValidationResult> {
        return listOf(
            validateFullName(request.fullName),
            validateEmail(request.email),
            validatePassword(request.password),
            validateConfirmPassword(request.password, request.confirmPassword),
            validateTermsAgreement(request.agreeToTerms)
        )
    }
    
    /**
     * Проверка, есть ли ошибки в результатах валидации
     */
    fun hasErrors(validationResults: List<ValidationResult>): Boolean {
        return validationResults.any { it.isInvalid }
    }
    
    /**
     * Получение всех сообщений об ошибках
     */
    fun getErrorMessages(validationResults: List<ValidationResult>): List<String> {
        return validationResults
            .filterIsInstance<ValidationResult.Invalid>()
            .map { it.errorMessage }
    }
} 