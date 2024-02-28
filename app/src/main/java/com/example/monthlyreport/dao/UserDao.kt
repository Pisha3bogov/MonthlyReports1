package com.example.monthlyreport.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.example.monthlyreport.db.Product
import com.example.monthlyreport.db.Report
import com.example.monthlyreport.db.User

@Dao
interface UserDao {
    @Insert
    fun insertUser(user: User)
    @Query("SELECT * FROM users")
    fun getAllUser(): List<User>
    @Update
    fun updateUser(user: User)
    @Delete
    fun deleteUser(user: User)
}