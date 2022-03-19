package com.accubits.whiterabitmachinetest.db

import androidx.room.Database
import androidx.room.RoomDatabase
import com.accubits.whiterabitmachinetest.model.EmployeeList
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent


@Database(entities = [EmployeeData::class], version = 1 ,exportSchema = false)
abstract class AppDatabase : RoomDatabase() {
    abstract fun employeeDao(): EmployeeDao
}