package com.coordinadora.coordinadoraapp.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.coordinadora.coordinadoraapp.database.entity.User

@Dao
interface UserDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun save(user: User)

    @Query("DELETE FROM usuario_login")
    suspend fun delete()

    @Query("UPDATE usuario_login SET validationPeriod = validationPeriod - 1")
    suspend fun reduceValidationPeriod()
}
