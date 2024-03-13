package com.apitesting

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.apitesting.databinding.ActivityShoeItemDetailsBinding
import com.bumptech.glide.Glide


class ShoeItemDetails : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = ActivityShoeItemDetailsBinding.inflate(layoutInflater)
        setContentView(binding.root)


        val shoeName = intent.getStringExtra("SHOE_NAME")
        val shoePrice = intent.getStringExtra("SHOE_PRICE")
        val shoeDescription = intent.getStringExtra("SHOE_DESCRIPTION")
        val shoeId = intent.getIntExtra("SHOE_ID", -1)

        // Retrieve other shoe details as needed

        val shoeImageUrl = intent.getStringExtra("SHOE_IMAGE_URL")

        //val shoeImageURL = ShoeImage.getImageURLs(shoeId)

        binding.productTitleTextView.text = "$shoeName"
        binding.productPriceTextView.text = "$shoePrice"
        binding.productNameBelowImage.text = "$shoeName"
        binding.productDescriptionTextView.text = "$shoeDescription"
        // Display other shoe details as needed

        Glide.with(this)
            .load(shoeImageUrl)
            .placeholder(R.drawable.placeholder_image)
            .error(R.drawable.baseline_error_outline_24)
            .into(binding.productImageView)
    }
}
