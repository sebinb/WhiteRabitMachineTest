package com.accubits.whiterabitmachinetest.repository

import com.accubits.whiterabitmachinetest.api.ApiCallStatus
import com.accubits.whiterabitmachinetest.api.ApiMapper
import com.accubits.whiterabitmachinetest.api.RetrofitApi
import com.accubits.whiterabitmachinetest.db.AppDatabase
import com.accubits.whiterabitmachinetest.db.EmployeeData
import com.accubits.whiterabitmachinetest.model.EmployeeList
import com.google.gson.Gson
import retrofit2.HttpException

class EmployeeRepository constructor(
    private val retrofitApi: RetrofitApi,
    private val gson: Gson,
    private val appDatabase: AppDatabase
) {
    suspend fun getEmployeeList() = try {
        val response = retrofitApi.getEmployeeList()
        ApiMapper(ApiCallStatus.SUCCESS, response, null)
    } catch (ex: HttpException) {
        ApiMapper(ApiCallStatus.ERROR, null, ex.message)
    }

    suspend fun setData(data: List<EmployeeData>) = try {
        val response = appDatabase.employeeDao().insertAllOrders(data)
    } catch (ex: HttpException) {
    }

    suspend fun getEmployeeListFromDb() = try {
        val response = appDatabase.employeeDao().loadAllUsers()
        ApiMapper(ApiCallStatus.SUCCESS, response, null)
    } catch (ex: HttpException) {
        ApiMapper(ApiCallStatus.ERROR, null, ex.message)
    }
}
