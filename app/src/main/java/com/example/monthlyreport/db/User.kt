package com.example.monthlyreport.db

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "users")
data class User(
    @PrimaryKey(autoGenerate = true)
    var id: Int? = null,
    @ColumnInfo(name = "name")
    var name: String,
    @ColumnInfo(name = "password")
    var password: String,
    @ColumnInfo(name = "email")
    var email: String,
    @ColumnInfo(name = "access")
    var access: Int,
    @ColumnInfo(name = "id_report")
    var id_report: Int
)
