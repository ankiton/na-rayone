# 🔐 Feature Auth

Модуль авторизации для приложения "На районе".

## Экраны

### 1. WelcomeScreen (Экран приветствия)
- Стартовый экран с приветствием пользователя
- Кнопка "Continue" для перехода к авторизации
- Градиентный коралловый фон с декоративными элементами
- Индикатор прогресса внизу

### 2. LoginScreen (Экран входа)  
- Поля для ввода email и пароля
- Checkbox "Remember Me"
- Ссылка "Forgot Password?"
- Кнопка "Login"
- Ссылка на регистрацию

### 3. SignUpScreen (Экран регистрации)
- Поля для ввода email, телефона, пароля и подтверждения пароля
- Валидация паролей
- Кнопка "Create Account"
- Ссылка на вход

## Использование

```kotlin
// В композабле
WelcomeScreen(
    onContinueClick = { /* навигация на LoginScreen */ }
)

LoginScreen(
    onLoginClick = { /* успешный вход */ },
    onSignUpClick = { /* навигация на SignUpScreen */ }
)

SignUpScreen(
    onCreateAccountClick = { /* создание аккаунта */ },
    onLoginClick = { /* навигация на LoginScreen */ }
)
```

## Дизайн

- Коралловый градиентный фон (CoralPrimary -> CoralBackground)
- Белая нижняя область с закругленными углами
- Декоративные полупрозрачные круги на фоне
- Индикатор прогресса внизу экрана
- Material Design 3 компоненты

## Цвета

```kotlin
val CoralPrimary = Color(0xFFFF6B6B)
val CoralBackground = Color(0xFFFF7979)
val GrayText = Color(0xFF666666)
```

## TODO

- [ ] Валидация полей ввода
- [ ] Обработка ошибок
- [ ] Интеграция с API
- [ ] Сохранение состояния авторизации
- [ ] Реальные иконки (пока используются эмодзи)
- [ ] Анимации переходов 