package com.accubits.whiterabitmachinetest.model

import androidx.room.Entity
import androidx.room.Ignore
import androidx.room.PrimaryKey
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
data class EmployeeList(
    @Expose
    @SerializedName("id")
    var id: Int,
    @Expose
    @SerializedName("name")
    val name: String,
    @Expose
    @SerializedName("username")
    val username: String,
    @Expose
    @SerializedName("email")
    val email: String,
    @Expose
    @SerializedName("profile_image")
    val profile_image: String,
    @Expose
    @SerializedName("address")
    var address: Address,
    @Expose
    @SerializedName("phone")
    val phone: String,
    @Expose
    @SerializedName("website")
    val website: String,
    @Expose
    @SerializedName("company")
    var company: Company

)

data class Company(
    @Expose
    @SerializedName("name")
    val name: String?,
    @Expose
    @SerializedName("catchPhrase")
    val catchPhrase: String?,
    @Expose
    @SerializedName("bs")
    val bs: String?
)

data class Address(
    @Expose
    @SerializedName("street")
    val street: String?,
    @Expose
    @SerializedName("suite")
    val suite: String?,
    @Expose
    @SerializedName("city")
    val city: String?,
    @Expose
    @SerializedName("zipcode")
    val zipcode: String?,
    @Expose
    @SerializedName("geo")
    var geo: Geo
)

data class Geo(
    @Expose
    @SerializedName("lat")
    val lat: String,
    @Expose
    @SerializedName("lng")
    val lng: String
)
