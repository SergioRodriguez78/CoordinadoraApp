package com.coordinadora.coordinadoraapp.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.coordinadora.coordinadoraapp.database.dao.UserDao
import com.coordinadora.coordinadoraapp.database.entity.User

@Database(entities = [User::class], version = 1)
abstract class AppDatabase : RoomDatabase() {
    abstract fun userDao(): UserDao
}
