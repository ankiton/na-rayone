# Feature Auth - Модуль аутентификации

## Описание

Модуль аутентификации для Android-приложения Na-Rayone, реализованный с использованием архитектурного паттерна MVI (Model-View-Intent). Модуль предоставляет полную функциональность для регистрации, входа и управления пользователями с локальным хранением данных.

## Архитектура

### MVI (Model-View-Intent)

Проект использует архитектурный паттерн MVI, который обеспечивает:

- **Intent** - действия пользователя (нажатия кнопок, ввод данных)
- **State** - состояние UI (данные форм, статус загрузки, ошибки)
- **Effect** - однократные события (навигация, показ уведомлений)

### Структура проекта

```
feature-auth/
├── data/
│   ├── dao/           # Data Access Objects для Room
│   ├── database/      # Конфигурация базы данных
│   ├── model/         # Модели данных
│   └── repository/    # Репозиторий для работы с данными
├── domain/
│   └── validator/     # Валидация данных
├── presentation/
│   ├── mvi/          # MVI компоненты (Intent, State, Effect)
│   ├── screens/      # UI экраны
│   └── viewmodel/    # ViewModel с бизнес-логикой
└── di/               # Dependency Injection модули
```

## Основные компоненты

### 1. Модели данных

- **User** - модель пользователя для хранения в Room
- **LoginRequest** - запрос для входа
- **SignUpRequest** - запрос для регистрации
- **AuthResponse** - ответ аутентификации

### 2. Валидация

Класс `Validator` предоставляет методы для валидации:
- Email адреса
- Паролей (длина, сложность)
- Полного имени
- Подтверждения пароля
- Согласия с условиями

### 3. База данных (Room)

- **AppDatabase** - главная база данных приложения
- **UserDao** - интерфейс для работы с пользователями
- Поддержка мягкого удаления пользователей
- Автоматическая генерация ID

### 4. Репозиторий

`AuthRepository` предоставляет методы:
- `signUp()` - регистрация нового пользователя
- `login()` - вход пользователя
- `logout()` - выход пользователя
- `getCurrentUser()` - получение текущего пользователя
- `isUserLoggedIn()` - проверка авторизации

### 5. ViewModel

`AuthViewModel` реализует MVI архитектуру:
- Обработка Intent'ов от UI
- Управление состоянием приложения
- Эмиссия Effect'ов для однократных событий
- Интеграция с репозиторием

## Использование

### 1. Инициализация

Убедитесь, что в `AndroidManifest.xml` указан Application класс:

```xml
<application android:name=".NaRayoneApplication">
```

### 2. Использование в UI

```kotlin
@Composable
fun MyScreen() {
    val viewModel: AuthViewModel = hiltViewModel()
    val state by viewModel.state.collectAsState()
    
    // Обработка эффектов
    LaunchedEffect(Unit) {
        viewModel.effect.collectLatest { effect ->
            when (effect) {
                is AuthEffect.NavigateToMain -> { /* навигация */ }
                is AuthEffect.ShowError -> { /* показать ошибку */ }
            }
        }
    }
    
    // Отправка Intent'ов
    Button(onClick = {
        viewModel.processIntent(AuthIntent.Login(email, password))
    }) {
        Text("Войти")
    }
}
```

### 3. Навигация

Модуль поддерживает навигацию между экранами:
- Welcome → Login/SignUp
- Login → Main/SignUp
- SignUp → Main/Login
- Main (после успешной аутентификации)

## Обработка ошибок

Система предоставляет детальную обработку ошибок:

- **Валидация данных** - проверка корректности введенных данных
- **Бизнес-логика** - проверка существования пользователей, паролей
- **Технические ошибки** - проблемы с базой данных, сетью

## Безопасность

### Текущие меры:
- Валидация всех входных данных
- Проверка сложности паролей
- Защита от SQL-инъекций через Room

### Рекомендации для продакшена:
- Хеширование паролей (bcrypt, Argon2)
- Шифрование локальной базы данных
- Биометрическая аутентификация
- Токены доступа для сессий

## Зависимости

```kotlin
dependencies {
    // Hilt для dependency injection
    implementation(libs.hilt.android)
    kapt(libs.hilt.compiler)
    
    // Room для локальной базы данных
    implementation(libs.androidx.room.runtime)
    implementation(libs.androidx.room.ktx)
    kapt(libs.androidx.room.compiler)
    
    // Coroutines
    implementation(libs.kotlinx.coroutines.android)
    
    // DataStore для SharedPreferences
    implementation(libs.androidx.datastore.preferences)
}
```

## Тестирование

Для тестирования модуля рекомендуется:

1. **Unit тесты** для ViewModel, Repository, Validator
2. **Integration тесты** для работы с базой данных
3. **UI тесты** для проверки взаимодействия пользователя

## Расширение функциональности

Модуль легко расширяется для добавления:

- Интеграции с серверной частью
- Социальной аутентификации
- Двухфакторной аутентификации
- Восстановления пароля
- Профиля пользователя

## Комментарии на русском языке

Весь код содержит подробные комментарии на русском языке для лучшего понимания архитектуры и бизнес-логики. 