package com.example.monthlyreport.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.example.monthlyreport.db.Product
import com.example.monthlyreport.db.Report

@Dao
interface ReportDao {
    @Insert
    fun insertReport(report: Report)
    @Query("SELECT * FROM reports")
    fun getAllReport(): List<Report>
    @Update
    fun updateReport(report: Report)
    @Delete
    fun deleteReport(report: Report)
}