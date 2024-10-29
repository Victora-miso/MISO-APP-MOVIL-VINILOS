package com.alphazetakapp.misoappvinilos.di

import android.content.Context
import androidx.room.Room
import com.alphazetakapp.misoappvinilos.data.local.AppDatabase
import com.alphazetakapp.misoappvinilos.data.local.dao.AlbumDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module //Indicates that this class provides dependencies
@InstallIn(SingletonComponent::class)
object DatabaseModule {

    @Provides //Tells Hilt that this method will provide a dependency.
    @Singleton //// Only one instance of the database will be created in the whole app.
    fun provideAppDatabase(
        @ApplicationContext context: Context // Hilt injects the context of the application
    ): AppDatabase {
        return Room.databaseBuilder( // Build the database using Room
            context,
            AppDatabase::class.java,
            "vinilos_database" //// Database file name
        ).build()
    }

    @Provides
    fun provideAlbumDao(database: AppDatabase): AlbumDao { // Receives the database provided by the previous method
        return database.albumDao()  // Returns the DAO for accessing the album table
                                    // Gets the DAO from the database
    }

}