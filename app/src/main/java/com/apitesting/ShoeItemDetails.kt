package com.apitesting

import android.graphics.Color
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.GridLayout
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.apitesting.databinding.ActivityShoeItemDetailsBinding
import com.bumptech.glide.Glide


class ShoeItemDetails : AppCompatActivity() {

    private var selectedSizeButton: Button? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = ActivityShoeItemDetailsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Retrieve other shoe details as needed
        val shoeName = intent.getStringExtra("SHOE_NAME")
        val shoePrice = intent.getStringExtra("SHOE_PRICE")
        val shoeDescription = intent.getStringExtra("SHOE_DESCRIPTION")
        val shoeId = intent.getIntExtra("SHOE_ID", -1)

        val shoeImageUrl = intent.getStringExtra("SHOE_IMAGE_URL")

        // Display other shoe details as needed
        binding.productTitleTextView.text = "$shoeName"
        binding.productPriceTextView.text = "PHP $shoePrice"
        binding.productNameBelowImage.text = "$shoeName"
        binding.productDescriptionTextView.text = "$shoeDescription"


        //Image fetching
        Glide.with(this)
            .load(shoeImageUrl)
            .placeholder(R.drawable.placeholder_image)
            .error(R.drawable.baseline_error_outline_24)
            .into(binding.productImageView)

        //Back button
        val backButton: View = findViewById(R.id.backButton)
        backButton.setOnClickListener {
            finish()
        }

        // Set up size buttons
        val sizesGridLayout: GridLayout = findViewById(R.id.sizesGridLayout)
        for (size in 41..48) {
            val sizeButton = Button(this)
            sizeButton.text = size.toString()
            sizeButton.setBackgroundColor(Color.TRANSPARENT)
            sizeButton.setTextColor(Color.BLACK)
            sizeButton.textSize = 16f // Set your desired text size
            sizeButton.setBackgroundResource(R.drawable.size_button_background) // Set button background
            sizeButton.setOnClickListener {
                handleSizeSelection(sizeButton)
                selectSize(sizeButton)
            }
            sizesGridLayout.addView(sizeButton)
        }
    }

    private fun handleSizeSelection(selectedButton: Button) {
        selectedSizeButton?.apply {
            // Reset previously selected button's state
            isSelected = false
        }
        selectedButton.isSelected = true
        selectedSizeButton = selectedButton
    }

    private fun selectSize(selectedButton: Button) {
        val selectedSize = selectedButton.text.toString()
        Toast.makeText(this, "Selected size: $selectedSize", Toast.LENGTH_SHORT).show()
    }
}
