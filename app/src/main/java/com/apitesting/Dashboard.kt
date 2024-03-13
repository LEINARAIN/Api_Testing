package com.apitesting

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.LiveData
import androidx.lifecycle.Observer
import androidx.lifecycle.liveData
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.apitesting.databinding.ActivityDashboardBinding
import retrofit2.Response

class Dashboard : AppCompatActivity() {

    private lateinit var binding: ActivityDashboardBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_dashboard)
        //super.onCreate(savedInstanceState)
        //binding = ActivityDashboardBinding.inflate(layoutInflater)
        //setContentView(binding.root)

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
    }
}