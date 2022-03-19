package com.accubits.whiterabitmachinetest.db

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.accubits.whiterabitmachinetest.model.EmployeeList

@Dao
interface EmployeeDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
     fun insertAllOrders(data:List<EmployeeData>)

    @Query("SELECT * FROM EmployeeData")
    fun loadAllUsers(): List<EmployeeData>
}