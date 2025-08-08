# Исправление ошибки KAPT

## Проблема
Ошибка: `e: file:///C:/Users/User/na-rayone/app/build.gradle.kts:69:5: Unresolved reference: kapt`

## Причина
После удаления плагина `kotlin.kapt` из секции `plugins`, использование `kapt` в `dependencies` стало невозможным.

## Решение
Добавлен плагин `kotlin.kapt` обратно в секцию `plugins` в файлах:
- `app/build.gradle.kts`
- `feature-auth/build.gradle.kts`

```kotlin
plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.compose.compiler)
    alias(libs.plugins.hilt.android)
    alias(libs.plugins.kotlin.kapt)  // ← Добавлен обратно
}
```

## Статус
✅ Ошибка исправлена в коде
⚠️ Требуется настройка Java/JDK для полной проверки сборки

## Зависимости, использующие kapt
- `kapt(libs.hilt.compiler)` - для генерации кода Hilt
- `kapt(libs.androidx.room.compiler)` - для генерации кода Room 