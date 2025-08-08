package ru.narayone.featureauth.data.dao

import androidx.room.*
import kotlinx.coroutines.flow.Flow
import ru.narayone.featureauth.data.model.User

/**
 * Data Access Object для работы с пользователями в базе данных
 */
@Dao
interface UserDao {
    
    /**
     * Получить всех пользователей
     */
    @Query("SELECT * FROM users WHERE isActive = 1")
    fun getAllUsers(): Flow<List<User>>
    
    /**
     * Получить пользователя по ID
     */
    @Query("SELECT * FROM users WHERE id = :userId AND isActive = 1")
    suspend fun getUserById(userId: Long): User?
    
    /**
     * Получить пользователя по email
     */
    @Query("SELECT * FROM users WHERE email = :email AND isActive = 1")
    suspend fun getUserByEmail(email: String): User?
    
    /**
     * Проверить существование пользователя по email
     */
    @Query("SELECT EXISTS(SELECT 1 FROM users WHERE email = :email AND isActive = 1)")
    suspend fun isUserExists(email: String): Boolean
    
    /**
     * Получить текущего активного пользователя
     */
    @Query("SELECT * FROM users WHERE isActive = 1 LIMIT 1")
    fun getCurrentUser(): Flow<User?>
    
    /**
     * Вставить нового пользователя
     */
    @Insert(onConflict = OnConflictStrategy.ABORT)
    suspend fun insertUser(user: User): Long
    
    /**
     * Обновить пользователя
     */
    @Update
    suspend fun updateUser(user: User)
    
    /**
     * Удалить пользователя (мягкое удаление)
     */
    @Query("UPDATE users SET isActive = 0 WHERE id = :userId")
    suspend fun deleteUser(userId: Long)
    
    /**
     * Полное удаление пользователя из базы данных
     */
    @Delete
    suspend fun hardDeleteUser(user: User)
    
    /**
     * Очистить всех неактивных пользователей
     */
    @Query("DELETE FROM users WHERE isActive = 0")
    suspend fun clearInactiveUsers()
    
    /**
     * Получить количество пользователей
     */
    @Query("SELECT COUNT(*) FROM users WHERE isActive = 1")
    suspend fun getUserCount(): Int
} 