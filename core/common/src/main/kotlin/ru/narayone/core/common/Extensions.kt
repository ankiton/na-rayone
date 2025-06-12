package ru.narayone.core.common

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onStart

/**
 * Преобразует Flow<T> в Flow<Result<T>>, добавляя состояния загрузки и ошибки
 */
fun <T> Flow<T>.asResult(): Flow<Result<T>> {
    return this
        .map<T, Result<T>> { Result.success(it) }
        .onStart { emit(Result.loading()) }
        .catch { emit(Result.error(it)) }
}

/**
 * Безопасно выполняет блок кода, возвращая Result
 */
suspend fun <T> safeCall(block: suspend () -> T): Result<T> {
    return try {
        Result.success(block())
    } catch (e: Exception) {
        Result.error(e)
    }
}

/**
 * Преобразует один тип в другой с помощью маппера
 */
fun <T, R> T.mapTo(mapper: (T) -> R): R {
    return mapper(this)
} 