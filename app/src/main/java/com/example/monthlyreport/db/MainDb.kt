package com.example.monthlyreport.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.monthlyreport.dao.ProductDao
import com.example.monthlyreport.dao.ReportDao
import com.example.monthlyreport.dao.UserDao

@Database(entities = [Product::class,Report::class,User::class], version = 1)
abstract class MainDb: RoomDatabase() {
    abstract fun getProductDao(): ProductDao
    abstract fun getReportDao(): ReportDao
    abstract fun getUserDao(): UserDao
    companion object{
        fun getDb(context: Context): MainDb{
            return Room.databaseBuilder(
                context.applicationContext,
                MainDb::class.java,
                "MonthlyReport"
            ).build()
        }
    }
}