package com.shrikissan.user

import android.Manifest
import android.content.pm.PackageManager
import android.location.Geocoder
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Looper
import android.util.Log
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import com.shrikissan.user.databinding.ActivityEditAddressBinding
import com.shrikissan.user.models.Address
import com.shrikissan.user.models.showToast
import com.shrikissan.user.network.Repository
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.json.Json
import java.util.*

class EditAddress : AppCompatActivity(), AdapterView.OnItemSelectedListener {
    private lateinit var binding: ActivityEditAddressBinding
    private var isNew = true
    private var state1 = "AndhraPradesh"
    private var addId = ""
    private lateinit var client:FusedLocationProviderClient
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        try {
            binding = ActivityEditAddressBinding.inflate(layoutInflater)
            supportActionBar?.hide()
            setContentView(binding.root)
            val adapter = ArrayAdapter.createFromResource(this,
                R.array.states,
                android.R.layout.simple_spinner_item)
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            binding.spinner.adapter = adapter
            binding.spinner.onItemSelectedListener = this
            client = LocationServices.getFusedLocationProviderClient(this)
            val address = intent.getStringExtra("address")
            if (address != null) {
                isNew = false
                val add = Json.decodeFromString<Address>(address)
                setValues(add)
            }
            else{
                checkLocationPermission()
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
            Log.e("edit", e.message.toString())
        }
    }

    private fun checkLocationPermission() {
        if (ActivityCompat.checkSelfPermission(this,
                Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(
                this,
                Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED
        ) {
            requestLocationPermission()
        }
        else{
            getLocation()
        }
    }

    private fun requestLocationPermission() {
        ActivityCompat.requestPermissions(
            this,
            arrayOf(
                Manifest.permission.ACCESS_FINE_LOCATION,
                Manifest.permission.ACCESS_COARSE_LOCATION
            ),
            1
        )
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<String>,
        grantResults: IntArray,
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        when (requestCode) {
            1 -> {
               getLocation()
            }
        }
    }
    private fun getLocation(){
       try {
           if (ActivityCompat.checkSelfPermission(this,
                   Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(
                   this,
                   Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED
           ) {
               return
           }
           client.lastLocation.addOnCompleteListener {
                val location = it.result
               val coder = Geocoder(this, Locale.getDefault())
                val address = coder.getFromLocation(location.latitude,location.longitude,1)
               binding.pinCode.setText(address[0].postalCode)
               binding.lane.setText(address[0].getAddressLine(0))
               binding.city.setText(address[0].locality)
           }
       }
       catch (e:Exception){
           e.printStackTrace()
       }
    }
    private fun saveAddress(address: Address) {
        val repository = Repository(this)
        val dialog = CustomProgressDialog(this)
        dialog.show()
        if (isNew) {
            repository.addAddress(address) {
                dialog.dismiss()
                if (it) {
                    showToast("Address Saved")
                    finish()
                } else {
                    showToast("Unknown error occurred")
                }
            }
        } else {
            repository.updateAddress(address) {
                dialog.dismiss()
                if (it) {
                    showToast("Address Saved")
                    finish()
                } else {
                    showToast("Unknown error occurred")
                }
            }
        }
    }

    private fun checkValues(): Address? {
        try {
            val name = binding.name.text.toString().trim()
            val city = binding.city.text.toString().trim()
            val landMark = binding.landmark.text.toString().trim()
            val lane = binding.lane.text.toString().trim()
            val number = binding.mobileNumber.text.toString().trim()
            val pinCode = binding.pinCode.text.toString().trim()

            val address =
                Address(addId, name, lane, landMark, city, state1, number, pinCode.toInt())

            var check = true
            if (name.isEmpty()) {
                binding.name.error = "*"
                check = false
            }
            if (city.isEmpty()) {
                binding.city.error = "*"
                check = false
            }
            if (landMark.isEmpty()) {
                binding.landmark.error = "*"
                check = false
            }
            if (lane.isEmpty()) {
                binding.lane.error = "*"
                check = false
            }
            if (number.isEmpty()) {
                binding.mobileNumber.error = "*"
                check = false
            }
            if (pinCode.isEmpty()) {
                binding.pinCode.error = "*"
                check = false
            }
            if (check) {
                return address
            }
        } catch (e: NumberFormatException) {
            Log.e("format", e.message.toString())
            showToast("Invalid pincode")
        }
        return null
    }

    private fun setValues(address: Address) {
        addId = address.id
        binding.city.setText(address.city)
        binding.landmark.setText(address.landmark)
        binding.lane.setText(address.lane)
        binding.mobileNumber.setText(address.phoneNumber.toString())
        binding.pinCode.setText(address.pinCode.toString())
        binding.name.setText(address.name)
        val array = resources.getStringArray(R.array.states)
        val ind = array.indexOf(address.state)
        if (ind >= 0 && ind < array.size) {
            binding.spinner.setSelection(ind)
        }
    }

    override fun onItemSelected(p0: AdapterView<*>?, p1: View?, p2: Int, p3: Long) {
        state1 = p0?.getItemAtPosition(p2).toString()
    }

    override fun onNothingSelected(p0: AdapterView<*>?) {

    }

}