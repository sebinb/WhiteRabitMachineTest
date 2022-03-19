package com.accubits.whiterabitmachinetest.db

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.accubits.whiterabitmachinetest.model.Address
import com.accubits.whiterabitmachinetest.model.Company
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize
import javax.annotation.Nullable
@Parcelize
@Entity
data class EmployeeData(
    @PrimaryKey
    var id: Int,
    val name: String?,
    val username: String?,
    @Nullable
    val email: String?,
    @Nullable
    val profile_image: String?,
    @Nullable
    var address: String?,
    @Nullable
    val phone: String?,
    @Nullable
    val website: String?,
    @Nullable
    var company: String?
): Parcelable
