package com.shrikissan.user

import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.transition.Slide
import android.view.View
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AlertDialog
import com.denzcoskun.imageslider.constants.ScaleTypes
import com.denzcoskun.imageslider.interfaces.ItemClickListener
import com.denzcoskun.imageslider.models.SlideModel
import com.shrikissan.user.databinding.ActivityReviewBinding
import com.shrikissan.user.models.Review
import com.shrikissan.user.models.showToast
import com.shrikissan.user.network.Repository

class ReviewActivity : AppCompatActivity() {
    private lateinit var binding: ActivityReviewBinding
    private val imagesList = ArrayList<String>()
    private val list = ArrayList<SlideModel>()
    private lateinit var repository: Repository
    private var isReviewed = false
    private var id = ""
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityReviewBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.uploadImage.setOnClickListener {
            resultLauncher.launch("image/*")
        }
        repository = Repository(this)
        binding.button2.setOnClickListener {
            val rating = binding.ratingBar.rating
            val reviewText = binding.review.text.toString()
            if (rating < 1f) {
                showToast("Minimum rating is 1")
                return@setOnClickListener
            }
            val review = Review()
            review.reviewText = reviewText
            review.rating = rating
            review.images = imagesList
            review(review)
        }
        id = intent.getStringExtra("id")?:"id"
        val name = intent.getStringExtra("name")?:"name"
        binding.name.text = name
        repository.getReview(id){
            if(it!=null){
                isReviewed = true
                setValues(it)
            }
        }
    }

    private fun setValues(it: Review) {
        binding.ratingBar.rating = it.rating
        binding.ratingBar.setIsIndicator(true)
        binding.review.setText(it.reviewText)
        binding.review.isEnabled = false
        binding.uploadImage.visibility = View.GONE
        val slList = ArrayList<SlideModel>()
        it.images.forEach {
            slList.add(SlideModel(it))
        }
        binding.imageSlider.setImageList(slList,ScaleTypes.FIT)
        binding.button2.isClickable = false
    }

    private val resultLauncher = registerForActivityResult(ActivityResultContracts.GetContent()) {
        if (it != null) {
            imagesList.add(it.toString())
            list.add(SlideModel(it.toString()))
            binding.imageSlider.setImageList(list, ScaleTypes.CENTER_INSIDE)
            binding.imageSlider.setItemClickListener(object : ItemClickListener {
                override fun onItemSelected(position: Int) {
                    AlertDialog.Builder(this@ReviewActivity)
                        .setTitle("Delete Image?")
                        .setPositiveButton("Yes") { dial, _ ->
                            dial.dismiss()
                            imagesList.removeAt(position)
                            list.removeAt(position)
                            binding.imageSlider.setImageList(list)
                        }
                        .setNegativeButton("No") { dial, _ ->
                            dial.dismiss()
                        }
                        .show()
                }
            })
        }
    }

    private fun review(review: Review) {
        val dialog = CustomProgressDialog(this)
        dialog.show()
        repository.addReview(id,review){
            dialog.dismiss()
            if(it){
                showToast("Saved")
            }
            else{
                showToast("Unknown error occurred")
            }
        }
    }
}