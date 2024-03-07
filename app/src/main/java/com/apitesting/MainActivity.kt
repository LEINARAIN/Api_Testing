package com.apitesting

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageView
import androidx.lifecycle.LiveData
import androidx.lifecycle.Observer
import com.apitesting.databinding.ActivityMainBinding
import retrofit2.Response
import androidx.lifecycle.liveData
import com.bumptech.glide.Glide

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

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

                val selectedIndex = 0

                if (shoeList.isNotEmpty() && selectedIndex < shoeList.size) {
                    val selectedShoe = shoeList[selectedIndex]

                    val shoeName = "Shoe Name: ${selectedShoe.name} \n"
                    binding.titleTextView.text = shoeName
                    // copy, paste, and modify the two code of lines above if you want to display other attributes like, price. Refer to the ShoeItem.kt for the naming)
            }
        }
        //fetching
        val url: String = "https://sneakersphere.online/frontendimg/img/jordan1.png"
        val imageView : ImageView = findViewById (R.id.imageView)
        Glide.with(this).load(url).into(imageView)
    })
}}


