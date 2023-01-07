package com.shrikissan.user

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Base64
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import androidx.activity.result.contract.ActivityResultContracts
import com.bumptech.glide.Glide
import com.denzcoskun.imageslider.constants.ScaleTypes
import com.denzcoskun.imageslider.models.SlideModel
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.core.Repo
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import com.shrikissan.user.databinding.ActivityContactUsBinding
import com.shrikissan.user.models.Constants
import com.shrikissan.user.models.FireConsts
import com.shrikissan.user.models.showToast
import com.shrikissan.user.network.Repository
import java.io.ByteArrayOutputStream
import java.io.InputStream
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList
import kotlin.collections.HashMap

class ContactUsActivity : AppCompatActivity(), AdapterView.OnItemSelectedListener {
    private lateinit var binding: ActivityContactUsBinding
    private var request: String = ""
    private var pos = 0
    private val list = ArrayList<String>()
    private val encodedList = ArrayList<String>()
    private val offlineList = ArrayList<SlideModel>()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityContactUsBinding.inflate(layoutInflater)
        setContentView(binding.root)
        val adapter = ArrayAdapter.createFromResource(this,
            R.array.requests,
            android.R.layout.simple_spinner_item)
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        binding.spinner.adapter = adapter
        binding.spinner.onItemSelectedListener = this
        binding.send.setOnClickListener {
            if (check()) {
                when (pos) {
                    0 -> {
                        makeRequest(
                            binding.name.text.toString(),
                            binding.mobileNumber.text.toString(),
                            binding.topic.text.toString(),
                            FireConsts.VoiceRequest,
                            FireConsts.VoiceType
                        )
                    }
                    1 -> {
                        makeRequest(
                            binding.name.text.toString(),
                            binding.mobileNumber.text.toString(),
                            binding.topic.text.toString(),
                            FireConsts.VideoRequest,
                            FireConsts.VideoType
                        )

                    }
                    else -> {
                        makeRequest(
                            binding.name.text.toString(),
                            binding.mobileNumber.text.toString(),
                            binding.topic.text.toString(),
                            FireConsts.soilRequest,
                            FireConsts.soilType
                        )
                    }
                }
            } else {
                showToast("Fill all fields")
            }
        }
        binding.upload.setOnClickListener {
            resultLauncher.launch("image/*")
        }
    }

    private val resultLauncher = registerForActivityResult(ActivityResultContracts.GetContent()) {
        if (it != null) {
            updateImage(it)
        }
    }

    private fun updateImage(uri: Uri) {
        val imageStream: InputStream? = contentResolver.openInputStream(uri)
        val selectedImage = BitmapFactory.decodeStream(imageStream)
        val encodedImage = encodeImage(selectedImage)
        encodedList.add(encodedImage)
        val slide = SlideModel(uri.toString())
        offlineList.add(slide)
        binding.imageSlider.setImageList(offlineList,ScaleTypes.FIT)
        binding.imageSlider.visibility = View.VISIBLE
    }

    private fun encodeImage(bm: Bitmap): String {
        val baos = ByteArrayOutputStream()
        bm.compress(Bitmap.CompressFormat.JPEG, 100, baos)
        val b: ByteArray = baos.toByteArray()
        return Base64.encodeToString(b, Base64.DEFAULT)
    }

    private fun makeRequest(
        name: String,
        number: String,
        topic: String,
        request: String,
        type: String,
    ) {
        val dialog = CustomProgressDialog(this)
        dialog.show()
        val map = HashMap<String, Any>()
        map["Name"] = name
        map["WhatsAppNumber"] = number
        map["Topic"] = topic
        map["Type"] = type
        uploadImages{
            map["images"] = list
            sendRequest(map, request){
                dialog.dismiss()
            }
        }
    }

    private fun uploadImages(onComplete: () -> Unit) {
         val repository = Repository(this)
         var counter = 0
        for(i in encodedList.indices){
            repository.uploadAndGetUri(encodedList[i]){
                counter++;
                if (it != null) {
                    list.add(it)
                }
                if(counter == encodedList.size){
                    onComplete()
                }
            }
        }
    }

    private fun sendRequest(map: HashMap<String, Any>, request: String,onComplete: () -> Unit) {
        val c: Date = Calendar.getInstance().time
        val df = SimpleDateFormat("dd-MMM-yyyy", Locale.getDefault())
        val date: String = df.format(c)
        val ref =
            Firebase.database("https://shrikissan-9cabf-default-rtdb.asia-southeast1.firebasedatabase.app")
                .reference
        ref.child(FireConsts.Support)
            .child(date)
            .child(request)
            .push()
            .setValue(map)
            .addOnCompleteListener {
                onComplete()
                if (it.isSuccessful) {
                    showToast("Request Sent")
                } else {
                    showToast("Failed to send request")
                }
            }
    }

    private fun check(): Boolean {
        var check = true
        if (binding.name.text.isEmpty()) {
            binding.name.error = "*"
            check = false
        }
        if (binding.mobileNumber.text.isEmpty()) {
            binding.mobileNumber.error = "*"
            check = false
        }
        if (binding.topic.text.isEmpty()) {
            binding.topic.error = "*"
            check = false
        }
        return check
    }

    override fun onItemSelected(p0: AdapterView<*>?, p1: View?, p2: Int, p3: Long) {
        request = p0?.getItemAtPosition(p2).toString()
        pos = p2
    }

    override fun onNothingSelected(p0: AdapterView<*>?) {

    }
}