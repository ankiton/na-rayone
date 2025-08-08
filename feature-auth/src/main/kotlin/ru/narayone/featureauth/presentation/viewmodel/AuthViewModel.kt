package ru.narayone.featureauth.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import ru.narayone.featureauth.data.model.LoginRequest
import ru.narayone.featureauth.data.model.SignUpRequest
import ru.narayone.featureauth.data.repository.AuthRepository
import ru.narayone.featureauth.domain.validator.ValidationErrors
import ru.narayone.featureauth.domain.validator.Validator
import ru.narayone.featureauth.presentation.mvi.*
import javax.inject.Inject

/**
 * ViewModel для экрана аутентификации с MVI архитектурой
 */
@HiltViewModel
class AuthViewModel @Inject constructor(
    private val authRepository: AuthRepository,
    private val validator: Validator
) : ViewModel() {
    
    // Состояние
    private val _state = MutableStateFlow(AuthState())
    val state: StateFlow<AuthState> = _state.asStateFlow()
    
    // Эффекты (однократные события)
    private val _effect = MutableSharedFlow<AuthEffect>()
    val effect: SharedFlow<AuthEffect> = _effect.asSharedFlow()
    
    init {
        // Проверяем статус авторизации при инициализации
        checkAuthStatus()
    }
    
    /**
     * Обработка Intent'ов
     */
    fun processIntent(intent: AuthIntent) {
        when (intent) {
            // Навигация
            is AuthIntent.NavigateToLogin -> navigateToLogin()
            is AuthIntent.NavigateToSignUp -> navigateToSignUp()
            is AuthIntent.NavigateToWelcome -> navigateToWelcome()
            is AuthIntent.NavigateToMain -> navigateToMain()
            
            // Вход
            is AuthIntent.Login -> login(intent.email, intent.password, intent.rememberMe)
            
            // Регистрация
            is AuthIntent.SignUp -> signUp(
                intent.fullName,
                intent.email,
                intent.password,
                intent.confirmPassword,
                intent.agreeToTerms
            )
            
            // Выход
            is AuthIntent.Logout -> logout()
            
            // Проверка авторизации
            is AuthIntent.CheckAuthStatus -> checkAuthStatus()
            
            // Очистка ошибок
            is AuthIntent.ClearError -> clearError()
            
            // Обновление полей формы входа
            is AuthIntent.UpdateLoginEmail -> updateLoginEmail(intent.email)
            is AuthIntent.UpdateLoginPassword -> updateLoginPassword(intent.password)
            is AuthIntent.UpdateLoginRememberMe -> updateLoginRememberMe(intent.rememberMe)
            
            // Обновление полей формы регистрации
            is AuthIntent.UpdateSignUpFullName -> updateSignUpFullName(intent.fullName)
            is AuthIntent.UpdateSignUpEmail -> updateSignUpEmail(intent.email)
            is AuthIntent.UpdateSignUpPassword -> updateSignUpPassword(intent.password)
            is AuthIntent.UpdateSignUpConfirmPassword -> updateSignUpConfirmPassword(intent.confirmPassword)
            is AuthIntent.UpdateSignUpAgreeToTerms -> updateSignUpAgreeToTerms(intent.agreeToTerms)
        }
    }
    
    private fun navigateToWelcome() {
        _state.update { it.copy(currentScreen = AuthScreen.Welcome) }
        viewModelScope.launch {
            _effect.emit(AuthEffect.NavigateToWelcome)
        }
    }
    
    /**
     * Навигация к экрану входа
     */
    private fun navigateToLogin() {
        _state.update { it.copy(currentScreen = AuthScreen.Login) }
        viewModelScope.launch {
            _effect.emit(AuthEffect.NavigateToLogin)
        }
    }
    
    /**
     * Навигация к экрану регистрации
     */
    private fun navigateToSignUp() {
        _state.update { it.copy(currentScreen = AuthScreen.SignUp) }
        viewModelScope.launch {
            _effect.emit(AuthEffect.NavigateToSignUp)
        }
    }
    
    /**
     * Навигация к главному экрану
     */
    private fun navigateToMain() {
        _state.update { it.copy(currentScreen = AuthScreen.Main) }
        viewModelScope.launch {
            _effect.emit(AuthEffect.NavigateToMain)
        }
    }
    
    /**
     * Вход пользователя
     */
    private fun login(email: String, password: String, rememberMe: Boolean) {
        viewModelScope.launch {
            try {
                _state.update { it.copy(isLoading = true, error = null) }
                
                val request = LoginRequest(email, password, rememberMe)
                val result = authRepository.login(request)
                
                when (result) {
                    is ru.narayone.core.common.Result.Success -> {
                        _state.update { 
                            it.copy(
                                isLoading = false,
                                isLoggedIn = true,
                                currentUser = result.data.user,
                                authResult = result,
                                currentScreen = AuthScreen.Main
                            )
                        }
                        _effect.emit(AuthEffect.AuthSuccess(result.data))
                        _effect.emit(AuthEffect.NavigateToMain)
                    }
                    is ru.narayone.core.common.Result.Error -> {
                        _state.update { 
                            it.copy(
                                isLoading = false,
                                error = result.exception.message
                            )
                        }
                        _effect.emit(AuthEffect.ShowError(result.exception.message ?: "Ошибка входа"))
                    }
                    is ru.narayone.core.common.Result.Loading -> {
                        _state.update { it.copy(isLoading = true) }
                    }
                }
            } catch (e: Exception) {
                _state.update { 
                    it.copy(
                        isLoading = false,
                        error = e.message
                    )
                }
                _effect.emit(AuthEffect.ShowError(e.message ?: "Неизвестная ошибка"))
            }
        }
    }
    
    /**
     * Регистрация пользователя
     */
    private fun signUp(
        fullName: String,
        email: String,
        password: String,
        confirmPassword: String,
        agreeToTerms: Boolean
    ) {
        viewModelScope.launch {
            try {
                _state.update { it.copy(isLoading = true, error = null) }
                
                val request = SignUpRequest(fullName, email, password, confirmPassword, agreeToTerms)
                val result = authRepository.signUp(request)
                
                when (result) {
                    is ru.narayone.core.common.Result.Success -> {
                        _state.update { 
                            it.copy(
                                isLoading = false,
                                isLoggedIn = true,
                                currentUser = result.data.user,
                                authResult = result,
                                currentScreen = AuthScreen.Main
                            )
                        }
                        _effect.emit(AuthEffect.AuthSuccess(result.data))
                        _effect.emit(AuthEffect.ShowToast("Регистрация успешна!"))
                        _effect.emit(AuthEffect.NavigateToMain)
                    }
                    is ru.narayone.core.common.Result.Error -> {
                        _state.update { 
                            it.copy(
                                isLoading = false,
                                error = result.exception.message
                            )
                        }
                        _effect.emit(AuthEffect.ShowError(result.exception.message ?: "Ошибка регистрации"))
                    }
                    is ru.narayone.core.common.Result.Loading -> {
                        _state.update { it.copy(isLoading = true) }
                    }
                }
            } catch (e: Exception) {
                _state.update { 
                    it.copy(
                        isLoading = false,
                        error = e.message
                    )
                }
                _effect.emit(AuthEffect.ShowError(e.message ?: "Неизвестная ошибка"))
            }
        }
    }
    
    /**
     * Выход пользователя
     */
    private fun logout() {
        viewModelScope.launch {
            try {
                _state.update { it.copy(isLoading = true) }
                
                val result = authRepository.logout()
                
                when (result) {
                    is ru.narayone.core.common.Result.Success -> {
                        _state.update { 
                            it.copy(
                                isLoading = false,
                                isLoggedIn = false,
                                currentUser = null,
                                currentScreen = AuthScreen.Welcome
                            )
                        }
                        _effect.emit(AuthEffect.LogoutSuccess)
                        _effect.emit(AuthEffect.ShowToast("Выход выполнен успешно"))
                    }
                    is ru.narayone.core.common.Result.Error -> {
                        _state.update { 
                            it.copy(
                                isLoading = false,
                                error = result.exception.message
                            )
                        }
                        _effect.emit(AuthEffect.ShowError(result.exception.message ?: "Ошибка выхода"))
                    }
                    is ru.narayone.core.common.Result.Loading -> {
                        _state.update { it.copy(isLoading = true) }
                    }
                }
            } catch (e: Exception) {
                _state.update { 
                    it.copy(
                        isLoading = false,
                        error = e.message
                    )
                }
                _effect.emit(AuthEffect.ShowError(e.message ?: "Неизвестная ошибка"))
            }
        }
    }
    
    /**
     * Проверка статуса авторизации
     */
    private fun checkAuthStatus() {
        viewModelScope.launch {
            try {
                val isLoggedIn = authRepository.isUserLoggedIn()
                _state.update { it.copy(isLoggedIn = isLoggedIn) }
                
                if (isLoggedIn) {
                    authRepository.getCurrentUser().collect { user ->
                        _state.update { it.copy(currentUser = user) }
                    }
                }
            } catch (e: Exception) {
                _state.update { it.copy(error = e.message) }
            }
        }
    }
    
    /**
     * Очистка ошибки
     */
    private fun clearError() {
        _state.update { it.copy(error = null) }
        viewModelScope.launch {
            _effect.emit(AuthEffect.ClearError)
        }
    }
    
    // Обновление полей формы входа
    private fun updateLoginEmail(email: String) {
        _state.update { it.copy(loginEmail = email) }
    }
    
    private fun updateLoginPassword(password: String) {
        _state.update { it.copy(loginPassword = password) }
    }
    
    private fun updateLoginRememberMe(rememberMe: Boolean) {
        _state.update { it.copy(loginRememberMe = rememberMe) }
    }
    
    // Обновление полей формы регистрации
    private fun updateSignUpFullName(fullName: String) {
        _state.update { it.copy(signUpFullName = fullName) }
    }
    
    private fun updateSignUpEmail(email: String) {
        _state.update { it.copy(signUpEmail = email) }
    }
    
    private fun updateSignUpPassword(password: String) {
        _state.update { it.copy(signUpPassword = password) }
    }
    
    private fun updateSignUpConfirmPassword(confirmPassword: String) {
        _state.update { it.copy(signUpConfirmPassword = confirmPassword) }
    }
    
    private fun updateSignUpAgreeToTerms(agreeToTerms: Boolean) {
        _state.update { it.copy(signUpAgreeToTerms = agreeToTerms) }
    }
} 