package com.accubits.whiterabitmachinetest.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.accubits.whiterabitmachinetest.api.ApiCallStatus
import com.accubits.whiterabitmachinetest.api.ApiMapper
import com.accubits.whiterabitmachinetest.db.EmployeeData
import com.accubits.whiterabitmachinetest.model.EmployeeList
import com.accubits.whiterabitmachinetest.repository.EmployeeRepository
import com.google.gson.Gson
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class EmployeeViewModel @Inject constructor(
    private val employeeRepository: EmployeeRepository,
    private val gson: Gson
) :
    ViewModel() {
    var employeeSuccessModel =
        MutableStateFlow<ApiMapper<List<EmployeeList>>>(
            ApiMapper(
                ApiCallStatus.EMPTY,
                null,
                null
            )
        )

    var employeeData =
        MutableStateFlow<ApiMapper<List<EmployeeData>>>(
            ApiMapper(
                ApiCallStatus.EMPTY,
                null,
                null
            )
        )

    fun getEmployeeList() {
        employeeSuccessModel = MutableStateFlow<ApiMapper<List<EmployeeList>>>(
            ApiMapper(
                ApiCallStatus.LOADING,
                null,
                null
            )
        )
        viewModelScope.launch {
            val response = employeeRepository.getEmployeeList()
            when (response.status) {
                ApiCallStatus.SUCCESS -> {
                    employeeSuccessModel.value =
                        ApiMapper(ApiCallStatus.SUCCESS, response.data, null)
                }
                ApiCallStatus.ERROR -> {
                    employeeSuccessModel.value =
                        ApiMapper(ApiCallStatus.ERROR, null, response.errorMessage)
                }
            }
        }
    }

    fun getEmployeeListFromDb() {
        employeeData = MutableStateFlow<ApiMapper<List<EmployeeData>>>(
            ApiMapper(
                ApiCallStatus.LOADING,
                null,
                null
            )
        )
        viewModelScope.launch(Dispatchers.IO) {
            val response = employeeRepository.getEmployeeListFromDb()
            when (response.status) {
                ApiCallStatus.SUCCESS -> {
                    employeeData.value =
                        ApiMapper(ApiCallStatus.SUCCESS, response.data, null)
                }
                ApiCallStatus.ERROR -> {
                    employeeData.value =
                        ApiMapper(ApiCallStatus.ERROR, null, response.errorMessage)
                }
            }
        }
    }

    fun setDataToDb(data: List<EmployeeList>) {
        viewModelScope.launch(Dispatchers.IO) {
            var employeeData= mutableListOf<EmployeeData>()


            for (i in data) {
               var employee = EmployeeData(
                    i.id,
                    i.name,
                    i.username,
                    i.email,
                    i.profile_image,
                    gson.toJson(i.address),
                    i.phone,
                    i.website,
                    gson.toJson(i.company)
                )
                employeeData.add(employee)
            }

            employeeRepository.setData(employeeData)
        }
    }
}