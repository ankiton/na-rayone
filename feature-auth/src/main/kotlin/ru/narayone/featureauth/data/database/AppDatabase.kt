package ru.narayone.featureauth.data.database

import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import android.content.Context
import androidx.sqlite.db.SupportSQLiteDatabase
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import ru.narayone.featureauth.data.dao.UserDao
import ru.narayone.featureauth.data.model.User

/**
 * Главная база данных приложения
 */
@Database(
    entities = [User::class],
    version = 3,
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
                .addCallback(object : RoomDatabase.Callback() {
                    override fun onCreate(db: SupportSQLiteDatabase) {
                        super.onCreate(db)
                        val now = System.currentTimeMillis()
                        db.execSQL("INSERT INTO users (fullName, email, password, createdAt, isActive) VALUES ('Test Пользователь', 'test22@example.com', 'test1234', $now, 1)")
                        db.execSQL("INSERT INTO users (fullName, email, password, createdAt, isActive) VALUES ('Demo User', 'test23@example.com', 'test1234', $now, 1)")
                    }
                    override fun onOpen(db: SupportSQLiteDatabase) {
                        super.onOpen(db)
                        val now = System.currentTimeMillis()
                        db.execSQL("INSERT INTO users (fullName, email, password, createdAt, isActive) SELECT 'Test Пользователь', 'test22@example.com', 'test1234', $now, 1 WHERE NOT EXISTS (SELECT 1 FROM users WHERE email='test22@example.com')")
                        db.execSQL("INSERT INTO users (fullName, email, password, createdAt, isActive) SELECT 'Demo User', 'test23@example.com', 'test1234', $now, 1 WHERE NOT EXISTS (SELECT 1 FROM users WHERE email='test23@example.com')")
                    }
                })
                .build()
                INSTANCE = instance
                instance
            }
        }
    }
} 