package com.example.monthlyreport.db

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "reports")
data class Report(
    @PrimaryKey(autoGenerate = true)
    var id: Int? = null,
    @ColumnInfo(name = "day")
    var day: Int,
    @ColumnInfo(name = "month")
    var month: Int,
    @ColumnInfo(name = "year")
    var year: Int,
    @ColumnInfo(name = "id_product")
    var id_product: Int,
    @ColumnInfo(name = "quantity")
    var quantity: Int,
    @ColumnInfo(name = "price")
    var price: Int
)