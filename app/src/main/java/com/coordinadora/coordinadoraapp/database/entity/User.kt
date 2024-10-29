package com.coordinadora.coordinadoraapp.database.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "usuario_login")
data class User(
    @PrimaryKey val username: String,
    val data: String,
    val validationPeriod: Int
)
