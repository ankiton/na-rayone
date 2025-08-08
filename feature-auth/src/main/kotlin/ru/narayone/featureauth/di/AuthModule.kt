package ru.narayone.featureauth.di

import android.content.Context
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import ru.narayone.featureauth.data.dao.UserDao
import ru.narayone.featureauth.data.database.AppDatabase
import ru.narayone.featureauth.domain.validator.Validator
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AuthModule {

    @Provides
    @Singleton
    fun provideAppDatabase(@ApplicationContext context: Context): AppDatabase =
        AppDatabase.getDatabase(context)

    @Provides
    @Singleton
    fun provideUserDao(db: AppDatabase): UserDao = db.userDao()

    @Provides
    @Singleton
    fun provideValidator(): Validator = Validator()
}
