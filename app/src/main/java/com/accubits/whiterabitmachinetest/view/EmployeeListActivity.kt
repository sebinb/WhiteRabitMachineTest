package com.accubits.whiterabitmachinetest.view

import android.content.Intent
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.widget.Adapter
import android.widget.Toast
import androidx.activity.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.accubits.whiterabitmachinetest.R
import com.accubits.whiterabitmachinetest.api.ApiCallStatus
import com.accubits.whiterabitmachinetest.databinding.ActivityMainBinding
import com.accubits.whiterabitmachinetest.helpers.EmployeeConstants.EMPLOYEE
import com.accubits.whiterabitmachinetest.helpers.Utils
import com.accubits.whiterabitmachinetest.view.adapter.EmployeeListAdapter
import com.accubits.whiterabitmachinetest.viewmodel.EmployeeViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

@AndroidEntryPoint
class EmployeeListActivity : AppCompatActivity() {
    private val viewModel by viewModels<EmployeeViewModel>()
    private lateinit var activityMainBinding: ActivityMainBinding

    @Inject
    lateinit var employeeLisAdapter: EmployeeListAdapter


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        activityMainBinding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(activityMainBinding.root)
        activityMainBinding.employeeList.layoutManager =
            LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)


        if (Utils.isNetworkAvailable(this)!!) {
            viewModel.getEmployeeList()
            viewModel.getEmployeeListFromDb()
        } else {
            viewModel.getEmployeeListFromDb()

        }
        updateUi()

        employeeLisAdapter.setOnItemClickListener {
            val intent = Intent(this, EmployeeDetailActivity::class.java).apply {
                putExtra(EMPLOYEE, it)
            }
            startActivity(intent)
        }

        employeeLisAdapter.setDataValue{
            if(it=="0"){
                activityMainBinding.tvNoData.visibility=View.VISIBLE
                activityMainBinding.employeeList.visibility=View.GONE
            }else{
                activityMainBinding.tvNoData.visibility=View.GONE
                activityMainBinding.employeeList.visibility=View.VISIBLE
            }

        }
        activityMainBinding.etName.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable) {}
            override fun beforeTextChanged(
                s: CharSequence,
                start: Int,
                count: Int,
                after: Int
            ) {
            }

            override fun onTextChanged(
                s: CharSequence,
                start: Int,
                before: Int,
                count: Int
            ) {

                    employeeLisAdapter.filter.filter(s)


            }

        })
    }

    private fun updateUi() {
        lifecycleScope.launch {
            viewModel.employeeSuccessModel.collectLatest { response ->
                when (response.status) {
                    ApiCallStatus.LOADING -> {
                        activityMainBinding.progressBar.visibility = View.VISIBLE
                    }
                    ApiCallStatus.SUCCESS -> {
                        activityMainBinding.progressBar.visibility = View.GONE
                        if (response.data!!.isNotEmpty()) {
                            viewModel.setDataToDb(response.data)
                        } else {
                            Toast.makeText(
                                this@EmployeeListActivity,
                                "Something went wrong",
                                Toast.LENGTH_LONG
                            )
                                .show()
                        }
                    }
                    ApiCallStatus.ERROR -> {
                        activityMainBinding.progressBar.visibility = View.GONE
                        Toast.makeText(
                            this@EmployeeListActivity,
                            "Something went wrong",
                            Toast.LENGTH_LONG
                        )
                            .show()
                    }
                }
            }
        }

        lifecycleScope.launch {
            viewModel.employeeData.collectLatest { response ->
                when (response.status) {
                    ApiCallStatus.LOADING -> {
                        activityMainBinding.progressBar.visibility = View.VISIBLE
                    }
                    ApiCallStatus.SUCCESS -> {
                        activityMainBinding.progressBar.visibility = View.GONE
                        if (response.data!!.isNotEmpty()) {
                            employeeLisAdapter.setItems(response.data)
                            activityMainBinding.employeeList.adapter = employeeLisAdapter
                            activityMainBinding.tvNoData.visibility = View.GONE
                            activityMainBinding.employeeList.visibility = View.VISIBLE
                        } else {
                            activityMainBinding.tvNoData.visibility = View.VISIBLE
                            activityMainBinding.employeeList.visibility = View.GONE
                        }
                    }
                    ApiCallStatus.ERROR -> {
                        activityMainBinding.progressBar.visibility = View.GONE
                        Toast.makeText(
                            this@EmployeeListActivity,
                            "Something went wrong",
                            Toast.LENGTH_LONG
                        )
                            .show()
                    }
                }
            }
        }
    }
}