package ru.narayone.featureauth.data.repository

import kotlinx.coroutines.flow.Flow
import ru.narayone.core.common.Result
import ru.narayone.featureauth.data.dao.UserDao
import ru.narayone.featureauth.data.model.AuthResponse
import ru.narayone.featureauth.data.model.LoginRequest
import ru.narayone.featureauth.data.model.SignUpRequest
import ru.narayone.featureauth.data.model.User
import ru.narayone.featureauth.domain.validator.ValidationErrors
import ru.narayone.featureauth.domain.validator.Validator
import javax.inject.Inject
import javax.inject.Singleton

/**
 * Репозиторий для работы с аутентификацией
 */
@Singleton
class AuthRepository @Inject constructor(
    private val userDao: UserDao,
    private val validator: Validator
) {
    
    /**
     * Регистрация нового пользователя
     */
    suspend fun signUp(request: SignUpRequest): Result<AuthResponse> {
        return try {
            // Валидация данных
            val validationResults = validator.validateSignUpRequest(request)
            if (validator.hasErrors(validationResults)) {
                val errorMessage = validator.getErrorMessages(validationResults).first()
                return Result.error(Exception(errorMessage))
            }
            
            // Проверка существования пользователя
            if (userDao.isUserExists(request.email)) {
                return Result.error(Exception(ValidationErrors.USER_ALREADY_EXISTS))
            }
            
            // Создание нового пользователя
            val newUser = User(
                fullName = request.fullName.trim(),
                email = request.email.trim().lowercase(),
                password = request.password // В реальном приложении пароль должен быть захеширован
            )
            
            val userId = userDao.insertUser(newUser)
            val createdUser = newUser.copy(id = userId)
            
            Result.success(AuthResponse(user = createdUser))
        } catch (e: Exception) {
            Result.error(e)
        }
    }
    
    /**
     * Вход пользователя
     */
    suspend fun login(request: LoginRequest): Result<AuthResponse> {
        return try {
            // Валидация данных
            val validationResults = validator.validateLoginRequest(request)
            if (validator.hasErrors(validationResults)) {
                val errorMessage = validator.getErrorMessages(validationResults).first()
                return Result.error(Exception(errorMessage))
            }
            
            // Поиск пользователя по email
            val user = userDao.getUserByEmail(request.email.trim().lowercase())
            if (user == null) {
                return Result.error(Exception(ValidationErrors.USER_NOT_FOUND))
            }
            
            // Проверка пароля
            if (user.password != request.password) {
                return Result.error(Exception(ValidationErrors.INVALID_CREDENTIALS))
            }
            
            Result.success(AuthResponse(user = user))
        } catch (e: Exception) {
            Result.error(e)
        }
    }
    
    /**
     * Выход пользователя
     */
    suspend fun logout(): Result<Unit> {
        return try {
            // В данном случае просто возвращаем успех
            // В реальном приложении здесь может быть очистка токенов
            Result.success(Unit)
        } catch (e: Exception) {
            Result.error(e)
        }
    }
    
    /**
     * Получение текущего пользователя
     */
    fun getCurrentUser(): Flow<User?> {
        return userDao.getCurrentUser()
    }
    
    /**
     * Проверка авторизации пользователя
     */
    suspend fun isUserLoggedIn(): Boolean {
        return userDao.getUserCount() > 0
    }
    
    /**
     * Получение пользователя по ID
     */
    suspend fun getUserById(userId: Long): Result<User> {
        return try {
            val user = userDao.getUserById(userId)
            if (user != null) {
                Result.success(user)
            } else {
                Result.error(Exception(ValidationErrors.USER_NOT_FOUND))
            }
        } catch (e: Exception) {
            Result.error(e)
        }
    }
    
    /**
     * Обновление данных пользователя
     */
    suspend fun updateUser(user: User): Result<User> {
        return try {
            userDao.updateUser(user)
            Result.success(user)
        } catch (e: Exception) {
            Result.error(e)
        }
    }
    
    /**
     * Удаление пользователя
     */
    suspend fun deleteUser(userId: Long): Result<Unit> {
        return try {
            userDao.deleteUser(userId)
            Result.success(Unit)
        } catch (e: Exception) {
            Result.error(e)
        }
    }
} 