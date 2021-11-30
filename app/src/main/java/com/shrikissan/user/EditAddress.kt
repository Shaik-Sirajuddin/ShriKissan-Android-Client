package com.shrikissan.user

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import com.shrikissan.user.databinding.ActivityEditAddressBinding
import com.shrikissan.user.models.Address
import com.shrikissan.user.models.showToast
import com.shrikissan.user.network.Repository
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.json.Json

class EditAddress : AppCompatActivity(), AdapterView.OnItemSelectedListener {
    private lateinit var binding: ActivityEditAddressBinding
    private var isNew = true
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        try {
            binding = ActivityEditAddressBinding.inflate(layoutInflater)
            setContentView(binding.root)
            val adapter = ArrayAdapter.createFromResource(this,
                R.array.states,
                android.R.layout.simple_spinner_item)
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            binding.spinner.adapter = adapter
            binding.spinner.onItemSelectedListener = this
            val address = intent.getStringExtra("address")
            if (address != null) {
                isNew = false
                val add = Json.decodeFromString<Address>(address)
                setValues(add)
            }
            binding.saveButton.setOnClickListener {
                val add = checkValues()
                if (add != null) {
                    saveAddress(add)
                } else {
                    showToast("Fill All Fields")
                }
            }
        } catch (e: Exception) {
            Log.e("edit",e.message.toString())
        }
    }

    private fun saveAddress(address: Address) {
        val repository = Repository(this)
        if(isNew){
            repository.addAddress(address)
        }
        else{
            repository.updateAddress(address)
        }
    }

    private fun checkValues(): Address? {
        val name = binding.name.text.toString().trim()
        val city = binding.city.text.toString().trim()
        val landMark = binding.landmark.text.toString().trim()
        val lane = binding.lane.text.toString().trim()
        val number = binding.mobileNumber.text.toString().trim()
        val pinCode = binding.pinCode.text.toString().trim()
        val address = Address("", name, lane, landMark, city, state = "", number, pinCode.toInt())
        var check = true
        if (name.isEmpty()) {
            binding.name.error = ""
            check = false
        }
        if (city.isEmpty()) {
            binding.city.error = ""
            check = false
        }
        if (landMark.isEmpty()) {
            binding.landmark.error = ""
            check = false
        }
        if (lane.isEmpty()) {
            binding.lane.error = ""
            check = false
        }
        if (number.isEmpty()) {
            binding.mobileNumber.error = ""
            check = false
        }
        if (pinCode.isEmpty()) {
            binding.pinCode.error = ""
            check = false
        }
        if (check) {
            return address
        }
        return null
    }

    private fun setValues(address: Address) {
        binding.city.setText(address.city)
        binding.landmark.setText(address.landmark)
        binding.lane.setText(address.lane)
        binding.mobileNumber.setText(address.phoneNumber)
        binding.pinCode.setText(address.pinCode)
    }

    override fun onItemSelected(p0: AdapterView<*>?, p1: View?, p2: Int, p3: Long) {
        val text = p0?.getItemAtPosition(p2).toString()
        showToast(text)
    }

    override fun onNothingSelected(p0: AdapterView<*>?) {

    }

}