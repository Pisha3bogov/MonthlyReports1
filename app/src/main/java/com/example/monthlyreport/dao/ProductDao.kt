package com.example.monthlyreport.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.example.monthlyreport.db.Product

@Dao()
interface ProductDao {
    @Insert
    fun insertProduct(product: Product)
    @Query("SELECT * FROM PRODUCTS")
    fun getAllProduct(): List<Product>
    @Update
    fun updateProduct(product: Product)
    @Delete
    fun deleteProduct(product: Product)
    @Query("SELECT name FROM PRODUCTS")
    fun getNameProduct(): List<String>
}