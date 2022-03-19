package com.accubits.whiterabitmachinetest.view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.accubits.whiterabitmachinetest.R
import com.accubits.whiterabitmachinetest.databinding.ActivityEmployeeDetailBinding
import com.accubits.whiterabitmachinetest.db.EmployeeData
import com.accubits.whiterabitmachinetest.helpers.EmployeeConstants
import com.accubits.whiterabitmachinetest.model.Address
import com.accubits.whiterabitmachinetest.model.Company
import com.bumptech.glide.Glide
import com.google.gson.Gson
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class EmployeeDetailActivity : AppCompatActivity() {
    @Inject
    lateinit var gson: Gson
    private lateinit var activityEmployeeDetailBinding: ActivityEmployeeDetailBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        activityEmployeeDetailBinding = ActivityEmployeeDetailBinding.inflate(layoutInflater)
        setContentView(activityEmployeeDetailBinding.root)
        var mydata = intent?.getParcelableExtra<EmployeeData>(EmployeeConstants.EMPLOYEE)
        if (mydata != null) {
            val company = Gson().fromJson(mydata.company, Company::class.java)
            val address = Gson().fromJson(mydata.address, Address::class.java)
            if (company != null) {
                activityEmployeeDetailBinding.tvCompanyName.text = company.name ?: "--"
                activityEmployeeDetailBinding.tvBs.text = company.bs ?: "--"
                activityEmployeeDetailBinding.tvCatch.text = company.catchPhrase ?: "--"
            }
            if (address != null) {
                activityEmployeeDetailBinding.tvCity.text = address.city ?: "--"
                activityEmployeeDetailBinding.tvZip.text = address.zipcode ?: "--"
                activityEmployeeDetailBinding.tvStreet.text = address.street ?: "--"
                activityEmployeeDetailBinding.tvSuite.text = address.suite ?: "--"
            }
            activityEmployeeDetailBinding.tvName.text = mydata.name ?: "--"
            activityEmployeeDetailBinding.TvEmail.text = mydata.email ?: "--"
            activityEmployeeDetailBinding.tvUserName.text = mydata.username ?: "--"
            activityEmployeeDetailBinding.tvCity.text = mydata.name ?: "--"

            Glide
                .with(this)
                .load(mydata.profile_image ?: "")
                .centerCrop()
                .placeholder(R.drawable.ic_profile)
                .into(activityEmployeeDetailBinding.ivProfile)
        }

    }
}