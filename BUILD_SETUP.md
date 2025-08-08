# Инструкции по настройке сборки проекта

## Требования

### 1. Java Development Kit (JDK)
- Версия: JDK 17 или выше
- Рекомендуется использовать JDK, поставляемый с Android Studio

### 2. Android Studio
- Версия: Android Studio Hedgehog (2023.1.1) или выше
- Android SDK API 35
- Android Build Tools

### 3. Gradle
- Версия: 8.0 или выше (автоматически управляется через Gradle Wrapper)

## Настройка окружения

### Установка JDK
1. Скачайте и установите JDK 17 с официального сайта Oracle или используйте OpenJDK
2. Настройте переменную окружения `JAVA_HOME`:
   ```bash
   # Windows
   set JAVA_HOME=C:\Program Files\Java\jdk-17
   
   # macOS/Linux
   export JAVA_HOME=/usr/lib/jvm/java-17-openjdk
   ```

### Настройка Android Studio
1. Установите Android Studio
2. Откройте SDK Manager и установите:
   - Android SDK Platform 35
   - Android Build Tools
   - Android SDK Command-line Tools

## Сборка проекта

### Через Android Studio
1. Откройте проект в Android Studio
2. Дождитесь синхронизации Gradle
3. Выберите Build → Make Project (Ctrl+F9)

### Через командную строку
```bash
# Windows
.\gradlew.bat build

# macOS/Linux
./gradlew build
```

## Исправленные проблемы

### Ошибка плагина kotlin.kapt
**Проблема:** `Error resolving plugin [id: 'org.jetbrains.kotlin.kapt', version: '1.9.22']` - плагин уже находится в classpath с неизвестной версией

**Решение:** Заменен `kapt` на `KSP` (Kotlin Symbol Processing) в `gradle/libs.versions.toml`:
```toml
[plugins]
ksp = { id = "com.google.devtools.ksp", version.ref = "ksp" }
```

И обновлены зависимости:
- `kapt(libs.hilt.compiler)` → `ksp(libs.hilt.compiler)`
- `kapt(libs.androidx.room.compiler)` → `ksp(libs.androidx.room.compiler)`

KSP - это более быстрая и современная альтернатива kapt.

### Ошибка плагина Compose
**Проблема:** `Compose Multiplatform 1.5.11 doesn't support Kotlin 1.9.22`

**Решение:** Удален плагин `compose.compiler` из всех модулей проекта:
- `feature-feed/build.gradle.kts`
- `shared-ui/build.gradle.kts`
- `feature-post-create/build.gradle.kts`
- `feature-map/build.gradle.kts`
- `feature-main/build.gradle.kts`
- `feature-house-detail/build.gradle.kts`

Compose работает через зависимости в `dependencies` без необходимости в отдельном плагине.

## Архитектура проекта

Проект использует архитектуру MVI (Model-View-Intent) с следующими компонентами:

- **Data Layer**: Room Database, DataStore Preferences
- **Domain Layer**: Use Cases, Validators
- **Presentation Layer**: MVI (Intent, State, Effect), ViewModels
- **DI**: Hilt для dependency injection

## Модули

- `app`: Главный модуль приложения
- `feature-auth`: Модуль аутентификации
- `feature-main`: Главный экран
- `feature-map`: Экран карты
- `feature-house-detail`: Детали дома
- `core/common`: Общие утилиты
- `shared-ui`: Общие UI компоненты 