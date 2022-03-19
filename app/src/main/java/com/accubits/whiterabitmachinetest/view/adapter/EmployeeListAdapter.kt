package com.accubits.whiterabitmachinetest.view.adapter

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.RecyclerView
import com.accubits.whiterabitmachinetest.R
import com.accubits.whiterabitmachinetest.databinding.EmployeeItenBinding

import com.accubits.whiterabitmachinetest.db.EmployeeData
import com.accubits.whiterabitmachinetest.model.Company
import com.accubits.whiterabitmachinetest.model.EmployeeList
import com.bumptech.glide.Glide
import com.google.gson.Gson
import java.util.*
import javax.inject.Inject
import kotlin.collections.ArrayList

class EmployeeListAdapter @Inject constructor(private val gson: Gson) :
    RecyclerView.Adapter<EmployeeListAdapter.ImageViewHolder>(), Filterable {
    private var context: Context? = null
    private var employeeData: List<EmployeeData> = ArrayList()
    private var onItemClickListener: ((EmployeeData) -> Unit)? = null
    private var onItem: ((String) -> Unit)? = null
    var employeeDataFiltered: List<EmployeeData> = ArrayList()

    fun setItems(employeeData: List<EmployeeData>) {
        this.employeeData = employeeData
        employeeDataFiltered = employeeData
    }

    class ImageViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val tvName: TextView = view.findViewById(R.id.tvName)
        val tvCompanyName: TextView = view.findViewById(R.id.tvCompanyName)
        val ivProfile: ImageView = view.findViewById(R.id.ivProfile)
        val ivRoot: ConstraintLayout = view.findViewById(R.id.root)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ImageViewHolder {
        context = parent.context
        return ImageViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.employee_iten, parent, false)
        )
    }

    override fun onBindViewHolder(holder: ImageViewHolder, position: Int) {
        val company =
            Gson().fromJson(employeeDataFiltered.get(position).company, Company::class.java)

        holder.tvName.text = employeeDataFiltered[position]?.name ?: ""
        if (company != null)
            holder.tvCompanyName.text = company.name
        Glide
            .with(context!!)
            .load(employeeDataFiltered[position].profile_image ?: "")
            .centerCrop()
            .placeholder(R.drawable.ic_profile)
            .into(holder.ivProfile)
        holder.ivRoot.setOnClickListener {
            onItemClickListener?.let { it1 -> it1(employeeDataFiltered[position]) }

        }
    }

    fun setOnItemClickListener(listener: (EmployeeData) -> Unit) {
        onItemClickListener = listener
    }

    fun setDataValue(listener: (String) -> Unit) {
        onItem = listener
    }

    override fun getItemCount(): Int {
        return employeeDataFiltered.size
    }

    override fun getFilter(): Filter {
        return object : Filter() {
            override fun performFiltering(constraint: CharSequence?): FilterResults {
                val charString = constraint?.toString() ?: ""
                if (charString.isEmpty()) employeeDataFiltered =
                    employeeData as List<EmployeeData> else {
                    val filteredList = ArrayList<EmployeeData>()
                    employeeData.filter {
                        (it.name!!.uppercase().contains(constraint!!.toString().uppercase())) or
                                (it.email!!.contains(constraint!!.toString().uppercase()))

                    }
                        .forEach { filteredList.add(it) }
                    employeeDataFiltered = filteredList

                }
                return FilterResults().apply { values = employeeDataFiltered }
            }

            override fun publishResults(constraint: CharSequence?, results: FilterResults?) {
                if (results?.values == null) {
                    employeeDataFiltered = ArrayList()
                    onItem?.let { "0" }
                } else {
                    employeeDataFiltered = results.values as ArrayList<EmployeeData>
                    notifyDataSetChanged()
                    if (employeeDataFiltered.isEmpty()) {
                        onItem!!.invoke("0")
                    } else {
                        onItem!!.invoke("1")
                    }

                }
            }
        }
    }


}
