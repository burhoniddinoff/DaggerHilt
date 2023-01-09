package com.example.daggerhilt.di

import android.content.Context
import androidx.room.Room
import com.example.daggerhilt.database.PostDao
import com.example.daggerhilt.database.PostDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {
    @Provides
    @Singleton
    fun providePostDatabase(
        @ApplicationContext context: Context
    ): PostDatabase {
        return Room.databaseBuilder(
            context,
            PostDatabase::class.java,
            "Post.db"
        ).build()
    }

    @Provides
    @Singleton
    fun providePostDao(database: PostDatabase): PostDao {
        return database.dao
    }
}