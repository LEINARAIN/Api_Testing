package com.apitesting

import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.GridLayout
import android.widget.ImageButton
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.apitesting.databinding.ActivityShoeItemDetailsBinding
import com.bumptech.glide.Glide

class ShoeItemDetails : AppCompatActivity() {

    private var selectedSizeButton: Button? = null
    private val cartItems = mutableListOf<CartItem>()

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
        binding.productTitleTextView.text = shoeName
        binding.productPriceTextView.text = "PHP $shoePrice"
        binding.productNameBelowImage.text = shoeName
        binding.productDescriptionTextView.text = shoeDescription

        //Image fetching
        Glide.with(this)
            .load(shoeImageUrl)
            .placeholder(R.drawable.placeholder_image)
            .error(R.drawable.baseline_error_outline_24)
            .into(binding.productImageView)

        // Back button
        binding.backButton.setOnClickListener {
            finish()
        }

        // Set up size buttons
        val sizesGridLayout: GridLayout = findViewById(R.id.sizesGridLayout)
        for (size in 6..10) {
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

        // Cart button click listener
        val cartButton: ImageButton = findViewById(R.id.detailCartButton)
        cartButton.setOnClickListener{
            val intent = Intent (this, CartActivity::class.java)
            startActivity(intent)
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

    fun addToCart(view: View) {
        val shoeId = intent.getIntExtra("SHOE_ID", -1)
        val shoeName = intent.getStringExtra("SHOE_NAME")
        val shoePrice = intent.getStringExtra("SHOE_PRICE")
        val shoeImageUrl = intent.getStringExtra("SHOE_IMAGE_URL")
        val selectedSize = selectedSizeButton?.text.toString() // Get the selected size

        val cartItem = CartItem(shoeId, Shoe(shoeId, shoeName.orEmpty(), "", shoeImageUrl.orEmpty(), shoePrice.orEmpty(), "", ""), 1, selectedSize)
        cartItems.add(cartItem)

        Toast.makeText(this, "Added to cart", Toast.LENGTH_SHORT).show()

        // Clear the selected size button
        selectedSizeButton?.isSelected = false
    }
}
