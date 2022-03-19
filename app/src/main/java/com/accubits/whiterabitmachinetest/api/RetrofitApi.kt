package com.accubits.whiterabitmachinetest.api
import com.accubits.whiterabitmachinetest.model.EmployeeList
import retrofit2.http.*

interface RetrofitApi {
    companion object {
        const val BASE_URL = "http://www.mocky.io/"
    }


    @GET("v2/5d565297300000680030a986//")
    suspend fun getEmployeeList(): List<EmployeeList>



}