package com.apitesting

import android.content.Intent
import android.os.Bundle
import android.widget.ImageButton
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.LiveData
import androidx.lifecycle.Observer
import androidx.lifecycle.liveData
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.apitesting.databinding.ActivityDashboardBinding
import com.bumptech.glide.Glide
import retrofit2.Response
import java.util.Timer
import java.util.TimerTask

class Dashboard : AppCompatActivity() {

    private lateinit var binding: ActivityDashboardBinding
    //Image Slides
    private lateinit var imageView: ImageView
    private val imageUrls = listOf(
        "https://www.sneakersphere.online/images/12.png",
        "https://www.sneakersphere.online/images/1.png",
        "https://www.sneakersphere.online/images/11.png",
        "https://www.sneakersphere.online/images/2.png",
        "https://www.sneakersphere.online/images/10.png",
        "https://www.sneakersphere.online/images/3.png",
        "https://www.sneakersphere.online/images/9.png",
        "https://www.sneakersphere.online/images/4.png",
        "https://www.sneakersphere.online/images/8.png",
        "https://www.sneakersphere.online/images/5.png",
        "https://www.sneakersphere.online/images/7.png",
        "https://www.sneakersphere.online/images/6.png"
    )
    private var currentPage = 0
    private lateinit var timer: Timer

    //Shoes RecyclerView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_dashboard)
        //super.onCreate(savedInstanceState)
        //binding = ActivityDashboardBinding.inflate(layoutInflater)
        //setContentView(binding.root)

        //Image
        imageView = findViewById(R.id.imageView)

        // Start automatic image slider
        startImageSlider()

        val recyclerView: RecyclerView = findViewById(R.id.productRecyclerView)
        recyclerView.layoutManager = GridLayoutManager(this, 2)

        val adapter = ShoeItemRecyclerViewAdapter(emptyList()) { shoe, imageUrl ->
            val intent = Intent(this, ShoeItemDetails::class.java)
            intent.putExtra("SHOE_NAME", shoe.name)
            intent.putExtra("SHOE_PRICE", shoe.price)
            intent.putExtra("SHOE_DESCRIPTION", shoe.description)
            intent.putExtra("SHOE_IMAGE_URL", imageUrl)
            startActivity(intent)
        }
        // Initialize adapter with empty list
        recyclerView.adapter = adapter

        val retrofitService = RetrofitInstance.getRetrofitInstance().create(ShoeService::class.java)

        val responseLiveData: LiveData<Response<ShoeItem>> =
            liveData {
                val response = retrofitService.getShoeItem()
                emit(response)
            }

        responseLiveData.observe(this, Observer { response ->
            val shoeItem = response.body()
            shoeItem?.let {
                val shoeList = it.shoes
                adapter.setData(shoeList) // Update adapter data with the fetched list
            }
        })

        // Search button click listener
        val searchButton: ImageButton = findViewById(R.id.searchButton)
        searchButton.setOnClickListener {
            val intent = Intent(this, SearchActivity::class.java)
            startActivity(intent)
        }
    }

    private fun startImageSlider() {
        timer = Timer()
        timer.scheduleAtFixedRate(object : TimerTask() {
            override fun run() {
                runOnUiThread {
                    if (currentPage == imageUrls.size) {
                        currentPage = 0
                    }
                    // Load image using Glide or Picasso
                    Glide.with(this@Dashboard)
                        .load(imageUrls[currentPage])
                        .into(imageView)
                    currentPage++
                }
            }
        }, 0, 3000) // Change slide duration as needed
    }
}