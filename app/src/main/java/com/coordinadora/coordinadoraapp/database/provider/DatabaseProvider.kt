package com.coordinadora.coordinadoraapp.database.provider

import android.content.Context
import androidx.room.Room
import com.coordinadora.coordinadoraapp.database.AppDatabase

class DatabaseProvider(private val context: Context) {

    fun getDatabase(): AppDatabase =
        Room.databaseBuilder(
            context.applicationContext,
            AppDatabase::class.java,
            DATABASE_NAME
        ).build()

    companion object {
        const val DATABASE_NAME = "coordinadora_database"
    }
}
