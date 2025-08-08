package ru.narayone.featureauth.data.database

import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import android.content.Context
import ru.narayone.featureauth.data.dao.UserDao
import ru.narayone.featureauth.data.model.User

/**
 * Главная база данных приложения
 */
@Database(
    entities = [User::class],
    version = 1,
    exportSchema = false
)
abstract class AppDatabase : RoomDatabase() {
    
    /**
     * DAO для работы с пользователями
     */
    abstract fun userDao(): UserDao
    
    companion object {
        @Volatile
        private var INSTANCE: AppDatabase? = null
        
        /**
         * Получить экземпляр базы данных (Singleton)
         */
        fun getDatabase(context: Context): AppDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    AppDatabase::class.java,
                    "na_rayone_database"
                )
                .fallbackToDestructiveMigration() // Удаляет базу при изменении схемы
                .build()
                INSTANCE = instance
                instance
            }
        }
    }
} 